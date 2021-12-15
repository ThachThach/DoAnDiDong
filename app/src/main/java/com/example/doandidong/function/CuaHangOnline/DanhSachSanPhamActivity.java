package com.example.doandidong.function.CuaHangOnline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.Product;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Adapter.DanhSachSanPhamOnlineAdapter;
import java.util.ArrayList;

public class DanhSachSanPhamActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private FirebaseDatabase firebaseDatabase;
    private DanhSachSanPhamOnlineAdapter danhSachSanPhamOnlineAdapter;
    private ArrayList<Product> listProduct;
    private RecyclerView recyclerView;
    private EditText searchView;
    private String key;
    private String ID_CUAHANG;
    private ArrayList<Product> listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);
        recyclerView = findViewById(R.id.recyclerViewProduct);
        searchView = findViewById(R.id.btn_searchsp);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        mDatabase = firebaseDatabase.getReference("cuaHang/"+ID_CUAHANG).child("sanpham");
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                key = searchView.getText().toString();
                getListSearch(key);

            }
        });
        Danhsachsanpham();
    }

    //Hiển thị danh sách sản phẩm
    public void Danhsachsanpham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataSnapshot aaa = snapshot1;
                    for (DataSnapshot snapshot2 : aaa.getChildren()){
                        Product product = snapshot2.getValue(Product.class);
                        listProduct.add(product);
                        DataSnapshot aaa1 = snapshot2;
                    }
                    danhSachSanPhamOnlineAdapter = new DanhSachSanPhamOnlineAdapter(DanhSachSanPhamActivity.this,DanhSachSanPhamActivity.this, listProduct);
                    recyclerView.setAdapter(danhSachSanPhamOnlineAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachSanPhamActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListSearch(String newText) {
        listSearch = new ArrayList<>();
        if(newText == null){
            danhSachSanPhamOnlineAdapter = new DanhSachSanPhamOnlineAdapter(DanhSachSanPhamActivity.this,DanhSachSanPhamActivity.this, listProduct);
            recyclerView.setAdapter(danhSachSanPhamOnlineAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachSanPhamActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        for(int i =0; i < listProduct.size();i++)
        {
            if(listProduct.get(i).getNameProduct().toUpperCase().contains(newText.toUpperCase().trim())){
                listSearch.add(listProduct.get(i));
            }
        }
        danhSachSanPhamOnlineAdapter = new DanhSachSanPhamOnlineAdapter(DanhSachSanPhamActivity.this,DanhSachSanPhamActivity.this, listSearch);
        recyclerView.setAdapter(danhSachSanPhamOnlineAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachSanPhamActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //Xóa sản phẩm
    public void delete(final int position){
        new AlertDialog.Builder(DanhSachSanPhamActivity.this, R.style.AlertDialog).setMessage(
                "Do you want to delete this item"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            DataSnapshot aaa = snapshot1;
                            Toast.makeText(DanhSachSanPhamActivity.this,listProduct.get(position).getId()+"",Toast.LENGTH_LONG).show();
                            mDatabase1 = firebaseDatabase.getReference("cuaHang/"+ID_CUAHANG).child("sanpham").child(aaa.getKey());
                            new SupportSaveLichSu(DanhSachSanPhamActivity.this, "Xóa sản phẩm: " + listProduct.get(position).getNameProduct() + " :ID-"+ listProduct.get(position).getId());
                            mDatabase1.child(listProduct.get(position).getId()).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }).setNegativeButton("No", null)
                .show();
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