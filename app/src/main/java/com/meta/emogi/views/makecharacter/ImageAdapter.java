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
import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.data.network.model.CharacterImageResponse;

import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<CharacterImageResponse> characterImageResponseList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ImageAdapter(List<CharacterImageResponse> characterImageResponseList) {
        this.characterImageResponseList = characterImageResponseList;
    }

    public String getSelectedImageUrl() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return characterImageResponseList.get(selectedPosition).getImageUrl();
        }
        return null;
    }

    public void setSelectedImageUrl(String imageUrl) {
        if (imageUrl != null) {
            for (int i = 0; i < characterImageResponseList.size(); i++) {
                if (characterImageResponseList.get(i).getImageUrl().equals(imageUrl)) {
                    selectedPosition = i;
                    notifyItemChanged(selectedPosition);
                    break;
                }
            }
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_make_character, parent, false);

        int screenWidth = MyApplication.getDeviceWidthPx();
        int itemWidth = (int) (screenWidth * 0.25f);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                itemWidth,
                RecyclerView.LayoutParams.MATCH_PARENT
        );

        view.setLayoutParams(params);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        CharacterImageResponse characterImageResponse = characterImageResponseList.get(position);

        boolean isSelected = position == selectedPosition;
        holder.imageView.setSelected(isSelected);

        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(20)); // 둥근 모서리 설정

        Glide.with(holder.itemView.getContext()).load(characterImageResponse.getImageUrl()) // 이미지 URL 로드
                .apply(requestOptions) // 둥근 모서리 적용
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 플레이스홀더 이미지
                .error(R.drawable.drawable_background_toolbar_profile) // 오류 발생 시 대체 이미지
                .into(holder.imageView); // ImageView에 로드

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전에 선택된 아이템의 선택 상태 해제
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
        return characterImageResponseList.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
