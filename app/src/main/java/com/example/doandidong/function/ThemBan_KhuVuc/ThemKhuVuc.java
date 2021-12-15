package com.example.doandidong.function.ThemBan_KhuVuc;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.R;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemKhuVuc extends AppCompatActivity {

    private TextInputEditText tenKhuVuc;
    private AutoCompleteTextView trangThai;
    private Button btnThem;
    private String id;
    private ArrayList<String> arrayLisTT = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC ="khuvuc";
    private String trangthai;
    private String setTT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khu_vuc);
        tenKhuVuc = findViewById(R.id.edttenkhachang);
        trangThai = findViewById(R.id.spn_khuvuc);
        btnThem = findViewById(R.id.bnt_themkhuvuc);
        arrayLisTT.add("Chưa hoạt động");
        arrayLisTT.add("Hỏng");
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        adapter = new ArrayAdapter<String>(ThemKhuVuc.this,R.layout.support_simple_spinner_dropdown_item,arrayLisTT);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        trangThai.setAdapter(adapter);
        trangThai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trangthai = parent.getItemAtPosition(position).toString();
                if(trangthai.equals("Chưa hoạt động")){
                    setTT = "1";
                }
                if(trangthai.equals("Hỏng")){
                    setTT = "3";
                }

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenKhuVuc.getText().toString().isEmpty()){
                    tenKhuVuc.setError("Hãy nhập tên khu vực");
                    tenKhuVuc.requestFocus();
                }else if(setTT == null){
                    Toast.makeText(ThemKhuVuc.this,"Hãy chọn trạng thái",Toast.LENGTH_LONG).show();
                }
                else {
                    id = mDatabase.push().getKey();
                    String ten = tenKhuVuc.getText().toString();
                    StaticModelKhuVuc staticModelKhuVuc = new StaticModelKhuVuc(ten,setTT,id);
                    mDatabase.child(id).setValue(staticModelKhuVuc);
                    tenKhuVuc.setText("");

                    finish();
                }

            }
        });


    }
}