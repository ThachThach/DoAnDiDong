package com.example.doandidong.ChucNang.BaoCao;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.QuanLyKhoAdapter;
import com.example.doandidong.Data.BaoCaoKho;
import com.example.doandidong.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BaoCaoKhoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuanLyKhoAdapter quanLyKhoAdapter;
    private List<BaoCaoKho> baoCaoKhos;
    private Button back;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_kho);
        displayItem();
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);

        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
    }

    private void displayItem() {
        recyclerView = findViewById(R.id.recycler_bao_cao_kho);

        baoCaoKhos = new ArrayList<>();

        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        quanLyKhoAdapter = new QuanLyKhoAdapter(this, baoCaoKhos);
        recyclerView.setAdapter(quanLyKhoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        getData();
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFirebaseDatabase= FirebaseDatabase.getInstance().getReference();
                mFirebaseDatabase.child("BaoCaoKho").child(ID_CUAHANG).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            BaoCaoKho baoCaoKho=snapshot1.getValue(BaoCaoKho.class);
                            baoCaoKhos.add(baoCaoKho);

                        }
                        quanLyKhoAdapter.isShimmer = false;
                        sapXepList();
                        quanLyKhoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, 5000);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaoCaoKhoActivity.this);
            builder.setTitle("Dữ liệu xóa sẽ không thể khôi phục");
            builder.setMessage("Bạn có muốn xóa không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = viewHolder.getAdapterPosition();
                    baoCaoKhos.remove(position);
                    quanLyKhoAdapter.notifyItemRemoved(position);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quanLyKhoAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            builder.show();
        }
    };

    private void sapXepList() {
        Collections.sort(baoCaoKhos, new Comparator<BaoCaoKho>() {
            @Override
            public int compare(BaoCaoKho o1, BaoCaoKho o2) {
                Date dt1 = chuyenStringThanhDate(o1.getNgay(), o1.getGio());
                Date dt2 = chuyenStringThanhDate(o2.getNgay(), o2.getGio());
                if (dt1.after(dt2)) {
                    return -1;
                } else if (dt1.equals(dt2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    private Date chuyenStringThanhDate(String ngay, String gio) {
        String date_s = ngay + " " + gio;
        DateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date date;
        try {
            date = dt.parse(date_s);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}