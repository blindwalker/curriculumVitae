package at.kropf.curriculumvitae;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.viewpagerindicator.TitlePageIndicator;

import at.kropf.curriculumvitae.fragment.AndroidSkillFragment;
import at.kropf.curriculumvitae.fragment.InterestsFragment;
import at.kropf.curriculumvitae.fragment.OtherSkillFragment;

/*
 * Acitivity holds a viewpager
 * This viewpager holds the skills-fragments
 */
public class SkillsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        PagerAdapter mFragmentAdapter = new PagerAdapter(getSupportFragmentManager(), this);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.vpPager);
        mViewPager.setAdapter(mFragmentAdapter);

        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(mViewPager);
    }

    //PagerAdapter holds the actual fragments
    public static class PagerAdapter extends FragmentPagerAdapter {
        private final static int NUM_ITEMS = 3;
        private static Context mContext;

        public PagerAdapter(FragmentManager fragmentManager, Context context) {
            super(fragmentManager);
            mContext = context;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AndroidSkillFragment.newInstance();
                case 1:
                    return OtherSkillFragment.newInstance();
                case 2:
                    return InterestsFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getResources().getString(R.string.androidSkills);
                case 1:
                    return mContext.getResources().getString(R.string.otherSkills);
                case 2:
                    return mContext.getResources().getString(R.string.interests);
                default:
                    return "";
            }
        }
    }
}