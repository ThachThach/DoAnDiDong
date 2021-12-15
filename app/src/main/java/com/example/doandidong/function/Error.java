package com.example.doandidong.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.doandidong.R;
import com.example.doandidong.function.Account.IntroductoryActivity;

public class Error extends AppCompatActivity {

    Button again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(Error.this, R.color.red));
        setContentView(R.layout.activity_error);

        again = findViewById(R.id.btnAgain);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Error.this, IntroductoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}