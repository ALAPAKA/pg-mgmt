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

/**
 *
 * Service used to maintain a list of users who are registered with the application.
 *
 * Created by Siva on 4/9/2017.
 */
public interface UserRegistry {

	AppUser findUser(String userId);

	void registerUser(AppUser newAppUser);

	void removeUser(String userId);
}
