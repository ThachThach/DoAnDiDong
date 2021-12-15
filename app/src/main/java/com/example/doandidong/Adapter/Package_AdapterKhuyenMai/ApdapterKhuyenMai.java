package com.example.doandidong.Adapter.Package_AdapterKhuyenMai;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.KhuyenMai.KhuyenMai;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.KhuyenMai.ListKhuyenMai;
import java.util.ArrayList;


public class ApdapterKhuyenMai extends RecyclerView.Adapter<ApdapterKhuyenMai.AdapterKhuyenMaiHolder> {
    ArrayList<KhuyenMai> khuyenMais;
    ListKhuyenMai context;




    public ApdapterKhuyenMai(ListKhuyenMai context, ArrayList<KhuyenMai> khuyenMais){
        this.khuyenMais =khuyenMais;
        this.context = context;

    }


    @NonNull
    @Override
    public AdapterKhuyenMaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyenmai,parent,false);
        AdapterKhuyenMaiHolder staticRvViewHolder = new AdapterKhuyenMaiHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKhuyenMaiHolder holder, int position) {
        KhuyenMai khuyenMai = khuyenMais.get(position);
        holder.textViewTen.setText(khuyenMai.getGiaDeDuocKhuyenMai().toString());
        if (khuyenMai.getLoaiKhuyenmai() == 1) {
            holder.textViewLoai.setText("Loại theo phần trăm");
        } else {
            holder.textViewLoai.setText("Loại theo địa chỉ");
        }
    }

    @Override
    public int getItemCount() {
        return khuyenMais.size();
    }

    public class AdapterKhuyenMaiHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewLoai;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterKhuyenMaiHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.text_tenKM);
            textViewLoai = itemView.findViewById(R.id.text_loaiKM);
            imgXoa = itemView.findViewById(R.id.btnXoaKM);

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });
        }


    }
}

