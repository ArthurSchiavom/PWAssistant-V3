package com.github.ArthurSchiavom.old.information.users;

import java.util.ArrayList;
import java.util.List;

public class CacheUser {
	private final long id;
	private List<Warning> warnings = new ArrayList<>();

	public CacheUser(long id) {
		this.id = id;
	}

	public boolean isThisUser(long id) {
		return this.id == id;
	}

	public long getId() {
		return id;
	}

	public List<Warning> getWarnings() {
		return new ArrayList<>(warnings);
	}

	public void addWarning(Warning warning, boolean addToDatabase) {
		warnings.add(new Warning(warning));
		if (addToDatabase) throw new UnsupportedOperationException();
			//TODO - add tO DATABASE
	}
}
