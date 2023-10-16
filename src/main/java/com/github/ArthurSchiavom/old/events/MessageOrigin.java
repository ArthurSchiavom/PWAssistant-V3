package com.github.ArthurSchiavom.old.events;

/**
 * Represents a message type.
 */
public enum MessageOrigin {
	GUILD("server"), PRIVATE_CHANNEL("direct message channel");

	private String name;
	MessageOrigin(String name) {
		this.name = name;
	}

	/**
	 * @return A visual representation of this enum element.
	 */
	@Override
	public String toString() {
		return name;
	}
}
