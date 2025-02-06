package com.rayvarz.rayasset3.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rayvarz.rayasset3.R;

public class BS_AstCategory extends BottomSheetDialogFragment {
    private BottomSheetListener listener;

    public interface BottomSheetListener {
        void onOptionSelected(String option);
    }

    public void setBottomSheetListener(BS_AstCategory.BottomSheetListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_category, container, false);

        TextView option1 = view.findViewById(R.id.option1);
        TextView option2 = view.findViewById(R.id.option2);
        TextView option3 = view.findViewById(R.id.option3);

        option1.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected("وسائط نقلیه");
            dismiss();
        });

        option2.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected("زمین و ساختمان");
            dismiss();
        });

        option3.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected("تجهیزات اداری");
            dismiss();
        });
        return view;
    }
}
