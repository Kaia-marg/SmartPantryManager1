package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button btnViewPantry = findViewById(R.id.btnViewPantry);
        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnViewRecipes = findViewById(R.id.btnViewRecipes);

        // Launch Pantry Inventory Screen
        btnViewPantry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PantryListActivity.class);
            startActivity(intent);
        });

        // Launch Add Item Screen
        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        // Launch Suggested Recipes Screen
        btnViewRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });
    }
}