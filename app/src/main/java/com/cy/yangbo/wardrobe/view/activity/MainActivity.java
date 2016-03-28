package com.cy.yangbo.wardrobe.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cy.yangbo.wardrobe.R;
import com.cy.yangbo.wardrobe.comm.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView mContentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mContentTV = (TextView) findViewById(R.id.tv_main);
        SpannableStringBuilder ssb = new SpannableStringBuilder("啊啊啊啊啊啊啊啊啊");
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)),
                1, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mContentTV.setText(ssb);
        mContentTV.setTextColor(getResources().getColor(android.R.color.black));
    }

    @Override
    protected void setListener() {
        RxView.clicks(fab).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                /*String fileName = new SimpleDateFormat("yyyy-mm-dd:HH:MM:SS").format(new Date()) + ".jpg";
                cameraProxy.getPhoto2AlbumCrop(AppConfig.PHOTO_DIRECTORY + "/" + fileName);*/

                startActivity(new Intent(context, AddCostumeActivity.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_season) {
            Intent intent = new Intent(context, SeasonActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_style) {

        } else if (id == R.id.menu_weather){
            Intent intent = new Intent(context, WeatherActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
