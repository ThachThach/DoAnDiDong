package com.example.doandidong.Adapter.DanhSachChonKhuyenMaiOFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Model.KhuyenMaiOffModel;
import com.example.doandidong.R;
import com.example.doandidong.function.KhuyenMaiOffLine.KhuyenMaiOff;
import java.util.ArrayList;

public class AdapterDanhSachKhuyenMai extends RecyclerView.Adapter<AdapterDanhSachKhuyenMai.danhsachkhuyenmai> {
    ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels ;
    KhuyenMaiOff khuyenMaiOff;
    public AdapterDanhSachKhuyenMai(ArrayList<KhuyenMaiOffModel> khuyenMaiOffModels,KhuyenMaiOff khuyenMaiOff){
        this.khuyenMaiOffModels = khuyenMaiOffModels;
        this.khuyenMaiOff = khuyenMaiOff;
    }
    @NonNull
    @Override
    public danhsachkhuyenmai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachend_khuyenmaioff,parent,false);
        danhsachkhuyenmai adapterChonKhuyenMai = new danhsachkhuyenmai(view);
        return adapterChonKhuyenMai;

    }

    @Override
    public void onBindViewHolder(@NonNull danhsachkhuyenmai holder, int position) {
        KhuyenMaiOffModel crr = khuyenMaiOffModels.get(position);
        holder.giatu.setText(crr.getGiakhuyenmaitu());
        holder.giaden.setText(crr.getGiakhuyenmaiden());
        holder.giakhuyenmai.setText(crr.getGiakhuyenmai());
       holder.bnt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                khuyenMaiOff.delete(position, new KhuyenMaiOff.delete1() {
                    @Override
                    public void delete() {
                        notifyDataSetChanged();
                    }
                });

                Toast.makeText(khuyenMaiOff, "delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (khuyenMaiOffModels!=null){
            return
                    khuyenMaiOffModels.size() ;
        }
        return 0;
    }

    public class danhsachkhuyenmai extends RecyclerView.ViewHolder {
        TextView giatu,giaden,giakhuyenmai;
        ImageView bnt_delete;
        public danhsachkhuyenmai(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            bnt_delete = itemView.findViewById(R.id.bnt_delete);

        }
    }
}
