package com.fachru.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import com.fachru.myapplication.utils.Constanta;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener{

    // widget
    private EditText input_phone_number, input_pin;
    private AppCompatButton button_login;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init_comp();

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
        String url ="http://192.168.0.112/reload-manager/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.i(Constanta.TAG, response.toString());
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

        progressDialog.dismiss();
    }

    @Override
    public void onResponse(String response) {
        Log.i(Constanta.TAG, response);
        progressDialog.dismiss();
    }
}
