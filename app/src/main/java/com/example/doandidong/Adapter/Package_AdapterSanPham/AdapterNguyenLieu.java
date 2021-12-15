package com.example.doandidong.Adapter.Package_AdapterSanPham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doandidong.Data.NguyenLieu;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.SanPham.DanhSachNguyenLieuActivity;
import java.util.ArrayList;

public class AdapterNguyenLieu extends RecyclerView.Adapter<AdapterNguyenLieu.NguyenLieuHolder> {
    private ArrayList<NguyenLieu> arrayList;
    private DanhSachNguyenLieuActivity context;
    private Context context2;

    public AdapterNguyenLieu(Context context2, DanhSachNguyenLieuActivity context, ArrayList<NguyenLieu> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 =context2;
    }

    @NonNull
    @Override
    public NguyenLieuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsach_nguyenlieu, parent, false);
        NguyenLieuHolder viewHolder = new NguyenLieuHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NguyenLieuHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getTen());
        holder.soluong.setText(arrayList.get(position).getSoluong() + " " + arrayList.get(position).getDonvi());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NguyenLieuHolder extends RecyclerView.ViewHolder {

        private TextView name, soluong;
        private ImageView btnDelete;
        private LinearLayout item;

        public NguyenLieuHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            soluong = itemView.findViewById(R.id.soluong);
            btnDelete= itemView.findViewById(R.id.btnDelete);
            item = itemView.findViewById(R.id.item);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position, item);
                }
            });

        }
    }
}
