package com.roundarch.codetest.part2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.roundarch.codetest.ProgressDialogFragment;
import com.roundarch.codetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EditFragment extends Fragment {
    public static final int RESULT_SAVE = 1;
    public static final String EXTRA_MODEL = "extra_model";

    private DataModel mModel; // TODO - needs to be provided from original Activity/Fragment
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;

//    @BindView(R.id.editText1) EditText edit1;
//    @BindView(R.id.editText2) EditText edit2;
//    @BindView(R.id.editText3) EditText edit3;

    // TODO - This fragment should allow you to edit the fields of DataModel
    // TODO - Then when you click the save button, it should get the DataModel back to the prior activity
    // TODO - so it's up to date
    @Override
    public View
            onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container);
        view.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_save();
            }
        });

        //mModel = getArguments().getParcelable("data");

        edit1 = (EditText)view.findViewById(R.id.editText1);
        edit2 = (EditText)view.findViewById(R.id.editText2);
        edit3 = (EditText)view.findViewById(R.id.editText3);

        setRetainInstance(true);

        return view;
    }

    private void modifyModelOperation(final DataModel model) {
        showLoadingDialog();
        refreshModelFromViews();

        // TODO - you need to implement swapText
        swapText(model);

        // TODO - the BlackBox simulates a slow operation, so you will need to update
        // TODO - this code to prevent it from blocking the main thread

        Double newValue = model.getText3();

        Observable.just(BlackBox.doMagic(model.getText3()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(aDouble -> {
                    model.setText3(aDouble);
                    editComplete();
                });

        model.setText3(newValue);

        // TODO - once the model has been updated, you need to find a good way to
        // TODO - to provide it back to Part2Fragment in the MainActivity

    }

    private void editComplete() {
        Intent ii = getActivity().getIntent().putExtra("newData", mModel);

        getActivity().setResult(Activity.RESULT_OK, ii);
        getActivity().finish();
    }

    private void showLoadingDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ProgressDialogFragment editNameDialog = new ProgressDialogFragment();
        editNameDialog.show(fm, "progress_dialog_fragment");
    }

    private void refreshModelFromViews() {
        // TODO - update our model from the views in our layout

        if(mModel==null){
            mModel = new DataModel();
        }

        mModel.setText1(edit1.getText().toString());
        mModel.setText2(edit2.getText().toString());
        mModel.setText3(Double.valueOf(edit3.getText().toString()));

    }

    // Modifies the data model to swap the values in text1 and text2
    private void swapText(DataModel model) {
        // TODO - swap the text1 and text2 fields on the data model

        String one = model.getText1();
        String two = model.getText2();

        model.setText1(two);
        model.setText2(one);

    }

    public void onClick_save() {
        modifyModelOperation(mModel);
    }

    // TODO - use this method from the Activity to set the model and update
    // TODO - the views in the layout.  You will need to implement the refreshViewsFromModel()
    // TODO - method as well
    public void setModel(DataModel model) {

        if(mModel==null){
            mModel = new DataModel();
        }

        mModel.setText1(model.getText1());
        mModel.setText2(model.getText2());
        mModel.setText3(Double.valueOf(model.getText3()));

        refreshViewsFromModel();
    }

    private void refreshViewsFromModel() {
        // TODO - update our views based on the model's state

        //ButterKnife.bind(this, getView());
//
        edit1.setText(mModel.getText1());
        edit2.setText(mModel.getText2());
        edit3.setText(String.valueOf(mModel.getText3()));

    }

    private class doubleOperation extends AsyncTask<Double, Double, Double>{

        @Override
        protected Double doInBackground(Double... doubles) {

            double newValue = doubles[0];
            BlackBox.doMagic(newValue);
            return newValue;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);

            mModel.setText3(aDouble);
            editComplete();
        }
    }
}
