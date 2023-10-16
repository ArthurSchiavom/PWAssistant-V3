package com.github.ArthurSchiavom.old.information.users;

import java.util.HashMap;

public class CacheUserRegister {

	private static CacheUserRegister instance;
	private CacheUserRegister() {}
	public static CacheUserRegister getInstance() {
		return instance;
	}

	/**
	 * Initializes this table.
	 *
	 * @return The table instance.
	 */
	public static CacheUserRegister initialize() {
		if (instance == null) {
			instance = new CacheUserRegister();
		}
		return instance;
	}

	private HashMap<Long, CacheUser> users = new HashMap<>();

	/**
	 *
	 *
	 * @param userId The ID of the user to retrieve.
	 * @param addToDatabase If the user is not registered yet, if it should be registered on the com.github.ArthurSchiavom.old.database.
	 * @return The user of given ID.
	 */
	public CacheUser getUser(Long userId, boolean addToDatabase) {
		CacheUser cacheUser = users.get(userId);
		if (cacheUser == null) {
			cacheUser = registerUser(userId, addToDatabase);
		}
		return cacheUser;
	}

	private CacheUser registerUser(long userId, boolean addToDatabase) {
		CacheUser cacheUser = new CacheUser(userId);
		users.put(userId, cacheUser);
		if (addToDatabase)
			throw new UnsupportedOperationException();
		return cacheUser;
	}
}
