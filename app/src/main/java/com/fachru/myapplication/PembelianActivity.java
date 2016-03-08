package com.fachru.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PembelianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onRegulerClick(View view) {
        startActivity(new Intent(getApplicationContext(), PembelianPulsaActivity.class));
    }

    public void onDataClick(View view) {
        startActivity(new Intent(getApplicationContext(), PembelianDataActivity.class));
    }

    public void onVoucherClick(View view) {
        startActivity(new Intent(getApplicationContext(), VoucherActivity.class));
    }

}
