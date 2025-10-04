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
import com.github.staticv.attritionassistant.ui.CounterView; // Needed to access getBinding()

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

        // Load the HashMap in the ViewModel (Still necessary)
        resources.setBuildingCosts(buildingLabels, costsData);

        // --- 2. Define the Building View references for dynamic setup ---
        // Create an array of the CounterViews in the order of the XML arrays.
        // We use the binding object to access the views by their defined IDs (e.g., counterFarm).
        final CounterView[] buildingViews = new CounterView[] {
                binding.counterFarm,
                binding.counterMill,
                binding.counterQuarry,
                binding.counterStable,
                binding.counterForge,
                binding.counterPalace
        };

        // --- 3. Dynamic Setup and Listener Assignment (Index-Free Logic) ---

        for (int i = 0; i < buildingLabels.length; i++) {
            final String currentLabel = buildingLabels[i];
            final CounterView currentView = buildingViews[i];

            // Setup ViewModel linkage
            currentView.setupWithViewModel(getViewLifecycleOwner(), resources, currentLabel);

            // Override the Increment Button Listener
            currentView.getBinding().incButton.setOnClickListener(v -> {
                // Get the cost using the currentLabel (the HashMap key)
                String cost = resources.getCostForBuilding(currentLabel);

                if (resources.canAffordComplex(cost)) {
                    resources.deductCost(cost);
                    resources.incrementCounter(currentLabel);
                }
            });
        }

        // --- 4. Centralized update logic (Runnable) and Observers ---

        Runnable updateAllBuildingButtons = () -> {
            for (int i = 0; i < buildingLabels.length; i++) {
                final String currentLabel = buildingLabels[i];
                final CounterView currentView = buildingViews[i];

                // Get cost dynamically to check affordability
                String cost = resources.getCostForBuilding(currentLabel);

                currentView.setIncrementButtonEnabled(resources.canAffordComplex(cost));
            }
        };


        // Set up observers for ALL relevant resources
        // Note: We still need explicit resource labels here, as they are static resource names.
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