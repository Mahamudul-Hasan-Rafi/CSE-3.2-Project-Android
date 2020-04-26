package com.example.mylenovo.testapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MyCustomAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<DataModel> dataModels;

    public MyCustomAdapter(Context context, int layout, ArrayList<DataModel> dataModels) {
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
        TextView user_name, user_mobile;
        ImageView img1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.user_name=convertView.findViewById(R.id.sample_name);
            viewHolder.user_mobile=convertView.findViewById(R.id.sample_mobile);
            viewHolder.img1=convertView.findViewById(R.id.img_edit1);

            convertView.setTag(viewHolder);
        }

        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        DataModel dataModel = dataModels.get(position);

        viewHolder.user_name.setText(dataModel.getName_user());
        viewHolder.user_mobile.setText(dataModel.getMobile_no());


        return convertView;
    }
}
