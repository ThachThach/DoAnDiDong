package com.example.doandidong.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.FormatDouble;
import com.example.doandidong.Common.SupportFragmentDonOnline;
import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.ThongTinCuaHangOnlineActivity;
import com.example.doandidong.function.DonHangOnline.data.DonHang;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.DonChoXacNhan>{
    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private int select;
    private Dialog dialogHuy;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;


    public ChoXacNhanAdapter (Context context, ArrayList<DonHang> list, Dialog dialog, Dialog dialogHuy) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
        this.select = 0;
        this.dialogHuy = dialogHuy;
        formatDouble = new FormatDouble();
        support = new SupportFragmentDonOnline();
        thongTinCuaHangSql = new ThongTinCuaHangSql(context);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
    }

    @NonNull
    @Override
    public DonChoXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonChoXacNhan(LayoutInflater.from(context).inflate(R.layout.item_don_hang_cho_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonChoXacNhan holder, int position) {
        int k = position;
        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.CENTER, k);
            }
        });

        holder.lblHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialogHuy(Gravity.CENTER, k);
            }
        });

        if (list.get(k).getPhuongThucThanhToan() == 0){
            holder.thanhtoan.setText("Thanh toán khi nhận");
        }else {
            holder.thanhtoan.setText("Chuyển khoản");
        }
        holder.lblThoiGian.setText(support.formartDate(list.get(k).getDate()));
        holder.lblDiaChi.setText(list.get(k).getDiaChi());
        holder.lblKhachang.setText(list.get(k).getTenKhachhang());
        holder.lblDonGia.setText(formatDouble.formatStr(list.get(k).getDonGia() - list.get(k).getThunhap()));
        holder.tv_id_donhang.setText(list.get(k).getIdDonHang());
        holder.lblXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = k;
                setFirebaseXacNhanDonHang(select);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonChoXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblDiaChi, lblXacNhan, lblHuy, lblKhachang, thanhtoan,tv_id_donhang;
        private LinearLayout layoutThongTin;
        public DonChoXacNhan(@NonNull View ItemView) {
            super(ItemView);
            tv_id_donhang = ItemView.findViewById(R.id.tv_id_donhang);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            lblXacNhan = ItemView.findViewById(R.id.lblXacNhan);
            lblHuy = ItemView.findViewById(R.id.lblhuy);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
            thanhtoan = ItemView.findViewById(R.id.thanhtoan);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_don_hang_online);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tenkhachhang = dialog.findViewById(R.id.tenkhachhang);
        TextView diachi = dialog.findViewById(R.id.diachi);
        RecyclerView recycleview = dialog.findViewById(R.id.recycleview);
        TextView tongtien = dialog.findViewById(R.id.tongtien);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        Button btn_xac_nhan = dialog.findViewById(R.id.btn_xac_nhan);
        ImageView close = dialog.findViewById(R.id.close);
        TextView khuyenmai = dialog.findViewById(R.id.khuyenmai);
        TextView thanhTien = dialog.findViewById(R.id.thanhTien);
        TextView thoigian = dialog.findViewById(R.id.thoigian);

        thoigian.setText(support.formartDate(list.get(position).getDate()));
        tongtien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap() + list.get(position).getGiaKhuyenMai()));
        khuyenmai.setText(formatDouble.formatStr(list.get(position).getGiaKhuyenMai()));
        thanhTien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap()));
        tenkhachhang.setText(list.get(position).getTenKhachhang());
        diachi.setText(list.get(position).getDiaChi());


        displayItem(recycleview, dialog, position);
        Log.d("www", list.get(position).getSanpham().size()+"");
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

        btn_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebaseXacNhanDonHang(position);
                dialog.dismiss();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openFeedbackDialogHuy(int gravity, int positon) {
        dialogHuy = new Dialog(context);
        dialogHuy.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogHuy.setContentView(R.layout.dialog_huy_don);

        Window window = dialogHuy.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_dong = dialogHuy.findViewById(R.id.btn_dong);
        Button btn_huy = dialogHuy.findViewById(R.id.btn_huy);

        if (Gravity.BOTTOM == gravity) {
            dialogHuy.setCancelable(true);
        } else {
            dialogHuy.setCancelable(false);
        }

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebaseHuyDonDonHang(positon);
                dialogHuy.dismiss();
            }
        });

        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHuy.dismiss();
            }
        });

        dialogHuy.show();
    }

    private void displayItem(RecyclerView recyclerView , Dialog dialog, int position){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));

        itemDonHangAdapter = new ItemDonHangAdapter(dialog, list.get(position).getSanpham());
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }

    //TODO: setDuLieu Firebase xác nhận
    private void setFirebaseXacNhanDonHang (int position) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        Log.d("abc", list.get(position).getDate() + " - " +  list.get(position).getIdDonHang());
        mFirebaseDatabase.child("CuaHangOder/"+ ID_CUAHANG +"/donhangonline/dondadat/"+support.ngayHientai(list.get(position).getDate())+"/"+list.get(position).getIdDonHang()+"/trangthai").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Đã xác nhận đơn", Toast.LENGTH_SHORT).show();
                new SupportSaveLichSu(context, "Đã xác nhận đơn hàng online: "+ list.get(position).getIdDonHang());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xác nhận đơn không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
        list.get(position).setIdDonHang(list.get(position).getKey());
        mFirebaseDatabase.child("DonHangOnline/DaDatDon/" + list.get(position).getIdKhachhang() + "/" +list.get(position).getIdDonHang()).setValue(list.get(position));
    }

    //TODO: setDuLieu Firebase hủy đơn
    private void setFirebaseHuyDonDonHang ( int position) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("CuaHangOder/"+ ID_CUAHANG +"/donhangonline/dondadat/"+support.ngayHientai(list.get(position).getDate())+"/"+list.get(position).getIdDonHang()+"/trangthai").setValue(8).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Đã hủy đơn", Toast.LENGTH_SHORT).show();
                new SupportSaveLichSu(context, "Đã hủy nhận đơn hàng online: "+ list.get(position).getIdDonHang());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
