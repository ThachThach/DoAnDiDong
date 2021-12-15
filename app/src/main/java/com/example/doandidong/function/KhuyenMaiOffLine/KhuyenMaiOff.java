package com.example.doandidong.function.KhuyenMaiOffLine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterChonKhuyenMai;
import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterDanhSachKhuyenMai;
import com.example.doandidong.Model.KhachHang.NhomKhachHang;
import com.example.doandidong.Model.KhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.function.ThietLapActivity;
import java.util.ArrayList;
import java.util.Calendar;


public class KhuyenMaiOff extends AppCompatActivity {
    private Toolbar toolbar;
    ProgressBar progressBar;
    String id_CuaHang;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView spnNhomKhachHang;
    private DatabaseReference mDatabase, mDatabase2,mDatabase3;
    private ArrayList<NhomKhachHang> nhomKhachHangs;
    private FirebaseDatabase firebaseDatabase;
    private String STR_NKH = "nhomkhachhang";
    private NhomKhachHang nhomKhachHang;
    private ArrayList<String> arrayListKhachHang;
    private String stamps;
    Button giakhuyenmai, themkhoanggia, btnDialogHuyThemDVT, btnThemDialogThemDVT, bnt_huy, bnt_them,bnt_khuyenmai;
    private Window window, window1, window2;
    private Dialog dialog, dialog1, dialog2,dialog3,dialog4;
    private KhuyenMaiOffModel khuyenMaiOffModel;
    private EditText giakhuyenmaitu, giakhuyenmaiden, giakhuyenmais;
    private RecyclerView rv_1;
    private ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels = new ArrayList<>();
    private AdapterChonKhuyenMai adapterChonKhuyenMai;
    private AdapterDanhSachKhuyenMai adapterDanhSachKhuyenMai;
    ArrayList<KhuyenMaiOffModel> offModels=new ArrayList<>();
    RecyclerView rv_2;
    TextView datebatdau,dateketthuc;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText edtnoidungkhuyenmai,edttenkhuyenmai;
    TextView title;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai_off);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thiết lập Khuyến Mãi");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference(id_CuaHang).child(STR_NKH);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuyenmaioff");
        mDatabase3 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("danhsachkhuyenmaioff");
        spnNhomKhachHang = findViewById(R.id.spn_khuvuc);
        giakhuyenmai = findViewById(R.id.giakhuyenmai);
        themkhoanggia = findViewById(R.id.themkhoanggia);
        bnt_khuyenmai = findViewById(R.id.bnt_khuyenmai);
        datebatdau = findViewById(R.id.datebatdau);
        dateketthuc = findViewById(R.id.dateketthuc);
        edtnoidungkhuyenmai = findViewById(R.id.edtnoidungkhuyenmai);
        edttenkhuyenmai = findViewById(R.id.edttenkhuyenmai);
        rv_2 = findViewById(R.id.rv_2);

        Hampickerdatebatdau();
        Hampickerdateketthuc();
