package com.afinal.clay.pacecar;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

public class LogFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Context context = null;
    private EditText mileEditText;
    private EditText gasEditText;
    private CheckBox oilCheck;
    private CheckBox tireCheck;
    private CheckBox fluidCheck;
    private CheckBox gasCheck;
    private CheckBox wiperCheck;
    private CheckBox tuneCheck;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LogFragment() {
        // Required empty public constructor
    }

    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("**********STEP CHECK-IN**********", "OnCreateView()");
        context = getActivity();
        View view =  inflater.inflate(R.layout.fragment_log, container, false);
        mileEditText = (EditText) view.findViewById(R.id.edit_log_hint);
        gasEditText = (EditText) view.findViewById(R.id.edit_log_hint2);
        oilCheck = (CheckBox) view.findViewById(R.id.oilCheck);
        tireCheck = (CheckBox) view.findViewById(R.id.tireCheck);
        fluidCheck = (CheckBox)  view.findViewById(R.id.fluidCheck);
        gasCheck = (CheckBox) view.findViewById(R.id.gasCheck);
        wiperCheck = (CheckBox) view.findViewById(R.id.wiperCheck);
        tuneCheck = (CheckBox) view.findViewById(R.id.tuneCheck);

        Button logButton = (Button) view.findViewById(R.id.logButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("******USER INPUT******", "Log button clicked");
                context = getActivity();
                if (mileEditText.getText().length() != 0) {
                    AsyncTask<Object, Object, Object> saveLog = new AsyncTask<Object, Object, Object>() {
                        DatabaseConnector databaseConnector = new DatabaseConnector(context);

                        @Override
                        protected Object doInBackground(Object... params) {
                            saveLog();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object result) {
                            //finish();
                        }
                    };
                    saveLog.execute((Object[]) null);
                    mileEditText.setText("");
                    gasEditText.setText("");
                    oilCheck.setChecked(false);
                    tireCheck.setChecked(false);
                    fluidCheck.setChecked(false);
                    gasCheck.setChecked(false);
                    wiperCheck.setChecked(false);
                    tuneCheck.setChecked(false);
                    Toast.makeText(getActivity(),
                            "Data Logged", Toast.LENGTH_SHORT).show();
                } else {
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context);

                    // set dialog title & message, and provide Button to dismiss
                    builder.setTitle(R.string.errorTitle);
                    builder.setMessage(R.string.errorMessage);
                    builder.setPositiveButton(R.string.errorButton, null);
                    builder.show(); // display the Dialog
                } // end else
            } // end method onClick
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void saveLog(){
        int oil = 0;
        int tire = 0;
        int fluid = 0;
        int gas = 0;
        int wiper = 0;
        int tune = 0;
        int noGas = 0;

        if(oilCheck.isChecked())
            oil = 1;
        if(tireCheck.isChecked())
            tire = 1;
        if(fluidCheck.isChecked())
            fluid = 1;
        if(gasCheck.isChecked())
            gas = 1;
        if(wiperCheck.isChecked())
            wiper = 1;
        if(tuneCheck.isChecked())
            tune = 1;
        //TODO: Dear future clay. This may not always work, sorry.
        if (gasEditText.getText().length() > 1){
            noGas = Integer.parseInt(gasEditText.getText().toString());
        }

        DatabaseConnector databaseConnector = new DatabaseConnector(context);

        databaseConnector.InsertLog(
                Integer.parseInt(mileEditText.getText().toString()),
                "1970-01-01 00:00:00.000",
                noGas,
                gas, oil, tire,
                wiper, tune, fluid);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}