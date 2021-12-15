package com.example.doandidong.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.SanPhamTop;
import com.example.doandidong.R;
import java.util.List;

public class CustomTopSanPhamAdapter extends RecyclerView.Adapter<CustomTopSanPham> {

    private Context context;
    private List<SanPhamTop> list;

    public CustomTopSanPhamAdapter(Context context, List<SanPhamTop> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomTopSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomTopSanPham(LayoutInflater.from(context).inflate(R.layout.item_top_san_pham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomTopSanPham holder, int position) {
        holder.soluong.setText(String.valueOf(list.get(position).getSoLuong()));
        holder.name.setText(list.get(position).getName());

        switch (position){
            case 0:
                if(checkValue(list.get(position).getSoLuong())){
                    holder.cardView.setCardBackgroundColor(Color.RED);
                }
                break;
            case 1:
                if(checkValue(list.get(position).getSoLuong())){
                    holder.cardView.setCardBackgroundColor(Color.BLUE);
                }
                break;
            case 2:
                if(checkValue(list.get(position).getSoLuong())){
                    holder.cardView.setCardBackgroundColor(Color.YELLOW);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean checkValue(int value){
        if(value == 0){
            return false;
        }
        return true;
    }
}
