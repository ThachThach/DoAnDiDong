package com.example.doandidong.Adapter.Package_AdapterDatBan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.DatBan.DatBanModel;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.DatBan.XacNhanDatBan;
import java.util.ArrayList;

public class DatBanOnlineAdapter extends RecyclerView.Adapter<DatBanOnlineAdapter.DatBanholder> {
    private ArrayList<DatBanModel> items;
    private DatabaseReference mDatabase1;
    private XacNhanDatBan danhSachDatBan;
    private String id_CuaHang;
    private DatabaseReference mDatabase;
    private Dialog dialogban;
    private Window window;
    private Dialog dialog1;
    private Window window1;


    public DatBanOnlineAdapter(ArrayList<DatBanModel> items, XacNhanDatBan danhSachDatBan,Dialog dialogban,Dialog dialog1,Window window,Window window1) {
        this.items = items;
        this.danhSachDatBan = danhSachDatBan;
        this.dialogban = dialogban;
        this.dialog1 = dialog1;
        this.window = window;
        this.window1 = window1;
    }

    @NonNull
    @Override
    public DatBanholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(danhSachDatBan);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datbanonline, parent, false);
        DatBanholder datBanholder = new DatBanholder(view);
        return datBanholder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatBanholder holder, int position) {

        DatBanModel CrrItem = items.get(position);
        holder.tvtenkhachhang.setText(CrrItem.getTenkhachhang());
        holder.tvsodienthoai.setText(CrrItem.getSodienthoai());
        holder.tvsotiendattruoc.setText(CrrItem.getSotiendadattruoc());
        holder.tvngaydat.setText(CrrItem.getNgayhientai());
        holder.tvngaydatban.setText(CrrItem.getNgaydat());
        holder.tvgiodat.setText(CrrItem.getGiodat());
        holder.tvtenban.setText(CrrItem.getTenban());
        holder.tvgiokt.setText(CrrItem.getGioketthuc());
        holder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHuy(Gravity.CENTER,position);
                notifyDataSetChanged();
            }
        });
        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogNhan(Gravity.CENTER,position);
                notifyDataSetChanged();
            }
        });





    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public class DatBanholder extends RecyclerView.ViewHolder {
        TextView tvtenkhachhang, tvsodienthoai, tvsotiendattruoc, tvngaydat, tvtenban, tvngaydatban, tvgiodat, tvgiokt;
        LinearLayout click, isclick;
        TextView btnHuy,btnXacNhan;

        private LinearLayout.LayoutParams params;

        public DatBanholder(@NonNull View itemView) {
            super(itemView);
            tvtenkhachhang = itemView.findViewById(R.id.tvtenkhachhang);
            tvsodienthoai = itemView.findViewById(R.id.tvsodienthoai);
            tvsotiendattruoc = itemView.findViewById(R.id.tvsotiendattruoc);
            tvngaydat = itemView.findViewById(R.id.tvngaydat);
            tvtenban = itemView.findViewById(R.id.tvtenban);
            tvngaydatban = itemView.findViewById(R.id.tvngaydatban);
            tvgiodat = itemView.findViewById(R.id.tvgiodat);
            tvgiokt = itemView.findViewById(R.id.tvgiokt);
            click = itemView.findViewById(R.id.click);
            isclick = itemView.findViewById(R.id.isclick);
            params = (LinearLayout.LayoutParams) isclick.getLayoutParams();
            btnHuy = itemView.findViewById(R.id.lblhuy);
            btnXacNhan = itemView.findViewById(R.id.lblHangCho);

            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (items.get(position).isUp()) {
                        params.height = 0;
                        items.get(position).setUp(false);
//                        updownIMG.setImageResource(R.drawable.down_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    } else {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        items.get(position).setUp(true);
//                        updownIMG.setImageResource(R.drawable.up_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }

    private void HuyDon(int position){
        mDatabase =FirebaseDatabase.getInstance().getReference("DuyetDatBan");
        mDatabase.child(items.get(position).getKey_khachhang()).child(items.get(position).getId_bk()).child(items.get(position).getId()).child("trangthai_dat").setValue("2");
    }

    private void NhanDon(int position){
        mDatabase = FirebaseDatabase.getInstance().getReference("DuyetDatBan");
        mDatabase1 =  FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(items.get(position).getId_bk()).child(items.get(position).getId());
        mDatabase.child(items.get(position).getKey_khachhang()).child(items.get(position).getId_bk()).child(items.get(position).getId()).child("trangthai_dat").setValue("1");
        mDatabase1.child("tenkhachhang").setValue(items.get(position).getTenkhachhang());
        mDatabase1.child("id_bk").setValue(items.get(position).getId_bk());
        mDatabase1.child("sodienthoai").setValue(items.get(position).getSodienthoai());
        mDatabase1.child("sotiendattruoc").setValue(items.get(position).getSotiendadattruoc());
        mDatabase1.child("ngayhientai").setValue(items.get(position).getNgayhientai());
        mDatabase1.child("ngaydat").setValue(items.get(position).getNgaydat());
        mDatabase1.child("giodat").setValue(items.get(position).getGiodat());
        mDatabase1.child("tenban").setValue(items.get(position).getTenban());
        mDatabase1.child("trangthai").setValue(items.get(position).getTrangthai());
        mDatabase1.child("gioketthuc").setValue(items.get(position).getGioketthuc());
    }

    private void DialogHuy(int gravity,int position) {

        Button cancel = dialogban.findViewById(R.id.btn_cancel);
        Button chapnhan = dialogban.findViewById(R.id.btn_ok);
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialogban.setCancelable(true);
        } else {
            dialogban.setCancelable(false);
        }



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
            }
        });
        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuyDon(position);
                dialogban.dismiss();
            }
        });
        dialogban.show();

    }

    private void DialogNhan(int gravity,int position){

        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }
        Button cancel = dialog1.findViewById(R.id.btn_khong);
        Button chapnhan = dialog1.findViewById(R.id.btn_co);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanDon(position);
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }
}
