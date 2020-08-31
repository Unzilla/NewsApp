package com.example.googlenews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Manifest;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_Data_From_Google_News();
    }
    public void get_Data_From_Google_News() {


        Handler mainhandler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=b19e48d4d11046b8b037b56de8bab3c9";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();

                    client.get(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.i("jsondata", response.toString());
                            try{
                                JSONArray articles=response.getJSONArray("articles");
                                for(int i=0;i<articles.length();i++){
                                    JSONObject article=(JSONObject)articles.get(i);
                                    Log.e("title",article.getString("title"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.i("Error", Integer.toString(statusCode));
                            Toast.makeText(MainActivity.this, "Failure:" + Integer.toString(statusCode), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Exception:", e.toString());
                }
            }

            };




        mainhandler.post(runnable);

    }
}