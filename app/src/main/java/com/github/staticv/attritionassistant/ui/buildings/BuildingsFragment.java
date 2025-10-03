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
// Removed: import com.github.staticv.attritionassistant.ui.CounterView; // Unused import

public class BuildingsFragment extends Fragment {

    private FragmentBuildingsBinding binding;
    // Removed: private Resources resources;    // Converted to local variable
    // Removed: private String[] costsData;    // Converted to local variable

    // Define indices to access the cost strings, matching the new order in arrays.xml
    private static final int FARM_INDEX = 0;
    private static final int MILL_INDEX = 1;
    private static final int QUARRY_INDEX = 2;
    private static final int STABLE_INDEX = 3;
    private static final int FORGE_INDEX = 4;
    private static final int PALACE_INDEX = 5;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Converted fields to local variables inside onCreateView
        final Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);
        binding = FragmentBuildingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 1. Load the complex cost data from the string array XML
        final String[] costsData = getResources().getStringArray(R.array.building_costs_data);

        // Define all cost strings once for use in the listeners
        final String FARM_COST = costsData[FARM_INDEX];
        final String MILL_COST = costsData[MILL_INDEX];
        final String QUARRY_COST = costsData[QUARRY_INDEX];
        final String STABLE_COST = costsData[STABLE_INDEX];
        final String FORGE_COST = costsData[FORGE_INDEX];
        final String PALACE_COST = costsData[PALACE_INDEX];

        // Define all label strings once
        final String FARM_LABEL = getString(R.string.label_farm);
        final String MILL_LABEL = getString(R.string.label_mill);
        final String QUARRY_LABEL = getString(R.string.label_quarry);
        final String STABLE_LABEL = getString(R.string.label_stable);
        final String FORGE_LABEL = getString(R.string.label_forge);
        final String PALACE_LABEL = getString(R.string.label_palace);


        // Removed: View.OnClickListener buildButtonListener = (v) -> { ... }; // Was unused

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