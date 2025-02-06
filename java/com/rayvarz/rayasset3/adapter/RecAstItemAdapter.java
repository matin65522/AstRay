package com.rayvarz.rayasset3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rayvarz.rayasset3.R;
import com.rayvarz.rayasset3.model.RecAstItem;
import com.rayvarz.rayasset3.view.SelectionItemManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecAstItemAdapter extends RecyclerView.Adapter<RecAstItemAdapter.RecAstItemViewHolder> {
    private List<RecAstItem> recAstItemList;
    private OnItemClickListener listener;
    private SelectionItemManager<String> selectionManager;

    public RecAstItemAdapter(OnItemClickListener listener) {
        this.recAstItemList = new ArrayList<>();
        this.listener = listener;
        this.selectionManager = new SelectionItemManager<>(selectedItems -> {
            List<String> selectedNames = new ArrayList<>();
            for (RecAstItem item : recAstItemList) {
                if (selectedItems.contains(item.getAstBarcode())) {
                    selectedNames.add(item.getAstTitle());
                }
            }
            if (listener != null) {
                listener.onSelectionChanged(selectedNames, selectedItems.size());
            }
            notifyDataSetChanged();
        });
    }

    public interface OnItemClickListener {
        void onItemClick(RecAstItem item);
        void onSelectionChanged(List<String> selectedNames, int selectedItemCount);
    }

    @NonNull
    @Override
    public RecAstItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_asset_item, parent, false);
        return new RecAstItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAstItemViewHolder holder, int position) {
        RecAstItem recAstItem = recAstItemList.get(position);
        holder.tv_AstTitle.setText(recAstItem.getAstTitle());
        holder.tv_AstBarcode.setText(recAstItem.getAstImg());
        holder.tv_AstLocation.setText(recAstItem.getAstLocation());

        // تغییر رنگ آیتم بر اساس وضعیت انتخاب‌شده
        if (selectionManager.isSelected(recAstItem.getAstBarcode())) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.selected));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectionManager.isMultiSelectMode()) {
                selectionManager.toggleSelection(recAstItem.getAstBarcode());
            } else if (listener != null) {
                listener.onItemClick(recAstItem);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (!selectionManager.isMultiSelectMode()) {
                selectionManager.toggleSelection(recAstItem.getAstBarcode());
            }
            return true;
        });

        // تنظیم آیکون بر اساس دسته‌بندی
        switch (recAstItem.getAstClassify()) {
            case 10: holder.iv_AstImg.setImageResource(R.drawable.plot); break;
            case 12: holder.iv_AstImg.setImageResource(R.drawable.building); break;
            case 13: holder.iv_AstImg.setImageResource(R.drawable.pipeline); break;
            case 14: holder.iv_AstImg.setImageResource(R.drawable.tools); break;
            case 17: holder.iv_AstImg.setImageResource(R.drawable.machinery); break;
            case 18: holder.iv_AstImg.setImageResource(R.drawable.decoration); break;
            default: holder.iv_AstImg.setImageResource(R.drawable.preload);
        }
    }

    public void updateList(List<RecAstItem> newList) {
        this.recAstItemList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recAstItemList.size();
    }

    static class RecAstItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_AstTitle, tv_AstBarcode, tv_AstLocation;
        ImageView iv_AstImg;

        public RecAstItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_AstTitle = itemView.findViewById(R.id.tv_AstTitle);
            tv_AstBarcode = itemView.findViewById(R.id.tv_AstBarcode);
            tv_AstLocation = itemView.findViewById(R.id.tv_AstLocation);
            iv_AstImg = itemView.findViewById(R.id.iv_AstImg);
        }
    }
}
