package com.example.doandidong.function.KhuyenMai;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import com.example.doandidong.Common.DataAddress;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.AddressVN.DiaChi;
import com.example.doandidong.Model.AddressVN.Huyen;
import com.example.doandidong.Model.KhuyenMai.KhuyenMai;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.ThongTinCuaHangOnlineActivity;
import java.io.IOException;
import java.util.ArrayList;

public class ThemKhuyenMai extends AppCompatActivity {
    private AutoCompleteTextView spnLoaiKM;
    private TextView chonLoaiKN,thongtinKM;
    private EditText editGiaKM,editPhantramKM,editQuan,editGia,editmoTa;
    private Button btnThemKM,btnHuyKM,btnThemKM1,btnHuyKM1,btnThemKM2,btnHuyKM2;
    private Dialog dialog,dialog1;
    private Window window,window1;
    private DatabaseReference mDatabase;
    private KhuyenMai khuyenMai;
    private String STR_CUAHANG;
    private String STR_KM = "khuyenmai";
    private Double gia;
    private Double phanTram;
    private String quan;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private int vitri = 0;
    private RadioGroup radioGroup;
    private int vitriTP = 0;
    private RadioButton rdTp,rdHuyen,rdXa;
    private AutoCompleteTextView spnTinh,spnHuyen,spnXa;
    private TextInputLayout textTinh,textXa,textHuyen;
    private ArrayAdapter<String> adapterTinh,adapterHuyen,adapterXa;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();
    private String[] tinh;
    private String[] huyen;
    private String[] xa;
    private int  ViTri =0;
    private String tenTinh;
    private String tenHuyen;
    private String tenXa;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themkhuyenmaikhachhang);

        spnLoaiKM = findViewById(R.id.spinner_nhomkhuyenmai);
        thongtinKM = findViewById(R.id.edtThongtinkhuyenmai);
        btnThemKM = findViewById(R.id.btnTaoKhuyenMai);
        btnHuyKM = findViewById(R.id.btnhuyTaoKhuyenMai);
        editmoTa = findViewById(R.id.edtghiMoTa);

        dialog = new Dialog(ThemKhuyenMai.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_khuyenmai1);
        window = dialog.getWindow();
        dialog1 = new Dialog(ThemKhuyenMai.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_khuyenmai2);
        window1 = dialog1.getWindow();
        radioGroup = dialog1.findViewById(R.id.radioGruop);
        rdTp = dialog1.findViewById(R.id.rdTP);
        rdHuyen = dialog1.findViewById(R.id.rdHuyen);
        rdXa = dialog1.findViewById(R.id.rdXa);
        rdTp.setChecked(true);
        vitriTP = 1;
        DataAddress dataAddress = new DataAddress();
        try {
            listDiaChi = dataAddress.readCompanyJSONFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rdTP:
                        textHuyen.setVisibility(View.GONE);
                        textXa.setVisibility(View.GONE);
                        vitriTP =1;
                        break;
                    case R.id.rdHuyen:
                        textHuyen.setVisibility(View.VISIBLE);
                        textXa.setVisibility(View.GONE);
                        vitriTP =2;
                        break;
                    case R.id.rdXa:
                        textHuyen.setVisibility(View.VISIBLE);
                        textXa.setVisibility(View.VISIBLE);
                        vitriTP =3;
                        break;
                }
            }
        });


        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        STR_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        mDatabase = FirebaseDatabase.getInstance().getReference(STR_KM);
        arrayList.add("Khuyến mãi theo phần trăm giá");
        arrayList.add("Khuyến mãi theo địa điểm");
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        spnLoaiKM.setAdapter(adapter);
        Click();
        ThemKhuyenMai();
        btnHuyKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent = new Intent(ThemKhuyenMai.this, ListKhuyenMai.class);
