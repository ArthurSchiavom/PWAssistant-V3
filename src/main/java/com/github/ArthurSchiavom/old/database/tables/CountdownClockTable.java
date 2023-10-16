package com.github.ArthurSchiavom.old.database.tables;

import com.github.ArthurSchiavom.old.database.base.DatabaseManager;
import com.github.ArthurSchiavom.old.database.base.Table;
import com.github.ArthurSchiavom.old.database.base.TableValue;
import com.github.ArthurSchiavom.old.information.clocks.ClockRegister;
import com.github.ArthurSchiavom.old.information.clocks.CountdownClock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;

public class CountdownClockTable extends Table {
	private static final String VALUE_GUILD_ID = "GuildId";
	private static final String VALUE_CHANNEL_ID = "ChannelId";
	private static final String VALUE_MSG_ID = "MsgId";
	private static final String VALUE_END_TIME_EPOCH = "EndTimeEpoch";

	private static CountdownClockTable instance;
	private CountdownClockTable() {}
	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static CountdownClockTable initialize() {
		if (instance == null) {
			instance = new CountdownClockTable();
			instance.setTableName("CountdownClocks");
			instance.addTableValue(new TableValue(VALUE_GUILD_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_CHANNEL_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_MSG_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_END_TIME_EPOCH, "bigint"));
		}
		return instance;
	}
	public static CountdownClockTable getInstance() {
		return instance;
	}

	@Override
	public boolean loadIntoMemory() {
		ResultSet rs = this.executeSelectAll();
		if (rs != null) {
			try {
				ClockRegister register = ClockRegister.getInstance();
				while (rs.next()) {
					long guildId = rs.getLong(1);
					long channelId = rs.getLong(2);
					long msgId = rs.getLong(3);
					long endTimeEpoch = rs.getLong(4);
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(endTimeEpoch);
					Instant endTime = cal.toInstant();

					register.register(new CountdownClock(endTime, guildId, channelId, msgId), false);
				}
			} catch (SQLException e) {
				System.out.println("FAILED TO ACCESS DATABASE");
				return false;
			}
		}
		return true;
	}

	public boolean add(CountdownClock clock) {
		PreparedStatement stm = this.getInsertStatement();
		try {
			stm.setLong(1, Long.parseLong(clock.getGuildId()));
			stm.setLong(2, Long.parseLong(clock.getChannelId()));
			stm.setLong(3, Long.parseLong(clock.getMessageId()));
			stm.setLong(4, clock.getEndTime().toEpochMilli());
			stm.execute();
		} catch (SQLException e) {
			System.out.println("FAILED TO ACCESS DATABASE");
			return false;
		}
		return true;
	}

	@SuppressWarnings("Duplicates")
	public boolean remove(String msgId) {
		PreparedStatement stm = DatabaseManager.getInstance().createPreparedStatement("DELETE FROM " + this.getTableName()
				+ " WHERE " + VALUE_MSG_ID + "=?");
		if (stm == null)
			return false;

		try {
			stm.setLong(1, Long.parseLong(msgId));
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILED TO ACCESS DATABASE");
			return false;
		}
		return true;
	}
}
