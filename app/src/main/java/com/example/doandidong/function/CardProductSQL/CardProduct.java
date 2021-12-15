package com.example.doandidong.function.CardProductSQL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.ChucNangThanhToan.DonGia;
import com.example.doandidong.Model.Product;
import com.example.doandidong.Model.ChucNangThanhToan.ProductPushFB;
import com.example.doandidong.Adapter.Package_CartAdapter_SQL.StaticCardAdapter;
import com.example.doandidong.Model.ChucNangThanhToan.ProuductPushFB1;
import com.example.doandidong.R;
import com.example.doandidong.database.Database_order;

import com.example.doandidong.function.MonOrder;
import com.example.doandidong.function.ThanhToanActivity;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CardProduct extends AppCompatActivity {
    private Toolbar toolbar;//tool bar khai bao id
    private RecyclerView rv_3;//tool bar khai bao id
    StaticCardAdapter staticCartAdapter;
    ArrayList<Product> listcard;
    ProuductPushFB1 list;
    ArrayList<DonGia> cardProducts;
    ArrayList<ProuductPushFB1> listSP;
    private ProductPushFB productPushFB;
    private ProductPushFB productPushFBm;
    String id_ban;
    String id_khuvuc;
    String id;
    String a;
    String S;
    int sl;
    public Handler mHandler;
    String trangthai;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;//khai bao database
    private Product staticMonOrderModel;
    Button bntluu, bntthanhtoan;
    private Database_order database_order;
    private ProuductPushFB1 prouductPushFB1;
    private boolean flag;
    String yeuCau;
    int trangThai = 0;
    private ArrayList<ProductPushFB> ListDate_yc;
    private ArrayList<ProuductPushFB1> listmon = new ArrayList<ProuductPushFB1>();
    private TextView tvkhongsanpham, tvtentongsp;
    private final String TEN_BANG = "ProductSQL1";
    public static int soluong2;
    TextView tvsoluong, tongsanphams;
    String id_datban;
    Activity activity;
    ArrayList<ProuductPushFB1> carsList;
    String id_CuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_san_pham);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        bntluu = findViewById(R.id.bnt_luu);
        bntthanhtoan = findViewById(R.id.bnt_xacnhan);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);


        //viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
        //Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvkhongsanpham = findViewById(R.id.tv_khongsanpham);
        tvtentongsp = findViewById(R.id.tvtentongsp);
        tongsanphams = findViewById(R.id.Tongsanpham);
        //djshdjsd

        //
        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        id_datban = intent.getStringExtra("id_datban");
        id = id_ban + "_" + id_khuvuc;

        rv_3 = findViewById(R.id.rv_3);
        listcard = new ArrayList<Product>();
