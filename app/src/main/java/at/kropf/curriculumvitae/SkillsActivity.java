package at.kropf.curriculumvitae;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import at.kropf.curriculumvitae.fragment.AndroidSkillFragment;
import at.kropf.curriculumvitae.fragment.InterestsFragment;
import at.kropf.curriculumvitae.fragment.OtherSkillFragment;

public class SkillsActivity extends AppCompatActivity {

    PagerAdapter mFragmentAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        mFragmentAdapter = new PagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        mViewPager.setAdapter(mFragmentAdapter);
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
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
                case 0: // Fragment # 0 - This will show FirstFragment
                    return AndroidSkillFragment.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return OtherSkillFragment.newInstance();
                case 2: // Fragment # 0 - This will show FirstFragment different title
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