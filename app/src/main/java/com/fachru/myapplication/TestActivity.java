package com.fachru.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fachru.myapplication.model.InboxTodo;
import com.fachru.myapplication.utils.Constanta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    public Map<String, String> objectMap;
    public Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                requestToServer();
            }
        });

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        InboxTodo todo = new InboxTodo();
        objectMap = gson.fromJson(
                gson.toJson(todo),
                new TypeToken<HashMap<String, String>>() {}.getType()
        );


        Log.e(Constanta.TAG, objectMap.toString());
    }

    private void requestToServer() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.0.107/reload-manager/inbox";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.e(Constanta.TAG, response.toString());
                Log.e(Constanta.TAG, response.headers.toString());
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return objectMap;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(Constanta.TAG, "VolleyError ", error);

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Log.e(Constanta.TAG, "TimeoutError");
        } else if (error instanceof AuthFailureError) {
            Log.e(Constanta.TAG, "AuthFailureError");
        } else if (error instanceof ServerError) {
            Log.e(Constanta.TAG, "ServerError");
        } else if (error instanceof NetworkError) {
            Log.e(Constanta.TAG, "NetworkError");
        } else if (error instanceof ParseError) {
            Log.e(Constanta.TAG, "ParseError");
        }
    }

    @Override
    public void onResponse(String response) {
        Log.e(Constanta.TAG, response);
    }
}
