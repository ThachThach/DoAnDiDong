package com.example.doandidong.Adapter.Package_AdapterSanPham;

import android.content.Context;
import android.content.Intent;
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
import com.example.doandidong.ChucNang.SanPham.ListProduct;
import com.example.doandidong.ChucNang.SanPham.SuaSanPhamActivity;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private ArrayList<Product> arrayList;
    private ListProduct context;
    private Context context2;


    public AdapterProduct(Context context2, ListProduct context,ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 =context2;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.textViewStatus.setText(product.getStatus());
        holder.textViewName.setText(product.getNameProduct());
        Picasso.get().load(product.getImgProduct()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewStatus;
        private CircleImageView imageView;
        private ImageView imgXoa;
        private ImageView imgSua;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textnameStatus);
            textViewName = itemView.findViewById(R.id.textnameProduct);
            imageView = itemView.findViewById(R.id.circle_imgView);
            imgXoa = itemView.findViewById(R.id.btnXoaSP);
            imgSua = itemView.findViewById(R.id.btnSuaSP);
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context2, SuaSanPhamActivity.class);
                    intent.putExtra("Key_aray",arrayList.get(position));
                    context2.startActivity(intent);

                }
            });

        }
    }
}
