package com.example.stephenvickers.proofofconcept;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.database.*;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends SingleQuestionActivity{


    private static final String TAG = "MainActivity";

    Questions mQuestion = new Questions();

    //Set<Questions> myQuestions = new HashSet<>();




//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }

    /**
     * Method to Create the Fragment if it's null
     *
     * @return a new {@link Fragment} for the view
     */
    @Override
    protected Fragment createFragment() {

        //TODO: Figure out how to get the values out of fire base.

//        final Questions temp = new Questions();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = database.getReference();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Questions value = dataSnapshot.child("Questions").child("q1").getValue(Questions.class);
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "Failed ot read value", databaseError.toException());
//
//                System.out.println(databaseError.getMessage());
//            }
//        });

//        Bundle bundle = new Bundle();
//        bundle.putString("Question", temp.getQuestion());
//        bundle.putString("answer_1", temp.getNextAnswer());
//        bundle.putString("answer_2", temp.getNextAnswer());
//        bundle.putString("answer_3", temp.getNextAnswer());
//        bundle.putString("answer_4", temp.getNextAnswer());
//        bundle.putString("correctAnswer", temp.getCorrectAnswer());


        return new QuestionFragment();
    }

}