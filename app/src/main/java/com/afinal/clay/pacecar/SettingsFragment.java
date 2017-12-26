package com.afinal.clay.pacecar;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.NotificationCompat;


public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static Context context = null;
    private Button saveButton;
    private EditText editMake;
    private EditText editModel;
    private EditText editYear;
    private EditText editTank;
    private CheckBox pushCheck;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        context = getActivity();
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        saveButton = (Button) view.findViewById(R.id.SaveButton);
        editMake = (EditText) view.findViewById(R.id.EditMake);
        editModel = (EditText) view.findViewById(R.id.EditModel);
        editYear = (EditText) view.findViewById(R.id.EditYear);
        editTank = (EditText) view.findViewById(R.id.EditTank);
        pushCheck = (CheckBox) view.findViewById(R.id.pushCheck);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getActivity();
                if (editMake.getText().length() != 0) {
                    AsyncTask<Object, Object, Object> saveLog = new AsyncTask<Object, Object, Object>() {
                        DatabaseConnector databaseConnector = new DatabaseConnector(context);

                        @Override
                        protected Object doInBackground(Object... params) {
                            saveSettings();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object result) {
                            //finish();
                        }
                    };
                    saveLog.execute((Object[]) null);
                    editTank.setText("");
                    editYear.setText("");
                    editModel.setText("");
                    editMake.setText("");
                    Toast.makeText(getActivity(),
                            "Settings Saved", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(context);
                    builder.setTitle("Error");
                    builder.setMessage("You must fill out all fields");
                    builder.setPositiveButton(R.string.errorButton, null);
                    builder.show(); // display the Dialog
                } // end else
            } // end method onClick
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void saveSettings(){
        int pushNum = 0;

        if(pushCheck.isChecked()) {
            pushNum = 1;
            ((MainActivity)getActivity()).createNotification();
        }

        DatabaseConnector databaseConnector = new DatabaseConnector(context);


        try {
            databaseConnector.InsertCar(
                    editMake.getText().toString(),
                    editModel.getText().toString(),
                    editYear.getText().toString(),
                    Integer.parseInt(editTank.getText().toString()),
                    pushNum);
        }catch (Exception e){
            Log.d("ERROR","ERROR");
        }
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
        void onFragmentInteraction(Uri uri);
    }


}
