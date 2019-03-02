package com.example.stackrecette;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        private static final String url = "http://www.recipepuppy.com/api/";

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
            setText(data);
            System.out.println("onpost" + result);
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

        public void setText (Object[][] data)
        {
            int[] arr_foodname = {R.id.food_name1,R.id.food_name2,R.id.food_name3,R.id.food_name4,R.id.food_name5,R.id.food_name6,R.id.food_name7};
            int[] arr_foodimage = {R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,R.id.imageView5,R.id.imageView6,R.id.imageView7};
            int[] arr_ll = {R.id.ll1,R.id.ll2,R.id.ll3,R.id.ll4,R.id.ll5,R.id.ll6,R.id.ll7};
            System.out.println(Arrays.deepToString(data));
            for(int i=0;i<arr_foodname.length;i++)
            {
                food_name = findViewById(arr_foodname[i]);
                food_image = findViewById(arr_foodimage[i]);
                food_name.setText(data[i][0].toString());

                if(!String.valueOf(data[i][data[0].length-1]).isEmpty()) {
                    Picasso.get().load(data[i][data[0].length - 1].toString()).into(food_image);
                }
                findViewById(arr_ll[i]).setVisibility(View.VISIBLE);
            }
        }
    }


}


