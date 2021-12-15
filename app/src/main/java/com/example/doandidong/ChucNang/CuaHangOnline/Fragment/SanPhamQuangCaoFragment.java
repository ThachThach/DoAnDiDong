package com.example.doandidong.ChucNang.CuaHangOnline.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.CuaHangOnline.Adapter.SanPhamChoXacNhanAdapter;
import com.example.doandidong.ChucNang.CuaHangOnline.Adapter.SanPhamQuangCaoAdapter;
import com.example.doandidong.ChucNang.CuaHangOnline.Data.Product;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SanPhamQuangCaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SanPhamQuangCaoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SanPhamQuangCaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChoXacNhanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SanPhamQuangCaoFragment newInstance(String param1, String param2) {
        SanPhamQuangCaoFragment fragment = new SanPhamQuangCaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recycleview, recycleviewChoXacNhan;
    private SanPhamQuangCaoAdapter sanPhamQuangCaoAdapter;
    private SanPhamChoXacNhanAdapter sanPhamChoXacNhanAdapter;
    private ArrayList<Product> listQuanCao = new ArrayList<>();
    private ArrayList<Product> listChoXacNhan = new ArrayList<>();
    private ProgressBar progressBarLayout;
    private LinearLayout.LayoutParams params, params2;
    private LinearLayout danhsach, btnMatHangQuangCao, btnChoXacNhan, dsChoXacNhan;
    private boolean setL = true;
    private boolean setC = true;
    private ImageView quangCaosanPhamIMG, choXacNhanIMG;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_san_pham_quang_cao, container, false);
        IDLayout(view);
        thongTinCuaHangSql = new ThongTinCuaHangSql(view.getContext());
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        progressBarLayout.setVisibility(View.VISIBLE);

        getData();
        displayRecycleView();

        return view;
    }

    private void IDLayout(View view) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        recycleview = view.findViewById(R.id.recycleview);
        recycleviewChoXacNhan = view.findViewById(R.id.recycleviewChoXacNhan);
        progressBarLayout = view.findViewById(R.id.progressBarLayout);
        danhsach = view.findViewById(R.id.danhsach);
        dsChoXacNhan = view.findViewById(R.id.dsChoXacNhan);
        choXacNhanIMG = view.findViewById(R.id.choXacNhanIMG);
        btnMatHangQuangCao = view.findViewById(R.id.btnMatHangQuangCao);
        btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        quangCaosanPhamIMG = view.findViewById(R.id.quangCaosanPhamIMG);
        scrollView = view.findViewById(R.id.scrollView4);
        progressBarLayout.setVisibility(View.GONE);

        params = (LinearLayout.LayoutParams) danhsach.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) dsChoXacNhan.getLayoutParams();


        btnMatHangQuangCao.setOnClickListener(this);
        btnChoXacNhan.setOnClickListener(this);
    }

    private void displayRecycleView() {
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        sanPhamQuangCaoAdapter = new SanPhamQuangCaoAdapter(getContext(), listQuanCao, this);
        recycleview.setAdapter(sanPhamQuangCaoAdapter);
        sanPhamQuangCaoAdapter.notifyDataSetChanged();

        recycleviewChoXacNhan.setHasFixedSize(true);
        recycleviewChoXacNhan.setLayoutManager(new GridLayoutManager(getContext(), 1));
        sanPhamChoXacNhanAdapter = new SanPhamChoXacNhanAdapter(getContext(), listChoXacNhan, this);
        recycleviewChoXacNhan.setAdapter(sanPhamChoXacNhanAdapter);
        sanPhamChoXacNhanAdapter.notifyDataSetChanged();
    }

    private void getData() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.getValue() != null) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listQuanCao.add(product);
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                    sanPhamQuangCaoAdapter.notifyDataSetChanged();
                } else {
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (int i = 0; i < listQuanCao.size(); i++) {
                    if (listQuanCao.get(i).getId().equals(key)) {
                        listQuanCao.remove(i);
                        sanPhamQuangCaoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {
            }
        };

        ChildEventListener childEventListenerChoXacNhan = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.getValue() != null) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listChoXacNhan.add(product);
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                    sanPhamChoXacNhanAdapter.notifyDataSetChanged();
                } else {
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (int i = 0; i < listChoXacNhan.size(); i++) {
                    if (listChoXacNhan.get(i).getId().equals(key)) {
                        listChoXacNhan.remove(i);
                        sanPhamChoXacNhanAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sanPhamQuangCao").child(ID_CUAHANG).child("sanpham");
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("ChoXacNhan");
        databaseReference.addChildEventListener(childEventListener);
        databaseReference2.addChildEventListener(childEventListenerChoXacNhan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMatHangQuangCao:
                if (setL) {
                    params.height = 0;
                    setL = false;
                    quangCaosanPhamIMG.setImageResource(R.drawable.down_24);
                    danhsach.setLayoutParams(params);
                } else {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL = true;
                    quangCaosanPhamIMG.setImageResource(R.drawable.up_24);
                    danhsach.setLayoutParams(params);
                }
                break;
            case R.id.btnChoXacNhan:
                if (setC) {
                    params2.height = 0;
                    setC = false;
                    choXacNhanIMG.setImageResource(R.drawable.down_24);
                    dsChoXacNhan.setLayoutParams(params2);
                } else {
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setC = true;
                    choXacNhanIMG.setImageResource(R.drawable.up_24);
                    dsChoXacNhan.setLayoutParams(params2);
                }
        }
    }

    public void delete(final int position) {
        new AlertDialog.Builder(getContext(), R.style.AlertDialog).setMessage(
                "Bạn có chắc chắn hủy đơn quảng cáo này?"
        ).setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sanPhamQuangCaoAdapter.notifyDataSetChanged();
                FirebaseDatabase.getInstance().getReference().child("sanPhamQuangCao/" + ID_CUAHANG + "/sanpham/" + listQuanCao
                        .get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        new SupportSaveLichSu(getContext(), "Hủy quảng cáo sản phẩm: " + listQuanCao.get(position).getNameProduct());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("không", null)
                .show();
    }

    public void delete2(final int position) {
        new AlertDialog.Builder(getContext(), R.style.AlertDialog).setMessage(
                "Bạn có chắc chắn hủy đơn quảng cáo này?"
        ).setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sanPhamChoXacNhanAdapter.notifyDataSetChanged();
                FirebaseDatabase.getInstance().getReference().child("ChoXacNhan/" +listChoXacNhan
                        .get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        sanPhamChoXacNhanAdapter.notifyDataSetChanged();
                        new SupportSaveLichSu(getContext(), "Hủy quảng cáo sản phẩm: " + listQuanCao.get(position).getNameProduct());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("không", null)
                .show();
    }
}