package com.example.stephenvickers.proofofconcept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stephenvickers.proofofconcept.R;

/**
 * Created by stephenvickers on 11/14/16.
 */

public class ScoreFragment extends Fragment {
    //there is another TextView in the score_fragment.xml that is not referenced here
    //as it only shows the word "Score" and never changes.

    /**
     * Private TextView for the {@link ScoreFragment} class
     */
    private TextView mScoreTextView;

    /**
     * Private Button for the {@link ScoreFragment} class
     */
    private Button mRedoButton;

    /**
     * Private static final float for the text size of {@link ScoreFragment#mScoreTextView}
     */
    private static final float TEXT_SIZE = 20;

    /**
     * Public no-args constructor for the {@link ScoreFragment} class
     */
    public ScoreFragment(){}


    /**
     * Override of the {@link Fragment} class for the {@link ScoreFragment} class
     *
     * @param inflater {@link LayoutInflater} reference for the {@link ScoreFragment} class
     * @param container {@link ViewGroup} reference for the {@link ScoreFragment} class
     * @param savedInstanceState {@link Bundle} reference for the {@link ScoreFragment} class
     * @return {@link View} for the new {@link ScoreFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     *          method for the {@link ScoreFragment} class
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //create a view and set the vew to the inflated fragment xml file
        View view = inflater.inflate(R.layout.score_fragment, container, false);

        //get the number correct and the total number of questions from the bundle
        int numberCorrect = getArguments().getInt("NumberCorrect");
        int totalQuestions = getArguments().getInt("TotalQuestions");

        //set up a string builder and build the string for the mScoreTextView
        StringBuilder builder = new StringBuilder();
        builder.append("You got ").append(numberCorrect).append(" out of ").append(totalQuestions).append(" correct");

        //set up the mScoreTextView to a new TextView
        this.mScoreTextView = (TextView) view.findViewById(R.id.score_text_view);

        //set the textSize for the mScoreTextView
        this.mScoreTextView.setTextSize(TEXT_SIZE);

        //set the text for the mScoreTextView using builder.toString() method
        this.mScoreTextView.setText(builder.toString());

        //set the mRedoButton to a new Button
        this.mRedoButton = (Button) view.findViewById(R.id.redo_quiz);

        //set the onClickListener for the mRedoButton
        this.mRedoButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Override of the {@link View.OnClickListener#onClick(View)} method
             *
             * @param view a {@link View} reference fo the {@link View.OnClickListener#onClick(View)}
             *             method
             */
            @Override
            public void onClick(View view) {

                //setup a new QuestionFragment quiz
                QuizFragment quiz = new QuizFragment();

                //start the transition of the to the new fragment for the to the QuestionFragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, quiz, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
