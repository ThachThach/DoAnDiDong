package com.example.doandidong.ChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTieuActivity extends AppCompatActivity {

    private TextInputEditText edt_tien, edt_mota;
    private TextView tv_xacnhan;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private NhanVien nhanVien;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private Double tongTienCuaHang;
    private ProgressBar progressBar;
    private LinearLayout layout_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tieu);

        edt_tien = findViewById(R.id.edt_tien);
        edt_mota = findViewById(R.id.edt_mota);
        tv_xacnhan = findViewById(R.id.tv_xacnhan);
        layout_pro = findViewById(R.id.layout_pro);
        progressBar = findViewById(R.id.progressBar);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        nhanVien = thongTinCuaHangSql.selectUser();
        tongTienCuaHang = 0.0;

        tv_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_xacnhan.setEnabled(true);
                setFirebase();
            }
        });

        getFirebase();

    }

    private void getFirebase () {
        mFirebaseDatabase.child("CuaHangOder").child(ID_CUAHANG).child("bienlai/taichinh").child("tongTien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                   Double tien = snapshot.getValue(Double.class);
                    tongTienCuaHang = tien;
                }
                progressBar.setVisibility(View.GONE);
                layout_pro.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFirebase() {

        String tien = edt_tien.getText().toString();
        String moTa = edt_mota.getText().toString();

        if (tien.isEmpty()) {
            edt_tien.setError("Không được bỏ trống");
            edt_tien.requestFocus();
            tv_xacnhan.setEnabled(true);
        } else if (moTa.isEmpty()) {
            edt_mota.setError("Không được bỏ trống");
            edt_mota.requestFocus();
            tv_xacnhan.setEnabled(true);
        } else {
            tv_xacnhan.setEnabled(true);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Double tongTien = Double.parseDouble(tien);


            Double tong = tongTienCuaHang - tongTien;

            mFirebaseDatabase.child("CuaHangOder").child(ID_CUAHANG).child("bienlai/chi").child(ngayHienTai()).child(timestamp.getTime()+"").child("lydo").setValue(moTa);
            mFirebaseDatabase.child("CuaHangOder").child(ID_CUAHANG).child("bienlai/chi").child(ngayHienTai()).child(timestamp.getTime()+"").child("tongchi").setValue(tongTien);
            mFirebaseDatabase.child("CuaHangOder").child(ID_CUAHANG).child("bienlai/taichinh").child("tongTien").setValue(tong);
            Toast.makeText(ChiTieuActivity.this, "Hoàn thành", Toast.LENGTH_SHORT).show();
            edt_tien.setText("");
            edt_mota.setText("");
        }
    }

    private String ngayHienTai() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);

        return dt;
    }

}