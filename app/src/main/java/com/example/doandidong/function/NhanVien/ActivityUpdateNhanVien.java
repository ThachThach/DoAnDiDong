package com.example.doandidong.function.NhanVien;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.NhanVien_CaLam.CaLam;
import com.example.doandidong.Model.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ActivityUpdateNhanVien extends AppCompatActivity {

    private EditText editHoTen, editPhone;
    private CheckBox checkBep,checkQLNV,checkQLSP,checkOder ,checkThuchi, checkquanlycuahangonline, checkkhachhang, checkkhuyenmai;
    private TextView Th2,Th3,Th4,Th5,Th6,Th7,chuNhat;
    private CaLam caLam = new CaLam();
    private   Boolean QUANLYSP = false;
    private  Boolean QUANLYNV = false;
    private  Boolean THUCHI = false;
    private  Boolean ODER = false;
    private  Boolean BEP = false;
    private Button btnCapNhat, btnHuy,btnCapNhatDialog,btnHuyDialog,checkBoxCaSang, checkBoxCaChieu, checkBoxCaToi;
    private NhanVien nhanVien;
    private ArrayList<Boolean> congViec = new ArrayList<>(5);
    private  Boolean T2 = false;
    private  Boolean T3 = false;
    private  Boolean T4 = false;
    private  Boolean T5 = false;
    private  Boolean T6 = false;
    private  Boolean T7 = false;
    private  Boolean CN = false;
    private Dialog dialog;
    private Window window;
    private Boolean[][] mangNgay2 = new Boolean[3][7];
    private Boolean[] cSang = new Boolean[7];
    private Boolean[] cTrua = new Boolean[7];
    private Boolean[] cToi = new Boolean[7];
    private int loai = 1; //1: casang, 2: trua, 3: toi
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_USER = "user";
    private DatabaseReference mData;
    private Boolean [] cSang1 = new Boolean[7];
    private Boolean [] cTrua1 = new Boolean[7];
    private Boolean [] cToi1 = new Boolean[7];
    private Intent intent;
    private String ID_CUAHANG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenhanvien);
        editHoTen = findViewById(R.id.edtTenNhanVienUpdate);
        editPhone = findViewById(R.id.edtPhoneUpdate);
        checkQLNV = findViewById(R.id.checkcongviecQuanlynhanvienUpdate);
        checkQLSP = findViewById(R.id.checkcongviecQuanlysanphamUpdate);
        checkBep = findViewById(R.id.checkcongviecBepUpdate);
        checkOder = findViewById(R.id.checkcongviecOderUpdate);
        checkThuchi = findViewById(R.id.checkcongviecThuchiUpdate);
        checkquanlycuahangonline = findViewById(R.id.checkquanlycuahangonline);
        checkkhachhang = findViewById(R.id.checkkhachhang);
        checkkhuyenmai = findViewById(R.id.checkkhuyenmai);
        btnCapNhat = findViewById(R.id.btnThayDoiNhanVien);
        btnHuy = findViewById(R.id.btnhuyThayDoi);
        checkBoxCaSang = findViewById(R.id.checkCaSangUpdate);
        checkBoxCaChieu = findViewById(R.id.checkCaChieuUpdate);
        checkBoxCaToi = findViewById(R.id.checkCaToiUpdate);
        nhanVien = (NhanVien) getIntent().getSerializableExtra("Key_arrayNV");

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        //dialog

        dialog = new Dialog(ActivityUpdateNhanVien.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thutrongtuan);
        window = dialog.getWindow();
        btnHuyDialog = dialog.findViewById(R.id.btnhuyDiaLogNgay);
        btnCapNhatDialog = dialog.findViewById(R.id.btnthemDiaLogNgay);

        Th2 = dialog.findViewById(R.id.checkBox2);
        Th3 = dialog.findViewById(R.id.checkBox3);
        Th4 = dialog.findViewById(R.id.checkBox4);
        Th5 = dialog.findViewById(R.id.checkBox5);
        Th6 = dialog.findViewById(R.id.checkBox6);
        Th7 = dialog.findViewById(R.id.checkBox7);
        chuNhat = dialog.findViewById(R.id.checkBox8);
        //setDulieu
        editHoTen.setText(nhanVien.getUsername());
        editPhone.setText(nhanVien.getPhone());
        congViec = nhanVien.getChucVu();
        caLam = nhanVien.getCaLam();
        cSang = caLam.getCaSang().toArray(new Boolean[0]);
        cTrua = caLam.getCaChieu().toArray(new Boolean[0]);
        cToi = caLam.getCaToi().toArray(new Boolean[0]);
        //firebase
        mData = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_USER);
        onClickThu();
        getChucVu();
        getCaSang();
        getCaChieu();
        getCaToi();
        checkBoxCaSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 1;
                Casang(Gravity.CENTER);
            }
        });
        checkBoxCaChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 2;
                Cachieu(Gravity.CENTER);
            }
        });
        checkBoxCaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = 3;
                Catoi(Gravity.CENTER);
            }
        });
        CapNhatNhanVien();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              intent = new Intent();
                finish();
            }
        });
    }


    public void Casang(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        for(int i =0;i< 7;i++){
            cSang1[i] = cSang[i];
        }
        if(cSang[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }



        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i < 7;i++){
                    cSang[i] = cSang1[i];
                    mangNgay2[0][i] = cSang1[i];
                }

                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void Cachieu(int gravity){

        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        for(int i =0;i< 7;i++){
            cTrua1[i] = cTrua[i];
        }

        if(cTrua[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }



        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i =0; i<7;i++){
                    cTrua[i] = cTrua1[i];
                    mangNgay2[1][i] = cTrua1[i];
                }
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    public void Catoi(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }

        for(int i =0;i< 7;i++){
            cToi1[i] = cToi[i];
        }

        if(cToi[0] == false){
            T2 = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[1] == false){
            T3 = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[2] == false){
            T4 = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[3] == false){
            T5 = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[4] == false){
            T6 = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[5] == false){
            T7 = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[6] == false){
            CN = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }


        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i =0; i<7;i++){
                    cToi[i] = cToi1[i];
                    mangNgay2[2][i] = cToi1[i];
                }

                dialog.dismiss();
            }


        });
        dialog.show();
    }

    public void getChucVu(){
        if(congViec.get(0) == true){
            checkQLNV.setChecked(true);
        } else {
            checkQLNV.setChecked(false);
        }
        if (congViec.get(1) == true){
            checkQLSP.setChecked(true);
        }else {
            checkQLSP.setChecked(false);
        }
        if (congViec.get(2) == true){
            checkThuchi.setChecked(true);
        }else {
            checkThuchi.setChecked(false);
        }
        if (congViec.get(3) == true){
            checkOder.setChecked(true);
        }else {
            checkOder.setChecked(false);
        }
        if (congViec.get(4) == true){
            checkBep.setChecked(true);
        }else {
            checkBep.setChecked(false);
        }
        if (congViec.get(5) == true) {
            checkquanlycuahangonline.setChecked(true);
        }else {
            checkquanlycuahangonline.setChecked(false);
        }
        if (congViec.get(6) == true) {
            checkkhachhang.setChecked(true);
        }else {
            checkkhachhang.setChecked(false);
        }
        if (congViec.get(7) == true) {
            checkkhuyenmai.setChecked(true);
        }else {
            checkkhuyenmai.setChecked(false);
        }
    }

    public void getCaSang(){

        if(cSang[0] == false){
            T2 = false;
            mangNgay2[0][0] = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            mangNgay2[0][0] = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[1] == false){
            T3 = false;
            mangNgay2[0][1] = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            mangNgay2[0][1] = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[2] == false){
            T4 = false;
            mangNgay2[0][2] = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            mangNgay2[0][2] = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[3] == false){
            T5 = false;
            mangNgay2[0][3] = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            mangNgay2[0][3] = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[4] == false){
            T6 = false;
            mangNgay2[0][4] = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            mangNgay2[0][4] = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[5] == false){
            T7 = false;
            mangNgay2[0][5] = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            mangNgay2[0][5] = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cSang[6] == false){
            CN = false;
            mangNgay2[0][6] = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            mangNgay2[0][6] = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }
    }

    public void getCaChieu(){

        if(cTrua[0] == false){
            T2 = false;
            mangNgay2[1][0] = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            mangNgay2[1][0] = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[1] == false){
            T3 = false;
            mangNgay2[1][1] = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            mangNgay2[1][1] = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[2] == false){
            T4 = false;
            mangNgay2[1][2] = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            mangNgay2[1][2] = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[3] == false){
            T5 = false;
            mangNgay2[1][3] = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            mangNgay2[1][3] = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[4] == false){
            T6 = false;
            mangNgay2[1][4] = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            mangNgay2[1][4] = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[5] == false){
            T7 = false;
            mangNgay2[1][5] = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            mangNgay2[1][5] = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cTrua[6] == false){
            CN = false;
            mangNgay2[1][6] = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            mangNgay2[1][6] = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }

    }

    public void getCaToi(){


        if(cToi[0] == false){
            T2 = false;
            mangNgay2[2][0] = false;
            Th2.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T2 = true;
            mangNgay2[2][0] = true;
            Th2.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[1] == false){
            T3 = false;
            mangNgay2[2][1] = false;
            Th3.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T3 = true;
            mangNgay2[2][1] = true;
            Th3.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[2] == false){
            T4 = false;
            mangNgay2[2][2] = false;
            Th4.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T4 = true;
            mangNgay2[2][2] = true;
            Th4.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[3] == false){
            T5 = false;
            mangNgay2[2][3] = false;
            Th5.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T5 = true;
            mangNgay2[2][3] = true;
            Th5.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[4] == false){
            T6 = false;
            mangNgay2[2][4] = false;
            Th6.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T6 = true;
            mangNgay2[2][4] = true;
            Th6.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[5] == false){
            T7 = false;
            mangNgay2[2][5] = false;
            Th7.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            T7 = true;
            mangNgay2[2][5] = true;
            Th7.setBackgroundResource(R.drawable.bg_textview_10);
        }

        if(cToi[6] == false){
            CN = false;
            mangNgay2[2][6] = false;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
        } else {
            CN = true;
            mangNgay2[2][6] = true;
            chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
        }
    }

    public void onClickThu(){
        Th2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang1[0] == false){
                        T2 = true;
                        cSang1[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
                        cSang1[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[0] == false){
                        T2 = true;
                        cTrua1[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
                        cTrua1[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[0] == false){
                        T2 = true;
                        cToi1[0] = true;
                        Th2.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T2 = false;
                        cToi1[0] = false;
                        Th2.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        Th3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang1[1] == false){
                        T3 = true;
                        cSang1[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cSang1[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[1] == false){
                        T3 = true;
                        cTrua1[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cTrua1[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[1] == false){
                        T3 = true;
                        cToi1[1] = true;
                        Th3.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T3 = false;
                        cToi1[1] = false;
                        Th3.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang1[2] == false){
                        T4 = true;
                        cSang1[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cSang1[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[2] == false){
                        T4 = true;
                        cTrua1[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cTrua1[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[2] == false){
                        T4 = true;
                        cToi1[2] = true;
                        Th4.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T4 = false;
                        cToi1[2] = false;
                        Th4.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang1[3] == false){
                        T5= true;
                        cSang1[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cSang1[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[3] == false){
                        T5= true;
                        cTrua1[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cTrua1[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[3] == false){
                        T5= true;
                        cToi1[3] = true;
                        Th5.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T5 = false;
                        cToi1[3] = false;
                        Th5.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        Th6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang1[4] == false){
                        T6= true;
                        cSang1[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cSang1[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[4] == false){
                        T6= true;
                        cTrua1[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cTrua1[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[4] == false){
                        T6= true;
                        cToi1[4] = true;
                        Th6.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T6 = false;
                        cToi1[4] = false;
                        Th6.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

            }
        });
        Th7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loai == 1) {
                    if(cSang1[5] == false){
                        T7= true;
                        cSang1[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cSang1[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[5] == false){
                        T7= true;
                        cTrua1[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cTrua1[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[5] == false){
                        T7= true;
                        cToi1[5] = true;
                        Th7.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        T7 = false;
                        cToi1[5] = false;
                        Th7.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
        chuNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    if(cSang1[6] == false){
                        CN= true;
                        cSang1[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cSang1[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 2) {
                    if(cTrua1[6] == false){
                        CN= true;
                        cTrua1[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cTrua1[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }

                if (loai == 3) {
                    if(cToi1[6] == false){
                        CN= true;
                        cToi1[6] = true;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_10);
                    }
                    else {
                        CN = false;
                        cToi1[6] = false;
                        chuNhat.setBackgroundResource(R.drawable.bg_textview_16);
                    }
                }
            }
        });
    }

    public void CapNhatNhanVien(){
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editHoTen.getText().toString().isEmpty()){
                    Toast.makeText(ActivityUpdateNhanVien.this,"Hãy nhập tên nhân viên!", Toast.LENGTH_LONG).show();
                }else if(editPhone.getText().toString().isEmpty()){
                    Toast.makeText(ActivityUpdateNhanVien.this,"Hãy nhập số điện thoại!", Toast.LENGTH_LONG).show();
                }else {
                    //cong viec
                    if (checkQLNV.isChecked()){
                        congViec.set(0,true);
                    }
                    else {
                        congViec.set(0,false);
                    }
                    if (checkQLSP.isChecked()){
                        congViec.set(1,true);
                    }
                    else {
                        congViec.set(1,false);
                    }
                    if (checkThuchi.isChecked()){
                        congViec.set(2,true);
                    }
                    else {
                        congViec.set(2,false);
                    }
                    if (checkOder.isChecked()){

                        congViec.set(3,true);
                    }
                    else {
                        congViec.set(3,false);
                    }
                    if (checkBep.isChecked()){
                        congViec.set(4,true);
                    }
                    else {
                        congViec.set(4,false);
                    }
                    if (checkquanlycuahangonline.isChecked()){
                        congViec.set(5,true);
                    }
                    else {
                        congViec.set(5,false);
                    }
                    if (checkkhachhang.isChecked()){
                        congViec.set(6,true);
                    }
                    else {
                        congViec.set(6,false);
                    }
                    if (checkkhuyenmai.isChecked()){
                        congViec.set(7,true);
                    }
                    else {
                        congViec.set(7,false);
                    }
                    caLam.set1(mangNgay2[0]);
                    caLam.set2(mangNgay2[1]);
                    caLam.set3(mangNgay2[2]);
                    String name = editHoTen.getText().toString();
                    String phone = editPhone.getText().toString();

                    mData.child(nhanVien.getId()).child("username").setValue(name);
                    mData.child(nhanVien.getId()).child("phone").setValue(phone);
                    mData.child(nhanVien.getId()).child("caLam").setValue(caLam);
                    mData.child(nhanVien.getId()).child("chucVu").setValue(congViec);
                    new SupportSaveLichSu(ActivityUpdateNhanVien.this, "Đã update nhân viên:" +nhanVien.getUsername());
                    finish();
                }
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
