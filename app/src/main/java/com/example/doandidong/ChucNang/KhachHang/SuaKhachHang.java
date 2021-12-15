package com.example.doandidong.ChucNang.KhachHang;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.AddressVN.DiaChi;
import com.example.doandidong.Data.AddressVN.Huyen;
import com.example.doandidong.Data.KhachHang.KhachHang;
import com.example.doandidong.Data.KhachHang.NhomKhachHang;
import com.example.doandidong.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;


public class SuaKhachHang extends AppCompatActivity {
    private EditText editHoTen,editSDT,editNgaySinh,editEmail,editGhiChu,soNha;
    private TextView editDiaChi, tv_nhom_khachHang;
    private Spinner spnNhomKhachHang;
    private RadioButton radioNam,radioNu,radioKhongCo;
    private AutoCompleteTextView spnTinh,spnHuyen,spnXa;
    private Button btnTaoDiaChi,btnHuyDiaChi,btnTaoKhachHang,btnhuyTaoKhachHang;
    private ImageButton btnLich;
    private ArrayAdapter<String> adapterTinh,adapterHuyen,adapterXa;
    private String[] tinh;
    private String[] huyen;
    private String[] xa;
    private int ViTri;
    private String tenTinh;
    private String tenHuyen;
    private String tenXa;
    private Dialog dialog;
    private Window window;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_KHACHHANG = "khachhang";
    private String STR_NHOMKH = "nhomkhachhang";
    private ArrayList<String> arrayListNhomKH;
    private String gioiTinh;
    private RadioGroup radioGroup;
    private KhachHang khachhang;
    private int com;
    private String ID_CUAHANG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        khachhang = (KhachHang) getIntent().getSerializableExtra("Key_arrKH");
        setContentView(R.layout.activity_addkhachhang);
        editHoTen = findViewById(R.id.edtTenKhachHang);
        editSDT = findViewById(R.id.edtPhoneKhachHang);
        editDiaChi = findViewById(R.id.edtDiaChiKhachHang);
        editEmail = findViewById(R.id.edtEmailKhachHang);
        editGhiChu = findViewById(R.id.edtghichuKhachHang);
        editNgaySinh = findViewById(R.id.edtNgaySinh);
        spnNhomKhachHang = findViewById(R.id.spnNhomKhachHang);
        btnLich = findViewById(R.id.imgLich);
        btnTaoKhachHang = findViewById(R.id.btnTaoKhachhang);
        btnhuyTaoKhachHang = findViewById(R.id.btnhuyTaoKhachHang);
        radioGroup = findViewById(R.id.radioGruop);
        radioNam = findViewById(R.id.gtNam);
        radioNu = findViewById(R.id.gtNu);
        radioKhongCo = findViewById(R.id.gtkhongco);
        tv_nhom_khachHang = findViewById(R.id.tv_nhom_khachHang);

