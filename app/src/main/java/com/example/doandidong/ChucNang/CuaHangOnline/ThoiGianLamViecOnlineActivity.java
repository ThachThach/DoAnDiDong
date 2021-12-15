package com.example.doandidong.ChucNang.CuaHangOnline;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.CuaHangOnline.Data.ThoiGian;
import com.example.doandidong.ChucNang.KhuyenMai.ListKhuyenMai;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThoiGianLamViecOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView thoiGianBatDau, thoiGianKetThuc, tamDung, xacNhan;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private DatabaseReference mFirebaseDatabase;
    private TextView thonbao;
    private ProgressBar progressBar,progressBarMini;
    private ScrollView scrollView;
    private ThoiGian thoiGian;

    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private boolean isCheckStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_gian_lam_viec_online);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.giolamviec);
        thoiGianBatDau.setEnabled(false);
        thoiGianKetThuc.setEnabled(false);
        xacNhan.setEnabled(false);
        progressBarMini.setVisibility(View.GONE);

        getDataFirebase();

    }

    private void IDLayout() {
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        thoiGianBatDau = findViewById(R.id.thoiGianBatDau);
        thoiGianKetThuc = findViewById(R.id.thoiGianKetThuc);
        tamDung = findViewById(R.id.tamDung);
        xacNhan = findViewById(R.id.xacNhan);
        thonbao = findViewById(R.id.thonbao);
        progressBar = findViewById(R.id.progressBar2);
        progressBarMini = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.scrollView);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        thoiGianBatDau.setOnClickListener(this);
        thoiGianKetThuc.setOnClickListener(this);
        xacNhan.setOnClickListener(this);
        tamDung.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thoiGianBatDau:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, R.style.TimePicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                thoiGianBatDau.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog1.show();
                break;
            case R.id.thoiGianKetThuc:
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, R.style.TimePicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                thoiGianKetThuc.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog2.show();
                break;
            case R.id.tamDung:
                kichHoat();
                break;
            case R.id.xacNhan:
                saveCheck();
                break;
        }
    }

    private void kichHoat() {
        progressBarMini.setVisibility(View.VISIBLE);
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thongtin/name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    if (isCheckStatus) {
                        isCheckStatus = false;
                        tamDung.setText("Kích hoạt");
                        tamDung.setTextColor(getColor(R.color.Python));
                        tamDung.setBackgroundResource(R.drawable.bg_kich_hoat);
                        thonbao.setText("Cửa hàng của đang không mở, kích hoạt để mở");
                        xacNhan.setEnabled(false);
                        thoiGianBatDau.setEnabled(false);
                        thoiGianKetThuc.setEnabled(false);
                        xacNhan.setVisibility(View.GONE);
                        new SupportSaveLichSu(ThoiGianLamViecOnlineActivity.this, "Đã khóa cửa hàng online");
                    } else {
                        isCheckStatus = true;
                        tamDung.setText("Tạm dừng");
                        tamDung.setTextColor(getColor(R.color.red));
                        tamDung.setBackgroundResource(R.drawable.bg_xoa_van_chuyen);
                        thonbao.setText("");
                        xacNhan.setEnabled(true);
                        thoiGianBatDau.setEnabled(true);
                        thoiGianKetThuc.setEnabled(true);
                        xacNhan.setVisibility(View.VISIBLE);
                        new SupportSaveLichSu(ThoiGianLamViecOnlineActivity.this, "Đã kích hoạt của hàng online");
                    }
                    progressBarMini.setVisibility(View.GONE);
                    mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec/status").setValue(isCheckStatus);
                }else {
                    Toast.makeText(ThoiGianLamViecOnlineActivity.this, "Chưa thiết lập thông tin cửa hàng", Toast.LENGTH_SHORT).show();
                    progressBarMini.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveCheck() {
        String batDau = thoiGianBatDau.getText().toString();
        String ketThuc = thoiGianKetThuc.getText().toString();

        long a = dataC(batDau);
        long b = dataC(ketThuc);

        if (a > b) {
            Toast.makeText(ThoiGianLamViecOnlineActivity.this, "Giờ bắt đầu sau giờ kết thúc", Toast.LENGTH_SHORT).show();
        } else {
            ThoiGian thoiGian = new ThoiGian(batDau, ketThuc, isCheckStatus);
            mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec").setValue(thoiGian).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThoiGianLamViecOnlineActivity.this, "Đã lưu!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThoiGianLamViecOnlineActivity.this, "Lưu thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private long dataC(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        try {
            Date date = formatter.parse(dateStr);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp.getTime();
        } catch (Exception e) {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp.getTime();
        }
    }

    private void getDataFirebase() {
        mFirebaseDatabase.child("cuaHang").child(ID_CUAHANG).child("thoiGianLamViec").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    thoiGian = snapshot.getValue(ThoiGian.class);
                    thoiGianBatDau.setText(thoiGian.getBatDau());
                    thoiGianKetThuc.setText(thoiGian.getKetThuc());
                    isCheckStatus = thoiGian.isStatus();

                    if (isCheckStatus) {
                        thoiGianBatDau.setEnabled(true);
                        thoiGianKetThuc.setEnabled(true);
                        tamDung.setText("Tạm dừng");
                        tamDung.setTextColor(getColor(R.color.red));
                        tamDung.setBackgroundResource(R.drawable.bg_xoa_van_chuyen);
                        thonbao.setText("");
                        xacNhan.setEnabled(true);
                        xacNhan.setVisibility(View.VISIBLE);
                    } else {
                        thoiGianBatDau.setEnabled(false);
                        thoiGianKetThuc.setEnabled(false);
                        tamDung.setText("Kích hoạt");
                        tamDung.setTextColor(getColor(R.color.Python));
                        tamDung.setBackgroundResource(R.drawable.bg_kich_hoat);
                        thonbao.setText("Cửa hàng của  đang không mở, kích hoạt để mở");
                        xacNhan.setEnabled(false);
                        xacNhan.setVisibility(View.GONE);
                    }
                } else {
                    thoiGian = new ThoiGian("8:00", "20:00", false);
                    thoiGianBatDau.setEnabled(false);
                    thoiGianKetThuc.setEnabled(false);
                    tamDung.setText("Kích hoạt");
                    tamDung.setTextColor(getColor(R.color.Python));
                    tamDung.setBackgroundResource(R.drawable.bg_kich_hoat);
                    thonbao.setText("Cửa hàng của đang không mở, kích hoạt để mở");
                    xacNhan.setEnabled(false);
                    xacNhan.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                scrollView.setAlpha(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThoiGianLamViecOnlineActivity.this, "Lưu thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
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
}
