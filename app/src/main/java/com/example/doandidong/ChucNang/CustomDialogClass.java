package com.example.doandidong.ChucNang;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.doandidong.R;

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {

    public interface pushFirebase{
        void pushQuangCao();
    }

    public pushFirebase data;
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public TextView tvStk,tvNguoiNhan,tvNganhang,tvNoidung,tvsoTien;
    private String stk;
    private String nguoinhan;
    private String nganhang;
    private String noidung;
    private String soTien;

    public CustomDialogClass(Activity a,String stk,String nguoinhan,String nganhang,String soTien,String noidung) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.stk=stk;
        this.nguoinhan=nguoinhan;
        this.nganhang=nganhang;
        this.noidung=noidung;
        this.soTien=soTien;


    }
    public void setData(pushFirebase data)
    {
        this.data=data;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        tvNoidung=findViewById(R.id.tvNoidung);
        tvStk=findViewById(R.id.tvStk);
        tvNguoiNhan=findViewById(R.id.tvNguoiNhan);
        tvNganhang=findViewById(R.id.tvNganhang);
        tvsoTien=findViewById(R.id.tvSotien);
        tvStk.setText(stk);
        tvNguoiNhan.setText(nguoinhan);
        tvNganhang.setText(nganhang);
        tvNoidung.setText(noidung);
        tvsoTien.setText(soTien);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                data.pushQuangCao();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
