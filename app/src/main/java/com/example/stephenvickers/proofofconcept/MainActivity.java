package com.example.stephenvickers.proofofconcept;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleViewActivityFragment{


    private static final String TAG = "MainActivity";


    /**
     * Method to Create the Fragment if it's null
     *
     * @return a new {@link Fragment} for the view
     */
    @Override
    protected Fragment createFragment() {

//        return new LoginFragment();
//        return new ScoreFragment();
        return new QuizFragment();
    }

}