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

public class MainActivity extends AppCompatActivity {

    private static final String PHONE = "069910522304";

    //Navigation Drawer
    private ResideMenu resideMenu;
    private ResideMenuItem itemLogout;

    //Icons
    private ImageView imgContact;
    private ImageView imgWork;
    private ImageView imgEdu;
    private ImageView imgSkills;
    private ImageView imgAbout;
    private ImageView imgMe;

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

    private void setupLayout() {

        imgWork = (ImageView) findViewById(R.id.imgWork);
        imgContact = (ImageView) findViewById(R.id.imgContact);
        imgEdu = (ImageView) findViewById(R.id.imgEdu);
        imgAbout = (ImageView) findViewById(R.id.imgAbout);
        imgSkills = (ImageView) findViewById(R.id.imgSkills);
        imgMe = (ImageView) findViewById(R.id.imgAvatar);

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
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.drawer_bg);
        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemLogout = new ResideMenuItem(this, "Logout");

        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurriculumVitaeApplication.getInstance().logoutUser();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });

        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);

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

    private void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + PHONE));
        startActivity(callIntent);
    }

    private void mail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "martinkropf08@gmail.com", null));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void linkedIn() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/AAMAABjjhdYBxFepxIjsYPBTDoeiSYRQ6HoLvEA"));
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=AAMAABjjhdYBxFepxIjsYPBTDoeiSYRQ6HoLvEA"));
        }
        startActivity(intent);
    }


}
