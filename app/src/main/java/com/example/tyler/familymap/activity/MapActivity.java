package com.example.tyler.familymap.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.fragment.LoginFragment;
import com.example.tyler.familymap.fragment.MapFragment;

/**
 * Created by Tyler on 8/3/2016.
 */
public class MapActivity extends AppCompatActivity {

    /*
    The map activity houses the map fragment and is the same functionally, except that when it is created, the
    map is centered on the event that was clicked and the marker is already pressed. Up button goes to the activity
    that created this and go to top button returns to the main activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment fragment = new MapFragment();
        String startEventID = getIntent().getStringExtra("eventID");
        fragment.setmStartEventID(startEventID);
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment) //call .replace in switchFragment
                .commit();


        //make the menu bar with just the up button and the go home button
        //enable the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return(true);
            case R.id.menu_item_go_to_top:
                //go to the top, destroying all activities on the way to the mainActivity
                Toast.makeText(getApplicationContext(), "go to top button pressed", Toast.LENGTH_SHORT).show();
                if(NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
