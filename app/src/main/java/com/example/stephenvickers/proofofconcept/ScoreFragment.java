package com.example.stephenvickers.proofofconcept;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ScoreFragment extends AppCompatActivity {

    private TextView mScoreTextView;

    private Button mRedoButton;

    public ScoreFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score_fragment);

        this.mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        this.mRedoButton = (Button) findViewById(R.id.redo_quiz);


    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.score_fragment, container, false);
//
//        this.mScoreTextView = (TextView) view.findViewById(R.id.score_text_view);
//        this.mRedoButton = (Button) view.findViewById(R.id.redo_quiz);
//
//        return view;
//    }
}
