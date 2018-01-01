package com.example.user.infyemart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.Main_RecyclerAdapter;
import com.example.user.infyemart.Adapter.Slider_Adapter;
import com.example.user.infyemart.Pojo.Pojo_Banner;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ItemOffsetDecoration;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AbsListView.OnScrollListener {
    RecyclerView gridView,recyclerView2;
    boolean isLastPage=false;
    private int visibleThreshold = 5;
    private int previousTotal = 0;
    private boolean loading = true;
    RecyclerView.LayoutManager layoutManager;
    String[] Category = {
            "Groceries Staples",
            "Spices Pickles",
            "House Holds",
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
            R.drawable.groceries,
            R.drawable.spices,
            R.drawable.household,
            R.drawable.beauty,
            R.drawable.toysnbaby,
            R.drawable.fruitsnveg,
            R.drawable.meat,
            R.drawable.laptopnmobiles,
            R.drawable.homeappliances,
            R.drawable.kitchenappliances,
            R.drawable.leathershoes,
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
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setVisibility(View.GONE);
        initSlide();

        categories();

//        banner();

        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
         ImageView mainCart=findViewById(R.id.mainToolbarCart);

        gridView=findViewById(R.id.categoryMainList);

        gridView.setHasFixedSize(true);
         layoutManager=new LinearLayoutManager(this);
        gridView.setLayoutManager(layoutManager);
        final Main_RecyclerAdapter adapter=new Main_RecyclerAdapter(MainActivity.this,Category,CategoryImgs);
        gridView.setAdapter(adapter);
        gridView.setFitsSystemWindows(true);
        gridView.addItemDecoration(new ItemOffsetDecoration(20));

        mainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        mainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);

            }
        });




        gridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        adapter.notifyDataSetChanged();


        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);


        ImageView pencilEdt=headerView.findViewById(R.id.addressEditDrawPencil);
        pencilEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddressFieldsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void banner() {

        new RetrofitHelper(MainActivity.this).getApIs().bannerMain("banner")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status=jsonObject.getString("status");
                            JSONArray  jsonArray=jsonObject.getJSONArray("banner");
                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                Pojo_Banner pojo=new Pojo_Banner();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    private void categories() {
        new RetrofitHelper(MainActivity.this).getApIs().categoryMain("category")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray  jsonArray=new JSONArray(response.body().toString());

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);


                                String status=jsonObject.getString("status");
                                String id=jsonObject.getString("id");
                                String department=jsonObject.getString("department");
                                String icons=jsonObject.getString("icons");

                            }


                        } catch (JSONException e) {
                            Log.i("TAG_e", e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
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
//        MenuItem item=menu.findItem(R.id.action_cart);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

         if (id == R.id.orders_draw) {
            Intent intent=new Intent(MainActivity.this,MyOrdersActivity.class);
            startActivity(intent);
        } else if (id == R.id.notification_draw) {
             Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
             startActivity(intent);

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}
