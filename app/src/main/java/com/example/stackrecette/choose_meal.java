package com.example.stackrecette;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.loopj.android.http.*;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class choose_meal {

    private static final String url = "http://www.recipepuppy.com/api/";

    private static String res = null;

    public static Object[][] get_request (String query,int num_result)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.recipepuppy.com/api/?i=egg", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                try {
                    String str = new String(response,"UTF-8");
                    str = String.valueOf(str);
                    res = str;
                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }
        });


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
        return data;
    }
}
