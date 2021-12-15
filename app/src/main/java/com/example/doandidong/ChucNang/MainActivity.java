package com.example.doandidong.ChucNang;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.SQL.Database_order;
import com.example.doandidong.ChucNang.Account.SignInActivity;
import com.example.doandidong.ChucNang.Account.ThongTinAccountActivity;
import com.example.doandidong.ChucNang.BaoCao.BaoCaoTongQuanActivity;
import com.example.doandidong.ChucNang.BepBar.BepActivity;
import com.example.doandidong.ChucNang.CuaHangOnline.CuaHangOnlineActivity;
import com.example.doandidong.ChucNang.DatBan.XacNhanDatBan;
import com.example.doandidong.ChucNang.DonHangOnline.DuyetDonHangActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Database_order database_order;
    FirebaseAuth mFirebaseAuth;
    RelativeLayout ordermenu, baocao, bep, account;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String ID_CUAHANG;
    private String ID_USER;
    private NhanVien nhanVien;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private boolean isChu;
    private TextView lbl_thongbao;
    private Dialog dialog;
    private String thongBao = "";
    private LinearLayout logoutdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ordermenu = findViewById(R.id.orderbutton);
        baocao = findViewById(R.id.baocao);
        bep = findViewById(R.id.bep);
        account = findViewById(R.id.account);

        com.example.doandidong.SQL.ThongTinCuaHangSql thongTinCuaHangSqlDB = new com.example.doandidong.SQL.ThongTinCuaHangSql(MainActivity.this, "app_database.sqlite", null, 2);
        thongTinCuaHangSqlDB.createTableUser();

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        nhanVien = thongTinCuaHangSql.selectUser();
        isChu = thongTinCuaHangSql.isChu();
        boolean ss = thongTinCuaHangSql.isChu();
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        ID_USER = thongTinCuaHangSql.IDUser();

        Log.d("ID_USER", ID_USER + " - " + ID_CUAHANG);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();


        mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG + "/user/" + ID_USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cursor cursor = thongTinCuaHangSqlDB.selectUser();
                nhanVien = snapshot.getValue(NhanVien.class);
                String quyen = "";
                for (int i = 0; i < nhanVien.getChucVu().size(); i++) {
                    if (nhanVien.getChucVu().get(i)) {
                        quyen = quyen + "1";
                    } else {
                        quyen = quyen + "0";
                    }
                }

                if (cursor.getCount() > 0) {
                    String IdOld = "";
                    while (cursor.moveToNext()) {
                        IdOld = cursor.getString(0);
                    }
                    thongTinCuaHangSqlDB.UpdateUser(nhanVien.getId(), nhanVien.getId(), nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                } else {
                    thongTinCuaHangSqlDB.InsertUser(nhanVien.getId(), nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ordermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(3)) {
                    Intent intent = new Intent(MainActivity.this, OrderMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien.getChucVu().get(2) || isChu) {
                    Intent intent = new Intent(MainActivity.this, BaoCaoTongQuanActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ssss", nhanVien.getChucVu().get(4)+"");
                if (nhanVien.getChucVu().get(4) || isChu) {
                    Intent intent = new Intent(MainActivity.this, BepActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThietLapActivity.class);
                startActivity(intent);
            }
        });

        // Write a message to the database
        mFirebaseAuth = FirebaseAuth.getInstance();

        /*lay id tung phan*/
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*lay ra ten cua toolbar*/
//        setSupportActionBar(toolbar);

        /* lay ra action bar*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_homes);
        navigationView.setEnabled(false);

        mFirebaseDatabase.child("ADMIN/ThongBaoKhoaCuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    thongBao = snapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG + "/khoa").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    ordermenu.setEnabled(true);
                    baocao.setEnabled(true);
                    bep.setEnabled(true);
                    account.setEnabled(true);
                    navigationView.setEnabled(true);
                }else {
                    openDialogEditTong(Gravity.CENTER);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogEditTong(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thong_bao_khoa);
        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        TextView lbl_thongbao = dialog.findViewById(R.id.lbl_thongbao);
        logoutdialog = dialog.findViewById(R.id.logout);

        logoutdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(MainActivity.this, SignInActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                finish();
            }
        });

        lbl_thongbao.setText(thongBao);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "nhấn trở lại 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homes:
                break;
            case R.id.profile:
                Intent intent11 = new Intent(MainActivity.this, ThongTinAccountActivity.class);
                startActivity(intent11);
                break;
            case R.id.lichsu:
                Intent intent = new Intent(MainActivity.this, LichSuHoatDongActivity.class);
                startActivity(intent);
                break;
            case R.id.bep:
                if (nhanVien.getChucVu().get(4) || isChu) {
                    Intent intent3 = new Intent(MainActivity.this, BepActivity.class);
                    startActivity(intent3);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.oder:
                if (nhanVien.getChucVu().get(3)) {
                    Intent intent4 = new Intent(MainActivity.this, OrderMenu.class);
                    startActivity(intent4);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(MainActivity.this, SignInActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}