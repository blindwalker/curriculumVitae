package at.kropf.curriculumvitae;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cocosw.bottomsheet.BottomSheet;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PHONE = "069910522304";

    private MainActivity mContext;
    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mContext = this;

        setUpMenu();

        findViewById(R.id.imgContact).setOnClickListener(new View.OnClickListener() {
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
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        //resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.profile_pic);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_phone, "Home");
        itemLogout = new ResideMenuItem(this, R.drawable.icon_mail, "Logout");

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

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

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

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
