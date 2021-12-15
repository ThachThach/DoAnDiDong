package com.example.doandidong.ChucNang.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Data.CuaHangSignIn;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.SQL.ThongTinCuaHangSql;
import com.example.doandidong.ChucNang.DonHangOnline.data.DiaChi;
import com.example.doandidong.ChucNang.MainActivity;
import java.util.ArrayList;

public class ChiNhanhActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnChiNhanhMoi, btnChuyen;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private com.example.doandidong.Common.ThongTinCuaHangSql thongTinCuaHangSqlCommon;
    private String ID_USER, ID_CUAHANG;
    private ArrayList<CuaHangSignIn> cuaHangSignIns = new ArrayList<>();
    private ArrayList<String> arr = new ArrayList<>();
    private AutoCompleteTextView spinner_chinhanh;
    private ArrayAdapter<String> adapter;
    private String key;
    private TextView ten_chi_nhanh;

    private Boolean checkDataUser = false;
    private Boolean checkThietLap = false;
    private Boolean thietLap = false;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_nhanh);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(ChiNhanhActivity.this,"app_database.sqlite", null, 2);
        thongTinCuaHangSqlCommon = new com.example.doandidong.Common.ThongTinCuaHangSql(this);
        ID_USER = thongTinCuaHangSqlCommon.IDUser();
        ID_CUAHANG = thongTinCuaHangSqlCommon.IDCuaHang();


        DanhSachChiNhanh();
    }

    private void IDLayout() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        btnChiNhanhMoi = findViewById(R.id.btnChiNhanhMoi);
        btnChuyen = findViewById(R.id.btnChuyen);
        spinner_chinhanh = findViewById(R.id.spinner_chinhanh);
        ten_chi_nhanh = findViewById(R.id.ten_chi_nhanh);

        btnChiNhanhMoi.setOnClickListener(this);
        btnChuyen.setOnClickListener(this);
    }

    private void DanhSachChiNhanh() {
        mFirebaseDatabase.child("ACCOUNT_LOGIN").child(ID_USER).child("CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot data: snapshot.getChildren()) {
                        String chucVu = data.child("ChucVu").getValue().toString();
                        String name = data.child("name").getValue().toString();
                        String ID = data.child("ID").getValue().toString();

                        CuaHangSignIn cuaHangSignIn = new CuaHangSignIn(chucVu, ID, name);
                        arr.add(name);
                        cuaHangSignIns.add(cuaHangSignIn);
                    }

                    adapter = new ArrayAdapter<String>(ChiNhanhActivity.this, R.layout.item_spinner1_setup_store, arr);
                    key = cuaHangSignIns.get(0).getID();
                    spinner_chinhanh.setText(cuaHangSignIns.get(0).getName());
                    spinner_chinhanh.setAdapter(adapter);

                    spinner_chinhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            key = cuaHangSignIns.get(position).getID();
                            checkDataUser = false;
                            checkThietLap = false;
                            getDataUser();
                        }
                    });
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("ssss", ID_USER+ " - "+ ID_CUAHANG);

            mFirebaseDatabase.child("ACCOUNT_LOGIN").child(ID_USER).child("CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() == 1) {
                        for (DataSnapshot snapshot1: snapshot.getChildren()) {
                            ten_chi_nhanh.setText(snapshot1.child("name").getValue().toString());
                        }
                    }else {
                        mFirebaseDatabase.child("ACCOUNT_LOGIN").child(ID_USER).child("CuaHang").child(ID_CUAHANG).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ten_chi_nhanh.setText(snapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChiNhanhMoi:
                Intent intent = new Intent(ChiNhanhActivity.this, TaoChiNhanhActivity.class);
                startActivity(intent);
                break;
            case R.id.btnChuyen:
                delayLoadata();
                break;
        }
    }

    //Biến test
    private void getDataUser () {
        checkDataUser = false;
        checkThietLap= false;
        thongTinCuaHangSql.createTableChuCuaHang();

        mFirebaseDatabase.child("CuaHangOder/"+key+"/user/"+ID_USER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cursor cursor = thongTinCuaHangSql.selectUser();
                Cursor cursor1 = thongTinCuaHangSql.selectChuCuaHang();
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                String quyen = "";

                for (int i = 0; i < nhanVien.getChucVu().size(); i++) {
                    if (nhanVien.getChucVu().get(i)) {
                        quyen = quyen + "1";
                    }else{
                        quyen = quyen + "0";
                    }
                }

                Log.d("ssss", nhanVien.isChuCuaHang()+"");

                if (cursor1.getCount() > 0) {
                    thongTinCuaHangSql.UpdateChuCuaHang(nhanVien.isChuCuaHang()+"");
                }else {
                    thongTinCuaHangSql.InsertChuCuaHang(nhanVien.isChuCuaHang()+"");
                }

                if (cursor.getCount() > 0){
                    String IdOld = "";
                    while (cursor.moveToNext()) {
                        IdOld = cursor.getString(0);
                    }
                    thongTinCuaHangSql.UpdateUser(ID_USER, IdOld, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }else {
                    thongTinCuaHangSql.InsertUser(ID_USER, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }
                checkDataUser = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mFirebaseDatabase.child("CuaHangOder/"+key+"/ThongTinCuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thongTinCuaHangSql.createTable();
                Cursor cursor = thongTinCuaHangSql.selectThongTin();
                DiaChi diaChi = snapshot.getValue(DiaChi.class);
                if (cursor.getCount() > 0){
                    String IdOld = "";
                    while (cursor.moveToNext()) {
                        IdOld = cursor.getString(0);
                    }
                    thongTinCuaHangSql.UpdateCuaHang(key, IdOld, diaChi.getTenCuaHang());
                }else {
                    thongTinCuaHangSql.InsertThonTin(key, diaChi.getTenCuaHang());
                }

                thietLap = (Boolean) snapshot.child("ThietLap").getValue();
                checkThietLap = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void delayLoadata() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkDataUser==true && checkThietLap == true) {
                    if (thietLap == false) {
                        Intent intent = new Intent(ChiNhanhActivity.this, StoreSetting.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(ChiNhanhActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }else{
                    delayLoadata();
                }
            }
        }, 100);
    }
}