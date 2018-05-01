package com.example.arife.a2018_hw2_ceng427;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setupViewerPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewerPager(ViewPager viewPager){
        PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FoodListFragment(),"Food List");
        pagerAdapter.addFragment(new AnnouncementFragment(),"Announcment");
        pagerAdapter.addFragment(new NewsFragment(),"News");
        viewPager.setAdapter(pagerAdapter);
   }

}
