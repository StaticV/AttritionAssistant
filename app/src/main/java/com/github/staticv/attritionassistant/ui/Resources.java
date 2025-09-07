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
        // The logic has been expanded to include all building-resource pairs.

        // Collect food based on the number of Farms
        Integer farmCount = counters.get("Farm").getValue();
        if (farmCount != null) {
            MutableLiveData<Integer> foodCounter = counters.get("Food");
            if (foodCounter != null && foodCounter.getValue() != null) {
                foodCounter.setValue(foodCounter.getValue() + farmCount);
            }
        }

        // Collect wood based on the number of Mills
        Integer millCount = counters.get("Mill").getValue();
        if (millCount != null) {
            MutableLiveData<Integer> woodCounter = counters.get("Wood");
            if (woodCounter != null && woodCounter.getValue() != null) {
                woodCounter.setValue(woodCounter.getValue() + millCount);
            }
        }

        // Collect stone based on the number of Quarries
        Integer quarryCount = counters.get("Quarry").getValue();
        if (quarryCount != null) {
            MutableLiveData<Integer> stoneCounter = counters.get("Stone");
            if (stoneCounter != null && stoneCounter.getValue() != null) {
                stoneCounter.setValue(stoneCounter.getValue() + quarryCount);
            }
        }

        // Collect horses based on the number of Stables
        Integer stableCount = counters.get("Stables").getValue();
        if (stableCount != null) {
            MutableLiveData<Integer> horseCounter = counters.get("Horses");
            if (horseCounter != null && horseCounter.getValue() != null) {
                horseCounter.setValue(horseCounter.getValue() + stableCount);
            }
        }

        // Collect iron based on the number of Forges
        Integer forgeCount = counters.get("Forge").getValue();
        if (forgeCount != null) {
            MutableLiveData<Integer> ironCounter = counters.get("Iron");
            if (ironCounter != null && ironCounter.getValue() != null) {
                ironCounter.setValue(ironCounter.getValue() + forgeCount);
            }
        }

        // Collect gold based on the number of Palaces
        Integer palaceCount = counters.get("Palace").getValue();
        if (palaceCount != null) {
            MutableLiveData<Integer> goldCounter = counters.get("Gold");
            if (goldCounter != null && goldCounter.getValue() != null) {
                goldCounter.setValue(goldCounter.getValue() + palaceCount);
            }
        }
    }
}