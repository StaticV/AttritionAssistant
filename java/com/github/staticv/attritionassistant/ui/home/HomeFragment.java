package com.github.staticv.attritionassistant.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.ui.Resources;
import com.github.staticv.attritionassistant.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Resources resources;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resources = new ViewModelProvider(requireActivity()).get(Resources.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] labels = {
                getString(R.string.label_food),
                getString(R.string.label_wood),
                getString(R.string.label_stone),
                getString(R.string.label_horse),
                getString(R.string.label_iron),
                getString(R.string.label_gold)
        };

        binding.collectButton.setOnClickListener(v -> {
            resources.collectResources();
        });

        binding.counterFood.setupWithViewModel(getViewLifecycleOwner(), resources, labels[0]);
        binding.counterWood.setupWithViewModel(getViewLifecycleOwner(), resources, labels[1]);
        binding.counterStone.setupWithViewModel(getViewLifecycleOwner(), resources, labels[2]);
        binding.counterHorse.setupWithViewModel(getViewLifecycleOwner(), resources, labels[3]);
        binding.counterIron.setupWithViewModel(getViewLifecycleOwner(), resources, labels[4]);
        binding.counterGold.setupWithViewModel(getViewLifecycleOwner(), resources, labels[5]);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}