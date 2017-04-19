package com.pg.mgmt.security.spring;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import com.pg.mgmt.security.users.AppUser;
import com.pg.mgmt.security.users.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Check for GAE Google logged-in user(For Admin) and
 * if no Google account login found, check for Firebase Token in request header or in Cookie
 * if Firebase Authtoken header found, verify the token, Compare the Token
 * Created by Siva on 4/9/2017.
 */
public class GaeFirebaseAuthenticationFilter extends GenericFilterBean {
    public static final String X_FIREBASE_AUTHORIZATION_KEY = "X-firebase-Authorization";
    //    private static final String REGISTRATION_URL = "/register.htm";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> ads = new WebAuthenticationDetailsSource();
    private AuthenticationManager authenticationManager;
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    private boolean allowSessionCreation = true;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        PreAuthenticatedAuthenticationToken token = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //First check for Googke authenticated user(generally portal admin)
        User googleUser = UserServiceFactory.getUserService().getCurrentUser();
        if (authentication != null && googleUser != null
                && !loggedInUserMatchesGaeOrFirebaseUser(authentication, UserUtils.buildAppUserFromGaeUser(googleUser))) {
            // clear context if google user and user in context are different
            SecurityContextHolder.clearContext();
            authentication = null;
            ((HttpServletRequest) request).getSession().invalidate();
        }
        if (authentication == null && googleUser != null) {
            logger.debug("Currently logged on to GAE as user " + googleUser);
            logger.debug("Authenticating to Spring Security");
            // AppUser has returned after authenticating via GAE. Need to authenticate
            // through Spring Security.
            token = new GAEPreAuthenticatedAuthenticationToken(googleUser, null);
            token.setDetails(ads.buildDetails((HttpServletRequest) request));
        }
        //if google authenticated user not found, proceed to verify Firebase authenticated user and create firebase auth token
        String firebaseJWT = null;
        boolean firebaseAuth = false;
        if (token == null && googleUser == null) {
            AppUser firebaseUser = null;
            firebaseJWT = getFirebaseAuthTokenFromRequest((HttpServletRequest) request);
            FirebaseToken decodedToken = findFirebaseTokenValidateAndParse(firebaseJWT);
            if (decodedToken != null) {
                firebaseUser = UserUtils.buildUserFromFirebaseToken(decodedToken);
            }
            if (authentication != null && firebaseUser != null
                    && !loggedInUserMatchesGaeOrFirebaseUser(authentication, firebaseUser)) {
                // clear context if google user and user in context are different
                SecurityContextHolder.clearContext();
                authentication = null;
                ((HttpServletRequest) request).getSession().invalidate();
            }
            if (authentication == null && firebaseUser != null) {
                logger.debug("Currently logged on to Firebase as user {}", firebaseUser);
                logger.debug("Authenticating to Spring Security");
                // AppUser has returned after authenticating via GAE. Need to authenticate
                // through Spring Security.
                token = new FirebasePreAuthenticatedAuthenticationToken(firebaseUser, null);
                token.setDetails(ads.buildDetails((HttpServletRequest) request));
                firebaseAuth = true;
            }
        }
        if (token != null) {
            try {
                authentication = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //TODO research if storing token in session give any advantage
//                if (firebaseAuth) {
//                    Cookie userCookie = new Cookie(X_FIREBASE_AUTHORIZATION_KEY, "value");
//                    userCookie.setMaxAge(60 * 60 * 24 * 365); //Store cookie for 1 year
//                    ((HttpServletResponse) response).addCookie(userCookie);
//                }
            } catch (AuthenticationException e) {
                failureHandler.onAuthenticationFailure((HttpServletRequest) request,
                        (HttpServletResponse) response, e);
                //Authentication exception handler responded to client, stop filter chain as response already sent
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private FirebaseToken findFirebaseTokenValidateAndParse(String firebaseAuthToken) {
        FirebaseToken decodedToken = null;
        long start = System.nanoTime();
        if (StringUtils.isNotBlank(firebaseAuthToken)) {
            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(firebaseAuthToken);
            try {
                Tasks.await(authTask);
            } catch (ExecutionException | InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            decodedToken = authTask.getResult();
            long end = System.nanoTime();
            long elapsedTimeNanos = end - start;
            long microseconds = TimeUnit.MICROSECONDS.convert(elapsedTimeNanos, TimeUnit.NANOSECONDS);
            long millis = TimeUnit.MILLISECONDS.convert(elapsedTimeNanos, TimeUnit.NANOSECONDS);
            logger.info("Verifying Firebase JWT Token took {} nanos, {} micros, {} millis.", elapsedTimeNanos, microseconds, millis);
        }
        return decodedToken;
    }

    private String getFirebaseAuthTokenFromRequest(HttpServletRequest request) {
        String firebaseAuthToken = request.getHeader(X_FIREBASE_AUTHORIZATION_KEY);
        if (StringUtils.isBlank(firebaseAuthToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie ck : cookies) {
                    if (X_FIREBASE_AUTHORIZATION_KEY.equals(ck.getName())) {
                        firebaseAuthToken = ck.getValue();
                        break;
                    }
                }
            }
        }
        return firebaseAuthToken;
    }

    private boolean loggedInUserMatchesGaeOrFirebaseUser(Authentication authentication, AppUser user) {
        if (authentication != null || authentication.getPrincipal() != null || user != null) {
            // User has logged out of Firebase/GAE, but is still logged into application
            // or principal is not available in authentication object
            AppUser appUser = (AppUser) authentication.getPrincipal();
            if (StringUtils.equals(appUser.getUserId(), user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        Assert.notNull(authenticationManager, "AuthenticationManager must be set");
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            initFireBaseServiceAccountWithAppDefaultCreds();
        } else {
            initFireBaseServiceAccountWithCredFile();
        }
    }

    private void initFireBaseServiceAccountWithAppDefaultCreds() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.applicationDefault())
//                .setCredential(GoogleCredential.getApplicationDefault())
                .setDatabaseUrl("https://pg-mgmt.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
    }

    private void initFireBaseServiceAccountWithCredFile() {
        InputStream serviceAccount =
                this.getClass().getResourceAsStream("/pg-mgmt-firebase-adminsdk-drbnl-642ed69822.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://pg-mgmt.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public boolean isAllowSessionCreation() {
        return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }
}
