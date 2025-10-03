package com.github.staticv.attritionassistant.ui.army;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.ui.Resources;
import com.github.staticv.attritionassistant.databinding.FragmentArmyBinding;


public class ArmyFragment extends Fragment {

    private FragmentArmyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);

        binding = FragmentArmyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.counterFarmer.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_farmer));
        binding.counterArcher.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_archer));
        binding.counterRider.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_rider));
        binding.counterKnight.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_knight));
        binding.counterSworder.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_sword));
        binding.counterPiker.setupWithViewModel(getViewLifecycleOwner(), resources, getString(R.string.label_piker));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}