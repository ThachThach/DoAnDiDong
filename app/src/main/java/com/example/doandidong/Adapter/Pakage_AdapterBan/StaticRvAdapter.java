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
            tenPhucVu = itemView.findViewById(R.id.tvtenphucvu);
            tenBan = itemView.findViewById(R.id.tvtenban);
            ngayGio = itemView.findViewById(R.id.tvngaygio);
            trangThai = itemView.findViewById(R.id.tvtrangthai);
            bacham = itemView.findViewById(R.id.bacham);
//            view = itemView.findViewById(R.id.view2);
            cardview_ban = itemView.findViewById(R.id.cardview_ban);
            constraintLayout = itemView.findViewById(R.id.constraintLayouts);
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
        holder.tenPhucVu.setText(CrrItem.getTenNhanVien());
        holder.tenBan.setText(CrrItem.getTenban());
        holder.ngayGio.setText(changeDate(CrrItem.getGioDaOder()));
        holder.trangThai.setText(CrrItem.getTrangthai());
        //ban hu

        if (!trangThaiKV.equals("3")) {
            if (staticBanModels.get(position).getTrangthai().equals("3")) {
                holder.cardview_ban.setBackgroundResource(R.color.red);
                holder.constraintLayout.setEnabled(false);

            }
            //da order nhung chua co mon
            if (staticBanModels.get(position).getTrangthai().equals("2")) {
                holder.cardview_ban.setBackgroundResource(R.color.maudat);


            }
            //da dat ban
            if (staticBanModels.get(position).getTrangthai().equals("4")) {
                holder.cardview_ban.setBackgroundResource(R.color.bac);


            }
            //da order cho lay
            if (staticBanModels.get(position).getTrangthai().equals("5")) {
                holder.cardview_ban.setBackgroundResource(R.color.xanh);


            }
            //dang an
            if (staticBanModels.get(position).getTrangthai().equals("6")) {
                holder.cardview_ban.setBackgroundResource(R.color.purple_200);


            }
            if (trangthaichucnang != null) {
                if (trangthaichucnang.equals("1") ||trangthaichucnang.equals("2") ) {
                    if ((CrrItem.getID() + "_" + Id_khuvuc).equals(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan)) {
                        holder.constraintLayout.setVisibility(View.GONE);
                    }
                }

            }
            if (trangthaichucnang != null) {
                if (trangthaichucnang.equals("3") ) {
                    if ((CrrItem.getID() + "_" + Id_khuvuc).equals(id_ban_tachban + "_" + id_khuvuc_tachban)) {
                        holder.constraintLayout.setVisibility(View.GONE);
                    }
                }

            }

            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData(CrrItem);

                }
            });

            holder.bacham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HamTaodialog(Gravity.BOTTOM, CrrItem);
                    Toast.makeText(orderMenu, "bacham", Toast.LENGTH_LONG).show();

                }
            });
        }else {
            holder.cardview_ban.setBackgroundResource(R.color.red);
            holder.constraintLayout.setEnabled(false);
        }



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
                    listmon = new ArrayList<ProuductPushFB1>();
                    DataSnapshot sss = snapshot.child("sanpham");
                    for (DataSnapshot postSnapshot : sss.getChildren()) {
                        String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                        int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                        String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                        Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
                        String Loai = postSnapshot.child("loai").getValue() + "";
                        String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
                        listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                    }
                }
                if (trangthaichucnang != null) {
                    if (trangthaichucnang.equals("1")) {
                        new AlertDialog.Builder(orderMenu).setMessage(
                                "bạn có muốn gộp bàn không"
                        ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                                String Id = CrrItem.getID() + "_" + Id_khuvuc;
                                mDatabase_datban = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
                                mDatabase_datban.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.getValue() != null) {
                                            datBanModel1 = new ArrayList<>();
                                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                DataSnapshot sss = postSnapshot;
                                                for (DataSnapshot aaa : sss.getChildren()) {
                                                    id_bk1 = aaa.child("id_bk").getValue() + "";
                                                    if (id_bk1.equals(Id)) {
                                                        if ((aaa.child("trangthai").getValue() + "").equals("1")) {
                                                            String id_ngaydat1 = aaa.getKey();
                                                            String giodat = aaa.child("giodat").getValue() + "";
                                                            String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                                            String ngaydat = aaa.child("ngaydat").getValue() + "";
                                                            String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                                            String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                                            String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                                            String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                                            String tenban = aaa.child("tenban").getValue() + "";
                                                            String trangthaidat = aaa.child("trangthai").getValue() + "";
                                                            datBanModel1.add(new DatBanModel(tenban, id_ngaydat1, giodat, gioketthuc, id_bk1, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang, trangthaidat));
                                                        }
                                                    }
                                                }
                                            }
                                            if (datBanModels.size() > 0) {
                                                Log.d("datBanModel1datban", datBanModel1.size() + "size1>0");
                                                Log.d("datBanModel1datban", datBanModels.size() + "sizes>0");
                                                if (datBanModel1.size() > 0) {
                                                    Log.d("datBanModel1datban", "if11");
                                                    int adds = prouductPushFB1.size();

                                                    for (int i = 0; i < adds; i++) {
                                                        for (int x = 0; x < listmon.size(); x++) {
                                                            if (prouductPushFB1.get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && prouductPushFB1.get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                                                prouductPushFB1.get(i).setSoluong(prouductPushFB1.get(i).getSoluong() + listmon.get(x).getSoluong());
                                                            } else {
                                                                prouductPushFB1.add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                                            }
                                                        }

                                                    }
                                                    double tien_bangop = Double.parseDouble(datBanModel1.get(0).getSotiendadattruoc());
                                                    double tien_banbigop = Double.parseDouble(datBanModels.get(0).getSotiendadattruoc());
                                                    double tongtien = tien_bangop + tien_banbigop;
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(id).child(datBanModels.get(0).getId_ngaydat()).removeValue();
                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModel1.get(0).getId_ngaydat());
                                                    databaseReference.child("tenkhachhang").setValue(datBanModel1.get(0).getTenkhachhang());
                                                    databaseReference.child("id_bk").setValue(Id);
                                                    databaseReference.child("sodienthoai").setValue(datBanModel1.get(0).getSodienthoai());
                                                    databaseReference.child("sotiendattruoc").setValue(tongtien);
                                                    databaseReference.child("ngayhientai").setValue(datBanModel1.get(0).getNgayhientai());
                                                    databaseReference.child("ngaydat").setValue(datBanModel1.get(0).getNgaydat());
                                                    databaseReference.child("giodat").setValue(datBanModel1.get(0).getGiodat());
                                                    databaseReference.child("tenban").setValue(datBanModel1.get(0).getTenban());
                                                    databaseReference.child("trangthai").setValue(datBanModel1.get(0).getTrangthai());
                                                    databaseReference.child("gioketthuc").setValue(datBanModel1.get(0).getGioketthuc());
                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").setValue(prouductPushFB1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            new SupportSaveLichSu(orderMenu,"Gộp Bàn:"+id+"Đến Bàn"+Id);

                                                        }
                                                    });
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("tenNhanVien").setValue("");
//                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);
                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                                                    database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();

                                                } else {
                                                    int adds = prouductPushFB1.size();

                                                    for (int i = 0; i < adds; i++) {
                                                        for (int x = 0; x < listmon.size(); x++) {
                                                            if (prouductPushFB1.get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && prouductPushFB1.get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                                                prouductPushFB1.get(i).setSoluong(prouductPushFB1.get(i).getSoluong() + listmon.get(x).getSoluong());
                                                            } else {
                                                                prouductPushFB1.add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                                            }
                                                        }

                                                    }
                                                    Log.d("datBanModel1datban", "10");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(id).child(datBanModels.get(0).getId_ngaydat()).removeValue();
                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModels.get(0).getId_ngaydat());
                                                    databaseReference.child("tenkhachhang").setValue(datBanModels.get(0).getTenkhachhang());
                                                    databaseReference.child("id_bk").setValue(Id);
                                                    databaseReference.child("sodienthoai").setValue(datBanModels.get(0).getSodienthoai());
                                                    databaseReference.child("sotiendattruoc").setValue(datBanModels.get(0).getSotiendadattruoc());
                                                    databaseReference.child("ngayhientai").setValue(datBanModels.get(0).getNgayhientai());
                                                    databaseReference.child("ngaydat").setValue(datBanModels.get(0).getNgaydat());
                                                    databaseReference.child("giodat").setValue(datBanModels.get(0).getGiodat());
                                                    databaseReference.child("tenban").setValue(datBanModels.get(0).getTenban());
                                                    databaseReference.child("trangthai").setValue(datBanModels.get(0).getTrangthai());
                                                    databaseReference.child("gioketthuc").setValue(datBanModels.get(0).getGioketthuc());
                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").setValue(prouductPushFB1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            new SupportSaveLichSu(orderMenu,"Gộp Bàn:"+id+"Đến Bàn"+Id);

                                                        }
                                                    });
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("tenNhanVien").setValue("");
//                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);
                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                                                    database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
                                                }

                                            } else  {
                                                int adds = prouductPushFB1.size();

                                                for (int i = 0; i < adds; i++) {
                                                    for (int x = 0; x < listmon.size(); x++) {
                                                        if (prouductPushFB1.get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && prouductPushFB1.get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                                            prouductPushFB1.get(i).setSoluong(prouductPushFB1.get(i).getSoluong() + listmon.get(x).getSoluong());
                                                        } else {
                                                            prouductPushFB1.add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                                        }
                                                    }

                                                }
                                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").setValue(prouductPushFB1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        new SupportSaveLichSu(orderMenu,"Gộp Bàn:"+id+"Đến Bàn"+Id);

                                                    }
                                                });
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("tenNhanVien").setValue("");
//                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);
                                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                                                database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }).setNegativeButton("No", null)
                                .show();


                    }
                    else if (trangthaichucnang.equals("2")) {
                        gettrangthaiban();
                        new AlertDialog.Builder(orderMenu).setMessage(
                                "bạn có muốn Chuyển bàn không"
                        ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//khanh
                                if (TrangThaiBan_doimau != null) {
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue(TrangThaiBan_doimau);
                                } else {
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("2");
                                }
                                String Id = CrrItem.getID() + "_" + Id_khuvuc;
                                String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                                for (int i = 0; i < productPushFBS.size(); i++) {
                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).setValue(productPushFBS.get(i)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            new SupportSaveLichSu(orderMenu,"Chuyển Bàn:"+id+"Đến Bàn"+Id);
                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("gioDaOder").setValue(productPushFBS.get(i).getDate());
                                }
                                if (datBanModels.size() > 0) {
                                    Log.d("datBanModelskkka", datBanModels.size() + "chuyenbanadapter");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(id).child(datBanModels.get(0).getId_ngaydat()).removeValue();
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModels.get(0).getId_ngaydat());
                                    databaseReference.child("tenkhachhang").setValue(datBanModels.get(0).getTenkhachhang());
                                    databaseReference.child("id_bk").setValue(Id);
                                    databaseReference.child("sodienthoai").setValue(datBanModels.get(0).getSodienthoai());
                                    databaseReference.child("sotiendattruoc").setValue(datBanModels.get(0).getSotiendadattruoc());
                                    databaseReference.child("ngayhientai").setValue(datBanModels.get(0).getNgayhientai());
                                    databaseReference.child("ngaydat").setValue(datBanModels.get(0).getNgaydat());
                                    databaseReference.child("giodat").setValue(datBanModels.get(0).getGiodat());
                                    databaseReference.child("tenban").setValue(datBanModels.get(0).getTenban());
                                    databaseReference.child("trangthai").setValue(datBanModels.get(0).getTrangthai());
                                    databaseReference.child("gioketthuc").setValue(datBanModels.get(0).getGioketthuc());
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                    database_order.QueryData("UPDATE " + TEN_BANG + " SET Id = '" + Id + "' WHERE Id= '" + id + "'");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("tenNhanVien").setValue("");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);

                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                                    database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
                                    notifyDataSetChanged();
                                } else {
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                    database_order.QueryData("UPDATE " + TEN_BANG + " SET Id = '" + Id + "' WHERE Id= '" + id + "'");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("tenNhanVien").setValue("");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").setValue("1");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("gioDaOder").setValue(0);
                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan).removeValue();
                                    database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
                                    notifyDataSetChanged();
                                }
                            }
                        }).setNegativeButton("No", null)
                                .show();
                    }
                    else if (trangthaichucnang.equals("3")) {
                        gettrangthaibandegopban();
                        new AlertDialog.Builder(orderMenu).setMessage(
                                "bạn chắc chắn tách món vào bàn này không"
                        ).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase3 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID());
                                mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        trangthai_tachBan = snapshot.child("trangthai").getValue() + "";
                                        if (trangthai_tachBan.equals("2")) {

                                            String ids = id_ban_tachban + "_" + id_khuvuc_tachban;
                                            int adds = ProductTachBan.getSanpham().size();
                                            for (int i = 0; i < adds; i++) {
                                                for (int x = 0; x < listmon.size(); x++) {
                                                    if (ProductTachBan.getSanpham().get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && ProductTachBan.getSanpham().get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                                        ProductTachBan.getSanpham().get(i).setSoluong(ProductTachBan.getSanpham().get(i).getSoluong() + listmon.get(x).getSoluong());
                                                    } else {
                                                        ProductTachBan.getSanpham().add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                                    }
                                                }
                                            }
                                            FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).child("sanpham").setValue(ProductTachBan.getSanpham()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    new SupportSaveLichSu(orderMenu,"Tách Món:"+ids+"Đến Bàn"+CrrItem.getID() + "_" + Id_khuvuc);
                                                }
                                            });
                                            if (carsListsaukhichon.size() == 0) {
                                                String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                                                String Id = CrrItem.getID() + "_" + Id_khuvuc;
                                                mDatabase_datban = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
                                                mDatabase_datban.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (snapshot.getValue() != null) {
                                                            datBanModel1 = new ArrayList<>();
                                                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                                DataSnapshot sss = postSnapshot;
                                                                for (DataSnapshot aaa : sss.getChildren()) {
                                                                    id_bk1 = aaa.child("id_bk").getValue() + "";
                                                                    if (id_bk1.equals(Id)) {
                                                                        if ((aaa.child("trangthai").getValue() + "").equals("1")) {
                                                                            String id_ngaydat1 = aaa.getKey();
                                                                            String giodat = aaa.child("giodat").getValue() + "";
                                                                            String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                                                            String ngaydat = aaa.child("ngaydat").getValue() + "";
                                                                            String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                                                            String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                                                            String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                                                            String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                                                            String tenban = aaa.child("tenban").getValue() + "";
                                                                            String trangthaidat = aaa.child("trangthai").getValue() + "";
                                                                            datBanModel1.add(new DatBanModel(tenban, id_ngaydat1, giodat, gioketthuc, id_bk1, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang, trangthaidat));

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (datBanModels.size() > 0) {
                                                                if (datBanModel1.size() > 0) {
                                                                    double tien_bangop = Double.parseDouble(datBanModel1.get(0).getSotiendadattruoc());
                                                                    double tien_banbigop = Double.parseDouble(datBanModels.get(0).getSotiendadattruoc());
                                                                    double tongtien = tien_bangop + tien_banbigop;
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(ids).child(datBanModels.get(0).getId_ngaydat()).removeValue();
                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModel1.get(0).getId_ngaydat());
                                                                    databaseReference.child("tenkhachhang").setValue(datBanModel1.get(0).getTenkhachhang());
                                                                    databaseReference.child("id_bk").setValue(Id);
                                                                    databaseReference.child("sodienthoai").setValue(datBanModel1.get(0).getSodienthoai());
                                                                    databaseReference.child("sotiendattruoc").setValue(tongtien);
                                                                    databaseReference.child("ngayhientai").setValue(datBanModel1.get(0).getNgayhientai());
                                                                    databaseReference.child("ngaydat").setValue(datBanModel1.get(0).getNgaydat());
                                                                    databaseReference.child("giodat").setValue(datBanModel1.get(0).getGiodat());
                                                                    databaseReference.child("tenban").setValue(datBanModel1.get(0).getTenban());
                                                                    databaseReference.child("trangthai").setValue(datBanModel1.get(0).getTrangthai());
                                                                    databaseReference.child("gioketthuc").setValue(datBanModel1.get(0).getGioketthuc());
                                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                                } else {
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(ids).child(datBanModels.get(0).getId_ngaydat()).removeValue();
                                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModels.get(0).getId_ngaydat());
                                                                    databaseReference.child("tenkhachhang").setValue(datBanModels.get(0).getTenkhachhang());
                                                                    databaseReference.child("id_bk").setValue(Id);
                                                                    databaseReference.child("sodienthoai").setValue(datBanModels.get(0).getSodienthoai());
                                                                    databaseReference.child("sotiendattruoc").setValue(datBanModels.get(0).getSotiendadattruoc());
                                                                    databaseReference.child("ngayhientai").setValue(datBanModels.get(0).getNgayhientai());
                                                                    databaseReference.child("ngaydat").setValue(datBanModels.get(0).getNgaydat());
                                                                    databaseReference.child("giodat").setValue(datBanModels.get(0).getGiodat());
                                                                    databaseReference.child("tenban").setValue(datBanModels.get(0).getTenban());
                                                                    databaseReference.child("trangthai").setValue(datBanModels.get(0).getTrangthai());
                                                                    databaseReference.child("gioketthuc").setValue(datBanModels.get(0).getGioketthuc());
                                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                                }

                                                            } else {
                                                                if (datBanModel1.size() > 0) {
                                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                                } else {
                                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                                }

                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            } else {

                                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).child("sanpham").setValue(carsListsaukhichon);

                                            }
                                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();

                                        }
                                        if (trangthai_tachBan.equals("1")) {
                                            String id = id_ban_thanhtoan + "_" + id_khuvuc_thanhtoan;
                                            String Id = CrrItem.getID() + "_" + Id_khuvuc;
                                            String ids = id_ban_tachban + "_" + id_khuvuc_tachban;
                                            FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(CrrItem.getID() + "_" + Id_khuvuc).setValue(ProductTachBan).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    new SupportSaveLichSu(orderMenu,"Gộp Tách Bàn:"+ids+"Đến Bàn"+CrrItem.getID() + "_" + Id_khuvuc);
                                                }
                                            });
                                            if (carsListsaukhichon.size() == 0) {
                                                if (datBanModels.size() > 0) {
                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(Id).child(datBanModels.get(0).getId_ngaydat());
                                                    databaseReference.child("tenkhachhang").setValue(datBanModels.get(0).getTenkhachhang());
                                                    databaseReference.child("id_bk").setValue(Id);
                                                    databaseReference.child("sodienthoai").setValue(datBanModels.get(0).getSodienthoai());
                                                    databaseReference.child("sotiendattruoc").setValue(datBanModels.get(0).getSotiendadattruoc());
                                                    databaseReference.child("ngayhientai").setValue(datBanModels.get(0).getNgayhientai());
                                                    databaseReference.child("ngaydat").setValue(datBanModels.get(0).getNgaydat());
                                                    databaseReference.child("giodat").setValue(datBanModels.get(0).getGiodat());
                                                    databaseReference.child("tenban").setValue(datBanModels.get(0).getTenban());
                                                    databaseReference.child("trangthai").setValue(datBanModels.get(0).getTrangthai());
                                                    databaseReference.child("gioketthuc").setValue(datBanModels.get(0).getGioketthuc());
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(ids).child(datBanModels.get(0).getId_ngaydat()).removeValue();
//chuyen tien ban khi tach ban
                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                } else {

                                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).setValue(null);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("tenNhanVien").setValue("");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("tenNhanVien").setValue(tennhanvien);
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").setValue("1");
                                                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("gioDaOder").setValue(0);
                                                }

                                            } else {
                                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(ids).child("sanpham").setValue(carsListsaukhichon);

                                            }
                                            if (TrangThaiBan_doimau_gop != null) {
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue(TrangThaiBan_doimau_gop);
                                            } else {
                                                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("2");
                                            }

                                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("gioDaOder").setValue(ProductTachBan.getDate());
                                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
                                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
