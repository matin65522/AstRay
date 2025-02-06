package com.rayvarz.rayasset3.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rayvarz.rayasset3.adapter.RecAstItemAdapter;
import com.rayvarz.rayasset3.databinding.FragmentAstBinding;
import com.rayvarz.rayasset3.model.RecAstItem;
import com.rayvarz.rayasset3.viewmodel.AstViewModel;

import java.util.List;

public class AstFragment extends Fragment {
    private FragmentAstBinding binding;
    private RecAstItemAdapter recAstItemAdapter;
    private SelectionItemManager selectionItemManager;
    private AstViewModel astViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // مقداردهی ViewModel قبل از Binding
        astViewModel = new ViewModelProvider(requireActivity()).get(AstViewModel.class);

        // مقداردهی Binding
        binding = FragmentAstBinding.inflate(inflater, container, false);
        binding.setViewModel(astViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setupRecyclerView();
        setupObservers();
        setupSearchView();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // مقداردهی Adapter
        recAstItemAdapter = new RecAstItemAdapter(new RecAstItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecAstItem item) {
                Intent intent = new Intent(getActivity(), AstDscActivity.class);
                intent.putExtra("idNo", item.getAstBarcode());
                startActivity(intent);
            }

            @Override
            public void onSelectionChanged(List<String> selectedNames, int selectedItemCount) {
                handleSelectionState(selectedNames, selectedItemCount);
            }
        });

        recyclerView.setAdapter(recAstItemAdapter);

        // ADD SwipeToAction
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToActionCallback(requireContext(), recyclerView));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // scroll for paging
        setupRecyclerViewWithPaging();
    }

    private void setupObservers() {
        // مشاهده تغییرات در لیست دارایی‌ها
        astViewModel.getAstItemList().observe(getViewLifecycleOwner(), recAstItemAdapter::updateList);

        // مشاهده تغییرات در ناوبری به AstDscActivity
        astViewModel.navigateToAstDsc.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                startActivity(new Intent(getActivity(), AstDscActivity.class));
                astViewModel.resetNavigation();
            }
        });

        // بارگیری داده‌های اولیه
        astViewModel.fetchAstItemsAsync(0, 25, "");
    }

    private void setupSearchView() {
        binding.svAstItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                astViewModel.fetchAstItemsAsync(0, 25, newText.trim().isEmpty() ? "" : newText);
                return true;
            }
        });
    }


    private void handleSelectionState(List<String> selectedNames, int selectedItemCount) {
        TextView tvSelectedItems = binding.tvSelectedItems;

        if (!selectionItemManager.isMultiSelectMode) {
            binding.ibConfirmAst.setVisibility(View.GONE);
            binding.ibCancel.setVisibility(View.GONE);
            binding.ibAddAst.setVisibility(View.VISIBLE);
            binding.tvSelected.setVisibility(View.GONE);
            binding.tvTitle.setVisibility(View.VISIBLE);
        } else {
            binding.tvSelected.setVisibility(View.VISIBLE);
            binding.tvSelected.setText(selectedItemCount + " انتخاب ");
            binding.tvTitle.setVisibility(View.GONE);
            binding.ibConfirmAst.setVisibility(View.VISIBLE);
            binding.ibCancel.setVisibility(View.VISIBLE);
            binding.ibAddAst.setVisibility(View.GONE);
//      //    لیست ایتم های انتخاب شده
//            tvSelectedItems.setVisibility(View.VISIBLE);
//            tvSelectedItems.setText("l " + TextUtils.join("  ,   ", selectedNames));
        }
    }

    private void setupRecyclerViewWithPaging() {
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (layoutManager != null) {
                    int visibleThreshold = 5;
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    int totalItemCount = recAstItemAdapter.getItemCount();

                    if (!astViewModel.isLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        astViewModel.fetchAstItemsAsync(totalItemCount, 25, "");
                    }
                }
            }
        });
    }
}
