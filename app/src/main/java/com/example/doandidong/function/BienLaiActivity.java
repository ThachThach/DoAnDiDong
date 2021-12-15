package com.example.doandidong.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.doandidong.Adapter.BienLaiAapter;
import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.model.BienLai;
import com.example.doandidong.function.BaoCao.model.SanPham;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BienLaiActivity extends AppCompatActivity {

    private TextView tv_ngay, tv_id_don_hang,tv_nhanvien, tv_khuyenmai, tv_tongcong, tv_thanhtoan;
    private RecyclerView recycleview;
    private BienLaiAapter bienLaiAapter;
    private BienLai bienLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_lai);
        IDLayout();
        bienLai = (BienLai) getIntent().getSerializableExtra("BIENLAI");

        tv_ngay.setText(setUpDate(bienLai.getKey()));
        tv_id_don_hang.setText(bienLai.getKey());
        tv_nhanvien.setText(bienLai.getId_nhanvien());
        tv_tongcong.setText(tongDon(bienLai.getSanpham())+"");
        tv_thanhtoan.setText(bienLai.getTongtien()+"");

        displayItem();
    }

    private void IDLayout() {
        tv_ngay = findViewById(R.id.tv_ngay);
        tv_id_don_hang = findViewById(R.id.tv_id_don_hang);
        tv_nhanvien = findViewById(R.id.tv_nhanvien);
        tv_khuyenmai = findViewById(R.id.tv_khuyenmai);
        tv_tongcong = findViewById(R.id.tv_tongcong);
        tv_thanhtoan = findViewById(R.id.tv_thanhtoan);
        recycleview = findViewById(R.id.recycleview);
    }

    private String setUpDate(String str) {
        long log = Long.parseLong(str);
        Timestamp timestamp = new Timestamp(log);
        java.sql.Date date = new java.sql.Date(timestamp.getTime());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dt = formatter.format(date);

        return  dt;
    }

    private void displayItem(){
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));
        bienLaiAapter = new BienLaiAapter(this, bienLai.getSanpham());
        recycleview.setAdapter(bienLaiAapter);
        bienLaiAapter.notifyDataSetChanged();
    }

    private Double tongDon(ArrayList<SanPham> sanPhams) {
        Double tongDon = 0.0;
        for (int i =0; i < sanPhams.size(); i++) {
            tongDon += sanPhams.get(i).getGiaProudct();
        }

        return  tongDon;
    }
}