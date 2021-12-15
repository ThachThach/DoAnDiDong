package com.example.doandidong.ChucNang.Account;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.doandidong.Common.Utils;
import com.example.doandidong.Data.CuaHangSignIn;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.Error;
import com.example.doandidong.ChucNang.MainActivity;
import java.util.ArrayList;

public class IntroductoryActivity extends AppCompatActivity {

//    private LottieAnimationView lottie;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private ArrayList<CuaHangSignIn> dataAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(IntroductoryActivity.this, R.color.hong_nhat));
        setContentView(R.layout.activity_introductory);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        lottie = findViewById(R.id.lottie);

        int secs = 2; // Delay in seconds

        Utils.delay(secs, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                boolean checkNetwork = isOnline();
                String id = mFirebaseAuth.getUid();
                if(checkNetwork == true){
                    if (mFirebaseUser != null) {
                        mDatabase.child("CuaHangOder/" +id+ "/ThongTinCuaHang/ThietLap").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    boolean checkThietLap = (Boolean) dataSnapshot.getValue();
                                    if (checkThietLap == true) {
                                        mDatabase.child("ACCOUNT_LOGIN/"+id+"/CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Log.d("qq", snapshot.getValue().toString());
                                                if (snapshot.getValue() != null) {
                                                    if (snapshot.getChildrenCount() > 1) {
                                                        getDataAccount(id);
                                                    }else {
                                                        Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }else {
                                                    Toast.makeText(IntroductoryActivity.this, "Đã sảy ra lỗi", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }else {
                                        mFirebaseAuth.signOut();
                                        Intent intent = new Intent(IntroductoryActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else {
                                    mFirebaseAuth.signOut();
                                    Intent intent = new Intent(IntroductoryActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else {
                        Intent intent = new Intent(IntroductoryActivity.this, OnboardingScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Intent intent = new Intent(IntroductoryActivity.this, Error.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
//        lottie.cancelAnimation();

    }

    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            return true;
        }
        return false;
    }

    private void getDataAccount(String UID) {
        dataAccount = new ArrayList<>();
        mDatabase.child("ACCOUNT_LOGIN/" + UID + "/CuaHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String name = snap.child("name").getValue().toString();
                    String chucvu = snap.child("ChucVu").getValue().toString();
                    String ID = snap.child("ID").getValue().toString();
                    dataAccount.add(new CuaHangSignIn(chucvu, ID, name));
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("LIST_CHINHANH",(ArrayList<CuaHangSignIn>) dataAccount);
                bundle.putString("ID_USER" , UID );
                Intent intent = new Intent(IntroductoryActivity.this, ChiNhanhSignInActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BBB", "onCancelled");
            }

        });
    }
}