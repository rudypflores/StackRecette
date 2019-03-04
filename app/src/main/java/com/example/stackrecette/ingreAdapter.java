package com.example.stackrecette;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class ingreAdapter extends RecyclerView.Adapter<ingreAdapter.MyViewHolder> {

    private String[] data;

    public ingreAdapter(String[] data) { this.data = data; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        MyViewHolder vh = new MyViewHolder(inflater, parent);
        return vh;
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int i)
    {
        holder.bind(this.data[i]);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView ingre;
        private String data;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.ingredient,parent,false));

            itemView.setOnClickListener(this);
            ingre = itemView.findViewById(R.id.ingre);
        }

        public void bind(String data)
        {
            this.data = data;
            ingre.setText(this.data);
        }

        @Override
        public void onClick(View v)
        {
            System.out.println(this.data + "");
        }
    }
}