//
                            }
                        }).setNegativeButton("No", null)
                                .show();

                    } else {
                        if (snapshot.getValue() != null) {
                            Intent intent = new Intent(orderMenu, ThanhToanActivity.class);
                            intent.putExtra("id_ban", CrrItem.getID());
                            Log.d("id_khuvuc_Truong", Id_khuvuc);
                            intent.putExtra("id_khuvuc", Id_khuvuc);
                            orderMenu.startActivity(intent);
                        } else {
                            Intent intent = new Intent(orderMenu, MonOrder.class);
                            intent.putExtra("id_ban", CrrItem.getID());
                            Log.d("id_khuvuc_Truong1", Id_khuvuc);
                            intent.putExtra("id_khuvuc", Id_khuvuc);
                            orderMenu.startActivity(intent);
                        }

                    }
                } else {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void HamTaodialog(int gravity, StaticBanModel CrrItem) {
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
        datban = dialogban.findViewById(R.id.tvdatban);
        listdatban = dialogban.findViewById(R.id.listdatban);
        hoantac = dialogban.findViewById(R.id.hoantac);
        EvenlistDatban(datban, listdatban, hoantac, CrrItem);
        hamdatban(CrrItem);
        dialogban.show();
    }

    private void EvenlistDatban(TextView datban, TextView listdatban, TextView
            hoantac, StaticBanModel CrrItem) {
        datban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
                Intent intent = new Intent(orderMenu, DatBan.class);
                intent.putExtra("id_ban", CrrItem.getID());
                intent.putExtra("tenban", CrrItem.getTenban());
                intent.putExtra("id_khuvuc", Id_khuvuc);
                orderMenu.startActivity(intent);

            }
        });
        listdatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
                Intent intent = new Intent(orderMenu, DanhSachDatBan.class);
                intent.putExtra("id_ban", CrrItem.getID());
                intent.putExtra("tenban", CrrItem.getTenban());
                intent.putExtra("id_khuvuc", Id_khuvuc);
                orderMenu.startActivity(intent);

            }
        });

        if (CrrItem.getTrangthai().equals("4")) {
            hoantac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (id_ngaydat != null) {
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(CrrItem.getID() + "_" + Id_khuvuc).child(id_ngaydat).child("trangthai").setValue("0");
                    }
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(Id_khuvuc).child("ban").child(CrrItem.getID()).child("trangthai").setValue("1");
                    dialogban.dismiss();

                }
            });
        } else {
            hoantac.setEnabled(true);
        }

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

    private void hamdatban(StaticBanModel CrrItem) {
        abc = CrrItem.getID() + "_" + Id_khuvuc;
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        DataSnapshot sss = postSnapshot;
                        for (DataSnapshot aaa : sss.getChildren()) {
                            id_bk = aaa.child("id_bk").getValue() + "";
                            if (id_bk.equals(abc)) {
                                if ((aaa.child("trangthai").getValue() + "").equals("1")) {
                                    id_ngaydat = aaa.getKey();
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gettrangthaiban() {
        if (id_khuvuc_thanhtoan != null && id_ban_thanhtoan != null) {
            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_thanhtoan).child("ban").child(id_ban_thanhtoan).child("trangthai").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TrangThaiBan_doimau = snapshot.getValue() + "";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void gettrangthaibandegopban() {
        if (id_khuvuc_tachban != null && id_ban_tachban != null) {
            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc_tachban).child("ban").child(id_ban_tachban).child("trangthai").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TrangThaiBan_doimau_gop = snapshot.getValue() + "";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
