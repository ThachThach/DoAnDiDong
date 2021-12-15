package com.example.doandidong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Model.ChiTietNhapKho;
import com.example.doandidong.R;
import java.util.List;

public class CapNhatBaoCaoKhoAdapter extends RecyclerView.Adapter<CapNhatBaoCaoKhoAdapter.anhXa> {

    private Context context;
    private List<ChiTietNhapKho> list;
    private EditText soLieuMoi;

    public CapNhatBaoCaoKhoAdapter(Context context, List<ChiTietNhapKho> list, EditText soLieuMoi) {
        this.context = context;
        this.list = list;
        this.soLieuMoi = soLieuMoi;
    }

    @NonNull
    @Override
    public anhXa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new anhXa(LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_cap_nhat_kho_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull anhXa holder, int position) {
        ChiTietNhapKho chiTietNhapKho=list.get(position);
        holder.tenSanPham.setText(chiTietNhapKho.getTenSanPham());
        holder.soLieuCu.setText(chiTietNhapKho.getSoLieuCu());
        holder.soLieuMoi.setText(chiTietNhapKho.getSoLieuMoi());

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                soLieuMoi.setText("");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) return list.size();
        return 0;
    }

    public class anhXa extends RecyclerView.ViewHolder{
        TextView tenSanPham,soLieuMoi,soLieuCu;
        ImageView delete_item;
        public anhXa(@NonNull View itemView) {
            super(itemView);
            tenSanPham=itemView.findViewById(R.id.tenSanPham);
            soLieuCu=itemView.findViewById(R.id.soLieuCu);
            soLieuMoi=itemView.findViewById(R.id.soLieuMoi);
            delete_item = itemView.findViewById(R.id.delete_item);
        }
    }
}
