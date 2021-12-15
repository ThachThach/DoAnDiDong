package com.example.doandidong.Adapter.Package_AdapterNhanVien;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.NhanVien.ActivityUpdateNhanVien;
import com.example.doandidong.ChucNang.NhanVien.ChamCongNhanVienActivity;
import com.example.doandidong.ChucNang.NhanVien.ListNhanVien;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterNhanVien extends RecyclerView.Adapter<AdapterNhanVien.NhanVienViewHolder> {
    private ArrayList<NhanVien> arrayList;
    private ListNhanVien context;
    private Context context1;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private boolean isChu;
    private NhanVien nhanVien;

    public AdapterNhanVien(Context context1, ListNhanVien context, ArrayList<NhanVien> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context1 = context1;
        thongTinCuaHangSql = new ThongTinCuaHangSql(context);
        isChu = thongTinCuaHangSql.isChu();
        nhanVien = thongTinCuaHangSql.selectUser();
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listnhanvien, parent, false);
        NhanVienViewHolder nhanVienViewHolder = new NhanVienViewHolder(view);
        return nhanVienViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = arrayList.get(position);
        holder.textViewName.setText(nhanVien.getUsername());
        holder.textViewSDT.setText(nhanVien.getPhone());
        if (nhanVien.getAvata() != null) {
            Picasso.get().load(nhanVien.getAvata()).into(holder.usernhanvien);
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewSDT;
        private ImageView imgSua;
        private ImageView imgXoa;
        private CircleImageView usernhanvien;
        private View view;


        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.edtextTenNhanVien);
            textViewSDT = itemView.findViewById(R.id.edtextSDT);
            imgXoa = itemView.findViewById(R.id.btnXoaNV);
            imgSua = itemView.findViewById(R.id.btnSuaNV);
            usernhanvien = itemView.findViewById(R.id.usernhanvien);
            view = itemView.findViewById(R.id.view);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (arrayList.get(position).isChuCuaHang() && isChu) {
                        Intent intent = new Intent(context1, ActivityUpdateNhanVien.class);
                        intent.putExtra("Key_arrayNV", arrayList.get(position));
                        context1.startActivity(intent);
                    } else if (!arrayList.get(position).isChuCuaHang() && nhanVien.getChucVu().get(0) && !nhanVien.getEmail().equals(arrayList.get(position).getEmail())) {
                        Intent intent = new Intent(context1, ActivityUpdateNhanVien.class);
                        intent.putExtra("Key_arrayNV", arrayList.get(position));
                        context1.startActivity(intent);
                    } else {
                        Toast.makeText(context1, "Không thực hiện được hành động này", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (arrayList.get(position).isChuCuaHang() && isChu) {
                        Intent intent = new Intent(context1, ChamCongNhanVienActivity.class);
                        intent.putExtra("ID_NHANVIEN", arrayList.get(position).getId());
                        context1.startActivity(intent);
                    } else if (!arrayList.get(position).isChuCuaHang() && nhanVien.getChucVu().get(0) && !nhanVien.getEmail().equals(arrayList.get(position).getEmail())) {
                        Intent intent = new Intent(context1, ChamCongNhanVienActivity.class);
                        intent.putExtra("ID_NHANVIEN", arrayList.get(position).getId());
                        context1.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context1, "Không thực hiện được hành động này", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (arrayList.get(position).isChuCuaHang() && isChu) {
//                        context.delete(position);
                    } else if (!arrayList.get(position).isChuCuaHang() && nhanVien.getChucVu().get(0) && !nhanVien.getEmail().equals(arrayList.get(position).getEmail())) {
                        context.delete(position);
                    } else {
                        Toast.makeText(context1, "Không thực hiện được hành động này", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
