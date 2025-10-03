package com.github.staticv.attritionassistant.ui.buildings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.ui.Resources;
import com.github.staticv.attritionassistant.databinding.FragmentBuildingBinding;

public class BuildingsFragment extends Fragment {

    private FragmentBuildingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);

        binding = FragmentBuildingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String farmLabel = getString(R.string.label_farm);
        String millLabel = getString(R.string.label_mill);
        String quarryLabel = getString(R.string.label_quarry);
        String stableLabel = getString(R.string.label_stable);
        String forgeLabel = getString(R.string.label_forge);
        String palaceLabel = getString(R.string.label_palace);

        binding.counterFarm.setupWithViewModel(getViewLifecycleOwner(), resources, farmLabel);
        binding.counterMill.setupWithViewModel(getViewLifecycleOwner(), resources, millLabel);
        binding.counterQuarry.setupWithViewModel(getViewLifecycleOwner(), resources, quarryLabel);
        binding.counterStable.setupWithViewModel(getViewLifecycleOwner(), resources, stableLabel);
        binding.counterForge.setupWithViewModel(getViewLifecycleOwner(), resources, forgeLabel);
        binding.counterPalace.setupWithViewModel(getViewLifecycleOwner(), resources, palaceLabel);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}