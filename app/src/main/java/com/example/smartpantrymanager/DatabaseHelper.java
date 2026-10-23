package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_EXPIRY = "expiry_date";

    public static final String TABLE_RECIPES = "recipes";
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_CATEGORY = "category";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_INSTRUCTIONS = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " REAL, " +
                COLUMN_UNIT + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_EXPIRY + " TEXT)";
        db.execSQL(createPantryTable);

        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " TEXT, " +
                RECIPE_CATEGORY + " TEXT, " +
                RECIPE_INGREDIENTS + " TEXT, " +
                RECIPE_INSTRUCTIONS + " TEXT)";
        db.execSQL(createRecipeTable);

        seedRecipes(db);
        seedPantry(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] initialRecipes = {
                {"Garlic Butter Steak & Rice", "Dinner", "Steak, Butter, Garlic, Rice, Soy Sauce", "Sear steak in a hot skillet with butter and garlic. Serve hot over steamed rice."},
                {"High-Protein Beef & Rice Bowl", "Lunch", "Minced Beef, Rice, Eggs, Soy Sauce", "Brown the minced beef in a pan, season with soy sauce, and serve over rice topped with fried eggs."},
                {"Creamy Garlic Chicken Pasta", "Dinner", "Chicken Breast, Pasta, Heavy Cream, Garlic, Parmesan", "Boil pasta. Sauté chicken and garlic, stir in cream and cheese, then combine."},
                {"Quick Veggie Stir-Fry", "Dinner", "Mixed Vegetables, Tofu, Soy Sauce, Sesame Oil", "Stir-fry cubed tofu and mixed vegetables in sesame oil and soy sauce until tender."},
                {"Classic Tomato Basil Spaghetti", "Dinner", "Spaghetti, Canned Tomatoes, Garlic, Fresh Basil, Olive Oil", "Simmer tomatoes and garlic into a sauce, toss with cooked spaghetti and fresh basil."},
                {"Scrambled Eggs & Toast", "Breakfast", "Eggs, Butter, Bread, Salt, Black Pepper", "Whisk eggs, scramble gently in a buttery pan, and serve over toasted bread."},
                {"Oatmeal Power Bowl", "Breakfast", "Oats, Milk, Honey, Bananas, Cinnamon", "Cook oats in milk, top with sliced bananas, honey, and a dash of cinnamon."},
                {"Avocado Egg Toast", "Breakfast", "Bread, Avocado, Eggs, Lemon Juice, Salt", "Mash avocado with lemon juice, spread on toast, and top with a fried or poached egg."},
                {"Banana Berry Smoothie", "Breakfast", "Banana, Mixed Berries, Milk, Yogurt", "Blend all ingredients together until smooth and creamy."},
                {"Honey Garlic Salmon", "Dinner", "Salmon Fillet, Honey, Soy Sauce, Garlic, Rice", "Pan-sear salmon, pour honey-garlic sauce over it, and serve with rice."},
                {"Cheesy Beef Quesadilla", "Lunch", "Tortillas, Minced Beef, Cheese, Bell Peppers", "Cook beef with peppers, place in tortilla with cheese, and toast in a pan until crispy."},
                {"Lemon Herb Chicken Salad", "Lunch", "Chicken Breast, Lettuce, Lemon Juice, Olive Oil, Cucumbers", "Grill chicken, chop over fresh lettuce and cucumbers, dress with lemon and olive oil."},
                {"Hearty Vegetable Soup", "Lunch", "Mixed Vegetables, Broth, Garlic, Onion", "Simmer all ingredients in a pot with broth until vegetables are soft."},
                {"Peanut Butter Banana Toast", "Snack", "Bread, Peanut Butter, Banana", "Spread peanut butter on toasted bread and top with sliced bananas."},
                {"Garlic Butter Shrimp", "Dinner", "Shrimp, Butter, Garlic, Lemon, Rice", "Sauté shrimp in butter and garlic, finish with a squeeze of lemon, and serve with rice."}
        };

        for (String[] r : initialRecipes) {
            ContentValues values = new ContentValues();
            values.put(RECIPE_NAME, r[0]);
            values.put(RECIPE_CATEGORY, r[1]);
            values.put(RECIPE_INGREDIENTS, r[2]);
            values.put(RECIPE_INSTRUCTIONS, r[3]);
            db.insert(TABLE_RECIPES, null, values);
        }
    }

    private void seedPantry(SQLiteDatabase db) {
        String[][] initialPantry = {
                {"Rice", "5.0", "kg", "Grains"},
                {"Eggs", "12.0", "pcs", "Protein"},
                {"Chicken Breast", "2.0", "kg", "Protein"},
                {"Garlic", "3.0", "bulbs", "Produce"},
                {"Minced Beef", "1.5", "kg", "Protein"},
                {"Pasta", "1.0", "kg", "Grains"},
                {"Butter", "500.0", "g", "Dairy"},
                {"Soy Sauce", "1.0", "bottles", "Condiments"}
        };

        for (String[] item : initialPantry) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, item[0]);
            values.put(COLUMN_QUANTITY, Double.parseDouble(item[1]));
            values.put(COLUMN_UNIT, item[2]);
            values.put(COLUMN_CATEGORY, item[3]);
            values.put(COLUMN_EXPIRY, "N/A");
            db.insert(TABLE_PANTRY, null, values);
        }
    }
}