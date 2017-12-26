package com.example.user.infyemart;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.SectionsPagerAdapter;

public class NotificationActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar=findViewById(R.id.toolbar_alert);
        TextView textView=findViewById(R.id.toolbar_title);
        textView.setText("Notifications");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
