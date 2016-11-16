package com.example.stephenvickers.proofofconcept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by stephenvickers on 10/13/16.
 */

/**
 * Abstract class to make a new fragment for the quiz
 */
public abstract class SingleViewActivityFragment extends FragmentActivity {

    /**
     * Method to Create the Fragment if it's null
     *
     * @return a new {@link Fragment} for the view
     */
    protected abstract Fragment createFragment();

    /**
     * Perform initialization of all fragments and loaders.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the content on the screen to the question fragment
        setContentView(R.layout.activity_main);

        //create the FragmentManager and the Fragment for the class
        FragmentManager fragManager = getSupportFragmentManager();
        Fragment fragment = fragManager.findFragmentById(R.id.fragment_container);

        //if the fragment is null then create a new fragment.
        if (fragment == null){

            fragment = createFragment();
            fragManager.beginTransaction().add(R.id.fragment_container, fragment).commit();

        }//else fragment is valid .doNothing()

    }
}
