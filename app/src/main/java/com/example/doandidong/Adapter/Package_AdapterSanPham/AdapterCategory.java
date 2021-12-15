package com.example.doandidong.Adapter.Package_AdapterSanPham;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doandidong.Model.SanPham.Category;
import com.example.doandidong.R;
import com.example.doandidong.function.SanPham.ListCategory;
import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryAdater> {

    private ArrayList<Category> arrayList;
    private ListCategory context;

    public AdapterCategory(ListCategory context,ArrayList<Category> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdater onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        CategoryAdater categoryAdater = new CategoryAdater(view);
        return categoryAdater;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdater holder, int position) {
        Category category = arrayList.get(position);
        String name = category.getNameCategory().toUpperCase();
        char nam =name.charAt(0) ;
        holder.nameCategory1.setText(nam+"");
        holder.nameCategory.setText(category.getNameCategory());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CategoryAdater extends RecyclerView.ViewHolder  {
        private TextView nameCategory;
        private TextView nameCategory1;
        private ImageView imgXoa;
        private  ImageView imgSua;

        public CategoryAdater(View itemView) {
            super(itemView);
            nameCategory = itemView.findViewById(R.id.text_tenNSP);
            nameCategory1 = (TextView) itemView.findViewById(R.id.text_name);
            imgSua = itemView.findViewById(R.id.btnSuaNSP);
            imgXoa = itemView.findViewById(R.id.btnXoaNSP);

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.update(position, Gravity.CENTER);
                }
            });

        }


    }
}
