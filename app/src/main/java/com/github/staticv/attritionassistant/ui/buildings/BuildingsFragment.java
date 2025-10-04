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

    // REMOVED ALL STATIC INDEX CONSTANTS (FARM_INDEX, MILL_INDEX, etc.)

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);
        binding = FragmentBuildingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 1. Load Cost Data and Building Labels from parallel arrays
        // NOTE: Ensure you have an array defined as 'building_labels_array' in your XML
        final String[] buildingLabels = getResources().getStringArray(R.array.building_labels_array);
        final String[] costsData = getResources().getStringArray(R.array.building_costs_data);

        // Load the HashMap in the ViewModel for safe lookups later
        resources.setBuildingCosts(buildingLabels, costsData);

        // --- 2. Define required variables using the loaded arrays ---
        // Accessing array elements by index is safe *here* because they are parallel arrays.
        final String FARM_LABEL = buildingLabels[0];
        final String MILL_LABEL = buildingLabels[1];
        final String QUARRY_LABEL = buildingLabels[2];
        final String STABLE_LABEL = buildingLabels[3];
        final String FORGE_LABEL = buildingLabels[4];
        final String PALACE_LABEL = buildingLabels[5];

        final String FARM_COST = costsData[0];
        final String MILL_COST = costsData[1];
        final String QUARRY_COST = costsData[2];
        final String STABLE_COST = costsData[3];
        final String FORGE_COST = costsData[4];
        final String PALACE_COST = costsData[5];

        // --- 3. Setup ALL counters and OVERRIDE the Increment Listener ---

        // FARM SETUP
        binding.counterFarm.setupWithViewModel(getViewLifecycleOwner(), resources, FARM_LABEL);
        binding.counterFarm.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(FARM_COST)) {
                resources.deductCost(FARM_COST);
                resources.incrementCounter(FARM_LABEL);
            }
        });

        // MILL SETUP
        binding.counterMill.setupWithViewModel(getViewLifecycleOwner(), resources, MILL_LABEL);
        binding.counterMill.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(MILL_COST)) {
                resources.deductCost(MILL_COST);
                resources.incrementCounter(MILL_LABEL);
            }
        });

        // QUARRY SETUP
        binding.counterQuarry.setupWithViewModel(getViewLifecycleOwner(), resources, QUARRY_LABEL);
        binding.counterQuarry.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(QUARRY_COST)) {
                resources.deductCost(QUARRY_COST);
                resources.incrementCounter(QUARRY_LABEL);
            }
        });

        // STABLE SETUP
        binding.counterStable.setupWithViewModel(getViewLifecycleOwner(), resources, STABLE_LABEL);
        binding.counterStable.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(STABLE_COST)) {
                resources.deductCost(STABLE_COST);
                resources.incrementCounter(STABLE_LABEL);
            }
        });

        // FORGE SETUP
        binding.counterForge.setupWithViewModel(getViewLifecycleOwner(), resources, FORGE_LABEL);
        binding.counterForge.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(FORGE_COST)) {
                resources.deductCost(FORGE_COST);
                resources.incrementCounter(FORGE_LABEL);
            }
        });

        // PALACE SETUP
        binding.counterPalace.setupWithViewModel(getViewLifecycleOwner(), resources, PALACE_LABEL);
        binding.counterPalace.getBinding().incButton.setOnClickListener(v -> {
            if (resources.canAffordComplex(PALACE_COST)) {
                resources.deductCost(PALACE_COST);
                resources.incrementCounter(PALACE_LABEL);
            }
        });


        // --- 4. Centralized update logic (Runnable) and Observers ---

        Runnable updateAllBuildingButtons = () -> {
            binding.counterFarm.setIncrementButtonEnabled(resources.canAffordComplex(FARM_COST));
            binding.counterMill.setIncrementButtonEnabled(resources.canAffordComplex(MILL_COST));
            binding.counterQuarry.setIncrementButtonEnabled(resources.canAffordComplex(QUARRY_COST));
            binding.counterStable.setIncrementButtonEnabled(resources.canAffordComplex(STABLE_COST));
            binding.counterForge.setIncrementButtonEnabled(resources.canAffordComplex(FORGE_COST));
            binding.counterPalace.setIncrementButtonEnabled(resources.canAffordComplex(PALACE_COST));
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