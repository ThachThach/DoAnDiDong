package com.example.doandidong.function.ThemBan_KhuVuc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticBanModel;
import com.example.doandidong.Adapter.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import com.example.doandidong.R;
import java.util.ArrayList;

public class SuaBanKV extends AppCompatActivity {
    private TextInputEditText tenBan;
    private AutoCompleteTextView trangThai,khuVuc;
    private Button btnThem;
    private String idBan;
    private String idKV;
    private ArrayList<String> arrayLisTT = new ArrayList<>();
    private ArrayList<String> arrayListKV = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterKV;
    private String STR_ID;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC = "khuvuc";
    private String STR_BAN = "ban";
    private String trangthai;
    private ArrayList<StaticModelKhuVuc> arrayList;
    private StaticBanModel staticBanModel;
    private String setTT;
    private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_them_ban);

        staticBanModel = (StaticBanModel) getIntent().getSerializableExtra("Key_arayBan");
        tenBan = findViewById(R.id.editTextBan);
        khuVuc = findViewById(R.id.spn_chonkhuvuc);
        trangThai = findViewById(R.id.spn_chontrangthai);
        btnThem = findViewById(R.id.bnt_themban);
        textInputLayout = findViewById(R.id.textInputLayout6);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        khuVuc.setVisibility(View.GONE);
        btnThem.setText("Sửa bàn");
        textInputLayout.setVisibility(View.GONE);
        tenBan.setText(staticBanModel.getTenban());
        trangthai = staticBanModel.getTrangthai();
        if(trangthai.equals("1")){
            trangThai.setText("Chưa hoạt động");
            setTT = "1";
        }
        if(trangthai.equals("3")) {
            trangThai.setText("Hỏng");
            setTT = "3";
        }
        idKV = staticBanModel.getId_khuvuc();
        idBan = staticBanModel.getID();
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC).child(idKV).child(STR_BAN).child(idBan);
        TrangThai();
        SuaBan();
    }

    private void TrangThai(){
        arrayLisTT.add("Chưa hoạt động");
        arrayLisTT.add("Hỏng");

        adapter = new ArrayAdapter<String>(SuaBanKV.this,R.layout.support_simple_spinner_dropdown_item,arrayLisTT);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        trangThai.setAdapter(adapter);
    }

    private void SuaBan(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(tenBan.getText().toString().isEmpty()){
                    tenBan.setError("Hãy nhập tên bàn");
                    tenBan.requestFocus();
                }
                else if(setTT == null){
                    Toast.makeText(SuaBanKV.this,"Hãy chọn trạng thái hoạt động",Toast.LENGTH_LONG).show();
                }
                else{
                    String name = tenBan.getText().toString();
                    mDatabase1.child("tenban").setValue(name);
                    mDatabase1.child("trangthai");
                    tenBan.setText("");
                    finish();
                }
            }
        });


    }
}
