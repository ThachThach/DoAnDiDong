package com.example.doandidong.ChucNang.KhachHang;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class ThemKhachHang extends AppCompatActivity {
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
    private KhachHang khachHang = new KhachHang();
    private String gioiTinh;
    private RadioGroup radioGroup;
    private String ID_CUAHANG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkhachhang);
        editHoTen = findViewById(R.id.edtTenKhachHang);
        editSDT = findViewById(R.id.edtPhoneKhachHang);
        editDiaChi = findViewById(R.id.edtDiaChiKhachHang);
        editEmail = findViewById(R.id.edtEmailKhachHang);
        editGhiChu = findViewById(R.id.edtghichuKhachHang);
        editNgaySinh = findViewById(R.id.edtNgaySinh);
        spnNhomKhachHang = findViewById(R.id.spnNhomKhachHang);
        tv_nhom_khachHang = findViewById(R.id.tv_nhom_khachHang);
        btnLich = findViewById(R.id.imgLich);
        btnTaoKhachHang = findViewById(R.id.btnTaoKhachhang);
        btnhuyTaoKhachHang = findViewById(R.id.btnhuyTaoKhachHang);
        radioGroup = findViewById(R.id.radioGruop);
        radioNam = findViewById(R.id.gtNam);
        radioNu = findViewById(R.id.gtNu);
        radioKhongCo = findViewById(R.id.gtkhongco);
        //Dialog
        dialog = new Dialog(ThemKhachHang.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogthemdiachikhachhang);
        window = dialog.getWindow();
        spnTinh = dialog.findViewById(R.id.spinner_tinh);
        spnHuyen = dialog.findViewById(R.id.spinner_huyen);
        spnXa = dialog.findViewById(R.id.spinner_xa);
        soNha = dialog.findViewById(R.id.edtSoNha);
        btnHuyDiaChi = dialog.findViewById(R.id.btnhuyTaoDiaChiKhachHang);
        btnTaoDiaChi = dialog.findViewById(R.id.btnTaoDiaChiKhachhang);

        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        mDatabase =  FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_KHACHHANG);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NHOMKH);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSon().execute("https://provinces.open-api.vn/api/?depth=3");
            }
        });
       editDiaChi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TaoDiaChiKhachHang(Gravity.CENTER);
           }
       });
       radioKhongCo.setChecked(true);
       radioGroup = findViewById(R.id.radioGruop);
        gioiTinh = "Không";
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
        Log.d("zzzz", gioiTinh+"");

       TaoKhachHang();
       btnhuyTaoKhachHang.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void TaoKhachHang(){
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ThemKhachHang.this, R.layout.support_simple_spinner_dropdown_item, arrayListNhomKH);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spnNhomKhachHang.setAdapter(adapter);
                }
                btnLich.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(ThemKhachHang.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editNgaySinh.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        }, year,month, day);
                        datePickerDialog.show();
                    }
                });
                btnTaoKhachHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_nhom_khachHang.setText("Nhóm khách hàng");
                        tv_nhom_khachHang.setTextColor(ContextCompat.getColor(ThemKhachHang.this, R.color.black));
                        spnNhomKhachHang.setBackgroundResource(R.drawable.bg_white_edittext);
                        if (editHoTen.getText().toString().isEmpty()){
                            editHoTen.setError("Hãy nhập tên khách hàng");
                            editHoTen.requestFocus();
                        }
                        else if(editSDT.getText().toString().isEmpty()){
                            editSDT.setError("Hãy nhập số điện thoại");
                            editSDT.requestFocus();
                        }else if (arrayListNhomKH.size() == 0) {
                            tv_nhom_khachHang.setText("Chưa có nhóm khách hàng nào");
                            tv_nhom_khachHang.setTextColor(ContextCompat.getColor(ThemKhachHang.this, R.color.CPP));
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
                            khachHang = new KhachHang(name,SDT,tenTinh,tenHuyen,tenXa,nha,nhomKh,gioiTinh,email,ghichu,ngaysinh);
                            mDatabase.child(nhomKh).child(SDT).setValue(khachHang);
                            editHoTen.setText("");
                            editSDT.setText("");
                            editDiaChi.setText("");
                            editNgaySinh.setText("");
                            editEmail.setText("");
                            editGhiChu.setText("");
                            new SupportSaveLichSu(ThemKhachHang.this, "Đã thêm khách hàng: " + khachHang.getHoTenKhachHang());
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

    public void TaoDiaChiKhachHang(int gravity){
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
        spnTinh.setAdapter(adapterTinh);
        adapterTinh.notifyDataSetChanged();

        spnTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(ThemKhachHang.this, R.layout.support_simple_spinner_dropdown_item,arrayHuyen);
                spnHuyen.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                spnHuyen.setAdapter(adapterHuyen);

                adapterXa = new ArrayAdapter<String>(ThemKhachHang.this, R.layout.support_simple_spinner_dropdown_item,listDiaChi.get(position).getHuyens().get(0).getXa());
                spnXa.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                spnXa.setAdapter(adapterXa);
            }
        });

        spnHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(ThemKhachHang.this, R.layout.support_simple_spinner_dropdown_item,
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
