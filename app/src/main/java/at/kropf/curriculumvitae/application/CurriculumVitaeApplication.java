package at.kropf.curriculumvitae.application;

import android.app.Application;

/**
 * Created by martinkropf on 23.08.15.
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


}
