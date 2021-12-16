package com.example.doandidong.ChucNang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF.AdapterChonKhuyenMaiThanhToan;
import com.example.doandidong.Adapter.Package_ThanhToanAdapter.ThanhToanAdapter;
import com.example.doandidong.Data.ChucNangThanhToan.ProductPushFB;
import com.example.doandidong.Data.ChucNangThanhToan.ProuductPushFB1;
import com.example.doandidong.Data.DatBan.DatBanModel;
import com.example.doandidong.Data.DatBan.ID_datban;
import com.example.doandidong.Data.KhuyenMaiOffModel;
import com.example.doandidong.Data.ListKhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.SQL.Database_order;
import com.example.doandidong.ChucNang.KhuyenMaiOffLine.KhuyenMaiThanhToan;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private String id_ban, id_khuvuc;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1, mDatabase2, mFirebaseDatabase;
    private DatabaseReference mDatabasea;
    private ThanhToanAdapter thanhToanAdapter;
    private RecyclerView recyclerView;
    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";
    String id;
    private int kt = -1;
    Double giaTong = 0.0;
    private Button bnt_thanhtoan;
    private TextView totalTxt;
    private String trangthai;
    private String id_datban;
    private ArrayList<ProuductPushFB1> listmon;
    private Toolbar toolbar;
    private ArrayList<ProductPushFB> ListDate_yc;
    private Dialog dialogban;
    Window window;
    ImageButton bnt_threedot;
    TextView gopban, chuyenban, tachban, thoat, tvhuydon;
    TextView title, sotiendadattruoc;
    Long date;
    String code1;
    private Window window1, window2;
    private Dialog dialog, dialog1, dialog2;
    ArrayList<DatBanModel> datBanModels;
    ArrayList<ID_datban> ID_datbans;
    String id_bk, id_ne,id_bk1;
    String abc, tenban;
    String id_ngaydat;
    Button bnt_huy, bnt_them;
    RecyclerView rv_1;
    String id_CuaHang;
    double tongTienCuaHang;
    ProgressBar progressBar;
    ThongTinCuaHangSql thongTinCuaHangSql;
    ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels;
    ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels;
    AdapterChonKhuyenMaiThanhToan adapterChonKhuyenMai_thanhToan;
    ArrayList<KhuyenMaiOffModel> listchuyen;
    TextView taxTxt, totalTxt1;
    ArrayList<KhuyenMaiOffModel> khuyenMaiOffModel;
    double TongCongtatca =0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        id_CuaHang = "CuaHangOder/" + thongTinCuaHangSql.IDCuaHang();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        getDatasql();
        hamlaydate();
        Hamlaygiohientai();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thanh Toán");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getFirebase();
        totalTxt = findViewById(R.id.totalTxt);
        totalTxt1 = findViewById(R.id.totalTxt1);
        sotiendadattruoc = findViewById(R.id.sotiendadattruoc);
        taxTxt = findViewById(R.id.taxTxt);
        dailongsucces();
        bnt_threedot = findViewById(R.id.bnt_threedot);
        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        id_datban = intent1.getStringExtra("id_datban");

        OnclickMenuchucnang();
        bnt_thanhtoan = findViewById(R.id.bnt_xacnhan);
        id = id_ban + "_" + id_khuvuc;
        abc = id_ban + "_" + id_khuvuc;
        list = new ArrayList<>();
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        code1 = code + "";



        mDatabase1 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("MangDi");
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    trangthai = snapshot1.getValue() + "";
                    getData();
                    if (trangthai.equals("1")) {
                        bnt_threedot.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        TinhTongTien();
        hamdatban();
        OnclickThanhtoan();


    }

    public void getData() {
        if (trangthai.equals("0")) {
            if (id_ban + "_" + id_khuvuc != null) {
                mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc);
            }
        }
        if (trangthai.equals("1")) {
            if (id_datban != null) {
                mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("sanphamorder").child(id_datban);
            }
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    giaTong = 0.0;
                    ListDate_yc = new ArrayList<>();
                    listmon = new ArrayList<ProuductPushFB1>();
                    ArrayList<ProuductPushFB1> mm = new ArrayList<>();
                    Long date1 = Long.parseLong(snapshot.child("date").getValue() + "");
                    Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue() + "");
                    int trangThais = Integer.parseInt(snapshot.child("trangThai").getValue() + "");
                    DataSnapshot sss = snapshot.child("sanpham");
                    for (DataSnapshot postSnapshot : sss.getChildren()) {
                        String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                        int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                        String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                        Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
                        String Loai = postSnapshot.child("loai").getValue() + "";
                        String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
                        listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                        mm.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                    }
                    ProductPushFB product = new ProductPushFB(date1, flag, trangThais, mm);
                    ListDate_yc.add(product);
                    for (int i = 0; i < listmon.size(); i++) {
                        giaTong += listmon.get(i).getGiaProudct() * listmon.get(i).getSoluong();
                    }
                    totalTxt1.setText(TinhTongTien() + "");
                    progressBar.setVisibility(View.INVISIBLE);
                    totalTxt.setText(giaTong + "");
                    recyclerView = findViewById(R.id.rv_3);
                    //đô dữ liệu vào Thanh Toám
                    thanhToanAdapter = new ThanhToanAdapter(listmon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setFocusable(false);
                    recyclerView.setAdapter(thanhToanAdapter);
                    thanhToanAdapter.notifyDataSetChanged();
                    if (listmon.size() > 0) {
                        bnt_thanhtoan.setEnabled(true);
                        bnt_threedot.setEnabled(true);
                    }
                } else {
                    bnt_threedot.setEnabled(false);
                    bnt_thanhtoan.setEnabled(false);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void OnclickThanhtoan() {

        bnt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

    }

    public String hamlaydate() {
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        Log.d("datenowww", date + "");
        return date;
    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    private void getDatasql() {

        database_order = new Database_order(this, "app_database.sqlite", null, 2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
    }

    private void XoaSpkhiOrder() {
        if (trangthai.equals("0")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
        } else if (trangthai.equals("1")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id_datban + "'");
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (listmon.size() > 0) {
            if (item_id == R.id.order) {
                Toast.makeText(this, "order thêm", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ThanhToanActivity.this, MonOrder.class);
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                startActivity(intent);
            }
        }

//        if (item_id == R.id.bacham) {
//            Chongiakhuyenmai(10);
//            Toast.makeText(this, "Chọn Danh sách Khuyến Mãi", Toast.LENGTH_LONG).show();
//
//            return true;
//        }
        if (item_id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

    private void HamTaodialog(int gravity) {

        dialogban = new Dialog(ThanhToanActivity.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dailongthanhtoan);

        window = dialogban.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialogban.setCancelable(true);
        } else {
            dialogban.setCancelable(false);
        }
        gopban = dialogban.findViewById(R.id.tvgopban);
        chuyenban = dialogban.findViewById(R.id.tvchuyenban);
//        chuyenban.setEnabled(false);
        tachban = dialogban.findViewById(R.id.tvtachban);
        thoat = dialogban.findViewById(R.id.thoat);
        tvhuydon = dialogban.findViewById(R.id.tvhuydon);
        OnclickChucnang(gopban, chuyenban, tachban, thoat, tvhuydon);
        dialogban.show();
    }

    public void OnclickMenuchucnang() {
        bnt_threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HamTaodialog(Gravity.BOTTOM);
            }
        });
    }

    public void OnclickChucnang(TextView gopban, TextView chuyenban, TextView tachban, TextView thoat, TextView tvhuydon) {
        gopban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);

                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code1).child("trangthai").setValue("1");
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai", code1);
                Gson gson = new Gson();
                String a = gson.toJson(listmon);
                ;
                String c = gson.toJson(datBanModels);
                intent.putExtra("list_as_string2", c);
                intent.putExtra("list_as_string", a);

                startActivity(intent);
            }
        });
        chuyenban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                FirebaseDatabase.getInstance().getReference(id_CuaHang).child("chucnang").child(code1).child("trangthai").setValue("2");
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai", code1);
                intent.putExtra("date", date);
                Gson gson = new Gson();
                String a = gson.toJson(listmon);
                String b = gson.toJson(ListDate_yc);
                String c = gson.toJson(datBanModels);
                intent.putExtra("list_as_string", a);
                intent.putExtra("list_as_string1", b);
                intent.putExtra("list_as_string2", c);
                startActivity(intent);
            }
        });
        tachban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, TachBanActivity.class);

                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai", code1);
                Gson gson = new Gson();
                String b = gson.toJson(ListDate_yc);
                String a = gson.toJson(listmon);
                String c = gson.toJson(datBanModels);
                intent.putExtra("list_as_string", a);
                intent.putExtra("list_as_string1", b);
                intent.putExtra("list_as_string2", c);
                startActivity(intent);
            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThanhToanActivity.this, "Đóng", Toast.LENGTH_LONG).show();
                dialogban.dismiss();
            }
        });
        tvhuydon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailonghuydonhang();
                Toast.makeText(ThanhToanActivity.this, "Hủy Thanh Toán", Toast.LENGTH_LONG).show();
                title.setText("Bạn Chắc Chắn Muốn Hủy Đơn Hàng");
                dialogban.dismiss();
                dialog1.show();
            }
        });
    }

    private void dailongsucces() {

        dialog = new Dialog(ThanhToanActivity.this);
        dialog.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String tennhanvien = thongTinCuaHangSql.selectUser().getUsername();
                if (trangthai.equals("0")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_ban").setValue(id_ban);
                    databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                    databaseReference.child("tongtien").setValue(TinhTongTien());
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue(tennhanvien);
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").setValue(tongTienCuaHang + giaTong);
                            XoaSpkhiOrder();
                            new SupportSaveLichSu(ThanhToanActivity.this, "Thanh toán bàn: " + id_ban + "KV: " + id_khuvuc);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (id_ngaydat != null) {
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(id_ban + "_" + id_khuvuc).child(id_ngaydat).removeValue();
                    }
                    DanhSachDon();
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc).removeValue();
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("tenNhanVien").setValue("");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);
                    progressBar.setVisibility(View.VISIBLE);

                } else if (trangthai.equals("1")) {
                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_datban").setValue(id_datban);
                    databaseReference.child("tongtien").setValue(TinhTongTien());
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue(tennhanvien);
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").setValue(tongTienCuaHang + giaTong);
                            XoaSpkhiOrder();
                            new SupportSaveLichSu(ThanhToanActivity.this, "Thanh toán đơn hàng mang đi: " + id_datban);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_datban).removeValue();
                }

                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                dialog.dismiss();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void dailonghuydonhang() {
        dialog1 = new Dialog(ThanhToanActivity.this);
        dialog1.setContentView(R.layout.dialog_thanhtoan_aleart);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        title = dialog1.findViewById(R.id.title);
        Button Okay = dialog1.findViewById(R.id.btn_okay);
        Button Cancel = dialog1.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trangthai.equals("0")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_ban").setValue(id_ban);
                    databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(0);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue("aaa");
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            XoaSpkhiOrder();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (id_ngaydat != null) {
                        FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("DatBan").child(id_ban + "_" + id_khuvuc).child(id_ngaydat).removeValue();
                    }
                    DanhSachDon();
                    FirebaseDatabase.getInstance().getReference().child(id_CuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc).removeValue();
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("tenNhanVien").setValue("");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                    FirebaseDatabase.getInstance().getReference(id_CuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);

                }
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//                dialog1.dismiss();

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void hamdatban() {
        mDatabase = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("DatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ID_datbans = new ArrayList<>();
                datBanModels = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        id_ne = postSnapshot.getKey();
                        DataSnapshot sss = postSnapshot;
                        for (DataSnapshot aaa : sss.getChildren()) {
                            id_bk = aaa.child("id_bk").getValue() + "";
                            if (id_bk.equals(abc)) {
                                if ((aaa.child("trangthai").getValue() + "").equals("1")) {
                                    id_ngaydat = aaa.getKey();
                                    String giodat = aaa.child("giodat").getValue() + "";
                                    String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                    String ngaydat = aaa.child("ngaydat").getValue() + "";
                                    String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                    String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                    String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                    String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                    String tenban = aaa.child("tenban").getValue() + "";
                                    String trangthaidat = aaa.child("trangthai").getValue() + "";
                                    datBanModels.add(new DatBanModel(tenban, id_ngaydat, giodat, gioketthuc, id_bk, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang, trangthaidat));

                                }
                            }
                        }
                    }
                    if (datBanModels.size() > 0) {
                        sotiendadattruoc.setText("$" + datBanModels.get(0).getSotiendadattruoc());
                    }
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFirebase() {
        FirebaseDatabase.getInstance().getReference(id_CuaHang).child("bienlai/taichinh").child("tongTien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Double tien = snapshot.getValue(Double.class);
                    tongTienCuaHang = tien;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Chongiakhuyenmai(int gravity) {
        dialog2 = new Dialog(ThanhToanActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialogdanhsachchon_khuyenmaioff);
        window2 = dialog2.getWindow();
        if (window2 == null) {
            return;
        }
        rv_1 = dialog2.findViewById(R.id.rv_1);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(id_CuaHang).child("danhsachkhuyenmaioff");
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listKhuyenMaiOffModels = new ArrayList<ListKhuyenMaiOffModel>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    khuyenMaiOffModels = new ArrayList<KhuyenMaiOffModel>();
                    String Ngaybatdau = postSnapshot.child("Ngaybatdau").getValue() + "";
                    String Ngayketthuc = postSnapshot.child("Ngayketthuc").getValue() + "";
                    String Nhomkhachhang = postSnapshot.child("Nhomkhachhang").getValue() + "";
                    String Noidungkhuyenmai = postSnapshot.child("Noidungkhuyenmai").getValue() + "";
                    String Tenkhuyenmai = postSnapshot.child("Tenkhuyenmai").getValue() + "";
                    DataSnapshot aaa = postSnapshot.child("Giasale");
                    for (DataSnapshot snapshot2 : aaa.getChildren()) {
                        String giakhuyenmaiden = snapshot2.child("giakhuyenmaiden").getValue() + "";
                        String giakhuyenmai = snapshot2.child("giakhuyenmai").getValue() + "";
                        String giakhuyenmaitu = snapshot2.child("giakhuyenmaitu").getValue() + "";
                        String key = snapshot2.child("key").getValue() + "";
                        khuyenMaiOffModels.add(new KhuyenMaiOffModel(giakhuyenmaitu, giakhuyenmaiden, giakhuyenmai, key));
                    }
                    listKhuyenMaiOffModels.add(new ListKhuyenMaiOffModel(Ngaybatdau, Ngayketthuc, Nhomkhachhang, Noidungkhuyenmai, Tenkhuyenmai, khuyenMaiOffModels));
                }
//                totalTxt1.setText(TinhTongTien() + "");
                adapterChonKhuyenMai_thanhToan = new AdapterChonKhuyenMaiThanhToan(listKhuyenMaiOffModels, ThanhToanActivity.this);
                rv_1.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                rv_1.setAdapter(adapterChonKhuyenMai_thanhToan);
                adapterChonKhuyenMai_thanhToan.notifyDataSetChanged();
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
    @Override
    protected void onResume() {
        super.onResume();

        khuyenMaiOffModel = new ArrayList<>();

        if (KhuyenMaiThanhToan.khuyenMaiOffModel != null) {
            khuyenMaiOffModel = KhuyenMaiThanhToan.khuyenMaiOffModel;
            taxTxt.setText("$ " + KhuyenMaiThanhToan.khuyenMaiOffModel.get(0).getGiakhuyenmai());
            totalTxt1.setText(TinhTongTien() + "");
        }
    }

    private Double TinhTongTien() {
        double TongTien = 0.0;
        if (khuyenMaiOffModel != null && datBanModels!=null) {
            Log.d("ThanhToankj","0");
            if(khuyenMaiOffModel.size()>0 && datBanModels.size() > 0) {
                double tienDatban = Double.parseDouble(datBanModels.get(0).getSotiendadattruoc() + "");
                double tienKhuyenMai = Double.parseDouble(khuyenMaiOffModel.get(0).getGiakhuyenmai() + "");
                TongTien = giaTong- (tienKhuyenMai + tienDatban);
                Log.d("ThanhToankj","1");
            }
            else if(khuyenMaiOffModel.size()>0 && datBanModels.size() == 0) {
                Log.d("ThanhToankj","2");
                double tienKhuyenMai = Double.parseDouble(khuyenMaiOffModel.get(0).getGiakhuyenmai() + "");
                TongTien =giaTong -  tienKhuyenMai ;
            }
            else if(khuyenMaiOffModel.size()==0 && datBanModels.size() == 0){
                Log.d("ThanhToankj","2.0");
                TongTien = giaTong;
            }
            else if(khuyenMaiOffModel.size()==0 && datBanModels.size() >0){
                Log.d("ThanhToankj","2.1");
                double tienDatban = Double.parseDouble(datBanModels.get(0).getSotiendadattruoc() + "");
                TongTien = giaTong-tienDatban;
            }
            else {
                Log.d("ThanhToankj",khuyenMaiOffModel.size()+"2.2");
                Log.d("ThanhToankj",datBanModels.size()+"2.3");
                Log.d("ThanhToankj","2.4");
            }

        } else if (khuyenMaiOffModel ==null  || khuyenMaiOffModel.size()==0) {
            Log.d("ThanhToankj","3");
            if(datBanModels!=null){
                if(datBanModels.size() > 0){
                    Log.d("ThanhToankj","4");
                    double tienDatban = Double.parseDouble(datBanModels.get(0).getSotiendadattruoc() + "");
                    TongTien = giaTong - tienDatban;
                }
                else {
                    TongTien = giaTong ;
                }
            }

        } else {
            Log.d("ThanhToankj","5");
            TongTien = giaTong;
        }
        return TongTien;
    }

    private void DanhSachDon(){
        mDatabase = FirebaseDatabase.getInstance().getReference("DuyetDatBan");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datBanModels = new ArrayList<>();
                if (snapshot.getValue() != null) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        for (DataSnapshot postSnapshot : snapshot1.getChildren()) {
                            DataSnapshot sss = postSnapshot;
                            for (DataSnapshot aaa : sss.getChildren()) {
                                String trangthai_dat = aaa.child("trangthai_dat").getValue()+"";
                                id_bk1 = aaa.child("id_bk").getValue() + "";
                                String id_ngaydat1 = aaa.getKey();
                                if(trangthai_dat.equals("1")){
                                    if(id_bk1.equals(abc)){
                                        if(id_ngaydat1.equals(id_ngaydat)) {
                                            String giodat = aaa.child("giodat").getValue() + "";
                                            String gioketthuc = aaa.child("gioketthuc").getValue() + "";
                                            String ngaydat = aaa.child("ngaydat").getValue() + "";
                                            String ngayhientai = aaa.child("ngayhientai").getValue() + "";
                                            String sodienthoai = aaa.child("sodienthoai").getValue() + "";
                                            String sotiendattruoc = aaa.child("sotiendattruoc").getValue() + "";
                                            String tenkhachhang = aaa.child("tenkhachhang").getValue() + "";
                                            String tenban = aaa.child("tenban").getValue() + "";
                                            String trangthai = aaa.child("trangthai").getValue() + "";
                                            String id = aaa.getKey();
                                            String key_khachhang = aaa.child("key_khachhang").getValue() + "";
                                            datBanModels.add(new DatBanModel(tenban, id_ngaydat, giodat, gioketthuc, id_bk, ngaydat, ngayhientai, sodienthoai, sotiendattruoc, tenkhachhang, trangthai, id, key_khachhang));
                                            FirebaseDatabase.getInstance().getReference("DuyetDatBan").child(key_khachhang).child(id_ban + "_" + id_khuvuc).child(id_ngaydat).removeValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}