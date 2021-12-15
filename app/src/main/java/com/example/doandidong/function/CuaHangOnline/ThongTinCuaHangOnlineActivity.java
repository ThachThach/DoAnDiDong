package com.example.doandidong.function.CuaHangOnline;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.doandidong.Common.DataAddress;
import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.AddressVN.DiaChi;
import com.example.doandidong.Model.AddressVN.Huyen;
import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Adapter.ImageAdapter;
import com.example.doandidong.function.CuaHangOnline.Data.DiaChiCuaHang;
import com.example.doandidong.function.CuaHangOnline.Data.Image;
import com.example.doandidong.function.CuaHangOnline.Data.ThongTinCuaHang;
import com.example.doandidong.function.KhuyenMai.ListKhuyenMai;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ThongTinCuaHangOnlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout btnDowUpThongtin, thongtinchung, btnDiaChi, diachi, hinhanh, btnHinhAnh, addImage, layoutLoadImage;
    private LinearLayout.LayoutParams params1, params2, params3, params4;
    private ImageView thongTinIMG, diaChiIMG, hinhanhIMG, imageLogo;
    private boolean setL1 = true;
    private boolean setL2 = true;
    private boolean setL3 = true;
    private TextView luu1, luu2, btnChonAnh;
    private RecyclerView recycleview;
    private ImageAdapter imageAdapter;
    private ArrayList<String> listImage;
    private StorageReference mStogref;
    private DatabaseReference mReference;
    private ArrayList<DiaChi> listDiaChi = new ArrayList<>();
    private String[] tinh;
    private String[] huyen;
    private String[] xa;

    private String tenTinh;
    private String tenHuyen;
    private String tenXa;

    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private Uri imageUri, imageLogoUri;
    private ProgressBar progressBar, progressBarLayout;
    private AutoCompleteTextView phuongxaAuto, thanhphoAuto, quan_huyenAuto;
    private TextInputEditText sonha_soduong, edt_tenCuaHang, edt_phone, edt_mota;
    private ScrollView scrollView3;

    private ArrayAdapter<String> adapterTinh;
    private ArrayAdapter<String> adapterHuyen;
    private ArrayAdapter<String> adapterXa;
    private ArrayList<Image> images = new ArrayList<>();

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private String nameLogo = "";
    private String logoUrl;
    private ThongTinCuaHang thongTinCuaHang;

    private int loai = 0;
    private int ViTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_cua_hang_online);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        movedFirebase();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.thongtin);

        displayItem();
        getThongTinChung();

        DataAddress dataAddress = new DataAddress();
        try {
            listDiaChi = dataAddress.readCompanyJSONFile(this);
            setDataText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayItem() {
        recycleview.setHasFixedSize(true);
        recycleview.setLayoutManager(new GridLayoutManager(this, 1));
        imageAdapter = new ImageAdapter(this, images, ID_CUAHANG, this);
        recycleview.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }


    private void IDLayout() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawable_layout);
        toolbar = findViewById(R.id.toolbar);
        thongTinIMG = findViewById(R.id.thongTinIMG);
        btnDiaChi = findViewById(R.id.btnDiaChi);
        diachi = findViewById(R.id.diachi);
        btnDowUpThongtin = findViewById(R.id.btnDowUpThongtin);
        thongtinchung = findViewById(R.id.thongtinchung);
        diaChiIMG = findViewById(R.id.diaChiIMG);
        btnHinhAnh = findViewById(R.id.btnHinhAnh);
        hinhanh = findViewById(R.id.hinhanh);
        hinhanhIMG = findViewById(R.id.hinhanhIMG);
        luu1 = findViewById(R.id.luu1);
        luu2 = findViewById(R.id.luu2);
        recycleview = findViewById(R.id.recycleview);
        addImage = findViewById(R.id.addImage);
        progressBar = findViewById(R.id.progressBar);
        layoutLoadImage = findViewById(R.id.layoutLoadImage);
        phuongxaAuto = findViewById(R.id.phuongxa);
        thanhphoAuto = findViewById(R.id.thanhpho);
        quan_huyenAuto = findViewById(R.id.quan_huyen);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        scrollView3 = findViewById(R.id.scrollView3);
        btnChonAnh = findViewById(R.id.btnChonAnh);
        imageLogo = findViewById(R.id.imageLogo);

        sonha_soduong = findViewById(R.id.sonha_soduong);
        edt_tenCuaHang = findViewById(R.id.edt_tenCuaHang);
        edt_phone = findViewById(R.id.edt_phone);
        edt_mota = findViewById(R.id.edt_mota);

        params1 = (LinearLayout.LayoutParams) thongtinchung.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) diachi.getLayoutParams();
        params3 = (LinearLayout.LayoutParams) hinhanh.getLayoutParams();
        params4 = (LinearLayout.LayoutParams) layoutLoadImage.getLayoutParams();

        params4.height = 0;
        layoutLoadImage.setLayoutParams(params4);

        btnDowUpThongtin.setOnClickListener(this);
        btnDiaChi.setOnClickListener(this);
        btnHinhAnh.setOnClickListener(this);
        addImage.setOnClickListener(this);
        luu1.setOnClickListener(this);
        luu2.setOnClickListener(this);
        btnChonAnh.setOnClickListener(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        progressBar.setVisibility(View.INVISIBLE);
        playFire();
    }

    private void getThongTinChung() {
        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG + "/thongtin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    scrollView3.setAlpha(1);
                    progressBarLayout.setVisibility(View.INVISIBLE);
                    thongTinCuaHang = snapshot.getValue(ThongTinCuaHang.class);
                    DiaChiCuaHang diaChiCuaHang = snapshot.getValue(DiaChiCuaHang.class);
                    edt_tenCuaHang.setText(thongTinCuaHang.getName());
                    edt_phone.setText(thongTinCuaHang.getSoDienThoai());
                    edt_mota.setText(thongTinCuaHang.getMoTa());
                    sonha_soduong.setText(diaChiCuaHang.getSoNha());

                    if (thongTinCuaHang.getLogoUrl() == null) {
                        imageLogo.setImageResource(R.drawable.alternate_24);
                    } else {
                        Picasso.get().load(thongTinCuaHang.getLogoUrl()).into(imageLogo);
                        logoUrl = thongTinCuaHang.getLogoUrl();
                        nameLogo = thongTinCuaHang.getNamelogo();
                    }

                    tenHuyen = diaChiCuaHang.getQuanHuyen();
                    tenTinh = diaChiCuaHang.getTinhThanhPho();
                    tenXa = diaChiCuaHang.getPhuongXa();

                    phuongxaAuto.setText(diaChiCuaHang.getPhuongXa());
                    quan_huyenAuto.setText(diaChiCuaHang.getQuanHuyen());
                    thanhphoAuto.setText(diaChiCuaHang.getTinhThanhPho());

                    tinh = ArrayTinh();

                    adapterTinh = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store, tinh);
                    thanhphoAuto.setAdapter(adapterTinh);
                    adapterTinh.notifyDataSetChanged();

                    for (int i = 0; i < listDiaChi.size(); i++) {
                        if (listDiaChi.get(i).getTenTinhTP().equals(tenTinh)) {
                            String[] arrayHuyen = ArrayHuyen(i);
                            ViTri = i;
                            adapterHuyen = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                            quan_huyenAuto.setAdapter(adapterHuyen);
                            adapterHuyen.notifyDataSetChanged();
                            break;
                        }
                    }

                    int position = 0;
                    for (int i = 0; i < listDiaChi.get(ViTri).getHuyens().size(); i++) {
                        if (listDiaChi.get(ViTri).getHuyens().get(i).getTenHuyen().equals(tenHuyen)) {
                            position = i;
                            adapterXa = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store,
                                    listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                            phuongxaAuto.setAdapter(adapterXa);
                            break;
                        }
                    }
                }
                else{
                    thongTinCuaHang = new ThongTinCuaHang();
                    luu1.setText("Đăng ký");
                    scrollView3.setAlpha(1);
                    progressBarLayout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void movedFirebase() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                images.add(dataSnapshot.getValue(Image.class));
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).getKey().equals(key)) {
                        images.remove(i);
                        imageAdapter.notifyDataSetChanged();
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cuaHang").child(ID_CUAHANG).child("thongtin/image");
        databaseReference.addChildEventListener(childEventListener);
    }

    public void delete(final int position) {
        new AlertDialog.Builder(ThongTinCuaHangOnlineActivity.this, R.style.AlertDialog).setMessage(
                "Bạn có chắc chắn xóa hình này?"
        ).setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cuaHang").child(ID_CUAHANG).child("thongtin/image");
                databaseReference.child(images.get(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Thất bại: " + images.get(position).getImageName(), Toast.LENGTH_SHORT).show();
                    }
                });
                reference.child(images.get(position).getImageName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã xóa hình", Toast.LENGTH_SHORT).show();
                        new SupportSaveLichSu(ThongTinCuaHangOnlineActivity.this, "Đã xóa 1 hình ảnh");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            }
        }).setNegativeButton("Hủy", null)
                .show();
    }

    private void setDataText() {
        tinh = ArrayTinh();
        adapterTinh = new ArrayAdapter<String>(this, R.layout.item_spinner1_setup_store, tinh);
        thanhphoAuto.setAdapter(adapterTinh);
        adapterTinh.notifyDataSetChanged();

        int i = 0;

        thanhphoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenTinh = parent.getItemAtPosition(position).toString();
                ViTri = position;
                String[] arrayHuyen = ArrayHuyen(position);
                adapterHuyen = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store, arrayHuyen);
                quan_huyenAuto.setText(listDiaChi.get(position).getHuyens().get(0).getTenHuyen());
                tenHuyen = listDiaChi.get(position).getHuyens().get(0).getTenHuyen();
                quan_huyenAuto.setAdapter(adapterHuyen);

                adapterXa = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(position).getHuyens().get(0).getXa());
                phuongxaAuto.setText(listDiaChi.get(position).getHuyens().get(0).getXa().get(0));
                tenXa = listDiaChi.get(position).getHuyens().get(0).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        quan_huyenAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenHuyen = parent.getItemAtPosition(position).toString();

                adapterXa = new ArrayAdapter<String>(ThongTinCuaHangOnlineActivity.this, R.layout.item_spinner1_setup_store,
                        listDiaChi.get(ViTri).getHuyens().get(position).getXa());
                phuongxaAuto.setText(listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0));
                tenXa = listDiaChi.get(ViTri).getHuyens().get(position).getXa().get(0);
                phuongxaAuto.setAdapter(adapterXa);
            }
        });

        phuongxaAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tenXa = parent.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent galleryIntent;
        switch (v.getId()) {
            case R.id.btnDowUpThongtin:
                if (setL1) {
                    params1.height = 0;
                    setL1 = false;
                    thongTinIMG.setImageResource(R.drawable.down_24);
                    thongtinchung.setLayoutParams(params1);
                } else {
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL1 = true;
                    thongTinIMG.setImageResource(R.drawable.up_24);
                    thongtinchung.setLayoutParams(params1);
                }
                break;
            case R.id.btnDiaChi:
                if (setL2) {
                    params2.height = 0;
                    setL2 = false;
                    diaChiIMG.setImageResource(R.drawable.down_24);
                    diachi.setLayoutParams(params2);
                } else {
                    params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL2 = true;
                    diaChiIMG.setImageResource(R.drawable.up_24);
                    diachi.setLayoutParams(params2);
                }
                break;
            case R.id.btnHinhAnh:
                if (setL3) {
                    params3.height = 0;
                    setL3 = false;
                    hinhanhIMG.setImageResource(R.drawable.down_24);
                    hinhanh.setLayoutParams(params3);
                } else {
                    params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    setL3 = true;
                    hinhanhIMG.setImageResource(R.drawable.up_24);
                    hinhanh.setLayoutParams(params3);
                }
                break;
            case R.id.addImage:
                loai = 1;
                galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;
            case R.id.luu1:
                saveCuaHang();
                break;
            case R.id.luu2:
                saveDiaChi();
                break;
            case R.id.btnChonAnh:
                loai = 2;
                galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;
        }
    }

    private void playFire() {
        if (setL1) {
            params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            thongTinIMG.setImageResource(R.drawable.up_24);
            thongtinchung.setLayoutParams(params1);
        } else {
            params1.height = 0;
            thongTinIMG.setImageResource(R.drawable.down_24);
            thongtinchung.setLayoutParams(params1);
        }

        if (setL2) {
            params2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            diaChiIMG.setImageResource(R.drawable.up_24);
            diachi.setLayoutParams(params2);
        } else {
            params2.height = 0;
            diaChiIMG.setImageResource(R.drawable.down_24);
            diachi.setLayoutParams(params2);
        }
        if (setL3) {
            params3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            hinhanhIMG.setImageResource(R.drawable.up_24);
            hinhanh.setLayoutParams(params3);
        } else {
            params3.height = 0;
            hinhanhIMG.setImageResource(R.drawable.down_24);
            hinhanh.setLayoutParams(params3);
        }

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
                intent = new Intent(this, TaoSanPhamOnlineActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.quangcao:
                intent = new Intent(this, QuangCaoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.thongtin:
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

    private void uploadToFirebase(Uri uri) {
        String name = System.currentTimeMillis() + "." + getFileExtension(uri);
        final StorageReference fileRef = reference.child(name);
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                params4.height = 0;
                                layoutLoadImage.setLayoutParams(params4);
                                layoutLoadImage.setEnabled(false);
                                progressBar.setProgress(0);
                            }
                        }, 1000);
                        String key = mFirebaseDatabase.push().getKey();
                        Image image = new Image(key, uri.toString(), name);
                        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin/image/" + key).setValue(image);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                params4.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutLoadImage.setLayoutParams(params4);
                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                params4.height = 0;
                layoutLoadImage.setLayoutParams(params4);
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void saveCuaHang() {
        String nameCH = edt_tenCuaHang.getText().toString();
        String phone = edt_phone.getText().toString();
        String moTa = edt_mota.getText().toString();

        if (nameCH.isEmpty()) {
            edt_tenCuaHang.setError("Nhập tên cửa hàng");
            edt_tenCuaHang.requestFocus();
        } else if (phone.isEmpty()) {
            edt_phone.setError("Nhập số điện thoại");
            edt_phone.requestFocus();
        } else if (nameLogo == null || nameLogo.equals("")) {
            Toast.makeText(this, "Vui lòng chọn hình", Toast.LENGTH_SHORT).show();
        } else {
            if (imageLogoUri != null) {
                if (thongTinCuaHang.getNamelogo()!=null) {
                    if (!nameLogo.equals(thongTinCuaHang.getNamelogo())){
                        Log.d("qqq", "abc");
                        reference.child(thongTinCuaHang.getNamelogo()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                final StorageReference fileRef = reference.child(nameLogo);
                                fileRef.putFile(imageLogoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                thongTinCuaHang.setLogoUrl(uri.toString());
                                                thongTinCuaHang.setNamelogo(nameLogo);
                                                mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("id").child(ID_CUAHANG);
                                                mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("namelogo").setValue(nameLogo);
                                                mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("logoUrl").setValue(uri.toString());
                                                new SupportSaveLichSu(ThongTinCuaHangOnlineActivity.this, "Đã thay đổi thông tin chung cửa hàng online");
                                            }
                                        });
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                    else {

                        final StorageReference fileRef = reference.child(nameLogo);
                        fileRef.putFile(imageLogoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        thongTinCuaHang.setLogoUrl(uri.toString());
                                        thongTinCuaHang.setNamelogo(nameLogo);
                                        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("id").child(ID_CUAHANG);
                                        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("namelogo").setValue(nameLogo);
                                        mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("logoUrl").setValue(uri.toString());
                                        new SupportSaveLichSu(ThongTinCuaHangOnlineActivity.this, "Đã thay đổi thông tin chung cửa hàng online");
                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }else {
                    Log.d("qqq", "zzzz");
                    final StorageReference fileRef = reference.child(nameLogo);
                    fileRef.putFile(imageLogoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    thongTinCuaHang.setLogoUrl(uri.toString());
                                    thongTinCuaHang.setNamelogo(nameLogo);
                                    mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("id").child(ID_CUAHANG);
                                    mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("namelogo").setValue(nameLogo);
                                    mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("logoUrl").setValue(uri.toString());
                                    new SupportSaveLichSu(ThongTinCuaHangOnlineActivity.this, "Đã thay đổi thông tin chung cửa hàng online");
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            }

            luu1.setText("Lưu");
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("id").setValue(ID_CUAHANG);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("moTa").setValue(moTa);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("name").setValue(nameCH);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("soDienThoai").setValue(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void saveDiaChi() {
        String soNhaDuong = sonha_soduong.getText().toString();

        if (soNhaDuong.isEmpty()) {
            sonha_soduong.setError("Nhập số nhà/ đường");
            sonha_soduong.requestFocus();
        } else if (tenTinh.isEmpty()) {
            thanhphoAuto.setError("Chọn tỉnh/ Thành phố");
            thanhphoAuto.requestFocus();
        } else if (tenHuyen.isEmpty()) {
            quan_huyenAuto.setError("Chọn quận/huyện");
            quan_huyenAuto.requestFocus();
        } else if (tenXa.isEmpty()) {
            phuongxaAuto.setError("Chọn phường xã");
            phuongxaAuto.requestFocus();
        } else {
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("id").child(ID_CUAHANG);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("phuongXa").setValue(tenXa);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("quanHuyen").setValue(tenHuyen);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("tinhThanhPho").setValue(tenTinh);
            mFirebaseDatabase.child("cuaHang/" + ID_CUAHANG).child("thongtin").child("soNha").setValue(soNhaDuong).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                    new SupportSaveLichSu(ThongTinCuaHangOnlineActivity.this, "Đã thay đổi địa chỉ cửa hàng online");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThongTinCuaHangOnlineActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (loai == 1) {
                imageUri = data.getData();
                if (imageUri != null) {
                    uploadToFirebase(imageUri);
                }
            } else if (loai == 2) {
                imageLogoUri = data.getData();
                imageLogo.setImageURI(imageLogoUri);
                nameLogo = System.currentTimeMillis() + "." + getFileExtension(imageLogoUri);
            }
        }
    }

    private String[] ArrayTinh() {

        String[] arr = new String[listDiaChi.size()];

        for (int i = 0; i < listDiaChi.size(); i++) {
            arr[i] = listDiaChi.get(i).getTenTinhTP();
        }

        return arr;
    }

    private String[] ArrayHuyen(int pos) {
        String[] arr = new String[listDiaChi.get(pos).getHuyens().size()];

        for (int i = 0; i < listDiaChi.get(pos).getHuyens().size(); i++) {
            arr[i] = listDiaChi.get(pos).getHuyens().get(i).getTenHuyen();
        }

        return arr;
    }
}