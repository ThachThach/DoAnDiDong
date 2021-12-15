package com.example.doandidong.function.CuaHangOnline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.ChucNangThanhToan.DonGia;
import com.example.doandidong.Model.SanPham.DonViTinh;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Adapter.AdapterDonGia;
import com.example.doandidong.function.CuaHangOnline.Data.Product;
import com.example.doandidong.function.KhuyenMai.ListKhuyenMai;
import java.util.ArrayList;

public class TaoSanPhamOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextInputEditText tenSanPham, soLuong, mota, giamGia;
    private AutoCompleteTextView nhomsanpham;
    private RecyclerView listView;
    private TextView taoDon,addDonViTinh, addLoai;
    private String nhomSPText;
    private Uri imageUri;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private ImageView quangCaosanPhamIMG, imgageView;
    private LinearLayout addImage, layout_image, layout_image1;
    private LinearLayout.LayoutParams paramsImage, paramsImage1;
    private Window window, window1;
    private Dialog dialog, dialog1;
    private EditText textGiaSanPham,textTenDonViTinh;
    private Button btnDialogHuyDVT, btnDialogThemDVT, btnDialogHuyThemDVT, btnThemDialogThemDVT;
    private ArrayAdapter<String> adapter;
    private String STR_CUAHANG = "CuaHangOder";
    private String STR_DONVITINH = "donvitinh";
    private ArrayList<String> listDonViTinh;
    private ArrayList<DonGia> listDonGia = new ArrayList<>();
    private AdapterDonGia adapterDonGia;
    private String id;
    private DonViTinh donViTinh;
    private Product product;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private ArrayList<String> listNhom = new ArrayList<>();
    private ArrayAdapter<String> adapterNhomSp;
    private String nhomSp = "";
    private ProgressBar progressBarlayout, progressBar;
    private ScrollView scrollView;
    private AutoCompleteTextView spnTenDonViTinh2;
    private String nhom = "";
    private TextInputEditText title;


    private DatabaseReference mDatabase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_san_pham_online);
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        IDLayout();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.sanpham);

        dialog = new Dialog(TaoSanPhamOnlineActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailongdonvitinh);
        window = dialog.getWindow();

        dialog1 = new Dialog(TaoSanPhamOnlineActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemdonvitinh);
        window1 = dialog1.getWindow();
        textTenDonViTinh = dialog1.findViewById(R.id.edtTenDonViTinh);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);
        spnTenDonViTinh2 = dialog.findViewById(R.id.spnTenDonViTinh2);

        textGiaSanPham = dialog.findViewById(R.id.tedtGiaDonVi);
        btnDialogHuyDVT = dialog.findViewById(R.id.btnhuyDiaLogDVT);
        btnDialogThemDVT = dialog.findViewById(R.id.btnthemDiaLogDVT);
        progressBar.setVisibility(View.INVISIBLE);

        getNhomSanPham();
    }

    private void IDLayout() {
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_DONVITINH);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        tenSanPham = findViewById(R.id.tenSanPham);
        soLuong = findViewById(R.id.soLuong);
        mota = findViewById(R.id.mota);
        nhomsanpham = findViewById(R.id.nhomsanpham);
        addImage = findViewById(R.id.addImage);
        listView = findViewById(R.id.donViTinh);
        taoDon = findViewById(R.id.taoDon);
        layout_image = findViewById(R.id.layout_image);
        layout_image1 = findViewById(R.id.layout_image1);
        quangCaosanPhamIMG = findViewById(R.id.quangCaosanPhamIMG);
        imgageView = findViewById(R.id.imgageView);
        addLoai = findViewById(R.id.addLoai);
        addDonViTinh = findViewById(R.id.addDonViTinh);
        giamGia = findViewById(R.id.giamGia);
        progressBarlayout = findViewById(R.id.progressBarlayout);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        title = findViewById(R.id.title);

        paramsImage = (LinearLayout.LayoutParams) layout_image.getLayoutParams();
        paramsImage1 = (LinearLayout.LayoutParams) layout_image1.getLayoutParams();

        adapterNhomSp = new ArrayAdapter<String>(TaoSanPhamOnlineActivity.this, R.layout.item_spinner1_setup_store, listNhom);
        nhomsanpham.setAdapter(adapterNhomSp);
        nhomsanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterNhomSp = new ArrayAdapter<String>(TaoSanPhamOnlineActivity.this, R.layout.item_spinner1_setup_store, listNhom);

                nhomSp = parent.getItemAtPosition(position).toString();
                nhomsanpham.setText(nhomSp);
                nhomsanpham.setAdapter(adapterNhomSp);
            }
        });
        adapterNhomSp.notifyDataSetChanged();

        taoDon.setOnClickListener(this);
        addImage.setOnClickListener(this);
        addLoai.setOnClickListener(this);
        addDonViTinh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.taoDon:
                TaoDon(imageUri);
                break;
            case R.id.addImage:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;
            case R.id.addLoai:
                donViTinh(Gravity.CENTER);
                break;
            case R.id.addDonViTinh:
                themDonViTinh(Gravity.CENTER);
                break;
        }
    }

    private String nameImage;
    private int sLuong = 0;

    private void TaoDon(Uri uri){

        String name =  tenSanPham.getText().toString();
        String soluong = soLuong.getText().toString();
        String moTaChiTiet = mota.getText().toString();
        String titleText = title.getText().toString();
        if (!soluong.isEmpty()) {
            sLuong = Integer.parseInt(soluong);
        }

        if (name.isEmpty()) {
            tenSanPham.setError("Nhập tên");
            tenSanPham.requestFocus();
        }else if (soluong.isEmpty()) {
            soLuong.setError("Nhập số lượng");
            soLuong.requestFocus();
        }else if (titleText.isEmpty()) {
            title.setError("Nhập mô tả ngắn");
            title.requestFocus();
        }else if (moTaChiTiet.isEmpty()) {
            mota.setError("Nhập mô tả chi tiết");
            mota.requestFocus();
        }else if (listDonGia.size()<=0){
            Toast.makeText(TaoSanPhamOnlineActivity.this, "Thêm đơn vị tính! ", Toast.LENGTH_SHORT).show();
        }else {
            nameImage = System.currentTimeMillis() + "." + getFileExtension(uri);
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileRef = reference.child(nameImage);
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String key = mFirebaseDatabase.push().getKey();
                            String imgProduct = uri.toString();

                            String img = uri.toString();
                            String status = "Còn";
                            product = new Product(key, name, moTaChiTiet, nhomSp, 0.0, 123, imgProduct, nameImage, 0.0, status, listDonGia, ID_CUAHANG, false, titleText, false);
                            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG + "/sanpham/" + product.getNhomsanpham() + "/" + key).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(TaoSanPhamOnlineActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                                    tenSanPham.setText(null);
                                    soLuong.setText(null);
                                    imgageView.setImageURI(null);
                                    giamGia.setText(null);
                                    listDonGia.clear();
                                    title.setText(null);
                                    adapterDonGia.notifyDataSetChanged();
                                    nhomsanpham.setText(null);
                                    addImage.setBackgroundResource(R.drawable.border_image_dashed);
                                    mota.setText(null);
                                    imageUri = null;
                                    nameImage = "";
                                    progressBar.setVisibility(View.INVISIBLE);
                                    new SupportSaveLichSu(TaoSanPhamOnlineActivity.this, "Tạo sản phẩm mới: " + product.getNameProduct());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TaoSanPhamOnlineActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
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


        }

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
                new SupportSaveLichSu(TaoSanPhamOnlineActivity.this, "Tạo đơn vị tính mới: " + donViTinh.getDonViTinh());
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
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    DonViTinh donViTinh1 = snapshot1.getValue(DonViTinh.class);
                    String name = donViTinh1.getDonViTinh();
                    listDonViTinh.add(name);
                }
                if (listDonViTinh != null && listDonViTinh.size() > 0) {
                    nhom = listDonViTinh.get(0);

                    adapter = new ArrayAdapter<String>(TaoSanPhamOnlineActivity.this, R.layout.item_spinner1_setup_store, listDonViTinh);
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
                adapter = new ArrayAdapter<String>(TaoSanPhamOnlineActivity.this, R.layout.item_spinner1_setup_store, listDonViTinh);
                spnTenDonViTinh2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonGia donGia = new DonGia();
                donGia.setTenDonGia(nhom);
                donGia.setId(id);
                if (textGiaSanPham.getText().toString().isEmpty()){
                    textGiaSanPham.setError("Hãy nhập giá !!!");
                    textGiaSanPham.requestFocus();
                    setFinishOnTouchOutside(true);
                }
                else {
                    donGia.setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                    listDonGia.add(donGia);
                    dialog.dismiss();
                }
                if (nhom.isEmpty()) {
                    spnTenDonViTinh2.setError("Đơn giá");
                    spnTenDonViTinh2.requestFocus();
                }else {
                    adapterDonGia = new AdapterDonGia(TaoSanPhamOnlineActivity.this,listDonGia,dialog,window,adapter,gravity,spnTenDonViTinh2, listDonViTinh);
                    listView.setLayoutManager(new LinearLayoutManager(TaoSanPhamOnlineActivity.this,LinearLayoutManager.VERTICAL,false));
                    listView.setAdapter(adapterDonGia);
                    adapterDonGia.notifyDataSetChanged();
                }
            }
        });

        dialog.show();

    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            paramsImage1.height = 0;
            layout_image1.setLayoutParams(paramsImage1);

            paramsImage.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layout_image.setLayoutParams(paramsImage);

            imageUri = data.getData();
            imgageView.setImageURI(imageUri);
            addImage.setBackgroundResource(R.drawable.border_image);
        }
    }

    private void getNhomSanPham() {
        mFirebaseDatabase.child("nhomSanPham").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        listNhom.add(snap.getValue().toString());
                    }

                    adapterNhomSp = new ArrayAdapter<String>(TaoSanPhamOnlineActivity.this, R.layout.item_spinner1_setup_store, listNhom);
                    nhomsanpham.setAdapter(adapterNhomSp);
                    adapterNhomSp.notifyDataSetChanged();
                    progressBarlayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                } else {
                    progressBarlayout.setVisibility(View.INVISIBLE);
                    scrollView.setAlpha(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cuahang:
                intent = new Intent(this, CuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sanpham:
                break;
            case R.id.quangcao:
                intent = new Intent(this, QuangCaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.thongtin:
                intent = new Intent(this, ThongTinCuaHangOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.giolamviec:
                intent = new Intent(this, ThoiGianLamViecOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.vanchuyen:
                intent = new Intent(this, CauHinhVanChuyenOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.khuyenmai:
                intent = new Intent(this, ListKhuyenMai.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_danh_sach_san_pham_online, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.danhsachsanpham:
                Intent intent = new Intent(TaoSanPhamOnlineActivity.this, DanhSachSanPhamActivity.class);
                startActivity(intent);
        }
        return true;
    }

}