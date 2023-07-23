package com.example.flixeblereads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RecentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SqlTables db;
    ArrayList<String> pdf_title,pdf_path;
    RecentAdapter recentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db =new SqlTables(RecentActivity.this);
        pdf_title=new ArrayList<>();
        pdf_path=new ArrayList<>();
        DisplayRecentData();


    }
    public void DisplayRecentData()
    {
        Cursor cursor=db.readAllRecentData();
        if(cursor.getCount()==0)
        {
            setContentView(R.layout.empty_data_layout);
            TextView emptyText= findViewById(R.id.Empty_Text);
            emptyText.setText("Your Recent list is empty");
        }
        else{
            setContentView(R.layout.activity_recent);
            while (cursor.moveToNext())
            {
                pdf_title.add(cursor.getString(1));
                pdf_path.add(cursor.getString(2));

            }
            recyclerView= findViewById(R.id.Recent_Recycler);
            recentAdapter=new RecentAdapter(RecentActivity.this,pdf_title,pdf_path);
            recyclerView.setAdapter(recentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(RecentActivity.this));


        }

    }
}