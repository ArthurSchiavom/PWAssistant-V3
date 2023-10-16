package com.github.ArthurSchiavom.old.information.ownerconfiguration.roles;

class Role {
	private String[] roleIdentifiyingNamesLowerCase;
	private long roleId;

	/**
	 * Creates an object that represents a role and it's identifiers.
	 *
	 * @param roleId The role ID.
	 * @param roleIdentifyingNames The names that identify the role such as "Blademast" and "BM"
	 */
	Role(long roleId, String... roleIdentifyingNames) {
		this.roleId = roleId;
		for (int i = 0; i < roleIdentifyingNames.length; i++) {
			roleIdentifyingNames[i] = roleIdentifyingNames[i].toLowerCase();
		}
		roleIdentifiyingNamesLowerCase = roleIdentifyingNames;
	}

	public long getRoleId() {
		return roleId;
	}

	/**
	 * Verifies if the text given contains any of this role's names.
	 *
	 * @param lowercaseText The text to analyse in lowercase.
	 * @return If the text given contains any of this role's names.
	 */
	public boolean textMentionsThisRole(String lowercaseText) {
		for (String name : roleIdentifiyingNamesLowerCase) {
			if (lowercaseText.contains(name))
				return true;
		}
		return false;
	}
}
