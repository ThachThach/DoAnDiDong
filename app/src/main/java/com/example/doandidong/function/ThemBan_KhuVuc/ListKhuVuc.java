package com.example.doandidong.function.ThemBan_KhuVuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.KhuVucAdapter;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ListKhuVuc extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC ="khuvuc";
    private String STR_BAN = "ban";
    private StaticModelKhuVuc staticModelKhuVuc;
    private ArrayList<StaticModelKhuVuc> arrayList;
    private KhuVucAdapter khuVucAdapter;
    private EditText searchView;
    private ArrayList<StaticModelKhuVuc> listSearch;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khu_vuc);
        recyclerView = findViewById(R.id.rv_2);
        floatingActionButton = findViewById(R.id.bnt_themkhuvuc);
        searchView = findViewById(R.id.btn_searchkv);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC);
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
        DanhSachKhuVuc();
        ThemKhuVuc();

    }

    private void DanhSachKhuVuc(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String name = snapshot1.child("tenkhuvuc").getValue().toString();
                    String status = snapshot1.child("trangthai").getValue().toString();
                    String idkhuvuc = snapshot1.child("id_khuvuc").getValue().toString();

                    staticModelKhuVuc = new StaticModelKhuVuc(name,status,idkhuvuc);
                    arrayList.add(staticModelKhuVuc);
//                    arrayListBan = new ArrayList<>();
//                    DataSnapshot snapshot2 = snapshot1.child(STR_BAN);
//                    for (DataSnapshot snapshot3 : snapshot2.getChildren()){
//                        String tenban = snapshot3.child("tenban").getValue() + "";
//                        String trangthai = snapshot3.child("trangthai").getValue() + "";
//                        String tennhanvien = snapshot3.child("tenNhanVien").getValue() + "";
//                        String gioDaorder = snapshot3.child("gioDaOder").getValue() + "";
//                        String id_khuvuc = snapshot3.child("id_khuvuc").getValue()+"";
//                        String id_ban = snapshot3.getKey();
//                        Log.d("kkkkk",id_khuvuc+"");
//                        staticBanModel = new StaticBanModel(id_ban,tenban,tennhanvien,gioDaorder,id_khuvuc,trangthai);
//                        arrayListBan.add(staticBanModel);
//                    }
                }

                khuVucAdapter = new KhuVucAdapter(ListKhuVuc.this,ListKhuVuc.this,arrayList);
                recyclerView.setAdapter(khuVucAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuVuc.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            khuVucAdapter = new KhuVucAdapter(ListKhuVuc.this,ListKhuVuc.this,arrayList);
            recyclerView.setAdapter(khuVucAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuVuc.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < arrayList.size();i++)
        {
            if(arrayList.get(i).getTenkhuvuc().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(arrayList.get(i));
            }
        }
        khuVucAdapter = new KhuVucAdapter(ListKhuVuc.this,ListKhuVuc.this,listSearch);
        recyclerView.setAdapter(khuVucAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListKhuVuc.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void ThemKhuVuc(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm khu vực mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListKhuVuc.this, ThemKhuVuc.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    public void delete(int position){
        new AlertDialog.Builder(ListKhuVuc.this).setMessage(
                "Bạn sẽ xóa tất cả bàn trong khu vực này !!!"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    mDatabase.child(arrayList.get(position).getId_khuvuc()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();
    }
}