package com.example.doandidong.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.ChiTietNhapKho;
import com.example.doandidong.R;
import java.util.List;

public class ChiTietNhapKhoAdapter extends RecyclerView.Adapter<CustomChiTietNhapKho> {

    private Context context;
    private List<ChiTietNhapKho> list;
    public static final String KEY_BAOCAO = "BAOCAO";

    public ChiTietNhapKhoAdapter(Context context, List<ChiTietNhapKho> list) {
        this.context = context;
        this.list = list;
        Log.d("triet", "onBindViewHolder: "+list.size());
    }

    public ChiTietNhapKhoAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CustomChiTietNhapKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomChiTietNhapKho(LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_cap_nhat_kho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiTietNhapKho holder, int position) {
        ChiTietNhapKho chiTietNhapKho=list.get(position);
        holder.tenSanPham.setText(chiTietNhapKho.getTenSanPham());
        holder.soLieuCu.setText(chiTietNhapKho.getSoLieuCu());
        holder.soLieuMoi.setText(chiTietNhapKho.getSoLieuMoi());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(chiTietNhapKho);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(list.size()==0){
            return 0;
        }else {
            return list.size();
        }
    }
}
