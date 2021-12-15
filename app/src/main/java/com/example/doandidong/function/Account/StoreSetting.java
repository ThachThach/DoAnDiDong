package com.example.doandidong.function.Account;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import com.example.doandidong.Common.DataAddress;
import com.example.doandidong.Model.Address;
import com.example.doandidong.Model.AddressVN.DiaChi;
import com.example.doandidong.R;
import com.example.doandidong.database.DbBaoCao;
import com.example.doandidong.database.ThongTinCuaHangSql;
import com.example.doandidong.function.MainActivity;
import java.io.IOException;
import java.util.ArrayList;

public class StoreSetting extends AppCompatActivity implements View.OnClickListener {
    public DataAddress address;
    private ArrayList<Address> arrAddresses;

    private AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2, spinner_xaAuto;
    private Button btnComplete;
    private TextInputEditText edtTenCuaHang, sonha_soduong;

    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    private String KEY_CHILD = "";
    private String ID_USER = "";
    private DbBaoCao dataSql;
    private ProgressBar progressBar;

    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();

    private String[] tinh;

    private String tenTinh;
    private String tenHuyen;
    private String tenXa;

    private int ViTri;

    //Firebase
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;

    private final static String KEY_CHILD_STORE = "KEY_CHILD_STORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            KEY_CHILD = bundle.getString(KEY_CHILD_STORE, "");
            ID_USER = bundle.getString("ID_USER", "");
        } else {
            Log.d("AAA", "null");
        }

        autoCompleteTextView1 = findViewById(R.id.spinner_province);
        autoCompleteTextView2 = findViewById(R.id.spinner_district);
        btnComplete = findViewById(R.id.btn_complete);
        edtTenCuaHang = findViewById(R.id.edtTenCuaHang);
        sonha_soduong = findViewById(R.id.sonha_soduong);
        spinner_xaAuto = findViewById(R.id.spinner_xa);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        btnComplete.setOnClickListener(this);

        DataAddress dataAddress = new DataAddress();
        try {
            listDiaChi = dataAddress.readCompanyJSONFile(this);
            setDataText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDataText() {
        String[] items = ArrayTinh();
        adapter1 = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, items);

        autoCompleteTextView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();

                String[] arrayHuyen = ArrayHuyen(position);
                ViTri = position;

                adapter2 = new ArrayAdapter<String>(StoreSetting.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                autoCompleteTextView2.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                autoCompleteTextView2.setAdapter(adapter2);

                adapter3 = new ArrayAdapter<String>(StoreSetting.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(position).getHuyens().get(0).getXa());
                spinner_xaAuto.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapter3 = new ArrayAdapter<String>(StoreSetting.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                spinner_xaAuto.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                spinner_xaAuto.setAdapter(adapter3);
            }
        });

        spinner_xaAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    //sử dụng dữ liễu mẫu
    private void setUpStore() {
        Intent intent1 = new Intent(StoreSetting.this, MainActivity.class);
        startActivity(intent1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                String nameStore = edtTenCuaHang.getText().toString();
                String sonha = sonha_soduong.getText().toString();

                if (nameStore.isEmpty()) {
                    edtTenCuaHang.setError("Can not be empty");
                    edtTenCuaHang.requestFocus();
                } else if (tenTinh == null) {
                    autoCompleteTextView1.setError("Can not be empty");
                    autoCompleteTextView1.requestFocus();
                } else if (tenHuyen == null) {
                    autoCompleteTextView2.setError("Can not be empty");
                    autoCompleteTextView2.requestFocus();
                } else if (tenXa == null) {
                    autoCompleteTextView2.setError("Can not be empty");
                    autoCompleteTextView2.requestFocus();
                } else if (sonha.isEmpty()) {
                    sonha_soduong.setError("Can not be empty");
                    sonha_soduong.requestFocus();
                } else {
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/tenCuaHang").setValue(nameStore);
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/tinh").setValue(tenTinh);
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/huyen").setValue(tenHuyen);
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/xa").setValue(tenXa);
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/soNha").setValue(tenXa);
                    mFirebaseDatabase.child("CuaHangOder/" + ID_USER + "/ThongTinCuaHang/ThietLap").setValue(true);

                    ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(StoreSetting.this, "app_database.sqlite", null, 2);
                    thongTinCuaHangSql.createTable();
                    Cursor cursor = thongTinCuaHangSql.selectThongTin();
                    if (cursor.getCount() > 0) {
                        String IdOld = "";
                        while (cursor.moveToNext()) {
                            IdOld = cursor.getString(0);
                        }
                        thongTinCuaHangSql.UpdateCuaHang(ID_USER, IdOld, nameStore);
                    } else {
                        thongTinCuaHangSql.InsertThonTin(ID_USER, nameStore);
                    }

                    Intent intent1 = new Intent(StoreSetting.this, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                break;
        }
    }

    private String[] ArrayTinh() {

        String[] arr = new String[listDiaChi.size()];

        for (int i = 0; i < listDiaChi.size(); i++) {
            arr[i] = listDiaChi.get(i).getTenTinhTP();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[listDiaChi.get(pos).getHuyens().size()];

        for (int i = 0; i < listDiaChi.get(pos).getHuyens().size(); i++) {
            arr[i] = listDiaChi.get(pos).getHuyens().get(i).getTenHuyen();
        }

        return arr;
    }

}