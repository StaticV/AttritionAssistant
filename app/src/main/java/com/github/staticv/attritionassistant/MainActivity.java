package com.github.staticv.attritionassistant;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.github.staticv.attritionassistant.databinding.ActivityMainBinding;
import com.github.staticv.attritionassistant.ui.Resources;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get ViewModel instance before any fragments are created
        resources = new ViewModelProvider(this).get(Resources.class);

        // Define and pass all string labels from all fragments to the ViewModel
        String[] allLabels = {
                getString(R.string.label_food),
                getString(R.string.label_wood),
                getString(R.string.label_stone),
                getString(R.string.label_horse),
                getString(R.string.label_iron),
                getString(R.string.label_gold),

                getString(R.string.label_farm),
                getString(R.string.label_mill),
                getString(R.string.label_quarry),
                getString(R.string.label_stable),
                getString(R.string.label_forge),
                getString(R.string.label_palace),

                getString(R.string.label_farmer),
                getString(R.string.label_archer),
                getString(R.string.label_rider),
                getString(R.string.label_knight),
                getString(R.string.label_sword),
                getString(R.string.label_piker)
        };
        resources.init(allLabels);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}