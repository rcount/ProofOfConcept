package com.example.stephenvickers.proofofconcept;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleQuestionActivity {

    /**
     * Method to Create the Fragment if it's null
     *
     * @return a new {@link Fragment} for the view
     */
    @Override
    protected Fragment createFragment() {
        return new QuestionFragment();
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.question_fragment);
//
//    }
}
