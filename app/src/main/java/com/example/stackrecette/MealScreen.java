package com.example.stackrecette;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.*;

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

    private TextView food_name;

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

        food_name = findViewById(R.id.food_name12);

        Button t = findViewById(R.id.test);
        
        t.setOnClickListener(v -> {
            new choose_meal().execute("http://www.recipepuppy.com/api/?i=egg");
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
            food_name.setText(data[0][0].toString());
            System.out.println("onpost" + result);
        }

        //this method will use to handle json
        public Object[][] handle_json (String res)
        {
            int num_result = 6; //default
            JSONArray arr = null;
            //data of var_data will be { {food_name,ingredient,img} , {food_name,ingredient,img} }
            Object[][] data = new Object[num_result][3];
            try {
                arr = new JSONObject(res).getJSONArray("results");
            } catch (Exception e) {
                System.out.println("no element results found");
            }

            try {
                System.out.println("handling json...");
                for(int i=0;i< num_result; i++)
                {
                    JSONObject res_obj = arr.getJSONObject(i);

                    data[i][0] = res_obj.getString("title");
                    data[i][1] = res_obj.getString("ingredients");
                    data[i][2] = res_obj.getString("thumbnail");

                }
            } catch (Exception e) {
                System.out.println(" unable to loop throught results obj json");
            }

            System.out.println("in handle json"  + Arrays.deepToString((data)));
            return data;
        }
    }


}


