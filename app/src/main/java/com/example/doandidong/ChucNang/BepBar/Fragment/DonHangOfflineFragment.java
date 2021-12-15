package com.example.doandidong.ChucNang.BepBar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.BepBar.Adapter.DonHangOfflineAdapter;
import com.example.doandidong.ChucNang.BepBar.Data.Mon;
import com.example.doandidong.ChucNang.BepBar.Data.SanPhamOder;
import java.util.ArrayList;

public class DonHangOfflineFragment extends Fragment {
    private RecyclerView recyclerViewTable;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private DatabaseReference mDatabase;
    private ArrayList<SanPhamOder> tableList;
    private DonHangOfflineAdapter donHangOfflineAdapter;
    private ProgressBar progressBar;
    private TextView lblThongBao;
    private ImageView imageView;

    View v;
    public DonHangOfflineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tableList=new ArrayList<>();
        v= inflater.inflate(R.layout.fragment_danh_sanh_don_hang, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        lblThongBao = v.findViewById(R.id.lblThongBao);
        imageView = v.findViewById(R.id.image);
        thongTinCuaHangSql = new ThongTinCuaHangSql(getContext());
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        progressBar.setVisibility(View.VISIBLE);

        recyclerViewTable= v.findViewById(R.id.recycler);
        donHangOfflineAdapter =new DonHangOfflineAdapter(getActivity(),getContext());
        onDataChange();
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void onDataChange()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("CuaHangOder/"+ID_CUAHANG).child("sanphamorder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    int i=0;
                    tableList=new ArrayList<>();
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        long date = Long.parseLong(postSnapshot.child("date").getValue().toString());
                        int trangThai = Integer.parseInt(postSnapshot.child("trangThai").getValue().toString());
                        String nametable = postSnapshot.getKey();
                        if (trangThai == 0) {
                            ArrayList<Mon> listMon = new ArrayList<>();
                            for (DataSnapshot snap: postSnapshot.child("sanpham").getChildren()) {
                                String nameProduct = snap.child("nameProduct").getValue().toString();
                                String yeuCau = snap.child("yeuCau").getValue().toString();
                                long soLuong = Long.parseLong(snap.child("soluong").getValue().toString());
                                Double giaProudct = Double.parseDouble(snap.child("giaProudct").getValue().toString());
                                String imgProduct = snap.child("imgProduct").getValue().toString();
                                Mon mon = new Mon(nameProduct, yeuCau, soLuong, giaProudct, imgProduct);
                                listMon.add(mon);
                            }
                            SanPhamOder sanPhamOder = new SanPhamOder(nametable, listMon, date, trangThai);
                            tableList.add(sanPhamOder);
                        }
                    }
                }

                progressBar.setVisibility(View.INVISIBLE);
                if (tableList.size() > 0) {
                    imageView.setImageResource(0);
                    lblThongBao.setText("");
                }else {
                    imageView.setImageResource(R.drawable.empty_list);
                    lblThongBao.setText("Chưa có đơn hàng nào");
                }

                donHangOfflineAdapter.setData(tableList);
                recyclerViewTable.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewTable.setAdapter(donHangOfflineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

