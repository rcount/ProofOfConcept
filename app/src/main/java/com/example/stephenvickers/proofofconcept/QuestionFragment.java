package com.example.stephenvickers.proofofconcept;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by stephenvickers on 10/4/16.
 */
public class QuestionFragment extends Fragment {

    private static final String TAG = "MainActivity";

    /**
     * Variable to hold a {@link Questions} for the Fragment
     */
    private Questions mQuestions;

    private ArrayList<Questions> mQuestionsSet = new ArrayList<>();

    private int listIndex;

    /**
     * Variable to hold a TextView for the question
     */
    private TextView mQuestionsTextView;

    private RadioGroup mRadioGroup;

    /**
     * Variable to hold a button to check if you have the right answer
     */
    private Button mCheckAnswerButton;

    /**
     * Variable to hold the Previous button to move to the Previous Question
     */
    private Button mPrevbutton;

    /**
     * Variable to hold the Next button to move to the Next Question
     */
    private Button mNextButton;

    /**
     * Variable to hold whether or not the person choose the correct choice or not
     */
    private boolean isCorrectChoice;

    private int mNumberOfAnswers;


    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link Fragment#onAttach(Activity)} and before
     * {@link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Questions q1 = new Questions("What is the rule of four?");
//        q1.pushAnswer("That four justices must decide the opinion of a case.");
//        q1.pushAnswer("Four justice must decide to take a case.");
//        q1.pushAnswer("There needs to be four parties in every case.");
//        q1.pushAnswer("There must be four constitutional issues.");
//        q1.setCorrectAnswer("Four justice must decide to take a case.");
//
//        this.mQuestionsSet.add(q1);
//
//        Questions q2 = new Questions("What is writ of certiorari?");
//        q2.pushAnswer("A change in administrative law by the court");
//        q2.pushAnswer("A demand by the court to end an unconstitutional government action.");
//        q2.pushAnswer("A request from the Supreme Court to a lower court for records of a case.");
//        q2.pushAnswer("A stay that prevents the execution of a prisoner.");
//        q2.setCorrectAnswer("A request from the Supreme Court to a lower court for records of a case.");
//
//        this.mQuestionsSet.add(q2);
//
//        this.listIndex = 1;

        FirebaseDatabase dataBase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = dataBase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed ot read value", databaseError.toException());
            }
        });


        this.mQuestions = new Questions("What is my Name?");
        this.mQuestions.pushAnswer("Stephen");
        this.mQuestions.pushAnswer("Mr. Awesome");
        this.mQuestions.setCorrectAnswer("Mr. Awesome");

        this.mNumberOfAnswers = this.mQuestions.getNumberOfAnswers();

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.question_fragment, container, false);

//        this.setQuestions();
        this.setupQuestion(view);



        //Set up the RadioGroup for the Buttons and add a serOnCheckedChaneListener
        this.mRadioGroup = (RadioGroup) view.findViewById(R.id.answer_radial_group);
        this.addRadioButtons(this.mRadioGroup);
        this.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int clickedID) {
                 RadioButton btn = (RadioButton)view.findViewById(mRadioGroup.getCheckedRadioButtonId());
//                 mChoiceText.setText(mQuestions.getCorrectAnswer());
//                 mChoiceText.setText(btn.getText().toString());
                 isCorrectAnswer(btn.getText().toString());
             }
        });

        this.mCheckAnswerButton = (Button)view.findViewById(R.id.check_answer);
        this.mCheckAnswerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                printToast();

            }
        });

        this.mPrevbutton = (Button)view.findViewById(R.id.prev_button);
        this.mPrevbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });

        this.mNextButton = (Button)view.findViewById(R.id.next_button);
        this.mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               listIndex++;
            }
        });


        return view;
    }

    private void setQuestions(){
        while(this.listIndex < this.mQuestionsSet.size()) {
            this.mQuestions = this.mQuestionsSet.get(this.listIndex);

        }
    }

    private void setupQuestion(View view){

        //Set up the mQuestionTextView and put the Question text into it
        this.mQuestionsTextView = (TextView)view.findViewById(R.id.question_text_view);
        this.mQuestionsTextView.setText(this.mQuestions.getQuestion());
    }

    private void addRadioButtons(RadioGroup radioGroup){

        RadioGroup.LayoutParams radioPrams;

        for(int index = 0; index < mNumberOfAnswers; index++){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(index + 1);
            radioButton.setText(this.mQuestions.getNextAnswer());
            radioPrams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(radioButton, index, radioPrams);


        }
    }


    private void isCorrectAnswer(String answer){
        this.isCorrectChoice = answer.equalsIgnoreCase(this.getQuestions().getCorrectAnswer());
    }

    private Questions getQuestions(){
        return this.mQuestions;
    }

    private void printToast(){
        if (this.isCorrectChoice){
            Toast.makeText(getContext().getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext().getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearChecked(){

        this.mRadioGroup.clearCheck();

    }



}
