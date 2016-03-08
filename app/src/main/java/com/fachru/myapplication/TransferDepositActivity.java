package com.fachru.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fachru.myapplication.service.DateTimeService;
import com.fachru.myapplication.utils.Constanta;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferDepositActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView tv_date;
    private EditText et_no_hp;

    private AppCompatSpinner sp_nama;

    private String[] nomor;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(Constanta.RESULT_DATE)) {
                    tv_date.setText(bundle.getString(Constanta.RESULT_DATE) + " " + bundle.getString(Constanta.RESULT_TIME));
                }
            } else {
                Toast.makeText(TransferDepositActivity.this, "FAILED",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_deposit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nomor = getResources().getStringArray(R.array.nomor_array);

        tv_date = (TextView) findViewById(R.id.tv_date);
        et_no_hp = (EditText) findViewById(R.id.et_no_hp);
        sp_nama = (AppCompatSpinner) findViewById(R.id.sp_nama);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nama_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tv_date.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));

        sp_nama.setAdapter(adapter);

        sp_nama.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startService(new Intent(getApplicationContext(), DateTimeService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver, new IntentFilter(Constanta.SERVICE_RECEIVER)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        et_no_hp.setText(nomor[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
