package com.example.doandidong.ChucNang.DatBan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.OrderMenu;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatBan extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Toolbar toolbar;
    String id_ban;
    String id_khuvuc;
    String tenban;
    String ids;
    Button bnt_datngay,bnt_datgio,bnt_datgiokt,bnt_datban;
    TextView txtDate, txtTime,txttimekt,tvngayhientai;
    //    TextInputLayout edttenkhachang;
    EditText edttenkhachang, editTextPhone,editTextNumber;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT_12 = "hh:mm:ss a";
    private static final String TIME_FORMAT_24 = "HH:mm:ss";
    Date date ;
    Timestamp timestamp;
    Timestamp timestamp1;
    String id_CuaHang ;
    TextView title;
    private Window window, window1, window2;
    private Dialog dialog, dialog1, dialog2,dialog3,dialog4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang ="CuaHangOder/"+thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        tenban = intent.getStringExtra("tenban");
        actionBar.setTitle("Đặt Bàn -"+tenban+"");

        bnt_datngay = findViewById(R.id.bnt_datngay);
        bnt_datgio = findViewById(R.id.bnt_datgio);
        bnt_datgiokt = findViewById(R.id.bnt_datgiokt);
        //textview
        txtDate = findViewById(R.id.tvngaydatban);
        txtTime = findViewById(R.id.tvgiodatban);
        txttimekt= findViewById(R.id.tvgiodatbankt);
        tvngayhientai= findViewById(R.id.tvngayhientai);
        bnt_datban = findViewById(R.id.bnt_themkhuvuc);
        //edittext
        edttenkhachang= findViewById(R.id.edttenkhachang);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNumber = findViewById(R.id.editTextNumber);
        bnt_datgio.setOnClickListener(this);
        bnt_datngay.setOnClickListener(this);
        bnt_datgiokt.setOnClickListener(this);
        bnt_datban.setOnClickListener(this);

        tvngayhientai.setText(hamlaydate());
    }


    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        switch (v.getId() )
        {
            case R.id.bnt_datngay:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String ngaysss = dayOfMonth+"";
                                if (dayOfMonth < 10) {
                                    ngaysss = "0" + dayOfMonth;
                                }

                                txtDate.setText(ngaysss + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.bnt_datgio:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.bnt_datgiokt:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txttimekt.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog1.show();
                break;
            case R.id.bnt_themkhuvuc:
                dailonghoitruockhitao();
                dialog4.show();
                break;
        }
    }

    public  String hamlaydate(){
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new java.util.Date());
        Log.d("datenowww",date+"");
        return date;
    }
    public String Hamlaygiohientai(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww",timestamp.getTime()+"");
        return  timestamp.getTime()+"";
    }



    private void dailonghoitruockhitao() {
        dialog4 = new Dialog(DatBan.this);
        dialog4.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog4.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog4.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog4.setCancelable(false); //Optional
        dialog4.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
        title =dialog4.findViewById(R.id.title);
        title.setText("Bạn!!Chắc Chắn Muốn Đặt Bàn");
        Button Okay = dialog4.findViewById(R.id.btn_okay);
        Button Cancel = dialog4.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa =txtTime.getText().toString()+" "+txtDate.getText().toString();
                String dd =txttimekt.getText().toString()+" "+txtDate.getText().toString();
                try {
                    java.util.Date date1 = new SimpleDateFormat("hh:mm dd-MM-yyyy").parse(aa);
                    timestamp = new Timestamp(date1.getTime());
                    timestamp.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    java.util.Date date = new SimpleDateFormat("hh:mm dd-MM-yyyy").parse(dd);
                    timestamp1 = new Timestamp(date.getTime());
                    timestamp1.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (edttenkhachang.getText().toString().isEmpty()){
                    edttenkhachang.setError("Hãy nhập tên khách Hàng");
                    edttenkhachang.requestFocus();
                }else if (editTextPhone.getText().toString().isEmpty()){
                    editTextPhone.setError("Hãy nhập Sô điện thoại");
                    editTextPhone.requestFocus();
                }
                else if (txtDate.getText().toString().equals("00:00:00")){
                    hamdialogkiemtra();
                    title.setText("chưa nhập ngày đặt");
                    dialog3.show();

                }
                else if (txtTime.getText().toString().equals("00:00:00")){
                    hamdialogkiemtra();
                    title.setText("chưa chọn giờ đặt");
                    dialog3.show();

                }
                else if (txttimekt.getText().toString().equals("00:00:00")){
                    hamdialogkiemtra();
                    title.setText("chưa chọn giờ kết thúc");
                    dialog3.show();
                }
                else if(timestamp.getTime()>timestamp1.getTime()){
                    hamdialogkiemtra();
                    title.setText("Giờ kết thúc nhỏ hơn giờ đặt");
                    dialog3.show();
                }
                else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(id_ban + "_" + id_khuvuc).child(timestamp.getTime() +"");
                    databaseReference.child("tenkhachhang").setValue(edttenkhachang.getText().toString());
                    databaseReference.child("id_bk").setValue(id_ban + "_" + id_khuvuc);
                    databaseReference.child("sodienthoai").setValue(editTextPhone.getText().toString());
                    databaseReference.child("sotiendattruoc").setValue(editTextNumber.getText().toString());
                    databaseReference.child("ngayhientai").setValue(tvngayhientai.getText().toString());
                    databaseReference.child("ngaydat").setValue(txtDate.getText().toString());
                    databaseReference.child("giodat").setValue(txtTime.getText().toString());
                    databaseReference.child("tenban").setValue(tenban);
                    databaseReference.child("trangthai").setValue("0");
                    databaseReference.child("gioketthuc").setValue(txttimekt.getText().toString());
                    Intent intent = new Intent(DatBan.this, OrderMenu.class);
                    intent.putExtra("id_ban",id_ban);
                    intent.putExtra("id_khuvuc",id_khuvuc);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                dialog4.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });

    }
    private void hamdialogkiemtra(){
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.dialog_canhbao);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog3.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog3.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.setCancelable(false);
        dialog3.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button Okay = dialog3.findViewById(R.id.btn_cancel);
        title = dialog3.findViewById(R.id.title);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DatBan.this, OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}