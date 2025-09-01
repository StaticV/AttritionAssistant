package com.github.staticv.attritionassistant.ui.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.databinding.CounterViewBinding;

public class CounterView extends LinearLayout {

    private int counter = 0;
    private CounterViewBinding binding;

    public CounterView(Context context) {
        super(context);
        init(context, null);
    }

    public CounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        binding = CounterViewBinding.inflate(LayoutInflater.from(context), this, true);

        // Read the custom attribute, with a default color
        int color = context.getColor(R.color.black);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CounterView);
            color = a.getColor(R.styleable.CounterView_counterColor, color);
            a.recycle();
        }

        // Apply the color to both the TextView and the Buttons
        binding.counterTextView.setTextColor(color);
        binding.decButton.setBackgroundTintList(ColorStateList.valueOf(color));
        binding.incButton.setBackgroundTintList(ColorStateList.valueOf(color));

        // Button handlers (no changes)
        binding.counterTextView.setText(String.valueOf(counter));
        binding.decButton.setOnClickListener(v -> {
            counter--;
            binding.counterTextView.setText(String.valueOf(counter));
        });
        binding.incButton.setOnClickListener(v -> {
            counter++;
            binding.counterTextView.setText(String.valueOf(counter));
        });
    }
}