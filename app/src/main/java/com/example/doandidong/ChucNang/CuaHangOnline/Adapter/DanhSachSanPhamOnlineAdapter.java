package com.example.doandidong.ChucNang.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.Data.Product;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.CuaHangOnline.DanhSachSanPhamActivity;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhSachSanPhamOnlineAdapter extends RecyclerView.Adapter<DanhSachSanPhamOnlineAdapter.ProductViewHolder> {

    private ArrayList<Product> arrayList;
    private DanhSachSanPhamActivity context;
    private Context context2;


    public DanhSachSanPhamOnlineAdapter(Context context2, DanhSachSanPhamActivity context,ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 =context2;
    }

    @NonNull
    @Override
    public DanhSachSanPhamOnlineAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_online,parent,false);
        DanhSachSanPhamOnlineAdapter.ProductViewHolder productViewHolder = new DanhSachSanPhamOnlineAdapter.ProductViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachSanPhamOnlineAdapter.ProductViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.textViewName.setText(product.getNameProduct());
        Picasso.get().load(product.getImgProduct()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private CircleImageView imageView;
        private ImageView imgXoa;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textnameProduct);
            imageView = itemView.findViewById(R.id.circle_imgView);
            imgXoa = itemView.findViewById(R.id.btnXoaSP);
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });
        }
    }
}
