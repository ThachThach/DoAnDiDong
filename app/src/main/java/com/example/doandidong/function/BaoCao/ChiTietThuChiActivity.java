package com.example.doandidong.function.BaoCao;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.FormatDate;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.CapNhatBaoCaoThuChiAdapter;
import com.example.doandidong.Model.ThuChi;
import com.example.doandidong.R;
import com.example.doandidong.database.DbBaoCao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChiTietThuChiActivity extends AppCompatActivity {
    private CapNhatBaoCaoThuChiAdapter capNhatBaoCaoThuChiAdapter;
    private RecyclerView recyclerView;
    private Button back;
    private DatabaseReference mDatabase;
    private List<ThuChi> list;private DbBaoCao dataSql;
    private String ngayBatDau;
    private String ngayKetThuc;
    private Calendar calendar;
    private int id;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_thu_chi);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        DatabaseSQlite();
        displayItem1();
    }

    private void displayItem1() {
        recyclerView = findViewById(R.id.recycler_bao_cao_thu_chi);

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(ChiTietThuChiActivity.this, LinearLayoutManager.VERTICAL,false));

        capNhatBaoCaoThuChiAdapter = new CapNhatBaoCaoThuChiAdapter(this);
        recyclerView.setAdapter(capNhatBaoCaoThuChiAdapter);

        displayItem();
    }
    private void displayItem(){
        ArrayList<String> mangNgay = MangNgay();
        for (String st: mangNgay) {
            FirebaseDatabase.getInstance().getReference().child("CuaHangOder").child(ID_CUAHANG).child("bienlai").
                    child("chi").child(st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot snap:snapshot.getChildren()) {
                            String key1 = snap.getKey();
                            String key = snap.getKey();
                            ThuChi thuChi = snap.getValue(ThuChi.class);
                            String lyDo = thuChi.getLydo();
                            long tongChi = thuChi.getTongchi();
                            list.add(new ThuChi(lyDo,tongChi,key1+ "  " +changeDate(key)));
                            capNhatBaoCaoThuChiAdapter.setData(list);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public String changeDate(String date) {
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if (dates == 0) {
            return "";
        }
        Date date1 = new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        Log.d("simpleDateFormat", aaa + "");
        return aaa;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void DatabaseSQlite() {
        // Tạo database
        dataSql = new DbBaoCao(this, "app_database.sqlite", null, 2);
        dataSql.QueryData("CREATE TABLE IF NOT EXISTS KieuHienThiBaoCao(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NgayBatDau VARCHAR(200), " +
                "NgayKetThuc VARCHAR(200), " +
                "KieuHienThi INTEGER DEFAULT 1);");


        //dataSql.QueryData("UPDATE CongViec SET NgayBatDau = 'Long' WHERE Id = '5'");

        Cursor dataHienThiBaoCao = dataSql.GetData("SELECT * FROM KieuHienThiBaoCao");

        if (dataHienThiBaoCao.getCount() > 0) {
            while (dataHienThiBaoCao.moveToNext()) {
                id = dataHienThiBaoCao.getInt(0);
                ngayBatDau = dataHienThiBaoCao.getString(1);
                ngayKetThuc = dataHienThiBaoCao.getString(2);
            }
        } else {
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.clear();
            Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
            calendar.setTimeInMillis(toDay);
            String homNay = CustomNgay2(calendar, 0);
            ngayBatDau = homNay;
            ngayKetThuc = homNay;

            dataSql.QueryData("INSERT INTO KieuHienThiBaoCao VALUES(null, '" + homNay + "', '" + homNay + "', 1)");
        }
    }

    private String CustomNgay2(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(toDay);
        Calendar cal = calendar;
        cal.add(Calendar.DAY_OF_YEAR, amount);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

    private ArrayList<String> MangNgay() {
        ArrayList<String> arrNgay = new ArrayList<String>();
        FormatDate formatDate = new FormatDate();

        int days = formatDate.truThoiGian(ngayBatDau, ngayKetThuc);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(ngayKetThuc);
        } catch (ParseException e) {

        }
        cal.setTime(date);

        if (days != 0) {
            arrNgay.add(ngayKetThuc.replaceAll("/", "-"));
            for (int i = 0; i < days; i++) {
                String ngay = CustomNgay(cal, -1).replaceAll("/", "-");
                arrNgay.add(ngay);
            }
        } else {
            String ngay = ngayBatDau.replaceAll("/", "-");
            arrNgay.add(ngay);
        }
        return arrNgay;
    }

    public String CustomNgay(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

}