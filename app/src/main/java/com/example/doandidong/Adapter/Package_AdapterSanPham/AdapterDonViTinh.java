package com.example.doandidong.Adapter.Package_AdapterSanPham;



import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doandidong.Data.SanPham.DonViTinh;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.SanPham.ListDonViTinh;
import java.util.ArrayList;

public class AdapterDonViTinh extends RecyclerView.Adapter<AdapterDonViTinh.DonViTinhViewHolder> {
    private ArrayList<DonViTinh> arrayList;
    private ListDonViTinh context;
    private Context context2;



    public AdapterDonViTinh( ListDonViTinh context,Context context2,ArrayList<DonViTinh> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 = context2;
    }

    @NonNull
    @Override
    public DonViTinhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donvitinh,parent,false);
        DonViTinhViewHolder productViewHolder = new DonViTinhViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DonViTinhViewHolder holder, int position) {
        DonViTinh donViTinh = arrayList.get(position);
        String name = donViTinh.getDonViTinh().toUpperCase();
        char nam =name.charAt(0) ;
        holder.textViewName.setText(donViTinh.getDonViTinh());
        holder.nameViewName1.setText(nam+"");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DonViTinhViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView nameViewName1;
        private ImageView imgXoa;
        private ImageView imgSua;

        public DonViTinhViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textnameDVT);
            nameViewName1 = itemView.findViewById(R.id.textTenDVT);
            imgXoa = itemView.findViewById(R.id.btnXoaDVT);
            imgSua = itemView.findViewById(R.id.btnSuaDVT);
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.update(Gravity.CENTER,position);
                }
            });

        }
    }


}


