package com.example.doandidong.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import com.example.doandidong.R;
import com.example.doandidong.function.CuaHangOnline.Data.Image;
import com.example.doandidong.function.CuaHangOnline.ThongTinCuaHangOnlineActivity;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private Context context;
    private ArrayList<Image> listImage;
    private String ID_CUAHANG;
    private ThongTinCuaHangOnlineActivity thongTinCuaHangOnlineActivity;

    public ImageAdapter(Context context, ArrayList<Image> list, String ID_CUAHANG, ThongTinCuaHangOnlineActivity thongTinCuaHangOnlineActivity) {
        this.context = context;
        this.listImage = list;
        this.ID_CUAHANG = ID_CUAHANG;
        this.thongTinCuaHangOnlineActivity = thongTinCuaHangOnlineActivity;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_imageview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Picasso.get().load(listImage.get(position).getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CardView btnDelete;
        public ImageHolder(@NonNull View ItemView) {
            super(ItemView);

            image = ItemView.findViewById(R.id.image);
            btnDelete = ItemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    thongTinCuaHangOnlineActivity.delete(position);
                }
            });
        }
    }
}
