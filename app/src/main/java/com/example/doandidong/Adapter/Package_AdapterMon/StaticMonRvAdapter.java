package com.example.doandidong.Adapter.Package_AdapterMon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.example.doandidong.Adapter.Pakage_AdapterDanhMuc_Mon.StaticCategoryMonModel;
import com.example.doandidong.ChucNang.ChiTietOrder.ChiTietSanPham;
import com.example.doandidong.Data.Product;
import com.example.doandidong.R;
import com.example.doandidong.ChucNang.MonOrder;
import java.util.ArrayList;

public class StaticMonRvAdapter  extends RecyclerView.Adapter<StaticMonRvAdapter.StaticMonRvViewHolder>{
    ArrayList<Product> staticMonOrderModels;
    public MonOrder monOrder;
//    public IclickGetMon iclickGetMon;

//    public interface IclickGetMon{
//        void clickItent(Product product );
//    }
//
//    public void setData(IclickGetMon iclickGetMon){
//        this.iclickGetMon = iclickGetMon;
//    }

    ArrayList<StaticCategoryMonModel> items;
    int pos;

    String id_khuvuc;
    String id_ban;
    String id_datban;
    String tenban;
    String key_SanPham;
    String trangthai;

    public StaticMonRvAdapter(ArrayList<Product> staticMonOrderModels, MonOrder monOrder, ArrayList<StaticCategoryMonModel> items, int pos, String tenban,String id_ban,String id_khuvuc,String key_SanPham,String id_datban, String trangthai){
        this.staticMonOrderModels = staticMonOrderModels;
        this.monOrder = monOrder;
        this.items = items;
        this.pos = pos;
        this.tenban = tenban;
        this.id_ban = id_ban;
        this.id_khuvuc = id_khuvuc;
        this.key_SanPham = key_SanPham;
        this.id_datban = id_datban;
        this.trangthai= trangthai;


    }

    @NonNull
    @Override
    public StaticMonRvAdapter.StaticMonRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);

        StaticMonRvAdapter.StaticMonRvViewHolder staticMonRvViewHolder = new StaticMonRvAdapter.StaticMonRvViewHolder(view);

        return staticMonRvViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StaticMonRvAdapter.StaticMonRvViewHolder holder, int position) {
        Product Crritem = staticMonOrderModels.get(position);
        if(Crritem==null){
            return;
        }
        holder.tvtensanpham.setText(Crritem.getNameProduct());
        Picasso.get().load(Crritem.getImgProduct()).into(holder.imgsanpham);
        holder.tvstatus.setText(Crritem.getStatus());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(monOrder, ChiTietSanPham.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sp",Crritem);
                intent1.putExtras(bundle);
                Log.d("aaa",id_ban+"baba1");
                intent1.putExtra("tenban",tenban);
                intent1.putExtra("id_ban",id_ban);
                intent1.putExtra("id_khuvuc",id_khuvuc);
                intent1.putExtra("key_sanpham",key_SanPham);
                intent1.putExtra("id_datban",id_datban);
//                    intent1.putExtra("trangthai",trangthai);

//                    Log.d("key_sanpham113",Crritem.getDonGia().get(position).getId() );
                monOrder.startActivity(intent1);

            }
        });

    }

    @Override
    public int getItemCount() {

        if (staticMonOrderModels!=null){
            return
                    staticMonOrderModels.size() ;
        }
        return 0;
    }


    public class StaticMonRvViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgsanpham;
        public TextView tvtensanpham;
        //        public TextView tvgiaSanpham;
        public  TextView tvstatus;
        ConstraintLayout constraintLayout;
        public StaticMonRvViewHolder(@NonNull View itemView) {

            super(itemView);
            imgsanpham = itemView.findViewById(R.id.imgsanpham);
            tvtensanpham = itemView.findViewById(R.id.tvtensanpham);
//            tvgiaSanpham = itemView.findViewById(R.id.tvgiasanpham);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            constraintLayout = itemView.findViewById(R.id.constraintLayouts);

        }
    }
}
