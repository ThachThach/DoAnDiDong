package com.example.doandidong.function.BaoCao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.example.doandidong.Adapter.ChiSoSanPhamAdapter;
import com.example.doandidong.Model.PieTongQuan;
import com.example.doandidong.R;
import com.example.doandidong.function.BaoCao.SanPhamBaoCao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChiSoSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChiSoSanPhamFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiSoSanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChiSoSanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChiSoSanPhamFragment newInstance(String param1, String param2) {
        ChiSoSanPhamFragment fragment = new ChiSoSanPhamFragment();
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


    // TODO: Firebase
    private FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    private DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

    //TODO: ID
    private PieChart pieChart, pieChartOnline, pieChartCuaHang;
    private RecyclerView recyclerView;
    private RelativeLayout sanPham;
    private ImageView image;
    private TextView lblThongBao, phanTranOnline, phanTranCuaHang;
    private ProgressBar progressBar;
    private ChiSoSanPhamAdapter chiSoSanPhamAdapter;
    private ArrayList<PieTongQuan> dsSanPham = new ArrayList<>();
    private ArrayList<SanPhamBaoCao> dsSanPhamBienLai = new ArrayList<>();
    private ArrayList<SanPhamBaoCao> dsSanPhamBienLaiOnline = new ArrayList<>();
    private ArrayList<Integer> checkThu = new ArrayList<>();
    private ArrayList<Integer> checkThuOnline = new ArrayList<>();
    private ArrayList<Integer> checkDSSP = new ArrayList<>();
    private ArrayList<String> listDays;
    private View view;
    private String ID_CuaHang = "";
    private Spinner spinner;
    private ArrayList<PieTongQuan> sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chi_so_san_pham, container, false);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(view.getContext());
        ID_CuaHang = thongTinCuaHangSql.IDCuaHang();

        sanPham = view.findViewById(R.id.sanPham);
        phanTranOnline = view.findViewById(R.id.phanTranOnline);
        phanTranCuaHang = view.findViewById(R.id.phanTranCuaHang);
        image = view.findViewById(R.id.image);
        progressBar = view.findViewById(R.id.progressBar);
        lblThongBao = view.findViewById(R.id.lblThongBao);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChartOnline = (PieChart) view.findViewById(R.id.piecharOnline);
        pieChartCuaHang = (PieChart) view.findViewById(R.id.piecharTaiQuan);
        spinner = view.findViewById(R.id.spinner);
        listDays = MangNgay();

        progressBar.setVisibility(View.VISIBLE);
        sanPham.setVisibility(View.GONE);
        getDataBienLai();
        setData();
        return view;
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

                        for (int i = 0; i < sp.size(); i++) {
                            pieChart.addPieSlice(
                                    new PieModel(
                                            sp.get(i).getName(),
                                            sp.get(i).getSoLuong(),
                                            Color.parseColor(sp.get(i).getColor())));
                        }
                        pieChart.startAnimation();

                        String colorCode1 = String.format("#%06x", obj.nextInt(0xffffff + 1));
                        String colorCode2 = String.format("#%06x", obj.nextInt(0xffffff + 1));

                        int online = dsSanPhamBienLaiOnline.size();
                        int cuahang = dsSanPhamBienLai.size();

                        int tong = online + cuahang;

                        int ptOnline;
                        int ptCuaHang;
                        sanPham.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        lblThongBao.setText("");
                        image.setImageResource(0);

                        if (tong == 0) {
                            ptOnline = 0;
                            ptCuaHang = 0;
                            sanPham.setVisibility(View.GONE);
                            image.setImageResource(R.drawable.empty_list);
                            lblThongBao.setText("Chưa có dữ liệu");
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

                        phanTranCuaHang.setText(ptCuaHang + "%");
                        phanTranOnline.setText(ptOnline+"%");

                        pieChartOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        online,
                                        Color.parseColor(colorCode1)));
                        pieChartOnline.addPieSlice(
                                new PieModel(
                                        "online",
                                        cuahang,
                                        Color.parseColor("#FFFFFFFF")));
                        pieChartCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        cuahang,
                                        Color.parseColor(colorCode2)));
                        pieChartCuaHang.addPieSlice(
                                new PieModel(
                                        "Cửa hàng",
                                        online,
                                        Color.parseColor("#FFFFFFFF")));

                        i = 0;
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("ssss", sp.size()+"");
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

    private void displayItem(View view, ArrayList<PieTongQuan> sp) {
        recyclerView = view.findViewById(R.id.recycler_chi_so_san_pham);
        chiSoSanPhamAdapter = new ChiSoSanPhamAdapter(view.getContext(), sp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setAdapter(chiSoSanPhamAdapter);
        chiSoSanPhamAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        spinner.setOnItemSelectedListener(this);
    }

    private void getDataBienLai() {
        dsSanPhamBienLai.clear();
        dsSanPhamBienLaiOnline.clear();
        checkThu.clear();
        checkThuOnline.clear();
        for (String st : listDays) {
            mFirebaseDatabase.child("CuaHangOder/"+ID_CuaHang+"/bienlai/thu/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot snap : snapshot.child("sanpham").getChildren()) {
                                SanPhamBaoCao sanpham = snap.getValue(SanPhamBaoCao.class);
                                dsSanPhamBienLai.add(sanpham);
                            }
                        }

                        checkThu.add(1);
                    } else {
                        checkThu.add(0);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            mFirebaseDatabase.child("CuaHangOder/"+ID_CuaHang+"/donhangonline/donhoanthanh/" + st).addListenerForSingleValueEvent(new ValueEventListener() {
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
        chiSoSanPhamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sort(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}