package com.example.doandidong.ChucNang.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.R;
import com.example.doandidong.ChucNang.CuaHangOnline.Data.Product;
import com.example.doandidong.ChucNang.CuaHangOnline.Fragment.SanPhamQuangCaoFragment;
import java.util.ArrayList;

public class SanPhamChoXacNhanAdapter extends RecyclerView.Adapter<SanPhamChoXacNhanAdapter.SanPhamHodel>{

    private Context context;
    private ArrayList<Product> list;
    private SanPhamQuangCaoFragment choXacNhanQuangCaoFragment;

    public SanPhamChoXacNhanAdapter(Context context, ArrayList<Product> list, SanPhamQuangCaoFragment choXacNhanQuangCaoFragment) {
        this.context = context;
        this.list = list;
        this.choXacNhanQuangCaoFragment = choXacNhanQuangCaoFragment;
    }

    @NonNull
    @Override
    public SanPhamHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SanPhamChoXacNhanAdapter.SanPhamHodel(LayoutInflater.from(context).inflate(R.layout.item_san_pham_cho_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHodel holder, int position) {
        Picasso.get().load(list.get(position).getImgProduct()).into(holder.image);
        holder.name.setText(list.get(position).getNameProduct());
        holder.idsanpham.setText(list.get(position).getIdCuaHang());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SanPhamHodel extends RecyclerView.ViewHolder {

        private TextView huy, name,idsanpham;
        private ImageView image;
        private LinearLayout itemClick, linerlayoutEnabled;

        public SanPhamHodel(@NonNull View ItemView) {
            super(ItemView);
            huy = ItemView.findViewById(R.id.huy);
            name = ItemView.findViewById(R.id.name);
            idsanpham = ItemView.findViewById(R.id.idsanpham);
            image = ItemView.findViewById(R.id.image);
            itemClick = ItemView.findViewById(R.id.itemClick);
            linerlayoutEnabled = ItemView.findViewById(R.id.linerlayoutEnabled);


            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    choXacNhanQuangCaoFragment.delete(position);
                }
            });
        }
    }
}
