package com.example.doandidong.function.BaoCao.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import com.example.doandidong.Common.FormatDate;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.ChiSoSanPham2Adapter;
import com.example.doandidong.Model.PieTongQuan;
import com.example.doandidong.Model.SanPhamTop;
import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.SanPhamBaoCao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachTopSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachTopSanPhamFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachTopSanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachTopSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachTopSanPhamFragment newInstance(String param1, String param2) {
        DanhSachTopSanPhamFragment fragment = new DanhSachTopSanPhamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private PieChart piechart, piecharOnline, piecharCuaHang;
    private TextView lblTitle, lblPhanTramOnline, lblPhanTramCuaHang, lblTitleThang, lblnam;
    public TextView lblTatCa1, lblTatCa2;
    private RecyclerView recyclerViewThang, recyclerViewNam;
    private ArrayList<SanPhamTop> list;
    private ChiSoSanPham2Adapter chiSoSanPham2Adapter;
    private LinearLayout lnPie;
    private TextView btnNgay;
    private Dialog dialog;
    private TextView btnTatCa, btnT1, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnT8, btnT9, btnT10, btnT11, btnT12;
    private Button btn_dong, btn_dong_y;
    private int thang = 1;
    private int thangStamp;
    private boolean kiemtraAll = false;
    private int month = 1;
    private DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
    private String ID_CUAHANG;

    private ArrayList<PieTongQuan> dsSanPham = new ArrayList<>();
    private ArrayList<SanPhamBaoCao> dsSanPhamBienLai = new ArrayList<>();
    private ArrayList<SanPhamBaoCao> dsSanPhamBienLaiOnline = new ArrayList<>();
    private ArrayList<Integer> checkThu = new ArrayList<>();
    private ArrayList<Integer> checkThuOnline = new ArrayList<>();
    private ArrayList<String> listDays;
    private ImageView image;
    private TextView lblThongBao;
    private ProgressBar progressBar;
    private View view;
    private LinearLayout layoutPie, layoutRecycleview;
    private Spinner spinner;
    private ArrayList<PieTongQuan> sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danh_sach_top_san_pham, container, false);
        ThongTinCuaHangSql thiThongTinCuaHangSql = new ThongTinCuaHangSql(getContext());
        ID_CUAHANG = thiThongTinCuaHangSql.IDCuaHang();
        anhXaID(view);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        month = cal.get(Calendar.MONTH) + 1;
        thang = month;
        listDays = MangNgay();

        getDataFirebase();
        setData();

        return view;
    }

    private void anhXaID(View view) {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ngay_bao_bao_san_pham);
        btnNgay = view.findViewById(R.id.btnNgay);
        piechart = (PieChart) view.findViewById(R.id.piechart);
        piecharOnline = (PieChart) view.findViewById(R.id.piecharOnline);
        piecharCuaHang = (PieChart) view.findViewById(R.id.piecharCuaHang);
        lblPhanTramOnline = view.findViewById(R.id.lblPhanTramOnline);
        lblPhanTramCuaHang = view.findViewById(R.id.lblPhanTramCuaHang);
        lblTitleThang = view.findViewById(R.id.lblTitleThang);
        recyclerViewThang = view.findViewById(R.id.recylerThang);
        image = view.findViewById(R.id.image);
        progressBar = view.findViewById(R.id.progressBar);
        lblThongBao = view.findViewById(R.id.lblThongBao);
        layoutRecycleview = view.findViewById(R.id.layoutRecycleview);
        layoutPie = view.findViewById(R.id.layoutPie);
        spinner = view.findViewById(R.id.spinner);

        btn_dong = dialog.findViewById(R.id.btn_dong);
        btn_dong_y = dialog.findViewById(R.id.btn_dong_y);
        btnTatCa = dialog.findViewById(R.id.btnTatCa);
        btnT1 = dialog.findViewById(R.id.btnT1);
        btnT2 = dialog.findViewById(R.id.btnT2);
        btnT3 = dialog.findViewById(R.id.btnT3);
        btnT4 = dialog.findViewById(R.id.btnT4);
        btnT5 = dialog.findViewById(R.id.btnT5);
        btnT6 = dialog.findViewById(R.id.btnT6);
        btnT7 = dialog.findViewById(R.id.btnT7);
        btnT8 = dialog.findViewById(R.id.btnT8);
        btnT9 = dialog.findViewById(R.id.btnT9);
        btnT10 = dialog.findViewById(R.id.btnT10);
        btnT11 = dialog.findViewById(R.id.btnT11);
        btnT12 = dialog.findViewById(R.id.btnT12);

        btnNgay.setOnClickListener(this);
        btnTatCa.setOnClickListener(this);
        btnT1.setOnClickListener(this);
        btnT2.setOnClickListener(this);
        btnT3.setOnClickListener(this);
        btnT4.setOnClickListener(this);
        btnT5.setOnClickListener(this);
        btnT6.setOnClickListener(this);
        btnT7.setOnClickListener(this);
        btnT8.setOnClickListener(this);
        btnT9.setOnClickListener(this);
        btnT10.setOnClickListener(this);
        btnT11.setOnClickListener(this);
        btnT12.setOnClickListener(this);
        btn_dong.setOnClickListener(this);
        btn_dong_y.setOnClickListener(this);
    }

    private void displayItem(View view, ArrayList<PieTongQuan> sp) {
        recyclerViewThang = view.findViewById(R.id.recylerThang);
        chiSoSanPham2Adapter = new ChiSoSanPham2Adapter(view.getContext(), sp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewThang.setAdapter(chiSoSanPham2Adapter);
        recyclerViewThang.setLayoutManager(linearLayoutManager);
        chiSoSanPham2Adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);
    }

    int i = 0;
    private void setData() {
        dsSanPham.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int demThu = 0;
                int demThuOnline = 0;
                if (checkThu.size() == listDays.size() && checkThuOnline.size() == listDays.size()) {
                    for (int i = 0; i < 7; i++) {
                        if (checkThu.get(i) == 0) {
                            demThu++;
                        }
                        if (checkThuOnline.get(i) == 0) {
                            demThuOnline++;
                        }
                    }



                    if (demThu == dsSanPhamBienLai.size() && demThuOnline == dsSanPhamBienLaiOnline.size()) {
                        i= 0;
                    }else {
                        sp = new ArrayList<>();
                        Random obj = new Random();


                        ArrayList<String> list = new ArrayList<>();

                        for (int i = 0; i< dsSanPhamBienLai.size(); i ++) {
                            list.add(dsSanPhamBienLai.get(i).getNameProduct());
                        }
                        for (int i = 0; i< dsSanPhamBienLaiOnline.size(); i++){
                            list.add(dsSanPhamBienLaiOnline.get(i).getNameProduct());
                        }

                        int count = list.size();
                        for (int i = 0; i < count; i++){

                            for (int j = i + 1; j < count; j++){
                                if (list.get(i).equals(list.get(j))){
                                    list.remove(j--);
                                    count--;
                                }
                            }
                        }



                        for (int i = 0; i < list.size(); i++){
                            dsSanPham.add(new PieTongQuan(list.get(i), 0));
                        }

                        Log.d("zzzz",dsSanPham.size() + "");

                        for (int i = 0; i < dsSanPham.size(); i++) {
                            for (int j = 0; j < dsSanPhamBienLai.size(); j++) {
                                if (dsSanPham.get(i).getName().equals(dsSanPhamBienLai.get(j).getNameProduct())) {
                                    dsSanPham.get(i).setSoLuong(dsSanPham.get(i).getSoLuong() + dsSanPhamBienLai.get(j).getSoluong());
                                    dsSanPham.get(i).setGia(dsSanPham.get(i).getGia() + dsSanPhamBienLai.get(j).getGiaProudct());
                                }
                            }
                            for (int j = 0; j < dsSanPhamBienLaiOnline.size(); j++) {
                                if (dsSanPham.get(i).getName().equals(dsSanPhamBienLaiOnline.get(j).getNameProduct())) {
                                    dsSanPham.get(i).setSoLuong(dsSanPham.get(i).getSoLuong() + dsSanPhamBienLaiOnline.get(j).getSoluong());
                                    dsSanPham.get(i).setGia(dsSanPham.get(i).getGia() + dsSanPhamBienLaiOnline.get(j).getGiaProudct());
                                }
                            }
                        }

                        for (int i = 0; i < dsSanPham.size(); i++) {
                            if (dsSanPham.get(i).getSoLuong() > 0 || dsSanPham.get(i).getGia() > 0) {
                                sp.add(dsSanPham.get(i));
                            }
                        }

                        sp.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());

                        for (int i = 0; i < sp.size(); i++) {
                            int rand_num = obj.nextInt(0xffffff + 1);
                            String colorCode = String.format("#%06x", rand_num);
                            sp.get(i).setColor(colorCode);
                        }

                        piechart.clearChart();
                        piecharCuaHang.clearChart();
                        piecharOnline.clearChart();

                        for (int i = 0; i < sp.size(); i++) {
                            piechart.addPieSlice(
                                    new PieModel(
                                            sp.get(i).getName(),
                                            sp.get(i).getSoLuong(),
                                            Color.parseColor(sp.get(i).getColor())));
                        }
                        piechart.startAnimation();

                        String colorCode1 = String.format("#%06x", obj.nextInt(0xffffff + 1));
                        String colorCode2 = String.format("#%06x", obj.nextInt(0xffffff + 1));

                        int online = dsSanPhamBienLaiOnline.size();
                        int cuahang = dsSanPhamBienLai.size();

                        int tong = online + cuahang;

                        int ptOnline;
                        int ptCuaHang;
                        progressBar.setVisibility(View.INVISIBLE);
                        lblThongBao.setText("");
                        image.setImageResource(0);
                        layoutPie.setVisibility(View.VISIBLE);
                        layoutRecycleview.setVisibility(View.VISIBLE);

                        if (tong == 0) {
                            ptOnline = 0;
                            ptCuaHang = 0;
                            image.setImageResource(R.drawable.empty_list);
                            lblThongBao.setText("Chưa có dữ liệu");
                            layoutPie.setVisibility(View.GONE);
                            layoutRecycleview.setVisibility(View.GONE);
                        }else if (online == 0 && cuahang != 0) {
                            ptOnline = 0;
                            ptCuaHang = 100;
                        }else if (online != 0 && cuahang == 0){
                            ptOnline = 100;
                            ptCuaHang = 0;
                        }else {
                            ptOnline = (online * 100) / tong;
                            ptCuaHang = (cuahang * 100)/ tong;
                        }

                        lblPhanTramCuaHang.setText(ptCuaHang + "%");
                        lblPhanTramOnline.setText(ptOnline+"%");

                        piecharOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        online,
                                        Color.parseColor(colorCode1)));
                        piecharOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        cuahang,
                                        Color.parseColor("#FFFFFFFF")));
                        piecharCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        cuahang,
                                        Color.parseColor(colorCode2)));
                        piecharCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        online,
                                        Color.parseColor("#FFFFFFFF")));

                        i = 0;
                        progressBar.setVisibility(View.INVISIBLE);
                        displayItem(view, sp);
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_product, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }

                }else {
                    i++;
                    if (i <= 500) {
                        setData();
                    } else {
                        i = 0;
                    }
                }

            }
        }, 50);
    }

    private void getDataFirebase() {
        dsSanPhamBienLai.clear();
        dsSanPhamBienLaiOnline.clear();
        checkThu.clear();
        checkThuOnline.clear();
        for (String st : listDays) {
            mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG + "/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            for (DataSnapshot s : snap.child("sanpham").getChildren()) {
                                SanPhamBaoCao sanpham = s.getValue(SanPhamBaoCao.class);
                                dsSanPhamBienLai.add(sanpham);
                            }
                        }
                        checkThu.add(1);
                    } else {
                        checkThu.add(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG + "/donhangonline/donhoanthanh/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot snap : snapshot.child("sanpham").getChildren()) {
                                String nameProduct = snap.child("nameProduct").getValue().toString();
                                int soluong = Integer.parseInt(snap.child("soluong").getValue().toString());
                                Double giaProudct = Double.parseDouble(snap.child("giaBan").getValue().toString()) * soluong;
                                String image = snap.child("imgProduct").toString();

                                SanPhamBaoCao sanpham = new SanPhamBaoCao();
                                sanpham.setGiaProudct(giaProudct);
                                sanpham.setLoai("");
                                sanpham.setNameProduct(nameProduct);
                                sanpham.setImgProduct(image);
                                sanpham.setYeuCau("");
                                sanpham.setSoluong(soluong);
                                dsSanPhamBienLaiOnline.add(sanpham);
                            }
                        }
                        checkThuOnline.add(1);
                    } else {
                        checkThuOnline.add(0);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private ArrayList<String> MangNgay() {
        String ngayBD = null;
        String ngayKT = null;
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int ngayT = instance.get(Calendar.DATE);

        if (kiemtraAll == false) {
            if (thang == 1) {
                ngayBD = "01/01/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 2) {
                ngayBD = "01/01/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 3) {
                ngayBD = "01/03/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 4) {
                ngayBD = "01/04/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 5) {
                ngayBD = "01/05/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 6) {
                ngayBD = "01/06/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 7) {
                ngayBD = "01/07/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 8) {
                ngayBD = "01/08/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 9) {
                ngayBD = "01/09/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 10) {
                ngayBD = "01/10/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 11) {
                ngayBD = "01/11/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            } else if (thang == 12) {
                ngayBD = "01/12/" + year;
                ngayKT = setDateStartEnd(ngayBD);
            }
            btnNgay.setText("Tháng "+ thang);
            lblTitleThang.setText("Tháng "+ thang);
        } else {
            ngayBD = "01/01/" + year;
            ngayKT = ngayT + "/" + month + "/" + year;
            btnNgay.setText("Năm "+ year);
            lblTitleThang.setText("Năm "+ year);
        }


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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNgay:
                openFeedbackDialog(Gravity.CENTER);
                break;
            case R.id.btnTatCa:
                if (kiemtraAll == false) {
                    kiemtraAll = true;
                    thangStamp = 0;
                } else {
                    kiemtraAll = false;
                    thangStamp = month;
                }
                setBackGroundClick();
                break;
            case R.id.btnT1:
                thangStamp = 1;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT2:
                thangStamp = 2;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT3:
                thangStamp = 3;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT4:
                thangStamp = 4;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT5:
                thangStamp = 5;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT6:
                thangStamp = 6;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT7:
                thangStamp = 7;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT8:
                thangStamp = 8;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT9:
                thangStamp = 9;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT10:
                thangStamp = 10;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT11:
                thangStamp = 11;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btnT12:
                thangStamp = 12;
                kiemtraAll = false;
                setBackGroundClick();
                break;
            case R.id.btn_dong:
                dialog.dismiss();
                break;
            case R.id.btn_dong_y:
                thang = thangStamp;
                listDays = MangNgay();
                getDataFirebase();
                setData();
                dialog.dismiss();
                break;
        }
    }

    private void openFeedbackDialog(int gravity) {
        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        setBackGroundFirst();


        dialog.show();
    }

    private void setBackGroundFirst() {
        switch (thang) {
            case 0:
                btnT1.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT2.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT3.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT4.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT5.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT6.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT7.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT8.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT9.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT10.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT11.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT12.setBackgroundResource(R.drawable.layout_radius50_red);
                break;
            case 1:
                btnT1.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);

                break;
            case 2:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 3:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 4:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 5:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 6:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 7:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 8:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 9:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 10:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 11:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 12:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50_red);
                break;
        }
    }

    private void setBackGroundClick() {
        switch (thangStamp) {
            case 0:
                btnT1.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT2.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT3.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT4.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT5.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT6.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT7.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT8.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT9.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT10.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT11.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT12.setBackgroundResource(R.drawable.layout_radius50_red);
                break;
            case 1:
                btnT1.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);

                break;
            case 2:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 3:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 4:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 5:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 6:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 7:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 8:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 9:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 10:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 11:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50_red);
                btnT12.setBackgroundResource(R.drawable.layout_radius50);
                break;
            case 12:
                btnT1.setBackgroundResource(R.drawable.layout_radius50);
                btnT2.setBackgroundResource(R.drawable.layout_radius50);
                btnT3.setBackgroundResource(R.drawable.layout_radius50);
                btnT4.setBackgroundResource(R.drawable.layout_radius50);
                btnT5.setBackgroundResource(R.drawable.layout_radius50);
                btnT6.setBackgroundResource(R.drawable.layout_radius50);
                btnT7.setBackgroundResource(R.drawable.layout_radius50);
                btnT8.setBackgroundResource(R.drawable.layout_radius50);
                btnT9.setBackgroundResource(R.drawable.layout_radius50);
                btnT10.setBackgroundResource(R.drawable.layout_radius50);
                btnT11.setBackgroundResource(R.drawable.layout_radius50);
                btnT12.setBackgroundResource(R.drawable.layout_radius50_red);
                break;
        }
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

    private void sort(int position) {
        displayItem(view, sp);
        if (position == 0) {
            sp.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());
        }else if (position == 1){
            sp.sort((o1, o2) -> o1.getSoLuong() - o2.getSoLuong());
        }else if (position == 2){
            sp.sort((o1, o2) -> Double.compare(o2.getGia(), o1.getGia()));
        }else if (position == 3){
            sp.sort((o1, o2) -> Double.compare(o1.getGia(), o2.getGia()));
        }
        chiSoSanPham2Adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        sort(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}