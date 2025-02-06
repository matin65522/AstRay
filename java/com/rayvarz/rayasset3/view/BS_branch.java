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

public class BS_branch extends BottomSheetDialogFragment {

    private BottomSheetListener listener;

    public interface BottomSheetListener {
        void onOptionSelected(String option);
    }

    public void setBottomSheetListener(BottomSheetListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView option1 = view.findViewById(R.id.option1);
        TextView option2 = view.findViewById(R.id.option2);
        TextView option3 = view.findViewById(R.id.option3);

        option1.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected("سیمان شاهرود");
            dismiss();
        });

        option2.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected("سیمان سبزوار");
            dismiss();
        });

        option3.setOnClickListener(v -> {
            if (listener != null) listener.onOptionSelected(" شهرداری مشهد");
            dismiss();
        });
        return view;
    }
}
