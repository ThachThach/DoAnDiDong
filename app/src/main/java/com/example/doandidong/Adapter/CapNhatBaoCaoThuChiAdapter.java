package com.example.doandidong.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.ThuChi;
import com.example.doandidong.R;
import java.util.List;

public class CapNhatBaoCaoThuChiAdapter extends RecyclerView.Adapter<CapNhatBaoCaoThuChiAdapter.anhXa> {

    private Context context;
    private List<ThuChi> list;

    public CapNhatBaoCaoThuChiAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThuChi> lists) {
        this.list = lists;
        Log.d("triet1", "setData: "+lists.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public anhXa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new anhXa(LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_thu_chi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull anhXa holder, int position) {
        ThuChi thuChi=list.get(position);
        holder.ngayThang.setText(thuChi.getNgayThang());
        holder.lydo.setText(thuChi.getLydo());
        holder.tongchi.setText(thuChi.getTongchi()+"");

    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class anhXa extends RecyclerView.ViewHolder{
        TextView ngayThang,lydo,tongchi;
        public anhXa(@NonNull View itemView) {
            super(itemView);
            ngayThang=itemView.findViewById(R.id.ngayThangNam);
            lydo=itemView.findViewById(R.id.lyDo);
            tongchi=itemView.findViewById(R.id.tongChi);
        }
    }
}
