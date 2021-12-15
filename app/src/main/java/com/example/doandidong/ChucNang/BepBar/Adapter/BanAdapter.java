package com.example.doandidong.ChucNang.BepBar.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BepBar.Data.Mon;
import java.util.ArrayList;
import java.util.List;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    List<Mon> mList = new ArrayList<>();

    public void setmList(List<Mon> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_ban_offline, parent, false);
        return new BanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BanAdapter.ViewHolder holder, int position) {
        Mon mon = mList.get(position);
        if (mon == null) {
            return;
        }
        holder.tvMon.setText(mon.getNameProduct());
        holder.tvSoLuong.setText(mon.getSoluong() + "");
        holder.yeuCau.setText(mon.getYeuCau());


    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMon;
        private TextView tvSoLuong;
        private TextView yeuCau;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMon
                    = itemView.findViewById(R.id.Mon);
            tvSoLuong
                    = itemView.findViewById(R.id.soLuong);
            yeuCau
                    = itemView.findViewById(R.id.yeuCau);
        }
    }
}
