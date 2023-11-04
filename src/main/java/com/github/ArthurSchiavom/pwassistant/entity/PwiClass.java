package com.github.ArthurSchiavom.pwassistant.entity;

import lombok.Getter;

import java.util.List;

@Getter
public enum PwiClass {
	BLADEMASTER("Blademaster", List.of("bm", "blad"), 475801535930957834L)
	, WIZARD("Wizard", List.of("wiz"), 469939961051873291L)
	, SEEKER("Seeker", List.of("seek"), 475801409200193546L)
	, BARBARIAN("Barbarian", List.of("barb", "kitten", "kitty"), 475801700385685504L)
	, EDGERUNNER("Edgerunner", List.of("edge"), 587737834752835584L)
	, ARCHER("Archer", List.of("arch"), 475801973086617641L)
	, ASSASSIN("Assassin", List.of("sin"), 475802151432486912L)
	, DUSKBLADE("Duskblade", List.of("dusk", "db"), 475802194180833320L)
	, TECHNICIAN("Technician", List.of("tech"), 587740707415130148L)
	, CLERIC("Cleric", List.of("cleri"), 475805335853072384L)
	, MYSTIC("Mystic", List.of("myst"), 475805377468825600L)
	, STORMBRINGER("Stormbringer", List.of("storm", "sb"), 475805413791629352L)
	, VENOMANCER("Venomancer", List.of("veno"), 475805562588889097L)
	, PSYCHIC("Psychic", List.of("psy"), 475806040579899392L)
	, WILDWALKER("Wildwalker", List.of("wild"), 1166870190814937148L)
	;

	private final String name;
	private final List<String> nameSubtexts;
	private final long roleId;

	PwiClass(final String name, final List<String> nameSubtexts, final long roleId) {
		this.name = name;
		this.nameSubtexts = nameSubtexts;
		this.roleId = roleId;
	}

	/**
	 * Finds a server mentioned in a String.
	 *
	 * @param string The string to analyze.
	 * @return (1) The enum item matching the name or
	 * <br>(2) <b>null</b> if no item matches the name.
	 */
	public static PwiClass fromString(String string) {
		string = string.toLowerCase();
		for (final PwiClass pwiClass : PwiClass.values()) {
			for (final String nameSubtext : pwiClass.nameSubtexts) {
				if (string.contains(nameSubtext)) {
					return pwiClass;
				}
			}
		}
		return null;
	}
}
