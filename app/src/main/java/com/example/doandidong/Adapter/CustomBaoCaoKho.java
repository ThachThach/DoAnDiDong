package com.example.doandidong.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.example.doandidong.R;

public class CustomBaoCaoKho extends RecyclerView.ViewHolder{

    ShimmerFrameLayout shimmerFrameLayout;
    CardView cardView;
    TextView nhanvien, gio, ngay, titleNhanVien;
    ImageView imageView;

    public CustomBaoCaoKho(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardviewBaoCaoKho);
        nhanvien = itemView.findViewById(R.id.nhanvien);
        gio = itemView.findViewById(R.id.gio);
        ngay = itemView.findViewById(R.id.ngay);
        shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
        imageView = itemView.findViewById(R.id.image);
        titleNhanVien = itemView.findViewById(R.id.titleNhanVien);
    }
}
