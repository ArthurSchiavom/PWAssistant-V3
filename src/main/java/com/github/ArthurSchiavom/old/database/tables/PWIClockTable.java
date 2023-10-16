package com.github.ArthurSchiavom.old.database.tables;

import com.github.ArthurSchiavom.old.database.base.DatabaseManager;
import com.github.ArthurSchiavom.old.database.base.Table;
import com.github.ArthurSchiavom.old.database.base.TableValue;
import com.github.ArthurSchiavom.pwassistant.entity.PwiServer;
import com.github.ArthurSchiavom.old.information.clocks.ClockRegister;
import com.github.ArthurSchiavom.old.information.clocks.PWIClock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PWIClockTable extends Table {
	private static final String PWI_SERVERS_SEPARATOR = ",";
	private static final String VALUE_GUILD_ID = "GuildId";
	private static final String VALUE_CHANNEL_ID = "ChannelId";
	private static final String VALUE_MSG_ID = "MsgId";
	private static final String VALUE_PWI_SERVERS_ID = "PWIServers";

	private static PWIClockTable instance;
	private PWIClockTable() {}
	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static PWIClockTable initialize() {
		if (instance == null) {
			instance = new PWIClockTable();
			instance.setTableName("PWIClocks");
			instance.addTableValue(new TableValue(VALUE_GUILD_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_CHANNEL_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_MSG_ID, "bigint"));
			instance.addTableValue(new TableValue(VALUE_PWI_SERVERS_ID, "varchar(50)"));
		}
		return instance;
	}
	public static PWIClockTable getInstance() {
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

					String[] pwiServersString = rs.getString(4).split(PWI_SERVERS_SEPARATOR);
					List<PwiServer> pwiServers = new ArrayList<>();
					for (String serverString : pwiServersString) {
						pwiServers.add(PwiServer.getServer(serverString));
					}

					register.register(new PWIClock(pwiServers, guildId, channelId, msgId), false);
				}
			} catch (SQLException e) {
				System.out.println("FAILED TO ACCESS DATABASE");
				return false;
			}
		}
		return true;
	}

	public boolean add(PWIClock clock) {
		PreparedStatement stm = this.getInsertStatement();
		try {
			stm.setLong(1, Long.parseLong(clock.getGuildId()));
			stm.setLong(2, Long.parseLong(clock.getChannelId()));
			stm.setLong(3, Long.parseLong(clock.getMessageId()));
			stm.setString(4, calculatePWIServersDatabaseList(clock.getPwiServers()));
			stm.execute();
		} catch (SQLException e) {
			System.out.println("FAILED TO ACCESS DATABASE");
			return false;
		}
		return true;
	}

	private String calculatePWIServersDatabaseList(List<PwiServer> servers) {
		StringBuilder sb = new StringBuilder();
		sb.append(servers.get(0));
		for (int i = 1; i < servers.size(); i++) {
			sb.append(PWI_SERVERS_SEPARATOR + servers.get(i));
		}
		return sb.toString();
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
