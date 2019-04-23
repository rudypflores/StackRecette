package com.example.stackrecette;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MealScreen extends AppCompatActivity {

    private Context context = this;
    Button sign_out_button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public static final String key = "meal_screen_ingredient";
    private static final String url = "http://www.recipepuppy.com/api/";
    private SearchView search;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_screen);

        sign_out_button = findViewById(R.id.sign_out_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MealScreen.this, MainActivity.class));
                }
            }
        };

        //Sign out through FireBase & GoogleClientAPI
        sign_out_button.setOnClickListener(v -> {
            mAuth.signOut();
        });

        search = findViewById(R.id.search_input);

        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) search.findViewById(searchSrcTextId);
        searchEditText.setTextColor(Color.WHITE); // set the text color
        searchEditText.setHintTextColor(Color.WHITE); // set the hint color

        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) search.findViewById(closeButtonId);
        closeButtonImage.setImageResource(R.drawable.ic_close_black_24dp);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int page = (int)((Math.random()*15) + 1);
                System.out.print(page);
                new choose_meal().execute(url+"?i="+query+"&p="+page);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }




    // inner class
    public class choose_meal extends AsyncTask<String, String, String> {

        //this method will do http request on the background thread
        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection con;
            try {
                URL url = new URL(params[0]);
                con = (HttpURLConnection) url.openConnection();
                con.connect();
                //read data from post request
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //read data using Stream

                return response.toString();


            } catch (Exception e) {
                System.out.println("IO Exception");
            }

            return null;
        }

        //this method will fire after doInbackground method terminate and get its returned data
        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            Object[][] data = handle_json(result.toString());
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            mLinearLayoutManager = new LinearLayoutManager(MealScreen.this);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            mAdapter = new mealAdapter(data,context);
            recyclerView.setAdapter(mAdapter);
        }

        //this method will use to handle json
        public Object[][] handle_json (String res)
        {
            JSONArray arr = null;
            //data of var_data will be { {food_name,ingredient,img} , {food_name,ingredient,img} }
            Object[][] data = null;
            try {
                arr = new JSONObject(res).getJSONArray("results");
                data = new Object[arr.length()][3];
                System.out.println(arr.length());
            } catch (Exception e) {
                System.out.println("no element results found");
            }

            try {
                for(int i=0;i< arr.length(); i++)
                {
                    JSONObject res_obj = arr.getJSONObject(i);

                    data[i][0] = res_obj.getString("title");
                    data[i][1] = res_obj.getString("ingredients");
                    data[i][2] = res_obj.getString("thumbnail");

                }
            } catch (Exception e) {
                System.out.println(" unable to loop throught results obj json");
            }
            return data;
        }
    }


}


