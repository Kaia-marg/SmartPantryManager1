package com.example.smartpantrymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listViewSuggested;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listViewSuggested = findViewById(R.id.listViewSuggestedRecipes);
        dbHelper = new DatabaseHelper(this);

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 1. Gather all pantry item names into a lowercase Set
        HashSet<String> pantryItems = new HashSet<>();
        Cursor pantryCursor = db.query(DatabaseHelper.TABLE_PANTRY, null, null, null, null, null, null);
        if (pantryCursor != null && pantryCursor.moveToFirst()) {
            do {
                String itemName = pantryCursor.getString(pantryCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                pantryItems.add(itemName.trim().toLowerCase());
            } while (pantryCursor.moveToNext());
            pantryCursor.close();
        }

        // 2. Fetch all recipes and evaluate matches flexibly
        ArrayList<String> suggestedList = new ArrayList<>();
        Cursor recipeCursor = db.query(DatabaseHelper.TABLE_RECIPES, null, null, null, null, null, null);

        if (recipeCursor != null && recipeCursor.moveToFirst()) {
            do {
                String name = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_NAME));
                String category = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_CATEGORY));
                String ingredientsStr = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow(DatabaseHelper.RECIPE_INGREDIENTS));

                String[] requiredIngredients = ingredientsStr.split(",");
                int matchedCount = 0;

                for (String ingredient : requiredIngredients) {
                    String cleanIng = ingredient.trim().toLowerCase();
                    for (String pantryItem : pantryItems) {
                        if (cleanIng.contains(pantryItem) || pantryItem.contains(cleanIng)) {
                            matchedCount++;
                            break;
                        }
                    }
                }

                if (matchedCount > 0) {
                    suggestedList.add("🍳 " + name + " [" + category + "]\nMatch: " + matchedCount + "/" + requiredIngredients.length + " ingredients\nNeeds: " + ingredientsStr);
                }

            } while (recipeCursor.moveToNext());
            recipeCursor.close();
        }

        if (suggestedList.isEmpty()) {
            suggestedList.add("No recipes match your current pantry items. Try adding more items!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                suggestedList
        );
        listViewSuggested.setAdapter(adapter);
    }
}