package com.example.doandidong.function.KhuyenMaiOffLine;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterListChonKhuyenMaiOff;
import com.example.doandidong.Model.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListChonKhuyenMaiOff extends AppCompatActivity {
    private RecyclerView rv_1;
    private DatabaseReference mDatabase2;
    private String id_CuaHang;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    TextView tv_tenkhuyenmai,tv_ngaybatdau,tv_ngayketthuc,tv_nhomkhachhang,tv_noidung;
    ListKhuyenMaiOffModel listchuyen;
    private AdapterListChonKhuyenMaiOff adapterListChonKhuyenMaiOff;
    private String idKM;
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listchon_khuyenmai);
        tv_tenkhuyenmai = findViewById(R.id.tv_tenkhuyenmai);
        tv_ngaybatdau = findViewById(R.id.tv_ngaybatdau);
        tv_ngayketthuc = findViewById(R.id.tv_ngayketthuc);
        tv_nhomkhachhang = findViewById(R.id.tv_nhomkhachhang);
        tv_noidung = findViewById(R.id.tv_noidung);
        rv_1 = findViewById(R.id.rv_1);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        Gson gson = new Gson();
        if (getIntent().getStringExtra("listchuyen") != null) {
            String ListCartDaCo = getIntent().getStringExtra("listchuyen");
            Type type3 = new TypeToken<ListKhuyenMaiOffModel>() {
            }.getType();
            listchuyen = gson.fromJson(ListCartDaCo, type3);
        }
        if(listchuyen!=null){
            tv_tenkhuyenmai.setText(listchuyen.getTenkhuyenmai());
            tv_ngaybatdau.setText(listchuyen.getNgaybatdau());
            tv_ngayketthuc.setText(listchuyen.getNgayketthuc());
            tv_nhomkhachhang.setText(listchuyen.getNhomkhachhang());
            tv_noidung.setText(listchuyen.getNoidungkhuyenmai());
            idKM = listchuyen.getId();

        }
        adapterListChonKhuyenMaiOff = new AdapterListChonKhuyenMaiOff(ListChonKhuyenMaiOff.this,listchuyen.getKhuyenMaiOffModels());
        rv_1.setLayoutManager(new LinearLayoutManager(ListChonKhuyenMaiOff.this, LinearLayoutManager.VERTICAL, false));
        rv_1.setAdapter(adapterListChonKhuyenMaiOff);
        adapterListChonKhuyenMaiOff.notifyDataSetChanged();


    }


    public void delete(int position,delete1 delete1){
        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("danhsachkhuyenmaioff").child(idKM).child("Giasale");
        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for(DataSnapshot snapshot1  : snapshot.getChildren()){
                    String id = snapshot1.getKey();
                    arrayList.add(id);
                }
                new AlertDialog.Builder(ListChonKhuyenMaiOff.this).setMessage(
                        "Do you want to delete this item"
                ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        mDatabase2.child(arrayList.get(position).toString()).removeValue();
                        delete1.delete();
                        listchuyen.getKhuyenMaiOffModels().remove(position);



                    }
                }).setNegativeButton("No", null)
                        .show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Toast.makeText(ListChonKhuyenMaiOff.this, arrayList.size(), Toast.LENGTH_SHORT).show();

    }

    public interface delete1
    {
        void delete();
    }
}
