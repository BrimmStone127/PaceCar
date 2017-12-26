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

import java.text.NumberFormat;
import java.util.Locale;

public class StatsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private TextView statsMileage;
    private TextView statsCost;
    private TextView statsOil;
    private TextView statsTires;
    private TextView statsTune;
    private TextView statsWipers;
    private TextView statsFluid;
    private TextView carData;

    private int currentMileage;
    private int oilMileage;
    private int tireMileage;
    private int wiperMileage;
    private int tuneMileage;
    private static Context context = null;
    private String carString = "";

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view =  inflater.inflate(R.layout.fragment_stats, container, false);
        statsMileage = (TextView) view.findViewById(R.id.statsMileage);
        statsOil = (TextView) view.findViewById(R.id.stats_oil);
        statsTires = (TextView) view.findViewById(R.id.stats_tires);
        statsTune = (TextView) view.findViewById(R.id.stats_tune);
        statsWipers = (TextView) view.findViewById(R.id.stats_wiper);
        statsFluid = (TextView) view.findViewById(R.id.stats_fluid);
        carData = (TextView) view.findViewById(R.id.carData);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Step Check","********Entered Profile onResume()*******");
        new LoadMiles().execute();
        new LoadOil().execute();
        new LoadTire().execute();
        new LoadTune().execute();
        new LoadWiper().execute();
        new LoadYear().execute();
        new LoadMake().execute();
        new LoadModel().execute();
    }

    private class LoadMiles extends AsyncTask<Long, Object, Cursor> {
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
                statsMileage.setText(numberAsString + " mi");
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadOil extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE OilCheck = 1");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                oilMileage = Integer.parseInt(initStr);
                oilMileage = currentMileage - oilMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(oilMileage);
                statsOil.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadTire extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE TireCheck = 1");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                tireMileage = Integer.parseInt(initStr);
                tireMileage = currentMileage - tireMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(oilMileage);
                statsTires.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadTune extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE TuneCheck = 1");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                tuneMileage = Integer.parseInt(initStr);
                tuneMileage = currentMileage - tuneMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(oilMileage);
                statsTune.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadWiper extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatest("Mileage", "WHERE WiperCheck = 1");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                String initStr = result.getString(0);
                wiperMileage = Integer.parseInt(initStr);
                wiperMileage = currentMileage - wiperMileage;
                NumberFormat numberFormat2 = NumberFormat.getNumberInstance(Locale.US);
                String numberAsString2 = numberFormat2.format(oilMileage);
                statsWipers.setText(numberAsString2);
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadYear extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatestCar("Year", "WHERE _id > 0");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                carString += result.getString(0)+" ";
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadMake extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatestCar("Make", "WHERE _id > 0");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                carString += result.getString(0)+" ";
                result.close();
                databaseConnector.Close();
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
        }
    }

    private class LoadModel extends AsyncTask<Long, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(context);
        @Override
        protected Cursor doInBackground(Long... params){
            databaseConnector.Open();
            Cursor miles = databaseConnector.getLatestCar("Model", "WHERE _id > 0");
            //Cursor miles = databaseConnector.getMax("Mileage");
            return miles;
        }
        @Override
        protected void onPostExecute(Cursor result){
            try{
                super.onPostExecute(result);
                result.moveToFirst();
                carString += result.getString(0);
                result.close();
                databaseConnector.Close();
                carData.setText(carString);
            }catch (Exception e){
                Log.d("ERROR",e.getMessage());
            }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
