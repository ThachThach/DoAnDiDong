package com.example.doandidong.function.CuaHangOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Data.HinhThucGiaoHang;
import com.example.doandidong.function.KhuyenMai.ListKhuyenMai;
import com.example.doandidong.function.ThanhToanActivity;

public class CuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextInputEditText edtThongTinChuyenKhoan, ghiChu;
    private CheckBox checkboxChuyenKhoan, checkboxTienMat;
//    private CardView cauhinhvanchuyen;
    private LinearLayout layoutThongTinChuyenKhoan;
    private TextView dangKy, addLoai;
    private LinearLayout.LayoutParams params;
    private ProgressBar progressBar, progressBar2;
    private ScrollView scrollView;

    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang_online);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.cuahang);

        progressBar2.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        getData();


        setCheck();
    }

    private void IDLayout() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        navigationView = findViewById(R.id.nav_view);
        edtThongTinChuyenKhoan = findViewById(R.id.edtThongTinChuyenKhoan);
        ghiChu = findViewById(R.id.ghiChu);
//        checkboxKhachHangDenLay = findViewById(R.id.checkboxKhachHangDenLay);
//        checkboxDonViVanChuyen = findViewById(R.id.checkboxDonViVanChuyen);
//        checkboxTuGiao = findViewById(R.id.checkboxTuGiao);
        checkboxChuyenKhoan = findViewById(R.id.checkboxChuyenKhoan);
        checkboxTienMat = findViewById(R.id.checkboxTienMat);
//        cauhinhvanchuyen = findViewById(R.id.cauhinhvanchuyen);
        layoutThongTinChuyenKhoan = findViewById(R.id.layoutThongTinChuyenKhoan);
        dangKy = findViewById(R.id.dangKy);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        params = (LinearLayout.LayoutParams) layoutThongTinChuyenKhoan.getLayoutParams();
        addLoai = findViewById(R.id.addLoai);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.scrollView);
        progressBar2 = findViewById(R.id.progressBar2);

        checkboxChuyenKhoan.setOnClickListener(this);
//        checkboxTuGiao.setOnClickListener(this);
        dangKy.setOnClickListener(this);
