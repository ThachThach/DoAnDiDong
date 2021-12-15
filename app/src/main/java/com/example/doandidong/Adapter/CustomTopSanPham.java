package com.example.doandidong.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;


public class CustomTopSanPham extends RecyclerView.ViewHolder {

    TextView name, soluong;
    CardView cardView;
    ImageView imageView;

    public CustomTopSanPham(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.lblname);
        soluong = itemView.findViewById(R.id.lblSoLuong);
        cardView = itemView.findViewById(R.id.cardviewTopSanPham);
        imageView = itemView.findViewById(R.id.imageTopSanPham);
    }
}
