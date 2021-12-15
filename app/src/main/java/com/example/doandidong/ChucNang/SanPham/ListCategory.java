package com.example.doandidong.ChucNang.SanPham;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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


import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterSanPham.AdapterCategory;
import com.example.doandidong.Data.SanPham.Category;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ListCategory extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private ArrayList<Category> listCategory;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private AdapterCategory adapterCategory;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_NHOMSANPHAM = "danhmucsanpham";
    private EditText searchView,editTenNhom;
    private ArrayList<Category> listSearch;
    private String ID_CUAHANG;
    private String key;
    private Dialog dialog;
    private Window window;
    private Button btnThem,btnHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        recyclerView = findViewById(R.id.rv_2);
        searchView = findViewById(R.id.btn_searchnsp);
        floatingActionButton = findViewById(R.id.themnhomsanpham);
        dialog = new Dialog(ListCategory.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suanhomsanpham);
        window = dialog.getWindow();
        editTenNhom = dialog.findViewById(R.id.edtTenNhomKH);
        btnThem = dialog.findViewById(R.id.btnthemDiaLogThemNKH);
        btnHuy = dialog.findViewById(R.id.btnhuyDiaLogThemNKH);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NHOMSANPHAM);

        Danhsachnhomsanpham();
        Taonhomsanpham();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = searchView.getText().toString();
                getListSearch(key);
            }
        });

    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            adapterCategory = new AdapterCategory(ListCategory.this,listCategory);
            recyclerView.setAdapter(adapterCategory);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < listCategory.size();i++)
        {
            if(listCategory.get(i).getNameCategory().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(listCategory.get(i));
            }
        }
        adapterCategory = new AdapterCategory(ListCategory.this,listSearch);
        recyclerView.setAdapter(adapterCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    //Button tạo nhóm sản phẩm
    public void Taonhomsanpham(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm nhóm sản phẩm  mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListCategory.this, AddCategory.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }
    //Danh sách nhóm sản phẩm
    public void Danhsachnhomsanpham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCategory = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategory.add(category);
                }
                adapterCategory = new AdapterCategory(ListCategory.this,listCategory);
                recyclerView.setAdapter(adapterCategory);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListCategory.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Xóa nhóm sản phẩm
    public void delete( int position){

        new AlertDialog.Builder(ListCategory.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDatabase.child(listCategory.get(position).getId()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();
    }
    //Sửa sản phẩm
    public void update( int position,int gravity){

        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        editTenNhom.setText(listCategory.get(position).getNameCategory());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(listCategory.get(position).getId()).child("nameCategory").setValue(editTenNhom.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
