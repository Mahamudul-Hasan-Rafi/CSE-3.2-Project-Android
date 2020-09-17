package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter_Product extends ArrayAdapter<ProductInfo> {

    Activity context;
    List<ProductInfo> productInfos;

    public CustomAdapter_Product(Activity context, List<ProductInfo> objects) {
        super(context, R.layout.product_layout, objects);

        this.context=context;
        this.productInfos=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.product_layout, null, false);

        TextView product_name = view.findViewById(R.id.product_name);
        TextView product_type = view.findViewById(R.id.product_type);
        TextView price_unit = view.findViewById(R.id.price_unit);
        Button update = view.findViewById(R.id.product_update);

        ProductInfo productInfo = productInfos.get(position);

        product_name.setText(productInfo.getP_name());
        product_type.setText("Type:  "+productInfo.getP_type());
        price_unit.setText("Price/Unit:  "+productInfo.getPrice_unit());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Helllo", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
