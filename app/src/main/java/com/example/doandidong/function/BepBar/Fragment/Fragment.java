package com.example.doandidong.function.BepBar.Fragment;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.doandidong.function.BepBar.BepActivity;

public class Fragment extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    private BepActivity bepActivity;

    public Fragment(Context context, FragmentManager fm, int totalTabs, BepActivity
            bepActivity) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.bepActivity = bepActivity;
    }

    // this is for fragment tabs
    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                DonHangOfflineFragment donOffline = new DonHangOfflineFragment();
                return donOffline;
            case 1:
                DonHangOfflineChoSuLyFragment tableFragment3 = new DonHangOfflineChoSuLyFragment();
                return tableFragment3;
            case 2:
                DonHangOfflineHoanThanhFragment donOnline = new DonHangOfflineHoanThanhFragment();
                return donOnline;
            case 3:
                DonHangOnlineDangChoSuLiFragment tableFragment4 = new DonHangOnlineDangChoSuLiFragment();
                return tableFragment4;
            case 4:
                DonHangOnlineDangDangSuLiFragment tableFragment5 = new DonHangOnlineDangDangSuLiFragment();
                return tableFragment5;
            case 5:
                DonHangOnlineDangHoanThanhFragment tableFragment6 = new DonHangOnlineDangHoanThanhFragment();
                return tableFragment6;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
