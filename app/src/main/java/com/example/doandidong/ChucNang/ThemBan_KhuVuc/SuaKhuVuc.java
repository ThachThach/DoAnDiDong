package com.example.doandidong.ChucNang.ThemBan_KhuVuc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.R;
import java.util.ArrayList;

public class SuaKhuVuc extends AppCompatActivity {

    private TextInputEditText tenKhuVuc;
    private AutoCompleteTextView trangThai;
    private Button btnThem;
    private String id;
    private ArrayList<String> arrayLisTT = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC ="khuvuc";
    private String trangthai;
    private String setTT;
    private StaticModelKhuVuc staticModelKhuVuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khu_vuc);
        staticModelKhuVuc = (StaticModelKhuVuc) getIntent().getSerializableExtra("Key_aray");
        tenKhuVuc = findViewById(R.id.edttenkhachang);
        trangThai = findViewById(R.id.spn_khuvuc);
        btnThem = findViewById(R.id.bnt_themkhuvuc);
        arrayLisTT.add("Chưa hoạt động");
        arrayLisTT.add("Hỏng");
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        setTT = staticModelKhuVuc.getTrangthai();
        if(setTT.equals("1")){
            trangthai = "Chưa hoạt động";
            setTT = "1";
        }
        if(setTT.equals("3")){
            trangthai = "Hỏng";
            setTT = "3";
        }
        trangThai.setText(trangthai);
        adapter = new ArrayAdapter<String>(SuaKhuVuc.this,R.layout.support_simple_spinner_dropdown_item,arrayLisTT);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        trangThai.setAdapter(adapter);
        tenKhuVuc.setText(staticModelKhuVuc.getTenkhuvuc());
        btnThem.setText("Sửa khu vực");
        trangThai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trangthai = parent.getItemAtPosition(position).toString();
                if(trangthai.equals("Chưa hoạt động")){
                    setTT = "1";
                }
                if(trangthai.equals("Hỏng")){
                    setTT = "3";
                }

            }
        });
SuaKhuVuc();

    }

    private void SuaKhuVuc(){
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenKhuVuc.getText().toString().isEmpty()){
                    tenKhuVuc.setError("Hãy nhập tên khu vực");
                    tenKhuVuc.requestFocus();
                }else if(setTT == null){
                    Toast.makeText(SuaKhuVuc.this,"Hãy chọn trạng thái",Toast.LENGTH_LONG).show();
                }
                else {
                    mDatabase.child(staticModelKhuVuc.getId_khuvuc()).child("tenkhuvuc").setValue(tenKhuVuc.getText().toString());
                    mDatabase.child(staticModelKhuVuc.getId_khuvuc()).child("trangthai").setValue(setTT);
                    tenKhuVuc.setText("");

                    finish();
                }
            }
        });
    }
}
