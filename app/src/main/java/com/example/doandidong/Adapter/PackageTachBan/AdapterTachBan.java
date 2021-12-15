package com.example.doandidong.Adapter.PackageTachBan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Common.ArrayListTachBan;
import com.example.doandidong.Model.ChucNangThanhToan.ProductPushFB;
import com.example.doandidong.R;
import com.example.doandidong.function.TachBanActivity;
import java.util.ArrayList;

public class AdapterTachBan extends RecyclerView.Adapter<AdapterTachBan.TachBanHodel> {
    private ArrayList<ProductPushFB> items;

private ArrayList<TachBanHodel> tachBanHodels =new ArrayList<>();
//    ArrayList<ProuductPushFB1> itemtach;
    int select;
    Boolean as = false;
    ArrayListTachBan arrayListTachBan;
    TachBanActivity tachBanActivity;
    public AdapterTachBan(TachBanActivity tachBanActivity, ArrayListTachBan  arrayListTachBan){
        this.items = new ArrayList<>();
        this.tachBanActivity = tachBanActivity;
        this.arrayListTachBan = arrayListTachBan;
    }
    public void  setData(ArrayList<ProductPushFB> items){
        this.items = items;
    }
    @NonNull
    @Override
    public TachBanHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tachban,parent,false);
        TachBanHodel tachBanHodel = new TachBanHodel(view);

        return tachBanHodel;
    }

    @Override
    public void onBindViewHolder(@NonNull TachBanHodel holder, int position) {
//        itemtach = new ArrayList<ProuductPushFB1>();
        ProductPushFB crr = items.get(0);
        tachBanHodels.add(holder);
        if(items.size()>0 && items!=null){
            int a =crr.getSanpham().get(position).getSoluong();
            Log.d("dddd",a+"");
                holder.tvtensanpham.setText(crr.getSanpham().get(position).getNameProduct());
                holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                holder.tvgiasanpham.setText(crr.getSanpham().get(position).getGiaProudct()+"");
                holder.tvLoai.setText(crr.getSanpham().get(position).getLoai());
                holder.imgminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                    if(crr.getSanpham().get(position).getSoluong()>0){
                        items.get(0).getSanpham().get(position).setSoluong(items.get(0).getSanpham().get(position).getSoluong()-1);
                        Log.d("dddd",items.get(0).getSanpham().get(position).getSoluong()+""+"");
                        holder.tvsoluong.setText(items.get(0).getSanpham().get(position).getSoluong()+"");

                    }
                }
            });
            holder.imgplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvsoluong.setText( items.get(0).getSanpham().get(position).getSoluong()+"");

                    if( items.get(0).getSanpham().get(position).getSoluong()<=a-1){
                        items.get(0).getSanpham().get(position).setSoluong(items.get(0).getSanpham().get(position).getSoluong()+1);
                        holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (items!=null){
            return items.get(0).getSanpham().size() ;
        }
        return 0;
    }

    public class TachBanHodel extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvtensanpham,tvLoai,tvgiasanpham,Tong1SanPham,tvsoluong;
        ImageView imgminus,imgplus;
        public TachBanHodel(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            tvtensanpham=itemView.findViewById(R.id.tvtensanpham);
            tvLoai=itemView.findViewById(R.id.tvLoai);
            tvgiasanpham=itemView.findViewById(R.id.tvgiasanpham);
            Tong1SanPham=itemView.findViewById(R.id.Tong1SanPham);
            imgminus=itemView.findViewById(R.id.imgminus);
            imgplus=itemView.findViewById(R.id.imgplus);
            tvsoluong=itemView.findViewById(R.id.tvsoluong);

        }
    }
    public ProductPushFB PublicArraylist(){
      ProductPushFB productPushFBS = new ProductPushFB();
        for (int i = 0; i <tachBanHodels.size() ; i++) {
            if(tachBanHodels.get(i).checkBox.isChecked()){
                productPushFBS.getSanpham().add( items.get(0).getSanpham().get(i));
                productPushFBS.setDate(items.get(0).getDate());
                Log.d("Productsdatene",productPushFBS.getDate()+"Tachban");

            }
        }
        return productPushFBS;
    }
}
