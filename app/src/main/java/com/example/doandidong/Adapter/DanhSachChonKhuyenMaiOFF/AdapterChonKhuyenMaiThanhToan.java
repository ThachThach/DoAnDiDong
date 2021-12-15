package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.example.doandidong.Data.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.KhuyenMaiOffLine.KhuyenMaiThanhToan;
import com.example.doandidong.ChucNang.ThanhToanActivity;
import java.util.ArrayList;

public class AdapterChonKhuyenMaiThanhToan extends RecyclerView.Adapter<AdapterChonKhuyenMaiThanhToan.AdapterChonkmhodel> {
    ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels;
    ArrayList<ListKhuyenMaiOffModel> listchuyen =new ArrayList<>();
    ThanhToanActivity thanhToanActivity;

    public AdapterChonKhuyenMaiThanhToan(ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels,ThanhToanActivity thanhToanActivity) {
        this.listKhuyenMaiOffModels = listKhuyenMaiOffModels;
        this.thanhToanActivity = thanhToanActivity;
    }

    @NonNull
    @Override
    public AdapterChonkmhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyenmai_in_dialogthanhtoan, parent, false);
        AdapterChonkmhodel adapterChonKhuyenMai = new AdapterChonkmhodel(view);
        return adapterChonKhuyenMai;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonkmhodel holder, int position) {
        ListKhuyenMaiOffModel crr = listKhuyenMaiOffModels.get(position);

        holder.giatu.setText(crr.getNgaybatdau());
        holder.giaden.setText(crr.getNgayketthuc());
        holder.giakhuyenmai.setText(crr.getNhomkhachhang());
        holder.tv_namesale.setText(crr.getTenkhuyenmai());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(thanhToanActivity, "Onclick", Toast.LENGTH_SHORT).show();
                listchuyen.add(listKhuyenMaiOffModels.get(position));
                Intent intent = new Intent(thanhToanActivity, KhuyenMaiThanhToan.class);
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String a = gson.toJson(listKhuyenMaiOffModels.get(position));
                intent.putExtra("listchuyen", a);
                intent.putExtras(bundle);
                thanhToanActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listKhuyenMaiOffModels != null) {
            return
                    listKhuyenMaiOffModels.size();
        }
        return 0;
    }

    public class AdapterChonkmhodel extends RecyclerView.ViewHolder {
        TextView giatu,giaden,giakhuyenmai,tv_namesale;
        ConstraintLayout constraintLayout;
        public AdapterChonkmhodel(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            tv_namesale = itemView.findViewById(R.id.tv_namesale);
            constraintLayout =itemView.findViewById(R.id.constraintLayout);

        }
    }
}
