package com.example.doandidong.function.Account;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Model.CuaHangSignIn;
import com.example.doandidong.Model.NhanVien_CaLam.NhanVien;
import com.example.doandidong.R;
import com.example.doandidong.database.DbBaoCao;
import com.example.doandidong.database.ThongTinCuaHangSql;
import com.example.doandidong.function.MainActivity;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sigupNow, login;
    private TextView logoText, sloganText, forgetPass;
    private ImageView imageView;
    private TextInputEditText username, password;
    private CardView btn_admin;
    private Dialog dialog;
    private Window window;

    private ArrayList<CuaHangSignIn> dataAccount;
    private String idUser;
    private Boolean thietLap;
    private Boolean checkThietLap = false;
    private String tenCuaHang;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private LinearLayout layout;

    //SQLite
    private DbBaoCao dataSql;
    private ThongTinCuaHangSql thongTinCuaHangSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sigupNow = findViewById(R.id.btn_signup_now);
        login = findViewById(R.id.btn_login);
        logoText = findViewById(R.id.logoText);
        sloganText = findViewById(R.id.sloganText);
        username = findViewById(R.id.edt_email_username);
        password = findViewById(R.id.edt_password);
        imageView = findViewById(R.id.imageView);
        forgetPass = findViewById(R.id.lbl_forget_pass);
        btn_admin = findViewById(R.id.btn_admin);
        layout = findViewById(R.id.layout);
        layout.setAlpha(1);
        dialog = new Dialog(SignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_loading);
        window = dialog.getWindow();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        Intent intent = getIntent();
        String emailIntent = intent.getStringExtra("email");

        if (emailIntent != null) {
            username.setText(emailIntent);
            sloganText.setText("Sign up succes! Sign in to continue...");
        }

        mAuth = FirebaseAuth.getInstance();

        sigupNow.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
        login.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup_now:
                callSignup();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.lbl_forget_pass:
                callForgetPassword();
                break;
        }
    }

    private void callSignup() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);

        Pair[] pairs = new Pair[7];

        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_welcome");
        pairs[2] = new Pair<View, String>(sloganText, "logo_signin");
        pairs[3] = new Pair<View, String>(username, "edt_username");
        pairs[4] = new Pair<View, String>(password, "edt_password");
        pairs[5] = new Pair<View, String>(forgetPass, "lbl_forget");
        pairs[6] = new Pair<View, String>(login, "button_sign");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }

    private void callForgetPassword() {
        Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);

        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(imageView, "logo_image");
        pairs[1] = new Pair<View, String>(logoText, "logo_welcome");
        pairs[2] = new Pair<View, String>(sloganText, "logo_signin");
        pairs[3] = new Pair<View, String>(username, "edt_username");
        pairs[4] = new Pair<View, String>(login, "button_sign");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }
    }

    // onclick login
    private void login() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        if (email.isEmpty()) {
            username.setError("Plese enter email id");
            username.requestFocus();
        } else if (pass.isEmpty()) {
            password.setError("Plese enter your password");
            password.requestFocus();
        } else if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Fialds Are Empty!", Toast.LENGTH_LONG).show();
        } else if (!(email.isEmpty() && pass.isEmpty())) {
            login.setEnabled(false);
            onDialog();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Signin error", Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);
                        dialog.dismiss();
                    } else {
                        login.setEnabled(true);
                        idUser = mAuth.getUid();
                        getDataAccount(idUser);
                        delaySignin(idUser);
                    }
                }
            });
        } else {
            Toast.makeText(SignInActivity.this, "Error Occurred!", Toast.LENGTH_LONG).show();
            login.setEnabled(true);
            dialog.dismiss();
        }
    }

    private void onDialog() {
        if (window == null) {
            return;
        }
        dialog.show();
    }

    private void getDataAccount(String UID) {
        dataAccount = new ArrayList<>();
        mFirebaseDatabase.child("ACCOUNT_LOGIN/" + UID + "/CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String name = snap.child("name").getValue().toString();
                    String chucvu = snap.child("ChucVu").getValue().toString();
                    String ID = snap.child("ID").getValue().toString();
                    dataAccount.add(new CuaHangSignIn(chucvu, ID, name));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BBB", "onCancelled");
            }

        });
    }

    int soVongLap = 50;
    private void delaySignin(String UID) {
        if ( soVongLap > 0){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dataAccount.size() >= 1){
                        soVongLap = 50;
                        if (dataAccount.size() > 1) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("LIST_CHINHANH",(ArrayList<CuaHangSignIn>) dataAccount);
                            bundle.putString("ID_USER" , UID );
                            Intent intent1 = new Intent(SignInActivity.this, ChiNhanhSignInActivity.class);
                            intent1.putExtras(bundle);
                            startActivity(intent1);
                            login.setEnabled(false);
                            finish();
                        }else if (dataAccount.size() == 0) {
                            Toast.makeText(SignInActivity.this, "Signin error", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            login.setEnabled(true);
                            dialog.dismiss();
                        } else {
                            getDataThietLap();
                            delayDataThietLap(UID);
                        }
                    }else{
                        delaySignin(UID);
                    }
                }
            }, 100);
        }else return;

        soVongLap--;
    }

    private void getDataThietLap() {
        mFirebaseDatabase.child("CuaHangOder/"+dataAccount.get(0).getID()+"/ThongTinCuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thietLap = (Boolean) snapshot.child("ThietLap").getValue();
                Log.d("AAA", thietLap+"");
                checkThietLap = true;
                if (thietLap == true) {
                    tenCuaHang = snapshot.child("tenCuaHang").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void delayDataThietLap(String UID) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkThietLap == true) {
                    if (thietLap == true) {
                        checkThietLap = false;
                        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(SignInActivity.this, "app_database.sqlite", null, 2);
                        thongTinCuaHangSql.createTable();
                        Cursor cursor = thongTinCuaHangSql.selectThongTin();
                        if (cursor.getCount() > 0){
                            String IdOld = "";
                            while (cursor.moveToNext()) {
                                IdOld = cursor.getString(0);
                            }
                            thongTinCuaHangSql.UpdateCuaHang(dataAccount.get(0).getID(), IdOld, tenCuaHang);
                        }else {
                            thongTinCuaHangSql.InsertThonTin(dataAccount.get(0).getID(), tenCuaHang);
                        }
                        login.setEnabled(false);
                        getDataUser(UID);

                    }else {
                        login.setEnabled(false);
                        Bundle bundle = new Bundle();
                        dialog.dismiss();
                        bundle.putString("ID_USER" , UID );
                        Toast.makeText(SignInActivity.this, "Signup succes", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, StoreSetting.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    delayDataThietLap(UID);
                }
            }
        }, 100);
    }

    private void getDataUser (String UID){
        thongTinCuaHangSql = new ThongTinCuaHangSql(SignInActivity.this, "app_database.sqlite", null, 2);
        thongTinCuaHangSql.createTableUser();
        thongTinCuaHangSql.createTableChuCuaHang();

        mFirebaseDatabase.child("CuaHangOder/"+dataAccount.get(0).getID()+"/user/"+UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cursor cursor = thongTinCuaHangSql.selectUser();
                Cursor cursor1 = thongTinCuaHangSql.selectChuCuaHang();
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                String quyen = "";

                for (int i = 0; i < nhanVien.getChucVu().size(); i++) {
                    if (nhanVien.getChucVu().get(i)) {
                        quyen = quyen+ "1";
                    }else{
                        quyen = quyen+ "0";
                    }
                }
                if (cursor1.getCount() > 0) {
                    thongTinCuaHangSql.UpdateChuCuaHang(nhanVien.isChuCuaHang()+"");
                }else {
                    thongTinCuaHangSql.InsertChuCuaHang(nhanVien.isChuCuaHang()+"");
                }

                if (cursor.getCount() > 0){
                    String IdOld = "";
                    while (cursor.moveToNext()) {
                        IdOld = cursor.getString(0);
                    }
                    thongTinCuaHangSql.UpdateUser(UID, IdOld, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }else {
                    thongTinCuaHangSql.InsertUser(UID, nhanVien.getUsername(), nhanVien.getEmail(), nhanVien.getPhone(), quyen);
                }
                dialog.dismiss();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( dialog!=null && dialog.isShowing() ){
            dialog.cancel();
        }
    }
}