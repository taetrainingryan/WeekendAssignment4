package com.roundarch.codetest.part2;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.roundarch.codetest.R;

public class EditActivity extends FragmentActivity{

    DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        EditFragment fragment = (EditFragment) getSupportFragmentManager().findFragmentById(R.id.edit_fragment);

        Bundle bundle = getIntent().getExtras();
        DataModel data = (DataModel) bundle.get("data");

        fragment.setModel(data);
//
//        EditFragment fragment = new EditFragment();
//        fragment.setModel(data);

//        Bundle bundle = getIntent().getExtras();
//        DataModel data = (DataModel) bundle.get("data");
//
////        Bundle bundle = new Bundle();
////        bundle.putParcelable("data", data);
//
//        EditFragment fragment = new EditFragment();
//        fragment.setModel(data);
        //fragment.setModel(data);


        // TODO - you will need to obtain the model object provided to this activity and provide it to the EditFragment
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        Bundle bundle = getIntent().getExtras();
        dataModel = (DataModel) bundle.get("data");

        return super.onCreateView(parent, name, context, attrs);
    }

}
