package doit.study.droid.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import doit.study.droid.R;

public class DrawerBaseActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    // Shia LaBeouf - Just Do it! (Auto-tuned)
    private final String URL = "http://www.youtube.com/watch?v=gJscrxxl_Bg";
    protected FrameLayout mFrameLayout;
    private static String version;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private String getVersion() {
        if (version != null)
            return version;

        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "buggy";
        }
        return version;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        mFrameLayout = (FrameLayout) findViewById(R.id.flContent);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView nvView = (NavigationView) findViewById(R.id.nvView);
        View header = nvView.getHeaderView(0);
        TextView tv = (TextView) header.findViewById(R.id.version_num_header);
        tv.setText(getVersion());
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(mActionBarDrawerToggle);
        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent = null;
                switch(menuItem.getItemId()) {
                    case R.id.nav_first_fragment:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(URL));
                        startActivity(intent);
                        break;
                    case R.id.nav_second_fragment:
                        intent = new Intent(DrawerBaseActivity.this, TopicsChooserActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_third_fragment:
                        intent = new Intent(DrawerBaseActivity.this, InterrogatorActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawer.openDrawer(GravityCompat.START);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
