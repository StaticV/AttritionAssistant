package com.github.staticv.attritionassistant.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.lifecycle.LifecycleOwner;

import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.ui.Resources;
import com.github.staticv.attritionassistant.databinding.CounterViewBinding;

public class CounterView extends LinearLayout {

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

        int color = context.getColor(R.color.black);
        String label = null;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CounterView);
            color = a.getColor(R.styleable.CounterView_counterColor, color);
            label = a.getString(R.styleable.CounterView_counterLabel);
            a.recycle();
        }

        binding.counterTextView.setTextColor(color);
        binding.counterLabelTextView.setTextColor(color);
        binding.decButton.setBackgroundTintList(ColorStateList.valueOf(color));
        binding.incButton.setBackgroundTintList(ColorStateList.valueOf(color));

        if (label != null) {
            binding.counterLabelTextView.setText(label);
        }

        binding.counterTextView.setText("0");
    }

    // This method is missing from your code. Add this to your CounterView class.
    public void setupWithViewModel(LifecycleOwner owner, Resources viewModel, String label) {
        // Set up the button click listeners to update the ViewModel
        binding.decButton.setOnClickListener(v -> viewModel.decrementCounter(label));
        binding.incButton.setOnClickListener(v -> viewModel.incrementCounter(label));

        // Observe changes to the LiveData for this specific counter
        viewModel.getCounter(label).observe(owner, count -> {
            binding.counterTextView.setText(String.valueOf(count));
        });
    }
}