package at.kropf.curriculumvitae.application;

import android.app.Application;

/**
 * Application Class holding single instance for PreferenceHandler
 * Also contains user-state check and logout method
 */
public class CurriculumVitaeApplication extends Application {

    private static CurriculumVitaeApplication curriculumVitaeApplication;

    private static PreferenceHandler preferenceHandler;

    public static CurriculumVitaeApplication getInstance() {
        if (curriculumVitaeApplication == null) {
            curriculumVitaeApplication = new CurriculumVitaeApplication();

        }

        return curriculumVitaeApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        if (preferenceHandler == null) {
            preferenceHandler = new PreferenceHandler(this);

        }
    }

    public PreferenceHandler getPreferenceHandler() {
        return preferenceHandler;
    }

    //checks for filled values in Preference handler to see if user is logged in
    public boolean isLoggedIn(){
        if(getPreferenceHandler().getSessionToken().length()==0){
            return false;

        }else if(getPreferenceHandler().getName().length()==0){
            return false;

        }else if(getPreferenceHandler().getUserName().length()==0){
            return false;

        }else if(getPreferenceHandler().getSessionExpires()==0){
            return false;

        }
        return true;
    }

    //remove all user values from PreferenceHandler
    public void logoutUser() {
        getPreferenceHandler().setSessionToken("");
        getPreferenceHandler().setUserName("");
        getPreferenceHandler().setSessionExpires(0);
        getPreferenceHandler().setName("");
        getPreferenceHandler().setUserImage("");

    }

}
