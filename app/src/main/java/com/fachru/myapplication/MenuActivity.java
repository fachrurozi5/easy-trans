package com.fachru.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fachru.myapplication.model.User;
import com.fachru.myapplication.service.DateTimeService;
import com.fachru.myapplication.utils.Constanta;
import com.fachru.myapplication.utils.SessionManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_date;
    private SessionManager session;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(Constanta.RESULT_DATE)) {
                    tv_date.setText(bundle.getString(Constanta.RESULT_DATE) + " " + bundle.getString(Constanta.RESULT_TIME));
                }
            } else {
                Toast.makeText(MenuActivity.this, "FAILED",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(this);
        User user = User.find(session.get_phone_number());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        String deposit = decimalFormat.format(user.deposit);

        tv_date = (TextView) findViewById(R.id.tv_date);
        TextView tv_name_phone_number = (TextView) findViewById(R.id.tv_name_phone_number);
        TextView tv_saldo_deposit = (TextView) findViewById(R.id.tv_saldo_deposit);

        tv_date.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(new Date()));
        tv_name_phone_number.setText(user.custname + " - " + user.custid);
        tv_saldo_deposit.setText("Saldo Deposit Rp." + deposit);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pin) {
            // Handle the camera action
        } else if (id == R.id.nav_hubungi_kami) {

        } else if (id == R.id.nav_status) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_keluar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActions(View view) {
        switch (view.getId()) {
            case R.id.btn_pembelian:
                startActivity(new Intent(getApplicationContext(), PembelianActivity.class));
                break;
            case R.id.btn_isi_deposit:
                startActivity(new Intent(getApplicationContext(), BankActivity.class));
                break;
            case R.id.btn_trns_deposit:
                startActivity(new Intent(getApplicationContext(), TransferDepositActivity.class));
            default:

        }
    }
}