//        list = new ArrayList<PushToFire>();
        listSP = new ArrayList<>();

        boolean flag = true;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("timestamp", timestamp.getTime() + "");
        long date = Long.parseLong(timestamp.getTime() + "");
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("MangDi");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    trangthai = snapshot1.getValue() + "";
                    Log.d("TrangThaiCardproduct", trangthai + "Cardproduct");

                }
                getDulieuSql();
                staticCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//        getDulieuSql();
        bntluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hamxulicardt();
                Log.d("OOOOO", trangthai);
                productPushFB = new ProductPushFB(date, flag, trangThai, listSP);
                productPushFBm= new ProductPushFB(date, flag, trangThai, listSP);
                if (trangthai.equals("1")) {
                    if (productPushFBm != null) {
                        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id_datban);
                        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getValue() != null) {
                                    ListDate_yc = new ArrayList<>();
                                    ArrayList<ProuductPushFB1> mm = new ArrayList<>();
                                    Long date1 = Long.parseLong(snapshot.child("date").getValue() + "");
                                    Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue() + "");
                                    int trangThais = Integer.parseInt(snapshot.child("trangThai").getValue() + "");
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
                                    int adds = listSP.size();
                                    for (int i = 0; i < adds; i++) {
                                        for (int x = 0; x < listmon.size(); x++) {
                                            if (listSP.get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && listSP.get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                                listSP.get(i).setSoluong(listSP.get(i).getSoluong() + listmon.get(x).getSoluong());
                                            } else {
                                                listSP.add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                            }

                                        }
                                    }
                                    ProductPushFB productPushFBs = new ProductPushFB(date1, flag, trangThai, listSP);
                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_datban).setValue(productPushFBs).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            new SupportSaveLichSu(CardProduct.this,"order mang đi: ID-"+id_datban);
                                        }
                                    });


                                } else {
                                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_datban).setValue(productPushFBm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            new SupportSaveLichSu(CardProduct.this,"order mang đi: ID-"+id_datban);
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                else if (trangthai.equals("0")) {
                    mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id);
                    mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                ListDate_yc = new ArrayList<>();
                                ArrayList<ProuductPushFB1> mm = new ArrayList<>();
                                Long date1 = Long.parseLong(snapshot.child("date").getValue() + "");
                                Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue() + "");
                                int trangThais = Integer.parseInt(snapshot.child("trangThai").getValue() + "");
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
                                Log.d("kssj", listmon.size() + "listmon");
                                Log.d("kssj", listSP.size() + "list");
                                int adds = listSP.size();
                                for (int i = 0; i < adds; i++) {
                                    for (int x = 0; x < listmon.size(); x++) {
                                        if (listSP.get(i).getNameProduct().equals(listmon.get(x).getNameProduct()) && listSP.get(i).getLoai().equals(listmon.get(x).getLoai())) {
                                            listSP.get(i).setSoluong(listSP.get(i).getSoluong() + listmon.get(x).getSoluong());
                                        } else {
                                            listSP.add(new ProuductPushFB1(listmon.get(x).getLoai(), listmon.get(x).getNameProduct(), listmon.get(x).getYeuCau(), listmon.get(x).getImgProduct(), listmon.get(x).getGiaProudct(), listmon.get(x).getSoluong()));

                                        }

                                    }
                                }
                                ProductPushFB productPushFBs = new ProductPushFB(date1, flag, trangThai, listSP);
                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id).setValue(productPushFBs).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        new SupportSaveLichSu(CardProduct.this,"order bàn:"+id_datban);
                                    }
                                });


                            } else {
                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id).setValue(productPushFB).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        new SupportSaveLichSu(CardProduct.this,"order bàn "+id_ban+", Khu vực: " + id_khuvuc);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if (listSP.size() > 0) {
                        String tennhanvien= thongTinCuaHangSql.selectUser().getUsername();
                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("tenNhanVien").setValue(tennhanvien);
                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("2");
                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(date);
                    }
                }


                Intent intent = new Intent(CardProduct.this, ThanhToanActivity.class);
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("trangthai", trangthai);
                startActivity(intent);
                XoaSpkhiOrder();
                Toast.makeText(CardProduct.this, "Order Thành Công", Toast.LENGTH_LONG).show();

            }
        });

//        pushData(listSP, date, flag, trangThai);
        staticCartAdapter = new StaticCardAdapter();
        staticCartAdapter.Setdata(listcard, new StaticCardAdapter.card() {
            @Override
            public void TinhTongTien(Double tongtien) {
                tongsanphams.setText("$" + tongtien);
            }
        }, CardProduct.this);

        for (int i = 0; i < listcard.size(); i++) {
            listcard.get(i).getSoluong();
            Log.d("soluong2", listcard.get(i).getSoluong() + "+1");
        }
        rv_3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_3.setAdapter(staticCartAdapter);
        staticCartAdapter.notifyDataSetChanged();

    }

    private void getDulieuSql() {
        Log.d("phamxuantruong", "else1");
        database_order = new Database_order(this, "app_database.sqlite", null, 2);
        Log.d("aaaaa", "aaaas");
        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
        Log.d("phamxuantruong1", trangthai + "tt1");
        if (trangthai.equals("1")) {
            Log.d("phamxuantruong1", trangthai + "y0");

            S = "SELECT * FROM " + TEN_BANG + " WHERE Id='" + id_datban + "' ";
        } else if (trangthai.equals("0")) {
            S = "SELECT * FROM " + TEN_BANG + " WHERE Id='" + id + "' ";
            Log.d("phamxuantruong1", trangthai + "y1");

        }

        Cursor cursor = database_order.GetData(S, null);
        Log.d("11111", cursor + "1111");
        cardProducts = new ArrayList<>();
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                a = cursor.getString(0);
                String tensp = cursor.getString(1);
                int soluong = cursor.getInt(2);
                Log.d("cosql", a + "");
                String img = cursor.getString(3);
                double gia = cursor.getDouble(4);
                String Loai = cursor.getString(5);
                String yeuCau = cursor.getString(6);
                cardProducts.add(new DonGia(Loai, gia));
                listcard.add(new Product(a, tensp, soluong, img, cardProducts));
                listSP.add(new ProuductPushFB1(Loai, tensp, yeuCau, img, gia, soluong));
                tvtentongsp.setVisibility(View.VISIBLE);
//                list =new PushToFire(tensp,soluong,addtocart);
            }

        } else {

            bntluu.setEnabled(false);
            tvkhongsanpham.setVisibility(View.VISIBLE);

        }
    }


