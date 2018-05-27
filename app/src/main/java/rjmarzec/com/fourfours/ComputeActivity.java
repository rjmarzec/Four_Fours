package rjmarzec.com.fourfours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class ComputeActivity extends AppCompatActivity
{
    //Declaring Widgets
    Button submitButton;
    TextView number1, number2, number3, number4, priorityBar2Label, selectedNumberTextView, targetNumberTextView, outputTextView;
    Spinner operationSpinner1, operationSpinner2, operationSpinner3;
    SeekBar prioritySeekBar1, prioritySeekBar2;

    //Declaring Other Variables
    SharedPreferences preferences;
    int selectedNumber, targetNumber;
    long globalCompletes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        //Connecting text-based widgets from the layout to the activity
        number1 = findViewById(R.id.computeNumber1);
        number2 = findViewById(R.id.computeNumber2);
        number3 = findViewById(R.id.computeNumber3);
        number4 = findViewById(R.id.computeNumber4);
        priorityBar2Label = findViewById(R.id.computePriorityBar2Label);
        targetNumberTextView = findViewById(R.id.computeGoalNumber);
        selectedNumberTextView = findViewById(R.id.computeCurrentNumber);
        outputTextView = findViewById(R.id.computeOutput);

        //Connecting our interactive widgets from the layout to the activity
        submitButton = findViewById(R.id.computeComputeButton);
        operationSpinner1 = findViewById(R.id.computeOperation1);
        operationSpinner2 = findViewById(R.id.computeOperation2);
        operationSpinner3 = findViewById(R.id.computeOperation3);
        prioritySeekBar1 = findViewById(R.id.computePriorityBar1);
        prioritySeekBar2 = findViewById(R.id.computePriorityBar2);

        //Pulling values from saved preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        selectedNumber = preferences.getInt("selectedNumber", 4);
        targetNumber = preferences.getInt("targetNumber", 4);

        //Filling text of widgets based on the target and selected numbers
        selectedNumberTextView.setText(R.string.selected_number_label + selectedNumber);
        targetNumberTextView.setText(R.string.target_label + targetNumber);
        number1.setText(String.valueOf(selectedNumber));
        number2.setText(String.valueOf(selectedNumber));
        number3.setText(String.valueOf(selectedNumber));
        number4.setText(String.valueOf(selectedNumber));

        //Code for setting up dropdown menu options
        String[] operationList = new String[]{"+", "-", "*", "/", "^"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, operationList);
        operationSpinner1.setAdapter(adapter);
        operationSpinner2.setAdapter(adapter);
        operationSpinner3.setAdapter(adapter);

        //Getting a Firebase reference setup so that we can pull and update the global completions easily when necessary later on
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference globalCompletesReference = database.getReference().child("" + preferences.getInt("selectedNumber", 4)).child("" + preferences.getInt("targetNumber", 4));
        globalCompletesReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //Storing the current global completes to save for later
                globalCompletes = (long) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Failed to retrieve high score. Error: NullPointerException");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                double result = getOperationResult();
                useResult(result, editor, globalCompletesReference);
            }
        });

        //Adjusts text based on using settings of the priority SeekBar
        prioritySeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (progress == 0)
                {
                    priorityBar2Label.setText("Operation 2\nPrevious & Middle Next   |   Right Half Together Next");
                }
                if (progress == 1)
                {
                    priorityBar2Label.setText("Operation 2\nLeft & Previous Next   |   Previous & Right Next");
                }
                if (progress == 2)
                {
                    priorityBar2Label.setText("Operation 2\nLeft Half Together Next   |   Middle & Previous Next");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Computes a math operation based on the selections of the priority SeekBars and operation spinners
    private double getOperationResult()
    {
        double result;

        //LMR | LR refer to the positions of the priority SeekBars, which in turn controls which operations are done first
        //This section is labeled and organized in the format L>M>R | L>R
        if (prioritySeekBar1.getProgress() == 0)
        {
            //Operation 2 - Previous & Middle Next   |   Right Half Together Next
            result = computeOperation(operationSpinner1, selectedNumber, selectedNumber);

            if (prioritySeekBar2.getProgress() == 0)
            {
                // L | L
                result = computeOperation(operationSpinner2, result, selectedNumber);
                result = computeOperation(operationSpinner3, result, selectedNumber);
            } else
            {
                // L | R
                double tempResult = computeOperation(operationSpinner3, selectedNumber, selectedNumber);
                result = computeOperation(operationSpinner2, result, tempResult);
            }
        } else if (prioritySeekBar1.getProgress() == 1)
        {
            //Operation 2 - Left & Previous Next   |   Previous & Right Next
            result = computeOperation(operationSpinner2, selectedNumber, selectedNumber);

            if (prioritySeekBar2.getProgress() == 0)
            {
                // M | L
                result = computeOperation(operationSpinner1, selectedNumber, result);
                result = computeOperation(operationSpinner3, result, selectedNumber);
            } else
            {
                // M | R
                result = computeOperation(operationSpinner3, result, selectedNumber);
                result = computeOperation(operationSpinner1, selectedNumber, result);
            }
        } else
        {
            //Operation 2 - Left Half Together Next   |   Middle & Previous Next
            result = computeOperation(operationSpinner3, selectedNumber, selectedNumber);
            // R | L
            if (prioritySeekBar2.getProgress() == 0)
            {
                double tempResult = computeOperation(operationSpinner1, selectedNumber, selectedNumber);
                result = computeOperation(operationSpinner2, tempResult, result);
            }
            // R | R
            else
            {
                result = computeOperation(operationSpinner2, result, selectedNumber);
                result = computeOperation(operationSpinner1, result, selectedNumber);
            }
        }
        return result;
    }

    //Takes the end result of our math operations and puts it to use
    private void useResult(double result, SharedPreferences.Editor editor, DatabaseReference globalCompletesReference)
    {
        //Doing stuff with the result of the operation result.
        if (result % 1 == 0)
        {
            int resultAsInt = (int) (result);
            outputTextView.setText(String.valueOf(resultAsInt));
            if (resultAsInt == targetNumber)
            {
                Toast.makeText(ComputeActivity.this, "You got it!", Toast.LENGTH_SHORT).show();

                //Checking the current saved values to check for dupes, in which case we don't save the current number
                String historyAsString = preferences.getString("historyOf" + Integer.toString(preferences.getInt("selectedNumber", 4)), "None!");
                //Checks if the history already contains the gotten value.
                if (!((Arrays.asList(historyAsString.split(";;"))).contains("" + resultAsInt)))
                {
                    if (historyAsString.equals("None!") || historyAsString.equals(""))
                        historyAsString = Integer.toString(resultAsInt);
                    else
                        historyAsString += ";;" + resultAsInt;
                    editor.putString("historyOf" + Integer.toString(selectedNumber), historyAsString);
                    editor.commit();

                    globalCompletesReference.setValue(globalCompletes + 1);
                    globalCompletesReference.push();
                }

                //Creating a delay before moving back to the previous screen
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    public void run()
                    {
                        startActivity(new Intent(getApplicationContext(), TargetActivity.class));
                    }
                }, 1500);
            }
        } else
        {
            outputTextView.setText(String.valueOf(result));
        }
    }

    //Does an operation with 2 numbers based on what operation is selected in the spinner
    private double computeOperation(Spinner spinner, double a, double b)
    {
        char charOfCurrentOperation = spinner.getSelectedItem().toString().charAt(0);

        if (charOfCurrentOperation == '+')
        {
            return (a + b);
        } else if (charOfCurrentOperation == '-')
        {
            return (a - b);
        } else if (charOfCurrentOperation == '*')
        {
            return (a * b);
        } else if (charOfCurrentOperation == '/')
        {
            return (a / b);
        } else if (charOfCurrentOperation == '^')
        {
            return (Math.pow(a, b));
        }
        return 0;
    }

    //Modifying the back button to take us one step out of our activities
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), TargetActivity.class));
    }
}
