package com.example.stephenvickers.proofofconcept;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.database.*;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends SingleViewActivityFragment{


    private static final String TAG = "MainActivity";

    /**
     * Method to Create the Fragment if it's null
     *
     * @return a new {@link Fragment} for the view
     */
    @Override
    protected Fragment createFragment() {

        return new QuizFragment();
    }

}