package com.example.doandidong.function.NhanVien;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.LichSuHoatDong;
import com.example.doandidong.Model.NhanVien_CaLam.CaLam;
import com.example.doandidong.Model.NhanVien_CaLam.ChamCong;
import com.example.doandidong.Model.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNhanVien extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mData2;
    private FirebaseUser user;
    private EditText edtTenNhanVien, edtEmail, edtPassword, edtPhone;
    private Button btnTaoNhanVien,btnhuyTaoNhanVien,checkBoxCaSang, checkBoxCaChieu, checkBoxCaToi,btnHuyDiaLogNgay,btnThemDiaLogNgay;
    private CaLam caLam = new CaLam();
    private  Boolean T2 = false;
    private  Boolean T3 = false;
    private  Boolean T4 = false;
    private  Boolean T5 = false;
    private  Boolean T6 = false;
    private  Boolean T7 = false;
    private  Boolean CN = false;
    private ArrayList<Boolean> congViec = new ArrayList<>(5);
    private  CheckBox checkBep,checkQLNV,checkQLSP,checkOder ,checkThuchi, checkquanlycuahangonline, checkkhachhang, checkkhuyenmai;;
    private TextView Th2,Th3,Th4,Th5,Th6,Th7,chuNhat;
    private String STR_USER = "user";
    private Dialog dialog;
    private Window window;
    private  NhanVien nhanVien;
    private String ID_CUAHANG;

    private Boolean[][] mangNgay2 = new Boolean[3][7];
    private Boolean[] cSang = new Boolean[] {false, false, false, false, false,false,false};
    private Boolean[] cTrua = new Boolean[] {false, false, false, false, false,false,false};
    private Boolean[] cToi = new Boolean[] {false, false, false, false, false,false,false};
    private Boolean [] cSang1 = new Boolean[7];
    private Boolean [] cTrua1 = new Boolean[7];
    private Boolean [] cToi1 = new Boolean[7];
    
    private int loai = 1; //1: casang, 2: trua, 3: toi

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnhanvien);

        //add ca lam
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 7; j ++) {
                mangNgay2[i][j] = false;
            }
        }
        
        //add ca lam
        congViec.add(0,false);
        congViec.add(1,false);
        congViec.add(2,false);
        congViec.add(3,false);
        congViec.add(4,false);
        congViec.add(5,false);
        congViec.add(6,false);
        congViec.add(7,false);
        // dialog
        dialog = new Dialog(AddNhanVien.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thutrongtuan);
        window = dialog.getWindow();
        btnHuyDiaLogNgay = dialog.findViewById(R.id.btnhuyDiaLogNgay);
        btnThemDiaLogNgay = dialog.findViewById(R.id.btnthemDiaLogNgay);
        //congviec
        checkBep = findViewById(R.id.checkcongviecBep);
        checkQLSP = findViewById(R.id.checkcongviecQuanlysanpham);
        checkQLNV = findViewById(R.id.checkcongviecQuanlynhanvien);
        checkOder = findViewById(R.id.checkcongviecOder);
        checkThuchi =  findViewById(R.id.checkcongviecThuchi);
        checkquanlycuahangonline = findViewById(R.id.checkquanlycuahangonline);
        checkkhachhang = findViewById(R.id.checkkhachhang);
        checkkhuyenmai = findViewById(R.id.checkkhuyenmai);

        Th2 = dialog.findViewById(R.id.checkBox2);
        Th3 = dialog.findViewById(R.id.checkBox3);
        Th4 = dialog.findViewById(R.id.checkBox4);
        Th5 = dialog.findViewById(R.id.checkBox5);
        Th6 = dialog.findViewById(R.id.checkBox6);
        Th7 = dialog.findViewById(R.id.checkBox7);
        chuNhat = dialog.findViewById(R.id.checkBox8);
        //firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        edtTenNhanVien = findViewById(R.id.edtTenNhanVien);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        checkBoxCaSang = findViewById(R.id.checkCaSang);
        checkBoxCaChieu = findViewById(R.id.checkCaChieu);
        checkBoxCaToi = findViewById(R.id.checkCaToi);
        btnTaoNhanVien = findViewById(R.id.btnTaoUser);
        btnhuyTaoNhanVien = findViewById(R.id.btnhuyTaoNhanVien);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        mData2 = FirebaseDatabase.getInstance().getReference();

                onClickThu();
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
        Taonhanvien();
        btnhuyTaoNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = new Intent(AddNhanVien.this, ListNhanVien.class);
                startActivity(intent);
                finish();
            }
        });
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

        btnHuyDiaLogNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThemDiaLogNgay.setOnClickListener(new View.OnClickListener() {
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
        btnHuyDiaLogNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemDiaLogNgay.setOnClickListener(new View.OnClickListener() {
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

        btnHuyDiaLogNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemDiaLogNgay.setOnClickListener(new View.OnClickListener() {
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

    public void Taonhanvien(){
        btnTaoNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();
                mFirebaseAuth = FirebaseAuth.getInstance();
                if(email.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập Email!", Toast.LENGTH_LONG).show();
                }else if(pass.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập password!", Toast.LENGTH_LONG).show();
                } else if(edtTenNhanVien.getText().toString().isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập tên nhân viên!", Toast.LENGTH_LONG).show();
                }else if(edtPhone.getText().toString().isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Hãy nhập số điện thoại!", Toast.LENGTH_LONG).show();
                }else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(AddNhanVien.this,"Fialdssss Are Empty!", Toast.LENGTH_LONG).show();
                }else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(AddNhanVien.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddNhanVien.this, "Tạo không thành công, hãy thử lại ", Toast.LENGTH_LONG).show();
                            }else {
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
                                }else {
                                    congViec.set(3,false);
                                }
                                if (checkBep.isChecked()){
                                    congViec.set(4,true);
                                }else {
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

                                String name = edtTenNhanVien.getText().toString();
                                String phone = edtPhone.getText().toString();
                                ChamCong chamCong = new ChamCong(0, 0, 0);
                                nhanVien = new NhanVien(name,email,congViec,caLam,phone,mFirebaseAuth.getUid(), chamCong);
                                nhanVien.setChuCuaHang(false);
                                mData2.child("CuaHangOder/"+ID_CUAHANG+"/user/"+mFirebaseAuth.getUid()).setValue(nhanVien);

                                edtEmail.setText("");
                                edtPassword.setText("");
                                edtTenNhanVien.setText("");
                                edtPhone.setText("");


                                mData2.child("ACCOUNT_LOGIN").child(mFirebaseAuth.getUid()+"/CuaHang/"+ID_CUAHANG).child("ChucVu").setValue(0);
                                mData2.child("ACCOUNT_LOGIN").child(mFirebaseAuth.getUid()+"/CuaHang/"+ID_CUAHANG).child("ID").setValue(ID_CUAHANG);
                                mData2.child("ACCOUNT_LOGIN").child(mFirebaseAuth.getUid()+"/CuaHang/"+ID_CUAHANG).child("name").setValue("Chi nhánh 1");
                                new SupportSaveLichSu(AddNhanVien.this, "Thêm nhân viên: " + nhanVien.getUsername());
                                finish();
                            }
                        }

                    });

                }else {
                    Toast.makeText(AddNhanVien.this,"Error Occurred!", Toast.LENGTH_LONG).show();
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
