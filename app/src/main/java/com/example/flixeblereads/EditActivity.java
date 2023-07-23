package com.example.flixeblereads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SqlTables db;
    ArrayList<String> pdf_title,pdf_text;
    EditAdapter editAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db =new SqlTables(EditActivity.this);
        pdf_title=new ArrayList<>();
        pdf_text=new ArrayList<>();
        display_edit_data();



    }

    void  display_edit_data(){
        Cursor cursor=db.readAllEditData();
        if(cursor.getCount()==0)
        {
            System.out.println("------the database is free");
            setContentView(R.layout.empty_data_layout);
            TextView emptyText= findViewById(R.id.Empty_Text);
            emptyText.setText("Your edit list is empty");
        }
        else{
            setContentView(R.layout.activity_edit);
            while (cursor.moveToNext())
            {
                pdf_title.add(cursor.getString(1));
                pdf_text.add(cursor.getString(2));

            }
            recyclerView=findViewById(R.id.Edit_Recycler);
            editAdapter=new EditAdapter(EditActivity.this,pdf_title,pdf_text);
            recyclerView.setAdapter(editAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(EditActivity.this));


        }

    }
}