package com.example.doandidong.ChucNang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Data.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.Account.ChiNhanhActivity;
import com.example.doandidong.ChucNang.Account.ThongTinAccountActivity;
import com.example.doandidong.ChucNang.BaoCao.CapNhatBaoCaoActivity;
import com.example.doandidong.ChucNang.KhachHang.ListKhachHang;
import com.example.doandidong.ChucNang.KhuyenMaiOffLine.KhuyenMaiOff;
import com.example.doandidong.ChucNang.KhuyenMaiOffLine.ListKhuyeMaiOff;
import com.example.doandidong.ChucNang.NhanVien.ListNhanVien;
import com.example.doandidong.ChucNang.SanPham.ListCategory;
import com.example.doandidong.ChucNang.SanPham.ListDonViTinh;
import com.example.doandidong.ChucNang.SanPham.ListProduct;
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.ListBan;
import com.example.doandidong.ChucNang.ThemBan_KhuVuc.ListKhuVuc;


public class ThietLapActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layout_taikhoan, layout_quanlynhanvien, layout_sanpham, layout_danhmuc,
            layout_chi, layout_chinhanh, layout_sodo, layout_khuvuc,layout_donvitinh;
    private String ID_CUAHANG;
    private ThongTinCuaHangSql thongTinCuaHangSql;
    private NhanVien nhanVien;
    private boolean isChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap);
        layout_taikhoan = findViewById(R.id.layout_taikhoan);
        layout_quanlynhanvien = findViewById(R.id.layout_quanlynhanvien);
        layout_sanpham = findViewById(R.id.layout_sanpham);
        layout_danhmuc = findViewById(R.id.layout_danhmuc);
//        layout_nhomkhachang = findViewById(R.id.layout_nhomkhachang);
//        layout_khuyenmai = findViewById(R.id.layout_khuyenmai);
//        layout_kho = findViewById(R.id.layout_kho);
        layout_chi = findViewById(R.id.layout_chi);
        layout_chinhanh = findViewById(R.id.layout_chinhanh);
        layout_sodo = findViewById(R.id.layout_sodo);
        layout_khuvuc = findViewById(R.id.layout_khuvuc);
//        layout_list_khuyenmai = findViewById(R.id.layout_list_khuyenmai);
        layout_donvitinh = findViewById(R.id.layout_donvitinh);


        layout_taikhoan.setOnClickListener(this);
        layout_quanlynhanvien.setOnClickListener(this);
        layout_sanpham.setOnClickListener(this);
        layout_danhmuc.setOnClickListener(this);
//        layout_nhomkhachang.setOnClickListener(this);
//        layout_khuyenmai.setOnClickListener(this);
        layout_chinhanh.setOnClickListener(this);
//        layout_kho.setOnClickListener(this);
        layout_chi.setOnClickListener(this);
        layout_sodo.setOnClickListener(this);
        layout_khuvuc.setOnClickListener(this);
//        layout_list_khuyenmai.setOnClickListener(this);
        layout_donvitinh.setOnClickListener(this);

        thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        nhanVien = thongTinCuaHangSql.selectUser();
        isChu = thongTinCuaHangSql.isChu();

        if (isChu) {
            layout_chinhanh.setVisibility(View.VISIBLE);
        }else {
            layout_chinhanh.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_taikhoan:
                Intent intent1 = new Intent(ThietLapActivity.this, ThongTinAccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_quanlynhanvien:
                Intent intent2 = new Intent(ThietLapActivity.this, ListNhanVien.class);
                startActivity(intent2);
                break;
            case R.id.layout_sanpham:
                if (isChu) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                } else if (nhanVien.getChucVu().get(1)) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.layout_danhmuc:
                if (isChu) {
                    Intent intent4 = new Intent(ThietLapActivity.this, ListCategory.class);
                    startActivity(intent4);
                } else if (nhanVien.getChucVu().get(1)) {
                    Intent intent3 = new Intent(ThietLapActivity.this, ListProduct.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.layout_nhomkhachang:
//                if (isChu) {
//                    Intent intent5 = new Intent(ThietLapActivity.this, ListKhachHang.class);
//                    startActivity(intent5);
//                } else if (nhanVien.getChucVu().get(6)) {
//                    Intent intent5 = new Intent(ThietLapActivity.this, ListKhachHang.class);
//                    startActivity(intent5);
//                }else {
//                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.layout_khuyenmai:
//                if (isChu) {
//                    Intent intent6 = new Intent(ThietLapActivity.this, KhuyenMaiOff.class);
//                    startActivity(intent6);
//                } else if (nhanVien.getChucVu().get(7)) {
//                    Intent intent6 = new Intent(ThietLapActivity.this, KhuyenMaiOff.class);
//                    startActivity(intent6);
//                } else {
//                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.layout_kho:
//                if (isChu || nhanVien.getChucVu().get(1)) {
//                    Intent intent9 = new Intent(ThietLapActivity.this, CapNhatBaoCaoActivity.class);
//                    startActivity(intent9);
//                } else {
//                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
//                }
//                break;
            case R.id.layout_chi:
                if (isChu || nhanVien.getChucVu().get(2) ) {
                    Intent intent8 = new Intent(ThietLapActivity.this, ChiTieuActivity.class);
                    startActivity(intent8);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_chinhanh:
                if (isChu) {
                    Intent intent7 = new Intent(ThietLapActivity.this, ChiNhanhActivity.class);
                    startActivity(intent7);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_sodo:
                if (isChu) {
                    Intent intent10 = new Intent(ThietLapActivity.this, ListBan.class);
                    startActivity(intent10);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_khuvuc:
                if (isChu) {
                    Intent intent11 = new Intent(ThietLapActivity.this, ListKhuVuc.class);
                    startActivity(intent11);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.layout_list_khuyenmai:
//                if (isChu) {
//                    Intent intent12 = new Intent(ThietLapActivity.this, ListKhuyeMaiOff.class);
//                    startActivity(intent12);
//                } else if (nhanVien.getChucVu().get(7)) {
//                    Intent intent12 = new Intent(ThietLapActivity.this, ListKhuyeMaiOff.class);
//                    startActivity(intent12);
//                } else {
//                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
//                }
//                break;

            case R.id.layout_donvitinh:
                if (isChu) {
                    Intent intent13 = new Intent(ThietLapActivity.this, ListDonViTinh.class);
                    startActivity(intent13);
                } else if (nhanVien.getChucVu().get(1)) {
                    Intent intent13 = new Intent(ThietLapActivity.this, ListDonViTinh.class);
                    startActivity(intent13);
                }else {
                    Toast.makeText(this, "Không thể thực hiện hành động này", Toast.LENGTH_SHORT).show();
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