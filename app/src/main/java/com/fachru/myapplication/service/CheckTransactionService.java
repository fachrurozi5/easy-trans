package com.fachru.myapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fachru.myapplication.ApplicationController;
import com.fachru.myapplication.LoginActivity;
import com.fachru.myapplication.MenuActivity;
import com.fachru.myapplication.R;
import com.fachru.myapplication.model.InboxTodo;
import com.fachru.myapplication.utils.Constanta;
import com.fachru.myapplication.utils.VolleyErrorHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fachru on 17/03/16.
 */
public class CheckTransactionService extends Service{

    public static final String TAG = "CheckTransactionService";

    public static final long INTERVAL = 10 * 1000;

    private Handler mHandler = new Handler();

    private Timer mTimer = null;

    private Gson gson;

    private int mpid;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public CheckTransactionService() {
        Log.d(Constanta.TAG, "Service CheckTransactionService start");

        mpid = 1;

        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }

        mTimer.scheduleAtFixedRate(new CheckStatusTask(), 0, INTERVAL);

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    class CheckStatusTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (InboxTodo.hasStatusVCH())
                        for (InboxTodo inboxTodo : InboxTodo.getStatusVCH()) requestToServer(inboxTodo);
                }
            });
        }
    }

    private void requestToServer(final InboxTodo inboxTodo) {
        Log.d(Constanta.TAG, "Request");
        String url = Constanta.BASE_URL + "inbox/status";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Constanta.TAG, response);
                InboxTodo todo = gson.fromJson(response, InboxTodo.class);

                InboxTodo inboxTodo1 = InboxTodo.find(todo.getPesan2());

                inboxTodo1.setXstatus(todo.getXstatus());
                inboxTodo1.save();
                createNotification(todo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constanta.TAG, VolleyErrorHelper.getMessage(error, getApplicationContext()));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pesan2", inboxTodo.getPesan2());
                return map;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(request, TAG);

    }

    private void createNotification(InboxTodo inboxTodo) {

        String[] message = inboxTodo.getMessage().split("\\.");

        Context context = getApplicationContext();

        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /*String status;

        if (inboxTodo.getStatus().equalsIgnoreCase("berhasil2"))
            status = "Berhasil";
        else if (inboxTodo.getStatus().equalsIgnoreCase("gagal2") ||
                inboxTodo.getStatus().equalsIgnoreCase("gagal-b") ||
                inboxTodo.getStatus().equalsIgnoreCase("gagal"))
            status = "Gagal";
        else
            status = "Berhasil";*/

        String msg = String.format("Pembelian pulsa ke nomor %s dengan nominal %s telah %s", message[1], inboxTodo.getNominal(), inboxTodo._getStatus());

        Notification notification = new android.support.v7.app.NotificationCompat.Builder(context)
                .setContentTitle("Easy Trans")
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setSmallIcon(R.drawable.ic_stat_et)
                .setColor(Color.parseColor("#FF0009"))
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(mpid++, notification);
    }
}
