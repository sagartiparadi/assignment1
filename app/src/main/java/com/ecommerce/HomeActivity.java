package com.ecommerce;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;

import com.ecommerce.config.BaseConfig;
import com.ecommerce.fragment.HomeFragment;
import com.ecommerce.helper.TypefaceSpan;

import yalantis.com.sidemenu.util.ViewAnimator;


public class HomeActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private static HomeFragment homeFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeObject();

    }

    private void initializeObject() {
        homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });




        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, homeFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(HomeFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(HomeFragment.PROFILE, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(HomeFragment.ORDER, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(HomeFragment.ADDRESS, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(HomeFragment.POLICY, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(HomeFragment.CONTACTUS, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(HomeFragment.LOGOUT, R.drawable.icn_6);
        list.add(menuItem6);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SpannableString s = new SpannableString("Venky's Nutrition");
        s.setSpan(new TypefaceSpan(this, BaseConfig.BOLD_FONT), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        getSupportActionBar().setTitle(s);
//        getSupportActionBar().setIcon(android.R.drawable.ic_menu_sort_by_size);
//        toolbar.setTitleTextColor(Color.WHITE);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private ScreenShotable replaceFragment(String position) {
        if(position.equals("Profile")) {
            startActivity(new Intent(this,ProfileActivity.class));
        }else if(position.equals("Order")){
            startActivity(new Intent(this,OrderActivity.class));
        }else if(position.equals("Address")){
            startActivity(new Intent(this,AddressActivity.class));
        }else if(position.equals("Policy")){
            startActivity(new Intent(this,PolicyActivity.class));
        }else if(position.equals("ContactUs")){
            startActivity(new Intent(this,ContactUs.class));
        }else if(position.equals("Logout")){

        }
        return homeFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case HomeFragment.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem.getName());
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public void onClick(View view) {
        Log.e("Testing id",view.getId()+"");
    }
}
