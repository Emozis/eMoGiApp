package com.meta.emogi.views.makecharacter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CharacterModel;

import java.util.ArrayList;
import java.util.List;

public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.RowViewHolder> {

    private final List<List<CharacterModel.CharacterRelationships>> groupedData = new ArrayList<>();
    private final List<CharacterModel.CharacterRelationships> selectedItems = new ArrayList<>();

    public RelationshipAdapter(List<CharacterModel.CharacterRelationships> relationshipModelList) {
        groupData(relationshipModelList);
    }

    // 데이터를 3개씩 묶어 그룹화
    private void groupData(List<CharacterModel.CharacterRelationships> relationshipModelList) {
        List<CharacterModel.CharacterRelationships> currentRow = new ArrayList<>();
        for (CharacterModel.CharacterRelationships item : relationshipModelList) {
            currentRow.add(item);
            if (currentRow.size() == 3) { // 3개씩 그룹화
                groupedData.add(new ArrayList<>(currentRow));
                currentRow.clear();
            }
        }
        if (!currentRow.isEmpty()) {
            groupedData.add(currentRow); // 남은 데이터 추가
        }
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_make_character, parent, false);

        int screenHeight = MyApplication.getDeviceHeightPx();
        int itemHeight = (int) (screenHeight * 0.1f);

        RecyclerView.LayoutParams params =
                new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, itemHeight);

        view.setLayoutParams(params);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        List<CharacterModel.CharacterRelationships> rowData = groupedData.get(position);
        holder.bind(rowData);
    }

    @Override
    public int getItemCount() {
        return groupedData.size();
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        private final List<TextView> categoryViews = new ArrayList<>();

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryViews.add(itemView.findViewById(R.id.categoryView1));
            categoryViews.add(itemView.findViewById(R.id.categoryView2));
            categoryViews.add(itemView.findViewById(R.id.categoryView3));
        }

        public void bind(List<CharacterModel.CharacterRelationships> rowData) {
            for (int i = 0; i < categoryViews.size(); i++) {
                if (i < rowData.size()) {
                    CharacterModel.CharacterRelationships item = rowData.get(i);
                    categoryViews.get(i).setVisibility(View.VISIBLE);
                    categoryViews.get(i).setText(item.getRelationshipName());
                    categoryViews.get(i).setSelected(isSelected(item));

                    // 클릭 리스너 설정
                    categoryViews.get(i).setOnClickListener(v -> {
                        if (isSelected(item)) {
                            removeById(item); // 선택 해제
                        } else if (selectedItems.size() < 3) {
                            selectedItems.add(item); // 최대 3개 선택 가능
                        }

                        notifyDataSetChanged();
                        Log.d("www", item.getRelationshipName());
                    });
                } else {
                    categoryViews.get(i).setVisibility(View.INVISIBLE); // 빈 셀 숨김
                }
            }
        }
    }

    private boolean isSelected(CharacterModel.CharacterRelationships item) {
        for (CharacterModel.CharacterRelationships selected : selectedItems) {
            if (selected.getRelationshipId() == item.getRelationshipId()) {
                return true;
            }
        }
        return false;
    }

    private void removeById(CharacterModel.CharacterRelationships item) {
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i).getRelationshipId() == item.getRelationshipId()) {
                selectedItems.remove(i);
                return;
            }
        }
    }

    public List<CharacterModel.CharacterRelationships> getSelectedRelationIds() {
        return selectedItems;
    }

    public void setSelectedItems(List<CharacterModel.CharacterRelationships> selectedList) {
        selectedItems.clear(); // 기존 선택 항목 초기화
        selectedItems.addAll(selectedList); // 새 선택 항목 추가
        notifyDataSetChanged(); // UI 업데이트
    }
}