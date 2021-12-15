package com.example.doandidong.ChucNang.SanPham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NguyenLieu;
import com.example.doandidong.R;

public class ThemNguyenLieuActivity extends AppCompatActivity {

    private TextInputEditText tenNguyenLieu, soluong, donvi;
    private TextView them;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String ID_CUAHANG;

    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguyen_lieu);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/" + ID_CUAHANG).child("nguyenlieu");

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNguyenLieu();
            }
        });
    }

    private void IDLayout() {
        tenNguyenLieu = findViewById(R.id.tenNguyenLieu);
        soluong = findViewById(R.id.soluong);
        donvi = findViewById(R.id.donvi);
        them = findViewById(R.id.them);
    }

    private void addNguyenLieu () {
        String ten = tenNguyenLieu.getText().toString();
        String sl = soluong.getText().toString();
        String donVi = donvi.getText().toString();

        if (ten.isEmpty()) {
            tenNguyenLieu.setError("Không được để trống");
            tenNguyenLieu.requestFocus();
        }else if (sl.isEmpty()) {
            soluong.setError("Không được để trống");
            soluong.requestFocus();
        }else if (donVi.isEmpty()) {
            donvi.setError("Không được để trống");
            donvi.requestFocus();
        }else {
            int num = Integer.parseInt(sl);
            NguyenLieu nguyenLieu = new NguyenLieu(ten, num, donVi);
            mDatabase.push().setValue(nguyenLieu).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThemNguyenLieuActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                    new SupportSaveLichSu(ThemNguyenLieuActivity.this, "Thêm nguyên liệu: "+nguyenLieu.getTen());
                    tenNguyenLieu.setText("");
                    soluong.setText("");
                    donvi.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThemNguyenLieuActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}