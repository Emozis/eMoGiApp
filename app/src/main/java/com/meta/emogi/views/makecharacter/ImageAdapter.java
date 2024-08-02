package com.meta.emogi.views.makecharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.network.recyclerview.ImageItem;

import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<ImageItem> imageItemList;

    public ImageAdapter(List<ImageItem> imageItemList) {
        this.imageItemList = imageItemList;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_make_character, parent, false);
        return new ImageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageItem imageItem = imageItemList.get(position);

        // Glide를 사용하여 이미지를 로드합니다.
        Glide.with(holder.imageView.getContext())
                .load(imageItem.getImageResId())  // 리소스 ID를 사용
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return imageItemList.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
