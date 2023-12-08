package com.github.ArthurSchiavom.pwassistant.boundary.questionnaire;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question.MessageType;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question.QuestionManager;
import com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.question.ReplyProcessorFunc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Questionnaire model
 */
public abstract class Questionnaire {
	public static final String CANCEL_MESSAGE_LOWERCASE = "$cancel";

	private QuestionManager questionManager = new QuestionManager(this);
	private MessageChannel channel; // DO NOT OPEN PUBLIC ACCESS - this object might not be updated (by JDA). BE SUSPICIOUS IF BUGS ARISE.
	private long channelId;
	private long userId;
	private WhichMessagesToDelete whichMessagesToDelete;
	private WhenToDeleteMessages whenToDeleteMessages;
	private List<Message> messagesToDeleteEnd = Collections.synchronizedList(new ArrayList<>()); // Synchronizing is a safety check, just to make sure that I won't ever end up with a concurrent access exception.
	private List<Message> messagesToDeleteNextQuestion = Collections.synchronizedList(new ArrayList<>());

	/**
	 * Start this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire with the default configuration.
	 *
	 * @param event The event that requested the com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.
	 */
	public void startQuestionnaire(MessageChannel channel, long userId) {
		this.channel = channel;
		channelId = channel.getIdLong();
		this.userId = userId;
		whenToDeleteMessages = WhenToDeleteMessages.NEVER;
		whichMessagesToDelete = WhichMessagesToDelete.NONE;
		setConfiguration();
		nextQuestion();
		QuestionnaireRegister.getInstance().register(this);
	}

	/**
	 * Set the com.github.ArthurSchiavom.pwassistant.boundary.questionnaire's default configuration such as which messages to delete by default.
	 */
	protected abstract void setConfiguration();

	public long getChannelId() {
		return channelId;
	}

	public long getUserId() {
		return userId;
	}

	/**
	 * Adds a question to this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.
	 *
	 * @param question The question to ask.
	 * @param questionReplyProcessorFunc The reply processor.
	 * @param runInNewThread If the processing should run in a parallel thread.
	 */
	protected void addQuestion(String question, ReplyProcessorFunc questionReplyProcessorFunc, boolean runInNewThread) {
		questionManager.addQuestion(question + String.format("\n\n`To cancel, reply with %s`", CANCEL_MESSAGE_LOWERCASE), questionReplyProcessorFunc, runInNewThread);
	}

	/**
	 * Processes a reply.
	 *
	 * @param event The event containing the reply.
	 * @return If the event was a com.github.ArthurSchiavom.pwassistant.boundary.questionnaire target and therefore processed.
	 */
	public boolean processPossibleReply(MessageReceivedEvent event) {
		if (!isQuestionnaireTarget(event))
			return false;

		processReply(event);
		return true;
	}

	/**
	 * Verifies if an event is target of this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire's analysis.
	 *
	 * @param event The event to analyse.
	 * @return If the event is target of this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire's analysis.
	 */
	public boolean isQuestionnaireTarget(MessageReceivedEvent event) {
		return isQuestionnaireTarget(event.getAuthor().getIdLong()
				,event.getChannel().getIdLong());
	}

	/**
	 * Verifies if a user+channel is target of this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire's analysis.
	 *
	 * @param userId The user's ID.
	 * @param channelId The channel's ID.
	 * @return If the user+channel is target of this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire's analysis.
	 */
	public boolean isQuestionnaireTarget(long userId, long channelId) {
		return this.userId == userId
				&& this.channelId == channelId;
	}

	/**
	 * Processes a reply.
	 *
	 * @param event The event containing the reply.
	 */
	public void processReply(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		queueMessageToDeleteDefault(msg, MessageType.ANSWER);
		if (msg.getContentRaw().toLowerCase().equals(CANCEL_MESSAGE_LOWERCASE)) {
			channel.sendMessage("Canceled!").queue();
			endQuestionnaire();
			return;
		}

		questionManager.processReply(event);
	}

	/**
	 * Moves over to the updateNextExecutionTime question and asks it.
	 */
	protected void nextQuestion() {
		String questionText = questionManager.goToNextQuestion();
		questionChanged(questionText);
	}

