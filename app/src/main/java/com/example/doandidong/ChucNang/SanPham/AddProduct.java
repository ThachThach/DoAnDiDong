package com.example.doandidong.ChucNang.SanPham;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


import com.example.doandidong.Common.SupportSaveLichSu;
import com.example.doandidong.Common.ThongTinCuaHangSql;
import com.example.doandidong.Adapter.Package_AdapterSanPham.AdapterDonGia;
import com.example.doandidong.Data.ChucNangThanhToan.DonGia;
import com.example.doandidong.Data.Product;
import com.example.doandidong.Data.SanPham.Category;
import com.example.doandidong.Data.SanPham.DonViTinh;
import com.example.doandidong.R;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {
    private EditText textName, textChitiet, textGianhap, textSoluong, textGiaSanPham, textTenDonViTinh;
    private Spinner spnNhomsanpham;
    private AutoCompleteTextView spnDonViTinh;
    private Button btnAdd,btnHuy, btnThemDonViTinh, btnDonViTinhSanPham, btnDialogHuyDVT, btnDialogThemDVT, btnDialogHuyThemDVT, btnThemDialogThemDVT;
    private ImageView btnChoose;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Product product;
    private ArrayList<String> arrayList;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri ImageUri;
    private StorageReference mStogref;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private ArrayList<String> listDonViTinh = new ArrayList<>();
    private ArrayList<DonGia> listDonGia = new ArrayList<>();
    private AdapterDonGia adapterDonGia;
    private RecyclerView listView;
    private DonViTinh donViTinh;
    private String STR_NHOMSANPHAM = "danhmucsanpham";
    private String STR_SANPHAM = "sanpham";
    private String ID_CUAHANG;
    private String STR_UPLOAD = "uploads";
    private String STR_DONVITINH = "donvitinh";
    private String STR_CUAHANG = "CuaHangOder";
    private String id;
    private ArrayAdapter<String> adapter;
    private Dialog dialog, dialog1;
    private Window window, window1;
    private  Intent intent = new Intent();
    private  String nameDonGia;
    private int gravity = Gravity.CENTER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        //
        textName = findViewById(R.id.textTensanpham);
        textChitiet = findViewById(R.id.textChitietsanpham);
        textGianhap = findViewById(R.id.textGianhap);
        textSoluong = findViewById(R.id.textSoluong);
        spnNhomsanpham = findViewById(R.id.spnNhomsanpham);
        btnChoose = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgChoose);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAddproduct);
        btnDonViTinhSanPham = findViewById(R.id.DonViTinhSanPham);
        btnThemDonViTinh = findViewById(R.id.themDonViTinh);
        listView = findViewById(R.id.listGiaSanPham);
        btnHuy = findViewById(R.id.btnhuyAddProduct);
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHANG = thongTinCuaHangSql.IDCuaHang();

        //firebase
        mStogref = FirebaseStorage.getInstance().getReference(STR_UPLOAD);
        mDatabase = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_NHOMSANPHAM);
        mDatabase1 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_SANPHAM);
        mDatabase2 = FirebaseDatabase.getInstance().getReference(STR_CUAHANG).child(ID_CUAHANG).child(STR_DONVITINH);
        id = mDatabase1.push().getKey();
        //dialog
        dialog = new Dialog(AddProduct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailongdonvitinh);
        window = dialog.getWindow();
        dialog1 = new Dialog(AddProduct.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialogthemdonvitinh);
        window1 = dialog1.getWindow();

        adapterDonGia = new AdapterDonGia(AddProduct.this, listDonGia, dialog, window, spnDonViTinh, adapter, gravity);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });

        btnDonViTinhSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailongDonViTinhSanPham(Gravity.CENTER);
            }
        });
        btnThemDonViTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 10;
                dailongThemDonViTinh(10);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AddProduct.this, ListProduct.class);
                startActivity(intent);
                finish();
            }
        });
        uploadFile();


    }

    private String getFileExtenstion(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    String name = category.getNameCategory();
                    arrayList.add(name);
                }
                if (arrayList.size() != 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProduct.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spnNhomsanpham.setAdapter(adapter);
                }
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (textName.getText().toString().isEmpty()) {
                            textName.setError("Hãy nhập tên sản phẩm");
                            textName.requestFocus();
                        } else if (textChitiet.getText().toString().isEmpty()) {
                            textChitiet.setError("Hãy nhập chi tiết sản phẩm");
                            textChitiet.requestFocus();
                        } else if (textGianhap.getText().toString().isEmpty()) {
                            textGianhap.setError("Hãy nhập giá nhập sản phẩm");
                            textGianhap.requestFocus();
                        } else if (textSoluong.getText().toString().isEmpty()) {
                            textSoluong.setError("Hãy nhập số lượng sản phẩm");
                            textSoluong.requestFocus();
                        } else if (ImageUri == null) {
                            Toast.makeText(AddProduct.this, "No file upload", Toast.LENGTH_SHORT).show();
                        } else if (listDonGia.size() == 0) {
                            Toast.makeText(AddProduct.this, "Hãy chọn đơn vị tính cho sàn phẩm", Toast.LENGTH_SHORT).show();
                        } else {
                            StorageReference fileRefence = mStogref.child("uploads/" + System.currentTimeMillis() + "." + getFileExtenstion(ImageUri));
                            fileRefence.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setProgress(0);

                                        }
                                    }, 5000);

                                    fileRefence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String name = textName.getText().toString();
                                            String chitiet = textChitiet.getText().toString();
                                            Double gianhap = Double.parseDouble(textGianhap.getText().toString());
                                            Integer soluong = Integer.parseInt(textSoluong.getText().toString());
                                            String nhomsanpham = spnNhomsanpham.getSelectedItem().toString();
                                            String img = uri.toString();
                                            String status = "Còn";
                                            product = new Product(id, name, chitiet, nhomsanpham, gianhap, listDonGia, soluong, img, status);
                                            mDatabase1.child(nhomsanpham).child(id).setValue(product);
                                            textName.setText("");
                                            textSoluong.setText("");
                                            textChitiet.setText("");
                                            textGianhap.setText("");
                                            new SupportSaveLichSu(AddProduct.this, "Thêm sản phẩm: " + product.getNameProduct());
                                            listDonGia.clear();
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    progressBar.setProgress((int) progress);
                                }
                            });


                            intent = new Intent(AddProduct.this, ListProduct.class);
                            startActivity(intent);
                            finish();

                        }


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void openFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void dailongDonViTinhSanPham(int gravity) {
        textGiaSanPham = dialog.findViewById(R.id.tedtGiaDonVi);
        spnDonViTinh = dialog.findViewById(R.id.spnTenDonViTinh2);
        btnDialogHuyDVT = dialog.findViewById(R.id.btnhuyDiaLogDVT);
        btnDialogThemDVT = dialog.findViewById(R.id.btnthemDiaLogDVT);

        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listDonViTinh = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DonViTinh donViTinh1 = snapshot1.getValue(DonViTinh.class);
                    String name = donViTinh1.getDonViTinh();
                    listDonViTinh.add(name);
                    adapterDonGia.setData(listDonViTinh);
                }
                if (listDonViTinh.size() != 0) {
                    adapter = new ArrayAdapter<String>(AddProduct.this, R.layout.support_simple_spinner_dropdown_item, listDonViTinh);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spnDonViTinh.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        spnDonViTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameDonGia = parent.getItemAtPosition(position).toString();
            }
        });
        btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonGia donGia = new DonGia();
                donGia.setId(id);
                if (textGiaSanPham.getText().toString().isEmpty()) {
                    textGiaSanPham.setError("Hãy nhập giá !!!");
                    textGiaSanPham.requestFocus();
                    setFinishOnTouchOutside(true);
                } else {
                    donGia.setTenDonGia(nameDonGia);
                    donGia.setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                    listDonGia.add(donGia);
                    dialog.dismiss();
                }
                textGiaSanPham.setText("");
                listView.setLayoutManager(new LinearLayoutManager(AddProduct.this, LinearLayoutManager.VERTICAL, false));
                listView.setAdapter(adapterDonGia);
                adapterDonGia.notifyDataSetChanged();

            }
        });

        dialog.show();


    }

    private void dailongThemDonViTinh(int gravity) {
        textTenDonViTinh = dialog1.findViewById(R.id.edtTenDonViTinh);
        btnDialogHuyThemDVT = dialog1.findViewById(R.id.btnhuyDiaLogThemDVT);
        btnThemDialogThemDVT = dialog1.findViewById(R.id.btnthemDiaLogThemDVT);

        if (window1 == null) {
            return;
        }
        window1.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window1.getAttributes();
        windownAttributes.gravity = gravity;
        window1.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialog1.setCancelable(true);
        } else {
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
                donViTinh = new DonViTinh(name, id);
                mDatabase2.child(id).setValue(donViTinh);
                textTenDonViTinh.setText("");
                dialog1.dismiss();
            }
        });
        dialog1.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);
        }


    }
}