//
        //dialog hiện thành công
        Hamdialogsucces();
        hamdialogkiemtra();

        DanhSachNhomKhachHang();
        dialog1 = new Dialog(KhuyenMaiOff.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemgiakhuyenmaioff);
        window1 = dialog1.getWindow();

        dialog2 = new Dialog(KhuyenMaiOff.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialogdanhsachchon_khuyenmaioff);
        window2 = dialog2.getWindow();
        giakhuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailongGiaKhuyenMai(10);
            }
        });
        themkhoanggia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chongiakhuyenmai(10);
            }
        });
        HampushdanhsachKhuyenmai();

    }

    public void DanhSachNhomKhachHang() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListKhachHang = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        nhomKhachHang = snapshot1.getValue(NhomKhachHang.class);
                        String nhomkhachHang = nhomKhachHang.getTenNhomKh();
                        arrayListKhachHang.add(nhomkhachHang);
                    }
                    if (arrayListKhachHang.size() != 0) {
                        adapter = new ArrayAdapter<String>(KhuyenMaiOff.this, R.layout.support_simple_spinner_dropdown_item, arrayListKhachHang);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                        spnNhomKhachHang.setAdapter(adapter);

                    }
                    spnNhomKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            stamps = parent.getItemAtPosition(position).toString();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dailongGiaKhuyenMai(int gravity) {
        giakhuyenmaitu = dialog1.findViewById(R.id.giakhuyenmaitu);
        giakhuyenmaiden = dialog1.findViewById(R.id.giakhuyenmaiden);
        giakhuyenmais = dialog1.findViewById(R.id.giakhuyenmais);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);

        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        windownAttributes.gravity = gravity;
        window1.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
            dialog1.setCancelable(false);
        }

        btnDialogHuyThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = giakhuyenmaitu.getText().toString();
                String name1 = giakhuyenmaiden.getText().toString();
                String name2 = giakhuyenmais.getText().toString();
                if (giakhuyenmaitu.getText().toString().isEmpty()) {
                    giakhuyenmaitu.setError("Hãy nhập giá Khuyến mãi từ");
                    giakhuyenmaitu.requestFocus();
                } else if (giakhuyenmaiden.getText().toString().isEmpty()) {
                    giakhuyenmaiden.setError("Hãy nhập giá Khuyến mãi đến");
                    giakhuyenmaiden.requestFocus();
                } else if (giakhuyenmais.getText().toString().isEmpty()) {
                    giakhuyenmais.setError("Hãy nhập giá nhập khuyến mãi");
                    giakhuyenmais.requestFocus();
                } else {
                    String id = mDatabase2.push().getKey();
                    khuyenMaiOffModel = new KhuyenMaiOffModel(name, name1, name2, id);
                    mDatabase2.child(id).setValue(khuyenMaiOffModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            giakhuyenmaitu.setText("");
                            giakhuyenmaiden.setText("");
                            giakhuyenmais.setText("");
                            dialog1.dismiss();
                            dialog.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });

        dialog1.show();
    }

    private void Chongiakhuyenmai(int gravity) {
        if (window2 == null) {
            return;
        }
        rv_1 = dialog2.findViewById(R.id.rv_1);
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khuyenMaiOffModels= new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String id = postSnapshot.getKey();
                    String giakhuyenmai = postSnapshot.child("giakhuyenmai").getValue() + "";
                    String giakhuyenmaiden = postSnapshot.child("giakhuyenmaiden").getValue() + "";
                    String giakhuyenmaitu = postSnapshot.child("giakhuyenmaitu").getValue() + "";
                    khuyenMaiOffModels.add(new KhuyenMaiOffModel(giakhuyenmaitu, giakhuyenmaiden, giakhuyenmai, id));
                }
                adapterChonKhuyenMai = new AdapterChonKhuyenMai(khuyenMaiOffModels);
                rv_1.setLayoutManager(new LinearLayoutManager(KhuyenMaiOff.this, LinearLayoutManager.VERTICAL, false));
                rv_1.setAdapter(adapterChonKhuyenMai);
                adapterChonKhuyenMai.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        bnt_huy = dialog2.findViewById(R.id.bnt_huy);
        bnt_them = dialog2.findViewById(R.id.bnt_them);
        bnt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offModels = adapterChonKhuyenMai.PublicArraylist();
                Log.d("offModels", offModels.size() + "");
                adapterDanhSachKhuyenMai = new AdapterDanhSachKhuyenMai(offModels,KhuyenMaiOff.this);
                rv_2.setLayoutManager(new LinearLayoutManager(KhuyenMaiOff.this, LinearLayoutManager.VERTICAL, false));
                rv_2.setAdapter(adapterDanhSachKhuyenMai);
                adapterDanhSachKhuyenMai.notifyDataSetChanged();
                dialog2.dismiss();
            }
        });
        bnt_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        window2.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window2.getAttributes();
        windownAttributes.gravity = gravity;
        window2.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog2.setCancelable(true);
        } else {
            dialog2.setCancelable(false);
        }
        dialog2.show();
    }
    //ham xoa item dung de xoa ben adapter
    public void delete(final int position,delete1 delete1){

        new AlertDialog.Builder(KhuyenMaiOff.this).setMessage(
                "Bạn Muốn Xóa khuyến Mãi Này ??"
        ).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //goi interface
                delete1.delete();
                offModels.remove(position);
            }
        }).setNegativeButton("No", null)
                .show();
    }
   public interface delete1
   {
       void delete();
   }
   private void Hampickerdatebatdau(){
       final Calendar c = Calendar.getInstance();
       mYear = c.get(Calendar.YEAR);
       mMonth = c.get(Calendar.MONTH);
       mDay = c.get(Calendar.DAY_OF_MONTH);
       datebatdau.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(KhuyenMaiOff.this,
                       new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {

                               datebatdau.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, mYear, mMonth, mDay);
               datePickerDialog.show();
           }
       });
   }
    private void Hampickerdateketthuc(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(KhuyenMaiOff.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateketthuc.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
    private void hamdialogkiemtra(){
        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.dialog_canhbao);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog3.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog3.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.setCancelable(false);
        dialog3.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button Okay = dialog3.findViewById(R.id.btn_cancel);
         title = dialog3.findViewById(R.id.title);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });


    }
    private void Hamdialogsucces(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(KhuyenMaiOff.this, "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(KhuyenMaiOff.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    private void HampushdanhsachKhuyenmai(){

        bnt_khuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailonghoitruockhitao();
                dialog4.show();

            }
        });
    }
    private void dailonghoitruockhitao() {
        dialog4 = new Dialog(KhuyenMaiOff.this);
        dialog4.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog4.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog4.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog4.setCancelable(false); //Optional
        dialog4.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
        title =dialog4.findViewById(R.id.title);
        title.setText("Bạn!!Chắc Chắn Muốn Thiết Lập Khuyến Mãi");
        Button Okay = dialog4.findViewById(R.id.btn_okay);
        Button Cancel = dialog4.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edttenkhuyenmai.getText().toString().isEmpty()){
                    edttenkhuyenmai.setError("Hãy nhập Tên Khuyến Mãi");
                    edttenkhuyenmai.requestFocus();
                }
                else if (edtnoidungkhuyenmai.getText().toString().isEmpty()) {
                    edtnoidungkhuyenmai.setError("Hãy nhập Nội Dung Khuyến Mãi");
                    edtnoidungkhuyenmai.requestFocus();
                }

                else if(datebatdau.getText().toString().equals("00-00-0000")){
                    title.setText("Hãy Chọn Ngày Bắt Đầu Khuyến Mãi");
                    dialog3.show();


                }
                else if(dateketthuc.getText().toString().equals("00-00-0000")){
                    title.setText("Hãy Chọn Ngày Kết Thúc Khuyến Mãi");
                    dialog3.show();
                }
                else if(stamps==null){
                    title.setText("Hãy Chọn Nhóm Khách Hàng");
                    dialog3.show();
                }
                else if(offModels.size()==0){
                    title.setText("Hãy Chọn Danh Sách Tiền Khuyến Mãi");
                    dialog3.show();
                }
                else {
                    String id = mDatabase3.push().getKey();
                    mDatabase3.child(id).child("Noidungkhuyenmai").setValue(edtnoidungkhuyenmai.getText().toString());
                    mDatabase3.child(id).child("Tenkhuyenmai").setValue(edttenkhuyenmai.getText().toString());
                    mDatabase3.child(id).child("Ngaybatdau").setValue(datebatdau.getText().toString());
                    mDatabase3.child(id).child("Ngayketthuc").setValue(dateketthuc.getText().toString());
                    mDatabase3.child(id).child("Nhomkhachhang").setValue(stamps);
                    mDatabase3.child(id).child("Giasale").setValue(offModels);
                    Intent intent = new Intent(KhuyenMaiOff.this, ThietLapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                dialog4.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;

    }

}