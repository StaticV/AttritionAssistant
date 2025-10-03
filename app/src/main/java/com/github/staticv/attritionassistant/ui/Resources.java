package com.github.staticv.attritionassistant.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.Map;

public class Resources extends ViewModel {

    private final Map<String, MutableLiveData<Integer>> counters = new HashMap<>();

    public Resources() {
        // The constructor is now empty.
    }

    public void init(String[] labels) {
        if (counters.isEmpty()) {
            for (String label : labels) {
                counters.put(label, new MutableLiveData<>(0));
            }
        }
    }

    public LiveData<Integer> getCounter(String label) {
        return counters.get(label);
    }

    public void incrementCounter(String label) {
        MutableLiveData<Integer> liveData = counters.get(label);
        if (liveData != null) {
            Integer value = liveData.getValue();
            if (value != null) {
                liveData.setValue(value + 1);
            }
        }
    }

    public void decrementCounter(String label) {
        MutableLiveData<Integer> liveData = counters.get(label);
        if (liveData != null) {
            Integer value = liveData.getValue();
            if (value != null) {
                liveData.setValue(value - 1);
            }
        }
    }

    public void collectResources() {

        // Collect food based on the number of Farms
        MutableLiveData<Integer> farmCounter = counters.get("Farm");
        Integer farmCount = (farmCounter != null) ? farmCounter.getValue() : null;
        if (farmCount != null) {
            MutableLiveData<Integer> foodCounter = counters.get("Food");
            if (foodCounter != null && foodCounter.getValue() != null) {
                foodCounter.setValue(foodCounter.getValue() + farmCount);
            }
        }

        // Collect wood based on the number of Mills
        MutableLiveData<Integer> millCounter = counters.get("Mill");
        Integer millCount = (millCounter != null) ? millCounter.getValue() : null;
        if (millCount != null) {
            MutableLiveData<Integer> woodCounter = counters.get("Wood");
            if (woodCounter != null && woodCounter.getValue() != null) {
                woodCounter.setValue(woodCounter.getValue() + millCount);
            }
        }

        // Collect stone based on the number of Quarries
        MutableLiveData<Integer> quarryCounter = counters.get("Quarry");
        Integer quarryCount = (quarryCounter != null) ? quarryCounter.getValue() : null;
        if (quarryCount != null) {
            MutableLiveData<Integer> stoneCounter = counters.get("Stone");
            if (stoneCounter != null && stoneCounter.getValue() != null) {
                stoneCounter.setValue(stoneCounter.getValue() + quarryCount);
            }
        }

        // Collect horses based on the number of Stables
        MutableLiveData<Integer> stableCounter = counters.get("Stables");
        Integer stableCount = (stableCounter != null) ? stableCounter.getValue() : null;
        if (stableCount != null) {
            MutableLiveData<Integer> horseCounter = counters.get("Horses");
            if (horseCounter != null && horseCounter.getValue() != null) {
                horseCounter.setValue(horseCounter.getValue() + stableCount);
            }
        }

        // Collect iron based on the number of Forges
        MutableLiveData<Integer> forgeCounter = counters.get("Forge");
        Integer forgeCount = (forgeCounter != null) ? forgeCounter.getValue() : null;
        if (forgeCount != null) {
            MutableLiveData<Integer> ironCounter = counters.get("Iron");
            if (ironCounter != null && ironCounter.getValue() != null) {
                ironCounter.setValue(ironCounter.getValue() + forgeCount);
            }
        }

        // Collect gold based on the number of Palaces
        MutableLiveData<Integer> palaceCounter = counters.get("Palace");
        Integer palaceCount = (palaceCounter != null) ? palaceCounter.getValue() : null;
        if (palaceCount != null) {
            MutableLiveData<Integer> goldCounter = counters.get("Gold");
            if (goldCounter != null && goldCounter.getValue() != null) {
                goldCounter.setValue(goldCounter.getValue() + palaceCount);
            }
        }
    }

    // ---------------------------------------------------------------------
    // COST DEDUCTION AND CHECKING METHODS
    // ---------------------------------------------------------------------

    /**
     * Deducts the cost of a building/unit from the current resources.
     * This should only be called AFTER canAffordComplex() has returned true.
     * @param costData The complex cost string (e.g., "Wood:1,Stone:1,Iron:1" or "Wood:2|Stone:2").
     */
    public void deductCost(String costData) {

        // 1. Determine which cost option to use (if it's an OR cost)
        String effectiveCostString = costData;

        if (costData.contains("|")) {
            String[] options = costData.split("\\|");
            // For OR costs, we assume the user pays with the FIRST option they can afford.
            for (String option : options) {
                if (canAffordAnd(option)) {
                    effectiveCostString = option;
                    break;
                }
            }
        }

        // 2. Deduct the resources based on the effective cost string (handles AND costs and single costs)
        String[] costs = effectiveCostString.split(",");

        for (String cost : costs) {
            String[] parts = cost.split(":");
            String resourceLabel = parts[0].trim();
            int requiredAmount = Integer.parseInt(parts[1].trim());

            MutableLiveData<Integer> liveData = counters.get(resourceLabel);

            if (liveData != null && liveData.getValue() != null) {
                // Subtract the required amount
                liveData.setValue(liveData.getValue() - requiredAmount);
            }
        }
    }

    /**
     * Checks if the user can afford a complex cost string with AND/OR logic.
     * Cost String Format Examples:
     * - AND cost: "Wood:1,Stone:1,Iron:1"
     * - OR cost: "Wood:2|Stone:2"
     */
    public boolean canAffordComplex(String costData) {
        if (costData.contains("|")) {
            // --- OR Logic (Can afford if ANY option is true) ---
            String[] options = costData.split("\\|");
            for (String option : options) {
                // Check if we can afford this specific 'OR' option
                if (canAffordAnd(option)) {
                    return true;
                }
            }
            return false; // Cannot afford any of the OR options

        } else {
            // --- AND Logic (Must afford ALL required resources) ---
            return canAffordAnd(costData);
        }
    }

    // Helper method for AND logic (handles the base case for single costs too)
    private boolean canAffordAnd(String costData) {
        String[] costs = costData.split(","); // Splits the string by AND requirements

        for (String cost : costs) {
            String[] parts = cost.split(":");
            String resourceLabel = parts[0].trim();
            int requiredAmount = Integer.parseInt(parts[1].trim());

            if (!canAffordSingleResource(resourceLabel, requiredAmount)) {
                return false; // Cannot afford this part of the AND cost
            }
        }
        return true; // Can afford all required resources
    }

    // Helper method to check if a single resource/cost can be afforded
    private boolean canAffordSingleResource(String resourceLabel, int cost) {
        LiveData<Integer> liveData = counters.get(resourceLabel);

        if (liveData != null && liveData.getValue() != null) {
            return liveData.getValue() >= cost;
        }
        return false;
    }
}