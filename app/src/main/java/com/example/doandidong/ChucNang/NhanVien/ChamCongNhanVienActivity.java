package com.example.doandidong.ChucNang.NhanVien;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NhanVien_CaLam.ChamCong;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChamCongNhanVienActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase, mDatabase2;
    private FirebaseDatabase firebaseDatabase;
    private String STR_CUAHANG;
    private String ID_USER;
    private TextView username, tv_lammoi, tv_luu, tv_tongca, tv_tong_canghi, tv_add_canghi, tv_add_tangca, tv_tongtangca, tv_add_calam,tru_tongCaLam, tru_tongCaNghi, tru_tongTangCa;
    private TextView mondaySang, tuesdaySang, wednesdaySang, thursdaySang, fridaySang, saturdaySang, sundaySang, nghi, cham, tangca;
    private TextView mondayChieu, tuesdayChieu, wednesdayChieu, thursdayChieu, fridayChieu, saturdayChieu, sundayChieu;
    private TextView mondayToi, tuesdayToi, wednesdayToi, thursdayToi, fridayToi, saturdayToi, sundayToi;
    private CircleImageView imageAvata;
    private ProgressBar progressbar,progressBarLamMoi, progressBarLuu;
    private ScrollView scrollView;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String ID_CUAHANG;
    private NhanVien nhanVien;
    private int soTangCa = 0;
    private int soNgayNghi = 0;
    private int soNgayLam = 0;
    private String ID_NHANVIEN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong_nhan_vien);
        IDLayout();
        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        STR_CUAHANG = thongTinCuaHangSql.IDCuaHang();
        ID_NHANVIEN = getIntent().getStringExtra("ID_NHANVIEN");
        progressBarLamMoi.setVisibility(View.INVISIBLE);
        progressBarLuu.setVisibility(View.INVISIBLE);


        ID_USER = thongTinCuaHangSql.IDUser();
        mDatabase = firebaseDatabase.getReference("CuaHangOder/" + STR_CUAHANG).child("user/" + ID_NHANVIEN);

        getFirebase();
    }

    private void IDLayout() {
        progressBarLamMoi = findViewById(R.id.progressBarLamMoi);
        progressBarLuu = findViewById(R.id.progressBarLuu);
        imageAvata = findViewById(R.id.imageAvata);
        username = findViewById(R.id.username);
        tv_luu = findViewById(R.id.tv_luu);
        tv_lammoi = findViewById(R.id.tv_lammoi);
        tv_tongca = findViewById(R.id.tv_tongca);
        tv_tong_canghi = findViewById(R.id.tv_tong_canghi);
        tv_add_canghi = findViewById(R.id.tv_add_canghi);
        tv_add_tangca = findViewById(R.id.tv_add_tangca);
        tv_tongtangca = findViewById(R.id.tv_tongtangca);
        tv_add_calam = findViewById(R.id.tv_add_calam);
        tru_tongCaLam = findViewById(R.id.tru_tongCaLam);
        tru_tongCaNghi = findViewById(R.id.tru_tongCaNghi);
        tru_tongTangCa = findViewById(R.id.tru_tongTangCa);

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

        cham = findViewById(R.id.cham);
        nghi = findViewById(R.id.nghi);
        tangca = findViewById(R.id.tangca);

        tv_luu.setOnClickListener(this);
        tv_lammoi.setOnClickListener(this);

        cham.setOnClickListener(this);
        nghi.setOnClickListener(this);
        tangca.setOnClickListener(this);
        tru_tongCaLam.setOnClickListener(this);
        tru_tongCaNghi.setOnClickListener(this);
        tru_tongTangCa.setOnClickListener(this);
    }

    private void getFirebase() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    nhanVien = snapshot.getValue(NhanVien.class);
                    username.setText(nhanVien.getUsername());

