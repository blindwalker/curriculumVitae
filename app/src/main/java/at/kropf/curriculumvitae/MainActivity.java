package at.kropf.curriculumvitae;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.cocosw.bottomsheet.BottomSheet;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.io.File;
import java.util.List;

import at.kropf.curriculumvitae.application.CurriculumVitaeApplication;
import at.kropf.curriculumvitae.augmented.SampleCamActivity;

/*
 * Home Screen Activity
 * Holds all the main navigation points as well as the navigation drawer
 */

public class MainActivity extends AppCompatActivity {

    private static final String PHONE = "069910522304";

    //Navigation Drawer
    private ResideMenu resideMenu;

    private ImageView imgWork;
    private ImageView imgEdu;
    private ImageView imgSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setUpMenu();

        setupLayout();

    }

    /*
     *  Create view references and set click listeners
     */
    private void setupLayout() {

        imgWork = (ImageView) findViewById(R.id.imgWork);
        ImageView imgContact = (ImageView) findViewById(R.id.imgContact);
        imgEdu = (ImageView) findViewById(R.id.imgEdu);
        ImageView imgAbout = (ImageView) findViewById(R.id.imgAbout);
        imgSkills = (ImageView) findViewById(R.id.imgSkills);
        ImageView imgMe = (ImageView) findViewById(R.id.imgAvatar);

        imgWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WorkActivity.class);

                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View sharedView = imgWork;
                    String transitionName = getString(R.string.work);

                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                    startActivity(i, transitionActivityOptions.toBundle());
                } else {
                    startActivity(i);
                }
            }
        });

        imgSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SkillsActivity.class);

                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View sharedView = imgSkills;
                    String transitionName = getString(R.string.skills);
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                    startActivity(i, transitionActivityOptions.toBundle());
                } else {
                    startActivity(i);
                }
            }
        });

        imgEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EduActivity.class);

                // Check if we're running on Android 5.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View sharedView = imgEdu;
                    String transitionName = getString(R.string.edu);
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                    startActivity(i, transitionActivityOptions.toBundle());
                } else {
                    startActivity(i);
                }
            }
        });

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(MainActivity.this, R.style.BottomSheet_StyleDialog).sheet(R.menu.list).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.call:
                                call();
                                break;
                            case R.id.mail:
                                mail();
                                break;
                            case R.id.linkedIn:
                                linkedIn();
                                break;
                            case R.id.github:
                                github();
                                break;
                        }
                    }
                }).show();
            }
        });

        imgAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));

            }
        });

        /*
        imgMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SampleCamActivity.class);
                intent.putExtra(SampleCamActivity.EXTRAS_KEY_ACTIVITY_TITLE_STRING,
                        "1.3 Interactivity");
                intent.putExtra(SampleCamActivity.EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL, "samples"
                        + File.separator + "1_Client$Recognition_3_Interactivity"
                        + File.separator + "index.html");
                intent.putExtra(SampleCamActivity.EXTRAS_KEY_ACTIVITY_IR,
                        true);
                intent.putExtra(SampleCamActivity.EXTRAS_KEY_ACTIVITY_GEO,
                        false);
                startActivity(intent);
            }
        });
        */
    }

    /*
     *  Setup navigation drawer
     */
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.drawer_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        ResideMenuItem itemLogout = new ResideMenuItem(this, getString(R.string.Logout));

        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurriculumVitaeApplication.getInstance().logoutUser();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });

        //add menu items
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);

        //disable swipe from right
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.imgAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);

        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //start intent for calling
    private void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + PHONE));
        startActivity(callIntent);
    }

    //start intent for email
    private void mail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "martinkropf08@gmail.com", null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    //start intent with linkedin url
    private void linkedIn() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/AAMAABjjhdYBxFepxIjsYPBTDoeiSYRQ6HoLvEA"));
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=AAMAABjjhdYBxFepxIjsYPBTDoeiSYRQ6HoLvEA"));
        }
        startActivity(intent);
    }

    //start intent with github url
    private void github() {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/blindwalker/"));
        startActivity(i);
    }


}
