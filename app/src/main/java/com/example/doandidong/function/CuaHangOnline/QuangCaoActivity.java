package com.example.doandidong.function.CuaHangOnline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Fragment.FragmentAdapter;
import com.example.doandidong.function.DonHangOnline.adapter.TablayoutAdapter;
import com.example.doandidong.function.KhuyenMai.ListKhuyenMai;
import java.util.ArrayList;

public class QuangCaoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RecyclerView recylerView;
    private ViewPager2 pager;
    private TablayoutAdapter tablayoutAdapter;


    private FragmentAdapter adapter;
    private ArrayList<String> title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quang_cao);
        IDLayout();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.quangcao);

        title = new ArrayList<String>();
        title.add("Tạo quảng cáo");
        title.add("Mặt hàng quảng cáo");

        recylerView = findViewById(R.id.recylerView);
        pager = findViewById(R.id.viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        displayItem();
    }

    private void IDLayout () {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
    }

    private void displayItem(){
        recylerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tablayoutAdapter = new TablayoutAdapter(this, title, pager);
        recylerView.setAdapter(tablayoutAdapter);
        recylerView.setLayoutManager(layoutManager);

        tablayoutAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sanpham:
                intent = new Intent(this, TaoSanPhamOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quangcao:
                break;
            case R.id.thongtin:
                intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.giolamviec:
                intent = new Intent(this, ThoiGianLamViecOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.vanchuyen:
                intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.khuyenmai:
                intent = new Intent(this, ListKhuyenMai.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}