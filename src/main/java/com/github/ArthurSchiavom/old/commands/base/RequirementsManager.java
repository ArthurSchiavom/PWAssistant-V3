package com.github.ArthurSchiavom.old.commands.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a command's requirements.
 */
public class RequirementsManager {
	private List<Requirement> requirements = new ArrayList<>();

	/**
	 * Creates an object that represents a command's requirements.
	 */
	public RequirementsManager() {}

	/**
	 * Adds a command requirement.
	 *
	 * @param requirement The requirement to add.
	 * @return The modified RequirementsManager instance for method chaining.
	 */
	public RequirementsManager addRequirement(Requirement requirement) {
		if (!requirements.contains(requirement)) {
			this.requirements.add(requirement);
		}
		return this;
	}

	/**
	 * Sets this command's requirements.
	 *
	 * @param requirements The list of requirements.
	 */
	public void setRequirements(Requirement... requirements) {
		if (requirements == null) {
			this.requirements.clear();
		}
		else
			this.requirements.addAll(Arrays.asList(requirements));
	}

	/**
	 * Sets this command's requirements.
	 *
	 * @param requirements The list of requirements.
	 */
	public void setRequirements(List<Requirement> requirements) {
		if (requirements != null) {
			this.requirements.addAll(requirements);
		}
		else {
			this.requirements.clear();
		}
	}

	/**
	 * @return This command's requirements.
	 */
	public List<Requirement> getRequirements() {
		return new ArrayList<>(requirements);
	}

	/**
	 * Verifies if an event meets the requirements for this command's execution.
	 * <br>If the requirements are not met, the user is informed of the reason in the channel that originated the event.
	 *
	 * @param event The event to verify.
	 * @return true if the event meets the requirements for this command's execution or
	 * <br>false otherwise.
	 */
	public boolean meetsRequirements(MessageReceivedEvent event) {
		for (Requirement requirement : requirements) {
			if (!requirement.meetsRequirements(event))
				return false;
		}
		return true;
	}

	/**
	 * Verifies if the command has a certain requirement to be run.
	 *
	 * @param requirement The requirement to verify.
	 * @return If it has the requirement.
	 */
	public boolean requires(Requirement requirement) {
		return requirements.contains(requirement);
	}

	/**
	 * Verifies if the command has only certain requirements to be run.
	 *
	 * @param mustHaveRequirements The com.github.ArthurSchiavom.old.commands must have these requirements. Can be null
	 * @param mayAlsoHaveRequirements The com.github.ArthurSchiavom.old.commands may also have these requirements. Can be null
	 * @return If it has only the requirements provided.
	 */
	public boolean requiresOnly(Requirement[] mustHaveRequirements, Requirement[] mayAlsoHaveRequirements) {
		if (mustHaveRequirements == null) {
			mustHaveRequirements = new Requirement[0];
		}
		if (mayAlsoHaveRequirements == null) {
			mayAlsoHaveRequirements = new Requirement[0];
		}

		for (Requirement mustHaveRequirement : mustHaveRequirements) {
			if (!requirements.contains(mustHaveRequirement))
				return false;
		}

		int nRequirements = requirements.size();
		int nRequirementsMatched = mustHaveRequirements.length;
		for (Requirement mayAlsoHaveRequirement : mayAlsoHaveRequirements) {
			if (requirements.contains(mayAlsoHaveRequirement)) {
				nRequirementsMatched++;
			}
		}
		return nRequirements == nRequirementsMatched;
	}

	/**
	 * Verifies if the command has only certain mustHaveRequirements to be run.
	 *
	 * @param mustHaveRequirements The mustHaveRequirements to verify. Can be null (= verify if there's no requirements).
	 * @return If it has only the mustHaveRequirements provided an no other.
	 */
	public boolean requiresOnly(Requirement[] mustHaveRequirements) {
		return requiresOnly(mustHaveRequirements, null);
	}

}
