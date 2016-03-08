package com.fachru.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PembelianDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private AppCompatSpinner /*sp_operator,*/ sp_denom_pulsa;
    private TextView tv_haga;

    private int[] denom_pulsa_array;
    private DecimalFormat formater = new DecimalFormat("'Harga Rp. '#,##0.-");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        denom_pulsa_array = getResources().getIntArray(R.array.d_pulsa_array);

//        sp_operator = (AppCompatSpinner) findViewById(R.id.sp_operator);
        sp_denom_pulsa = (AppCompatSpinner) findViewById(R.id.sp_denom_pulsa);
        tv_haga = (TextView) findViewById(R.id.tv_nominal);

//        ArrayAdapter<CharSequence> adapterOperator = ArrayAdapter.createFromResource(this, R.array.operator_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterDenomPulsa = ArrayAdapter.createFromResource(this, R.array.data_pulsa_array, android.R.layout.simple_spinner_item);

//        adapterOperator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDenomPulsa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        sp_operator.setAdapter(adapterOperator);
        sp_denom_pulsa.setAdapter(adapterDenomPulsa);

        sp_denom_pulsa.setOnItemSelectedListener(this);
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
        double hrg = denom_pulsa_array[position] - 1500;

        tv_haga.setText(formater.format(hrg));


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
