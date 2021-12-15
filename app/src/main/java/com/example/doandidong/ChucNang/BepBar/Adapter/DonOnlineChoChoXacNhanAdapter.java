package com.example.doandidong.ChucNang.BepBar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BepBar.DangXuLyOnlineActivity;
import com.example.doandidong.ChucNang.BepBar.Data.DonHang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DonOnlineChoChoXacNhanAdapter extends RecyclerView.Adapter<DonOnlineChoChoXacNhanAdapter.ViewHolder> {

    Context context;
    List<DonHang> mList;
    private DatabaseReference mDatabase;


    public DonOnlineChoChoXacNhanAdapter(Context context, List<DonHang> mList) {
        this.context = context;
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.item_bep_bar_danh_sanh_ban, parent, false);
        return new DonOnlineChoChoXacNhanAdapter.ViewHolder(heroView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.examName.setText("ID: " +mList.get(position).getKey());
        holder.examDate.setText(changeDate(mList.get(position).getKey()));
//        SanPhamDonHangOnlineAdapter sanphamHolder = new SanPhamDonHangOnlineAdapter(context, mList.get(position).getSanpham());

        holder.tongtien.setText(tongDon(position)+"");

        if (mList.get(position).getTrangthai() == 2) {
            holder.xuly.setText("Xử lí");
            holder.trangThai.setText("Chờ xử lí");
        }else if (mList.get(position).getTrangthai() == 3) {
            holder.xuly.setText("Đang xử lí");
            holder.trangThai.setText("Đang xử lí");
        }else if (mList.get(position).getTrangthai() == 4) {
            holder.xuly.setText("Hoàn thành");
            holder.trangThai.setText("Chờ shipper");
        }

        holder.xuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DangXuLyOnlineActivity.class);
                intent.putExtra("key", mList.get(position).getKey());
                intent.putExtra("key_ngay", ngayHientai(mList.get(position).getDate()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView examName;
        private TextView examDate;
        private TextView khuvuc;
        private TextView xuly;
        private TextView soluong, tongtien, trangThai;

        public ViewHolder(View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.examName);
            examDate = itemView.findViewById(R.id.examDate);
            khuvuc = itemView.findViewById(R.id.khuvuc);
            xuly = itemView.findViewById(R.id.xuly);
            soluong = itemView.findViewById(R.id.soluong);
            tongtien = itemView.findViewById(R.id.tongtien);
            trangThai = itemView.findViewById(R.id.trangThai);

        }
    }

    private String ngayHientai(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        return dt;
    }

    private String changeDate(String date) {
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if (dates == 0) {
            return "";
        }
        Date date1 = new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        return aaa;
    }

    private Double tongDon(int position) {
        Double tong = 0.0;
        for (int i = 0; i < mList.get(position).getSanpham().size(); i++) {
            tong += mList.get(position).getSanpham().get(i).getGiaBan();
        }

        return tong;
    }

}