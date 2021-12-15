package com.example.doandidong.function.KhuyenMaiOffLine;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterChonKmInKmThanhToan;
import com.example.doandidong.Model.KhuyenMaiOffModel;
import com.example.doandidong.Model.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class KhuyenMaiThanhToan extends AppCompatActivity {
TextView tv_tenkhuyenmai,tv_ngaybatdau,tv_ngayketthuc,tv_nhomkhachhang,tv_noidung;
ListKhuyenMaiOffModel listchuyen;
AdapterChonKmInKmThanhToan adapterChonKmInKmThanhToan;
RecyclerView rv_1;
Button bnt_xacnhan;
private Window window, window1, window2;
private Dialog dialog, dialog1, dialog2,dialog3,dialog4;
TextView title;
public static ArrayList<KhuyenMaiOffModel> khuyenMaiOffModel =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai_thanh_toan);
        tv_tenkhuyenmai = findViewById(R.id.tv_tenkhuyenmai);
        tv_ngaybatdau = findViewById(R.id.tv_ngaybatdau);
        tv_ngayketthuc = findViewById(R.id.tv_ngayketthuc);
        tv_nhomkhachhang = findViewById(R.id.tv_nhomkhachhang);
        tv_noidung = findViewById(R.id.tv_noidung);
        bnt_xacnhan = findViewById(R.id.bnt_xacnhan);
        Gson gson = new Gson();
        if (getIntent().getStringExtra("listchuyen") != null) {
            String ListCartDaCo = getIntent().getStringExtra("listchuyen");
            Type type3 = new TypeToken<ListKhuyenMaiOffModel>() {
            }.getType();
            listchuyen = gson.fromJson(ListCartDaCo, type3);
        }
        if(listchuyen!=null){
            tv_tenkhuyenmai.setText(listchuyen.getTenkhuyenmai());
            tv_ngaybatdau.setText(listchuyen.getNgaybatdau());
            tv_ngayketthuc.setText(listchuyen.getNgayketthuc());
            tv_nhomkhachhang.setText(listchuyen.getNhomkhachhang());
            tv_noidung.setText(listchuyen.getNoidungkhuyenmai());
        }
        rv_1 = findViewById(R.id.rv_1);
        adapterChonKmInKmThanhToan = new AdapterChonKmInKmThanhToan(listchuyen.getKhuyenMaiOffModels(), KhuyenMaiThanhToan.this);
        rv_1.setLayoutManager(new LinearLayoutManager(KhuyenMaiThanhToan.this, LinearLayoutManager.VERTICAL, false));
        rv_1.setAdapter(adapterChonKmInKmThanhToan);
        adapterChonKmInKmThanhToan.notifyDataSetChanged();
        hamclick();



    }
    public void hamclick(){
        bnt_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailonghoitruockhitao();
                dialog4.show();

            }
        });
    }
    private void dailonghoitruockhitao() {
        dialog4 = new Dialog(KhuyenMaiThanhToan.this);
        dialog4.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog4.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog4.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog4.setCancelable(false); //Optional
        dialog4.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
        title =dialog4.findViewById(R.id.title);
        title.setText("Xác Nhận Đã Chọn Khuyến Mãi!!!");
        Button Okay = dialog4.findViewById(R.id.btn_okay);
        Button Cancel = dialog4.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khuyenMaiOffModel = new ArrayList<>();
                khuyenMaiOffModel = adapterChonKmInKmThanhToan.PublicArraylist();
                dialog4.dismiss();
                finish();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });

    }


}