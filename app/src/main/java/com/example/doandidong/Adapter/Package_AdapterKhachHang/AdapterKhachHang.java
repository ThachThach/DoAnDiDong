package com.example.doandidong.Adapter.Package_AdapterKhachHang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Model.KhachHang.KhachHang;
import com.example.doandidong.R;
import com.example.doandidong.function.KhachHang.ListKhachHang;
import com.example.doandidong.function.KhachHang.SuaKhachHang;
import java.util.ArrayList;


public class AdapterKhachHang extends RecyclerView.Adapter<AdapterKhachHang.AdapterKhachHangHolder> {
    ArrayList<KhachHang> khachHangs;
    Context context;
    ListKhachHang context1;



    public AdapterKhachHang(ListKhachHang context1,Context context,ArrayList<KhachHang> khachHangs){
       this.khachHangs =khachHangs;
        this.context = context;
        this.context1 = context1;
    }


    @NonNull
    @Override
    public AdapterKhachHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang,parent,false);
        AdapterKhachHangHolder staticRvViewHolder = new AdapterKhachHangHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKhachHangHolder holder, int position) {
        KhachHang khachHang = khachHangs.get(position);
        holder.textViewTen.setText(khachHang.getHoTenKhachHang());
        holder.textViewSDT.setText(khachHang.getSoDT());
    }

    @Override
    public int getItemCount() {
        return khachHangs.size();
    }

    public class AdapterKhachHangHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewSDT;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterKhachHangHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.edtextTenKhachHang);
            textViewSDT = itemView.findViewById(R.id.edtextSDTKhachHang);
            imgXoa = itemView.findViewById(R.id.btnXoaKhachHang);
            imgSua = itemView.findViewById(R.id.btnSuaKhachHang);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, SuaKhachHang.class);
                    intent.putExtra("Key_arrKH",khachHangs.get(position));
                    context.startActivity(intent);
                }
            });

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context1.delete(position);
                }
            });
        }



    }
}
