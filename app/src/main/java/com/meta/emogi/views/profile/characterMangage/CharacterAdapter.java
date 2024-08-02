package com.meta.emogi.views.profile.characterMangage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.recyclerview.CategoryItem;
import com.meta.emogi.network.recyclerview.CharacterItem;
import com.meta.emogi.views.makecharacter.CategoryAdapter;

import java.util.List;
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<CharacterModel> characterList;

    public CharacterAdapter(List<CharacterModel> characterList) {
        this.characterList = characterList;
    }

    // 새로운 데이터를 추가하는 메서드
    public void addCharacterItem(CharacterModel newItem) {
        characterList.add(newItem);
        notifyItemInserted(characterList.size() - 1); // 새 아이템이 추가된 위치를 알려줍니다.
    }

    // 특정 위치에 데이터를 추가하는 메서드
    public void addCharacterItem(int position, CharacterModel newItem) {
        characterList.add(position, newItem);
        notifyItemInserted(position); // 새 아이템이 추가된 위치를 알려줍니다.
    }

    // 특정 아이템을 제거하는 메서드
    public void removeCharacterItem(int position) {
        if (position < characterList.size() && position >= 0) {
            characterList.remove(position);
            notifyItemRemoved(position); // 아이템이 제거된 위치를 알려줍니다.
        }
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_page_character_list, parent, false);
        return new CharacterAdapter.CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CharacterViewHolder holder, int position) {

        CharacterModel characterItem = characterList.get(position);

//        Glide.with(holder.characterImageView.getContext())
//                .load(characterItem.getImageResId())  // 리소스 ID를 사용
//                .into(holder.characterImageView);
        
        holder.characterDescriptionView.setText(characterItem.getCharacterDetails());
        holder.characterNameView.setText(characterItem.getCharacterName());

    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImageView;
        TextView characterNameView;
        TextView characterDescriptionView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImageView = itemView.findViewById(R.id.image_character);
            characterNameView = itemView.findViewById(R.id.name_character);
            characterDescriptionView = itemView.findViewById(R.id.description_character);
        }
    }
}
