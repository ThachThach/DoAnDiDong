package com.example.doandidong.Adapter.Pakage_AdapterBan;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doandidong.R;
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.ListBan;
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.SuaBanKV;
import java.util.ArrayList;

public class AdapterBan extends RecyclerView.Adapter<AdapterBan.BanViewHolder> {
    private ArrayList<StaticBanModel> arrayList;
    private ListBan context;
    private Context context2;



    public AdapterBan( ListBan context,Context context2,ArrayList<StaticBanModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 = context2;

    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban_edit,parent,false);
        BanViewHolder productViewHolder = new BanViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        StaticBanModel staticBanModel = arrayList.get(position);
        holder.textViewName.setText(staticBanModel.getTenban()+"");

        if (staticBanModel.getTrangthai().equals("1")) {
            holder.textViewStatus.setText("Chưa oder");
        }else if (staticBanModel.getTrangthai().equals("3")) {
            holder.textViewStatus.setText("Đang hư");
        }else if (staticBanModel.getTrangthai().equals("2")) {
            holder.textViewStatus.setText("Đã oder");
        }else if (staticBanModel.getTrangthai().equals("4")) {
            holder.textViewStatus.setText("");
        }else if (staticBanModel.getTrangthai().equals("2")) {
            holder.textViewStatus.setText("Đã oder");
        }else if (staticBanModel.getTrangthai().equals("5")) {
            holder.textViewStatus.setText("");
        }else if (staticBanModel.getTrangthai().equals("6")) {
            holder.textViewStatus.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BanViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewStatus;
        private ImageView imgXoa;
        private ImageView imgSua;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.tv_trangthai);
            textViewName = itemView.findViewById(R.id.tv_ban);
            imgXoa = itemView.findViewById(R.id.btn_xoaban);
            imgSua = itemView.findViewById(R.id.btn_suaban);
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
                    Intent intent = new Intent(context2, SuaBanKV.class);
                    intent.putExtra("Key_arayBan",arrayList.get(position));

                    context2.startActivity(intent);
                }
            });

        }
    }
}

