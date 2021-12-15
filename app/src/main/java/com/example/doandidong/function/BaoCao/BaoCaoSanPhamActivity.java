package com.example.doandidong.function.BaoCao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.fragment.FragmentAdapter;

public class BaoCaoSanPhamActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 pager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_san_pham);

        tabLayout = findViewById(R.id.tablayout);
        pager = findViewById(R.id.viewpager);

//        DanhSachTopSanPhamFragment ds = DanhSachTopSanPhamFragment.newInstance("long1", "long2");

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);


        tabLayout.addTab(tabLayout.newTab().setText("Sản phẩm tuần"));
        tabLayout.addTab(tabLayout.newTab().setText("Sản phẩm tháng/năm"));

//        tabLayout.setTabIndicatorFullWidth(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}