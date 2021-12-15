package com.example.doandidong.ChucNang.SanPham;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterSanPham.AdapterDonViTinh;
import com.example.doandidong.Data.SanPham.DonViTinh;
import com.example.doandidong.R;
import java.util.ArrayList;

public class ListDonViTinh extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_DVT = "donvitinh";
    private ArrayList<DonViTinh> arrayList;
    private DonViTinh donViTinh;
    private AdapterDonViTinh adapter;
    private Dialog dialog;
    private Window window;
    private EditText editText;
    private TextView textView;
    private Button btnThem,btnHuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdonvitinh);
        recyclerView = findViewById(R.id.recyclerDVT);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_DVT);
        dialog = new Dialog(ListDonViTinh.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemdonvitinh);
        window = dialog.getWindow();

        textView = dialog.findViewById(R.id.themDVT);
        textView.setText("Sửa Đơn Vị Tính");
        btnThem = dialog.findViewById(R.id.btnthemDiaLogThemDVT);
        btnHuy = dialog.findViewById(R.id.btnhuyDiaLogThemDVT);
        DanhSachDonViTinh();

    }

    private void DanhSachDonViTinh(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    donViTinh = snapshot1.getValue(DonViTinh.class);
                    arrayList.add(donViTinh);
                }
                adapter = new AdapterDonViTinh(ListDonViTinh.this,ListDonViTinh.this,arrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListDonViTinh.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void update(int gravity,int position){
        editText = dialog.findViewById(R.id.edtTenDonViTinh);
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
        editText.setText(arrayList.get(position).getDonViTinh());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(arrayList.get(position).getId()).child("donViTinh").setValue(editText.getText().toString());
                dialog.dismiss();
            }
        });
    }

    public void delete(int position){
        new AlertDialog.Builder(ListDonViTinh.this).setMessage(
                "Bạn có muốn xóa đơn vị tính này"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDatabase.child(arrayList.get(position).getId()).removeValue();

            }
        }).setNegativeButton("No", null)
                .show();
    }
}
