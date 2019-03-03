package com.example.stackrecette;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class mealAdapter extends RecyclerView.Adapter<mealAdapter.MyViewHolder> {

    private Object[][] data;
    private Context context;

    public mealAdapter(Object[][] data, Context context) { this.data = data; this.context = context; }

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
        holder.bind(data[i],context);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView food_name;
        private ImageView food_image;
        private Object[] data;
        private Context context;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.meal,parent,false));

            itemView.setOnClickListener(this);
            food_name = itemView.findViewById(R.id.food_name);
            food_image = itemView.findViewById(R.id.food_image);
        }

        public void bind(Object[] data, Context context)
        {
            this.context = context;
            this.data = data;
            food_name.setText(this.data[0].toString());
            if(!(this.data[this.data.length-1].toString().isEmpty())) {
                Picasso.get().load(this.data[this.data.length-1].toString()).into(food_image);
            }
        }


        @Override
        public void onClick(View v)
        {
            Toast.makeText(itemView.getContext(), this.data[0].toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.context, ingreScreen.class);
            intent.putExtra(MealScreen.key, Arrays.copyOfRange(this.data,1,this.data.length-1));
            v.getContext().startActivity(intent);
        }
    }
}
