package com.example.doandidong.function.DonHangOnline.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter implements SwipeRefreshLayout.OnRefreshListener{
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChoXacNhanFragment();
            case 1:
                return new DaXacNhanFragment();
            case 2:
                return new DangXuLyOnlineFragment();
            case 3:
                return new DangGiaoHangOnlineFragment();
            case 4:
                return new DonHoanThanhFragment();
            case 5:
                return new DonHuyFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onRefresh() {

    }
}
