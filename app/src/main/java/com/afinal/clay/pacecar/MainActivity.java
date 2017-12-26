package com.afinal.clay.pacecar;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements
        ProfileFragment.OnFragmentInteractionListener,
        StatsFragment.OnFragmentInteractionListener,
        LogFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Welcome to PaceCar");
        builder.setMessage("If this is your first time using the app please log your mileage on the log page first.");
        builder.setPositiveButton(R.string.errorButton, null);
        builder.show(); // display the Dialog

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.nav_profile);

        //Open homefragment initially
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new ProfileFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            builder.setTitle("About");
            builder.setMessage("PaceCar version: V.1.0.2\nCreated by Clayton Brimm 12/10/17\n");
            builder.setPositiveButton(R.string.errorButton, null);
            builder.show(); // display the Dialog
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Create fragment object
        Fragment fragment = null;

        if(id == R.id.nav_profile){
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_profile){
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_stats){
            fragment = new StatsFragment();
        } else if (id == R.id.nav_log){
            fragment = new LogFragment();
        } else if (id == R.id.nav_settings){
            fragment = new SettingsFragment();
        }

        //Fragment change code
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        //Close the drawer after selection
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createNotification(){
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.car)
                .setContentTitle("PaceCar")
                .setContentText("You haven't logged your mileage in a while.");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        long[] pattern = {300, 400, 500, 600, 500, 400, 300};
        builder.setVibrate(pattern);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
