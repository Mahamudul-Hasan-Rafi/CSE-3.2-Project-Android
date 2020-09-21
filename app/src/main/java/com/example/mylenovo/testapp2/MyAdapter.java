package com.example.mylenovo.testapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{

    private Context context;
    private List<Upload> uploadList;
    DatabaseReference ref;

    public MyAdapter(Context context, List<Upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }


    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycled_item_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Upload upload = uploadList.get(i);
        myViewHolder.textView1.setText(upload.getImageName());
        myViewHolder.textView2.setText(upload.getRequired_products());
        Picasso.with(context).load(upload.getImageUrl()).
                placeholder(R.mipmap.ic_launcher_round).fit().centerCrop().into(myViewHolder.imageView);
        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref=FirebaseDatabase.getInstance().getReference("Upload");
                ref.child(upload.getImageName()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }


    @Override
    public Filter getFilter() {
        return new Custom_Filter();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.img_name);
            textView2 = itemView.findViewById(R.id.required_products);
            imageView = itemView.findViewById(R.id.img);
            button = itemView.findViewById(R.id.rec_remove);
        }
    }

    public class Custom_Filter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }
}
