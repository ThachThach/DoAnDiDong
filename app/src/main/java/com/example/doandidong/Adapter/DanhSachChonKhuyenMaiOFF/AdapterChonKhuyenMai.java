package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.KhuyenMaiOffModel;
import com.example.doandidong.R;
import java.util.ArrayList;

public class AdapterChonKhuyenMai extends RecyclerView.Adapter<AdapterChonKhuyenMai.Chonkhuyenmaihodel> {
    ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels ;
    private ArrayList<Chonkhuyenmaihodel> chonkhuyenmaihodels =new ArrayList<>();
    public AdapterChonKhuyenMai( ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels){
        this.khuyenMaiOffModels = khuyenMaiOffModels;
    }
    @NonNull
    @Override
    public Chonkhuyenmaihodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themkhuyenmaioff,parent,false);
        Chonkhuyenmaihodel adapterChonKhuyenMai = new Chonkhuyenmaihodel(view);
        return adapterChonKhuyenMai;
    }

    @Override
    public void onBindViewHolder(@NonNull Chonkhuyenmaihodel holder, int position) {
        chonkhuyenmaihodels.add(holder);
        KhuyenMaiOffModel crr = khuyenMaiOffModels.get(position);
        holder.giatu.setText(crr.getGiakhuyenmaitu());
        holder.giaden.setText(crr.getGiakhuyenmaiden());
        holder.giakhuyenmai.setText(crr.getGiakhuyenmai());

    }

    @Override
    public int getItemCount() {
        if (khuyenMaiOffModels!=null){
            return
                    khuyenMaiOffModels.size() ;
        }
        return 0;
    }

    public class Chonkhuyenmaihodel extends RecyclerView.ViewHolder {
        CheckBox checkBox ;
        TextView giatu,giaden,giakhuyenmai;
        public Chonkhuyenmaihodel(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);

        }
    }
    public ArrayList<KhuyenMaiOffModel> PublicArraylist(){
        ArrayList<KhuyenMaiOffModel> khuyenMaiOffModel = new ArrayList<>();
        for (int i = 0; i <chonkhuyenmaihodels.size() ; i++) {
            if(chonkhuyenmaihodels.get(i).checkBox.isChecked()){
                khuyenMaiOffModel.add(khuyenMaiOffModels.get(i));
            }
        }
        return khuyenMaiOffModel;
    }
}
