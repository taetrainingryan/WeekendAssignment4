package com.roundarch.codetest.part3;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roundarch.codetest.MainActivity;
import com.roundarch.codetest.part3.model.ZipCodeResultModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Part3Service extends IntentService {


    private final String TAG = this.getClass().getSimpleName();

    // TODO - we can use this as the broadcast intent to filter for in our Part3Fragment
    public static final String ACTION_SERVICE_DATA_UPDATED = "com.roundarch.codetest.ACTION_SERVICE_DATA_UPDATED";

    private List<Map<String,String>> data = null;

    public Part3Service() {
        super("Part 3 Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO - this interface needs to be implemented to allow consumers
        // TODO - access to the data we plan to download
        return new Part3ServiceBinder();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        updateData();
        //broadcastDataUpdated(returned);
       //Toast.makeText(getBaseContext(), returned, Toast.LENGTH_SHORT).show();

        try {
            Request request = new Request.Builder()
                    .url("http://gomashup.com/json.php?fds=geo/usa/zipcode/state/VT")
                    .header("Accept", "application/json")
                    .get()
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            String body = response.body().string().replace("(", "").replace(")", "");

            Log.i("AAAAA", "Json data length " + body.length() + " chars.");

            Gson gson = new GsonBuilder().create();
            gson.fromJson(body, ZipCodeResultModel.class);
            Observable.just(gson.fromJson(body, ZipCodeResultModel.class)).subscribe(zipCodeResultModel -> Log.i("TAG", String.valueOf(zipCodeResultModel.getResult().size())));
        }

        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void updateData() {
        // TODO - start the update process for our data

        String data = "";

        try {
            Request request = new Request.Builder()
                    .url("http://gomashup.com/json.php?fds=geo/usa/zipcode/state/VT")
                    .header("Accept", "application/json")
                    .get()
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            String body = response.body().string().replace("(", "").replace(")", "");

            Log.i("AAAAA", "Json data length " + body.length() + " chars.");

            data = body;
            Gson gson = new GsonBuilder().create();
            gson.fromJson(body, ZipCodeResultModel.class);
            Observable.just(gson.fromJson(body, ZipCodeResultModel.class)).subscribe(zipCodeResultModel -> Log.i("TAG", String.valueOf(zipCodeResultModel.getResult().size())));
        }

        catch(Exception e){
            e.printStackTrace();
        }

       // return data;
    }

    private void broadcastDataUpdated(String value) {
        // TODO - send the broadcast

        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra("data", value);
    }

    public final class Part3ServiceBinder extends Binder {
        // TODO - we need to expose our public IBinder API to clients
    }

    // TODO - eventually we plan to request JSON from the network, so we need
    // TODO - to implement a way to perform that off the main thread.  Then, once we
    // TODO - have the data we can parse it as JSON (using standard Android APIs is fine)
    // TODO - before finally returning to the main thread to store our data on the service.
    // TODO - Keep in mind that the service will keep a local copy and will need an interface
    // TODO - to allow clients to access it.

    // TODO - if you need a simple JSON endpoint, you can obtain the ZIP codes for the state
    // TODO - of Illinois by using this URL:
    //
    // TODO - http://gomashup.com/json.php?fds=geo/usa/zipcode/state/IL

}
