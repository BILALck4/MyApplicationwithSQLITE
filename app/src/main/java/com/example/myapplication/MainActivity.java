package com.example.myapplication;




import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Product> productList = new ArrayList<>();
    private ListView listView;
    private ProductListAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new ProductListAdapter(this, productList);
        listView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);
        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());
        adapter.notifyDataSetChanged();

        Button addButton = findViewById(R.id.button2);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }



    private void addProduct() {
        EditText nameEditText = findViewById(R.id.editTextText);
        EditText priceEditText = findViewById(R.id.editTextNumberDecimal);

        String name = nameEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());

        Product product = new Product(name, price);
        databaseHelper.insertProduct(product);

        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());
        adapter.notifyDataSetChanged();

        nameEditText.getText().clear();
        priceEditText.getText().clear();
    }

    private class ProductListAdapter extends ArrayAdapter<Product> {
        private LayoutInflater inflater;

        public ProductListAdapter(Context context, List<Product> productList) {
            super(context, 0, productList);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = inflater.inflate(R.layout.list_item_product, parent, false);
            }

            TextView textViewName = itemView.findViewById(R.id.textViewName);
            TextView textViewPrice = itemView.findViewById(R.id.textViewPrice);

            Product product = getItem(position);
            if (product != null) {
                textViewName.setText(product.getName());
                textViewPrice.setText(String.valueOf(product.getPrice()));
            }

            return itemView;
        }
    }
}
