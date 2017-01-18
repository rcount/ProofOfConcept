package com.example.stephenvickers.proofofconcept;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.AppCompatBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.io.Serializable;
import java.util.*;



/**
 * Created by stephenvickers on 10/4/16.
 */
public class QuizFragment extends Fragment {

    private static final String TAG = "QuizFragment";

    private static final int RC_SIGN_IN = 1;

    /**
     * Variable to hold a {@link Questions} for the Fragment
     */
    private Questions mQuestion = new Questions();

    private List<Questions> mQuestionsSet = new ArrayList<>();

    private int mNumberRight = 0;

    /**
     * Variable to hold a TextView for the question
     */
    private TextView mQuestionsTextView;

    /**
     * Private variable for the {@link RadioGroup} used in the {@link QuizFragment Class}
     */
    private RadioGroup mRadioGroup;

    private ProgressBar mSpinner;

    /**
     * Variable to hold a button to check if you have the right answer
     */
    private FloatingActionButton mCheckAnswerButton;

    /**
     * Variable to hold the Previous button to move to the Previous Question
     */
    private Button mPrevButton;

    /**
     * Variable to hold the Next button to move to the Next Question
     */
    private FloatingActionButton mNextButton;

    /**
     * Variable to hold whether or not the person choose the correct choice or not
     */
    private boolean isCorrectChoice;

    /**
     * Private int to hold the number of Answers for the currend {@link QuizFragment#mQuestion}
     */
    private int mNumberOfAnswers;

    /**
     * private int for the number of questions in the {@link QuizFragment}
     */
    private int mQuestionNumber;

    /**
     * Private static string for the Question_List of the {@link QuizFragment}
     */
    private static final String QUESTION_LIST = "QuestionList";

    /**
     * Private static string for the Current_Question of the {@link QuizFragment}
     */
    private static final String CURRENT_QUESTION = "CurrentQuestion";

    /**
     * Private static string for the Number of questions correct of the {@link QuizFragment}
     */
    private static final String NUMBER_CORRECT = "NumberCorrect";

    /**
     * Private static int for the text size for the answers of the {@link Questions}
     * for the {@link QuizFragment}
     */
    private static final int TEXT_SIZE = 14;

    /**
     * Private reference to the {@link FirebaseAuth}
     * Reference calls back to the Firebase Authentication
     */
    private FirebaseAuth mFirebaseAuth;

    /**
     * Private reference to the {@link FirebaseAuth.AuthStateListener}
     * Makes a call back to Firebase Authentication to get the AuthStateListener
     * If AuthStateListener in nil, or changes it fires off the Login Screen
     */
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    /**
     * Private reverence to a {@link View} to make a new view for the {@link QuizFragment}
     */
    private View mView;

    /**
     * Default constructor for the {@link QuizFragment} Class
     */
    public QuizFragment(){}

    /**
     * Public method to make a new instance of the {@link QuizFragment} class
     *
     * @param bundle    A {@link Bundle} reference that is passed into the method to
     *                  get the new parameters for the {@link QuizFragment#newInstance(Bundle)}
     * @return          Return a new reference to the {@link QuizFragment} class
     */
    public static QuizFragment newInstance(Bundle bundle){
        QuizFragment quiz = new QuizFragment();
        quiz.setArguments(bundle);

        return quiz;
    }

    /**
     * @Override of the {@link Fragment#onPause()} method for the {@link QuizFragment}
     */
    @Override
    public void onPause() {
        super.onPause();

        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION_LIST, (Serializable) this.mQuestionsSet);
        Log.i(TAG, "this.mQuestionsSet added to Bundle");
        bundle.putInt(CURRENT_QUESTION, this.mQuestionNumber);
        Log.i(TAG, "this.mQuestionNumber added to Bundle");
        bundle.putInt(NUMBER_CORRECT, this.mNumberRight);
        Log.i(TAG, "this.mNumberRight added to Bundle");

