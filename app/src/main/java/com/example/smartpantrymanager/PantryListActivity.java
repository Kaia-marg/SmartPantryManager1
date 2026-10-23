package com.example.smartpantrymanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PantryListActivity extends AppCompatActivity {

    private ListView listViewPantry;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        listViewPantry = findViewById(R.id.listViewPantry);
        dbHelper = new DatabaseHelper(this);

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems(); // Refreshes list automatically when returning from adding an item
    }

    private void loadPantryItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> itemList = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PANTRY,
                null, null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                double qty = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY));

                itemList.add(name + " — " + qty + " " + unit + " [" + category + "]");
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                itemList
        );
        listViewPantry.setAdapter(adapter);
    }
}
