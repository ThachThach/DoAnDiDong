package com.example.doandidong.Adapter.Pakage_AdapterKhuVuc;

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
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.ListKhuVuc;
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.SuaKhuVuc;
import java.util.ArrayList;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.KhuVucViewHolder> {
    private ArrayList<StaticModelKhuVuc> arrayList;
    private ListKhuVuc context;
    private Context context2;



    public KhuVucAdapter( ListKhuVuc context,Context context2,ArrayList<StaticModelKhuVuc> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 = context2;
    }

    @NonNull
    @Override
    public KhuVucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuvuc_edit,parent,false);
        KhuVucViewHolder productViewHolder = new KhuVucViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull KhuVucViewHolder holder, int position) {
        StaticModelKhuVuc staticModelKhuVuc = arrayList.get(position);
        if (staticModelKhuVuc.getTrangthai().equals("1")) {
            holder.textViewStatus.setText("Trạng thái: tốt");
        }else {
            holder.textViewStatus.setText("Trạng thái: hỏng");
        }
        holder.textViewName.setText(staticModelKhuVuc.getTenkhuvuc());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class KhuVucViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewStatus;
        private ImageView imgXoa;
        private ImageView imgSua;

        public KhuVucViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.tv_trangthai);
            textViewName = itemView.findViewById(R.id.tv_khuvuc);
            imgXoa = itemView.findViewById(R.id.bnt_xoa);
            imgSua = itemView.findViewById(R.id.bnt_sua);
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
                    Intent intent = new Intent(context2, SuaKhuVuc.class);
                    intent.putExtra("Key_aray", arrayList.get(position));
                    context2.startActivity(intent);
                }
            });

        }
    }
}

