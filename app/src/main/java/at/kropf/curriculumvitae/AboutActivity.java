package at.kropf.curriculumvitae;

import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

import at.kropf.curriculumvitae.fragment.AboutFragment;

/*
 * Activity for displaying the viewpager holding the AboutFragments
 */
public class AboutActivity extends AppIntro {

    @Override
    public void init(Bundle bundle) {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        addSlide(AboutFragment.newInstance(1));
        addSlide(AboutFragment.newInstance(2));
        addSlide(AboutFragment.newInstance(3));
        //addSlide(AboutFragment.newInstance(5));
        addSlide(AboutFragment.newInstance(4));
        addSlide(AboutFragment.newInstance(6));

        setBarColor(getResources().getColor(R.color.white));
        setBarColor(getResources().getColor(R.color.colorPrimaryDark));

        // Hide Skip/Done button
        showSkipButton(false);
        showDoneButton(false);
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onDonePressed() {

    }
}
