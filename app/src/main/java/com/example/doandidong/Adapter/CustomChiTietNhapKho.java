package com.example.doandidong.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;

public class CustomChiTietNhapKho extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView tenSanPham, soLieuCu, soLieuMoi;
    ImageView edit, delete;


    public CustomChiTietNhapKho(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.recylerView_chi_tiet_cap_nhat_kho);
        tenSanPham = itemView.findViewById(R.id.tenSanPham);
        soLieuCu = itemView.findViewById(R.id.soLieuCu);
        soLieuMoi = itemView.findViewById(R.id.soLieuMoi);
        edit = itemView.findViewById(R.id.edit_item);
        delete = itemView.findViewById(R.id.delete_item);
    }
}
