package com.example.doandidong.ChucNang.SanPham;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterSanPham.AdapterNguyenLieu;
import com.example.doandidong.Data.NguyenLieu;
import com.example.doandidong.R;
import java.util.ArrayList;

public class DanhSachNguyenLieuActivity extends AppCompatActivity {

    private EditText btn_searchsp;
    private RecyclerView recyclerViewProduct;
    private FloatingActionButton themsanpham;

    private String ID_CUAHANG;
    String key;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private FirebaseDatabase firebaseDatabase;

    private ArrayList<NguyenLieu> listSearch;
    private ArrayList<NguyenLieu> listNguyenLieu;

    private AdapterNguyenLieu adapterNguyenLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nguyen_lieu);
        IDLayout();

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        mDatabase = firebaseDatabase.getReference("CuaHangOder/" + ID_CUAHANG).child("nguyenlieu");

        btn_searchsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = btn_searchsp.getText().toString();
                getListSearch(key);

            }
        });

        Taosanphamoi();
        Danhsachsanpham();
    }

    private void IDLayout() {
        btn_searchsp = findViewById(R.id.btn_searchsp);
        recyclerViewProduct = findViewById(R.id.recyclerViewProduct);
        themsanpham = findViewById(R.id.themsanpham);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void Danhsachsanpham() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listNguyenLieu = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    NguyenLieu nguyenLieu = snap.getValue(NguyenLieu.class);
                    nguyenLieu.setKey(snap.getKey());
                    listNguyenLieu.add(nguyenLieu);
                    DataSnapshot aaa1 = snap;

                }
                adapterNguyenLieu = new AdapterNguyenLieu(DanhSachNguyenLieuActivity.this, DanhSachNguyenLieuActivity.this, listNguyenLieu);
                recyclerViewProduct.setAdapter(adapterNguyenLieu);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachNguyenLieuActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewProduct.setLayoutManager(linearLayoutManager);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if (newText == null) {
            adapterNguyenLieu = new AdapterNguyenLieu(DanhSachNguyenLieuActivity.this, DanhSachNguyenLieuActivity.this, listNguyenLieu);
            recyclerViewProduct.setAdapter(adapterNguyenLieu);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachNguyenLieuActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerViewProduct.setLayoutManager(linearLayoutManager);
        }
        for (int i = 0; i < listNguyenLieu.size(); i++) {
            if (listNguyenLieu.get(i).getTen().toUpperCase().contains(newText.toUpperCase().trim())) {
                listSearch.add(listNguyenLieu.get(i));
            }
        }
        adapterNguyenLieu = new AdapterNguyenLieu(DanhSachNguyenLieuActivity.this, DanhSachNguyenLieuActivity.this, listSearch);
        recyclerViewProduct.setAdapter(adapterNguyenLieu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachNguyenLieuActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProduct.setLayoutManager(linearLayoutManager);
    }

    //Button taọ sản phẩm mới
    public void Taosanphamoi() {
        themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm nguyên liệu mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(DanhSachNguyenLieuActivity.this, ThemNguyenLieuActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    public void delete(final int position, LinearLayout linearLayout) {
        new AlertDialog.Builder(DanhSachNguyenLieuActivity.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                linearLayout.setBackgroundResource(R.color.delete);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.child(listNguyenLieu.get(position).getKey()).removeValue();
                        new SupportSaveLichSu(DanhSachNguyenLieuActivity.this, "Xóa nguyên liệu: "+listNguyenLieu.get(position).getTen());
                    }
                }, 1000);
            }
        }).setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}