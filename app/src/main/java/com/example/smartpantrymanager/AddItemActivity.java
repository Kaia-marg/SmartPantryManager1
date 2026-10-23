package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText etItemName, etQuantity, etUnit, etCategory;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DatabaseHelper(this);

        etItemName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etCategory = findViewById(R.id.etCategory);
        Button btnSaveItem = findViewById(R.id.btnSaveItem);

        btnSaveItem.setOnClickListener(v -> savePantryItem());
    }

    private void savePantryItem() {
        String name = etItemName.getText().toString().trim();
        String qtyString = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (name.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(this, "Please enter a name and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity = Double.parseDouble(qtyString);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_QUANTITY, quantity);
        values.put(DatabaseHelper.COLUMN_UNIT, unit);
        values.put(DatabaseHelper.COLUMN_CATEGORY, category);
        values.put(DatabaseHelper.COLUMN_EXPIRY, "N/A");

        long newRowId = db.insert(DatabaseHelper.TABLE_PANTRY, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Item saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the main menu
        } else {
            Toast.makeText(this, "Error saving item", Toast.LENGTH_SHORT).show();
        }
    }
}