	protected void previousQuestion() {
		String questionText = questionManager.goToPreviousQuestion();
		questionChanged(questionText);
	}

	protected void goForwardNQuestions(int n) {
		String questionText = questionManager.goForwardNQuestions(n);
		questionChanged(questionText);
	}

	protected void goBackNQuestions(int n) {
		String questionText = questionManager.goBackNQuestions(n);
		questionChanged(questionText);
	}

	protected void goToQuestion(int n) {
		String questionText = questionManager.goToQuestion(n);
		questionChanged(questionText);
	}

	protected void failedToSendMessageProcessing(Throwable throwable) {}

	/**
	 * * Deletes the messages queued for deletion on new question.
	 * <br>* (1) Ends the com.github.ArthurSchiavom.pwassistant.boundary.questionnaire or (2) asks the question and queues it for deletion if appliable.
	 *
	 * @param questionText The question text or null to end the com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.
	 */
	private void questionChanged(String questionText) {
		deleteMessagesNextQuestion();

		if (questionText == null) {
			endQuestionnaire();
		} else {
			channel.sendMessage(questionText)
					.queue(msg -> queueMessageToDeleteDefault(msg, MessageType.QUESTION)
					, throwable -> {
								failedToSendMessageProcessing(throwable);
								endQuestionnaire();
							});
		}
	}

	/**
	 * Initialize the timeout com.github.ArthurSchiavom.old.timer for a question.
	 */
	public void questionnaireTimedOut() {
		try {
			channel.sendMessage("You took too long to answer.").queue();
		} catch (Exception e) {} // will throw exception if the channel isn't reachable
		endQuestionnaire();
	}

	/**
	 * Adds a message to be deleted using the current settings, if set to be deleted.
	 *
	 * @param msg The message to possibly be queued for deletion.
	 * @param msgType The type of message.
	 */
	private void queueMessageToDeleteDefault(Message msg, MessageType msgType) {
		if (whichMessagesToDelete.appliesTo(msgType))
			whenToDeleteMessages.addMessageToDelete(this, msg);
	}

	/**
	 * Adds a message to delete at the end of the com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.
	 *
	 * @param message The message to add.
	 */
	public void queueMessageToDeleteEnd(Message message) {
		messagesToDeleteEnd.add(message);
	}

	/**
	 * Adds a message to delete when the updateNextExecutionTime question is asked.
	 *
	 * @param message The message to add.
	 */
	public void queueMessageToDeleteNextQuestion(Message message) {
		messagesToDeleteNextQuestion.add(message);
	}

	/**
	 * Ends this com.github.ArthurSchiavom.pwassistant.boundary.questionnaire.
	 */
	private void endQuestionnaire() {
		QuestionnaireRegister.getInstance().unregister(this);
		deleteMessagesEnd();
		questionManager.end();
	}

	private void deleteMessagesEnd() {
		batchDeleteMessages(messagesToDeleteEnd);
	}

	private void deleteMessagesNextQuestion() {
		batchDeleteMessages(messagesToDeleteNextQuestion);
	}

	private void batchDeleteMessages(List<Message> msgs) {
		if (!msgs.isEmpty()) {
			try {
				synchronized (msgs) {
					channel.purgeMessages(msgs);
				}
			} catch (Exception e) {}
			msgs.clear();
		}
	}

	public WhichMessagesToDelete getWhichMessagesToDelete() {
		return whichMessagesToDelete;
	}

	public void setConfigWhichMessagesToDelete(WhichMessagesToDelete whichMessagesToDelete) {
		this.whichMessagesToDelete = whichMessagesToDelete;
	}

	public WhenToDeleteMessages getWhenToDeleteMessages() {
		return whenToDeleteMessages;
	}

	public void setConfigWhenToDeleteMessages(WhenToDeleteMessages whenToDeleteMessages) {
		this.whenToDeleteMessages = whenToDeleteMessages;
	}

	/**
	 * It might take up to one minute more than the set time for the question to expire.
	 *
	 * @param timeInSeconds The new expiration time in seconds.
	 */
	public void setConfigExpirationTime(int timeInSeconds) {
		questionManager.setExpirationTimeSeconds(timeInSeconds);
	}
}
