package com.example.doandidong.function.Account;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doandidong.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Model.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private String STR_CUAHANG;
    private String ID_USER;
    private Uri imageUri;
    private TextView userName, usernameSmoll, email, soDienThoai;
    private TextView mondaySang, tuesdaySang, wednesdaySang, thursdaySang, fridaySang, saturdaySang, sundaySang;
    private TextView mondayChieu, tuesdayChieu, wednesdayChieu, thursdayChieu, fridayChieu, saturdayChieu, sundayChieu;
    private TextView mondayToi, tuesdayToi, wednesdayToi, thursdayToi, fridayToi, saturdayToi, sundayToi;
    private CircleImageView imageAvata;
    private ImageView editIMG;
    private ProgressBar progressbar;
    private ScrollView scrollView;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private StorageReference reference = FirebaseStorage.getInstance().getReference("hinhanh");
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_account);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        STR_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        ID_USER = thongTinCuaHangSql.IDUser();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/" + STR_CUAHANG).child("user/" + ID_USER);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        getFirebase();
    }

    private void getFirebase() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    nhanVien = snapshot.getValue(NhanVien.class);
                    Log.d("qq", nhanVien.getUsername());
                    soDienThoai.setText(nhanVien.getPhone());
                    userName.setText(nhanVien.getUsername());
                    usernameSmoll.setText(nhanVien.getUsername());
                    email.setText(nhanVien.getEmail());
                    if (nhanVien.getAvata() != null){
                        Picasso.get().load(nhanVien.getAvata()).into(imageAvata);
                    }

                    if (nhanVien.getCaLam().getCaSang().get(0) == true) {
                        mondaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(1) == true) {
                        tuesdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(2) == true) {
                        wednesdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(3) == true) {
                        thursdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(4) == true) {
                        fridaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(5) == true) {
                        saturdaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaSang().get(6) == true) {
                        sundaySang.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundaySang.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                    if (nhanVien.getCaLam().getCaChieu().get(0) == true) {
                        mondayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(1) == true) {
                        tuesdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(2) == true) {
                        wednesdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(3) == true) {
                        thursdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(4) == true) {
                        fridayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(5) == true) {
                        saturdayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaChieu().get(6) == true) {
                        sundayChieu.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundayChieu.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                    if (nhanVien.getCaLam().getCaToi().get(0) == true) {
                        mondayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        mondayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(1) == true) {
                        tuesdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        tuesdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(2) == true) {
                        wednesdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        wednesdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(3) == true) {
                        thursdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        thursdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(4) == true) {
                        fridayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        fridayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(5) == true) {
                        saturdayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        saturdayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }
                    if (nhanVien.getCaLam().getCaToi().get(6) == true) {
                        sundayToi.setBackgroundResource(R.drawable.layout_radius50_red);
                        sundayToi.setTextColor(getResources().getColor(R.color.Java, null));
                    }

                } else {
                }
                scrollView.setAlpha(1);
                progressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void IDLayout() {
        imageAvata = findViewById(R.id.imageAvata);
        editIMG = findViewById(R.id.editIMG);
        userName = findViewById(R.id.userName);
        usernameSmoll = findViewById(R.id.usernameSmoll);
        email = findViewById(R.id.email);
        soDienThoai = findViewById(R.id.soDienThoai);
        editIMG = findViewById(R.id.editIMG);

        mondaySang = findViewById(R.id.mondaySang);
        tuesdaySang = findViewById(R.id.tuesdaySang);
        wednesdaySang = findViewById(R.id.wednesdaySang);
        thursdaySang = findViewById(R.id.thursdaySang);
        fridaySang = findViewById(R.id.fridaySang);
        saturdaySang = findViewById(R.id.saturdaySang);
        sundaySang = findViewById(R.id.sundaySang);

        mondayChieu = findViewById(R.id.mondayChieu);
        tuesdayChieu = findViewById(R.id.tuesdayChieu);
        wednesdayChieu = findViewById(R.id.wednesdayChieu);
        thursdayChieu = findViewById(R.id.thursdayChieu);
        fridayChieu = findViewById(R.id.fridayChieu);
        saturdayChieu = findViewById(R.id.saturdayChieu);
        sundayChieu = findViewById(R.id.sundayChieu);

        mondayToi = findViewById(R.id.mondayToi);
        tuesdayToi = findViewById(R.id.tuesdayToi);
        wednesdayToi = findViewById(R.id.wednesdayToi);
        thursdayToi = findViewById(R.id.thursdayToi);
        fridayToi = findViewById(R.id.fridayToi);
        saturdayToi = findViewById(R.id.saturdayToi);
        sundayToi = findViewById(R.id.sundayToi);
        scrollView = findViewById(R.id.scrollView);
        progressbar = findViewById(R.id.progressbar);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        editIMG.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editIMG:
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
                break;
        }
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
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
                        String urlAvata = uri.toString();
                        nhanVien.setAvata(urlAvata);
                        nhanVien.setNameAvata(name);
                        mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHANG).child("user/"+ID_USER ).setValue(nhanVien);
                        Toast.makeText(ThongTinAccountActivity.this, "Cập nhật avata thành công!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                uploadToFirebase(imageUri);
                imageAvata.setImageURI(imageUri);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


}