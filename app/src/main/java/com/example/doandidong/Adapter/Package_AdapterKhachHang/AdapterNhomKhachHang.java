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

import com.example.doandidong.Data.KhachHang.NhomKhachHang;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.KhachHang.ListNhomKhachHang;
import com.example.doandidong.ChucNang.KhachHang.SuaNhomKhachHang;
import java.util.ArrayList;


public class AdapterNhomKhachHang extends RecyclerView.Adapter<AdapterNhomKhachHang.AdapterNhomKhachHangHolder> {
    ArrayList<NhomKhachHang> nhomKhachHangs;
    Context context;
    ListNhomKhachHang listNhomKhachHang;

    public AdapterNhomKhachHang(ListNhomKhachHang listNhomKhachHang,Context context,ArrayList<NhomKhachHang> nhomKhachHangs){
        this.nhomKhachHangs =nhomKhachHangs;
        this.context = context;
        this.listNhomKhachHang = listNhomKhachHang;
    }


    @NonNull
    @Override
    public AdapterNhomKhachHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhomkhachhang,parent,false);
        AdapterNhomKhachHangHolder staticRvViewHolder = new AdapterNhomKhachHangHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNhomKhachHangHolder holder, int position) {
        NhomKhachHang nhomKhachHang = nhomKhachHangs.get(position);
        String name = nhomKhachHang.getTenNhomKh().toUpperCase();
        char nam =name.charAt(0) ;
        holder.textViewTen1.setText(nam+"");
        holder.textViewTen.setText(nhomKhachHang.getTenNhomKh());
    }

    @Override
    public int getItemCount() {
        return nhomKhachHangs.size();
    }

    public class AdapterNhomKhachHangHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewTen1;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterNhomKhachHangHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.textnameNKH);
            textViewTen1 = itemView.findViewById(R.id.textTen);
            imgXoa = itemView.findViewById(R.id.btnXoaNKH);
            imgSua = itemView.findViewById(R.id.btnSuaNKH);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, SuaNhomKhachHang.class);
                    intent.putExtra("Key_arrNKH",  nhomKhachHangs.get(position));
                    context.startActivity(intent);
                }
            });
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    listNhomKhachHang.delete(position);
                }
            });
        }



    }
}
