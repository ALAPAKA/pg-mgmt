package com.pg.mgmt.security.users;

import com.google.appengine.api.users.User;
import com.google.firebase.auth.FirebaseToken;
import com.pg.mgmt.security.spring.AppRole;

/**
 * Created by Siva on 4/19/2017.
 */
public class UserUtils {

    public static AppUser buildAppUserFromGaeUser(User googleUser) {
        AppUser appUser = null;
        if (googleUser == null) {
            return null;
        }
        return new AppUser(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail());
    }

    public static AppUser buildUserFromFirebaseToken(FirebaseToken decodedToken) {
        AppUser appUser = new AppUser(decodedToken.getUid(), decodedToken.getName(), decodedToken.getEmail());
        if ("anonymous".equals(decodedToken.getClaims().get("provider_id"))) {
            appUser.getAuthorities().clear();
            appUser.getAuthorities().add(AppRole.ANONYMOUS);
        }
        return appUser;
    }
}
