package com.meta.emogi.views.makecharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.RelationshipModel;

import java.util.List;
public class RelationshipAdapter extends RecyclerView.Adapter<RelationshipAdapter.CategoryViewHolder> {

    private List<RelationshipModel> relationshipModelList;
    private int selectedPosition = RecyclerView.NO_POSITION;

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
        holder.itemView.setSelected(position == selectedPosition);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public int getSelectedRelationId() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return relationshipModelList.get(selectedPosition).getRelationshipId();
        }
        return -1; // 선택된 아이템이 없는 경우 null 반환
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
