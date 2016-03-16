package com.fachru.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
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
import com.fachru.myapplication.model.Product;
import com.fachru.myapplication.model.User;
import com.fachru.myapplication.utils.Constanta;
import com.fachru.myapplication.utils.SessionManager;
import com.fachru.myapplication.utils.VolleyErrorHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener{

    private SessionManager session;
    private Context context = this;
    private Gson gson;

    // widget
    private EditText input_phone_number, input_pin;
    private AppCompatButton button_login;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(context);
        gson = new Gson();
        init_comp();

        input_phone_number.setText(session.get_phone_number());

        button_login.setOnClickListener(this);

    }

    private void init_comp() {
        input_phone_number = (EditText) findViewById(R.id.input_nomor);
        input_pin = (EditText) findViewById(R.id.input_pin);
        button_login = (AppCompatButton) findViewById(R.id.btn_login);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");

        builder = new AlertDialog.Builder(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setIcon(getDrawable(R.drawable.icon));
        } else {
            builder.setIcon(getResources().getDrawable(R.drawable.icon));
        }
        builder.setTitle("Login Error");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input_pin.getText().clear();
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (input_phone_number.getText().toString().equals(""))
            input_phone_number.setError("Mohon di isi!");
        else if (input_pin.getText().toString().equals(""))
            input_pin.setError("Mohon di isi!");
        else
            requestToServer(input_phone_number.getText().toString(),
                    input_pin.getText().toString());

    }

    private void requestToServer(final String phone_number, final String pin) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.0.107/reload-manager/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.i(Constanta.TAG, response.headers.toString());
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone_number", phone_number);
                map.put("pin", pin);
                return map;
            }
        };

        progressDialog.show();
        progressDialog.setMessage("Authenticating...");

        queue.add(stringRequest);
    }

    private void RequestToServer() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.0.107/reload-manager/product";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type token = new TypeToken<List<Product>>() {}.getType();
                List<Product> products = gson.fromJson(response, token);

                ActiveAndroid.beginTransaction();
                try {
                    for (Product product : products)
                        product.save();

                    ActiveAndroid.setTransactionSuccessful();
                }
                finally {
                    ActiveAndroid.endTransaction();
                }

                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                builder.setMessage(VolleyErrorHelper.getMessage(error, context));

                progressDialog.dismiss();

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.i(Constanta.TAG, response.headers.toString());
                return super.parseNetworkResponse(response);
            }
        };

        progressDialog.show();
        progressDialog.setMessage("Get Product...");

        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        builder.setMessage(VolleyErrorHelper.getMessage(error, ApplicationController.getAppContext()));

        progressDialog.dismiss();

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    @Override
    public void onResponse(String response) {

        User user = gson.fromJson(response, User.class);
        session.set_phone_number(user.custid);
        user.save();

        progressDialog.dismiss();

        RequestToServer();
    }
}
