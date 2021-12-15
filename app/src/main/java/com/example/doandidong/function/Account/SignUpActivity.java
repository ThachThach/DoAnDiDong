package com.example.doandidong.function.Account;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.doandidong.Model.CaLam;
import com.example.doandidong.Model.NhanVien;
import com.example.doandidong.Model.NhanVien_CaLam.ChamCong;
import com.example.doandidong.R;
import com.example.doandidong.database.ThongTinCuaHangSql;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signinNow, signup;
    private TextView logoText, sloganText, forgetPass;
    private ImageView imageView;
    private TextInputEditText username, email, phone, password, confirm_password;
    private Dialog dialog;
    private Window window;

    //Firebase
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;

    private final String CUA_HANG = "CuaHangOder";
    private final String ACCOUNT_LOGIN = "ACCOUNT_LOGIN";
    private final static String KEY_CHILD_STORE = "KEY_CHILD_STORE";

    private List<CheckBox> checkBoxes;
    private ArrayList<Boolean> congViec;
    private CaLam caLam = new CaLam();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signinNow = findViewById(R.id.btn_signin_now);
        signup = findViewById(R.id.btn_signup);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        password = findViewById(R.id.edt_password);
        imageView = findViewById(R.id.imageView);
        forgetPass = findViewById(R.id.lbl_forget_pass);
        confirm_password = findViewById(R.id.edt_confirm_password);

        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);
        caLam.getCaSang().add(true);

        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);
        caLam.getCaChieu().add(true);

        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);
        caLam.getCaToi().add(true);

        signinNow.setOnClickListener(this);
        signup.setOnClickListener(this);

        dialog = new Dialog(SignUpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_loading);
        window = dialog.getWindow();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signin_now:
                onBackPressed();
                break;
            case R.id.btn_signup:
                signup();
                break;
        }
    }

    private void signup(){
        mFirebaseAuth = FirebaseAuth.getInstance();

        String mail = email.getText().toString();
        String userName = username.getText().toString();
        String mPhone = phone.getText().toString();
        String pass = password.getText().toString();
        String cpass = confirm_password.getText().toString();


        if(userName.isEmpty()){
            username.setError("Plese enter username");
            username.requestFocus();
        }else if(mail.isEmpty()){
            email.setError("Plese enter email");
            email.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("please provide valid email");
            email.requestFocus();
        } else if(mPhone.isEmpty()){
            phone.setError("Plese enter email");
            phone.requestFocus();
        }else if(pass.isEmpty()){
            password.setError("Plese enter password");
            password.requestFocus();
        }else if(cpass.isEmpty()){
            confirm_password.setError("Plese enter confirm password");
            confirm_password.requestFocus();
        }else if (!pass.equals(cpass)){
            confirm_password.setError("Passwords are not the sames");
            confirm_password.requestFocus();
        }
        else if(mail.isEmpty() && pass.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Fialds Are Empty!", Toast.LENGTH_LONG).show();
        }else if(!(mail.isEmpty() && pass.isEmpty() && mPhone.isEmpty() &&userName.isEmpty() )){
            dialog.show();
            mFirebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "SignUp UnSuccessful, plese Try Again", Toast.LENGTH_SHORT).show();
                    }else{
                        String UID = mFirebaseAuth.getUid();
                        mFirebaseInstance = FirebaseDatabase.getInstance();
                        mFirebaseDatabase = mFirebaseInstance.getReference();

                        congViec = new ArrayList<>();
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);
                        congViec.add(true);

                        mFirebaseDatabase.child(CUA_HANG+"/"+UID+"/ThongTinCuaHang/ThietLap").setValue(false);
                        mFirebaseDatabase.child(CUA_HANG+"/"+UID+"/ChuCuaHang").setValue(UID);

                        NhanVien nhanVien = new NhanVien(userName, mail, congViec ,caLam ,mPhone , UID, true);
                        ChamCong cham = new ChamCong(0,0,0);
                        nhanVien.setChamcong(cham);
                        mFirebaseDatabase.child(CUA_HANG+"/"+UID+"/user/"+UID).setValue(nhanVien);

                        mFirebaseDatabase.child(ACCOUNT_LOGIN).child(UID+"/CuaHang/"+UID).child("ChucVu").setValue(0);
                        mFirebaseDatabase.child(ACCOUNT_LOGIN).child(UID+"/CuaHang/"+UID).child("ID").setValue(UID);
                        mFirebaseDatabase.child(ACCOUNT_LOGIN).child(UID+"/CuaHang/"+UID).child("name").setValue("Chi nhánh 1");

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putString(KEY_CHILD_STORE , CUA_HANG+"/"+UID+"/user/"+UID );
                                bundle.putString("ID_USER" , UID );
                                ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(SignUpActivity.this, "app_database.sqlite", null, 2);
                                thongTinCuaHangSql.createTableUser();
                                Cursor cursor = thongTinCuaHangSql.selectUser();
                                Cursor cursor1 = thongTinCuaHangSql.selectChuCuaHang();
                                String quyen = "1111111";

                                if (cursor.getCount() > 0){
                                    String IdOld = "";
                                    while (cursor.moveToNext()) {
                                        IdOld = cursor.getString(0);
                                    }
                                    thongTinCuaHangSql.UpdateUser(UID, IdOld, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                                }else {
                                    thongTinCuaHangSql.InsertUser(UID, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                                }

                                if (cursor1.getCount() > 0) {
                                    thongTinCuaHangSql.UpdateChuCuaHang("true");
                                }else {
                                    thongTinCuaHangSql.InsertChuCuaHang("true");
                                }

                                Toast.makeText(SignUpActivity.this, "Signup succes", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, StoreSetting.class);
                                intent.putExtras(bundle);
                                dialog.dismiss();
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            });}else {
            Toast.makeText(SignUpActivity.this,"Error Occurred!", Toast.LENGTH_LONG).show();
        }
    }
}