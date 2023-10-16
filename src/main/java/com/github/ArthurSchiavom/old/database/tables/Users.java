package com.github.ArthurSchiavom.old.database.tables;

import com.github.ArthurSchiavom.old.database.base.Table;
import com.github.ArthurSchiavom.old.database.base.TableValue;

public class Users extends Table {
	private static Users instance;
	private Users() {}
	public static Users getInstance() {
		return instance;
	}


	public static final String VALUE_USER_ID = "UserId";
	public static final String VALUE_WARNINGS = "Warnings";

	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static Users initialize() {
		if (instance == null) {
			instance = new Users();
			instance.setTableName("Users");
			instance.addTableValue(new TableValue(VALUE_USER_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_WARNINGS, "varchar(2000)"));
		}
		return instance;
	}

	@Override
	public boolean loadIntoMemory() {
		return false;
	}
}
