package com.github.ArthurSchiavom.old.information.admins;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

/**
 * Manages admin permissions.
 */
public class AdminsManager {
	private static AdminsManager instance;
	private AdminsManager() {}
	public static void initialize() {
		if (instance == null)
			instance = new AdminsManager();
	}

	/**
	 * @return This singleton's instance.
	 */
	public static AdminsManager getInstance() {
		return instance;
	}

	/**
	 * Verifies if a member is admin.
	 *
	 * @param member The member to verify.
	 * @return If the member is an admin.
	 */
	public boolean isAdmin(Member member) {
		if (member.hasPermission(Permission.ADMINISTRATOR))
			return true;
		return false;
	}
}