//                startActivity(intent);
                finish();

            }
        });
    }

    public void dialogKM1(int gravity){
        editGiaKM = dialog.findViewById(R.id.edtGiakhuyenmai);
        editPhantramKM = dialog.findViewById(R.id.edtphantramkhuyenmai);
        btnHuyKM1 = dialog.findViewById(R.id.btnhuyDiaLogThemKM1);
        btnThemKM1 = dialog.findViewById(R.id.btnthemDiaLogThemKM1);

        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        btnHuyKM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThemKM1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editGiaKM.getText().toString().isEmpty()){
                    editGiaKM.setError("Hãy nhập giá khuyến mãi");
                    editGiaKM.requestFocus();
                }
                else if(editPhantramKM.getText().toString().isEmpty()){
                    editPhantramKM.setError("Hãy nhập phần trăm");

                    if(Double.parseDouble(editPhantramKM.getText().toString()) > 100){
                        editPhantramKM.setError("Không đúng định dạng");
                        editPhantramKM.requestFocus();
                    }
                }
                else {
                    gia = Double.parseDouble(editGiaKM.getText().toString());
                    phanTram = Double.parseDouble(editPhantramKM.getText().toString());
                    thongtinKM.setText("Giá: "+gia+" khuyến mãi: "+phanTram+"%");
                    dialog.dismiss();
                }

            }
        });
        dialog.show();

    }

    public void dialogKM2(int gravity){
        editGia = dialog1.findViewById(R.id.edtgia);
        btnHuyKM2 = dialog1.findViewById(R.id.btnhuyDiaLogThemKM2);
        btnThemKM2 = dialog1.findViewById(R.id.btnthemDiaLogThemKM2);
        textTinh = dialog1.findViewById(R.id.province);
        textHuyen = dialog1.findViewById(R.id.province1);
        textXa = dialog1.findViewById(R.id.province2);
        spnTinh = dialog1.findViewById(R.id.spinner_tinh);
        spnHuyen = dialog1.findViewById(R.id.spinner_huyen);
        spnXa = dialog1.findViewById(R.id.spinner_xa);

        if(vitriTP == 1){
            textHuyen.setVisibility(View.GONE);
            textXa.setVisibility(View.GONE);
        }

        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        window1.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, tinh);
        spnTinh.setAdapter(adapterTinh);
        adapterTinh.notifyDataSetChanged();

        int i = 0;

        spnTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(ThemKhuyenMai.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                spnHuyen.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                spnHuyen.setAdapter(adapterHuyen);

                adapterXa = new ArrayAdapter<String>(ThemKhuyenMai.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(position).getHuyens().get(0).getXa());
                spnXa.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                spnXa.setAdapter(adapterXa);
            }
        });

        spnHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(ThemKhuyenMai.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                spnXa.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                spnXa.setAdapter(adapterXa);
            }
        });

        spnXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
        btnHuyKM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemKM2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editGia.getText().toString().isEmpty()){
                    editGia.setError("Hãy nhập giá");
                    editGia.requestFocus();

                } else if(tenTinh == null){
                    Toast.makeText(ThemKhuyenMai.this, "Hãy nhập địa chỉ", Toast.LENGTH_LONG).show();
                }
                else {
                    gia = Double.parseDouble(editGia.getText().toString());
                    if (vitriTP == 1){
                        thongtinKM.setText("Giá: " + gia + " Địa chỉ: " + tenTinh);
                        quan = tenTinh;
                    }
                    if (vitriTP == 2){
                        thongtinKM.setText("Giá: " + gia + " Địa chỉ: " +tenHuyen +","+ tenTinh);
                        quan = tenTinh;
                    }
                    if (vitri == 3){
                        thongtinKM.setText("Giá: " + gia + " Địa chỉ: " + tenXa +","+tenHuyen +","+ tenTinh);
                        quan = tenXa;
                    }


                    dialog1.dismiss();

                }
            }
        });
        dialog1.show();

    }

    public void Click(){
        spnLoaiKM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Khuyến mãi theo phần trăm giá")){
                    vitri = 1;
                    dialogKM1(Gravity.CENTER);
                }
                else {
                    vitri = 2;
                    dialogKM2(Gravity.CENTER);
                }
            }
        });
        thongtinKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vitri == 1){
                    dialogKM1(Gravity.CENTER);
                }
                else {
                    dialogKM2(Gravity.CENTER);
                }
            }
        });
    }

    public void ThemKhuyenMai(){
        btnThemKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editmoTa.getText().toString().isEmpty()) {
                    editmoTa.setError("Nhập mô tả");
                    editmoTa.requestFocus();
                } else {
                    String mota = editmoTa.getText().toString();

                    if (vitri == 1) {
                        khuyenMai = new KhuyenMai(gia, STR_CUAHANG, vitri, phanTram, mota);
                    } else {
                        vitri = 2;
                        khuyenMai = new KhuyenMai(STR_CUAHANG, vitri, quan, gia, mota);
                    }
                    mDatabase.child(STR_CUAHANG).push().setValue(khuyenMai);
//                    Intent intent = new Intent();
//                    intent = new Intent(ThemKhuyenMai.this, ListKhuyenMai.class);
//                    startActivity(intent);
                    finish();
                }
            }
        });

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
