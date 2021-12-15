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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterBan.AdapterBan;
import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticBanModel;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ListBan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC ="khuvuc";
    private String STR_BAN = "ban";
    private StaticBanModel staticBanModel;
    private StaticModelKhuVuc staticModelKhuVuc;
    private ArrayList<StaticBanModel> arrayList;
    private ArrayList<String> arrayListKV;
    private AdapterBan adapterBan;
    private EditText searchView;
    private ArrayList<StaticBanModel> listSearch;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_banc);
        recyclerView = findViewById(R.id.listban);
        floatingActionButton = findViewById(R.id.bnt_themban);
        searchView = findViewById(R.id.btn_searchban);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC);
        DanhSachBan();
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
        ThemBan();
    }

    private void DanhSachBan(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                arrayListKV = new ArrayList<>();
                    if (snapshot.getValue() != null) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            DataSnapshot snapshot2 = snapshot1.child(STR_BAN);
                            for (DataSnapshot snapshot3 : snapshot2.getChildren()){
                                String tenban = snapshot3.child("tenban").getValue() + "";
                                String trangthai = snapshot3.child("trangthai").getValue() + "";
                                String tennhanvien = snapshot3.child("tenNhanVien").getValue() + "";
                                String gioDaorder = snapshot3.child("gioDaOder").getValue() + "";
                                String id_khuvuc = snapshot3.child("id_khuvuc").getValue()+"";
                                String id_ban = snapshot3.getKey();
                                Log.d("kkkkk",id_khuvuc+"");
                                staticBanModel = new StaticBanModel(id_ban,tenban,tennhanvien,gioDaorder,id_khuvuc,trangthai);
                                arrayList.add(staticBanModel);
                            }
                    }
                }


                    adapterBan = new AdapterBan(ListBan.this,ListBan.this,arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListBan.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapterBan);
                adapterBan.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            adapterBan = new AdapterBan(ListBan.this,ListBan.this,arrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(ListBan.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapterBan);
            adapterBan.notifyDataSetChanged();
        }
        for(int i =0; i < arrayList.size();i++)
        {
            if(arrayList.get(i).getTenban().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(arrayList.get(i));
            }
        }
        adapterBan = new AdapterBan(ListBan.this,ListBan.this,listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListBan.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterBan);
        adapterBan.notifyDataSetChanged();
    }

    private void ThemBan(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Thêm khu vực mới", Snackbar.LENGTH_LONG)
                        .setAction("Thêm", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent = new Intent(ListBan.this, ThemBan.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    public void delete(int position){
        String gio = arrayList.get(position).getGioDaOder();
        new AlertDialog.Builder(ListBan.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(gio.equals("0")){

                   mDatabase.child(arrayList.get(position).getId_khuvuc()).child(STR_BAN).child(arrayList.get(position).getID()).removeValue();

                }
                else {
                    Toast.makeText(ListBan.this, "Bàn này đang Oder không thể xóa "+gio, Toast.LENGTH_SHORT).show();

                }
            }
        }).setNegativeButton("No", null)
                .show();


    }
}