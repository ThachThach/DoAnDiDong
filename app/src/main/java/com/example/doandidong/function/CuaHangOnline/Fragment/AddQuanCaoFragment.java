package com.example.doandidong.function.CuaHangOnline.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.ChucNangThanhToan.DonGia;
import com.example.doandidong.Model.SanPham.DonViTinh;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Adapter.AdapterDonGia;
import com.example.doandidong.function.CuaHangOnline.Adapter.DanhSachSanPhamCoSanAdapter;
import com.example.doandidong.function.CuaHangOnline.Data.Product;
import com.example.doandidong.function.CustomDialogClass;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddQuanCaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddQuanCaoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddQuanCaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddQuanCaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddQuanCaoFragment newInstance(String param1, String param2) {
        AddQuanCaoFragment fragment = new AddQuanCaoFragment();
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


    private LinearLayout btnQuangCaoSanPham, quangCaoSanPham, layout_image1, addImage, layout_image;
    private ImageView quangCaosanPhamIMG, imgageView;
    private TextView chonSanPham, btnTaoDonMoi, lblLoai, btnHuy, taoDon, addDonViTinh, addLoai;
    private AutoCompleteTextView nhomsanpham;
    private TextInputEditText tenSanPham, soLuong, giamgia, mota, title;

    private boolean setL1 = true;
    private Uri imageUri;
    private LinearLayout.LayoutParams params1, paramsImage, paramsImage1;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mDatabase, mDatabase3;
    private FirebaseDatabase firebaseDatabase;

    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String ID_CUAHANG;
    private ProgressBar progressBar;
    private String nhomSp = "";
    private ArrayAdapter<String> adapterNhomSp;
    private ArrayList<String> listNhom = new ArrayList<>();
    private ProgressBar progressBarLayout;
    private ScrollView scrollView;
    private RecyclerView listView;

    private Window window, window1, window2;
    private Dialog dialog, dialog1, dialog3;
    private EditText textGiaSanPham,textTenDonViTinh;
    private Spinner spnDonViTinh;
    private Button btnDialogHuyDVT, btnDialogThemDVT, btnDialogHuyThemDVT, btnThemDialogThemDVT;
    private ArrayAdapter<String> adapter;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_DONVITINH = "donvitinh";
    private ArrayList<String> listDonViTinh = new ArrayList<>();
    private ArrayList<DonGia> listDonGia = new ArrayList<>();
    private AdapterDonGia adapterDonGia;
    private String id;
    private DonViTinh donViTinh;
    private Product product;
    private AutoCompleteTextView spnTenDonViTinh2;
    private String nhom = "";
    private boolean loai = true;
    private String tenHinh;
    private String nameCuaHang;
    private String soDT;


    //dialog
    private RecyclerView recycleview;
    private TextView dong, btnChonSanPham;

    private DatabaseReference mDatabase2;
    private CheckBox cbSuperQC;
    private String name;
    private int sLuong = 0;
    private double giamGia = 0;
    private String moTa;
    private String nameImage;

    private ArrayList<com.example.doandidong.Model.Product> listProduct;

    private DanhSachSanPhamCoSanAdapter danhSachSanPhamCoSanAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_quan_cao, container, false);
        thongTinCuaHangSql = new ThongTinCuaHangSql(view.getContext());
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        IDLayout(view);

        firebaseDatabase =  FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/"+ID_CUAHANG).child("sanpham");
        mDatabase3 = firebaseDatabase.getReference();

        paramsImage.height = 0;
        layout_image.setLayoutParams(paramsImage);
        paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layout_image1.setLayoutParams(paramsImage1);

        progressBarLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailongdonvitinh);
        window = dialog.getWindow();

        dialog3 = new Dialog(getContext());
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(R.layout.dialog_danh_sach_san_pham);
        window2 = dialog3.getWindow();

        dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemdonvitinh);
        window1 = dialog1.getWindow();
        textTenDonViTinh = dialog1.findViewById(R.id.edtTenDonViTinh);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);

        textGiaSanPham = dialog.findViewById(R.id.tedtGiaDonVi);
        spnDonViTinh = dialog.findViewById(R.id.spnTenDonViTinh);
        spnTenDonViTinh2 = dialog.findViewById(R.id.spnTenDonViTinh2);
        btnDialogHuyDVT = dialog.findViewById(R.id.btnhuyDiaLogDVT);
        btnDialogThemDVT = dialog.findViewById(R.id.btnthemDiaLogDVT);

        adapterDonGia = new AdapterDonGia(getContext(),listDonGia,dialog,window,adapter,Gravity.CENTER, spnTenDonViTinh2, listDonViTinh);
        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listView.setAdapter(adapterDonGia);


        recycleview = dialog3.findViewById(R.id.recycleview);
        dong = dialog3.findViewById(R.id.dong);

