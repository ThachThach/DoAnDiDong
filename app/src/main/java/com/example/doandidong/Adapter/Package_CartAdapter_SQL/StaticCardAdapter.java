package com.example.doandidong.Adapter.Package_CartAdapter_SQL;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.Model.Product;
import com.example.doandidong.R;
import com.example.doandidong.function.CardProductSQL.CardProduct;
import java.util.ArrayList;

public class StaticCardAdapter extends RecyclerView.Adapter<StaticCardAdapter.StaticRvCardHolder>{
    ArrayList<Product> items;
    private StaticCardAdapter.card intercart;
    private CardProduct cardProduct;
    public interface card {
        void TinhTongTien(Double tongtien);
    }
    public ArrayList<Product> getItems() {
        return items;
    }


    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public StaticCardAdapter(ArrayList<Product> items, Activity activity) {
        this.items = items;



    }

    public StaticCardAdapter() {
        this.items = new ArrayList<>();
    }
    public void Setdata(ArrayList<Product> staticMonOrderModel,StaticCardAdapter.card intercart,CardProduct cardProduct){
        this.items = staticMonOrderModel;
        this.intercart =intercart;
        this.cardProduct = cardProduct;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public StaticRvCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        StaticRvCardHolder staticRvViewHolder = new StaticRvCardHolder(view);
        intercart.TinhTongTien(TinhTong());
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvCardHolder holder, int position) {
        Product CrItem=items.get(position);

        holder.tensanpham.setText(CrItem.getNameProduct());
//        holder.giasanpham.setText(CrItem.getGiaBan()+"");
        holder.tvsoluong.setText(CrItem.getSoluong()+"");
        holder.Loai.setText(CrItem.getDonGia().get(position).getTenDonGia());
        holder.giasanpham.setText(CrItem.getDonGia().get(position).getGiaBan()+"");
        holder.Tong1SanPham.setText(CrItem.getDonGia().get(position).getGiaBan()*CrItem.getSoluong()+"");
        Log.d("tvsoluong",CrItem.getSoluong()+"");
        Picasso.get().load(CrItem.getImgProduct()).into(holder.imgsanpham);
        holder.imgplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvsoluong.setText(CrItem.getSoluong()+"");
                if(CrItem.getSoluong()!=0){

                    items.get(position).setSoluong(CrItem.getSoluong()+1);
                    holder.tvsoluong.setText(CrItem.getSoluong()+"");
                    holder.Tong1SanPham.setText(items.get(position).getSoluong()*items.get(position).getDonGia().get(position).getGiaBan()+"");
                }
                intercart.TinhTongTien(TinhTong());

            }
        });
        holder.imgminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvsoluong.setText(CrItem.getSoluong()+"");
                if(CrItem.getSoluong()>1) {

                    items.get(position).setSoluong(CrItem.getSoluong()-1);
                    holder.tvsoluong.setText(CrItem.getSoluong() + "");
                    holder.Tong1SanPham.setText(items.get(position).getSoluong()*items.get(position).getDonGia().get(position).getGiaBan()+"");
                }



            }
        });
        holder. img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                cardProduct.delete(position);

                notifyDataSetChanged();
            }
        });



    }
    private Double TinhTong() {
        double tong = 0.0;
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                tong = tong + items.get(i).getSoluong() * items.get(i).getDonGia().get(i).getGiaBan();

            }
            return tong;
        }
        return 0.0;
    }

    @Override
    public int getItemCount() {
        if (items!=null){
            return items.size();
        }
        return 0;
    }

    public static class StaticRvCardHolder extends RecyclerView.ViewHolder{
        TextView tensanpham ;
        TextView giasanpham;
        ImageView imgsanpham;
        TextView tvsoluong;
        TextView Loai;
        ImageView imgminus,imgplus;
        TextView Tong1SanPham;
        ImageView img_cancel;

        ConstraintLayout constraintLayouts ;
        public StaticRvCardHolder(@NonNull View itemView) {
            super(itemView);
            tensanpham =itemView.findViewById(R.id.tvtensanpham);
            giasanpham = itemView.findViewById(R.id.tvgiasanpham);
            imgsanpham = itemView.findViewById(R.id.imgsanpham);
            tvsoluong= itemView.findViewById(R.id.tvsoluong);
            Loai = itemView.findViewById(R.id.tvLoai);
            imgminus = itemView.findViewById(R.id.imgminus);
            imgplus = itemView.findViewById(R.id.imgplus);

            Tong1SanPham = itemView.findViewById(R.id.Tong1SanPham);
            img_cancel = itemView.findViewById(R.id.img_cancel);

        }
    }

}
