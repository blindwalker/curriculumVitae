package at.kropf.curriculumvitae.application;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences to store values
 */
public class PreferenceHandler {

    private static final String USERNAME = "username";
    private static final String APP_PREFIX = "currVit_shared_pref";
    private static final String SESSION_TOKEN = "session_token";
    private static final String SESSION_EXPIRATION = "session_expires";
    private static final String NAME = "name";
    private static final String USER_IMAGE = "userimage";


    private SharedPreferences globalPublicPreferences;
    private Context context;

    public PreferenceHandler(Context context) {
        this.context = context;
    }

    public void setUserName(String username) {
        getGlobalPublicPreferences().edit().putString(USERNAME, username).commit();
    }

    public String getUserName() {
        return getGlobalPublicPreferences().getString(USERNAME, "");
    }

    public void setSessionToken(String sessionToken) {
        getGlobalPublicPreferences().edit().putString(SESSION_TOKEN, sessionToken).commit();
    }

    public String getSessionToken() {
        return getGlobalPublicPreferences().getString(SESSION_TOKEN, "");
    }

    public void setSessionExpires(int sessionExpires) {
        getGlobalPublicPreferences().edit().putInt(SESSION_EXPIRATION, sessionExpires).commit();
    }

    public int getSessionExpires() {
        return getGlobalPublicPreferences().getInt(SESSION_EXPIRATION, 0);
    }

    public void setName(String name) {
        getGlobalPublicPreferences().edit().putString(NAME, name).commit();
    }

    public String getName() {
        return getGlobalPublicPreferences().getString(NAME, "");
    }

    public void setUserImage(String userImage) {
        getGlobalPublicPreferences().edit().putString(USER_IMAGE, userImage).commit();
    }

    public String getUserImage() {
        return getGlobalPublicPreferences().getString(USER_IMAGE, "");
    }


    private SharedPreferences getGlobalPublicPreferences() {
        if (globalPublicPreferences == null) {
            globalPublicPreferences = context.getSharedPreferences(APP_PREFIX, Context.MODE_PRIVATE);
        }

        return globalPublicPreferences;
    }

}
