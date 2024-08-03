package com.meta.emogi.views.makecharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ImageModel;

import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<ImageModel> imageModelList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ImageAdapter(List<ImageModel> imageModelList) {
        this.imageModelList = imageModelList;
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
        ImageModel imageModel = imageModelList.get(position);
        holder.imageView.setSelected(position == selectedPosition);

//        // Glide를 사용하여 이미지를 로드합니다.
//        Glide.with(holder.imageView.getContext())
//                .load(imageModel.getImageResId())  // 리소스 ID를 사용
//                .into(holder.imageView);

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(20)); // 반지름 설정

        Glide.with(holder.itemView.getContext())
                .load(imageModel.getImageUrl()) // characterProfile은 이미지 URL
                .apply(requestOptions) // 둥근 모서리 적용
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
                .error(R.drawable.drawable_background_toolbar_profile) // 이미지 로드 실패 시 보여줄 이미지
                .into(holder.imageView); // ImageView에 로드


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전에 선택된 아이템의 선택 상태를 해제
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition);
                }

                // 새로운 아이템을 선택하고 상태 업데이트
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            }
        });
    }
    @Override
    public int getItemCount() {
        return imageModelList.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
