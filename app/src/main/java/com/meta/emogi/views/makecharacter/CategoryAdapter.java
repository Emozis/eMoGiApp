package com.meta.emogi.views.makecharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.CategoryItem;

import java.util.List;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryItem> categoryItemList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CategoryAdapter(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_make_character, parent, false);
        return new CategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem categoryItem = categoryItemList.get(position);
        holder.textView.setText(categoryItem.getCategoryName());


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

    public String getSelectedCategoryText() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return categoryItemList.get(selectedPosition).getCategoryName();
        }
        return null; // 선택된 아이템이 없는 경우 null 반환
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.categoryView);
        }
    }
}
