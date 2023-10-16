package com.github.ArthurSchiavom.old.questionnaire.PWIKingdomWelcome;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Guilds;
import com.github.ArthurSchiavom.old.information.ownerconfiguration.roles.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import com.github.ArthurSchiavom.old.questionnaire.base.Questionnaire;
import com.github.ArthurSchiavom.old.utils.Utils;

import java.util.List;

public class PWIKingdomWelcomeQuestionnaire extends Questionnaire {
    public PWIKingdomWelcomeQuestionnaire() {
        this.addQuestion("**Hello and welcome to PWI Kingdom!** <:ffyay:489925231591424010>" +
                        "\nI'm the server's assistant! (I'm also a bot if you didn't notice) I will help you configure your experience in our server!" +
                        "\n\nWhat PWI server(s) do you play in? Answering this will let everyone easily know which server each of us plays in. :)"
                        + "\n(**Reply with `none` if you don't want to answer right now** or don't play. You can set this with a command at any time.)"
                        + "\n```fix\n1/3```"
                , event -> {
                    String msgLC = event.getMessage().getContentRaw().toLowerCase();

                    User author = event.getAuthor();
                    event.getJDA()
                            .getTextChannelById("589105381784027137")
                            .sendMessage(String.format("%s (%s, %s) started answering the welcome com.github.ArthurSchiavom.old.questionnaire",
                                    author.getAsMention(), author.getAsTag(), author.getId())).queue();

                    if (msgLC.equals("none")) {
                        this.nextQuestion();
                        return;
                    }

                    List<Long> mentionedRolesIds = Roles.getPWIServerRolesMentionedIn(msgLC);
                    if (mentionedRolesIds.isEmpty()) {
                        event.getChannel().sendMessage("I couldn't detect any server mentioned. Please choose `Dawnglory`, `Etherblade`, `Twilight Temple` and/or `Tideswell`").queue();
                        return;
                    }

                    Guild guild = Guilds.getMainGuild();
                    String userId = event.getAuthor().getId();
                    Member member = guild.getMemberById(userId);
                    Utils.batchToggleRoles(guild, member, mentionedRolesIds);
                    this.nextQuestion();
                }
                , true);

        this.addQuestion("Thank you! Which classes do you play? This will allow others to easily know it."
                        + "\n(**Reply with `none` if you don't want to answer right now** or don't play. You can set this with a command at any time.)"
                        + "\n```fix\n2/3```"
                , event -> {
                    String msgLC = event.getMessage().getContentRaw().toLowerCase();
                    if (msgLC.equals("none")) {
                        this.nextQuestion();
                        return;
                    }

                    List<Long> mentionedRolesIds = Roles.getPWIClassRolesMentionedIn(msgLC);
                    if (mentionedRolesIds.isEmpty()) {
                        event.getChannel().sendMessage("I couldn't detect any class mentioned. Please choose one or more such as `Mystic`, `Blademaster`, `Assassin`, etc").queue();
                        return;
                    }

                    Guild guild = Guilds.getMainGuild();
                    String userId = event.getAuthor().getId();
                    Member member = guild.getMemberById(userId);
                    Utils.batchToggleRoles(guild, member, mentionedRolesIds);
                    this.nextQuestion();
                }
                , true);

        this.addQuestion("Awesome! We often announce games that are temporarily offering free-forever copies. Would you like to be " +
                        "notified about these announcements? (answer with `yes` or `no`)" +
                        "\nThis can be changed later." +
                        "\n```fix\n3/3```"
                , event -> {
                    String msgLC = event.getMessage().getContentRaw().toLowerCase();
                    boolean giveRole;
                    giveRole = msgLC.contains("yes");

					if (giveRole) {
						Guild guild = Guilds.getMainGuild();
						String userId = event.getAuthor().getId();
						guild.retrieveMemberById(userId).flatMap(member -> guild.addRoleToMember(member, Roles.retrieveJdaFreeGamesRole())).queue();
					}

                    MessageChannel channel = event.getChannel();
                    channel.sendMessage("You're all set up! <:ffhappy3:489925231356411904>"
                            + "\nYou can change these configurations at any time. <:ffsurprise:489925231511601183>"
                            + "\n**Make sure to read the rules at <#617691793315725317>!**"
                            + "\nIf you need any help, feel free to mention @Moderator.").queue();
					this.nextQuestion();
                }
                , true);
    }

    @Override
    protected void setConfiguration() {
        this.setConfigExpirationTime(18000 /* 18000 = 3 hours */);
    }
}
