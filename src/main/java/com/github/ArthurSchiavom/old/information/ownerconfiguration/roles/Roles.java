package com.github.ArthurSchiavom.old.information.ownerconfiguration.roles;

import com.github.ArthurSchiavom.old.information.ownerconfiguration.Guilds;
import net.dv8tion.jda.api.JDA;

import java.util.ArrayList;
import java.util.List;

/**
 * Register com.github.ArthurSchiavom.old.information about the pwiClassRoles.
 */
public class Roles {
	private static List<Role> pwiClassRoles = new ArrayList<>();
	private static List<Role> pwiServerRoles = new ArrayList<>();
	private static final Role NSFW_ROLE = new Role(426451133628678146L, "nsfw");
	private static final Role FREE_GAMES_ROLE = new Role(568475847736950806L, "free game");

	/**
	 * Registers the role in the memory.
	 */
	public static void initialize() {
		pwiClassRoles.add(new Role(475801535930957834L, "blademast", "bm"));
		pwiClassRoles.add(new Role(475801409200193546L, "seek"));
		pwiClassRoles.add(new Role(475801700385685504L, "barb", "kitten", "kitty"));
		pwiClassRoles.add(new Role(587737834752835584L, "edge"));
		pwiClassRoles.add(new Role(475801973086617641L, "arch"));
		pwiClassRoles.add(new Role(475802151432486912L, "sin"));
		pwiClassRoles.add(new Role(475802194180833320L, "dusk", "db"));
		pwiClassRoles.add(new Role(587740707415130148L, "tech"));
		pwiClassRoles.add(new Role(469939961051873291L, "wiz"));
		pwiClassRoles.add(new Role(475805335853072384L, "cleri"));
		pwiClassRoles.add(new Role(475805377468825600L, "myst"));
		pwiClassRoles.add(new Role(475805413791629352L, "storm", "sb"));
		pwiClassRoles.add(new Role(475805562588889097L, "veno"));
		pwiClassRoles.add(new Role(475806040579899392L, "psy"));

		pwiServerRoles.add(new Role(718236488428683327L, "da"));
		pwiServerRoles.add(new Role(718233182956421162L, "et"));
		pwiServerRoles.add(new Role(718236177169252572L, "tw", "tt"));
		pwiServerRoles.add(new Role(718236344282906666L, "ti"));
	}

	public static Role getNsfwRole() {
		return NSFW_ROLE;
	}

	public static net.dv8tion.jda.api.entities.Role retrieveJdaNsfwRole() {
		return Guilds.getMainGuild().getRoleById(NSFW_ROLE.getRoleId());
	}

	public static net.dv8tion.jda.api.entities.Role retrieveJdaFreeGamesRole() {
		return Guilds.getMainGuild().getRoleById(FREE_GAMES_ROLE.getRoleId());
	}

	/**
	 * Finds the IDs of all pwi class roles contained in the text (names, not mentions)
	 *
	 * @param text The text to analyse.
	 * @return A never-null list of the IDs of the pwi class roles found.
	 */
	public static List<Long> getPWIClassRolesMentionedIn(String text) {
		return getRolesMentionedIn(text, pwiClassRoles);
	}

	/**
	 * Finds the IDs of all pwi server roles contained in the text (names, not mentions)
	 *
	 * @param text The text to analyse.
	 * @return A never-null list of the IDs of the pwi server roles found.
	 */
	public static List<Long> getPWIServerRolesMentionedIn(String text) {
		return getRolesMentionedIn(text, pwiServerRoles);
	}

	private static List<Long> getRolesMentionedIn(String text, List<Role> rolesToSearchFor) {
		List<Long> idsFound = new ArrayList<>();
		text = text.toLowerCase();
		for (Role role : rolesToSearchFor) {
			if (role.textMentionsThisRole(text))
				idsFound.add(role.getRoleId());
		}
		return idsFound;
	}

	/**
	 * Finds the pwiClassRoles of all pwiClassRoles contained in the text (names, not mentions).
	 *
	 * @param text The text to analyse.
	 * @param jda The JDA instance to use.
	 * @return A never-null list of the IDs of the pwiClassRoles found.
	 */
	public static List<net.dv8tion.jda.api.entities.Role> getPWIClassRolesMentionedIn(String text, JDA jda) {
		List<Long> idsFound = getPWIClassRolesMentionedIn(text);
		List<net.dv8tion.jda.api.entities.Role> rolesFound = new ArrayList<>();
		for (Long id : idsFound) {
			rolesFound.add(jda.getRoleById(id));
		}
		return rolesFound;
	}
}
