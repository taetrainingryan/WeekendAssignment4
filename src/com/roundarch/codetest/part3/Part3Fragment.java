package com.roundarch.codetest.part3;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.roundarch.codetest.R;
import com.roundarch.codetest.part3.model.Result;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Part3Fragment extends Fragment {

    public static final String DATA_RECEIVED = "com.roundarch.codetest.part3.DATA_RECEIVED";
    Part3BroadcastReceiver dataReceiver;
    ArrayList<Result> listData;
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part3, null);

        View emptyView = (View) view.findViewById(R.id.empty_textview);
        listView = (ListView) view.findViewById(R.id.part3_listview);
        listView.setEmptyView(emptyView);

        // TODO - the listview will need to be provided with a source for data
        // TODO - (optional) you can set up handling to list item selection if you wish

        dataReceiver = new Part3BroadcastReceiver();

        // Send intent to start IntentService (Part3IntentService)
        Intent fetchDataIntent = new Intent(this.getContext(), BackgroundService.class);
        getActivity().startService(fetchDataIntent);

        return view;
    }

    private void setListData(ArrayList<Result> data) {
        this.listData = data;
    }

    private void refreshListView() {
        final ListAdapter adapter = new ListAdapter(this.getContext(), listData);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO - when the fragment resumes, it would be a good time to register to receieve broadcasts
        // TODO - from the service.  The broadcast will serve as a way to inform us that data is available
        // TODO - for consumption

        // TODO - this is also a good place to leverage the Service's IBinder interface to tell it you want
        // TODO - to refresh data

        // Register BroadcastReceiver
        LocalBroadcastManager.getInstance(this.getActivity())
                .registerReceiver(dataReceiver, new IntentFilter(DATA_RECEIVED));
        this.getActivity().registerReceiver(dataReceiver, new IntentFilter(DATA_RECEIVED));

    }

    @Override
    public void onPause() {
        super.onPause();

        this.getActivity().unregisterReceiver(dataReceiver);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use this to start and trigger a service
        Intent i = new Intent(this.getContext(), BackgroundService.class);

// potentially add data to the intent
//        i.putExtra("KEY1", "Value to be used by the service");
        this.getContext().startService(i);
    }

    public class Part3BroadcastReceiver extends BroadcastReceiver {

        private static final String TAG = "Part3BroadcastReceiver";
        private Part3Fragment target;

        public Part3BroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() == Part3Fragment.DATA_RECEIVED) {
                ArrayList<Result> sentData = intent.getParcelableArrayListExtra("data_list");
                if (sentData != null) {
                    setListData(sentData);
                    refreshListView();
                } else {
                    Toast.makeText(context, "NO DATA", Toast.LENGTH_SHORT).show();
                }
            }

            // TODO - our listView needs a source of data, and here might be a good place to create that
            // TODO - we also need a means of responding to the Broadcasts sent by our Service

        }
    }
}
