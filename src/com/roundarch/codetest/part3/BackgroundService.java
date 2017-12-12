package com.roundarch.codetest.part3;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roundarch.codetest.part3.model.Result;
import com.roundarch.codetest.part3.model.ZipCodeResultModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Ryan on 11/12/2017.
 */

public class BackgroundService extends IntentService {

    public BackgroundService() {
        super(BackgroundService.class.getName());
    }

    public BackgroundService(String name) {
        super(BackgroundService.class.getName());
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i(TAG, "Intent received by Part3IntentService");

        try {
            Request request = new Request.Builder()
                    .url("http://gomashup.com/json.php?fds=geo/usa/zipcode/state/VT")
                    .header("Accept", "application/json")
                    .get()
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            String body = response.body().string().replace("(", "").replace(")", "");

            Gson gson = new GsonBuilder().create();
            gson.fromJson(body, ZipCodeResultModel.class);
            Observable.just(gson.fromJson(body, ZipCodeResultModel.class))
                    .subscribe(zipCodeResultModel -> {
                        Log.i("TAG", String.valueOf(zipCodeResultModel.getResult().size()));
                        sendBroadcast(zipCodeResultModel.getResult());
                    });
        }

        catch(Exception e){
            e.printStackTrace();
            Log.i("TAG", "ERROR");
        }

    }

    private void sendBroadcast(List<Result> returnList) {
        // Send Intent to BroadcastReceiver

        Intent returnIntent = new Intent();
        returnIntent = new Intent(Part3Fragment.DATA_RECEIVED);
        returnIntent.putParcelableArrayListExtra("data_list", new ArrayList<>(returnList));
        LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(returnIntent);
    }
}
