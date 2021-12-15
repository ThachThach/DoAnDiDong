package com.example.doandidong.function.Account;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Model.CuaHangSignIn;
import com.example.doandidong.Model.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.database.ThongTinCuaHangSql;
import com.example.doandidong.function.DonHangOnline.data.DiaChi;
import com.example.doandidong.function.MainActivity;
import java.util.ArrayList;

public class ChiNhanhSignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private Button btnStart;
    private AutoCompleteTextView spinner_chinhanh;

    private ArrayAdapter<String> adapter;
    private ArrayList<CuaHangSignIn> ListChiNhanh;
    private String ID_USER;
    private String[] ID;
    private String key;
    private Boolean thietLap = false;
    private Boolean checkDataUser = false;
    private Boolean checkThietLap = false;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_nhanh_sign_in);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        thongTinCuaHangSql = new ThongTinCuaHangSql(ChiNhanhSignInActivity.this,"app_database.sqlite", null, 2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ListChiNhanh = (ArrayList<CuaHangSignIn>) bundle.getSerializable("LIST_CHINHANH");
            ID_USER = bundle.getString("ID_USER", "");
        }else{
            Log.d("AAA", "null");
        }

        btnStart = findViewById(R.id.btnStart);
        spinner_chinhanh = findViewById(R.id.spinner_chinhanh);


        String[] items = arrData();
        adapter = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, items);
        key = ID[0];
        spinner_chinhanh.setText(items[0]);

        spinner_chinhanh.setAdapter(adapter);
        getDataUser();
        spinner_chinhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                key = ID[position];
                checkDataUser = false;
                checkThietLap = false;
                getDataUser();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayLoadata();
            }
        });
    }

    private String[] arrData () {

        String[] arr = new String[ListChiNhanh.size()];
        ID = new String[ListChiNhanh.size()];
        for (int i = 0; i < ListChiNhanh.size(); i++) {
            arr[i] = ListChiNhanh.get(i).getName();
            ID[i] = ListChiNhanh.get(i).getID();
        }
        return arr;
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
                        Intent intent = new Intent(ChiNhanhSignInActivity.this, StoreSetting.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(ChiNhanhSignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    delayLoadata();
                }
            }
        }, 100);
    }
}