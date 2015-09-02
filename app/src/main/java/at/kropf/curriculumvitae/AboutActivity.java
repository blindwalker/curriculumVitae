package at.kropf.curriculumvitae;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.paolorotolo.appintro.AppIntro;

import at.kropf.curriculumvitae.fragment.About1Fragment;
import at.kropf.curriculumvitae.fragment.About2Fragment;
import at.kropf.curriculumvitae.fragment.About3Fragment;
import at.kropf.curriculumvitae.fragment.About4Fragment;
import at.kropf.curriculumvitae.fragment.About5Fragment;
import at.kropf.curriculumvitae.fragment.AndroidSkillFragment;

public class AboutActivity extends AppIntro {


    @Override
    public void init(Bundle bundle) {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        addSlide(new About1Fragment());
        addSlide(new About2Fragment());
        addSlide(new About3Fragment());
        addSlide(new About5Fragment());
        addSlide(new About4Fragment());

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
