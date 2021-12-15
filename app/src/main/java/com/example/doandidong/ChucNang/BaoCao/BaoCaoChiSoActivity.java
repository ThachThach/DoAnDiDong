package com.example.doandidong.ChucNang.BaoCao;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import com.example.doandidong.Common.FormatDate;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BaoCaoChiSoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ValueLineChart lineChart;
    private BarChart mBarChart;
    private String ID_CUA_HANG;
    private ArrayList<String> listNgayTrongTuan;
    private String ngayBatDau;
    private String ngayKetThuc;
    private ArrayList<String> listNgay;

    private String ngayBatDau2;
    private String ngayKetThuc2;
    private ArrayList<String> listNgay2;
    private Button btn_chon_ngay;
    private Button btn_chon_nam;
    private SwipeRefreshLayout refresh;

    //Firebase
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private ArrayList<Integer> listCheckSizeDouble = new ArrayList<>();
    private ArrayList<Integer> listCheckSizeDouble2 = new ArrayList<>();
    private ArrayList<Integer> listCheckSizeChi = new ArrayList<>();
    private ArrayList<Integer> listCheckSizeDoanhSo = new ArrayList<>();
    private ArrayList<Double> listTienTuan = new ArrayList<>();
    private ArrayList<Double> listTienTuan2 = new ArrayList<>();
    private ArrayList<Double> listTienChi = new ArrayList<>();
    private ArrayList<Double> listDoanhSo = new ArrayList<>();

    private String batDau1, batDau2, batDau3, batDau4, batDau5, batDau6, batDau7, batDau8, batDau9, batDau10, batDau11, batDau12;
    private String ketThuc1, ketThuc2, ketThuc3, ketThuc4, ketThuc5, ketThuc6, ketThuc7, ketThuc8, ketThuc9, ketThuc10, ketThuc11, ketThuc12;
    private ArrayList<String> arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12;
    private boolean loai = false;
    private ArrayList<Double> doubles = new ArrayList<>();
    private ArrayList<Double> doublesChiPhi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_chi_so);

        IDLayout();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        setArr();
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUA_HANG = thongTinCuaHangSql.IDCuaHang();


        listNgay = MangNgay();
        getFirebaseThu();
        getFirebaseBienLai();
        setValue();
        setValues();
        setValuess();
        btn_chon_ngay.setBackgroundResource(R.color.colorShimmer);

        btn_chon_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = false;
                getFirebaseBienLai();
                setValues();
                btn_chon_nam.setEnabled(true);
                btn_chon_ngay.setEnabled(false);
                btn_chon_nam.setBackgroundResource(R.color.white);
                btn_chon_ngay.setBackgroundResource(R.color.colorShimmer);
            }
        });
        btn_chon_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loai = true;
                getFirebaseBienLai();
                setValuess();
                Log.d("ssssss", "sssss");
                btn_chon_ngay.setEnabled(true);
                btn_chon_nam.setEnabled(false);
                btn_chon_ngay.setBackgroundResource(R.color.white);
                btn_chon_nam.setBackgroundResource(R.color.colorShimmer);
            }
        });
        refresh.setOnRefreshListener(BaoCaoChiSoActivity.this);

    }

    private void IDLayout() {
        lineChart = (ValueLineChart) findViewById(R.id.lineChart);
        mBarChart = findViewById(R.id.doanhThuNgay);
        btn_chon_ngay = findViewById(R.id.btn_chon_ngay);
        btn_chon_nam = findViewById(R.id.btn_chon_nam);
        refresh = findViewById(R.id.refresh);
    }

    private void setLineChart() {
        lineChart.clearChart();
        ValueLineSeries series2 = new ValueLineSeries();
        series2.setColor(getColor(R.color.CPP));
        ValueLineSeries series3 = new ValueLineSeries();
        series3.setColor(getColor(R.color.Python));
        ValueLineSeries series4 = new ValueLineSeries();
        series4.setColor(getColor(R.color.doanh_so));

        if (loai){
            for (int i = 0; i< doubles.size(); i++) {
                float doanhSo = Float.parseFloat(doubles.get(i).toString());
                float chiPhi = Float.parseFloat(doublesChiPhi.get(i).toString());
                float doanhThu = doanhSo - chiPhi;
                series2.addPoint(new ValueLinePoint((i + 1) + "", doanhSo));
                series3.addPoint(new ValueLinePoint((i + 1) + "", doanhThu));
                series4.addPoint(new ValueLinePoint((i + 1) + "", chiPhi));
            }
        }else {
            for (int i = 0; i < listNgay2.size(); i++) {
                float doanhSo = Float.parseFloat(listDoanhSo.get(i).toString());
                float chiPhi = Float.parseFloat(listTienChi.get(i).toString());
                float doanhThu = doanhSo - chiPhi;
                series2.addPoint(new ValueLinePoint((i + 1) + "", doanhSo));
                series3.addPoint(new ValueLinePoint((i + 1) + "", doanhThu));
                series4.addPoint(new ValueLinePoint((i + 1) + "", chiPhi));
            }
        }


        lineChart.addSeries(series2);
        lineChart.addSeries(series3);
        lineChart.addSeries(series4);
        lineChart.startAnimation();
    }

    private void setBarChart(ArrayList<Double> listTien) {

        float t2 = Float.parseFloat(listTien.get(0).toString());
        float t3 = Float.parseFloat(listTien.get(1).toString());
        float t4 = Float.parseFloat(listTien.get(2).toString());
        float t5 = Float.parseFloat(listTien.get(3).toString());
        float t6 = Float.parseFloat(listTien.get(4).toString());
        float t7 = Float.parseFloat(listTien.get(5).toString());
        float cn = Float.parseFloat(listTien.get(6).toString());

        mBarChart.clearChart();

        mBarChart.addBar(new BarModel("Thứ 2", t2, 0xFF123456));
        mBarChart.addBar(new BarModel("Thứ 3", t3, 0xFF343456));
        mBarChart.addBar(new BarModel("Thứ 4", t4, 0xFF563456));
        mBarChart.addBar(new BarModel("Thứ 5", t5, 0xFF873F56));
        mBarChart.addBar(new BarModel("Thứ 6", t6, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("Thứ 7", t7, 0xFF343456));
        mBarChart.addBar(new BarModel("CN", cn, 0xFF1FF4AC));

        mBarChart.startAnimation();
    }

    private void getFirebaseThu() {
        listTienTuan.clear();
        listTienTuan2.clear();
        listCheckSizeDouble.clear();
        listCheckSizeDouble2.clear();
        listNgay.clear();

        ArrayList<String> listSteam = MangNgay();
        for (int i = listSteam.size() - 1; i >= 0; i--) {
            listNgay.add(listSteam.get(i));
        }

        for (String st : listNgay) {
            mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        Double tong = 0.0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            tong += Double.parseDouble(snap.child("tongtien").getValue().toString());
                        }
                        listTienTuan.add(tong);
                        listCheckSizeDouble.add(1);
                    } else {
                        listTienTuan.add(0.0);
                        listCheckSizeDouble.add(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/donhangonline/dondadat/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Double tong = 0.0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            int statuss = snapshot.child("trangthai").getValue(Integer.class);
                            if (statuss == 6) {
                                Double giaSP = 0.0;
                                Double khuyenMai = Double.parseDouble(snapshot.child("giaKhuyenMai").getValue().toString());
                                for (DataSnapshot snap : snapshot.child("sanpham").getChildren()) {
                                    int soluong = Integer.parseInt(snap.child("soluong").getValue().toString());
                                    Double giaProudct = Double.parseDouble(snap.child("giaBan").getValue().toString()) * soluong;
                                    giaSP += soluong * giaProudct;
                                }
                                tong += giaSP - khuyenMai;
                            }
                        }
                        listTienTuan2.add(tong);
                        listCheckSizeDouble2.add(1);
                    } else {
                        listTienTuan2.add(0.0);
                        listCheckSizeDouble2.add(0);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void getFirebaseBienLai() {
        listCheckSizeChi.clear();
        listTienChi.clear();
        listDoanhSo.clear();
        if (listNgay2 != null) {
            listNgay2.clear();
        } else {
            listNgay2 = new ArrayList<>();
        }
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH) + 1;
        if (loai == false) {
            ngayBatDau2 = "01/" + month + "/" + year;
            ngayKetThuc2 = setDateStartEnd(ngayBatDau2);
            ArrayList<String> listSteam = mangArr(ngayBatDau2, ngayKetThuc2);
            for (int i = listSteam.size() - 1; i >= 0; i--) {
                listNgay2.add(listSteam.get(i));
            }
            Log.d("qqq", listNgay2.size()+"");

            for (String st : listNgay2) {
                mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/bienlai/chi/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            Double tong = 0.0;
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                tong += Double.parseDouble(snap.child("tongchi").getValue().toString());
                            }
                            listTienChi.add(tong);
                            listCheckSizeChi.add(1);
                        } else {
                            listTienChi.add(0.0);
                            listCheckSizeChi.add(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            Double tong = 0.0;
                            int soLuong = 0;
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                tong += Double.parseDouble(snap.child("tongtien").getValue().toString());
                                soLuong++;
                            }
                            listDoanhSo.add(tong);
                            listCheckSizeDoanhSo.add(1);
                        } else {
                            listDoanhSo.add(0.0);
                            listCheckSizeDoanhSo.add(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        } else {
            ngayBatDau2 = "01/01/" + year;
            ngayKetThuc2 = "31/12/" + year;
            ArrayList<String> listSteam = mangArr(ngayBatDau2, ngayKetThuc2);
            for (int i = listSteam.size() - 1; i >= 0; i--) {
                listNgay2.add(listSteam.get(i));
            }
            for (String st : listNgay2) {
                mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/bienlai/chi/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Double tong = 0.0;
                        if (snapshot.getValue() != null) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                tong += Double.parseDouble(snap.child("tongchi").getValue().toString());
                            }
                            checkDate(tong, st);
                        }
                        demNgay++;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mFirebaseDatabase.child("CuaHangOder/" + ID_CUA_HANG + "/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            Double tong = 0.0;
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                tong += Double.parseDouble(snap.child("tongtien").getValue().toString());
                            }
                            checkChiPhi(tong, st);
                        }
                        demNgay2++;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

    }

    private void setValue() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listCheckSizeDouble.size() == listNgay.size() && listCheckSizeDouble2.size() == listNgay.size()) {
                    int checkNull = 0;
                    int checkNull2 = 0;
                    for (int i = 0; i < listNgay.size(); i++) {
                        if (listCheckSizeDouble.get(i) == 0) {
                            checkNull++;
                        }
                        if (listCheckSizeDouble2.get(i) == 0) {
                            checkNull2++;
                        }
                    }

                    if (checkNull == listNgay.size() && checkNull2 == listNgay.size()) {
                        return;
                    } else {
                        for (int i = 0; i < listNgay.size(); i++) {
                            Double stamp = listTienTuan.get(i) + listTienTuan2.get(i);
                            listTienTuan.set(i, stamp);
                        }
                        setBarChart(listTienTuan);
                        refresh.setRefreshing(false);
                    }
                } else {
                    setValue();
                }
            }
        }, 100);
    }

    private void setValues() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loai == false) {
                    if (listTienChi.size() == listNgay2.size() && listDoanhSo.size() == listNgay2.size()) {
                        int checkNull = 0;
                        int checkNull2 = 0;
                        for (int i = 0; i < listNgay2.size(); i++) {
                            if (listCheckSizeChi.get(i) == 0) {
                                checkNull++;
                            }
                            if (listCheckSizeDoanhSo.get(i) == 0) {
                                checkNull2++;
                            }
                        }

                        if (checkNull == listNgay2.size() && checkNull2 == listNgay2.size()) {
                            return;
                        } else {
                            setLineChart();
                             refresh.setRefreshing(false);
                        }
                    } else {
                        setValues();
                    }
                }
            }
        }, 100);
    }

    int demNgay = 0;
    int demNgay2 = 0;
    private void setValuess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loai == true) {
                    if (demNgay == listNgay2.size() && demNgay2 == listNgay2.size()) {
                        demNgay = 0;
                        demNgay2 = 0;
                        setLineChart();
                    } else {
                        setValuess();
                        refresh.setRefreshing(false);
                    }
                } else {

                }

            }
        }, 100);
    }

    private ArrayList<String> MangNgay() {
        // Get calendar set to current date and time
        Calendar c = GregorianCalendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        ngayBatDau = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        ngayKetThuc = df.format(c.getTime());
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

    private String CustomNgay(Calendar calendar, int amount) {
        String dinhDang = "dd/MM/yyyy";
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(dinhDang);
        String startDate = formatter.format(date);
        return startDate;
    }

    private String setDateStartEnd(String dateT) {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String ngayKT = "";
        try {
            cal1.setTime(sdf.parse(dateT));
            cal1.set(Calendar.DATE, cal1.getActualMaximum(Calendar.DATE));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            ngayKT = df.format(cal1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ngayKT;
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

    private void setArr() {
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        batDau1 = "01/01/" + year;
        batDau2 = "01/02/" + year;
        batDau3 = "01/03/" + year;
        batDau4 = "01/04/" + year;
        batDau5 = "01/05/" + year;
        batDau6 = "01/06/" + year;
        batDau7 = "01/07/" + year;
        batDau8 = "01/08/" + year;
        batDau9 = "01/09/" + year;
        batDau10 = "01/10/" + year;
        batDau11 = "01/11/" + year;
        batDau12 = "01/12/" + year;

        ketThuc1 = setDateStartEnd(batDau1);
        ketThuc2 = setDateStartEnd(batDau2);
        ketThuc3 = setDateStartEnd(batDau3);
        ketThuc4 = setDateStartEnd(batDau4);
        ketThuc5 = setDateStartEnd(batDau5);
        ketThuc6 = setDateStartEnd(batDau6);
        ketThuc7 = setDateStartEnd(batDau7);
        ketThuc8 = setDateStartEnd(batDau8);
        ketThuc9 = setDateStartEnd(batDau9);
        ketThuc10 = setDateStartEnd(batDau10);
        ketThuc11 = setDateStartEnd(batDau11);
        ketThuc12 = setDateStartEnd(batDau12);

        arr1 = mangArr(batDau1, ketThuc1);
        arr2 = mangArr(batDau2, ketThuc2);
        arr3 = mangArr(batDau3, ketThuc3);
        arr4 = mangArr(batDau4, ketThuc4);
        arr5 = mangArr(batDau5, ketThuc5);
        arr6 = mangArr(batDau6, ketThuc6);
        arr7 = mangArr(batDau7, ketThuc7);
        arr8 = mangArr(batDau8, ketThuc8);
        arr9 = mangArr(batDau9, ketThuc9);
        arr10 = mangArr(batDau10, ketThuc10);
        arr11 = mangArr(batDau11, ketThuc11);
        arr12 = mangArr(batDau12, ketThuc12);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doubles.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
        doublesChiPhi.add(0.0);
    }

    private void checkDate(double tong, String st) {
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).equals(st)) {
                doubles.set(0, tong);
                return;
            }
        }
        for (int i = 0; i < arr2.size(); i++) {
            if (arr2.get(i).equals(st)) {
                doubles.set(1, tong);
                return;
            }
        }
        for (int i = 0; i < arr3.size(); i++) {
            if (arr3.get(i).equals(st)) {
                doubles.set(2, tong);
                return;
            }
        }
        for (int i = 0; i < arr4.size(); i++) {
            if (arr4.get(i).equals(st)) {
                doubles.set(3, tong);
                return;
            }
        }
        for (int i = 0; i < arr5.size(); i++) {
            if (arr5.get(i).equals(st)) {
                doubles.set(4, tong);
                return;
            }
        }
        for (int i = 0; i < arr6.size(); i++) {
            if (arr6.get(i).equals(st)) {
                doubles.set(5, tong);
                return;
            }
        }
        for (int i = 0; i < arr7.size(); i++) {
            if (arr7.get(i).equals(st)) {
                doubles.set(6, tong);
                return;
            }
        }
        for (int i = 0; i < arr8.size(); i++) {
            if (arr8.get(i).equals(st)) {
                doubles.set(7, tong);
                return;
            }
        }
        for (int i = 0; i < arr9.size(); i++) {
            if (arr9.get(i).equals(st)) {
                doubles.set(8, tong);
                return;
            }
        }
        for (int i = 0; i < arr10.size(); i++) {
            if (arr10.get(i).equals(st)) {
                doubles.set(9, tong);
                return;
            }
        }
        for (int i = 0; i < arr11.size(); i++) {
            if (arr11.get(i).equals(st)) {
                doubles.set(10, tong);
                return;
            }
        }
        for (int i = 0; i < arr12.size(); i++) {
            if (arr12.get(i).equals(st)) {
                doubles.set(11, tong);
                return;
            }
        }
    }

    private void checkChiPhi(double tong, String st) {
        for (int i = 0; i < arr1.size(); i++) {
            if (arr1.get(i).equals(st)) {
                doublesChiPhi.set(0, tong);
                return;
            }
        }
        for (int i = 0; i < arr2.size(); i++) {
            if (arr2.get(i).equals(st)) {
                doublesChiPhi.set(1, tong);
                return;
            }
        }
        for (int i = 0; i < arr3.size(); i++) {
            if (arr3.get(i).equals(st)) {
                doublesChiPhi.set(2, tong);
                return;
            }
        }
        for (int i = 0; i < arr4.size(); i++) {
            if (arr4.get(i).equals(st)) {
                doublesChiPhi.set(3, tong);
                return;
            }
        }
        for (int i = 0; i < arr5.size(); i++) {
            if (arr5.get(i).equals(st)) {
                doublesChiPhi.set(4, tong);
                return;
            }
        }
        for (int i = 0; i < arr6.size(); i++) {
            if (arr6.get(i).equals(st)) {
                doublesChiPhi.set(5, tong);
                return;
            }
        }
        for (int i = 0; i < arr7.size(); i++) {
            if (arr7.get(i).equals(st)) {
                doublesChiPhi.set(6, tong);
                return;
            }
        }
        for (int i = 0; i < arr8.size(); i++) {
            if (arr8.get(i).equals(st)) {
                doublesChiPhi.set(7, tong);
                return;
            }
        }
        for (int i = 0; i < arr9.size(); i++) {
            if (arr9.get(i).equals(st)) {
                doublesChiPhi.set(8, tong);
                return;
            }
        }
        for (int i = 0; i < arr10.size(); i++) {
            if (arr10.get(i).equals(st)) {
                doublesChiPhi.set(9, tong);
                return;
            }
        }
        for (int i = 0; i < arr11.size(); i++) {
            if (arr11.get(i).equals(st)) {
                doublesChiPhi.set(10, tong);
                return;
            }
        }
        for (int i = 0; i < arr12.size(); i++) {
            if (arr12.get(i).equals(st)) {
                doublesChiPhi.set(11, tong);
                return;
            }
        }
    }

    @Override
    public void onRefresh() {
        getFirebaseThu();
        getFirebaseBienLai();
        setValue();
        setValues();
        setValuess();
    }
}