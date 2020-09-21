package com.example.mylenovo.testapp2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CustomAdapter_Staff extends ArrayAdapter<Stuff_Info> {
    Activity context;
    List<Stuff_Info> stuff_infoList;
    DatabaseReference dref;

    public CustomAdapter_Staff(Activity context, List<Stuff_Info> objects) {
        super(context, R.layout.stuff_layout, objects);
        this.context = context;
        this.stuff_infoList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.stuff_layout, null, false);

        final Stuff_Info stuff_info = stuff_infoList.get(position);

        TextView name = view.findViewById(R.id.stuff_name);
        TextView mail = view.findViewById(R.id.stuff_email);
        TextView phone = view.findViewById(R.id.stuff_phone);
        TextView area = view.findViewById(R.id.stuff_area);
        Button delete = view.findViewById(R.id.stuff_remove);

        name.setText(stuff_info.getStuff_name());
        mail.setText("Mail:   "+stuff_info.getStuff_mail());
        phone.setText("Phone:   "+stuff_info.getStuff_phone());
        area.setText("Coverage Area:   "+stuff_info.getStuff_area());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref = FirebaseDatabase.getInstance().getReference("Stuff");
                dref.child(stuff_info.getStuff_name()).removeValue();
            }
        });

        return view;
    }
}
