package com.github.staticv.attritionassistant.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
// Removed: import android.view.View; // Unused
// Removed: import androidx.lifecycle.Observer; // Unused

import androidx.lifecycle.LifecycleOwner;

import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.databinding.CounterViewBinding;

public class CounterView extends LinearLayout {

    private CounterViewBinding binding;
    private int originalButtonColor;

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
            // FIX: Using try-with-resources to ensure TypedArray is recycled,
            // addressing the warning.
            try (TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CounterView)) {
                color = a.getColor(R.styleable.CounterView_counterColor, color);
                label = a.getString(R.styleable.CounterView_counterLabel);
            }
            // The a.recycle() call is now implicit via the try-with-resources block
        }

        this.originalButtonColor = color;

        binding.counterTextView.setTextColor(color);
        binding.counterLabelTextView.setTextColor(color);

        // Use the saved color for initial setup
        binding.decButton.setBackgroundTintList(ColorStateList.valueOf(this.originalButtonColor));
        binding.incButton.setBackgroundTintList(ColorStateList.valueOf(this.originalButtonColor));

        if (label != null) {
            binding.counterLabelTextView.setText(label);
        }

        binding.counterTextView.setText("0");
    }

    /**
     * Initializes the CounterView to interact with the ViewModel.
     * @param owner The LifecycleOwner for observing LiveData.
     * @param viewModel The shared Resources ViewModel.
     * @param label The specific key/label for this counter in the ViewModel.
     */
    public void setupWithViewModel(LifecycleOwner owner, Resources viewModel, String label) {
        // Set up the button click listeners
        binding.decButton.setOnClickListener(v -> viewModel.decrementCounter(label));
        binding.incButton.setOnClickListener(v -> viewModel.incrementCounter(label));

        // Observe changes to the LiveData for this specific counter
        viewModel.getCounter(label).observe(owner, count -> {
            binding.counterTextView.setText(String.valueOf(count));

            // Disable the decrement button if the count is 0
            if (count != null && count <= 0) {
                binding.decButton.setEnabled(false);
                // Optionally adjust tint for decButton when disabled
                binding.decButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(android.R.color.darker_gray)));
            } else {
                binding.decButton.setEnabled(true);
                // Reapply original color when enabled
                binding.decButton.setBackgroundTintList(ColorStateList.valueOf(this.originalButtonColor));
            }
        });

        // Set the initial state of the increment button (it may be disabled by fragment logic)
        setIncrementButtonEnabled(true);
    }

    /**
     * Sets the enabled state of the increment (+) button.
     * @param isEnabled true to enable the button, false to disable it.
     */
    public void setIncrementButtonEnabled(boolean isEnabled) {
        binding.incButton.setEnabled(isEnabled);

        if (!isEnabled) {
            // Apply a lighter tint when disabled
            binding.incButton.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(android.R.color.darker_gray)));
        } else {
            // Reapply the stored original color when enabled
            binding.incButton.setBackgroundTintList(ColorStateList.valueOf(this.originalButtonColor));
        }
    }

    public CounterViewBinding getBinding() {
        return binding;
    }
}