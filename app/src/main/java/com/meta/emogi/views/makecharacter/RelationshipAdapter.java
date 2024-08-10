package com.meta.emogi.views.makecharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.RelationshipModel;

import java.util.ArrayList;
import java.util.List;
public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.CategoryViewHolder> {

    private List<RelationshipModel> relationshipModelList;
    private final List<RelationshipModel> selectedItems = new ArrayList<>();

    public RelationshipAdapter(List<RelationshipModel> relationshipModelList) {
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
        RelationshipModel relationshipModel = relationshipModelList.get(position);
        holder.textView.setText(relationshipModel.getRelationshipName());
        holder.itemView.setSelected(selectedItems.contains(relationshipModel));
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION)
                return;

            // 선택된 상태라면 선택 해제
            if (selectedItems.contains(relationshipModel)) {
                selectedItems.remove(relationshipModel);
            } else {
                // 선택된 항목이 3개 미만일 때만 추가
                if (selectedItems.size() < 3) {
                    selectedItems.add(relationshipModel);
                }
            }
            notifyDataSetChanged();
        });
    }

    public List<Integer> getSelectedRelationIds() {
        List<Integer> selectedIds = new ArrayList<>();
        for (RelationshipModel model : selectedItems) {
            selectedIds.add(model.getRelationshipId());
        }
        return selectedIds;
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
