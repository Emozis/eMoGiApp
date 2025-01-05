package com.meta.emogi.views.makecharacter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CharacterModel;
import com.meta.emogi.network.datamodels.RelationshipModel;

import java.util.ArrayList;
import java.util.List;
public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.CategoryViewHolder> {

    private List<CharacterModel.CharacterRelationships> relationshipModelList;
    private final List<CharacterModel.CharacterRelationships> selectedItems = new ArrayList<>();

    public RelationshipAdapter(List<CharacterModel.CharacterRelationships> relationshipModelList) {
        this.relationshipModelList = relationshipModelList;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_make_character, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CharacterModel.CharacterRelationships relationshipModel = relationshipModelList.get(position);
        holder.textView.setText(relationshipModel.getRelationshipName());

        holder.itemView.setSelected(selectedItems.contains(relationshipModel));

        boolean isSelected = isItemSelected(relationshipModel);
        holder.itemView.setSelected(isSelected);

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION)
                return;

            CharacterModel.CharacterRelationships selectedItem = findSelectedItemById(relationshipModel.getRelationshipId());
            // 선택된 상태라면 선택 해제
            if (selectedItem != null) {
                // 이미 선택된 아이템이면 제거
                selectedItems.remove(selectedItem);
            } else {
                // 선택되지 않은 아이템이고, 3개 미만이면 추가
                if (selectedItems.size() < 3) {
                    selectedItems.add(relationshipModel);
                }
            }
            notifyDataSetChanged();
        });
    }

    private CharacterModel.CharacterRelationships findSelectedItemById(int relationshipId) {
        for (CharacterModel.CharacterRelationships item : selectedItems) {
            if (item.getRelationshipId() == relationshipId) {
                return item;
            }
        }
        return null;
    }



    private boolean isItemSelected(CharacterModel.CharacterRelationships relationship) {
        for (CharacterModel.CharacterRelationships selected : selectedItems) {
            if (selected.getRelationshipId() == relationship.getRelationshipId()) {
                return true;
            }
        }
        return false;
    }

    public List<CharacterModel.CharacterRelationships> getSelectedRelationIds() {
        return selectedItems;
    }

    public void setSelectedItems(List<CharacterModel.CharacterRelationships> selectedList) {
        selectedItems.clear(); // 기존 선택 항목 초기화
        selectedItems.addAll(selectedList); // 새 선택 항목
        notifyDataSetChanged(); // UI 업데이트
    }

    @Override
    public int getItemCount() {
        return relationshipModelList.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.categoryView);
        }
    }
}
