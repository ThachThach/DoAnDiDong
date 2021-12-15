package com.example.doandidong.Adapter.Package_ApdaterLoaiGia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Model.ChucNangThanhToan.DonGia;
import com.example.doandidong.R;
import java.util.ArrayList;

public class AdapterLoaiGia extends RecyclerView.Adapter<AdapterLoaiGia.DonGiaHodel> {
    private ArrayList<DonGia> items;
    private Double numcheck;
    TextView tonggiasp;
    int select;
    TextView soluong2;
    String Loai;
    Double gia;
    int position;

    public AdapterLoaiGia(ArrayList<DonGia> donGiaOrders, Double numcheck , TextView tonggiap, TextView soluong2, String Loai, Double gia, int position) {
        this.items= donGiaOrders;
        this.numcheck = numcheck;
        this.tonggiasp = tonggiap;
        this.select=0;
        this.soluong2 =soluong2;
        this.Loai = Loai;
        this.gia= gia;
        this.position = position;
    }



    @NonNull
    @Override
    public DonGiaHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisanpham,parent,false);

        DonGiaHodel staticMonRvViewHolder = new DonGiaHodel(view);

        return staticMonRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonGiaHodel holder, int position) {
        DonGia Crritem = items.get(position);

        holder.tvloaisanpham.setText(Crritem.getTenDonGia());
        holder.tvgialoaisanpham.setText(Crritem.getGiaBan()+"");

        if(select==position){
            holder.checkBox.setChecked(true);
            items.get(position).setCheck(true);
//            Loai= items.get(position).getTenDonGia();
//            Log.d("LoaiKKK",Loai);
            items.get(position).setTenLoaiChung(items.get(position).getTenDonGia());
            gia=items.get(position).getGiaBan();
//            Log.d("LoaiKKK",gia+"");
            tonggiasp.setText(items.get(select).getGiaBan()+"");
            items.get(position).setGiachung(items.get(position).getGiaBan());
        }
        else {

            items.get(position).setCheck(false);
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    soluong2.setText("1");

                  select=position;
                    notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (items.size()==0){
               return 0;
        }
        return items.size();
    }

    public class DonGiaHodel extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvloaisanpham;
        TextView tvgialoaisanpham;

        public DonGiaHodel(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvloaisanpham= itemView.findViewById(R.id.tvloaisanpham);
            tvgialoaisanpham=itemView.findViewById(R.id.tvgialoaisanpham);

        }
    }
}
