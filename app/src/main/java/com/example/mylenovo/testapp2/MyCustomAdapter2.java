package com.example.mylenovo.testapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomAdapter2 extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<DataModel> dataModels;

    public MyCustomAdapter2(Context context, int layout, ArrayList<DataModel> dataModels) {
        this.context = context;
        this.layout = layout;
        this.dataModels = dataModels;
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView user_house, user_street, user_area;
        ImageView img2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyCustomAdapter2.ViewHolder viewHolder = new MyCustomAdapter2.ViewHolder();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.user_house=convertView.findViewById(R.id.sample_house);
            viewHolder.user_street=convertView.findViewById(R.id.sample_street);
            viewHolder.user_area=convertView.findViewById(R.id.sample_area);
            viewHolder.img2=convertView.findViewById(R.id.img_edit2);

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder=(MyCustomAdapter2.ViewHolder)convertView.getTag();
        }

        DataModel dataModel = dataModels.get(position);

        viewHolder.user_house.setText("House#  "+dataModel.getHouse_no());
        viewHolder.user_street.setText("Street#  "+dataModel.getStreet_name());
        viewHolder.user_area.setText("Area#  "+dataModel.getArea());



        return convertView;
    }
}
