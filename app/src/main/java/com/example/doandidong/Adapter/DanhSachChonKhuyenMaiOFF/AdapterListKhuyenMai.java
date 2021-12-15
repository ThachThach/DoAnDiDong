package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;


import com.example.doandidong.Model.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.function.KhuyenMaiOffLine.ListChonKhuyenMaiOff;
import com.example.doandidong.function.KhuyenMaiOffLine.ListKhuyeMaiOff;
import java.util.ArrayList;

public class AdapterListKhuyenMai extends RecyclerView.Adapter<AdapterListKhuyenMai.ListKhuyeMaiViewHolder> {
    private ArrayList<ListKhuyenMaiOffModel> arrayList;
    private ListKhuyeMaiOff context;
    ArrayList<ListKhuyenMaiOffModel> listchuyen =new ArrayList<>();




    public AdapterListKhuyenMai( ListKhuyeMaiOff context,ArrayList<ListKhuyenMaiOffModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ListKhuyeMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyenmai_in_curd,parent,false);
        ListKhuyeMaiViewHolder productViewHolder = new ListKhuyeMaiViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListKhuyeMaiViewHolder holder, int position) {
        ListKhuyenMaiOffModel crr = arrayList.get(position);
        holder.giatu.setText(crr.getNgaybatdau());
        holder.giaden.setText(crr.getNgayketthuc());
        holder.giakhuyenmai.setText(crr.getNhomkhachhang());
        holder.tv_namesale.setText(crr.getTenkhuyenmai());
        int k = position;
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listchuyen.add(arrayList.get(k));
                Intent intent = new Intent(context, ListChonKhuyenMaiOff.class);
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String a = gson.toJson(arrayList.get(k));
                intent.putExtra("listchuyen", a);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListKhuyeMaiViewHolder extends RecyclerView.ViewHolder {

        TextView giatu,giaden,giakhuyenmai,tv_namesale;
        ConstraintLayout constraintLayout;
        ImageView xoa;

        public ListKhuyeMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            tv_namesale = itemView.findViewById(R.id.tv_namesale);
            constraintLayout =itemView.findViewById(R.id.constraintLayout15);
            xoa = itemView.findViewById(R.id.btnXoa);

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });


        }
    }

}


