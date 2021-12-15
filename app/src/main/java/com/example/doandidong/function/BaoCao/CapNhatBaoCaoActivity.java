package com.example.doandidong.function.BaoCao;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.CapNhatBaoCaoKhoAdapter;
import com.example.doandidong.Model.BaoCaoKho;
import com.example.doandidong.Model.ChiTietNhapKho;
import com.example.doandidong.Model.NguyenLieu;
import com.example.doandidong.R;
import com.example.doandidong.function.SanPham.DanhSachNguyenLieuActivity;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class  CapNhatBaoCaoActivity extends AppCompatActivity{
    private EditText tenSanPham,soLieuMoi,soLieuCu;
    private Spinner tenNhanVien;
    private Button addBaoCao,addSanPham,thoat,them;
    private DatabaseReference mDatabase;
    private BaoCaoKho baoCaoKho;
    private CapNhatBaoCaoKhoAdapter capNhatBaoCaoKhoAdapter;
    private Dialog dialog;
    private Window window;
    private RecyclerView recyclerView;
    private ArrayList<ChiTietNhapKho> list;
    private ArrayList<String> nguyenLieus;
    private ArrayList<NguyenLieu> nguyenLieuArrayList;
    private String id_CuaHang;
    private String tenSanPham1;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String donviTinh = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_chi_tiet_kho);
        thongTinCuaHangSql=new ThongTinCuaHangSql(this);
        id_CuaHang=thongTinCuaHangSql.IDCuaHang();
        tenNhanVien=findViewById(R.id.textTenNhanVien);
        addSanPham=findViewById(R.id.themChiTietSanPham);
        addBaoCao = findViewById(R.id.btnAddBaoCao);
        recyclerView=findViewById(R.id.listSanPhamKho);
        dialog = new Dialog(CapNhatBaoCaoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_chitietkho);
        window = dialog.getWindow();
        tenSanPham=dialog.findViewById(R.id.textSanPham);
        soLieuMoi=dialog.findViewById(R.id.textsoLieuMoi);
        soLieuCu=dialog.findViewById(R.id.textSoLieuCu);
        thoat=dialog.findViewById(R.id.thoat);
        them=dialog.findViewById(R.id.addChiTietKho);
        getSpinner();
        nguyenLieus=new ArrayList<>();
        nguyenLieuArrayList = new ArrayList<>();
        list=new ArrayList<>();
        addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChiTietKho(Gravity.CENTER);
            }
        });

        addBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBaoCaoKho();
            }
        });

    }

    public void getSpinner(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CuaHangOder").child(id_CuaHang).child("nguyenlieu");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nguyenLieus=new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    NguyenLieu nguyenLieu=snapshot1.getValue(NguyenLieu.class);
                    nguyenLieu.setKey(snapshot1.getKey());
                    nguyenLieus.add(nguyenLieu.getTen());
                    nguyenLieuArrayList.add(nguyenLieu);


                }
                ArrayList<String> spinner = nguyenLieus;
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(CapNhatBaoCaoActivity.this, android.R.layout.simple_spinner_item, spinner);
                //phải gọi lệnh này để hiển thị danh sách cho Spinner
                adapter.setDropDownViewResource
                        (android.R.layout.simple_list_item_single_choice);
                //Thiết lập adapter cho Spinner
                tenNhanVien.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tenNhanVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tenSanPham1=spinner.get(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addBaoCaoKho(){
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j=0; j < nguyenLieuArrayList.size(); j++) {
                    if (list.get(i).getTenSanPham().equals(nguyenLieuArrayList.get(j).getTen())) {
                        Log.d("ssss", nguyenLieuArrayList.get(j).getKey());
                        FirebaseDatabase.getInstance().getReference().child("CuaHangOder").child(id_CuaHang).
                                child("nguyenlieu").child(nguyenLieuArrayList.get(j).getKey())
                                .child("soluong").setValue(list.get(i).getSoLieu());
                        nguyenLieuArrayList.get(j).setSoluong(list.get(i).getSoLieu());
                    }
                }
            }
            String TenNhanVien = thongTinCuaHangSql.Username();
            Date date = Calendar.getInstance().getTime();
            // Display a date in day, month, year format
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String today = formatter.format(date);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            FirebaseDatabase.getInstance().getReference().child("BaoCaoKho").child(id_CuaHang).child(timestamp.getTime()+"").setValue(new BaoCaoKho(TenNhanVien,today,java.time.LocalTime.now().toString(),list));
            list.clear();
            capNhatBaoCaoKhoAdapter.notifyDataSetChanged();
            soLieuMoi.setText("");
            Toast.makeText(CapNhatBaoCaoActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(CapNhatBaoCaoActivity.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    public void addChiTietKho(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        tenSanPham.setText(tenSanPham1);
        for (int i=0;i<nguyenLieuArrayList.size();i++){
            if(nguyenLieuArrayList.get(i).getTen().equals(tenSanPham1)){
                soLieuCu.setText(nguyenLieuArrayList.get(i).getSoluong()+" "+nguyenLieuArrayList.get(i).getDonvi());
                donviTinh = nguyenLieuArrayList.get(i).getDonvi();
            }
        }
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenSanPham=tenSanPham.getText().toString();
                String SoLieuCu=soLieuCu.getText().toString();
                String SoLieuMoi=soLieuMoi.getText().toString() + " " + donviTinh;
                if (TenSanPham.isEmpty()) {
                    tenSanPham.setError("Chưa có tên sản phẩm");
                    tenSanPham.requestFocus();
                }else if (SoLieuCu.isEmpty()) {
                    soLieuCu.setError("Chưa có số liệu cũ");
                    soLieuCu.requestFocus();
                }else if (SoLieuMoi.isEmpty()) {
                    soLieuMoi.setError("Chưa có số liệu cũ");
                    soLieuMoi.requestFocus();
                }else {
                    int num = Integer.parseInt(soLieuMoi.getText().toString());
                    ChiTietNhapKho chiTietNhapKho=new ChiTietNhapKho(TenSanPham,SoLieuCu, num ,SoLieuMoi);
                    list.add(chiTietNhapKho);
                    capNhatBaoCaoKhoAdapter=new CapNhatBaoCaoKhoAdapter(CapNhatBaoCaoActivity.this,list, soLieuMoi);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CapNhatBaoCaoActivity.this,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(capNhatBaoCaoKhoAdapter);
                    capNhatBaoCaoKhoAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_danh_sach_nguyen_lieu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.danhsachnguyenlieu:
                Intent intent = new Intent(CapNhatBaoCaoActivity.this, DanhSachNguyenLieuActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
