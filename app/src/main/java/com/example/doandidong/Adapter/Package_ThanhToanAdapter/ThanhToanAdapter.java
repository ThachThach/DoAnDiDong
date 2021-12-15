package com.example.doandidong.Adapter.Package_ThanhToanAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import com.example.doandidong.Model.ChucNangThanhToan.ProuductPushFB1;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.CardDaOrderHoler> {
    private  ArrayList<ProuductPushFB1> items;
    public ThanhToanAdapter(ArrayList<ProuductPushFB1> items){
        this.items = items;
    }
    public void setData(ArrayList<ProuductPushFB1> item){
        items= item;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CardDaOrderHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_fordsell,parent,false);
        ThanhToanAdapter.CardDaOrderHoler card = new ThanhToanAdapter.CardDaOrderHoler(view);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDaOrderHoler holder, int position) {
        ProuductPushFB1 CrItem=items.get(position);
        holder.tvtensanpham.setText(CrItem.getNameProduct());
        holder.tvtensanphamtrong.setText(CrItem.getNameProduct());
        holder.tvgiasanphamngoai.setText(CrItem.getGiaProudct()+"");
        holder.tvloaisanphamtrong.setText(CrItem.getLoai());
        holder.tvgiasanphamtrong.setText(CrItem.getGiaProudct()+"");
        holder.tvghichu.setText(CrItem.getYeuCau());
        holder.tvsoluongsanphamtrong.setText("X"+CrItem.getSoluong()+"");
        holder.tonggiasanphamtrong.setText(CrItem.getSoluong()*CrItem.getGiaProudct()+"");
        holder.tvsoluong.setText("X"+CrItem.getSoluong()+"");
        Picasso.get().load(CrItem.getImgProduct()).into(holder.imgsanpham);
        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public class CardDaOrderHoler extends RecyclerView.ViewHolder {

        TextView tvtensanpham,tvtensanphamtrong,tvloaisanphamtrong,tvgiasanphamtrong,tvghichu,tvsoluongsanphamtrong,tonggiasanphamtrong,tvsoluong,tvgiasanphamngoai;
        FoldingCell foldingCell;
        ImageView imgsanpham;
        public CardDaOrderHoler(@NonNull View itemView) {
            super(itemView);
            foldingCell= itemView.findViewById(R.id.folding_cell);
            tvtensanphamtrong= itemView.findViewById(R.id.tvtensanphamtrong);
            tvtensanpham= itemView.findViewById(R.id.tvtensanpham);
            tvgiasanphamngoai= itemView.findViewById(R.id.tvgiasanphamngoai);
            tvloaisanphamtrong= itemView.findViewById(R.id.tvloaisanphamtrong);
            tvgiasanphamtrong= itemView.findViewById(R.id.tvgiasanphamtrong);
            tvghichu= itemView.findViewById(R.id.tvghichu);
            tvsoluongsanphamtrong= itemView.findViewById(R.id.tvsoluongsanphamtrong);
            tonggiasanphamtrong= itemView.findViewById(R.id.tonggiasanphamtrong);
            tvsoluong= itemView.findViewById(R.id.tvsoluong);
            imgsanpham= itemView.findViewById(R.id.imgsanpham);
        }
    }
}
