package com.example.flixeblereads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    EditText textField1;
    String tempText="",tempTitle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentValues();
        setContentView(R.layout.activity_create);
         textField1=(EditText) findViewById(R.id.editTextTextMultiLine2);
         textField1.setText(tempText);
        Button ConvertToPdf=(Button) findViewById(R.id.convertToPDFbutton);
        ConvertToPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textField1.getText().toString().equals(""))
                {
                    Toast.makeText(CreateActivity.this, "The Field is Empty", Toast.LENGTH_LONG).show();
                }
                else{
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
                    {
                        String[]permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,1000);
                    }
                    else{
                        SavePdf();

                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1000:{
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                   SavePdf();
                }
                else{
                    Toast.makeText(this, "Permission Denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void SavePdf()
    {
        String fileName;

        //pdf file name

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("File Name");
        final View CustomLayout=getLayoutInflater().inflate(R.layout.custom_layout,null);
        builder.setView(CustomLayout);
        EditText editText=CustomLayout.findViewById(R.id.TextfileName);
        editText.setText(tempTitle);
        builder.setPositiveButton("Save",(dialogInterface, i) -> {

           SaveFileData(editText.getText().toString());
        });

        AlertDialog dialog= builder.create();
        dialog.show();

    }

    private void SaveFileData(String fileName) {
        com.itextpdf.text.Document doc = (com.itextpdf.text.Document) new com.itextpdf.text.Document();

        // String fileName= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filePath= Environment.getExternalStorageDirectory()+"/"+fileName+".pdf";

        try {
            PdfWriter.getInstance(doc,new FileOutputStream(filePath));
            doc.open();
            String mText=textField1.getText().toString();

            doc.add(new Paragraph(mText));
            doc.close();
            Toast.makeText(this, fileName+".pdf \n is saved to \n"+filePath, Toast.LENGTH_SHORT).show();

            SqlTables db=new SqlTables(this);
            boolean isInDb=db.isExistInDb_Edit(fileName);
            if(!isInDb)
            {
                db.AddPdfForEdit(fileName,mText.trim());

            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    private  void getIntentValues()
    {
        if(getIntent().hasExtra("file_name")&&getIntent().hasExtra("file_text"))
        {
            tempTitle=getIntent().getStringExtra("file_name");
            tempText=getIntent().getStringExtra("file_text");
        }
    }
}