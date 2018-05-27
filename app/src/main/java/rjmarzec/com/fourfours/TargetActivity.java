package rjmarzec.com.fourfours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class TargetActivity extends AppCompatActivity
{
    //Declaring widgets
    TextView historyTextView, selectedNumberTextView, globalTimesCompleted;
    EditText enteredTarget;
    Button startButton;
    CheckBox isCompletedLocally;

    //Declaring other variables
    private SharedPreferences preferences;
    ArrayList<String> historyList;
    String historyString;
    boolean numberEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        //Connecting the widgets from the layouts to the java, and
        enteredTarget = findViewById(R.id.targetEnteredTarget);
        selectedNumberTextView = findViewById(R.id.targetSelectedNumber);
        globalTimesCompleted = findViewById(R.id.targetGlobalTimesCompleted);
        startButton = findViewById(R.id.targetStartButton);
        historyTextView = findViewById(R.id.targetHistory);
        isCompletedLocally = findViewById(R.id.targetCheckBox);
        isCompletedLocally.setClickable(false);
        isCompletedLocally.setChecked(false);
        numberEntered = false;

        //Pulling the history of solved values
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        historyString = preferences.getString("historyOf" + Integer.toString(preferences.getInt("selectedNumber", 4)), "None!");
        historyList = new ArrayList<>(Arrays.asList(historyString.split(";;")));

        //Sorting the history list by converting into ints, sorting that list, and then converting back into a String array
        int[] myIntArray = new int[historyList.size()];
        if (!(historyList.get(0).equals("None!")))
        {
            for (int i = 0; i < historyList.size(); i++)
            {
                myIntArray[i] = Integer.parseInt(historyList.get(i));
            }
            Arrays.sort(myIntArray);
            for (int i = 0; i < historyList.size(); i++)
            {
                historyList.set(i, "" + myIntArray[i]);
            }
        }

        //Getting the history of solved values to be displayed in a nice format
        selectedNumberTextView.setText("Selected Number: " + preferences.getInt("selectedNumber", 4));
        historyTextView.setText(createHistoryTextViewText());

        //Button listener for moving to the compute activity
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Checks the entered target, and if it is a number, store it, and move to the next screen
                try
                {
                    int num = Integer.parseInt(enteredTarget.getText().toString());
                    editor.putInt("targetNumber", num);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), ComputeActivity.class));
                } catch (NumberFormatException e)
                {
                    //Otherwise, let the user know that were was an error with their input
                    Toast.makeText(TargetActivity.this, "A non-number was entered. Please enter a number.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Set up the listener for updating the checkbox when a solved number is entered into the edit text
        enteredTarget.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                //Check to be sure that the user has entered a number as the target, and not anything else
                try
                {
                    //int num goes unused, but it allow us to check if entered target is actually a number via the try-catch statement
                    int num = Integer.parseInt(s.toString());

                    //Checks the checkbox if the current target appears in the history of the selected number
                    if (historyList.contains(enteredTarget.getText().toString()))
                        isCompletedLocally.setChecked(true);
                    else
                        isCompletedLocally.setChecked(false);
                    //Once a number is entered, store it and grab info from Firebase on it
                    numberEntered = true;
                    updateGlobalStat();
                } catch (NumberFormatException e)
                {
                    //If the user has not entered a number, do not allow them to move to the next screen
                    numberEntered = false;
                }
            }
        });
    }

    //Creates a string containing all the locally solved target numbers for selected number for use in displaying in a TextView
    private String createHistoryTextViewText()
    {
        String historyTextViewText = "History of Solved Number:\n";
        StringBuilder stringBuilder = new StringBuilder(historyTextViewText);
        for (int i = 0; i < historyList.size(); i++)
        {
            //If the current number of the history list is not the last one, include a comma and space after it
            stringBuilder.append(historyList.get(i));
            if (i != historyList.size() - 1)
            {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    //Updates the stat of how many times the target has been solved globally by pulling from Firebase
    private void updateGlobalStat()
    {
        //Code for Firebase checking of how many users have gotten a specific number.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference globalCompletesReference = database.getReference().child("" + preferences.getInt("selectedNumber", 4)).child(enteredTarget.getText().toString());
        globalCompletesReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //A any user has attempted to solve the target, pull the number of times the target has been solved and show it to the user
                if (dataSnapshot.getValue() != null)
                {
                    globalTimesCompleted.setText("Times Target Solved Globally: " + String.valueOf(dataSnapshot.getValue()));

                } else
                {
                    //If there is no data for the target, create the database reference with a solved value of 0
                    globalCompletesReference.setValue((long) 0);
                    globalCompletesReference.push();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Failed to retrieve high score. Error: NullPointerException");
            }
        });
    }

    //Modifying the back button to take us one step out of our activities
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), SelectActivity.class));
    }
}
