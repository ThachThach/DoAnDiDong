package com.example.doandidong.SQL;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperSql {
    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

    public HelperSql() {}

    private void getThongTinUser() {
        mFirebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public String getIDCuaHang(Context context) {
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(context, "app_database.sqlite", null, 1);
        thongTinCuaHangSql.createTable();
        Cursor cursor = thongTinCuaHangSql.selectThongTin();
        String Id = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Id = cursor.getString(0);
            }
        }else {
            Toast.makeText(context, "Lỗi không thể đọc dữ liệu", Toast.LENGTH_SHORT).show();
        }
        return Id;
    }
}
