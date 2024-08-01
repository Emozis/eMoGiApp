package com.meta.emogi.views.menu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CharacterModel;

import java.util.List;
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.CharacterViewHolder> {
    private List<CharacterModel> characterList;

    public MenuListAdapter(List<CharacterModel> characterList) {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu_chacter, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        CharacterModel character = characterList.get(position);
        holder.characterName.setText(character.getCharacterName());
        holder.characterDescription.setText(character.getCharacterDetails());
        // ImageView에 이미지를 로드하는 코드를 추가
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage;
        TextView characterName;
        TextView characterDescription;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.character_image);
            characterName = itemView.findViewById(R.id.character_name);
            characterDescription = itemView.findViewById(R.id.character_description);
        }
    }
}