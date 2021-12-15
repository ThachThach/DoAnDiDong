package com.example.doandidong.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Data.Setting;
import java.util.ArrayList;
import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.DiaChiHolder> {

    private Context context;
    private List<Setting> list;

    public DiaChiAdapter(Context context, ArrayList<Setting> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DiaChiAdapter.DiaChiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaChiAdapter.DiaChiHolder(LayoutInflater.from(context).inflate(R.layout.item_danh_sach_dia_chi_cua_hang_giao, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiHolder holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.itemdrop.getLayoutParams();
        if (list.get(position).isDrop()) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.img.setImageResource(R.drawable.up_24);
            holder.itemdrop.setLayoutParams(params);
        } else {
            params.height = 0;
            holder.img.setImageResource(R.drawable.down_24);
            holder.itemdrop.setLayoutParams(params);
        }

        holder.btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isDrop()) {
                    params.height = 0;
                    list.get(position).setDrop(false);
                    holder.img.setImageResource(R.drawable.down_24);
                    holder.itemdrop.setLayoutParams(params);
                    notifyDataSetChanged();
                } else {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    list.get(position).setDrop(true);
                    holder.img.setImageResource(R.drawable.up_24);
                    holder.itemdrop.setLayoutParams(params);
                    notifyDataSetChanged();
                }
            }
        });

        Recycleview(holder);
    }

    private void Recycleview(DiaChiHolder holder) {
        holder.recyclerview.setHasFixedSize(true);
        holder.recyclerview.setLayoutManager(new GridLayoutManager(context, 1));
        ItemDiaChiAdapter itemDiaChiAdapter = new ItemDiaChiAdapter(context);
        holder.recyclerview.setAdapter(itemDiaChiAdapter);
        itemDiaChiAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DiaChiHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemdrop, btnLine;
        private ImageView img;
        private RecyclerView recyclerview;

        public DiaChiHolder(@NonNull View ItemView) {
            super(ItemView);

            itemdrop = ItemView.findViewById(R.id.itemdrop);
            btnLine = ItemView.findViewById(R.id.btnLine);
            img = ItemView.findViewById(R.id.img);
            recyclerview = ItemView.findViewById(R.id.recyclerview);
        }
    }
}
