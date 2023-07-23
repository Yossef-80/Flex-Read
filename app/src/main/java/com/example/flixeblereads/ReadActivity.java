package com.example.flixeblereads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadActivity extends AppCompatActivity {
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        PDFView pdfFile=(PDFView) findViewById(R.id.pdfView);
        pdfFile.enableAnnotationRendering(true);
        pdfFile.enableRenderDuringScale(true);
        Intent intent=getIntent();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        Uri uri= Uri.parse(intent.getStringExtra("path"));





        pdfFile.enableAntialiasing(true);
        pdfFile.fromUri(uri).defaultPage(1).scrollHandle(new DefaultScrollHandle(ReadActivity.this)).spacing(10).load();
        //get name
         fileName=intent.getStringExtra("fileName");
            if(fileName==null)
            {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                String name = cursor.getString(nameIndex);
                //store file uri and name
                SqlTables db=new SqlTables(ReadActivity.this);
                boolean isInDb=db.isExistInDb_Recent(name);
                if(!isInDb)
                {
                    db.AddPdfForRecent(name,uri.toString());

                }
            }


    }
}