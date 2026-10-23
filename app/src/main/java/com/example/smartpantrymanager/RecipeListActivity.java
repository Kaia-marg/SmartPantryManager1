package com.example.smartpantrymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private ListView listViewRecipes;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        listViewRecipes = findViewById(R.id.listViewRecipes);
        dbHelper = new DatabaseHelper(this);

        loadRecipes();
    }

    private void loadRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> recipeList = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_RECIPES,
                null, null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_CATEGORY));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_INGREDIENTS));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_INSTRUCTIONS));

                recipeList.add(name + " [" + category + "]\nIngredients: " + ingredients + "\nInstructions: " + instructions);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recipeList
        );
        listViewRecipes.setAdapter(adapter);
    }
}