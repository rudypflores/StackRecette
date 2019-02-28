package com.example.stackrecette;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.*;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;


public class MealScreen extends AppCompatActivity {

    private Object[][] data;
    Button signOut;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

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

        Button t = findViewById(R.id.test);
        t.setOnClickListener(v -> {
            data = choose_meal.get_request("egg",4);
            System.out.println(Arrays.deepToString(data));
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }

//    public Object[][] get_choose_meal()
//    {
//        int page = (int)(Math.random()*5);
//        int result = 5;
//        try {
//           return choose_meal.handle_json(choose_meal.get_request("egg,onion",page), result);
//        } catch (Exception e) {
//            System.out.println("io excpetion");
//        }
//        return null;
//    }

}
