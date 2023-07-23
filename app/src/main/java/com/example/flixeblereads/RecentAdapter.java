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

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.MyViewHolder> {

    Context context;
    ArrayList file_name,file_path;
        RecentAdapter(Context context,ArrayList file_name,ArrayList file_path)
        {
         this.context=context;
         this.file_name=file_name;
         this.file_path=file_path;
        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_row,parent,false);
        return  new RecentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.FileName.setText(String.valueOf(file_name.get(position)));
            holder.FilePath.setText(String.valueOf(file_path.get(position)));
            holder.RecentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,ReadActivity.class);
                    intent.putExtra("fileName",String.valueOf(file_name.get(holder.getAdapterPosition())));
                    intent.putExtra("path",String.valueOf(file_path.get(holder.getAdapterPosition())));
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return file_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView FileName,FilePath;
        LinearLayout RecentLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            FileName=itemView.findViewById(R.id.fileName);
            FilePath=itemView.findViewById(R.id.file_path);
            RecentLayout=itemView.findViewById(R.id.card_rec);
        }
    }
}