        if (mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }


    }

    /**
     * @Override of the {@link Fragment#onSaveInstanceState(Bundle)} for the {@link QuizFragment}
     *
     * @param outState  a reference of a {@link Bundle} that saves the parameters for the {@link QuizFragment} class
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable(QUESTION_LIST, (Serializable) this.mQuestionsSet);
//        Log.i(TAG, "this.mQuestionsSet added to Bundle in onSaveInstanceState");
        outState.putInt(CURRENT_QUESTION, this.mQuestionNumber);
        Log.i(TAG, "this.mQuestionNumber added to Bundle in onSaveInstanceState");
        outState.putInt(NUMBER_CORRECT, this.mNumberRight);
        Log.i(TAG, "this.mNumberRight added to Bundle in onSavedInstanceState");

    }

    /**
     * @Overide of the {@link Fragment#onViewStateRestored(Bundle)} for the {@link QuizFragment} class
     *
     * @param savedInstanceState    a reference to a {@link Bundle} that holds the parameters to be
     *                              passed to the new reference to of the {@link QuizFragment}
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
//            this.mQuestionsSet.clear();
//            this.mQuestionsSet = (List<Questions>) savedInstanceState.getSerializable(QUESTION_LIST);
            this.mQuestionNumber = savedInstanceState.getInt(CURRENT_QUESTION);
            StringBuilder builder = new StringBuilder();
            builder.append(this.mQuestionNumber);
            Log.i(CURRENT_QUESTION, builder.toString() + "in onViewStateRestored");

            this.mNumberRight = savedInstanceState.getInt(NUMBER_CORRECT);
            builder = new StringBuilder();
            builder.append(this.mNumberRight);
            Log.i(NUMBER_CORRECT, builder.toString() + "in onViewStateRestored");
        }
    }

    /**
     * @Override of the {@link Fragment#onResume()} method for the {@link QuizFragment} class
     * Loads the {@link QuizFragment#mAuthStateListener} when the {@link QuizFragment} class is called
     */
    @Override
    public void onResume() {

        super.onResume();

        this.mFirebaseAuth.addAuthStateListener(this.mAuthStateListener);


        if(getArguments() != null) {

            this.mQuestionNumber = getArguments().getInt(CURRENT_QUESTION);
            StringBuilder builder = new StringBuilder();
            builder.append(this.mQuestionNumber);
            Log.i(CURRENT_QUESTION, builder.toString() + "in onResume");

            this.mNumberRight = getArguments().getInt(NUMBER_CORRECT);
            builder = new StringBuilder();
            builder.append(this.mNumberRight);
            Log.i(NUMBER_CORRECT, builder.toString() + "in onResume");


            setQuestions();
            setupQuestion(this.mView);
        }

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

        this.mView = inflater.inflate(R.layout.quiz_fragment, container, false);


        //set up the references to the Text field and buttons.
        this.mCheckAnswerButton = (FloatingActionButton) this.mView.findViewById(R.id.check_answer);
        this.mQuestionsTextView = (TextView)this.mView.findViewById(R.id.question_text_view);
        this.mRadioGroup = (RadioGroup) this.mView.findViewById(R.id.answer_radial_group);
        this.mRadioGroup = (RadioGroup) this.mView.findViewById(R.id.answer_radial_group);
        this.mSpinner = (ProgressBar)this.mView.findViewById(R.id.progress_bar);
        this.mNextButton = (FloatingActionButton) this.mView.findViewById(R.id.next_button);
        this.mNextButton.setVisibility(View.GONE);

        //set the visibility of everything except the spinner to View.GONE
        this.setVisibility(View.GONE);

        //read the questions into the mQuestionSet
        this.readQuestions(this.mView);

        this.mFirebaseAuth = FirebaseAuth.getInstance();

        this.mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //user signed in
                    Toast.makeText(getContext().getApplicationContext(), "Signed In", Toast.LENGTH_SHORT).show();
                }
                else {
                    //user signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.AppTheme)
                                    .setProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN
                    );
                }
            }
        };


        //return the view for the onCreateView
        return this.mView;
    }

    /**
     * Get a question from the {@link QuizFragment#mQuestionsSet}
     * and set the {@link QuizFragment#mNumberOfAnswers} from the current {@link QuizFragment#mQuestion}
     */
    private void setQuestions(){
        this.mQuestion = this.mQuestionsSet.get(this.mQuestionNumber);
        this.mNumberOfAnswers = this.mQuestion.getNumberOfAnswers();
    }

    /**
     * Set the view visibility for the following parameters
     * {@link QuizFragment#mCheckAnswerButton}
     * {@link QuizFragment#mQuestionsTextView}
     * {@link QuizFragment#mRadioGroup}
     * {@link QuizFragment#mPrevButton}
     * {@link QuizFragment#mNextButton}
     *
     * @param view int variable that is either passed View.Gone or View.VISIBLE
     */
    private void setVisibility(int view){
        this.mCheckAnswerButton.setVisibility(view);
        this.mQuestionsTextView.setVisibility(view);
        this.mRadioGroup.setVisibility(view);
//        this.mPrevButton.setVisibility(view);
        this.mNextButton.setVisibility(View.GONE);

    }

    /**
     * private method to setup the current question for the {@link QuizFragment} from the {@link QuizFragment#mQuestion}
     *
     * @param view a final {@link View} reference for the current question
     */
    private void setupQuestion(final View view){

        //Set up the mQuestionTextView and put the Question text into it
        this.mQuestionsTextView.setText(this.mQuestion.getQuestion());

        //set up the mRadioButtons for the mRadioGroup for the QuizFragment class
        this.addRadioButtons(view);

        //set the onClickListener for the mRadioGroup
        this.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            /**
             * Override of the {@link RadioGroup.OnClickListener}
             *
             * @param radioGroup {@link RadioGroup} reference for the onCheckedChanged listener
             * @param clickedID {@link RadioGroup} reference for the {@link RadioButton} that was clicked inside of the the
             *                  RadioGroup
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int clickedID) {

                //set a reference to the RadioButton that was clicked inside the RadioGroup
                RadioButton clicked = (RadioButton) mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());


                //if the clicked button number is null then Log it to Logcat
                if (clicked == null) {
                    Log.d(TAG, " Clicked is NULL!!!");
                }
                //else Log what button was clicked by getting the text from it.
                //log the correct answer to form the current mQuestion
                //Check if the answer was correct by call the QuizFragment.isCorrectAnswer method
                else {
                    Log.d(TAG, clicked.getText().toString());
                    Log.d(TAG, mQuestion.getCorrectAnswer());
                    isCorrectAnswer(clicked.getText().toString());

                }
            }
        });
    }

    /**
     * Set the number of {@link RadioButton}'s needed for the {@link QuizFragment#mRadioGroup} for the current
     * {@link QuizFragment#mQuestion}
     *
     * @param view a {@link View} reference to add the {@link RadioButton}'s to the correct {@link QuizFragment#mRadioGroup} view
     */
    private void addRadioButtons(View view){

        //create a reverence to the RadioGroup.LayoutParams so we can add the buttons for the RadioGroup
        RadioGroup.LayoutParams radioPrams;

        //iterate over the mNumberOfAnswers cor the current mQuestion and add a button for each
        for(int index = 0; index < this.mNumberOfAnswers; index++){

            //set up a new radio button
            RadioButton radioButton = new RadioButton(getContext());

            //set the id for the new Radio button
            radioButton.setId(index + 1);

            //set the text for the new Radio button from the mQuestion.getNextAnswer method
            //method just returns the answer at the current index for the the List of Answers
            radioButton.setText(this.mQuestion.getNextAnswer(index));

            //set the size of the text on the radio button
            radioButton.setTextSize(TEXT_SIZE);

            radioButton.setTextColor(getResources().getColor(R.color.primary_text));

            //set the radioParams to the new LayoutParams for the current button, wrapping the content for width and height
            radioPrams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //add the new button the the mRadioGroup
            this.mRadioGroup.addView(radioButton, index, radioPrams);

        }


    }

    /**
     * Private void method to increase the question number when {@link QuizFragment#mNextButton} is pushed
     */
    private void increaseQuestionNumber(){

        //if the question number is within a valid range of question that we have the increase the number of the question
        if(this.mQuestionNumber < this.mQuestionsSet.size()-1){
            this.mQuestionNumber++;
        }
        //if the new mQuestionNumber would be out of Range then show the ScoreFragment by calling the showScoreFragment() method
        else{
            showScoreFragment();
        }
    }

    /**
     * private void method to decrease the question number when {@link QuizFragment#mPrevButton} is pushed
     */
    private void decreaseQuestionNumber(){

        //if the mQuestion number is not 0 (the first index in the List) then the decrease the mQuestion number
        if (this.mQuestionNumber != 0) {
            this.mQuestionNumber--;
        }
        //else just set the mQuestion number to 0 (the first index in the List) and print a Toast telling the user that
        //they are at the first question
        else{
            this.mQuestionNumber = 0;
            Toast.makeText(getContext().getApplicationContext(),"First Question", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * private viod method to check if the answer selected by the student was the correct answer
     * set the bool value of {@link QuizFragment#isCorrectChoice} to either true or false depending on
     * what the {@link String#compareToIgnoreCase(String)} returns
     *
     * @param answer a string that from the {@link RadioGroup}'s {@link RadioButton} that was pressed
     */
    private void isCorrectAnswer(String answer){

        //set the bool value of isCorrectChoice
        this.isCorrectChoice = answer.equalsIgnoreCase(this.mQuestion.getCorrectAnswer());

        //Log the whether the user clicked the correct answer or not
        Log.d(TAG, "Is it Correct = " + this.isCorrectChoice);
    }

    /**
     * Private void function to print the {@link Toast} for whether or not the student got the question right
     * uses {@link QuizFragment#isCorrectChoice} to determine if they got it correct or not
     */
    private void printToast(){
        if (this.isCorrectChoice){
            //print the correct toast
            Toast.makeText(getContext().getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

            //increase the number of right answers for the final score
            this.mNumberRight++;

            this.mCheckAnswerButton.setVisibility(View.GONE);
            this.mNextButton.setVisibility(View.VISIBLE);
        }
        else {
            //print the incorrect toast
            Toast.makeText(getContext().getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
            this.mCheckAnswerButton.setVisibility(View.GONE);
            this.mNextButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Private void function to read the questions from the {@link FirebaseDatabase} set up with the questions
     * Since the call to {@link FirebaseDatabase} is asynchronous the Google Firebase team has advised to put the rest of the
     * logic for getting the questions and setting up the view inside of the call to {@link FirebaseDatabase}
     *
     * @param view a final {@link View} reference to add the {@link QuizFragment#mQuestionsTextView}, {@link QuizFragment#mRadioGroup}.
     *             {@link QuizFragment#mPrevButton}, {@link QuizFragment#mNextButton}, and {@link QuizFragment#mCheckAnswerButton} to the correct view
     *             declared final because it is used inside an anonymous class
     */
    private void readQuestions(final View view){

        //set up a DatabaseReference for the FirebaseDatabase we have
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        //add a new ValueEvenListener fo a onDataChange call,
        //this is called the first time the app runs and then only triggered if there is an update on the server side
        myRef.addValueEventListener(new ValueEventListener() {

            /**
             * Override of the {@link ValueEventListener#onDataChange(DataSnapshot)} method for the {@link DatabaseReference} we have
             *
             * @param dataSnapshot a {@link DataSnapshot} reference for the {@link FirebaseDatabase} server side
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //name of the general questions inside the FirebaseDatabase
                String question = "general_questions";

                int questionCount = (int)dataSnapshot.child(question).getChildrenCount();

                //iterate over the number of children on the general_questions node and download them into the mQuestionSet
                for (int index = 0; index < questionCount; index++) {

                    //string build to trun the question number into a string question number is index + 1
                    //needed to get the question from the FirebaseDataBase Server
                    StringBuilder builder = new StringBuilder();
                    builder.append(index + 1);
                    String questionNumber = builder.toString();

                    //create a new question reference
                    Questions questions = new Questions();

                    //add the question properties from the FirebaseData base to the at the current questionNumber to the new Question reference
                    questions.setQuestion(dataSnapshot.child(question).child(questionNumber).child("question").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_1").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_2").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_3").getValue(String.class));
                    questions.pushAnswer(dataSnapshot.child(question).child(questionNumber).child("answers").child("answer_4").getValue(String.class));
                    questions.setCorrectAnswer(dataSnapshot.child(question).child(questionNumber).child("correctAnswer").getValue(String.class));

                    //add the question to the question set
                    mQuestionsSet.add(questions);
                }

                //set the visibility of the spinner to GONE and set the visibility of everything else to VISIBLE
                mSpinner.setVisibility(View.GONE);
                setVisibility(View.VISIBLE);

                //set the current mQuestion to the approate one from the mQuestionSet
                setQuestions();

                //setup the current mQuestion
                //set the mQuestionTextView and  the answers to mRadioGroup
                setupQuestion(view);

                //set an onClickedListener for the mCheckAnswerButton
                mCheckAnswerButton.setOnClickListener(new View.OnClickListener() {

                    /**
                     * Override of the onClick method for the {@link QuizFragment#mCheckAnswerButton}
                     *
                     * @param view {@link View} reference to see if the button was clicked
                     */
                    @Override
                    public void onClick(View view) {
                        //print the toast of isCorrectAnswer
                        printToast();


                        //mRadioGroup.clearCheck();
                    }
                });

                //set an onClickedListener for the mNextButton
                mNextButton.setOnClickListener(new View.OnClickListener() {

                    /**
                     * Override of the onClick method for the {@link QuizFragment#mNextButton}
                     *
                     * @param view {@link View} reference to see if the button was clicked
                     */
                    @Override
                    public void onClick(final View view) {

                        mCheckAnswerButton.setVisibility(View.VISIBLE);
                        mNextButton.setVisibility(View.GONE);

                        //clear the current check from the mRadioGroup if there is one
                        mRadioGroup.clearCheck();

                        //remove all the old RadioButton answers from the mRadioGroup
                        mRadioGroup.removeAllViews();

                        //increse the current mQuestionSet number
                        increaseQuestionNumber();


                        //set the current mQuestion to the right mQuestionSet number
                        setQuestions();

                        //setup the current mQuestion
                        //set the mQuestionTextView and  the answers to mRadioGroup
                        setupQuestion(view);
                    }
                });



            }

            /**
             * Override of the onCancelled method, called if the {@link FirebaseDatabase} read failed
             *
             * @param databaseError a {@link DatabaseError} reference holding the information of the error
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

                //Log the error to the Logcat
                Log.w(TAG, "Failed ot read value", databaseError.toException());

                //print it out on the console for debug purposes
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }


    /**
     * private void method to inflate and show the Fragment for the {@link ScoreFragment} class
     */
    private void showScoreFragment(){

        //a new reference for a Bundle so se can pass the number correct and the total number of question to the ScoreFragment
        Bundle bundle = new Bundle();

        //and the parameters we need to the bundle
        bundle.putInt("NumberCorrect", this.mNumberRight);
        bundle.putInt("TotalQuestions", this.mQuestionsSet.size());

        //create a new reference to the ScoreFragment
        Fragment score = new ScoreFragment();

        //add the bundle to the new ScoreFragment Reference
        score.setArguments(bundle);

        //get the FragmentManager and begin the Transaction with the R.id.fragment_container (activity_main.xml)
        //the and the ScoreFragment reference.
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, score, null)
                .addToBackStack(null)
                .commit();

    }



}
