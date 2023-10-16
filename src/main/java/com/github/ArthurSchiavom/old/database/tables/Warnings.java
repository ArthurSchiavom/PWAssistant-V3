package com.github.ArthurSchiavom.old.database.tables;

import com.github.ArthurSchiavom.old.database.base.Table;
import com.github.ArthurSchiavom.old.database.base.TableValue;

public class Warnings extends Table {
	private static Warnings instance;
	private Warnings() {}
	public static Warnings getInstance() {
		return instance;
	}


	public static final String VALUE_USER_ID = "UserID";
	public static final String VALUE_REASON = "Reason";
	public static final String VALUE_TIME = "Time";

	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static Warnings initialize() {
		if (instance == null) {
			instance = new Warnings();
			instance.setTableName("Warnings");
			instance.addTableValue(new TableValue(VALUE_USER_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_REASON, "varchar(2000)"));
			instance.addTableValue(new TableValue(VALUE_TIME, "bigint"));
		}
		return instance;
	}

	@Override
	public boolean loadIntoMemory() {

		return false;
	}
}
