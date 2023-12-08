package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.questionnaires.messagingSchedule;

import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingScheduler;
import com.github.ArthurSchiavom.old.information.scheduling.messageSchedule.MessagingSchedulerRegister;
import com.github.ArthurSchiavom.old.utils.Utils;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.Questionnaire;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhenToDeleteMessages;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.WhichMessagesToDelete;
import com.github.ArthurSchiavom.pwassistant.control.schedule.ScheduledMessageService;
import com.github.ArthurSchiavom.pwassistant.entity.RepetitionType;
import com.github.ArthurSchiavom.pwassistant.entity.ScheduledMessage;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

import java.util.List;

public class AddMessagingScheduleQuestionnaire extends Questionnaire {

	private String name;
	private RepetitionType repetitionType;
	private List<Integer> days;
	private int[] hourMinute;
	private String channelId;
	private String guildId;
	private final ScheduledMessageService scheduleService;
	public AddMessagingScheduleQuestionnaire(ScheduledMessageService scheduleService) {
		this.scheduleService = scheduleService;

		this.addQuestion("What do you want to call this schedule? The name will identify the schedule so you can manage it later."
				, event -> {
					name = event.getMessage().getContentRaw();
					guildId = event.getGuild().getId();
					if (name.length() > 20) {
						event.getChannel().sendMessage("The name can't have more than 20 characters. What's the schedule name?").queue();
					}
					else {
						MessagingScheduler messagingScheduler = MessagingSchedulerRegister.getSchedule(guildId, name);
						if (messagingScheduler == null)
							this.nextQuestion();
						else
							event.getChannel().sendMessage("There's already a schedule with that name! Please choose a different one." +
									"\nWhat's the schedule name?").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
				}
				, false);

		this.addQuestion("Is the repetition weekly (select days of week) or monthly (select days of month)?" +
						"\nReply with `weekly` or `monthly`."
				, event -> {
					String reply = event.getMessage().getContentRaw();
					if (reply.equalsIgnoreCase("weekly")) {
						repetitionType = RepetitionType.WEEKLY;
						this.nextQuestion();
					}
					else if (reply.equalsIgnoreCase("monthly")) {
						repetitionType = RepetitionType.MONTHLY;
						this.goForwardNQuestions(2);
					}
					else {
						event.getChannel().sendMessage("I did not understand that. You must reply with either `weekly` or `monthly`.").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
				}
				, false);

		this.addQuestion("Which are the days of the week in UTC timezone? (`monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`)"
				, event -> {
					days = Utils.getMentionedDaysOfWeek(event.getMessage().getContentRaw());
					if (days.isEmpty())
						event.getChannel().sendMessage("Failed to understand that. Please choose 1+ of the following: `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					else
						this.goForwardNQuestions(2);
				}
				, false);

		this.addQuestion("Which are the days of the month in UTC timezone? **__Reply with only numbers separated by white spaces!!__** (`1 5 18 30`)" +
						"\nNote that if you choose a day that a month doesn't have," +
						" the closest smaller day will be chosen." +
						" For example, if you choose day 31 but the month only has 29 days, the message will be sent on day 29."
				, event -> {
					String reply = event.getMessage().getContentRaw();
					days = Utils.extractNumbersSeparatedByWhitespace(reply);
					if (days.isEmpty())
						event.getChannel().sendMessage("I could not understand that. Input the days in numbers and separated by **__whitespaces__**").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					else
						this.nextQuestion();
				}
				, false);

		this.addQuestion("What's the time in 24H format and UTC timezone? Example: `22:15`"
				, event -> {
					String reply = event.getMessage().getContentRaw();
					hourMinute = Utils.TimeStringToIntArray(reply);
					if (hourMinute != null)
						this.nextQuestion();
					else
						event.getChannel().sendMessage("Invalid time. What's the time in 24H format? Example: `22:15`").queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
				}
				, false);

		this.addQuestion("Which channel should the messages be sent on? (example: #announcements)"
				, event -> {
					List<GuildMessageChannel> textChannels = event.getMessage().getMentions().getChannels(GuildMessageChannel.class);
					if (textChannels.isEmpty()) {
						TextChannel channel = event.getChannel().asTextChannel();
						channel.sendMessage("I couldn't understand that. Please mention the channel, for example: " + channel.getAsMention()).queue(msg -> this.queueMessageToDeleteNextQuestion(msg));
					}
					else {
						channelId = textChannels.get(0).getId();
						this.nextQuestion();
					}
				}
				, false);

		this.addQuestion("And finally! What's the message to send?"
				, event -> {
					final String messageString = event.getMessage().getContentRaw();

					final ScheduledMessage scheduledMessage = new ScheduledMessage();
					scheduledMessage.setScheduleName(name);
					scheduledMessage.setServerId(event.getGuild().getIdLong());
					scheduledMessage.setChannelId(Long.parseLong(channelId));
					scheduledMessage.setMessage(messageString);
					scheduledMessage.setRepetitionType(repetitionType);
					scheduledMessage.setScheduleDays(days);
					scheduledMessage.setHour(hourMinute[0]);
					scheduledMessage.setMinute(hourMinute[1]);
					scheduleService.addSchedule(scheduledMessage);

					final String reply = "Message scheduled! <:png13:489925231356411904>";
					event.getChannel().sendMessage(reply).queue();
					this.nextQuestion();
				}
				, false);
	}

	@Override
	protected void setConfiguration() {
		this.setConfigWhichMessagesToDelete(WhichMessagesToDelete.ALL);
		this.setConfigWhenToDeleteMessages(WhenToDeleteMessages.NEXT_QUESTION);
	}
}
