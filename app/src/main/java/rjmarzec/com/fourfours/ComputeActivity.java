package rjmarzec.com.fourfours;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ComputeActivity extends AppCompatActivity
{
    Button submitButton;
    TextView number1, number2, number3, number4, priorityBar2Label, selectedNumberTextView, goalNumberTextView, outputTextView;
    Spinner operationSpinner1, operationSpinner2, operationSpinner3;
    ArrayList<TextView> layoutNumbers = new ArrayList<>();
    char[] operationArray;
    char currentOperation;
    SharedPreferences preferences;
    SeekBar prioritySeekBar1, prioritySeekBar2;
    int selectedNumber, targetNumber;
    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        submitButton = findViewById(R.id.computeComputeButton);

        number1 = findViewById(R.id.computeNumber1);
        number2 = findViewById(R.id.computeNumber2);
        number3 = findViewById(R.id.computeNumber3);
        number4 = findViewById(R.id.computeNumber4);
        priorityBar2Label = findViewById(R.id.computePriorityBar2Label);

        operationSpinner1 = findViewById(R.id.computeOperation1);
        operationSpinner2 = findViewById(R.id.computeOperation2);
        operationSpinner3 = findViewById(R.id.computeOperation3);

        prioritySeekBar1 = findViewById(R.id.computePriorityBar1);
        prioritySeekBar2 = findViewById(R.id.computePriorityBar2);

        goalNumberTextView = findViewById(R.id.computeGoalNumber);
        selectedNumberTextView = findViewById(R.id.computeCurrentNumber);
        outputTextView = findViewById(R.id.computeOutput);

        layoutNumbers.add(number1);
        layoutNumbers.add(number2);
        layoutNumbers.add(number3);
        layoutNumbers.add(number4);

        //Pulling values from saved preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        selectedNumber = preferences.getInt("selectedNumber", 4);
        targetNumber = preferences.getInt("targetNumber", 4);

        for (TextView textView : layoutNumbers)
            textView.setText(Integer.valueOf(selectedNumber).toString());

        goalNumberTextView.setText("Goal: " + targetNumber);

        //Code for setting up dropdown menu options
        String[] operationList = new String[]{"+", "-", "*", "/", "^"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, operationList);
        operationSpinner1.setAdapter(adapter);
        operationSpinner2.setAdapter(adapter);
        operationSpinner3.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                double result;

                //Actual math for the selections
                if (prioritySeekBar1.getProgress() == 0)
                {
                    //"Operation 2\nPrevious & Middle Next   |   Right Half Together Next"
                    result = computeOperation(operationSpinner1, selectedNumber, selectedNumber);

                    if (prioritySeekBar2.getProgress() == 0)
                    {
                        result = computeOperation(operationSpinner2, result, selectedNumber);
                        result = computeOperation(operationSpinner3, result, selectedNumber);
                    } else
                    {
                        result = computeOperation(operationSpinner2, result, computeOperation(operationSpinner3, selectedNumber, selectedNumber));
                    }
                } else if (prioritySeekBar1.getProgress() == 1)
                {
                    //"Operation 2\nLeft & Previous Next   |   Previous & Right Next"
                    result = computeOperation(operationSpinner2, selectedNumber, selectedNumber);

                    if (prioritySeekBar2.getProgress() == 0)
                    {
                        result = computeOperation(operationSpinner1, selectedNumber, result);
                        result = computeOperation(operationSpinner3, result, selectedNumber);
                    } else
                    {
                        result = computeOperation(operationSpinner3, result, selectedNumber);
                        result = computeOperation(operationSpinner1, selectedNumber, result);
                    }
                } else
                {
                    //"Operation 2\nLeft Half Together Next   |   Middle & Previous Next"
                    result = computeOperation(operationSpinner3, selectedNumber, selectedNumber);
                    if (prioritySeekBar2.getProgress() == 0)
                    {
                        result = computeOperation(operationSpinner2, result, computeOperation(operationSpinner1, selectedNumber, selectedNumber));
                    } else
                    {
                        result = computeOperation(operationSpinner2, result, selectedNumber);
                        result = computeOperation(operationSpinner1, result, selectedNumber);
                    }
                }
                outputTextView.setText(Double.valueOf(result).toString());
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

    public double computeOperation(Spinner spinner, double a, double b) {
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
}
