package com.example.doandidong.ChucNang.BepBar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BepBar.Data.Mon;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Mon> list;

    public ChiTietDonHangAdapter(Context context, ArrayList<Mon> mons) {
        this.context = context;
        this.list = mons;
    }

    @NonNull
    @Override
    public ChiTietDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChiTietDonHangAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_bep, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangAdapter.ViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getImgProduct()).into(holder.circleImageView);
//        Glide.with(context).load(list.get(position).getImgProduct()).into(holder.circleImageView);
        holder.textnameProduct.setText(list.get(position).getNameProduct());
        holder.textSoLuong.setText("Số lượng: "+list.get(position).getSoluong()+"");
        if (list.get(position).getYeuCau() == null) {
            holder.textYeuCau.setText("Yêu cầu:  " + "Không có");
        }else {
            holder.textYeuCau.setText("Yêu cầu:  "+list.get(position).getYeuCau());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView textnameProduct, textSoLuong, textYeuCau;

        public ViewHolder(@NonNull View ItemView) {
            super(ItemView);

            circleImageView = ItemView.findViewById(R.id.circle_imgView);
            textnameProduct = ItemView.findViewById(R.id.textnameProduct);
            textSoLuong = ItemView.findViewById(R.id.textSoLuong);
            textYeuCau = ItemView.findViewById(R.id.textYeuCau);
        }
    }
}
