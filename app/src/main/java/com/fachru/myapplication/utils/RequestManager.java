package com.fachru.myapplication.utils;

import com.activeandroid.Model;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fachru.myapplication.interfaces.MainListener;
import com.fachru.myapplication.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by fachru on 15/03/16.
 */
public class RequestManager {

    private static final String URL = "http://192.168.0.112/reload-manager";

    private final MainListener listener;

    private final Gson gson;

    private String stringJson;

    public RequestManager(MainListener listener) {
        this.listener = listener;

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public void requestProduct() {
        listener.onRequest();
    }

    public StringRequest getStringRequest(String s) {
        return new StringRequest(
                String.format("%s/%s", URL, s),
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse();
                        stringJson = response;
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                }
        );
    }

    public List<Product> getProductList() {
        return gson.fromJson(stringJson, new TypeToken<List<Product>>() {}.getType());
    }

}
