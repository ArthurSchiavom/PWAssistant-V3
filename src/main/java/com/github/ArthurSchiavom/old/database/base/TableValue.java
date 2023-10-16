package com.github.ArthurSchiavom.old.database.base;

public class TableValue {
	private final String valueName;
	private final String valueType;

	public TableValue(String valueName, String valueType) {
		this.valueName = valueName;
		this.valueType = valueType;
	}

	public String getValueName() {
		return valueName;
	}

	public String getValueType() {
		return valueType;
	}

	/**
	 * @return The MySQL request representation
	 */
	public String calcMysqlRepresentation() {
		return valueName + " " + valueType;
	}
}
