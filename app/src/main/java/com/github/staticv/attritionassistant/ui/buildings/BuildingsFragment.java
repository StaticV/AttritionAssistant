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
import com.github.staticv.attritionassistant.databinding.FragmentBuildingsBinding;

public class BuildingsFragment extends Fragment {

    private FragmentBuildingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);
        binding = FragmentBuildingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 1. Load Cost Data and Building Labels from parallel arrays
        final String[] buildingLabels = getResources().getStringArray(R.array.building_labels_array);
        final String[] costsData = getResources().getStringArray(R.array.building_costs_data);

        // This is the crucial step: Loading the map data into the ViewModel
        resources.setBuildingCosts(buildingLabels, costsData);

        // --- 2. Define Label variables (Labels are the HashMap keys) ---
        final String FARM_LABEL = buildingLabels[0];
        final String MILL_LABEL = buildingLabels[1];
        final String QUARRY_LABEL = buildingLabels[2];
        final String STABLE_LABEL = buildingLabels[3];
        final String FORGE_LABEL = buildingLabels[4];
        final String PALACE_LABEL = buildingLabels[5];

        // REMOVED local COST variables (FARM_COST, MILL_COST, etc.)
        // We will now retrieve the cost dynamically using the label.

        // --- 3. Setup ALL counters and OVERRIDE the Increment Listener ---

        // FARM SETUP
        binding.counterFarm.setupWithViewModel(getViewLifecycleOwner(), resources, FARM_LABEL);
        binding.counterFarm.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically from the ViewModel's HashMap
            String cost = resources.getCostForBuilding(FARM_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(FARM_LABEL);
            }
        });

        // MILL SETUP
        binding.counterMill.setupWithViewModel(getViewLifecycleOwner(), resources, MILL_LABEL);
        binding.counterMill.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically
            String cost = resources.getCostForBuilding(MILL_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(MILL_LABEL);
            }
        });

        // QUARRY SETUP
        binding.counterQuarry.setupWithViewModel(getViewLifecycleOwner(), resources, QUARRY_LABEL);
        binding.counterQuarry.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically
            String cost = resources.getCostForBuilding(QUARRY_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(QUARRY_LABEL);
            }
        });

        // STABLE SETUP
        binding.counterStable.setupWithViewModel(getViewLifecycleOwner(), resources, STABLE_LABEL);
        binding.counterStable.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically
            String cost = resources.getCostForBuilding(STABLE_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(STABLE_LABEL);
            }
        });

        // FORGE SETUP
        binding.counterForge.setupWithViewModel(getViewLifecycleOwner(), resources, FORGE_LABEL);
        binding.counterForge.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically
            String cost = resources.getCostForBuilding(FORGE_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(FORGE_LABEL);
            }
        });

        // PALACE SETUP
        binding.counterPalace.setupWithViewModel(getViewLifecycleOwner(), resources, PALACE_LABEL);
        binding.counterPalace.getBinding().incButton.setOnClickListener(v -> {
            // FIX: Get cost dynamically
            String cost = resources.getCostForBuilding(PALACE_LABEL);
            if (resources.canAffordComplex(cost)) {
                resources.deductCost(cost);
                resources.incrementCounter(PALACE_LABEL);
            }
        });


        // --- 4. Centralized update logic (Runnable) and Observers ---

        Runnable updateAllBuildingButtons = () -> {
            // FIX: Use the ViewModel's cost lookup for the button checks
            binding.counterFarm.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(FARM_LABEL)));
            binding.counterMill.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(MILL_LABEL)));
            binding.counterQuarry.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(QUARRY_LABEL)));
            binding.counterStable.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(STABLE_LABEL)));
            binding.counterForge.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(FORGE_LABEL)));
            binding.counterPalace.setIncrementButtonEnabled(resources.canAffordComplex(resources.getCostForBuilding(PALACE_LABEL)));
        };


        // Set up observers for ALL relevant resources
        resources.getCounter(getString(R.string.label_wood)).observe(getViewLifecycleOwner(), amount -> updateAllBuildingButtons.run());
        resources.getCounter(getString(R.string.label_stone)).observe(getViewLifecycleOwner(), amount -> updateAllBuildingButtons.run());
        resources.getCounter(getString(R.string.label_iron)).observe(getViewLifecycleOwner(), amount -> updateAllBuildingButtons.run());

        // Immediately check and set the initial state of the buttons
        updateAllBuildingButtons.run();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}