//        cauhinhvanchuyen.setOnClickListener(this);
    }

    private void getData() {
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    HinhThucGiaoHang hinhThucGiaoHang = snapshot.getValue(HinhThucGiaoHang.class);
//                    checkboxKhachHangDenLay.setChecked(hinhThucGiaoHang.isKhacHangDenLay());
//                    checkboxDonViVanChuyen.setChecked(hinhThucGiaoHang.isQuaDoiTac());
//                    checkboxTuGiao.setChecked(hinhThucGiaoHang.isTuGiao());
                    checkboxChuyenKhoan.setChecked(hinhThucGiaoHang.isChuyenKhoan());
                    checkboxTienMat.setChecked(hinhThucGiaoHang.isTienMat());
                    edtThongTinChuyenKhoan.setText(hinhThucGiaoHang.getThongTinChuyenKhoan());
                    ghiChu.setText(hinhThucGiaoHang.getGhiChu());

                    if (hinhThucGiaoHang.isChuyenKhoan()) {
                        layoutThongTinChuyenKhoan.setVisibility(View.VISIBLE);
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        layoutThongTinChuyenKhoan.setLayoutParams(params);
                    }

//                    if (hinhThucGiaoHang.isTuGiao()) {
//                        cauhinhvanchuyen.setVisibility(View.VISIBLE);
//                    }
                }else {
                }
                progressBar2.setVisibility(View.INVISIBLE);
                scrollView.setAlpha(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setCheck() {
        if (checkboxChuyenKhoan.isChecked()) {
            layoutThongTinChuyenKhoan.setVisibility(View.VISIBLE);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutThongTinChuyenKhoan.setLayoutParams(params);
        } else {
            layoutThongTinChuyenKhoan.setVisibility(View.INVISIBLE);
            params.height = 0;
            layoutThongTinChuyenKhoan.setLayoutParams(params);
        }
//        if (checkboxTuGiao.isChecked()) {
//            cauhinhvanchuyen.setVisibility(View.VISIBLE);
//        } else {
//            cauhinhvanchuyen.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.checkboxTuGiao:
//                if (checkboxTuGiao.isChecked()) {
//                    cauhinhvanchuyen.setVisibility(View.VISIBLE);
//                } else {
//                    cauhinhvanchuyen.setVisibility(View.INVISIBLE);
//                }
//                break;
//            case R.id.cauhinhvanchuyen:
//                Intent intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
//                startActivity(intent);
//                break;
            case R.id.checkboxChuyenKhoan:
                if (checkboxChuyenKhoan.isChecked()) {
                    layoutThongTinChuyenKhoan.setVisibility(View.VISIBLE);
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layoutThongTinChuyenKhoan.setLayoutParams(params);
                } else {
                    layoutThongTinChuyenKhoan.setVisibility(View.INVISIBLE);
                    params.height = 0;
                    layoutThongTinChuyenKhoan.setLayoutParams(params);
                }
                break;
            case R.id.dangKy:
                saveData();
                break;
        }
    }

    private void saveData() {
        HinhThucGiaoHang hinhThucGiaoHang = new HinhThucGiaoHang();

//        hinhThucGiaoHang.setKhacHangDenLay(checkboxKhachHangDenLay.isChecked());
//        hinhThucGiaoHang.setQuaDoiTac(checkboxDonViVanChuyen.isChecked());
//        hinhThucGiaoHang.setTuGiao(checkboxTuGiao.isChecked());
        hinhThucGiaoHang.setChuyenKhoan(checkboxChuyenKhoan.isChecked());
        hinhThucGiaoHang.setTienMat(checkboxTienMat.isChecked());

//        int dem1 = demHinhThucGiaoHang(hinhThucGiaoHang);
        int dem2 = demHinhThucChuyenKhoan(hinhThucGiaoHang);

        String thongTinChuyenKhoan = edtThongTinChuyenKhoan.getText().toString();
        String noiDungThoanhToan = ghiChu.getText().toString();
        if (!hinhThucGiaoHang.isChuyenKhoan() && thongTinChuyenKhoan.isEmpty()) {
            thongTinChuyenKhoan = "";
        }
        if (noiDungThoanhToan.isEmpty()) {
            noiDungThoanhToan = "";
        }
//        if (dem1 < 1) {
//            Toast.makeText(CuaHangOnlineActivity.this, "Ít nhất một hình thức giao hàng", Toast.LENGTH_SHORT).show();
//        } else
        if (dem2 < 1) {
            Toast.makeText(CuaHangOnlineActivity.this, "Ít nhất một hình thanh toán", Toast.LENGTH_SHORT).show();
        }else if (hinhThucGiaoHang.isChuyenKhoan() && thongTinChuyenKhoan.isEmpty()) {
            edtThongTinChuyenKhoan.setError("Nhập thông tin chuyển khoản");
            edtThongTinChuyenKhoan.requestFocus();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            dangKy.setText("");
            hinhThucGiaoHang.setGhiChu(noiDungThoanhToan);
            hinhThucGiaoHang.setThongTinChuyenKhoan(thongTinChuyenKhoan);

            mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin/chuyenKhoan").setValue(hinhThucGiaoHang.isChuyenKhoan());
            mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin/ghiChu").setValue(hinhThucGiaoHang.getGhiChu());
            mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin/thongTinChuyenKhoan").setValue(hinhThucGiaoHang.getThongTinChuyenKhoan());
            mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin/tienMat").setValue(hinhThucGiaoHang.isTienMat()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressBar.setVisibility(View.INVISIBLE);
                    dangKy.setText("Lưu");
                    new SupportSaveLichSu(CuaHangOnlineActivity.this, "Thay đổi thông tin của hàng");
                    Toast.makeText(CuaHangOnlineActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    dangKy.setText("Lưu");
                    Toast.makeText(CuaHangOnlineActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                break;
            case R.id.sanpham:
                intent = new Intent(this, TaoSanPhamOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quangcao:
                intent = new Intent(this, QuangCaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.thongtin:
                intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.giolamviec:
                intent = new Intent(this, ThoiGianLamViecOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.vanchuyen:
                intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.khuyenmai:
                intent = new Intent(this, ListKhuyenMai.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

//    private int demHinhThucGiaoHang(HinhThucGiaoHang hinhThucGiaoHang) {
//        int dem = 0;
//        if (hinhThucGiaoHang.isTuGiao()) {
//            dem ++;
//        }
    
//        if (hinhThucGiaoHang.isKhacHangDenLay()) {
//            dem ++;
//        }

//        if (hinhThucGiaoHang.isQuaDoiTac()) {
//            dem ++;
//        }
//
//        return dem;
//    }

    private int demHinhThucChuyenKhoan(HinhThucGiaoHang hinhThucGiaoHang) {
        int dem = 0;
        if (hinhThucGiaoHang.isChuyenKhoan()) {
            dem ++;
        }
        if (hinhThucGiaoHang.isTienMat()){
            dem ++;
        }

        return dem;
    }

}