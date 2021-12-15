package com.example.doandidong.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;

public class ItemDiaChiAdapter extends RecyclerView.Adapter<ItemDiaChiAdapter.ThietLapHolder>{

    private Context context;

    public ItemDiaChiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ThietLapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDiaChiAdapter.ThietLapHolder(LayoutInflater.from(context).inflate(R.layout.item_dia_chi_cua_hang_giao, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThietLapHolder holder, int position) {
        if(position%2==0)
        {
            holder.line.setBackgroundResource(R.color.background_baocao);
        }
        else
        {
            holder.line.setBackgroundResource(R.color.hong_nhat);
        }

    }



    @Override
    public int getItemCount() {
        return 3;
    }

    public class ThietLapHolder extends RecyclerView.ViewHolder {
        private LinearLayout line;
        public ThietLapHolder(@NonNull View ItemView) {
            super(ItemView);

            line = ItemView.findViewById(R.id.line);
        }
    }

}
