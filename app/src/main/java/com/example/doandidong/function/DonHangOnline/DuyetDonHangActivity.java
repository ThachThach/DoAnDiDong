package com.example.doandidong.function.DonHangOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doandidong.R;
import com.example.doandidong.function.BepBar.BepActivity;
import com.example.doandidong.function.DonHangOnline.adapter.TablayoutAdapter;
import com.example.doandidong.function.DonHangOnline.fragment.FragmentAdapter;
import java.util.ArrayList;

public class DuyetDonHangActivity extends AppCompatActivity {

    private TablayoutAdapter tablayoutAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> title;
    private ViewPager2 pager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_don_hang);

        title = new ArrayList<String>();
        title.add("Chờ xác nhận");
        title.add("Đã xác nhận");
        title.add("Đang thực hiện");
        title.add("Đơn đang giao");
        title.add("Đơn hoàn thành");
        title.add("Đơn hủy");

        recyclerView = findViewById(R.id.recylerView);
        pager = findViewById(R.id.viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        displayItem();
    }

    private void displayItem(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tablayoutAdapter = new TablayoutAdapter(this, title, pager);
        recyclerView.setAdapter(tablayoutAdapter);
        recyclerView.setLayoutManager(layoutManager);

        tablayoutAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_donhang_online, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_donhang_online:
                Intent intent = new Intent(DuyetDonHangActivity.this, BepActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


}