        //dialog
        dialog = new Dialog(SuaKhachHang.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemdiachikhachhang);
        window = dialog.getWindow();
        spnTinh = dialog.findViewById(R.id.spinner_tinh);
        spnHuyen = dialog.findViewById(R.id.spinner_huyen);
        spnXa = dialog.findViewById(R.id.spinner_xa);
        soNha = dialog.findViewById(R.id.edtSoNha);
        btnHuyDiaChi = dialog.findViewById(R.id.btnhuyTaoDiaChiKhachHang);
        btnTaoDiaChi = dialog.findViewById(R.id.btnTaoDiaChiKhachhang);
        tenTinh = khachhang.getDiaChiTinh();
        tenHuyen = khachhang.getDiaChiHuyen();
        tenXa = khachhang.getDiaChiXa();
        soNha.setText(khachhang.getSoNha());
        gioiTinh = khachhang.getGioiTinh();
        if(gioiTinh.equals("Nam")){
            radioNam.setChecked(true);
//            gioiTinh = "Nam";
        }
        else if(gioiTinh.equals("Nữ")){
            radioNu.setChecked(true);
//            gioiTinh = "Nữ";
        }
        else {
            radioKhongCo.setChecked(true);
//            gioiTinh = "Không";
        }

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        //firebase
        mDatabase =  FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_KHACHHANG);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NHOMKH);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });

        editHoTen.setText(khachhang.getHoTenKhachHang());
        editDiaChi.setText(khachhang.getSoNha()+","+khachhang.getDiaChiXa()+","+khachhang.getDiaChiHuyen()+","+khachhang.getDiaChiTinh());
        editEmail.setText(khachhang.getEmail());
        editNgaySinh.setText(khachhang.getNgaySinh());
        editGhiChu.setText(khachhang.getGhiChu());
        editSDT.setText(khachhang.getSoDT());
        editDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDiaChiKhachHang(Gravity.CENTER);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.gtNam:
                        gioiTinh = "Nam";
                        break;
                    case R.id.gtNu:
                        gioiTinh = "Nữ";
                        break;
                    case R.id.gtkhongco:
                        gioiTinh = "Không";
                        break;
                }
            }
        });
        updateKhachHang();
        btnhuyTaoKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateDiaChiKhachHang(int gravity){
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
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tinh);
        if(tenTinh!= null){
           spnTinh.setText(khachhang.getDiaChiTinh());
           tenTinh = khachhang.getDiaChiTinh();
            spnTinh.setAdapter(adapterTinh);
            com = adapterTinh.getPosition(tenTinh);
        }
        adapterTinh.notifyDataSetChanged();
        if(com >= 0)
        {
            if(tenHuyen != null){
                String[] arrayHuyen = ArrayHuyen(com);
                spnHuyen.setText(tenHuyen);
                tenHuyen =khachhang.getDiaChiHuyen();
                adapterHuyen = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,arrayHuyen);
                tenHuyen = listDiaChi.get(com).getHuyens().get(0).getTenHuyen();
                spnHuyen.setAdapter(adapterHuyen);
                adapterHuyen.notifyDataSetChanged();
            }
            if(tenXa != null){
                spnXa.setText(tenXa);
                tenXa = khachhang.getDiaChiXa();
                adapterXa = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,listDiaChi.get(ViTri).getHuyens().get(com).getXa());
                spnXa.setAdapter(adapterXa);
                tenXa = listDiaChi.get(ViTri).getHuyens().get(com).getXa().get(0);
                adapterXa.notifyDataSetChanged();
            }
        }

        spnTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,arrayHuyen);
                spnHuyen.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                spnHuyen.setAdapter(adapterHuyen);
                adapterHuyen.notifyDataSetChanged();
                adapterXa = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,listDiaChi.get(position).getHuyens().get(0).getXa());
                spnXa.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                spnXa.setAdapter(adapterXa);

            }
        });

        spnHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();
                adapterXa = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item,listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                spnXa.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                spnXa.setAdapter(adapterXa);
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                adapterXa.notifyDataSetChanged();
            }
        });
        spnXa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
        btnHuyDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnTaoDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDiaChi.setText(soNha.getText()+","+tenXa+","+tenHuyen+","+tenTinh);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void updateKhachHang(){
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListNhomKH = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    NhomKhachHang nhomKhachHang = postSnapshot.getValue(NhomKhachHang.class);
                    String name = nhomKhachHang.getTenNhomKh();
                    arrayListNhomKH.add(name);
                }
                if (arrayListNhomKH.size() != 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuaKhachHang.this, R.layout.support_simple_spinner_dropdown_item, arrayListNhomKH);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spnNhomKhachHang.setAdapter(adapter);
                    if(khachhang.getNhomKhachKhach() != null){
                        int com = adapter.getPosition(khachhang.getNhomKhachKhach());
                        spnNhomKhachHang.setSelection(com);
                    }
                }
                btnLich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(SuaKhachHang.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editNgaySinh.setText(dayOfMonth+"/"+month+"/"+year);
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                btnTaoKhachHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_nhom_khachHang.setText("Nhóm khách hàng");
                        tv_nhom_khachHang.setTextColor(ContextCompat.getColor(SuaKhachHang.this, R.color.black));
                        spnNhomKhachHang.setBackgroundResource(R.drawable.bg_white_edittext);
                        if (editHoTen.getText().toString().isEmpty()){
                            editHoTen.setError("Hãy nhập tên khách hàng");
                            editHoTen.requestFocus();
                        }
                        else if(editSDT.getText().toString().isEmpty()){
                            editSDT.setError("Hãy nhập số điện thoại");
                            editSDT.requestFocus();
                        }
                        else if (arrayListNhomKH.size() == 0) {
                            tv_nhom_khachHang.setText("Chưa có nhóm khách hàng nào");
                            tv_nhom_khachHang.setTextColor(ContextCompat.getColor(SuaKhachHang.this, R.color.CPP));
                            spnNhomKhachHang.setBackgroundResource(R.drawable.bg_white_edittext_error);
                        }
                        else {
                            String name = editHoTen.getText().toString();
                            String SDT = editSDT.getText().toString();
                            String nha = soNha.getText().toString();
                            String nhomKh = spnNhomKhachHang.getSelectedItem().toString();
                            String ngaysinh = editNgaySinh.getText().toString();
                            String email = editEmail.getText().toString();
                            String ghichu = editGhiChu.getText().toString();
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("hoTenKhachHang").setValue(name);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("soDT").setValue(SDT);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("nhomKhachKhach").setValue(nhomKh);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("ngaySinh").setValue(ngaysinh);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("ghiChu").setValue(ghichu);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("email").setValue(email);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("soNha").setValue(nha);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("diaChiTinh").setValue(tenTinh);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("diaChiHuyen").setValue(tenHuyen);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("diaChiXa").setValue(tenXa);
                            mDatabase.child(khachhang.getNhomKhachKhach()).child(khachhang.getSoDT()).child("gioiTinh").setValue(gioiTinh);
                            new SupportSaveLichSu(SuaKhachHang.this, "Đã update thông tin khách hàng: " + khachhang.getHoTenKhachHang());
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


    class docJSon extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject khuVuc = root.getJSONObject(i);
                    String tinhTP = khuVuc.getString("name");
                    JSONArray arrHuyen = khuVuc.getJSONArray("districts");
                    ArrayList<Huyen> huyens = new ArrayList<>();
                    for (int j = 0; j < arrHuyen.length(); j++) {
                        JSONObject khuVucHuyen = arrHuyen.getJSONObject(j);
                        String tenHuyen = khuVucHuyen.getString("name");
                        JSONArray arrXa = khuVucHuyen.getJSONArray("wards");
                        ArrayList<String> xas = new ArrayList<>();
                        for (int k = 0; k < arrXa.length(); k++) {
                            JSONObject khuVucXa = arrXa.getJSONObject(k);
                            String xa = khuVucXa.getString("name");
                            xas.add(xa);
                        }
                        Huyen huyen = new Huyen(tenHuyen, xas);
                        huyens.add(huyen);
                    }

                    DiaChi diaChi = new DiaChi(tinhTP, huyens);
                    listDiaChi.add(diaChi);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            //Create a url object
            URL url = new URL(theUrl);

            //create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            //read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
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
