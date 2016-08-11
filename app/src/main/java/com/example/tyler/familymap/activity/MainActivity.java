package com.example.tyler.familymap.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.fragment.LoginFragment;
import com.example.tyler.familymap.fragment.MapFragment;
import com.example.tyler.familymap.model.ModelData;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (ModelData.getInstance().loggedIn)
        {
            fragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment) //call .replace in switchFragment
                    .commit();
        }
        if (fragment == null)
        {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    //Make function SwitchFragment, which will be called in on proxy result when the user is fully logged in and synced
    public void switchFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        //MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.fragment_container);

        MapFragment mapFragment = new MapFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .commit();

        ModelData.getInstance().loggedIn = true;
    }



}



