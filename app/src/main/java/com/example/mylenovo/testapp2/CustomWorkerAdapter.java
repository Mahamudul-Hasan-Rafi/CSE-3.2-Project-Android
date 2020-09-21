package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomWorkerAdapter extends BaseAdapter implements Filterable {
    List<Stuff_Info> stuff_infoList;
    List<Stuff_Info> filter_stuff_list;
    Activity context;
    String SELL;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences1,sharedPreferences2;

    public CustomWorkerAdapter(List<Stuff_Info> stuff_infoList, Activity context, String sell) {
        this.stuff_infoList = stuff_infoList;
        this.filter_stuff_list=stuff_infoList;
        this.context = context;
        SELL = sell;
    }

    @Override
    public int getCount() {
        return stuff_infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return stuff_infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.worker_layout, parent, false);

        TextView textView1 = view.findViewById(R.id.worker_name);
        TextView textView2 = view.findViewById(R.id.coverage_area);
        Button notify = view.findViewById(R.id.notify);

        Stuff_Info stuff_info = stuff_infoList.get(position);
        textView1.setText(stuff_info.getStuff_name());
        textView2.setText(stuff_info.getStuff_area());

         notify.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setMessage("Do you want to complete this transaction?").setCancelable(true)
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 showDialog(context, position);
                             }
                         }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
                     }
                 });
                 AlertDialog alertDialog = builder.create();
                 alertDialog.show();
             }
         });

        return view;
    }

    public void showDialog(final Activity contxt, int pos){
        final Dialog dialog = new Dialog(contxt);
        dialog.setContentView(R.layout.add_transaction);

        int width=(int)(contxt.getResources().getDisplayMetrics().widthPixels*0.95);
        int height=(int)(contxt.getResources().getDisplayMetrics().widthPixels*1.5);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

        TextView date_time = dialog.findViewById(R.id.date_time);
        TextView materials = dialog.findViewById(R.id.materials);
        TextView service_prov = dialog.findViewById(R.id.service_provider);
        Button add_transaction = dialog.findViewById(R.id.add_transaction);

        if(SELL.equals("TRUE")){
            final ProductDB productDB = new ProductDB(contxt);
            Cursor cursor = productDB.readAll();

            String name="";
            while(cursor.moveToNext()){
                int columnIndex1=cursor.getColumnIndex("Product_Name");
                name += cursor.getString(columnIndex1);
                name+=", ";
            }

            materials.setText(name);
            final String stuff_name=stuff_infoList.get(pos).getStuff_name();
            service_prov.setText(stuff_name);
            final String moment=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            date_time.setText(moment);
            final Sold_ItemsDB sold_itemsDB = new Sold_ItemsDB(contxt);
            final String nm = name;

            add_transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long id=sold_itemsDB.insertData(moment, nm, stuff_name);

                    ProductDB productDB = new ProductDB(context);
                    Cursor cursor = productDB.readAll();

                    List<ProductInfo> productInfoList = new ArrayList<>();

                    while(cursor.moveToNext()){
                        int columnIndex1=cursor.getColumnIndex("Product_Name");
                        int columnIndex2=cursor.getColumnIndex("Product_Type");
                        int columnIndex3=cursor.getColumnIndex("Price_Unit");

                        String name = cursor.getString(columnIndex1);
                        String type = cursor.getString(columnIndex2);
                        String p_u = cursor.getString(columnIndex3);

                        productInfoList.add(new ProductInfo(name, type, p_u));
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference("Transaction");

                    sharedPreferences1 = contxt.getSharedPreferences("UserKey", Context.MODE_PRIVATE);
                    String key = sharedPreferences1.getString("Key","N/A");

                    sharedPreferences2 = contxt.getSharedPreferences("UserData", Context.MODE_PRIVATE);


                    for(int i=0; i<productInfoList.size(); i++){
                        databaseReference.child(key).child("Product").child(productInfoList.get(i).getP_name()).setValue(productInfoList.get(i));
                    }
                    databaseReference.child(key).child("User").child("Name").setValue(sharedPreferences2.getString("Name","N/A"));
                    databaseReference.child(key).child("User").child("ContactNo").setValue(sharedPreferences2.getString("ContactNo.","N/A"));
                    databaseReference.child(key).child("User").child("Email").setValue(sharedPreferences2.getString("Email","N/A"));
                    databaseReference.child(key).child("User").child("Address").setValue(sharedPreferences2.getString("Address","N/A"));

                    databaseReference.child(key).child("DateTime").child("DateTime").setValue(moment);
                    databaseReference.child(key).child("Type").child("Picking").setValue("Picks product from user");

                    if(id>0){
                        Toast.makeText(contxt, "Transaction Completed Succesfully", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();

                    productDB.deleteAll();

                    Intent intent = new Intent(contxt, Selling_Transaction.class);
                    contxt.startActivity(intent);
                }
            });


        }
        else{
            final ProductDB_Purchase productDB_purchase = new ProductDB_Purchase(contxt);
            Cursor cursor = productDB_purchase.readAll();

            String name="";
            while(cursor.moveToNext()){
                int columnIndex1=cursor.getColumnIndex("Product_Name");
                name += cursor.getString(columnIndex1);
                name+=", ";
            }

            materials.setText(name);
            final String stuff_name=stuff_infoList.get(pos).getStuff_name();
            service_prov.setText(stuff_name);
            final String moment=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            date_time.setText(moment);
            final Purchased_ItemsDB purchased_itemsDB = new Purchased_ItemsDB(contxt);
            final String nm = name;

            add_transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long id=purchased_itemsDB.insertData(moment, nm, stuff_name);

                    ProductDB_Purchase productDB_purchase = new ProductDB_Purchase(context);
                    Cursor cursor = productDB_purchase.readAll();

                    List<ProductInfo> productInfoList = new ArrayList<>();

                    while(cursor.moveToNext()){
                        int columnIndex1=cursor.getColumnIndex("Product_Name");
                        int columnIndex2=cursor.getColumnIndex("Product_Type");
                        int columnIndex3=cursor.getColumnIndex("Price_Unit");

                        String name = cursor.getString(columnIndex1);
                        String type = cursor.getString(columnIndex2);
                        String p_u = cursor.getString(columnIndex3);

                        productInfoList.add(new ProductInfo(name, type, p_u));
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference("Transaction");

                    sharedPreferences1 = contxt.getSharedPreferences("UserKey", Context.MODE_PRIVATE);
                    String key = sharedPreferences1.getString("Key","N/A");

                    sharedPreferences2 = contxt.getSharedPreferences("UserData", Context.MODE_PRIVATE);


                    for(int i=0; i<productInfoList.size(); i++){
                        databaseReference.child(key).child("Product").child(productInfoList.get(i).getP_name()).setValue(productInfoList.get(i));
                    }
                    databaseReference.child(key).child("User").child("Name").setValue(sharedPreferences2.getString("Name","N/A"));
                    databaseReference.child(key).child("User").child("ContactNo").setValue(sharedPreferences2.getString("ContactNo.","N/A"));
                    databaseReference.child(key).child("User").child("Email").setValue(sharedPreferences2.getString("Email","N/A"));
                    databaseReference.child(key).child("User").child("Address").setValue(sharedPreferences2.getString("Address","N/A"));

                    databaseReference.child(key).child("DateTime").child("DateTime").setValue(moment);
                    databaseReference.child(key).child("Type").child("Selling").setValue("User purchase products");


                    if(id>0){
                        Toast.makeText(contxt, "Transaction Completed Succesfully", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();

                    productDB_purchase.deleteAll();

                    Intent intent = new Intent(contxt, Purchasing_Transaction.class);
                    contxt.startActivity(intent);
                }
            });

        }

    }

    @Override
    public Filter getFilter() {
        return new CustomizeFilter();
    }

    class CustomizeFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Stuff_Info> filter = new ArrayList<>();

            if(constraint!=null && constraint.length()>0){
                String cons = constraint.toString().toUpperCase();
                for(int i=0; i<filter_stuff_list.size(); i++){
                    if(filter_stuff_list.get(i).getStuff_area().toUpperCase().contains(cons)){
                        filter.add(filter_stuff_list.get(i));
                    }
                    filterResults.values=filter;
                    filterResults.count=filter.size();
                }
            }
            else{
                filterResults.values=filter_stuff_list;
                filterResults.count=filter_stuff_list.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stuff_infoList = (ArrayList<Stuff_Info>)results.values;
            notifyDataSetChanged();
        }
    }

}
