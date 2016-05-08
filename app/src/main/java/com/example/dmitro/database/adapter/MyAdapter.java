package com.example.dmitro.database.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmitro.database.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Information> mData = Collections.emptyList();
    public MyAdapter(List<Information> data){
        mData = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_items, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Information info = mData.get(position);
        holder.name_for_list.setText(info.getName());
        holder.about_for_list.setText(info.getAbout());

        Uri uri = Uri.parse(info.getImage());
        Context context = holder.image_for_list.getContext();
        Picasso.with(context).load(uri).resize(60,80).into(holder.image_for_list);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_for_list;
        private TextView about_for_list;
        private ImageView image_for_list;
        public ViewHolder(View itemView) {
            super(itemView);
            name_for_list = (TextView) itemView.findViewById(R.id.name_for_list);
            about_for_list = (TextView) itemView.findViewById(R.id.about_for_list);
            image_for_list = (ImageView) itemView.findViewById(R.id.image_for_list);
        }
    }
}