//        btnChonSanPham.setVisibility(View.GONE);

        getNhomSanPham();
        Danhsachsanpham();
        getName();

        return view;
    }

    private void IDLayout(View view) {
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_DONVITINH);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        quangCaoSanPham = view.findViewById(R.id.quangCaoSanPham);
        btnQuangCaoSanPham = view.findViewById(R.id.btnQuangCaoSanPham);
        quangCaosanPhamIMG = view.findViewById(R.id.quangCaosanPhamIMG);
        addImage = view.findViewById(R.id.addImage);
        imgageView = view.findViewById(R.id.imgageView);
        taoDon = view.findViewById(R.id.taoDon);
        tenSanPham = view.findViewById(R.id.tenSanPham);
        soLuong = view.findViewById(R.id.soLuong);
        giamgia = view.findViewById(R.id.giamgia);
        mota = view.findViewById(R.id.mota);
        progressBar = view.findViewById(R.id.progressBar);
        layout_image = view.findViewById(R.id.layout_image);
        layout_image1 = view.findViewById(R.id.layout_image1);
        nhomsanpham = view.findViewById(R.id.nhomsanpham);
        progressBarLayout = view.findViewById(R.id.progressBarLayout);
        scrollView = view.findViewById(R.id.scrollView4);
        addDonViTinh = view.findViewById(R.id.addDonViTinh);
        addLoai = view.findViewById(R.id.addLoai);
        listView = view.findViewById(R.id.donViTinh);
        title = view.findViewById(R.id.title);
        btnChonSanPham = view.findViewById(R.id.btnChonSanPham);
        cbSuperQC=view.findViewById(R.id.cbSuperQC);
        paramsImage = (LinearLayout.LayoutParams) layout_image.getLayoutParams();
        paramsImage1 = (LinearLayout.LayoutParams) layout_image1.getLayoutParams();

        params1 = (LinearLayout.LayoutParams) quangCaoSanPham.getLayoutParams();

        btnQuangCaoSanPham.setOnClickListener(this);
        addImage.setOnClickListener(this);
        taoDon.setOnClickListener(this);
        addDonViTinh.setOnClickListener(this);
        addLoai.setOnClickListener(this);
        btnChonSanPham.setOnClickListener(this);

        taoDon.setEnabled(true);

        adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);
        nhomsanpham.setAdapter(adapterNhomSp);
        nhomsanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);

                nhomSp = parent.getItemAtPosition(position).toString();
                nhomsanpham.setText(nhomSp);
                nhomsanpham.setAdapter(adapterNhomSp);
            }
        });
        adapterNhomSp.notifyDataSetChanged();
    }

    private void getNhomSanPham() {
        mFirebaseDatabase.child("nhomSanPham").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        listNhom.add(snap.getValue().toString());
                    }

                    adapterNhomSp = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listNhom);
                    nhomsanpham.setAdapter(adapterNhomSp);
                    adapterNhomSp.notifyDataSetChanged();
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                } else {
                    progressBarLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        //sanphamQuangcao
        switch (v.getId()) {
            case R.id.btnQuangCaoSanPham:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    quangCaosanPhamIMG.setImageResource(R.drawable.down_24);
                    quangCaoSanPham.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    quangCaosanPhamIMG.setImageResource(R.drawable.up_24);
                    quangCaoSanPham.setLayoutParams(params1);
                }
                break;
            case R.id.addImage:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;

            case R.id.taoDon:
//                CustomDialogClass dialogClass =new CustomDialogClass(getActivity());
//                Toast.makeText(getContext(),cbSuperQC.isChecked()+" abc",Toast.LENGTH_SHORT).show();

                if(cbSuperQC.isChecked())
                {

                    if (imageUri != null && loai == true) {
                        uploadFirebaseQuangCao(imageUri,true);
                    } else if (loai == false) {
                        uploadFirebaseQuangCao(imageUri,true);
                    } else {
                        Toast.makeText(v.getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    }


                }
                else {

                    if (imageUri != null && loai == true) {
                        uploadFirebaseQuangCao(imageUri,false);
                    } else if (loai == false) {
                        uploadFirebaseQuangCao(imageUri,false);
                    }
                    else {
                        Toast.makeText(v.getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    }

                }

//                if (imageUri != null && loai == true) {
//                    uploadFirebaseQuangCao(imageUri);
//                } else if (loai == false) {
//                    uploadFirebaseQuangCao(imageUri);
//                } else {
//                    Toast.makeText(v.getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.addLoai:
                donViTinh(Gravity.CENTER);
                break;
            case R.id.addDonViTinh:
                themDonViTinh(Gravity.CENTER);
                break;
            case R.id.btnChonSanPham:
                SanPhamCoSan(Gravity.CENTER);
                break;
        }
    }

    private void getName() {
        Log.d("SSS", ID_CUAHANG);
        mDatabase3.child("cuaHang").child(ID_CUAHANG).child("thongtin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    if (snapshot.child("name").getValue() != null) {
                        nameCuaHang = snapshot.child("name").getValue().toString();
                        soDT = snapshot.child("soDienThoai").getValue().toString();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void themDonViTinh(int gravity) {
        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        windownAttributes.gravity = gravity;
        window1.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog1.setCancelable(true);
        }
        else {
            dialog1.setCancelable(false);
        }

        btnDialogHuyThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnThemDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textTenDonViTinh.getText().toString();
                String id = mDatabase2.push().getKey();
                donViTinh = new DonViTinh(name,id);
                mDatabase2.child(id).setValue(donViTinh);
                textTenDonViTinh.setText("");
                dialog1.dismiss();
            }
        });
        dialog1.show();

    }

    private void donViTinh(int gravity){
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }

        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDonViTinh = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DonViTinh donViTinh1 = snapshot1.getValue(DonViTinh.class);
                    String name = donViTinh1.getDonViTinh();
                    listDonViTinh.add(name);
                    adapterDonGia.setData2(listDonViTinh);
                }
                if (listDonViTinh != null && listDonViTinh.size() > 0) {
                    nhom = listDonViTinh.get(0);
                    adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listDonViTinh);
                    spnTenDonViTinh2.setText(nhom);
                    spnTenDonViTinh2.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spnTenDonViTinh2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nhom = parent.getItemAtPosition(position).toString();
                spnTenDonViTinh2.setText(nhom);
                adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner1_setup_store, listDonViTinh);
                spnTenDonViTinh2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textGiaSanPham.setText("");
                dialog.dismiss();
            }
        });
        btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonGia donGia = new DonGia();
                donGia.setId(id);
                if (textGiaSanPham.getText().toString().isEmpty()){
                    textGiaSanPham.setError("Hãy nhập giá !!!");
                    textGiaSanPham.requestFocus();
                    getActivity().setFinishOnTouchOutside(true);
                }
                else {
                    donGia.setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                    donGia.setTenDonGia(nhom);
                    listDonGia.add(donGia);
                    dialog.dismiss();
                }

                textGiaSanPham.setText("");
                listView.setAdapter(adapterDonGia);
                adapterDonGia.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    private void uploadFirebaseQuangCao(Uri uri,boolean superQuangcao) {
        if (loai) {
            nameImage = System.currentTimeMillis() + "." + getFileExtension(uri);
        }

        name = tenSanPham.getText().toString();
        String sLuongText = soLuong.getText().toString();
        String sGiamGiaText = giamgia.getText().toString();
        moTa = mota.getText().toString();
        String titleText = title.getText().toString();

        if (!sLuongText.isEmpty()) {
            sLuong = Integer.parseInt(sLuongText);
        }
        if (!sGiamGiaText.isEmpty()) {
            giamGia = Double.parseDouble(sGiamGiaText);
        }

        if (nameCuaHang == null) {
            Toast.makeText(getContext(), "Chưa thiết lập thông tin cửa hàng", Toast.LENGTH_SHORT).show();
        }else if (name.isEmpty()) {
            tenSanPham.setError("Nhập tên sản phẩm");
            tenSanPham.requestFocus();
        } else if (sLuongText.isEmpty()) {
            soLuong.setError("Nhập số lượng");
            soLuong.requestFocus();
        } else if (!sLuongText.isEmpty() && !isNumeric(sLuongText)) {
            soLuong.setError("Nhập đúng định dạng");
            soLuong.requestFocus();
        } else if (sGiamGiaText.isEmpty()) {
            giamgia.setError("Nhập giảm giá");
            giamgia.requestFocus();
        } else if (!sGiamGiaText.isEmpty() && !isNumeric(sGiamGiaText)) {
            giamgia.setError("Nhập đúng định dạng");
            giamgia.requestFocus();
        } else if (nhomSp.isEmpty()) {
            nhomsanpham.setError("Chọn nhóm sản phẩm");
            nhomsanpham.requestFocus();
        } else if (sLuong < 1) {
            soLuong.setError("Số lượng phải lớn hơn 1");
            soLuong.requestFocus();
        } else if (moTa.isEmpty()) {
            mota.setError("Mô tả sản phẩm");
            mota.requestFocus();
        }else if (titleText.isEmpty()) {
            mota.setError("Mô tả sản phẩm");
            mota.requestFocus();
        } else if (listDonGia.size()<=0) {
            Toast.makeText(getContext(), "Chưa tạo đơn giá", Toast.LENGTH_SHORT).show();
        } else{
            taoDon.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            if (loai) {
                final StorageReference fileRef = reference.child(nameImage);
                fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String key = mFirebaseDatabase.push().getKey();
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

                                paramsImage.height = 0;
                                layout_image.setLayoutParams(paramsImage);
                                paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                layout_image1.setLayoutParams(paramsImage1);
                                taoDon.setEnabled(true);
                                String status = "Còn";
                                String img = uri.toString();
                                if(superQuangcao)
                                {
                                    product = new Product(key,name,moTa,nhomSp,0.0, sLuong, img, nameImage, giamGia, status, listDonGia, ID_CUAHANG, false, titleText, true);
                                    product.setName(nameCuaHang);
                                    product.setSoDienThoai(soDT);
                                    getThongtinChuyenkhoan("50.000 VND",product,key,true);
                                }else {
                                    product = new Product(key,name,moTa,nhomSp,0.0, sLuong, img, nameImage, giamGia, status, listDonGia, ID_CUAHANG, false, titleText, false);
                                    product.setName(nameCuaHang);
                                    product.setSoDienThoai(soDT);
                                    getThongtinChuyenkhoan("100.000 VND",product,key,false);
                                }


//                                DatabaseReference mFirebaseDatabase1 = mFirebaseInstance.getReference();
//                                DatabaseReference mFirebaseDatabase2 = mFirebaseInstance.getReference();
//                                mFirebaseDatabase1.child("ChoXacNhan/" + key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
//                                        mFirebaseDatabase2.child("cuaHang/"+ID_CUAHANG).child("sanpham").child(product.getNhomsanpham()).child(key).setValue(product);
//                                        name = null;
//                                        sLuong = 0;
//                                        giamGia = 0;
//                                        moTa = null;
//                                        nameImage = null;
//                                        tenSanPham.setText(null);
//                                        soLuong.setText(null);
//                                        giamgia.setText(null);
//                                        imgageView.setImageURI(null);
//                                        nhomsanpham.setText(null);
//                                        title.setText(null);
//                                        addImage.setBackgroundResource(R.drawable.border_image_dashed);
//                                        mota.setText(null);
//                                        imageUri = null;
//                                        listDonGia.clear();
//                                        adapterDonGia.notifyDataSetChanged();
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        new SupportSaveLichSu(getContext(), "Thêm sản phẩm quảng cáo: " + product.getNameProduct());
//
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }else {
                String key = mFirebaseDatabase.push().getKey();
                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

                paramsImage.height = 0;
                layout_image.setLayoutParams(paramsImage);
                paramsImage1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layout_image1.setLayoutParams(paramsImage1);
                taoDon.setEnabled(true);
                String status = "Còn";

                if(superQuangcao)
                {
                    product = new Product(key,name,moTa,nhomSp,0.0, sLuong, tenHinh, "", giamGia, status, listDonGia, ID_CUAHANG, false, titleText, true);
                    product.setName(nameCuaHang);
                    product.setSoDienThoai(soDT);
                    getThongtinChuyenkhoan("100.000 VND",product,key,true);
                }else {
                    product = new Product(key,name,moTa,nhomSp,0.0, sLuong, tenHinh, "", giamGia, status, listDonGia, ID_CUAHANG, false, titleText, false);
                    product.setName(nameCuaHang);
                    product.setSoDienThoai(soDT);
                    getThongtinChuyenkhoan("50.000 VND",product,key,false);
                }


//                DatabaseReference mFirebaseDatabase1 = mFirebaseInstance.getReference();
//                DatabaseReference mFirebaseDatabase2 = mFirebaseInstance.getReference();
//                mFirebaseDatabase1.child("ChoXacNhan/" + key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
//                        mFirebaseDatabase2.child("cuaHang/"+ID_CUAHANG).child("sanpham").child(product.getNhomsanpham()).child(key).setValue(product);
//                        name = null;
//                        sLuong = 0;
//                        giamGia = 0;
//                        moTa = null;
//                        nameImage = null;
//                        tenSanPham.setText(null);
//                        soLuong.setText(null);
//                        giamgia.setText(null);
//                        imgageView.setImageURI(null);
//                        nhomsanpham.setText(null);
//                        title.setText(null);
//                        addImage.setBackgroundResource(R.drawable.border_image_dashed);
//                        mota.setText(null);
//                        imageUri = null;
//                        listDonGia.clear();
//                        adapterDonGia.notifyDataSetChanged();
//                        progressBar.setVisibility(View.INVISIBLE);
//                        new SupportSaveLichSu(getContext(), "Thêm sản phẩm quảng cáo: " + product.getNameProduct());
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            paramsImage1.height = 0;
            layout_image1.setLayoutParams(paramsImage1);

            paramsImage.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layout_image.setLayoutParams(paramsImage);

            imageUri = data.getData();
            loai = true;
            imgageView.setImageURI(imageUri);
            addImage.setBackgroundResource(R.drawable.border_image);
        }
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    //Hiển thị danh sách sản phẩm
    public void Danhsachsanpham(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listProduct = new ArrayList<>();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    DataSnapshot aaa = snapshot1;
                    for (DataSnapshot snapshot2 : aaa.getChildren()){
                        com.example.doandidong.Model.Product product = snapshot2.getValue(com.example.doandidong.Model.Product.class);
                        listProduct.add(product);
                        DataSnapshot aaa1 = snapshot2;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SanPhamCoSan(int gravity){
        boolean loaiStamp = loai;
        loai = false;
        if (window2 == null) {
            return;
        }
        window2.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        window2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window2.getAttributes();
        window2.setAttributes(windownAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog3.setCancelable(true);
        }
        else {
            dialog3.setCancelable(false);
        }


        danhSachSanPhamCoSanAdapter = new DanhSachSanPhamCoSanAdapter(getContext(), AddQuanCaoFragment.this, listProduct);
        recycleview.setAdapter(danhSachSanPhamCoSanAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(linearLayoutManager);
        danhSachSanPhamCoSanAdapter.notifyDataSetChanged();

        dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                if (!loaiStamp) {
                    loai = false;
                }else {
                    loai = true;
                }
            }
        });

        dialog3.show();
    }

    public void onclickItem(final int position) {
        soLuong.setText("");
        giamgia.setText("");
        imgageView.setTag("");
        Log.d("zzzz", listDonGia.size()+"");
        tenSanPham.setText(listProduct.get(position).getNameProduct());
        adapterDonGia.notifyDataSetChanged();
        for (int i = 0; i < listProduct.get(position).getDonGia().size(); i ++ ) {
            listDonGia.add(listProduct.get(position).getDonGia().get(i));
            adapterDonGia.setData(listDonGia);
            listView.setAdapter(adapterDonGia);
        }

        title.setText(listProduct.get(position).getChitiet());
        mota.setText(listProduct.get(position).getChitiet());
        adapterDonGia.notifyDataSetChanged();
        paramsImage1.height = 0;
        layout_image1.setLayoutParams(paramsImage1);
        paramsImage.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layout_image.setLayoutParams(paramsImage);
        nameImage = listProduct.get(position).getImgProduct();
        tenHinh = listProduct.get(position).getImgProduct();
        Picasso.get().load(listProduct.get(position).getImgProduct()).into(imgageView);

        dialog3.dismiss();
    }

    private void getThongtinChuyenkhoan(String soTien,Product noiDung,String key,boolean flag)
    {
        mFirebaseDatabase=FirebaseDatabase.getInstance().getReference("cuaHang").child(ID_CUAHANG);
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key= snapshot.getKey();
                if(key.equals("thongtin"))
                {
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        if(snapshot1.getKey().equals("thongTinChuyenKhoan"))
                        {
                            String ttck=snapshot1.getValue(String.class);
                            String[] abc= ttck.split("\n");
                            CustomDialogClass dialog =new CustomDialogClass(getActivity(),abc[0],abc[2],abc[1],soTien,noiDung.getId());
                            dialog.setData(new CustomDialogClass.pushFirebase() {
                                @Override
                                public void pushQuangCao() {
                                    pushFB(key,noiDung,flag);
                                }
                            });
                            dialog.show();

                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pushFB(String key,Product product1,boolean flag)
    {
        DatabaseReference mFirebaseDatabase1 = mFirebaseInstance.getReference();
        DatabaseReference mFirebaseDatabase2 = mFirebaseInstance.getReference();
        product1.setSuperquangcao(flag);
        mFirebaseDatabase1.child("ChoXacNhan/" + product1.getId()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Thành công 3!", Toast.LENGTH_SHORT).show();
                mFirebaseDatabase2.child("cuaHang/"+ID_CUAHANG).child("sanpham").child(product.getNhomsanpham()).child(key).setValue(product1);
                name = null;
                sLuong = 0;
                giamGia = 0;
                moTa = null;
                nameImage = null;
                tenSanPham.setText(null);
                soLuong.setText(null);
                giamgia.setText(null);
                imgageView.setImageURI(null);
                nhomsanpham.setText(null);
                title.setText(null);
                addImage.setBackgroundResource(R.drawable.border_image_dashed);
                mota.setText(null);
                imageUri = null;
                listDonGia.clear();
                adapterDonGia.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                new SupportSaveLichSu(getContext(), "Thêm sản phẩm quảng cáo: " + product.getNameProduct());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}