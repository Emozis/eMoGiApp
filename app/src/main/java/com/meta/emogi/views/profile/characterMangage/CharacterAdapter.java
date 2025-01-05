package com.meta.emogi.views.profile.characterMangage;
import android.app.AlertDialog;
import android.util.Log;
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
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<CharacterModel> characterList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;
    private boolean deleteMode = false;

    public CharacterAdapter(List<CharacterModel> characterList) {
        this.characterList = characterList;
    }

    public void setDeleteMode(boolean deleteMode){
        this.deleteMode = deleteMode;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int characterId,int type); // 1 캐릭터 상세, 2 캐릭터 삭제 , 3 캐릭터 수정
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_page_character_list, parent, false);
        return new CharacterAdapter.CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        CharacterModel characterItem = characterList.get(position);
        holder.myPageCharacterListLayout.setSelected(position == selectedPosition);

        if(deleteMode){
            holder.modifyCharacter.setText("삭제");
        }else{
            holder.modifyCharacter.setText("수정");
        }

        String description="";
        description += characterItem.getCharacterGender().equals("male") ? "남자" : "여자";
        description += " / "+characterItem.getCharacterPersonality()+" / ";
        List<CharacterModel.CharacterRelationships> realationships = characterItem.getCharacterRelationships();
        for(CharacterModel.CharacterRelationships realationship :realationships){
            description+=realationship.getRelationshipName()+" / ";
        }
        description+=characterItem.getCharacterDetails();

        if (description.endsWith("/")) {
            description = description.substring(0, description.length() - 1);
        }

        holder.characterDescriptionView.setText(description);

        holder.characterNameView.setText(characterItem.getCharacterName());

        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(12)); // 반지름 설정
        
        Glide.with(holder.itemView.getContext()).load(characterItem.getCharacterProfile()) // characterProfile은 이미지 URL
                .apply(requestOptions) // 둥근 모서리 적용
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
                .error(R.drawable.drawable_background_toolbar_profile) // 이미지 로드 실패 시 보여줄 이미지
                .into(holder.characterImageView); // ImageView에 로드

        holder.myPageCharacterListLayout.setOnClickListener(new View.OnClickListener() {
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
                    onItemClickListener.onItemClick(clickedCharacterId,1);
                }
            }
        });

        holder.modifyCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog 생성
                if(deleteMode){
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("캐릭터 삭제")
                            .setMessage("정말로 삭제하시겠습니까?")
                            .setPositiveButton("삭제", (dialog, which) -> {
                                // 이전에 선택된 아이템의 선택 상태를 해제
                                if (selectedPosition != RecyclerView.NO_POSITION) {
                                    notifyItemChanged(selectedPosition);
                                }

                                // 새로운 아이템을 선택하고 상태 업데이트
                                selectedPosition = holder.getAdapterPosition();
                                notifyItemChanged(selectedPosition);

                                if (onItemClickListener != null) {
                                    int clickedCharacterId = characterList.get(selectedPosition).getCharacterId();
                                    onItemClickListener.onItemClick(clickedCharacterId, 2);
                                }
                            })
                            .setNegativeButton("취소", (dialog, which) -> dialog.dismiss()) // 취소 버튼 동작
                            .show();
                }else{
                    if (selectedPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(selectedPosition);
                    }

                    // 새로운 아이템을 선택하고 상태 업데이트
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(selectedPosition);


                    if (onItemClickListener != null) {
                        int clickedCharacterId = characterList.get(selectedPosition).getCharacterId();
                        onItemClickListener.onItemClick(clickedCharacterId, 3);
                    }
                }

            }
        });
    }

    public void updateCharacterList(List<CharacterModel> updatedList) {
        // 최신순 정렬 (Character ID가 클수록 최신)
        updatedList.sort((c1, c2) -> Integer.compare(c2.getCharacterId(), c1.getCharacterId()));

        // 리스트 업데이트
        this.characterList = updatedList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout myPageCharacterListLayout;
        ImageView characterImageView;
        TextView characterNameView;
        TextView characterDescriptionView;
        TextView modifyCharacter;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            myPageCharacterListLayout = itemView.findViewById(R.id.my_page_character_list_layout);
            characterImageView = itemView.findViewById(R.id.image_character);
            characterNameView = itemView.findViewById(R.id.name_character);
            characterDescriptionView = itemView.findViewById(R.id.description_character);
            modifyCharacter = itemView.findViewById(R.id.modify_character);


        }
    }
}
