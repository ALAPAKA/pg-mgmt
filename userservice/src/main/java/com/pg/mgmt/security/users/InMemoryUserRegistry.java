/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pg.mgmt.security.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Siva on 4/9/2017.
 */
public class InMemoryUserRegistry implements UserRegistry {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, AppUser> users = Collections
			.synchronizedMap(new HashMap<String, AppUser>());

	public AppUser findUser(String userId) {
		return users.get(userId);
	}

	public void registerUser(AppUser newAppUser) {
		logger.debug("Attempting to create new user " + newAppUser);

		Assert.isTrue(!users.containsKey(newAppUser.getUserId()), "user should not exist");

		users.put(newAppUser.getUserId(), newAppUser);
	}

	public void removeUser(String userId) {
		users.remove(userId);
	}
}
