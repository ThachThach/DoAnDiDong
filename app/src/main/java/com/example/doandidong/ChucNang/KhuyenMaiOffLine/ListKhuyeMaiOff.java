package com.example.doandidong.ChucNang.KhuyenMaiOffLine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterListKhuyenMai;
import com.example.doandidong.Data.KhuyenMaiOffModel;
import com.example.doandidong.Data.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ListKhuyeMaiOff extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase2;
    private String id_CuaHang;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels;
    private ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels;
    private AdapterListKhuyenMai adapterListKhuyenMai;
    private ArrayList<String> arrayList;
    private EditText searchView;
    private ArrayList<ListKhuyenMaiOffModel> listSearch;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listkhuyenmaioff);
        recyclerView = findViewById(R.id.recyclerViewKhuyenMaiOff);
        searchView = findViewById(R.id.btn_searchkm);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("danhsachkhuyenmaioff");
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
        DanhSachKhuyenMai();

    }

    private void DanhSachKhuyenMai(){
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listKhuyenMaiOffModels = new ArrayList<ListKhuyenMaiOffModel>();
                arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    khuyenMaiOffModels = new ArrayList<KhuyenMaiOffModel>();
                    String Ngaybatdau = postSnapshot.child("Ngaybatdau").getValue() + "";
                    String Ngayketthuc = postSnapshot.child("Ngayketthuc").getValue() + "";
                    String Nhomkhachhang = postSnapshot.child("Nhomkhachhang").getValue() + "";
                    String Noidungkhuyenmai = postSnapshot.child("Noidungkhuyenmai").getValue() + "";
                    String Tenkhuyenmai = postSnapshot.child("Tenkhuyenmai").getValue() + "";
                    String id = postSnapshot.getKey();
                    DataSnapshot aaa = postSnapshot.child("Giasale");
                    for (DataSnapshot snapshot2 : aaa.getChildren()) {
                        String giakhuyenmaiden = snapshot2.child("giakhuyenmaiden").getValue() + "";
                        String giakhuyenmai = snapshot2.child("giakhuyenmai").getValue() + "";
                        String giakhuyenmaitu = snapshot2.child("giakhuyenmaitu").getValue() + "";
                        String key = snapshot2.child("key").getValue() + "";
                        khuyenMaiOffModels.add(new KhuyenMaiOffModel(giakhuyenmaitu, giakhuyenmaiden, giakhuyenmai, key));
                    }
                    listKhuyenMaiOffModels.add(new ListKhuyenMaiOffModel(Ngaybatdau, Ngayketthuc, Nhomkhachhang, Noidungkhuyenmai, Tenkhuyenmai, khuyenMaiOffModels,id));
                    arrayList.add(id);
                }
                adapterListKhuyenMai  = new AdapterListKhuyenMai(ListKhuyeMaiOff.this,listKhuyenMaiOffModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListKhuyeMaiOff.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapterListKhuyenMai);
                adapterListKhuyenMai.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            adapterListKhuyenMai  = new AdapterListKhuyenMai(ListKhuyeMaiOff.this,listKhuyenMaiOffModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(ListKhuyeMaiOff.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapterListKhuyenMai);
            adapterListKhuyenMai.notifyDataSetChanged();
        }
        for(int i =0; i < listKhuyenMaiOffModels.size();i++)
        {
            if(listKhuyenMaiOffModels.get(i).getTenkhuyenmai().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(listKhuyenMaiOffModels.get(i));
            }
        }
        adapterListKhuyenMai  = new AdapterListKhuyenMai(ListKhuyeMaiOff.this,listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListKhuyeMaiOff.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterListKhuyenMai);
        adapterListKhuyenMai.notifyDataSetChanged();
    }

    public void delete(int position){
        new AlertDialog.Builder(ListKhuyeMaiOff.this).setMessage(
                "Bạn có muốn xóa Khuyến mãi này !!!"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase2.child(arrayList.get(position).toString()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();

    }
}
