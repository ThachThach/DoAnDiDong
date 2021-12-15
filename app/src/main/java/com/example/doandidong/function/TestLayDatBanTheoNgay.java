package com.example.doandidong.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import java.util.Calendar;

public class TestLayDatBanTheoNgay extends AppCompatActivity {

    private Button button;
    private TextView text;
    private DatePickerDialog datePickerDialog;
    private String ngay;
    private DatabaseReference databaseReference;
    private String ID_CUAHANG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lay_dat_ban_theo_ngay);

        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();


        databaseReference = FirebaseDatabase.getInstance().getReference();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String mDay = day+"";
        if (day < 10) {
            mDay = 0+ "" +day;
        }

        ngay = mDay +"-"+(month+1)+"-"+year;

        datePickerDialog = new DatePickerDialog(TestLayDatBanTheoNgay.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String d = dayOfMonth+"";
                if (dayOfMonth< 10) {
                    d = "0"+dayOfMonth;
                }
                ngay = d + "-" + (month + 1) + "-" + year;
                getDatFirebase();

            }
        }, year, month, day);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        getDatFirebase();
    }

    private void getDatFirebase () {
        databaseReference.child("CuaHangOder").child(ID_CUAHANG).child("DatBan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    for (DataSnapshot s: snap.getChildren()) {
                        if (s.child("ngaydat").getValue().toString().equals(ngay)) {
                            text.setText(s.getValue().toString());
                            return;
                        }else {
                            text.setText("");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}