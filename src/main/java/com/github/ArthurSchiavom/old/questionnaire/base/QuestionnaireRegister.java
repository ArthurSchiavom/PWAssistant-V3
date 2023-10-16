package com.github.ArthurSchiavom.old.questionnaire.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that registers questionnaires.
 */
public class QuestionnaireRegister {
	private static QuestionnaireRegister instance;
	/**
	 * Initializes this singleton class.
	 */
	public static void initialize() {
		if (instance == null)
			instance = new QuestionnaireRegister();
	}
	/**
	 * @return This singleton's class instance.
	 */
	public static QuestionnaireRegister getInstance() {
		return instance;
	}

	private List<Questionnaire> questionnaires = Collections.synchronizedList(new ArrayList<>());

	public void register(Questionnaire questionnaire) {
		Questionnaire questionnaireToRemove = getQuestionnaire(questionnaire.getUserId(), questionnaire.getChannelId());
		if (questionnaireToRemove != null)
			questionnaires.remove(questionnaireToRemove);
		questionnaires.add(questionnaire);
	}

	public void unregister(Questionnaire questionnaire) {
		questionnaires.remove(questionnaire);
	}

	/**
	 * Processes a possible reply to a com.github.ArthurSchiavom.old.questionnaire;
	 *
	 * @param event The event that contains the reply.
	 * @return If it was a reply.
	 */
	public boolean processPossibleReply(MessageReceivedEvent event) {
		boolean isReply = false;
		for (Questionnaire questionnaire : questionnaires) {
			if (questionnaire.processPossibleReply(event)) {
				isReply = true;
				break;
			}
		}
		return isReply;
	}

	public Questionnaire getQuestionnaire(long userId, long channelId) {
		for (Questionnaire questionnaire : questionnaires) {
			if (questionnaire.isQuestionnaireTarget(userId, channelId)) {
				return questionnaire;
			}
		}
		return null;
	}
}
