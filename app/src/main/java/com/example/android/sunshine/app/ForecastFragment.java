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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;  //initializing the ArrayAdapter holds Strings

    public ForecastFragment() {     //no-arg constructor , no needed actually but just in case if we want to automate it.
    }

    @Override
    public void onCreate(Bundle savedInstanceState){    //Called when the activity is starting
    //If the activity is being re-initialized after previously being shut down then this Bundle
    //contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
        super.onCreate(savedInstanceState);   //Derived classes must call through to the super class's implementation of this method
        //Added this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        //Report that this fragment would like to participate in populating the options menu by
        //receiving a call to onCreateOptionsMenu(Menu, MenuInflater) and related methods.
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        //Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu.
        // For this method to be called, you must have first called setHasOptionsMenu(boolean).
        inflater.inflate(R.menu.forecastfragment, menu);
        // MenuInflater: This class is used to instantiate menu XML files into Menu objects.
        //public void inflate (int menuRes, Menu menu) : Inflate a menu hierarchy from the specified XML resource.

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //notifies us when menu item selected
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //public abstract long getItemId (int position) : Get the row id associated with the specified position in the list.
        // Parameters : position , The position of the item within the adapter's data set whose row id we want.


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {    //if Refresh button is clicked .
            updateWeather();
            return true;                //for now we just return true.
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Called to have the fragment instantiate its user interface view. This is optional,
        // Returns the View for the fragment's UI, or null.



        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mForecastAdapter =
                new ArrayAdapter<String>(  // Constructor :
                                           // ArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects)
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_forecast, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        new ArrayList<String>());

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //public View inflate (int resource, ViewGroup root, boolean attachToRoot):

        // Get a reference to the ListView, and attach this adapter to it. we cast it from R and indicate it's a ListView by CASTING.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int position, long l){
                String forecast = mForecastAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
                //Complete version : Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }

    private void updateWeather(){
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(),mForecastAdapter);  //initiate FetchWeatherTask
        //removed the hardcoded weatherTask.execute("94043") here
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateWeather();
    }



}
