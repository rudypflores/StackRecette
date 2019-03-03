package com.example.stackrecette;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.*;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;


public class MealScreen extends AppCompatActivity {

    private Object[][] data;
    Button signOut;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private static final String url = "http://www.recipepuppy.com/api/";
    private TextView food_name;
    private ImageView food_image;
    private SearchView search;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private mealAdapter mAppAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_screen);

        signOut = findViewById(R.id.sign_out_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MealScreen.this, MainActivity.class));
                }
            }
        };
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        search = findViewById(R.id.search_input);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new choose_meal().execute(url+"?i="+query);
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
            System.out.println("onpost" + result);

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            mLinearLayoutManager = new LinearLayoutManager(MealScreen.this);
            recyclerView.setLayoutManager(mLinearLayoutManager);
            mAdapter = new mealAdapter(data);
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
                System.out.println("handling json...");
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


