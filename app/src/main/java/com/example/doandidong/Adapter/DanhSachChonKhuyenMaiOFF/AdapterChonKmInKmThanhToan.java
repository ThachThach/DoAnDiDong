package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.KhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.KhuyenMaiOffLine.KhuyenMaiThanhToan;
import java.util.ArrayList;

public class AdapterChonKmInKmThanhToan extends RecyclerView.Adapter<AdapterChonKmInKmThanhToan.ChonKmInKmThanhToan> {
    KhuyenMaiThanhToan khuyenMaiThanhToan;
    ArrayList<KhuyenMaiOffModel> listchuyen;
    ArrayList<ChonKmInKmThanhToan> chonKmInKmThanhToans = new ArrayList<>();

    public AdapterChonKmInKmThanhToan(ArrayList<KhuyenMaiOffModel> listchuyen, KhuyenMaiThanhToan khuyenMaiThanhToan) {
        this.khuyenMaiThanhToan = khuyenMaiThanhToan;
        this.listchuyen = listchuyen;
    }

    @NonNull
    @Override
    public ChonKmInKmThanhToan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themkhuyenmaioff, parent, false);
        ChonKmInKmThanhToan adapterChonKhuyenMai = new ChonKmInKmThanhToan(view);
        return adapterChonKhuyenMai;
    }

    @Override
    public void onBindViewHolder(@NonNull ChonKmInKmThanhToan holder, int position) {
        chonKmInKmThanhToans.add(holder);
        KhuyenMaiOffModel crr = listchuyen.get(position);
        holder.giatu.setText(crr.getGiakhuyenmaitu());
        holder.giaden.setText(crr.getGiakhuyenmaiden());
        holder.giakhuyenmai.setText(crr.getGiakhuyenmai());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChekBox(position);
                Toast.makeText(khuyenMaiThanhToan, chonKmInKmThanhToans.size() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listchuyen != null) {
            return listchuyen.size();
        }
        return 0;
    }

    public class ChonKmInKmThanhToan extends RecyclerView.ViewHolder {
        TextView giatu, giaden, giakhuyenmai;
        CheckBox checkBox;
        ConstraintLayout constraintLayout;

        public ChonKmInKmThanhToan(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    private void setChekBox(int pos) {
        for (int i = 0; i < chonKmInKmThanhToans.size(); i++) {
            if (i == pos) {
                chonKmInKmThanhToans.get(i).checkBox.setChecked(true);
            } else {
                chonKmInKmThanhToans.get(i).checkBox.setChecked(false);
            }
        }
    }

    public ArrayList<KhuyenMaiOffModel> PublicArraylist() {
        ArrayList<KhuyenMaiOffModel> lists = new ArrayList<KhuyenMaiOffModel>();
        for (int i = 0; i < chonKmInKmThanhToans.size(); i++) {
            if (chonKmInKmThanhToans.get(i).checkBox.isChecked()) {
                lists.add(listchuyen.get(i));
            }
        }

        return lists;
    }
}
