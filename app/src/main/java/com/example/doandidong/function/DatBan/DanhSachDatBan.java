package com.example.doandidong.function.DatBan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterDatBan.RvDatBanAdapter;
import com.example.doandidong.Model.DatBan.DatBanModel;
import com.example.doandidong.Model.DatBan.ID_datban;
import com.example.doandidong.R;
import java.util.ArrayList;

public class DanhSachDatBan extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<DatBanModel> datBanModels;
    ArrayList<ID_datban> ID_datbans;
    private FirebaseDatabase firebaseDatabase;
    String id_ban;
    private DatabaseReference mDatabase1;
    String id_khuvuc;
    String id_bk;
    String id;
    RecyclerView recyclerView;
    String abc, tenban;
    ProgressBar progressBar;
    TextView rong;
    private Toolbar toolbar;
    RvDatBanAdapter datBanAdapter;
    private Dialog dialogban;
    Window window;
    String id_CuaHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_dat_ban);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        abc = id_ban + "_" + id_khuvuc;
        tenban = intent.getStringExtra("tenban");
        toolbar = findViewById(R.id.toolbars);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("DS_" + tenban);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rong = findViewById(R.id.tvchuadattruoc);
        progressBar.setVisibility(View.VISIBLE);
        dialogban = new Dialog(DanhSachDatBan.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dialog_canhbao);
        window = dialogban.getWindow();
        hamdatban();
    }

    private void hamdatban() {
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID_datbans = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        datBanModels = new ArrayList<>();
                        id = postSnapshot.getKey();
                        DataSnapshot sss = postSnapshot;
                        for (DataSnapshot aaa : sss.getChildren()) {
                            id_bk = aaa.child("id_bk").getValue() + "";
                            if (id_bk.equals(abc)) {
                                String id_ngaydat = aaa.getKey();
                                String giodat = aaa.child("giodat").getValue() + "";
                                String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                String ngaydat = aaa.child("ngaydat").getValue() + "";
                                String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                String tenban = aaa.child("tenban").getValue() + "";
                                datBanModels.add(new DatBanModel(id_ngaydat, giodat, gioketthuc, id_bk, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang, tenban));
                                ID_datban datban = new ID_datban(id, datBanModels);
                                ID_datbans.add(datban);
                            }
                        }
                    }

                }
                else {
                    rong.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView = findViewById(R.id.rv_1);
                datBanAdapter = new RvDatBanAdapter(ID_datbans, DanhSachDatBan.this, window, dialogban);
                recyclerView.setLayoutManager(new LinearLayoutManager(DanhSachDatBan.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(datBanAdapter);
                datBanAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void delete(final int position) {

        new AlertDialog.Builder(DanhSachDatBan.this).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ID_datbans.size() > 0) {
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan").child(abc).child(ID_datbans.get(position).getDatBanModels().get(position).getId_ngaydat()).removeValue();
                    ID_datbans.remove(position);
                }
            }
        }).setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;

    }
}
