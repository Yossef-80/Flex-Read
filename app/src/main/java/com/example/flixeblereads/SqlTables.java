package com.example.flixeblereads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SqlTables extends SQLiteOpenHelper {
        private Context context;
        private static final String DataBase_Name="PDFReader.db";
        private static final int DataBase_Version=1;
        //----for edit files
        private static final String Table_Name="Edit_Table";
        private static final String Column_Id="_id";
        private static final String Column_Title="pdf_title";
        private static final String Column_Text="pdf_Text";
        //----for recent files
        private static final String Table_recent_Name="Recent_Table";
        private static final  String Column_recent_id="file_id";
        private static final String Column_recent_Title="file_title";
        private static final String Column_recent_path="file_path";


    public SqlTables(@Nullable Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_Edit="CREATE TABLE "+Table_Name+
                " ("+Column_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Title+" Text ,"+
                Column_Text+" Text);";
        db.execSQL(query_Edit);
        String query_Recent="CREATE TABLE "+Table_recent_Name+
                " ("+Column_recent_id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_recent_Title+" Text ,"+
                Column_recent_path+" Text);";
        db.execSQL(query_Recent);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop Table if exists "+Table_Name);
        db.execSQL("Drop Table if exists "+Table_recent_Name);
        onCreate(db);
    }
   void AddPdfForEdit(String title,String text)
   {
       SQLiteDatabase db=this.getWritableDatabase();
       ContentValues cv=new ContentValues();
       cv.put(Column_Title,title);
       cv.put(Column_Text,text);
       long result=db.insert(Table_Name,null,cv);
       if(result==-1)
       {
           Toast.makeText(context, "Saving To Edit Failed", Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(context, "Successful Save", Toast.LENGTH_SHORT).show();
       }
   }
    void AddPdfForRecent(String title,String path)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Column_recent_Title,title);
        cv.put(Column_recent_path,path);
       long result=db.insert(Table_recent_Name,null,cv);
       if(result==-1)
       {
           Toast.makeText(context, "Saving To Recent Failed", Toast.LENGTH_SHORT).show();
       }

    }
    Cursor readAllEditData()
    {
        String query="Select * from "+Table_Name;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;

           cursor= db.rawQuery(query,null);

        return cursor;
    }
    Cursor readAllRecentData()
    {
        String query="Select * from "+Table_recent_Name;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;

            cursor= db.rawQuery(query,null);

        return cursor;
    }
    boolean isExistInDb_Recent(String file_name)
    {
        String query="Select * from "+Table_recent_Name;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;

            cursor= db.rawQuery(query,null);

        while(cursor.moveToNext())
        {
            if(cursor.getString(1).equals(file_name))
            {
                cursor.close();
                return  true;
            }
        }
        cursor.close();
        return false;
    }
    boolean isExistInDb_Edit(String file_name)
    {
        String query="Select * from "+Table_Name;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;

            cursor= db.rawQuery(query,null);

        while(cursor.moveToNext())
        {
            if(cursor.getString(1).equals(file_name))
            {
                return  true;
            }
        }
        return false;
    }
}
