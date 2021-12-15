package com.example.doandidong.ChucNang.DonHangOnline.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.SupportFragmentDonOnline;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.DonHangOnline.adapter.DangXuLiAdapter;
import com.example.doandidong.ChucNang.DonHangOnline.data.DonHang;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangXuLyOnlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangXuLyOnlineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangXuLyOnlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangXuLyOnlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangXuLyOnlineFragment newInstance(String param1, String param2) {
        DangXuLyOnlineFragment fragment = new DangXuLyOnlineFragment();
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

    private RecyclerView recyclerView;
    private DangXuLiAdapter dangXuLy;
    private ArrayList<DonHang> donHangs;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private SupportFragmentDonOnline support = new SupportFragmentDonOnline();
    private TextView lblThongBao;
    private ProgressBar progressBar;
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private ImageView image;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dang_xu_ly_online, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        lblThongBao = view.findViewById(R.id.lblThongBao);
        progressBar = view.findViewById(R.id.progressBar);
        refreshLayout = view.findViewById(R.id.swipeRefreshlayout);
        image = view.findViewById(R.id.image);
        thongTinCuaHangSql = new ThongTinCuaHangSql(getContext());
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        progressBar.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshListener(this);
        getDataFireBase(view);
        return view;
    }

    private void displayItem(View view){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        dangXuLy = new DangXuLiAdapter(view.getContext(), donHangs);
        recyclerView.setAdapter(dangXuLy);

        dangXuLy.notifyDataSetChanged();
    }

    private void getDataFireBase(View view) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("CuaHangOder/"+ID_CUAHANG+"/donhangonline/dondadat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                donHangs = new ArrayList<>();
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String ngay = postSnapshot.getKey();
                    for (DataSnapshot snap : postSnapshot.getChildren()) {
                        DonHang donHang = snap.getValue(DonHang.class);
                        if (donHang.getTrangthai() == 3 || donHang.getTrangthai() == 4) {
                            String key = snap.getKey();
                            donHangs.add(donHang);
                            Date date = support.dateKey(ngay);
                            donHangs.get(i).setIdDonHang(key);
                            donHangs.get(i).setDate(date);
                            i++;
                        }
                    }
                }
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
                if (donHangs.size() > 0) {
                    lblThongBao.setText("");
                    image.setImageResource(0);
                }else {
                    lblThongBao.setText("Không có đơn hàng đang xử lí");
                    image.setImageResource(R.drawable.empty_list);
                }
                support.SapXepDate(donHangs);
                displayItem(view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onRefresh() {
        getDataFireBase(view);
    }
}