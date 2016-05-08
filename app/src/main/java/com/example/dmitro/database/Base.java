package com.example.dmitro.database;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dmitro.database.adapter.Information;
import com.example.dmitro.database.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Base extends AppCompatActivity {
    private DataBaseHelper mDataBaseHelper;
    private List<Information> data;
    private String pathForImage = "http://office.icenter.com.ua/product_image/";

    //adapter
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //create data base
        mDataBaseHelper = new DataBaseHelper(getApplicationContext());
        mDataBaseHelper.create_db();

        getData();

        //adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


    }

    public void getData() {
        data = new ArrayList<>();
        try {
            mDataBaseHelper.open();
            //запрос sql
            String query = "SELECT " + DataBaseHelper.COLUMN_ID + ", "
                    + DataBaseHelper.COLUMN_NAME + ", "
                    + DataBaseHelper.COLUMN_ABOUT + ", "
                    + DataBaseHelper.COLUMN_IMAGE
                    + " FROM " + DataBaseHelper.TABLE;
            Cursor cursor = mDataBaseHelper.database.rawQuery(query, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
                String about = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_ABOUT));
                String image = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_IMAGE));
                Log.i("LOG_TAG", "ROW " + id + " HAS NAME " + name + " ABOUT " + about + " IMAGE " + image);

                Information info = new Information();
                info.setId(id);
                info.setName(name);
                info.setAbout(about);
                info.setImage(pathForImage+image);
                data.add(info);
            }
            cursor.close();
            mDataBaseHelper.database.close();
        } catch (SQLException ex) {
        }
    }

}