/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    //Returns the simple name of the class represented by this Class as defined in the source code.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Called when the activity is starting. This is where most initialization should go:
        // calling setContentView(int) to inflate the activity's UI, using findViewById(int)
        // to programmatically interact with widgets in the UI
        super.onCreate(savedInstanceState);
        //Derived classes must call through to the super class's implementation of this method.
        // If they do not, an exception will be thrown.
        //If the activity is being re-initialized after previously being shut down then this Bundle
        // contains the data it most recently supplied in onSaveInstanceState(Bundle).
        // Note: Otherwise it is null.

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Serhan's Weather App");
        //Set the activity content from a layout resource. The resource will be inflated,
        //adding all top-level views to the activity.

        if (savedInstanceState == null) {               //IF YOU ARE MAKING FIRST TIME INITIALIZATION
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();

            //getSupportFragmentManager (): Return the FragmentManager for interacting with fragments associated with this activity.
            //(FragmentManager:Interface for interacting with Fragment objects inside of an Activity)
            //beginTransaction() : Start a series of edit operations on the Fragments associated with this FragmentManager.
            //add() : Add a fragment to the activity state. This fragment may optionally also have its view
            // (if Fragment.onCreateView returns non-null) into a container view of the activity.
            //commit() : Schedules a commit of this transaction.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);   //getMenuInflater() : method of Activity class
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_map) {
                        openPreferredLocationInMap();
                        return true;
                    }
        if (id == R.id.popup){
            YesNoActivity myDiag = new YesNoActivity();
            myDiag.show(getFragmentManager(), "Diag");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }
}


