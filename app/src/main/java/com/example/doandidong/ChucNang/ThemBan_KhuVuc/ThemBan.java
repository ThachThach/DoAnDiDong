package com.example.doandidong.ChucNang.ThemBan_KhuVuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Pakage_AdapterBan.StaticBanModel;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThemBan extends AppCompatActivity {
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
    private String khuvuc;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHUVUC = "khuvuc";
    private String STR_BAN = "ban";
    private String trangthai;
    private ArrayList<StaticModelKhuVuc> arrayList;
    private StaticModelKhuVuc staticModelKhuVuc;
    private  StaticModelKhuVuc staticModelKhuVuc1;
    private String setTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban);

        tenBan = findViewById(R.id.editTextBan);
        khuVuc = findViewById(R.id.spn_chonkhuvuc);
        trangThai = findViewById(R.id.spn_chontrangthai);
        btnThem = findViewById(R.id.bnt_themban);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_ID = thongTinCuaHangSql.IDCuaHang();
        ThemBan();
        TrangThai();
    }

    private void ThemBan(){
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListKV = new ArrayList<>();
                arrayList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String name = snapshot1.child("tenkhuvuc").getValue().toString();
                    String status = snapshot1.child("trangthai").getValue().toString();
                    String id_kv = snapshot1.child("id_khuvuc").getValue().toString();
                    arrayListKV.add(name);
                    staticModelKhuVuc = new StaticModelKhuVuc(name,status,id_kv);
                    arrayList.add(staticModelKhuVuc);
                }

                adapterKV = new ArrayAdapter<String>(ThemBan.this,R.layout.support_simple_spinner_dropdown_item,arrayListKV);
                khuVuc.setAdapter(adapterKV);
                khuVuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        khuvuc = parent.getItemAtPosition(position).toString();
                        staticModelKhuVuc1 = arrayList.get(position);
                        idKV = staticModelKhuVuc1.getId_khuvuc();
                    }
                });
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
               btnThem.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(tenBan.getText().toString().isEmpty()){
                           tenBan.setError("Hãy nhập tên bàn");
                           tenBan.requestFocus();
                       }
                       else if(khuvuc == null ){
                           Toast.makeText(ThemBan.this,"Hãy chọn khu vực",Toast.LENGTH_LONG).show();
                       }
                       else if(setTT == null){
                           Toast.makeText(ThemBan.this,"Hãy chọn trạng thái hoạt động",Toast.LENGTH_LONG).show();
                       }
                       else {
                           mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(STR_ID).child(STR_KHUVUC).child(idKV).child(STR_BAN);
                           idBan= mDatabase1.push().getKey();
                           String name = tenBan.getText().toString();
                           StaticBanModel staticBanModel = new StaticBanModel(idBan,name,null,"0",idKV,setTT);
                           mDatabase1.child(idBan).setValue(staticBanModel);
                           tenBan.setText("");
                           finish();
                       }
                   }
               });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void TrangThai(){
        arrayLisTT.add("Chưa hoạt động");
        arrayLisTT.add("Hỏng");
//        arrayLisTT.add("");
        adapter = new ArrayAdapter<String>(ThemBan.this,R.layout.support_simple_spinner_dropdown_item,arrayLisTT);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        trangThai.setAdapter(adapter);
    }

}