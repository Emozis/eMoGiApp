package com.meta.emogi.views.menu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CharacterModel;

import java.util.List;
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.CharacterViewHolder> {
    private List<CharacterModel> characterList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;

    private static final String TAG = "MenuListAdapter";

    public MenuListAdapter(List<CharacterModel> characterList) {
        this.characterList = characterList;
    }

    public interface OnItemClickListener {
        void onItemClick(int characterId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu_chracter, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        CharacterModel character = characterList.get(position);
        holder.itemMenuCharacter.setSelected(position == selectedPosition);

        holder.characterName.setText(character.getCharacterName());
        holder.characterDescription.setText(character.getCharacterDetails());
        // ImageView에 이미지를 로드하는 코드를 추가

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(20)); // 반지름 설정

        Glide.with(holder.itemView.getContext()).load(character.getCharacterProfile()) // characterProfile은 이미지 URL
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
                .apply(requestOptions) // 둥근 모서리 적용
                .error(R.drawable.drawable_background_toolbar_profile) // 이미지 로드 실패 시 보여줄 이미지
                .into(holder.characterImage); // ImageView에 로드

        holder.itemMenuCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전에 선택된 아이템의 선택 상태를 해제
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition);
                }

                // 새로운 아이템을 선택하고 상태 업데이트
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);

                if (onItemClickListener != null) {
                    int clickedCharacterId = characterList.get(selectedPosition).getCharacterId();
                    onItemClickListener.onItemClick(clickedCharacterId);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemMenuCharacter;
        ImageView characterImage;
        TextView characterName;
        TextView characterDescription;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMenuCharacter = itemView.findViewById(R.id.item_menu_character);
            characterImage = itemView.findViewById(R.id.character_image);
            characterName = itemView.findViewById(R.id.character_name);
            characterDescription = itemView.findViewById(R.id.character_description);
        }
    }
}