package com.example.doandidong.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.BaoCaoKho;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BaoCao.ChiTietNhapKhoActivity;
import java.util.List;

public class QuanLyKhoAdapter extends RecyclerView.Adapter<CustomBaoCaoKho>{
    public static final String KEY_BAOCAO = "BAOCAO";
    private Context context;
    private List<BaoCaoKho> list;

    public QuanLyKhoAdapter(Context context, List<BaoCaoKho> list) {
        this.context = context;
        this.list = list;
    }

    public boolean isShimmer = true;
    public int shimmerNumber = 10;

    @NonNull
    @Override
    public CustomBaoCaoKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomBaoCaoKho(LayoutInflater.from(context).inflate(R.layout.item_quan_li_kho, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CustomBaoCaoKho holder, int position) {

        if (isShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }else {
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);

            holder.nhanvien.setBackground(null);
            holder.nhanvien.setText(list.get(position).getNhanVien());

            holder.ngay.setText(list.get(position).getNgay());
            holder.ngay.setBackground(null);

            holder.gio.setText(list.get(position).getGio());
            holder.gio.setBackground(null);

            holder.titleNhanVien.setText("Tên nhân viên:");
            holder.titleNhanVien.setBackground(null);

            holder.imageView.setBackground(null);
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.check_ok));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietNhapKhoActivity.class);
                    BaoCaoKho baoCaoKho = list.get(position);
                    Log.d("aaa","bbb");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_BAOCAO, baoCaoKho);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return isShimmer?shimmerNumber:list.size();
    }

    private boolean checkValue(int value){
        if(value == 0){
            return false;
        }
        return true;
    }
}
