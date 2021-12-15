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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.DatBan.ID_datban;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.DatBan.DanhSachDatBan;
import java.util.ArrayList;

public class RvDatBanAdapter extends RecyclerView.Adapter<RvDatBanAdapter.DatBanholder> {
    private ArrayList<ID_datban> items;
    private DatabaseReference mDatabaĐanh;
    private DanhSachDatBan danhSachDatBan;
    String id_CuaHang;
    String trangthai_ban;
    private DatabaseReference mDatabase;//khai bao database
    private Dialog dialogban;
    Window window;
    TextView title;

    public RvDatBanAdapter(ArrayList<ID_datban> items, DanhSachDatBan danhSachDatBan, Window window, Dialog dialogban) {
        this.items = items;
        this.danhSachDatBan = danhSachDatBan;
        this.window = window;
        this.dialogban = dialogban;
    }

    @NonNull
    @Override
    public DatBanholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(danhSachDatBan);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listdatban, parent, false);
        DatBanholder datBanholder = new DatBanholder(view);
        return datBanholder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatBanholder holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.isclick.getLayoutParams();
        if (items.get(position).getDatBanModels().get(position).isUp()) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            holder.updownIMG.setImageResource(R.drawable.up_24);
            holder.isclick.setLayoutParams(params);
        } else {
            params.height = 0;
//            holder.updownIMG.setImageResource(R.drawable.down_24);
            holder.isclick.setLayoutParams(params);
        }
        ID_datban CrrItem = items.get(position);
        holder.tvtenkhachhang.setText(CrrItem.getDatBanModels().get(position).getTenkhachhang());
        holder.tvsodienthoai.setText(CrrItem.getDatBanModels().get(position).getSodienthoai());
        holder.tvsotiendattruoc.setText(CrrItem.getDatBanModels().get(position).getSotiendadattruoc());
        holder.tvngaydat.setText(CrrItem.getDatBanModels().get(position).getNgayhientai());
        holder.tvngaydatban.setText(CrrItem.getDatBanModels().get(position).getNgaydat());
        holder.tvgiodat.setText(CrrItem.getDatBanModels().get(position).getGiodat());
        holder.tvtenban.setText(CrrItem.getDatBanModels().get(position).getTenban());
        holder.tvgiokt.setText(CrrItem.getDatBanModels().get(position).getGioketthuc());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                danhSachDatBan.delete(position);

                notifyDataSetChanged();
            }
        });


        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HamTaodialog(Gravity.BOTTOM);
                String[] words = items.get(position).getDatBanModels().get(position).getId_bk().split("_");
                GetTrangThaiBan(position,holder,CrrItem);



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
        //            TextInputLayout tvtenkhachhang
        LinearLayout click, isclick;
        ImageButton cancel, check;
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
            cancel = itemView.findViewById(R.id.cancel);
            check = itemView.findViewById(R.id.check);
            click = itemView.findViewById(R.id.click);
            isclick = itemView.findViewById(R.id.isclick);
            params = (LinearLayout.LayoutParams) isclick.getLayoutParams();
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (items.get(position).getDatBanModels().get(position).isUp()) {
                        params.height = 0;
                        items.get(position).getDatBanModels().get(position).setUp(false);
//                        updownIMG.setImageResource(R.drawable.down_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    } else {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        items.get(position).getDatBanModels().get(position).setUp(true);
//                        updownIMG.setImageResource(R.drawable.up_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    }
                }
            });

        }


    }

    public void GetTrangThaiBan(int position,DatBanholder holder,ID_datban CrrItem) {
        String[] words = items.get(position).getDatBanModels().get(position).getId_bk().split("_");
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(words[1]).child("ban").child(words[0]);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    trangthai_ban = snapshot.child("trangthai").getValue() + "";
                    if (trangthai_ban.equals("2")) {
                        title.setText("Bàn đang bận!!!");
                        dialogban.show();
                        holder.check.setEnabled(true);

                        Toast.makeText(danhSachDatBan, "setEnabled", Toast.LENGTH_SHORT).show();
                    }
                    else if (trangthai_ban.equals("3")) {
                        title.setText("Bàn đang bận!!!");
                        dialogban.show();
                        holder.check.setEnabled(true);

                        Toast.makeText(danhSachDatBan, "setEnabled", Toast.LENGTH_SHORT).show();
                    }
                    else if (trangthai_ban.equals("4")) {
                        title.setText("Bàn đang bận!!!");
                        dialogban.show();
                        holder.check.setEnabled(true);

                        Toast.makeText(danhSachDatBan, "setEnabled", Toast.LENGTH_SHORT).show();
                    }
                    else if (trangthai_ban.equals("5")) {
                        title.setText("Bàn đang bận!!!");
                        dialogban.show();
                        holder.check.setEnabled(true);

                        Toast.makeText(danhSachDatBan, "setEnabled", Toast.LENGTH_SHORT).show();
                    }
                    else if (trangthai_ban.equals("6")) {
                        title.setText("Bàn đang bận!!!");
                        dialogban.show();
                        holder.check.setEnabled(true);

                        Toast.makeText(danhSachDatBan, "setEnabled", Toast.LENGTH_SHORT).show();
                    }else {
                        title.setText("Đã Chuyển Sang Trang Thái Đặt!!!");
                        dialogban.show();
                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(words[1]).child("ban").child(words[0]).child("trangthai").setValue("4");
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(words[0] + "_" + words[1]).child(CrrItem.getDatBanModels().get(position).getId_ngaydat()).child("trangthai").setValue("1");
                        holder.check.setBackgroundResource(R.color.bac);
                        Toast.makeText(danhSachDatBan, "trangthai1", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void HamTaodialog(int gravity) {
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
        Button Okay = dialogban.findViewById(R.id.btn_cancel);
        title = dialogban.findViewById(R.id.title);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
            }
        });

    }


}
