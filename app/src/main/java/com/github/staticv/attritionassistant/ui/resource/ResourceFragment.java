package com.github.staticv.attritionassistant.ui.resource;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import com.github.staticv.attritionassistant.R;
import com.github.staticv.attritionassistant.ui.Resources;
import com.github.staticv.attritionassistant.databinding.FragmentResourceBinding;

public class ResourceFragment extends Fragment {

    private FragmentResourceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources resources = new ViewModelProvider(requireActivity()).get(Resources.class);
        binding = FragmentResourceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add the MenuProvider to the fragment's menu host
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                // Inflate the menu for this fragment
                menuInflater.inflate(R.menu.menu_resource_collect, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_collect) {
                    resources.collectResources();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        String[] labels = {
                getString(R.string.label_food),
                getString(R.string.label_wood),
                getString(R.string.label_stone),
                getString(R.string.label_horse),
                getString(R.string.label_iron),
                getString(R.string.label_gold)
        };

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