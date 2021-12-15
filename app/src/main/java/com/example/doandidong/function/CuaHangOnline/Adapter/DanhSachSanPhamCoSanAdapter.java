package com.example.doandidong.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import com.example.doandidong.Model.Product;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.DanhSachSanPhamActivity;
import com.example.doandidong.function.CuaHangOnline.Data.Image;
import com.example.doandidong.function.CuaHangOnline.Fragment.AddQuanCaoFragment;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhSachSanPhamCoSanAdapter extends RecyclerView.Adapter<DanhSachSanPhamCoSanAdapter.ProductViewHolder> {

    private ArrayList<Product> arrayList;
    private Context context;
    private TextInputEditText tenSanPham, soLuong, mota, title, giamgia;
    private ImageView imgageView;
    private AddQuanCaoFragment addQuanCaoFragment;


    public DanhSachSanPhamCoSanAdapter(Context context, ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public DanhSachSanPhamCoSanAdapter(Context context, AddQuanCaoFragment addQuanCaoFragment, ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.addQuanCaoFragment = addQuanCaoFragment;
    }

//    public void setData (TextInputEditText tenSanPham, TextInputEditText soLuong, TextInputEditText mota, TextInputEditText title,
//                         TextInputEditText giamgia, ImageView imgageView) {
//        this.tenSanPham = tenSanPham;
//        this.soLuong = soLuong;
//        this.mota = mota;
//        this.title = title;
//        this.giamgia = giamgia;
//        this.imgageView = imgageView;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public DanhSachSanPhamCoSanAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_dialog,parent,false);
        DanhSachSanPhamCoSanAdapter.ProductViewHolder productViewHolder = new DanhSachSanPhamCoSanAdapter.ProductViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachSanPhamCoSanAdapter.ProductViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.textViewName.setText(product.getNameProduct());
        Picasso.get().load(product.getImgProduct()).into(holder.imageView);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuanCaoFragment.onclickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private CircleImageView imageView;
        private RelativeLayout item;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textnameProduct);
            imageView = itemView.findViewById(R.id.circle_imgView);
            item = itemView.findViewById(R.id.item);
        }
    }
}
