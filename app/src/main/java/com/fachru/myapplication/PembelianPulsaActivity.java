package com.fachru.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

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
import com.fachru.myapplication.model.Product;
import com.fachru.myapplication.model.User;
import com.fachru.myapplication.utils.Constanta;
import com.fachru.myapplication.utils.SessionManager;
import com.fachru.myapplication.utils.VolleyErrorHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PembelianPulsaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Response.Listener<String>, Response.ErrorListener{

    private SessionManager session;

    private EditText et_nomor_tujuan;
    private TextView tv_haga;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;

    public Gson gson;

    private DecimalFormat formater = new DecimalFormat("'Harga Rp. '#,##0.-");

    private List<Product> products = new ArrayList<>();

    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian_pulsa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionManager(this);

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        if (Product.find().size() > 0)
            products = Product.find();

        AppCompatSpinner sp_denom_pulsa = (AppCompatSpinner) findViewById(R.id.sp_denom_pulsa);
        et_nomor_tujuan = (EditText) findViewById(R.id.et_nmr_tujuan);
        tv_haga = (TextView) findViewById(R.id.tv_nominal);

        ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, products);

        ArrayAdapter<CharSequence> adapterOperator = ArrayAdapter.createFromResource(this, R.array.operator_array, android.R.layout.simple_spinner_item);

        adapterOperator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_denom_pulsa.setAdapter(arrayAdapter);

        sp_denom_pulsa.setOnItemSelectedListener(this);

        progressDialog = new ProgressDialog(PembelianPulsaActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);

        builder = new AlertDialog.Builder(PembelianPulsaActivity.this, R.style.AppTheme_Dark_Dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setIcon(getDrawable(R.drawable.icon));
        } else {
            builder.setIcon(getResources().getDrawable(R.drawable.icon));
        }
        builder.setTitle("Informasi");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_nomor_tujuan.getText().clear();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        product = (Product) parent.getItemAtPosition(position);

        tv_haga.setText(formater.format(product.price));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void requestToServer(final Map<String, String> map) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.0.107/reload-manager/inbox/store";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.e(Constanta.TAG, response.headers.toString());
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d(Constanta.TAG, map.toString());
                return map;
            }
        };

        progressDialog.setMessage("Mohong Tunggu...");
        progressDialog.show();

        queue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        builder.setMessage(VolleyErrorHelper.getMessage(error, ApplicationController.getAppContext()));

        progressDialog.dismiss();
        builder.setMessage("Pembelian pulsa gagal");
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public void onResponse(String response) {
        Log.e(Constanta.TAG, "OnResponse : " + response);

        progressDialog.dismiss();

        builder.setMessage("Pembelian pulsa berhasil");
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void submit(View view) {

        String nomor_tujuan = et_nomor_tujuan.getText().toString();

        if (nomor_tujuan.equals("")) {
            et_nomor_tujuan.setError("Mohon isi nomor tujuan");
        } else {
            InboxTodo todo = new InboxTodo(product.prodid, nomor_tujuan, User.find(session.get_phone_number()));
            Map<String, String> objectMap;objectMap = gson.fromJson(
                    gson.toJson(todo),
                    new TypeToken<HashMap<String, String>>() {}.getType()
            );

            requestToServer(objectMap);
        }
    }
}
