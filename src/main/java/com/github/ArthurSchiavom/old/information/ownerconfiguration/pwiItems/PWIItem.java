package com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems;

import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;

public class PWIItem {
	private final static String infoLinkBase = "http://www.pwdatabase.com/pwi/items/";
	private final static String priceLinkBase = "https://pwcats.info/";
	private final String id;
	private final String name;
	private final String nameLowercase;

	public PWIItem(String id, String name) {
		this.id = id;
		this.name = name;
		this.nameLowercase = name.toLowerCase();
	}

	private String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNameLowercase() {
		return nameLowercase;
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
		return infoLinkBase + id;
	}

	public String getPriceLink(PwiServer pwiServer) {
		String PWIServerName = pwiServer.getName().replace(" ", "");
		return priceLinkBase + PWIServerName + "/" + id;
	}
}
