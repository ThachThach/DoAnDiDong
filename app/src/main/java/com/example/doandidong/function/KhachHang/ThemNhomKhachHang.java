package com.example.doandidong.function.KhachHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.KhachHang.NhomKhachHang;
import com.example.doandidong.R;

public class ThemNhomKhachHang extends AppCompatActivity {
    private EditText editTenNhom,editMa,editGhiChu;
    private Button btnTao,btnHuy;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private  String STR_NKH = "nhomkhachhang";
    private NhomKhachHang nhomKhachHang;
    private String ID_CUAHANG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynhomkhachhang);
        editTenNhom = findViewById(R.id.edtTenNhomKhachHang);
        editMa = findViewById(R.id.edtMaKhachHang);
        editGhiChu = findViewById(R.id.edtGhiChuNhomKhachHang);
        btnHuy = findViewById(R.id.btnhuyTaoNhomKhachhang);
        btnTao = findViewById(R.id.btnTaoNhomKhachhang);

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG= thongTinCuaHangSql.IDCuaHang();

        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NKH);

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTenNhom.getText().toString().isEmpty()){
                    editTenNhom.setError("Hãy nhập tên nhóm khách hàng");
                    editTenNhom.requestFocus();
                }
                else if(editMa.getText().toString().isEmpty()){
                    editMa.setError("Hãy nhập mã nhóm khách hàng");
                }
                else {
                    String id = mDatabase.push().getKey();
                    String name = editTenNhom.getText().toString();
                    String ma = editMa.getText().toString();
                    String ghichu = editGhiChu.getText().toString();
                    nhomKhachHang  = new NhomKhachHang(name,ma,ghichu,id);
                    mDatabase.child(id).setValue(nhomKhachHang);
                    editTenNhom.setText("");
                    editMa.setText("");
                    editGhiChu.setText("");
                    new SupportSaveLichSu(ThemNhomKhachHang.this, "Đã thêm nhóm khách hàng: " + nhomKhachHang.getTenNhomKh());
                    finish();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
