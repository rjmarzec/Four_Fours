package rjmarzec.com.fourfours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class TargetActivity extends AppCompatActivity
{

    Button startButton;
    EditText enteredTarget;
    TextView historyTextView, selectedNumberTextView;
    CheckBox isCompletedLocally;
    private SharedPreferences preferences;
    String history;
    ArrayList<String> historyList;
    boolean numberEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        enteredTarget = findViewById(R.id.targetEnteredTarget);
        selectedNumberTextView = findViewById(R.id.targetSelectedNumber);
        startButton = findViewById(R.id.targetStartButton);
        historyTextView = findViewById(R.id.targetHistory);
        isCompletedLocally = findViewById(R.id.targetCheckBox);
        isCompletedLocally.setClickable(false);
        isCompletedLocally.setChecked(false);
        numberEntered = false;

        //Pulling the history of solved values
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        history = preferences.getString("historyOf" + Integer.toString(preferences.getInt("selectedNumber", 4)), "None!");
        historyList = new ArrayList<>(Arrays.asList(history.split(";;")));

        //Sorting the history list by converting into ints and then comparing
        int[] myIntArray = new int[historyList.size()];
        if (historyList.get(0) != "None!")
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
        String historyTextViewText = "History of Solved Number:\n";
        for (int i = 0; i < historyList.size(); i++)
        {
            if (i == historyList.size() - 1)
            {
                historyTextViewText += historyList.get(i);
            } else
            {
                historyTextViewText += historyList.get(i) + ", ";
            }
        }
        historyTextView.setText(historyTextViewText);

        //Button listener for moving to the compute activity
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    int num = Integer.parseInt(enteredTarget.getText().toString());
                    editor.putInt("targetNumber", num);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), ComputeActivity.class));
                } catch (NumberFormatException e)
                {
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
                try
                {
                    int num = Integer.parseInt(s.toString());

                    if (historyList.contains(enteredTarget.getText().toString()))
                        isCompletedLocally.setChecked(true);
                    else
                        isCompletedLocally.setChecked(false);
                    numberEntered = true;
                } catch (NumberFormatException e)
                {
                    numberEntered = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), SelectActivity.class));
    }
}
