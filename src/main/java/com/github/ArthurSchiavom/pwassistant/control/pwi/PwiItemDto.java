package com.github.ArthurSchiavom.pwassistant.control.pwi;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class PwiItemDto {
	@Getter(AccessLevel.NONE)
	private final static String pwDatabaseLinkPrefix = "http://www.pwdatabase.com/pwi/items/";
	private final String id;
	private final String name;
	private final String nameLowercase;

	public PwiItemDto(final String id, final String name) {
		this.id = id;
		this.name = name;
		this.nameLowercase = name.toLowerCase();
	}

	/**
	 * Checks if some text might be a reference to this item.
	 *
	 * @param textLowerCase The text to analyse, in lower case.
	 * @return if the text might be a reference to this item.
	 */
	public boolean mightReferToThisItem(String textLowerCase) {
		return nameLowercase.contains(textLowerCase);
	}

	public String getInfoLink() {
		return pwDatabaseLinkPrefix + id;
	}
}