//    private void pushData(ArrayList<ProuductPushFB1> list, long date, boolean flag, int trangThai) {
//        bntluu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Hamxulicardt();
//                productPushFB = new ProductPushFB(date, flag, trangThai, list);
//                if (trangthai.equals("1")) {
//                    if (productPushFB != null) {
//                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_datban);
//
//                    }
//                } else if (trangthai.equals("0")) {
//                    Log.d("kssj", "dsjdskdjk1");
//                    mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id);
//                    mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.getValue() != null) {
//                                ListDate_yc = new ArrayList<>();
//                                Log.d("kssj", "dsjdskdjk2");
//                                listmon = new ArrayList<ProuductPushFB1>();
//                                ArrayList<ProuductPushFB1> mm = new ArrayList<>();
//                                Long date1 = Long.parseLong(snapshot.child("date").getValue() + "");
//                                Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue() + "");
//                                int trangThais = Integer.parseInt(snapshot.child("trangThai").getValue() + "");
//                                DataSnapshot sss = snapshot.child("sanpham");
//                                for (DataSnapshot postSnapshot : sss.getChildren()) {
//                                    String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
//                                    int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
//                                    String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
//                                    Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
//                                    String Loai = postSnapshot.child("loai").getValue() + "";
//                                    String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
//                                    listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
//
//                                }
//                                Log.d("kssj", listmon.size() + "listmon");
//                                Log.d("kssj", list.size() + "list");
//                                for (int i = 0; i < list.size(); i++) {
//                                    for (int x = 0; x < listmon.size(); x++) {
//                                        if (list.get(i).equals(listmon.get(x).getNameProduct()) && list.get(i).getLoai().equals(listmon.get(x).getLoai())) {
//                                            list.get(i).setSoluong(list.get(i).getSoluong() + listmon.get(x).getSoluong());
//                                        } else {
////                                            listmon.add(list.get(i));
//                                            list.add(listmon.get(i));
//                                        }
//
//                                    }
//                                }
//                                ProductPushFB productPushFBs = new ProductPushFB(date1, flag, trangThai, list);
//                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id).setValue(productPushFBs);
//
//
//                            } else {
//                                Log.d("kssj", "dsjdskdjk3");
//                                FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id).setValue(productPushFB);
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    if (list.size() > 0) {
//                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("2");
//                        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(date);
//                    }
//                }
//
//
//                Intent intent = new Intent(CardProduct.this, ThanhToanActivity.class);
//                intent.putExtra("id_ban", id_ban);
//                intent.putExtra("id_khuvuc", id_khuvuc);
//                intent.putExtra("id_datban", id_datban);
//                intent.putExtra("trangthai", trangthai);
//                startActivity(intent);
//                XoaSpkhiOrder();
//                Toast.makeText(CardProduct.this, "Order Thành Công", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//    }

    private void Hamxulicardt() {
        ArrayList<Product> lists = staticCartAdapter.getItems();
        for (int i = 0; i < lists.size(); i++) {
            listSP.get(i).setSoluong(lists.get(i).getSoluong());
        }
    }

    private void XoaSpkhiOrder() {
        if (trangthai.equals("0")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
        } else if (trangthai.equals("1")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id_datban + "'");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.order) {
            Toast.makeText(this, "order nè", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CardProduct.this, MonOrder.class);
            intent.putExtra("id_datban",id_datban);
            intent.putExtra("id_ban", id_ban);
            intent.putExtra("id_khuvuc", id_khuvuc);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

    public void delete(final int position) {

        new AlertDialog.Builder(CardProduct.this).setMessage(
                "Bạn Chắc Chắn muốn xóa sản phẩm này chứ "
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listcard.size() > 0) {
                    database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + listcard.get(position).getId() + "'");
                    listSP.remove(position);
                    listcard.remove(position);
                    staticCartAdapter.notifyDataSetChanged();
                    tongsanphams.setText("$" + TinhTong());

                }
                if (listcard.size() <= 0) {
                    bntluu.setEnabled(false);
                    tvkhongsanpham.setVisibility(View.VISIBLE);
                    tongsanphams.setVisibility(View.INVISIBLE);
                    tvtentongsp.setVisibility(View.INVISIBLE);
                    staticCartAdapter.notifyDataSetChanged();
                }

            }
        }).setNegativeButton("No", null)
                .show();
    }

    private Double TinhTong() {
        double tong = 0.0;
        for (int i = 0; i < listcard.size(); i++) {
            tong = tong + listcard.get(i).getSoluong() * listcard.get(i).getDonGia().get(i).getGiaBan();
        }
        return tong;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}