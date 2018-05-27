package rjmarzec.com.fourfours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ComputeActivity extends AppCompatActivity {


    Button submitButton;
    TextView number1, number2, number3, number4, goalNum, output, currentNumber;
    Spinner operationSpinner1, operationSpinner2, operationSpinner3;
    ArrayList<TextView> numbers = new ArrayList<>();
    char[] operationArray;
    char currentOperation;
    int targetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        submitButton = findViewById(R.id.computeComputeButton);

        number1 = findViewById(R.id.computeNumber1);
        number2 = findViewById(R.id.computeNumber2);
        number3 = findViewById(R.id.computeNumber3);
        number4 = findViewById(R.id.computeNumber4);
        goalNum = findViewById(R.id.computeGoalNumber);
        output = findViewById(R.id.computeOutput);
        currentNumber = findViewById(R.id.computeCurrentNumber);

        operationSpinner1 = findViewById(R.id.computeOperation1);
        operationSpinner2 = findViewById(R.id.computeOperation2);
        operationSpinner3 = findViewById(R.id.computeOperation3);

        goalNum.setText("Goal: " + targetNumber);

        //TODO: Pulling the target number from shared preferences
        //number1 = ;
        //number2 = ;
        //number3 = ;
        //number4 = ;

        number1 = currentNumber;
        number2 = currentNumber;
        number3 = currentNumber;
        number4 = currentNumber;

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //TODO logic for compute
            }
        });
    }

    public char getComputeOperation(Spinner spinner) {
        char charOfCurrentOperation = spinner.getSelectedItem().toString().charAt(0);

        if (charOfCurrentOperation == '+')
        {
            //targetNumber += targetNumber;
            return '+';
        } else if (charOfCurrentOperation == '-')
        {
            //targetNumber -= targetNumber;
            return '-';
        } else if (charOfCurrentOperation == '*')
        {
            //targetNumber *= targetNumber;
            return '*';
        } else if (charOfCurrentOperation == '/')
        {
            //targetNumber /= targetNumber;
            return '/';
        } else if (charOfCurrentOperation == '^')
        {
            //targetNumber /= targetNumber;
            return '^';
        }
        return 0;
    }
}
