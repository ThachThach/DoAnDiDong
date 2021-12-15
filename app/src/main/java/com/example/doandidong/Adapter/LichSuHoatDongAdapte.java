package com.example.doandidong.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Model.LichSuHoatDong;
import com.example.doandidong.R;
import java.util.ArrayList;

public class LichSuHoatDongAdapte extends RecyclerView.Adapter<LichSuHoatDongAdapte.LichSuHoatDongHodle> {
    private ArrayList<LichSuHoatDong> list;

    public LichSuHoatDongAdapte(ArrayList<LichSuHoatDong> list) {
        this.list = list;
    }

    public void setData(ArrayList<LichSuHoatDong> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LichSuHoatDongHodle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su_hoat_dong,parent,false);
        LichSuHoatDongAdapte.LichSuHoatDongHodle productViewHolder = new LichSuHoatDongAdapte.LichSuHoatDongHodle(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuHoatDongHodle holder, int position) {
        holder.nhanVien.setText(list.get(position).getNhanVien());
        holder.thoigian.setText(list.get(position).getThoiGian());
        holder.mota.setText(list.get(position).getCongViec());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LichSuHoatDongHodle extends RecyclerView.ViewHolder{

        private TextView mota, thoigian, nhanVien;

        public LichSuHoatDongHodle(@NonNull View itemView) {
            super(itemView);
            mota =  itemView.findViewById(R.id.mota);
            thoigian = itemView.findViewById(R.id.thoigian);
            nhanVien = itemView.findViewById(R.id.nhanVien);
        }
    }
}
