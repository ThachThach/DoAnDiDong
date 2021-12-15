package com.example.doandidong.ChucNang.CuaHangOnline.Fragment;

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
                return new AddQuanCaoFragment();
            case 1:
                return new SanPhamQuangCaoFragment();
            case 2:
                return new AddQuanCaoFragment();
            case 3:
                return new AddQuanCaoFragment();
            case 4:
                return new AddQuanCaoFragment();
            case 5:
                return new AddQuanCaoFragment();
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
