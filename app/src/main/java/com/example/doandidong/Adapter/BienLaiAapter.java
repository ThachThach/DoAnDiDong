package com.example.doandidong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BaoCao.model.SanPham;
import java.util.ArrayList;

public class BienLaiAapter extends RecyclerView.Adapter<BienLaiAapter.BienLaiHodel>{

    private Context context;
    private ArrayList<SanPham> sanPhams;

    public BienLaiAapter(Context context, ArrayList<SanPham> sanPhams) {
        this.context = context;
        this.sanPhams = sanPhams;
    }

    @NonNull
    @Override
    public BienLaiHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BienLaiAapter.BienLaiHodel(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bien_lai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BienLaiHodel holder, int position) {
        holder.tv_soluong.setText(sanPhams.get(position).getSoluong()+"");
        holder.tv_tensanpham.setText(sanPhams.get(position).getNameProduct());
        holder.tv_giatien.setText("" + (sanPhams.get(position).getGiaProudct() * sanPhams.get(position).getSoluong()));
    }

    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class BienLaiHodel extends RecyclerView.ViewHolder {
        private TextView tv_giatien, tv_tensanpham, tv_soluong;
        public BienLaiHodel(@NonNull View ItemView) {
            super(ItemView);
            tv_giatien = ItemView.findViewById(R.id.tv_giatien);
            tv_tensanpham = ItemView.findViewById(R.id.tv_tensanpham);
            tv_soluong = ItemView.findViewById(R.id.tv_tensanpham);
        }
    }
}