//                    soTangCa = nhanVien.getChamcong().getTongTangCa();
//                    soNgayNghi = nhanVien.getChamcong().getTongCaNghi();
//                    soNgayLam = nhanVien.getChamcong().getTongCaLam();

                    tv_tongca.setText(nhanVien.getChamcong().getTongCaLam() + "");
                    tv_tong_canghi.setText(nhanVien.getChamcong().getTongCaNghi() + "");
                    tv_tongtangca.setText(nhanVien.getChamcong().getTongTangCa() + "");


                    if (nhanVien.getAvata() != null) {
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

    private void updateFirebase() {
        int sCL = soTangCa + nhanVien.getChamcong().getTongTangCa();
        int sCN = soNgayNghi + nhanVien.getChamcong().getTongCaNghi();
        int sTC = soNgayLam + nhanVien.getChamcong().getTongCaLam();
        if (sCL >= 0 && sCN >= 0 && sTC >=0) {
            progressBarLuu.setVisibility(View.VISIBLE);
            tv_luu.setText("");
            nhanVien.getChamcong().setTongTangCa(soTangCa + nhanVien.getChamcong().getTongTangCa());
            nhanVien.getChamcong().setTongCaNghi(soNgayNghi + nhanVien.getChamcong().getTongCaNghi());
            nhanVien.getChamcong().setTongCaLam(soNgayLam + nhanVien.getChamcong().getTongCaLam());
            mDatabase.child("chamcong").setValue(nhanVien.getChamcong()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ChamCongNhanVienActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                    tv_tongca.setText(""+ nhanVien.getChamcong().getTongCaLam());
                    tv_tong_canghi.setText(""+nhanVien.getChamcong().getTongCaNghi());
                    tv_tongtangca.setText(""+nhanVien.getChamcong().getTongTangCa());
                    soNgayLam = 0;
                    soNgayNghi = 0;
                    soTangCa = 0;
                    tv_add_calam.setText("");
                    tv_add_canghi.setText("");
                    tv_add_tangca.setText("");tv_luu.setText("Lưu");
                    progressBarLuu.setVisibility(View.INVISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChamCongNhanVienActivity.this, "Không thể lưu", Toast.LENGTH_SHORT).show();
                    tv_add_tangca.setText("");tv_luu.setText("Lưu");
                    progressBarLuu.setVisibility(View.INVISIBLE);
                }
            });
        }else {
            Toast.makeText(ChamCongNhanVienActivity.this, "Lỗi! số thay đổi quá nhỏ", Toast.LENGTH_SHORT).show();
        }
    }

    private void lamMoi() {

        new AlertDialog.Builder(ChamCongNhanVienActivity.this).setMessage(
                "Xác nhận làm mới"
        ).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ChamCong chamCong = new ChamCong(0, 0, 0);
                progressBarLamMoi.setVisibility(View.VISIBLE);
                tv_lammoi.setText("");
                mDatabase.child("chamcong").setValue(chamCong).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ChamCongNhanVienActivity.this, "Đã làm mới", Toast.LENGTH_SHORT).show();
                        nhanVien.getChamcong().setTongCaLam(0);
                        nhanVien.getChamcong().setTongCaNghi(0);
                        nhanVien.getChamcong().setTongTangCa(0);
                        tv_tongca.setText(""+ nhanVien.getChamcong().getTongCaLam());
                        tv_tong_canghi.setText(""+nhanVien.getChamcong().getTongCaNghi());
                        tv_tongtangca.setText(""+nhanVien.getChamcong().getTongTangCa());
                        soNgayLam = 0;
                        soNgayNghi = 0;
                        soTangCa = 0;
                        tv_add_calam.setText("");
                        tv_add_canghi.setText("");
                        tv_add_tangca.setText("");
                        progressBarLamMoi.setVisibility(View.INVISIBLE);
                        tv_lammoi.setText("Làm mới");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChamCongNhanVienActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        progressBarLamMoi.setVisibility(View.INVISIBLE);
                        tv_lammoi.setText("Làm mới");
                    }
                });
            }
        }).setNegativeButton("hủy", null)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_luu:
                updateFirebase();
                break;
            case R.id.tv_lammoi:
                lamMoi();
                break;
            case R.id.cham:
                soNgayLam++;
                if (soNgayLam < 0) {
                    tv_add_calam.setText(""+soNgayLam);
                }else {
                    tv_add_calam.setText("+"+ soNgayLam);
                }
                break;
            case R.id.nghi:
                soNgayNghi++;
                if (soNgayNghi < 0) {
                    tv_add_canghi.setText(""+soNgayNghi);
                }else {
                    tv_add_canghi.setText("+"+ soNgayNghi);
                }
                break;
            case R.id.tangca:
                soTangCa++;
                if (soTangCa < 0) {
                    tv_add_tangca.setText(""+soTangCa);
                }else {
                    tv_add_tangca.setText("+"+ soTangCa);
                }
                break;
            case R.id.tru_tongCaLam:
                soNgayLam--;
                if (soNgayLam < 0) {
                    tv_add_calam.setText(""+soNgayLam);
                }else {
                    tv_add_calam.setText("+"+ soNgayLam);
                }
                break;
            case R.id.tru_tongCaNghi:
                soNgayNghi--;
                if (soNgayNghi < 0) {
                    tv_add_canghi.setText(""+soNgayNghi);
                }else {
                    tv_add_canghi.setText("+"+ soNgayNghi);
                }
                break;
            case R.id.tru_tongTangCa:
                soTangCa--;
                if (soTangCa < 0) {
                    tv_add_tangca.setText(""+soTangCa);
                }else {
                    tv_add_tangca.setText("+"+ soTangCa);
                }
                break;
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