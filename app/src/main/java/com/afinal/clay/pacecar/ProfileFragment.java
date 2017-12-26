package com.afinal.clay.pacecar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView txtMileage;
    private TextView txtOil;
    private TextView txtTires;
    private TextView txtWipers;
    private static Context context = null;
    private int currentMileage;
    private int oilMileage;
    private int tireMileage;
    private int wiperMileage;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        txtMileage = (TextView) view.findViewById(R.id.Txtmileage);
        txtOil = (TextView) view.findViewById(R.id.TxtOilChange);
        txtTires = (TextView) view.findViewById(R.id.TxtTireRotate);
        txtWipers = (TextView) view.findViewById(R.id.TxtWipers);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Step Check","********Entered Profile onResume()*******");
        new LoadMiles().execute();
        new LoadOil().execute();
        new LoadTires().execute();
        new LoadWipers().execute();
    }

    private class LoadMiles extends AsyncTask<Long, Object, Cursor>{
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            Log.d("Step Check","********Entered Profile doInBackground()*******");
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try {
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                currentMileage = Integer.parseInt(initStr);
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString = numberFormat.format(currentMileage);
                txtMileage.setText(numberAsString + " mi");
                Log.d("currentMileage", "********" + currentMileage + "*******");
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadOil extends AsyncTask<Long, Object, Cursor>{
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            Log.d("Step Check","********Entered Profile oil doInBackground()*******");
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE OilCheck = 1");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try {
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                oilMileage = Integer.parseInt(initStr);
                oilMileage = currentMileage - oilMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(oilMileage);
                txtOil.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadTires extends AsyncTask<Long, Object, Cursor>{
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            Log.d("Step Check","********Entered Profile oil doInBackground()*******");
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE TireCheck = 1");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try {
                super.onPostExecute(result);
                Log.d("Step Check", "********Entered Profile onPostExecute()*******");
                result.moveToFirst();
                String initStr = result.getString(0);
                tireMileage = Integer.parseInt(initStr);
                tireMileage = currentMileage - tireMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(tireMileage);
                txtTires.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadWipers extends AsyncTask<Long, Object, Cursor>{
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            Log.d("Step Check","********Entered Profile oil doInBackground()*******");
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE WiperCheck = 1");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                Log.d("Step Check","********Entered Profile onPostExecute()*******");
                result.moveToFirst();
                String initStr = result.getString(0);
                wiperMileage = Integer.parseInt(initStr);
                wiperMileage = currentMileage - wiperMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(wiperMileage);
                txtWipers.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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