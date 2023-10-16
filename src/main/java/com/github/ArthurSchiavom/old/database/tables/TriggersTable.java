package com.github.ArthurSchiavom.old.database.tables;

import com.github.ArthurSchiavom.old.database.base.DatabaseManager;
import com.github.ArthurSchiavom.old.database.base.Table;
import com.github.ArthurSchiavom.old.database.base.TableValue;
import com.github.ArthurSchiavom.old.information.triggers.Trigger;
import com.github.ArthurSchiavom.old.information.triggers.TriggerRegister;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TriggersTable extends Table {
	private static TriggersTable instance;
	private TriggersTable() {}
	public static TriggersTable getInstance() {
		return instance;
	}


	public static final String VALUE_GUILD_ID = "GuildId";
	public static final String VALUE_TRIGGER = "TriggerText";
	public static final String VALUE_REPLY = "Reply";

	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static TriggersTable initialize() {
		if (instance == null) {
			instance = new TriggersTable();
			instance.setTableName("Triggers");
			instance.addTableValue(new TableValue(VALUE_GUILD_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_TRIGGER, "varchar(2000)"));
			instance.addTableValue(new TableValue(VALUE_REPLY, "varchar(2000)"));
		}
		return instance;
	}

	@Override
	public boolean loadIntoMemory() {
		ResultSet rs = this.executeSelectAll();
		if (rs == null)
			return false;

		try {
			while (rs.next()) {
				Trigger loadedTrigger = new Trigger(rs.getLong(1), rs.getString(2), rs.getString(3));
				TriggerRegister.getInstance().register(loadedTrigger, false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean add(Trigger trigger) {
		PreparedStatement stm = this.getInsertStatement();
		if (stm == null)
			return false;

		try {
			stm.setLong(1, trigger.getGuildId());
			stm.setString(2, trigger.getTriggerLowerCase());
			stm.setString(3, trigger.getReply());
			stm.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public boolean remove(long guildId, String trigger) {
		PreparedStatement stm = DatabaseManager.getInstance().createPreparedStatement("DELETE FROM " + this.getTableName()
				+ " WHERE " + VALUE_GUILD_ID + "=? AND " + VALUE_TRIGGER + "=?");
		if (stm == null)
			return false;

		try {
			stm.setLong(1, guildId);
			stm.setString(2, trigger);
			stm.execute();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}