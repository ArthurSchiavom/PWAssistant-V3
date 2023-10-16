package com.github.ArthurSchiavom.old.information.ownerconfiguration.pwiItems;

import java.util.ArrayList;
import java.util.List;

public class PWIItems {
	private static List<PWIItem> pwiItems = new ArrayList<>();

	public static void addItem(String name, String id) {
		pwiItems.add(new PWIItem(name, id));
	}

	public static List<PWIItem> getMatchingItems(String itemName) {
		itemName = itemName.toLowerCase();
		List<PWIItem> matchingItems = new ArrayList<>();
		for (PWIItem item : pwiItems) {
			if (item.mightReferToThisItem(itemName))
				matchingItems.add(item);
		}
		return matchingItems;
	}
}
