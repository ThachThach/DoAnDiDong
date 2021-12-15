package com.example.doandidong.function.BepBar.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.example.doandidong.function.BepBar.Data.SanPham;
import java.util.List;

public class SanPhamDonHangAdapter extends RecyclerView.Adapter<SanPhamDonHangAdapter.ViewHolder>{

    private List<SanPham> sanPhams;
    private Context context;

    public SanPhamDonHangAdapter(Context context, List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mon_ban_offline,parent,false);
        return new SanPhamDonHangAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMon.setText(sanPhams.get(position).getNameProduct());
        holder.tvSoLuong.setText(sanPhams.get(position).getSoluong()+"");
        holder.yeuCau.setText(sanPhams.get(position).getYeuCau());
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMon;
        private TextView tvSoLuong;
        private TextView yeuCau;
        public ViewHolder(View itemView) {
            super(itemView);
            tvMon
                    =itemView.findViewById(R.id.Mon);
            tvSoLuong
                    =itemView.findViewById(R.id.soLuong);
            yeuCau
                    =itemView.findViewById(R.id.yeuCau);
        }
    }

}

