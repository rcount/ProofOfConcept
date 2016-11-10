package com.example.stephenvickers.proofofconcept;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import java.util.*;

import static android.R.attr.key;
import static android.R.attr.tag;


/**
 * Created by stephenvickers on 10/4/16.
 */
public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";

    /**
     * Variable to hold a {@link Questions} for the Fragment
     */
    private Questions mQuestion = new Questions();

    private List<Questions> mQuestionsSet = new ArrayList<>();

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

    private int mQuestionNumber = 0;

    public QuestionFragment(){}



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
        //this. readQuestions();
        final View view = inflater.inflate(R.layout.question_fragment, container, false);

        //set up the references to the Text field and buttons.
        this.mCheckAnswerButton = (Button) view.findViewById(R.id.check_answer);
        this.mQuestionsTextView = (TextView)view.findViewById(R.id.question_text_view);
        this.mRadioGroup = (RadioGroup) view.findViewById(R.id.answer_radial_group);
        this.mPrevbutton = (Button) view.findViewById(R.id.prev_button);
        this.mNextButton = (Button) view.findViewById(R.id.next_button);
        this.mRadioGroup = (RadioGroup) view.findViewById(R.id.answer_radial_group);
        this.readQuestions(view);



        return view;
    }


    private void setQuestions(final View view){
        this.mQuestion = this.mQuestionsSet.get(this.mQuestionNumber);
        this.mNumberOfAnswers = this.mQuestion.getNumberOfAnswers();
    }

    private void setupQuestion(final View view){

        //Set up the mQuestionTextView and put the Question text into it

        this.mQuestionsTextView.setText(this.mQuestion.getQuestion());


        this.addRadioButtons(view);

        this.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int clickedID) {
                RadioButton clicked = (RadioButton) mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());

                if (clicked == null) {
                    Log.d(TAG, " Clicked is NULL!!!");
                }

                if (clicked != null) {
                    Log.d(TAG, clicked.getText().toString());
                    Log.d(TAG, mQuestion.getCorrectAnswer());
                    isCorrectAnswer(clicked.getText().toString());

                }
            }
        });
    }

    private void addRadioButtons(View view){


        RadioGroup.LayoutParams radioPrams;

        for(int index = 0; index < this.mNumberOfAnswers; index++){

            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(index + 1);
            radioButton.setText(this.mQuestion.getNextAnswer());
            radioPrams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.mRadioGroup.addView(radioButton, index, radioPrams);

        }


    }

    private void increaseQuestionNumber(){
        this.mQuestionNumber++;
    }

    private void decreaseQeustionNumber(){
        this.mQuestionNumber--;
    }

    private void isCorrectAnswer(String answer){
        this.isCorrectChoice = answer.equalsIgnoreCase(this.mQuestion.getCorrectAnswer());
        Log.d(TAG, "Is it Correct = " + this.isCorrectChoice);
    }

    private Questions getQuestion(){
        return this.mQuestion;
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

    private void readQuestions(final View view){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String question = "general_questions";

                for (int index = 0; index < 10; index++) {

                    StringBuilder builder = new StringBuilder();
                    builder.append(index + 1);
                    Questions questions = new Questions();
                    String questionNumber = builder.toString();
                    questions.setQuestion(dataSnapshot.child(question).child(questionNumber).child("question").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_1").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_2").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_3").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_4").getValue(String.class));
                    questions.setCorrectAnswer(dataSnapshot.child(question).child(questionNumber).child("correctAnswer").getValue(String.class));

                    mQuestionsSet.add(questions);
                }

                setQuestions(view);
                setupQuestion(view);




                mCheckAnswerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        printToast();
                        //mRadioGroup.clearCheck();
                    }
                });

                mPrevbutton.setOnClickListener(new View.OnClickListener(){
                    public void onClick(final View view) {
                        mRadioGroup.setOnCheckedChangeListener(null);
                        mRadioGroup.clearCheck();
                        mRadioGroup.removeAllViews();
                        decreaseQeustionNumber();
                        setupQuestion(view);
                        setQuestions(view);
//                        addRadioButtons(mRadioGroup);
                    }
                });

                mNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        mRadioGroup.setOnCheckedChangeListener(null);
                        mRadioGroup.clearCheck();
                        mRadioGroup.removeAllViews();
                        increaseQuestionNumber();
                        setQuestions(view);
                        setupQuestion(view);


//                        addRadioButtons(mRadioGroup);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed ot read value", databaseError.toException());
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }



}
