package com.example.flixeblereads;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.shockwave.pdfium.PdfDocument;

import java.io.Console;

public class MainActivity extends AppCompatActivity {
    PdfDocument pdfDocument;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView readCard=(CardView) findViewById(R.id.cardRead);
        CardView CreateCard=(CardView) findViewById(R.id.CardCreate);
        CardView EditCard=(CardView)findViewById(R.id.cardEdit);
        CardView RecentCard=(CardView)findViewById(R.id.cardRecent);
        CreateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateActivity.class);
                startActivity(intent);
            }
        });
        readCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filePick=new Intent(Intent.ACTION_GET_CONTENT);
                filePick.setType("application/pdf");
                filePick.addCategory(Intent.CATEGORY_OPENABLE);
                filePick.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                filePick.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(filePick,10);

                }
        });
        EditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
        RecentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RecentActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10&&resultCode==RESULT_OK&&data!=null)
        {
                    Uri uri=data.getData();
                    String path=uri.getPath();
                    String fileName=data.getScheme();



            Intent intent= new Intent(MainActivity.this,ReadActivity.class);

            intent.putExtra("path",uri.toString());
                  startActivity(intent);

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}