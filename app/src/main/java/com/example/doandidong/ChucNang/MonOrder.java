package com.example.doandidong.ChucNang;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.SQL.Database_order;
import com.example.doandidong.ChucNang.CardProductSQL.CardProduct;
import com.example.doandidong.Adapter.Pakage_AdapterDanhMuc_Mon.StaticCategoryAdapter;
import com.example.doandidong.Adapter.Pakage_AdapterDanhMuc_Mon.StaticCategoryMonModel;
import com.example.doandidong.Common.Interface_CategorySp_Sp;
import com.example.doandidong.Data.ChucNangThanhToan.DonGia;
import com.example.doandidong.Data.Product;
import com.example.doandidong.Adapter.Package_AdapterMon.StaticMonRvAdapter;
import com.example.doandidong.R;
import com.example.doandidong.Adapter.Package_CartAdapter_SQL.StaticCardAdapter;
import java.util.ArrayList;

public class  MonOrder extends AppCompatActivity implements Interface_CategorySp_Sp {
    public ArrayList<MonOrder> listmon = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    Bundle bundle1 = intent.getExtras();
                    if (bundle1.getSerializable("mon") != null) {
                    }
                    MonOrder monOrder = (MonOrder) bundle1.getSerializable("mon");

                    listmon.add(monOrder);
                }
            });
    private RecyclerView recyclerView, recyclerView2;//rv category mon, mon
    private StaticCategoryAdapter staticCategoryAdapter;
    ArrayList<Product> items = new ArrayList<>();//araylist mon
    StaticMonRvAdapter staticMonRvAdapter;//adapter ban
    private DatabaseReference mDatabase,mDatabase1;
    private Toolbar toolbar;//tool bar khai bao id
    ArrayList<StaticCategoryMonModel> item;
    Product staticMonOrderModel;
    DonGia dongia;
    String tenban;
    String id_ban;
    String id_khuvuc;
    String id_datban;
    String trangthai;
    String trangthaimangdi;
    StaticCardAdapter staticCardAdapter;
    ArrayList<Product> listcard = new ArrayList<>();//araylist mon
    Button bnt_card;
    Interface_CategorySp_Sp interface_categorySp_sp;
    ArrayList<DonGia> donGiaOrders;
    String value1;
    ImageView img_nocart;
    String key_SanPham;
    ProgressBar progressBar;
    Button bnt_huy;
    String id_CuaHang;
    TextView soluongdem;
    String S;
    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";

    // LinearProgressIndicator
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_order);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();

        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        id_datban = intent.getStringExtra("id_datban");
        img_nocart = findViewById(R.id.img_nocart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        listcard = new ArrayList<>();
        bnt_card = findViewById(R.id.bnt_luu);
        bnt_huy = findViewById(R.id.bnt_huy);
        bnt_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bnt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MonOrder.this, CardProduct.class);
                intent1.putExtra("id_ban", id_ban);
                intent1.putExtra("id_khuvuc", id_khuvuc);
                intent1.putExtra("id_datban", id_datban);
//                intent1.putExtra("trangthai",trangthai);
                startActivity(intent1);

            }
        });
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanpham");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item = new ArrayList<>();
                donGiaOrders = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        ArrayList<Product> mm = new ArrayList<>();
                        String tencategory = snapshot1.getKey() + "";
                        DataSnapshot aaa = snapshot1;
                        for (DataSnapshot snapshot2 : aaa.getChildren()) {
                            key_SanPham = snapshot2.getKey();
                            staticMonOrderModel = snapshot2.getValue(Product.class);
                            String nameProduct = staticMonOrderModel.getNameProduct();
                            int soluong = Integer.parseInt(staticMonOrderModel.getSoluong() + "");
                            String imgProduct = staticMonOrderModel.getImgProduct();
                            String status = staticMonOrderModel.getStatus();
                            String id = staticMonOrderModel.getId();
                            DataSnapshot sss = snapshot2.child("donGia");
                            for (DataSnapshot snapshot3 : sss.getChildren()) {
                                DonGia donGiaOrder = snapshot3.getValue(DonGia.class);
                                donGiaOrders.add(donGiaOrder);
                            }
                            mm.add(new Product(nameProduct, soluong, imgProduct, donGiaOrders, status, id));
                        }
                        StaticCategoryMonModel product = new StaticCategoryMonModel(tencategory, mm);
                        item.add(product);
                    }
                } else {
                    img_nocart.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView = findViewById(R.id.rv_1);
                staticCategoryAdapter = new StaticCategoryAdapter(item, MonOrder.this, MonOrder.this, 0);
                recyclerView.setLayoutManager(new LinearLayoutManager(MonOrder.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(staticCategoryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        items = new ArrayList<>();

        recyclerView2 = findViewById(R.id.rv_2);

        staticMonRvAdapter = new StaticMonRvAdapter(items, MonOrder.this, item, 0, tenban, id_ban, id_khuvuc, key_SanPham, id_datban, trangthai);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(gridLayoutManager);
        recyclerView2.setAdapter(staticMonRvAdapter);

    }

    @Override
    public void GetBack1(int pos, ArrayList<Product> items) {
        staticMonRvAdapter = new StaticMonRvAdapter(items, MonOrder.this, item, pos, tenban, id_ban, id_khuvuc, key_SanPham, id_datban, trangthai);
        staticMonRvAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(staticMonRvAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}