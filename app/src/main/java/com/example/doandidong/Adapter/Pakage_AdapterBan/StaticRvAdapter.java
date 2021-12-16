package com.example.doandidong.Adapter.Pakage_AdapterBan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.Data.ChucNangThanhToan.ProductPushFB;
import com.example.doandidong.Data.ChucNangThanhToan.ProuductPushFB1;
import com.example.doandidong.Data.DatBan.DatBanModel;
import com.example.doandidong.SQL.Database_order;
import com.example.doandidong.ChucNang.DatBan.DanhSachDatBan;
import com.example.doandidong.ChucNang.DatBan.DatBan;
import com.example.doandidong.ChucNang.ThanhToanActivity;
import com.example.doandidong.ChucNang.MonOrder;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.OrderMenu;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRvHolderBan> {
    private OrderMenu orderMenu;
    public ArrayList<StaticBanModel> staticBanModels;
    ArrayList<StaticModelKhuVuc> items;

    private ArrayList<ProductPushFB> ListDate_yc = new ArrayList<>();
    String Id_khuvuc;
    private Dialog dialogban;
    Window window;
    TextView datban, listdatban, hoantac, gopban;
    String trangthaigop;
    String id_ban_thanhtoan;
    String id_khuvuc_thanhtoan;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase3, mDatabase_datban;
    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";
    ArrayList<ProuductPushFB1> prouductPushFB1;
    ArrayList<ProductPushFB> productPushFBS;
    ProductPushFB ProductTachBan;
    String id_ban_tachban;
    String id_khuvuc_tachban;
    ArrayList<ProuductPushFB1> carsListsaukhichon;
    String trangthai_tachBan;
    private String trangthaichucnang;
    String code_chucnang;
    String id_CuaHang;
    String id_bk, id_ne, id_ngaydat, id_bk1;
    String abc;
    private ArrayList<ProuductPushFB1> listmon;
    ArrayList<DatBanModel> datBanModels, datBanModel1;
    String TrangThaiBan_doimau,TrangThaiBan_doimau_gop;
    String tennhanvien;
    private String trangThaiKV;
    public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels, OrderMenu orderMenu, ArrayList<StaticModelKhuVuc> items, String Id_khuvuc) {
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;


    }

    public StaticRvAdapter(ArrayList<StaticBanModel> staticBanModels, OrderMenu orderMenu, ArrayList<StaticModelKhuVuc> items, String Id_khuvuc, Window window, Dialog dialogban, String trangthaigop, String id_ban_thanhtoan,
                           String id_khuvuc_thanhtoan, ArrayList<ProuductPushFB1> prouductPushFB1, ArrayList<ProductPushFB> productPushFBS, ProductPushFB ProductTachBan, ArrayList<ProuductPushFB1> carsListsaukhichon, String id_ban_tachban, String id_khuvuc_tachban,
                           String trangthaichucnang, String code_chucnang, ArrayList<DatBanModel> datBanModels, String trangThai
    ) {
        this.staticBanModels = staticBanModels;
        this.orderMenu = orderMenu;
        this.items = items;
        this.Id_khuvuc = Id_khuvuc;
        this.window = window;
        this.dialogban = dialogban;
        this.trangthaigop = trangthaigop;
        this.id_ban_thanhtoan = id_ban_thanhtoan;
        this.id_khuvuc_thanhtoan = id_khuvuc_thanhtoan;
        this.prouductPushFB1 = prouductPushFB1;
        this.productPushFBS = productPushFBS;
        this.ProductTachBan = ProductTachBan;
        this.carsListsaukhichon = carsListsaukhichon;
        this.id_ban_tachban = id_ban_tachban;
        this.id_khuvuc_tachban = id_khuvuc_tachban;
        this.trangthaichucnang = trangthaichucnang;
        this.code_chucnang = code_chucnang;
        this.datBanModels = datBanModels;
        this.trangThaiKV = trangThai;
        if (datBanModels != null) {
            Log.d("datBanModelskkka", datBanModels.size() + "adapterOrdermenu");
        }

    }

    public class StaticRvHolderBan extends RecyclerView.ViewHolder {

//

        public TextView tenPhucVu;
        public TextView tenBan;
        public TextView ngayGio;
        public TextView trangThai;
        ImageView bacham;
        LinearLayout cardview_ban;
        CardView constraintLayout;

        public StaticRvHolderBan(@NonNull View itemView) {
            super(itemView);
            cardview_ban = itemView.findViewById(R.id.cardview_ban);
            constraintLayout = itemView.findViewById(R.id.constraintLayouts);
            tenBan = itemView.findViewById(R.id.tvtenban);
        }
    }

    @NonNull
    @Override
    public StaticRvHolderBan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ban, parent, false);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(orderMenu);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        tennhanvien = thongTinCuaHangSql.selectUser().getUsername();
        StaticRvHolderBan staticRvHolderBan = new StaticRvHolderBan(view);
        getDatasql();
        return staticRvHolderBan;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvHolderBan holder, int position) {
        StaticBanModel CrrItem = staticBanModels.get(position);
        Log.d("khanhkhanh",CrrItem.getID());
        Log.d("khanhkhanh",Id_khuvuc+"odkhuvuc");
        holder.tenBan.setText(CrrItem.getTenban());
        //ban hu

        if (staticBanModels.get(position).getTrangthai().equals("3")) {
            holder.cardview_ban.setBackgroundResource(R.color.red);
            holder.constraintLayout.setEnabled(false);

        }
        //da order nhung chua co mon
        if (staticBanModels.get(position).getTrangthai().equals("2")) {
            holder.cardview_ban.setBackgroundResource(R.color.link);


        }
        //da dat ban
        if (staticBanModels.get(position).getTrangthai().equals("4")) {
            holder.cardview_ban.setBackgroundResource(R.color.Java);


        }
        //da order cho lay
        if (staticBanModels.get(position).getTrangthai().equals("5")) {
            holder.cardview_ban.setBackgroundResource(R.color.brown_soc);


        }
        //dang an
        if (staticBanModels.get(position).getTrangthai().equals("6")) {
            holder.cardview_ban.setBackgroundResource(R.color.so_hoa_don);
        }

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(orderMenu,"ban",Toast.LENGTH_LONG).show();
                getData(CrrItem);


            }
        });

//        }else {
////            holder.cardview_ban.setBackgroundResource(R.color.red);
////            holder.constraintLayout.setEnabled(false);
////        }



    }

    //chuyeenr doii String sang ngay
    public String changeDate(String date) {
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if (dates == 0) {
            return "";
        }
        Date date1 = new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        Log.d("simpleDateFormat", aaa + "");
        return aaa;

    }

    @Override
    public int getItemCount() {
        if (staticBanModels != null) {
            return staticBanModels.size();
        }
        return 0;

    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    public void getData(StaticBanModel CrrItem) {
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    Intent intent = new Intent(orderMenu, ThanhToanActivity.class);
                    intent.putExtra("id_ban", CrrItem.getID());
                    intent.putExtra("id_khuvuc", Id_khuvuc);
                    orderMenu.startActivity(intent);
                } else {
                    Intent intent = new Intent(orderMenu, MonOrder.class);
                    intent.putExtra("id_ban", CrrItem.getID());
                    intent.putExtra("id_khuvuc", Id_khuvuc);
                    orderMenu.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDatasql() {

        database_order = new Database_order(orderMenu, "app_database.sqlite", null, 2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
    }

}
