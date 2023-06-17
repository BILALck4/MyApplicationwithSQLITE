package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRICE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
        int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
        int priceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            double price = cursor.getDouble(priceColumnIndex);
            Product product = new Product(name, price);
            productList.add(product);
        }

        cursor.close();
        db.close();
        return productList;
    }

}
