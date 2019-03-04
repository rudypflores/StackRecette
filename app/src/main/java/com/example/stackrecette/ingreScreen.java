package com.example.stackrecette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class ingreScreen extends AppCompatActivity {

    private TextView food_name;

    private RecyclerView mRecyclerView;
    private ingreAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingre_screen);

        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra(MealScreen.key);



        String[] adapter_data = Arrays.copyOfRange(data,1,data.length);

        System.out.println(Arrays.toString(adapter_data[0].split(", ")));
        mRecyclerView = findViewById(R.id.recycler_ingre);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ingreAdapter(adapter_data[0].split(", "));
        mRecyclerView.setAdapter(mAdapter);

        food_name = findViewById(R.id.food_text);
        food_name.setText(data[0]);

    }
}
