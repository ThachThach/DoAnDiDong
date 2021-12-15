package com.example.doandidong.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.FormatDouble;
import com.example.doandidong.Common.SupportFragmentDonOnline;
import com.example.doandidong.R;
import com.example.doandidong.function.DonHangOnline.data.DonHang;
import com.example.doandidong.function.DonHangOnline.data.SanPham;
import java.util.ArrayList;

public class DonHangHoanThanhAdapter extends RecyclerView.Adapter<DonHangHoanThanhAdapter.DonHoanThanh>{
    private Context context;
    private ArrayList<DonHang> list;
    private ItemDonHangDangGiaoAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support;


    public DonHangHoanThanhAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        formatDouble = new FormatDouble();
        support = new SupportFragmentDonOnline();
    }

    @NonNull
    @Override
    public DonHoanThanh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangHoanThanhAdapter.DonHoanThanh(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonHoanThanh holder, int position) {
        holder.trangthaidonhang.setText("Đơn hàng hoàn thành");
        holder.lblThoiGian.setText(support.formartDate(list.get(position).getDate()));
        holder.lblDonGia.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap()));
        holder.lblKhachang.setText(list.get(position).getTenKhachhang());
        holder.lblDiaChi.setText(list.get(position).getDiaChi());
        holder.tv_id_donhang.setText(list.get(position).getIdDonHang());
        holder.nguoiThucHien.setText(list.get(position).getNhanVien());

        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.CENTER, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonHoanThanh extends RecyclerView.ViewHolder {
        private TextView trangthaidonhang, nguoiThucHien, lblDonGia, lblThoiGian, lblKhachang, lblDiaChi, tv_id_donhang;
        private LinearLayout layoutThongTin;
        public DonHoanThanh(@NonNull View ItemView) {
            super(ItemView);
            trangthaidonhang = ItemView.findViewById(R.id.trangthaidonhang);
            nguoiThucHien = ItemView.findViewById(R.id.nguoiThucHien);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            tv_id_donhang = ItemView.findViewById(R.id.tv_id_donhang);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_don_hang_da_xac_nhan);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tenkhachhang = dialog.findViewById(R.id.tenkhachhang);
        TextView diachi = dialog.findViewById(R.id.diachi);
        TextView khuyenmai = dialog.findViewById(R.id.khuyenmai);
        RecyclerView recycleview = dialog.findViewById(R.id.recycleview);
        TextView tongtien = dialog.findViewById(R.id.tongtien);
        ImageView close = dialog.findViewById(R.id.close);
        TextView thanhTien = dialog.findViewById(R.id.thanhTien);
        TextView thoigian = dialog.findViewById(R.id.thoigian);

        thoigian.setText(support.formartDate(list.get(position).getDate()));
        tenkhachhang.setText(list.get(position).getTenKhachhang());
        diachi.setText(list.get(position).getDiaChi());
        tongtien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap() + list.get(position).getGiaKhuyenMai()));
        khuyenmai.setText(formatDouble.formatStr(list.get(position).getGiaKhuyenMai()));
        thanhTien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap()));

        displayItem(recycleview, dialog, list.get(position).getSanpham());

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void displayItem(RecyclerView recyclerView , Dialog dialog, ArrayList<SanPham> sanPhams){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));

        itemDonHangAdapter = new ItemDonHangDangGiaoAdapter(dialog, sanPhams);
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }
}