package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.Main_RecyclerAdapter;
import com.example.user.infyemart.Adapter.Slider_Adapter;
import com.example.user.infyemart.Pojo.Pojo_Banner;
import com.example.user.infyemart.Pojo.Pojo_categories;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ItemOffsetDecoration;
import com.example.user.infyemart.Utils.RecyclerItemClickListener;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AbsListView.OnScrollListener {
    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
    TextView mainPageName,mainPageAddress;
    CardView cardrecentHead,cardMainHead;
    LinearLayout contentMain;
    String count;
    private ViewPager mPager;
    private static int currentPage=0;
    private ArrayList<Pojo_Banner> bannerImgsArray=new ArrayList<>();
    private ArrayList<String> banStrImgsArray=new ArrayList<>();
    private  ArrayList <Pojo_categories>categories_call=new ArrayList<>();
    ProgressDialog dialog;
    String category;
    Object[] objectList;
    String[] stringImgs;
    String cart_id,user_id;
    TextView toolbarTit,toolbarCount;
    String userAddressNav,userNameNav;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
         toolbarTit = findViewById(R.id.toolbar_title);
         toolbarTit.setVisibility(View.INVISIBLE);
         toolbarCount=findViewById(R.id.cartCountId);

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
            cart_id=prefs.getString("session_id","0");
            userNameNav=prefs.getString("user_name","0");
            userAddressNav=prefs.getString("addressString","0");
            user_id=prefs.getString("user_id","0");
            Log.e("loggg", "onCreate: user id "+user_id+"," +userNameNav+", "+userAddressNav );
        }

        MyASyncTask task=new MyASyncTask(MainActivity.this);
        task.execute();

        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
         ImageView mainCart=findViewById(R.id.mainToolbarCart);

         cardMainHead=findViewById(R.id.cardTopCat_main);
         cardrecentHead=findViewById(R.id.cardRecent_main);
         cardrecentHead.setVisibility(View.GONE);
         cardMainHead.setVisibility(View.GONE);
         contentMain=findViewById(R.id.contentMain);
        recycler=findViewById(R.id.categoryMainList);
        recycler.setHasFixedSize(true);
         layoutManager=new GridLayoutManager(MainActivity.this,2);
         ItemOffsetDecoration itemDecoration =new ItemOffsetDecoration(MainActivity.this,R.dimen.item_offset);
         recycler.addItemDecoration(itemDecoration);
        recycler.setLayoutManager(layoutManager);
        final Main_RecyclerAdapter adapter=new Main_RecyclerAdapter(MainActivity.this,categories_call);
        recycler.setAdapter(adapter);
        recycler.setFitsSystemWindows(true);
        recycler.addItemDecoration(new ItemOffsetDecoration(MainActivity.this, 20));

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, recycler,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int number= Integer.parseInt(categories_call.get(position).getId());
                Intent intent=new Intent(MainActivity.this,SubCategoey_Activity.class);
                intent.putExtra("position",number);
                intent.putExtra("subCategory",categories_call.get(position).getCategory());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));



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

        adapter.notifyDataSetChanged();


        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);

        mainPageAddress=headerView.findViewById(R.id.mainPageAddress);
        mainPageName=headerView.findViewById(R.id.mainPageName);
        mainPageName.setText(userNameNav);
        mainPageAddress.setText(userAddressNav);

        ImageView pencilEdt=headerView.findViewById(R.id.addressEditDrawPencil);
        pencilEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
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
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String status=jsonObject.getString("status");



                                    Pojo_Banner pojo=new Pojo_Banner();
                                    String ban1,ban2,ban3;
                                    ban1=jsonObject.getString("banner1");
                                    ban2=jsonObject.getString("banner2");
                                    ban3=jsonObject.getString("banner3");

                                    pojo.setBaner1(ban1);
                                    pojo.setBaner2(ban2);
                                    pojo.setBaner3(ban3);

                                    banStrImgsArray.add(ban1);
                                banStrImgsArray.add(ban2);
                                banStrImgsArray.add(ban3);

                                    bannerImgsArray.add(pojo);
                                objectList = banStrImgsArray.toArray();
                                     stringImgs=  Arrays.copyOf(objectList,objectList.length,String[].class);

//                                mPager=findViewById(R.id.pager);
//                                mPager.setAdapter(new Slider_Adapter(MainActivity.this,bannerImgsArray,stringImgs));

                                initSlide();

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
        new RetrofitHelper(MainActivity.this).getApIs().categoryMain("category",cart_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {

                            JSONObject object=new JSONObject(response.body().toString());


                            JSONArray  jsonArray=object.getJSONArray("category");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String status=jsonObject.getString("status");
                                String id=jsonObject.getString("id");
                                 category=jsonObject.getString("category");
                                String department=jsonObject.getString("department");
                                String icons=jsonObject.getString("image");
                                Pojo_categories pojo=new Pojo_categories();
                                pojo.setId(id);
                                pojo.setDepartment(department);
                                pojo.setIcon(icons);
                                pojo.setCategory(category);
                                categories_call.add(pojo);
                            }
                             count=object.getString("count");
                            Log.e("loggg", "onResponse: "+count );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        cardMainHead.setVisibility(View.VISIBLE);
                        cardrecentHead.setVisibility(View.VISIBLE);
                        if (count.equals("null")){
                            count="0";
                            toolbarCount.setVisibility(View.GONE);
                        }else if (count.equals("0")){
                            toolbarCount.setVisibility(View.GONE);
                        }else {
                            toolbarCount.setText(count);
                            toolbarCount.setVisibility(View.VISIBLE);
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                    }
                });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            System.exit(0);
            finish();
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

         if (id == R.id.orders_draw) {
            Intent intent=new Intent(MainActivity.this,MyOrderList_Activity.class);
            startActivity(intent);
        } else if (id == R.id.notification_draw) {
             Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
             startActivity(intent);

        } else if (id == R.id.locate_draw) {
             Intent intent=new Intent(MainActivity.this,AddressFieldsActivity.class);
             startActivity(intent);

        }
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initSlide() {
        for (int i=0;i<stringImgs.length;i++);

        CircleIndicator indicator=findViewById(R.id.indicator);
        mPager=findViewById(R.id.pager);
        mPager.setAdapter(new Slider_Adapter(MainActivity.this,bannerImgsArray,stringImgs));
        indicator.setViewPager(mPager);

        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (currentPage==stringImgs.length){
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
    private class MyASyncTask extends AsyncTask<Void,Void,Void>{

        private ProgressDialog dialog;
        public MyASyncTask(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            banner();
            categories();
            return null;
            
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
