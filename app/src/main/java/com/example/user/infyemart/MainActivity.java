package com.example.user.infyemart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.user.infyemart.Adapter.Grid_MainAdapter;
import com.example.user.infyemart.Adapter.Slider_Adapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridView;
    String[] Category = {
            "Groceries Staples",
            "Spices Pickles",
            "House Holds",
            "Instant Foods Drinks",
            "Beauty Health",
            "Toys Baby Care",
            "Vegetables Fruits",
            "Fresh Meat Fish",
            "Mobiles Laptops",
            "Home Appliances",
            "Kitchen Appliances",
            "Leather Trends",
    } ;
    int[] CategoryImgs = {
            R.drawable.c_grocery,
            R.drawable.c_spices,
            R.drawable.c_households,
            R.drawable.c_instantfood,
            R.drawable.c_beauty,
            R.drawable.c_toys,
            R.drawable.c_vegitable,
            R.drawable.c_meat,
            R.drawable.c_mobiles,
            R.drawable.c_homeapplia,
            R.drawable.c_kitchenappl,
            R.drawable.c_leather,
    };
    private ViewPager mPager;
    private static int currentPage=0;
    private static final Integer[] imgs={R.drawable.banner_a,R.drawable.banner_c,R.drawable.banner_b};
    private ArrayList<Integer> imgsArray=new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        initSlide();

        Grid_MainAdapter adapter=new Grid_MainAdapter(MainActivity.this,Category,CategoryImgs);
        gridView=findViewById(R.id.gridViewMain);
        gridView.setAdapter(adapter);
        gridView.setFitsSystemWindows(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, Category[+position], Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_cart);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId()==R.id.action_cart){
            Intent intent=new Intent(MainActivity.this,CartActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.actionb_account){
            Intent intent=new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dr_groceries) {
            Intent intent=new Intent(MainActivity.this,MainProductsActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initSlide() {
        for (int i=0;i<imgs.length;i++)
            imgsArray.add(imgs[i]);

        mPager=findViewById(R.id.pager);
        mPager.setAdapter(new Slider_Adapter(MainActivity.this,imgsArray));
        CircleIndicator indicator=findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (currentPage==imgs.length){
                    currentPage=0;
                }
                mPager.setCurrentItem(currentPage++,true);
            }
        };
        Timer swipeTimer=new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },2500,2000);
    }
}
