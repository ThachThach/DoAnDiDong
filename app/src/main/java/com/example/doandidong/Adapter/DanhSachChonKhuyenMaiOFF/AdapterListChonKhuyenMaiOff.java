package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doandidong.Model.KhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.function.KhuyenMaiOffLine.ListChonKhuyenMaiOff;
import java.util.ArrayList;

public class AdapterListChonKhuyenMaiOff extends RecyclerView.Adapter<AdapterListChonKhuyenMaiOff.ListChonKhuyeMaiViewHolder> {
    private  ArrayList<KhuyenMaiOffModel>  arrayList;
    private ListChonKhuyenMaiOff context;




    public AdapterListChonKhuyenMaiOff( ListChonKhuyenMaiOff context,ArrayList<KhuyenMaiOffModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ListChonKhuyeMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachend_khuyenmaioff1,parent,false);
        ListChonKhuyeMaiViewHolder productViewHolder = new ListChonKhuyeMaiViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListChonKhuyeMaiViewHolder holder, int position) {
        KhuyenMaiOffModel crr = arrayList.get(position);
        holder.giatu.setText(crr.getGiakhuyenmaitu());
        holder.giaden.setText(crr.getGiakhuyenmaiden());
        holder.giakhuyenmai.setText(crr.getGiakhuyenmai());
        int k = position;


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListChonKhuyeMaiViewHolder extends RecyclerView.ViewHolder {

        TextView giatu,giaden,giakhuyenmai,tv_namesale;
        ConstraintLayout constraintLayout;
        ImageView xoa;

        public ListChonKhuyeMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            constraintLayout =itemView.findViewById(R.id.constraintLayout3);
            xoa = itemView.findViewById(R.id.bnt_delete);

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position, new ListChonKhuyenMaiOff.delete1() {
                        @Override
                        public void delete() {
                            notifyDataSetChanged();
                        }
                    });

                }
            });

        }
    }
}


