package com.example.doandidong.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.model.BienLai;
import com.example.doandidong.function.BienLaiActivity;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DanhSachHoaDonAdapter extends RecyclerView.Adapter<DanhSachHoaDonAdapter.CustomChiSoSanPham> {

    private Context context;
    private ArrayList<BienLai> list;

    public DanhSachHoaDonAdapter(Context context, ArrayList<BienLai> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(ArrayList<BienLai> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CustomChiSoSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomChiSoSanPham(LayoutInflater.from(context).inflate(R.layout.item_danh_sach_bien_lai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiSoSanPham holder, int position) {
        holder.ngay.setText(setUpDate(list.get(position).getKey()));
        holder.tv_iDdonhang.setText(list.get(position).getKey());
        holder.tv_tongtien.setText(list.get(position).getTongtien()+"");
        if (list.get(position).isLoai()== false) {
            holder.tv_loai.setText("Cửa hàng");
            holder.tv_loai.setTextColor(ContextCompat.getColor(context, R.color.so_hoa_don));
        }else {
            holder.tv_loai.setText("Online");
            holder.tv_loai.setTextColor(ContextCompat.getColor(context, R.color.Python));
        }
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    public class CustomChiSoSanPham extends RecyclerView.ViewHolder {
        private TextView ngay, tv_loai, tv_trangthai, tv_iDdonhang, tv_tongtien;
        private LinearLayout allLinear;
        public CustomChiSoSanPham(@NonNull View ItemView) {
            super(ItemView);
            ngay = ItemView.findViewById(R.id.ngay);
            tv_loai = ItemView.findViewById(R.id.tv_loai);
            tv_trangthai = ItemView.findViewById(R.id.tv_trangthai);
            tv_iDdonhang = ItemView.findViewById(R.id.tv_iDdonhang);
            tv_tongtien = ItemView.findViewById(R.id.tv_tongtien);
            allLinear = ItemView.findViewById(R.id.allLinear);

            allLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, BienLaiActivity.class);
                    intent.putExtra("BIENLAI", list.get(position));
                    context.startActivity(intent);
                }
            });

        }
    }

    private String setUpDate(String str) {
        long log = Long.parseLong(str);
        Timestamp timestamp = new Timestamp(log);
        java.sql.Date date = new java.sql.Date(timestamp.getTime());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dt = formatter.format(date);

        return  dt;

    }
}