package com.github.ArthurSchiavom.old.database.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Table {
	private String tableName;
	private ArrayList<TableValue> tableValues = new ArrayList<>(); // Must maintain order
	private String tableValuesNamesOnlyList;
	private String fullQuestionMarkListForStatement;
	private String insertCommand;

	/**
	 * Loads the com.github.ArthurSchiavom.old.database info into the memory.
	 */
	public abstract boolean loadIntoMemory();

	protected void setTableName(String tableName) {
		this.tableName = tableName;
	}

	protected void addTableValue(TableValue tableValue) {
		tableValues.add(tableValue);
		if (tableValuesNamesOnlyList == null) {
			tableValuesNamesOnlyList = tableValue.getValueName();
			fullQuestionMarkListForStatement = "?";
		}
		else {
			tableValuesNamesOnlyList += ", " + tableValue.getValueName();
			fullQuestionMarkListForStatement += ", ?";
		}
		updateInsertCommand();
	}

	public String getTableName() {
		return tableName;
	}

	public String calcMySQLCreateTableCommand() {
		return "CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + calcTableValuesStringFull() +
				",id int(10) unsigned primary KEY AUTO_INCREMENT)";
	}

	private String calcTableValuesStringFull() {
		StringBuilder sb = new StringBuilder();
		sb.append(tableValues.get(0).calcMysqlRepresentation());
		for (int i = 1; i < tableValues.size(); i++) {
			sb.append(",").append(tableValues.get(i).calcMysqlRepresentation());
		}
		return sb.toString();
	}

	/**
	 * @return (1) The statement with values to set according to the order that they were inserted in if successul or (2) null otherwise
	 * <br><br>Prints com.github.ArthurSchiavom.old.error message to console in case of failure.
	 */
	protected PreparedStatement getInsertStatement() {
		return DatabaseManager.getInstance().createPreparedStatement(insertCommand);
	}

	private void updateInsertCommand() {
		insertCommand = String.format("INSERT INTO %s(%s) values (%s)", tableName
				, tableValuesNamesOnlyList
				, fullQuestionMarkListForStatement);
	}


	/**
	 * Executes <b>SELECT * FROM TableName</b> MySQL command.
	 * <br><br>Prints com.github.ArthurSchiavom.old.error message to console in case of failure.
	 *
	 * @return (1) The result in case of success or (2) null otherwise.
	 */
	protected ResultSet executeSelectAll() {
		PreparedStatement stm = DatabaseManager.getInstance().createPreparedStatement("SELECT * FROM " + tableName);
		try {
			return stm.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Adds a row to the table.
	 *
	 * @param values The values, according to the order they were added
	 * @return If the operation was successful.
	 */
	protected boolean add__Internal(Object... values) {
		try {
			PreparedStatement stm = this.getInsertStatement();
			setStatementValues(stm, values);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
			com.github.ArthurSchiavom.old.error.Reporter.report("Failed to add row on table " + tableName);
			return false;
		}
		return true;
	}

	/**
	 * Removes the rows that have the values specified.
	 *
	 * @param valuesNames The names of the constraint values.
	 * @param values The values, according to the order of valuesNames;
	 * @return (1) The number of rows modified in case of success or (2) null if failure.
	 */
	protected Integer remove__Internal(String[] valuesNames, Object... values) {
		String statementStr = "DELETE FROM " + this.getTableName() + " WHERE " + calcSqlStatementNConstraints(valuesNames);
		PreparedStatement stm = DatabaseManager.getInstance().createPreparedStatement(statementStr);
		if (stm == null)
			return null;

		try {
			setStatementValues(stm, values);
			return stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			com.github.ArthurSchiavom.old.error.Reporter.report("Failed to remove row on table " + tableName);
			return null;
		}
	}

	/**
	 * Sets the values in the given statement by the order they are inserted. (1st value has parameter index 1, 2nd value has parameter index 2,...)
	 *
	 * @param stm The target statement.
	 * @param values The values to set in order.
	 * @return If the operation was succesful.
	 */
	private boolean setStatementValues(PreparedStatement stm, Object... values) {
		try {
			int nValue = 0;
			for (Object value : values) {
				// TODO - what is this? stm.setArray()
				stm.setObject(++nValue, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			com.github.ArthurSchiavom.old.error.Reporter.report("Failed to set statement values on table " + tableName);
			return false;
		}
		return true;
	}

	/**
	 * Calculates the SQL statement part that restricts to certain parameters: "valName1=? AND valName2=?...".
	 *
	 * @param valuesNames The name of the constraint values.
	 * @return The constraint string part of the statement.
	 */
	private String calcSqlStatementNConstraints(String[] valuesNames) {
		StringBuilder sb = new StringBuilder();
		sb.append(valuesNames[0]).append("=?");
		for (int i = 1; i < valuesNames.length; i++) {
			sb.append(" AND ").append(valuesNames[i]).append("=?");
		}
		return sb.toString();
	}
}
