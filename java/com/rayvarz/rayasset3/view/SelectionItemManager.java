package com.rayvarz.rayasset3.view;

import java.util.HashSet;
import java.util.Set;

public class SelectionItemManager<T> {
    public Set<T> selectedItems = new HashSet<>();
    public static boolean isMultiSelectMode = false;
    private OnSelectionChangeListener<T> listener;

    public interface OnSelectionChangeListener<T> {
        void onSelectionChanged(Set<T> selectedItems);
    }

    public SelectionItemManager(OnSelectionChangeListener<T> listener) {
        this.listener = listener;
    }

    public void toggleSelection(T itemId) {
        if (selectedItems.contains(itemId)) {
            selectedItems.remove(itemId);
        } else {
            selectedItems.add(itemId);
        }

        isMultiSelectMode = !selectedItems.isEmpty();

        if (listener != null) {
            listener.onSelectionChanged(selectedItems);
        }
    }

    public boolean isSelected(T itemId) {
        return selectedItems.contains(itemId);
    }

    public boolean isMultiSelectMode() {
        return isMultiSelectMode;
    }

    public void clearSelection() {
        selectedItems.clear();
        isMultiSelectMode = false;

        if (listener != null) {
            listener.onSelectionChanged(selectedItems);
        }
    }
}