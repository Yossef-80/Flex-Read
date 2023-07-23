package com.example.flixeblereads;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {
    Context context;
    ArrayList file_name,file_text;
    EditAdapter(Context context,ArrayList file_name,ArrayList file_text)
    {
        this.context=context;
        this.file_name=file_name;
        this.file_text=file_text;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_row,parent,false);
        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.FileName.setText(String.valueOf(file_name.get(position)));
        holder.FileText.setText(String.valueOf(file_text.get(position)));
        holder.file_icon.setImageResource(R.drawable.round_edit_24);
        holder.EditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,CreateActivity.class);
                intent.putExtra("file_name",String.valueOf(file_name.get(holder.getAdapterPosition())));
                intent.putExtra("file_text",String.valueOf(file_text.get(holder.getAdapterPosition())));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return file_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView FileName,FileText;
        ImageView file_icon;
        LinearLayout EditLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            FileName=itemView.findViewById(R.id.fileName);
            FileText=itemView.findViewById(R.id.file_path);
            file_icon=itemView.findViewById(R.id.imageView2);
            EditLayout=itemView.findViewById(R.id.card_rec);

        }
    }
}
