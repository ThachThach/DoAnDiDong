package com.example.doandidong.function;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa90.materialarcmenu.ArcMenu;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticBanModel;
import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticRvAdapter;
import com.example.doandidong.Common.Interface_KhuVuc_ban;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;

import com.example.doandidong.Model.ChucNangThanhToan.ProductPushFB;
import com.example.doandidong.Model.ChucNangThanhToan.ProuductPushFB1;
import com.example.doandidong.Model.DatBan.DatBanModel;
import com.example.doandidong.Model.DatBan.ID_datban;
import com.example.doandidong.R;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticRvKhuVucAdapter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderMenu extends AppCompatActivity implements Interface_KhuVuc_ban {
    private RecyclerView recyclerView, recyclerView2;//rv khu vuc ban
    private StaticRvKhuVucAdapter staticRvKhuVucAdapter;//adapter khu vuc
    ArrayList<StaticBanModel> items = new ArrayList<>();//araylist ban
    StaticRvAdapter staticRvAdapter;//adapter ban
    private DatabaseReference mDatabase;//khai bao database
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    Interface_KhuVuc_ban interfaceKhuVucBan; //ham get back
    private ArcMenu arcMenu;//arc menu material
    ArrayList<StaticModelKhuVuc> item;
    private StaticModelKhuVuc product;
    private Toolbar toolbar;
    ProgressBar progressBar;
    private Dialog dialogban;
    Window window;
    String id_ban_thanhtoan;
    String id_khuvuc_thanhtoan;
    String id_ban_tachban;
    String id_khuvuc_tachban;
    String ids;
    private ProuductPushFB1 prouductPushFB1;
    ArrayList<DatBanModel> datBanModels;
    ArrayList<ID_datban> ID_datbans;
    String id_bk;
    ImageView img_nocart;
    private String trangthaine;
    private String trangthaigop;
    private String trangthaichucnang;
    ArrayList<ProuductPushFB1> carsList;
    ArrayList<ProuductPushFB1> carsListsaukhichon;
    ArrayList<ProductPushFB> carsList1;
    ProductPushFB productPushFB;
    private String tennhanvien;

    String code_chucnang;
    String id_CuaHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        img_nocart = findViewById(R.id.img_nocart);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent1 = getIntent();

        id_ban_thanhtoan = intent1.getStringExtra("id_ban");
        id_khuvuc_thanhtoan = intent1.getStringExtra("id_khuvuc");
        if (intent1.getStringExtra("id_trangthai") != null) {
            code_chucnang = intent1.getStringExtra("id_trangthai");
        }
        id_ban_tachban = intent1.getStringExtra("id_banTachBan");
        id_khuvuc_tachban = intent1.getStringExtra("id_khuvucTachBan");
        String carListAsString = getIntent().getStringExtra("list_as_string");
        String carListAsString1 = getIntent().getStringExtra("list_as_string1");
        String carListAsString2 = getIntent().getStringExtra("list_as_string2");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProuductPushFB1>>() {
        }.getType();
        Type type1 = new TypeToken<ArrayList<ProductPushFB>>() {
        }.getType();
        Type type2 = new TypeToken<ArrayList<DatBanModel>>() {
        }.getType();
        datBanModels = gson.fromJson(carListAsString2, type2);

        if (datBanModels != null) {
            if (datBanModels.size() > 0) {
                Log.d("datBanModelskkka", datBanModels.size() + "ordermenu");
            }

        }
        carsList1 = gson.fromJson(carListAsString1, type1);
        carsList = gson.fromJson(carListAsString, type);
        if (getIntent().getStringExtra("carsList") != null) {
            String ListCartDaCo = getIntent().getStringExtra("carsList");

            Type type3 = new TypeToken<ArrayList<ProuductPushFB1>>() {
            }.getType();
            carsListsaukhichon = gson.fromJson(ListCartDaCo, type3);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productPushFB = (ProductPushFB) getIntent().getSerializableExtra("en");


        }

        dialogban = new Dialog(OrderMenu.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dailongban);
        window = dialogban.getWindow();

        ID_datbans = new ArrayList<>();
        trangthaine = "0";

        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("MangDi");
        mDatabase.child("trangthai").setValue(trangthaine);
//
//
        if (code_chucnang == null) {
            code_chucnang = "123";
            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
            getDataOrder(actionBar);
        } else if (code_chucnang != null) {
            mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang);
            mDatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        trangthaichucnang = snapshot.child("trangthai").getValue() + "";
                    }
                    getDataOrder(actionBar);
                    tieude(actionBar);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

        callback();
        items = new ArrayList<>();
        recyclerView2 = findViewById(R.id.rv_2);
        staticRvAdapter = new StaticRvAdapter(items, OrderMenu.this, item, "", window, dialogban, trangthaigop, id_ban_thanhtoan, id_khuvuc_thanhtoan, carsList, carsList1, productPushFB, carsListsaukhichon, id_ban_tachban,
                id_khuvuc_tachban, trangthaichucnang, code_chucnang, datBanModels, "");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(gridLayoutManager);
        recyclerView2.setAdapter(staticRvAdapter);
        staticRvAdapter.notifyDataSetChanged();
        recyclerView2.setAlpha(1);


    }

    @Override
    public void GetBack(int position, ArrayList<StaticBanModel> items, String id_khuvuc, String trangThai) {
        id_khuvuc = item.get(position).getId_khuvuc();
        trangThai = item.get(position).getTrangthai();
        staticRvAdapter = new StaticRvAdapter(items, OrderMenu.this, item, id_khuvuc, window, dialogban, trangthaigop, id_ban_thanhtoan, id_khuvuc_thanhtoan,
                carsList, carsList1, productPushFB, carsListsaukhichon, id_ban_tachban, id_khuvuc_tachban, trangthaichucnang, code_chucnang, datBanModels, trangThai);
        staticRvAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(staticRvAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem itemk) {
        int item_id = itemk.getItemId();
        TextView ad;

        if(item.size()>0) {
            if (trangthaichucnang == null) {
                if (item_id == R.id.mangdi) {
                    mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("MangDi");
                    mDatabase.child("trangthai").setValue("1");
                    int code = (int) Math.floor(((Math.random() * 899999) + 100000));
                    Toast.makeText(this, "mang di", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OrderMenu.this, MonOrder.class);
                    intent.putExtra("id_datban", code + "");
                    startActivity(intent);
                    finish();
                }
            }
        }
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return true;
    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    private void callback() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderMenu.this, MainActivity.class);
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).child("trangthai").setValue("0");
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code_chucnang).removeValue();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void getDataOrder(ActionBar actionBar) {
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item = new ArrayList<>();
                if (snapshot.getValue() != null) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        ArrayList<StaticBanModel> mm = new ArrayList<>();
                        String trangthai = postSnapshot.child("trangthai").getValue() + "";
                        String tenkhuvuc = postSnapshot.child("tenkhuvuc").getValue() + "";
                        String id_khuvuc = postSnapshot.getKey();
                        DataSnapshot sss = postSnapshot.child("ban");
                        for (DataSnapshot aaa : sss.getChildren()) {
                            String tenban = aaa.child("tenban").getValue() + "";
                            String trangthai1 = aaa.child("trangthai").getValue() + "";
                            if(aaa.child("tenNhanVien").getValue()==null){
                                tennhanvien ="";
                            }
                            else {
                                tennhanvien = aaa.child("tenNhanVien").getValue() + "";
                            }
                            String gioDaorder = aaa.child("gioDaOder").getValue() + "";
                            String id_ban = aaa.getKey();
                            //gopban
                            if ( trangthaichucnang != null &&trangthaichucnang.equals("1") ) {
                                if (trangthai1.equals("2") ||trangthai1.equals("5") ||trangthai1.equals("6")) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    mm.add(new StaticBanModel(id_ban, tenban, trangthai1, tennhanvien, gioDaorder));
                                    if(mm.size()<1){
                                        img_nocart.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            //chuyen bàn
                            else if ( trangthaichucnang != null && trangthaichucnang.equals("2") ) {
                                if (trangthai1.equals("1")) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    mm.add(new StaticBanModel(id_ban, tenban, trangthai1, tennhanvien, gioDaorder));
                                    if(mm.size()<1){
                                        img_nocart.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            //tách bàn
                            else if (  trangthaichucnang != null && trangthaichucnang.equals("3") ) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (trangthai1.equals("1") || trangthai1.equals("2")) {
                                    mm.add(new StaticBanModel(id_ban, tenban, trangthai1, tennhanvien, gioDaorder));
                                    if(mm.size()<1){
                                        img_nocart.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            else {
                                progressBar.setVisibility(View.VISIBLE);
                                mm.add(new StaticBanModel(id_ban, tenban, trangthai1, tennhanvien, gioDaorder));
                            }
                        }

                        StaticModelKhuVuc product = new StaticModelKhuVuc(tenkhuvuc, trangthai, id_khuvuc, mm);
                        item.add(product);
                    }
                } else {
                    img_nocart.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView = findViewById(R.id.rv_1);
                staticRvKhuVucAdapter = new StaticRvKhuVucAdapter(item, OrderMenu.this, OrderMenu.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderMenu.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(staticRvKhuVucAdapter);
                staticRvKhuVucAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tieude(ActionBar actionBar) {
        if (code_chucnang != null) {
            if (trangthaichucnang.equals("0") || trangthaichucnang == null) {
                actionBar.setTitle("Danh sách Bàn");

            } else if (trangthaichucnang.equals("1")) {
                actionBar.setTitle("Gộp bàn");
            } else if (trangthaichucnang.equals("2")) {
                actionBar.setTitle("Chuyển Bàn");
            } else if (trangthaichucnang.equals("3")) {
                actionBar.setTitle("Tách Bàn");
            }
        } else {
            actionBar.setTitle("Danh sách Bàn");
        }
    }
}
