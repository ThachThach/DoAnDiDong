package com.example.doandidong.function.SanPham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.SanPham.Category;
import com.example.doandidong.R;
import com.example.doandidong.function.KhachHang.ListKhachHang;
import com.example.doandidong.function.KhachHang.ThemKhachHang;

public class AddCategory extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextInputEditText editTextAddCategory;
    private Button btnAddCategory;
    private Category category;
    private String STR_NHOMSANPHAM ="danhmucsanpham";
    private String STR_CUAHANG = "CuaHangOder";
    private String ID_CUAHANG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        editTextAddCategory = findViewById(R.id.editAdd);
        btnAddCategory = findViewById(R.id.btnAdd);
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NHOMSANPHAM);
        ButtonLuu();
    }

    public void ButtonLuu(){
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mDatabase.push().getKey();
                if(editTextAddCategory.getText().toString().isEmpty()){
                    Toast.makeText(AddCategory.this,"Hãy nhập tên nhóm sản phẩm",Toast.LENGTH_LONG).show();
                }
                else {
                    String name = editTextAddCategory.getText().toString();
                    category = new Category(id,name);
                    mDatabase.child(id).setValue(category);

                    finish();
                }
            }
        });
    }
}
