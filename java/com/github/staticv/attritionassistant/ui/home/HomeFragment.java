package com.github.staticv.attritionassistant.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.R;

import com.github.staticv.attritionassistant.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int number = 0; // Initialize the number

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView numberTextView = binding.numberTextView;
        Button decrementButton = binding.decrementButton;
        Button incrementButton = binding.incrementButton;

        // Set text on TextView initially
        numberTextView.setText(String.valueOf(number));

        // Handle button click for decrementing the number
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number--;
                numberTextView.setText(String.valueOf(number));
            }

            
        });

        // Handle button click for incrementing the number
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                numberTextView.setText(String.valueOf(number));
            }
        });

        return root;
    }
}