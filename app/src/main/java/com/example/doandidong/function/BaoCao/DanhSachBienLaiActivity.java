package com.example.doandidong.function.BaoCao;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.FormatDate;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.DanhSachHoaDonAdapter;
import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.model.BienLai;
import com.example.doandidong.function.DonHangOnline.data.DonHang;
import com.example.doandidong.function.DonHangOnline.data.SanPham;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DanhSachBienLaiActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    private RecyclerView recycleview;
    private DanhSachHoaDonAdapter danhSachHoaDonAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String ID_CUAHANG;
    private ArrayList<BienLai> arrayList = new ArrayList<>();
    private ArrayList<BienLai> arrayListAll = new ArrayList<>();
    private ArrayList<BienLai> arrayListLoai1 = new ArrayList<>();
    private ArrayList<BienLai> arrayListLoai2 = new ArrayList<>();
    private ProgressBar progressBar;
    private ImageView image;
    private TextView tv_thongbao,tv_sTongDon;
    private Spinner spinner;
    private SwipeRefreshLayout swipeRefreshlayout;
    private ArrayList<String> listNgay = new ArrayList<>();
    private int loai = 1;
    private String ngayBD;
    private String ngayKT;
    private MaterialDatePicker materialDatePicker;
    private Calendar calendar2;
    private ArrayList<Integer> listCheckSizeOnline = new ArrayList<>();
    private ArrayList<Integer> listCheckSizeCuaHang = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bien_lai);
        IDLayout();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        spinner.setOnItemSelectedListener(DanhSachBienLaiActivity.this);
        dialogDate();
        displayItem();
        getDataBL();
        setData();
    }

    private void getDataBL() {
        listCheckSizeOnline.clear();
        listCheckSizeCuaHang.clear();
        listNgay.clear();
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }else {
            arrayList.clear();
        }
        arrayListAll.clear();
        arrayListLoai1.clear();
        arrayListLoai2.clear();
        if (loai == 1) {
            listNgay = MangNgay();
        }else {
            listNgay = mangArr(ngayBD, ngayKT);
        }
        danhSachHoaDonAdapter.notifyDataSetChanged();
        for (String st: listNgay) {
            FirebaseDatabase.getInstance().getReference().child("CuaHangOder/"+ID_CUAHANG).child("bienlai/thu").child(st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if ( snapshot.getValue() != null) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            BienLai bienLai = dataSnapshot.getValue(BienLai.class);
                            bienLai.setLoai(false);
                            bienLai.setKey(dataSnapshot.getKey());
                            arrayListAll.add(0, bienLai);
                            arrayListLoai1.add(0, bienLai);
                        }

                        listCheckSizeCuaHang.add(1);
                    }else {
                        listCheckSizeCuaHang.add(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DanhSachBienLaiActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            });
            FirebaseDatabase.getInstance().getReference().child("CuaHangOder/"+ID_CUAHANG).child("donhangonline/dondadat").child(st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null){
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                            DonHang donHang = dataSnapshot.getValue(DonHang.class);
                            if (donHang.getTrangthai() == 6 || donHang.getTrangthai() == 7) {
                                BienLai bienLai = new BienLai();
                                ArrayList<com.example.doandidong.function.BaoCao.model.SanPham> sp = new ArrayList<>();
                                ArrayList<SanPham> sanPhams = donHang.getSanpham();
                                for (SanPham sanPham: sanPhams) {
                                    String name = sanPham.getNameProduct();
                                    int soluong = sanPham.getSoluong();
                                    String img = sanPham.getImgProduct();
                                    com.example.doandidong.function.BaoCao.model.SanPham s = new com.example.doandidong.function.BaoCao.model.SanPham();
                                    s.setNameProduct(name);
                                    s.setSoluong(soluong);
                                    s.setImgProduct(img);
                                    sp.add(s);

                                }

                                bienLai.setSanpham(sp);
                                bienLai.setTongtien(200000);
                                bienLai.setKey(dataSnapshot.getKey());
                                bienLai.setLoai(true);
                                arrayListAll.add(0, bienLai);
                                arrayListLoai2.add(0, bienLai);
                            }

                        }
                        listCheckSizeOnline.add(1);
                    }else {
                        listCheckSizeOnline.add(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void setData () {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listCheckSizeOnline.size() == listNgay.size() && listCheckSizeCuaHang.size() == listNgay.size()) {
                    int checkNull = 0;
                    int checkNull2 = 0;
                    for (int i = 0; i < listNgay.size(); i++) {
                        if (listCheckSizeCuaHang.get(i) == 0) {
                            checkNull++;
                        }
                        if (listCheckSizeOnline.get(i) == 0) {
                            checkNull2++;
                        }
                    }
                    if (checkNull == listNgay.size() && checkNull2 == listNgay.size()) {
                        progressBar.setVisibility(View.GONE);
                        image.setImageResource(R.drawable.empty_list);
                        tv_thongbao.setText("Chưa có dữ liệu");
                        tv_sTongDon.setText("0");
                        swipeRefreshlayout.setRefreshing(false);
                        danhSachHoaDonAdapter.notifyDataSetChanged();
                        return;
                    } else {
                        arrayList.clear();
                        arrayList.addAll(arrayListAll);
                        progressBar.setVisibility(View.GONE);
                        image.setImageResource(0);
                        tv_thongbao.setText("");
                        tv_sTongDon.setText(arrayList.size()+"");
                        swipeRefreshlayout.setRefreshing(false);
                        danhSachHoaDonAdapter.notifyDataSetChanged();
                    }
                }else {
                    setData();
                }
            }
        },100);
    }

    private void IDLayout() {
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.image);
        tv_thongbao = findViewById(R.id.tv_thongbao);
        spinner = findViewById(R.id.spinner);
        tv_sTongDon = findViewById(R.id.tv_sTongDon);
        swipeRefreshlayout = findViewById(R.id.swipeRefreshlayout);

        swipeRefreshlayout.setOnRefreshListener(DanhSachBienLaiActivity.this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DanhSachBienLaiActivity.this, R.array.sort_bienlai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private void displayItem(){
        recycleview = findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));

        danhSachHoaDonAdapter = new DanhSachHoaDonAdapter(this, arrayList);
        recycleview.setAdapter(danhSachHoaDonAdapter);

        danhSachHoaDonAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> MangNgay() {
        Calendar c = GregorianCalendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayBD = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        String ngayKT = df.format(c.getTime());


        ArrayList<String> arrNgay = new ArrayList<String>();
        FormatDate formatDate = new FormatDate();

        int days = formatDate.truThoiGian(ngayBD, ngayKT);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKT);
        } catch (ParseException e) {

        }
        cal.setTime(date);

        if (days != 0) {
            arrNgay.add(ngayKT.replaceAll("/", "-"));
            for (int i = 0; i < days; i++) {
                String ngay = CustomNgay(cal, -1).replaceAll("/", "-");
                arrNgay.add(ngay);
            }
        } else {
            String ngay = ngayBD.replaceAll("/", "-");
            arrNgay.add(ngay);
        }
        return arrNgay;
    }

    private String CustomNgay(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0);
            item.setTitle(spanString);
        }

        return true;
    }

    private void dialogDate() {

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();

        calendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar2.clear();

        calendar2.setTimeInMillis(toDay);

        calendar2.set(Calendar.YEAR, 2000);
        Long year = calendar2.getTimeInMillis();

        CalendarConstraints.Builder contraBuilder = new CalendarConstraints.Builder();
        contraBuilder.setStart(year);
        contraBuilder.setEnd(toDay);

        contraBuilder.setValidator(DateValidatorPointBackward.before(toDay));
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setCalendarConstraints(contraBuilder.build());
        materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String startDate = formatter.format(selection.first);
                String endDate = formatter.format(selection.second);
                ngayBD = startDate;
                ngayKT = endDate;
                loai = 2;
                getDataBL();
                setData();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tuanNay_moinhat:
                loai = 1;
                getDataBL();
                return true;
            case R.id.tuy_chinh:
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.spinner:
                Toast.makeText(DanhSachBienLaiActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private ArrayList<String> mangArr(String ngayBD, String ngayKT) {
        ArrayList<String> arrNgay = new ArrayList<String>();

        FormatDate formatDate = new FormatDate();
        int days = formatDate.truThoiGian(ngayBD, ngayKT);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKT);
        } catch (ParseException e) {

        }
        cal.setTime(date);

        if (days != 0) {
            arrNgay.add(ngayKT.replaceAll("/", "-"));
            for (int i = 0; i < days; i++) {
                String ngay = CustomNgay(cal, -1).replaceAll("/", "-");
                arrNgay.add(ngay);
            }
        } else {
            String ngay = ngayBD.replaceAll("/", "-");
            arrNgay.add(ngay);
        }
        return arrNgay;
    }

    @Override
    public void onRefresh() {
        getDataBL();
        setData();
    }

    private void locLoai (int position) {
        arrayList.clear();
        danhSachHoaDonAdapter.notifyDataSetChanged();
        if (position == 0) {
            arrayList.addAll(arrayListAll);
        }else if (position == 1){
            arrayList.addAll(arrayListLoai1);
        }else if (position == 2) {
            arrayList.addAll(arrayListLoai2);

        }
        tv_sTongDon.setText(arrayList.size() + "");
        danhSachHoaDonAdapter.notifyDataSetChanged();
        return;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        locLoai(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}