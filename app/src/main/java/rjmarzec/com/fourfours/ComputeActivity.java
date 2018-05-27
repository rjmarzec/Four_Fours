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
    Spinner operation1, operation2, operation3;
    ArrayList<TextView> numbers = new ArrayList<>();
    char[] operationArray;
    int currentOperationString;


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

        operation1 = findViewById(R.id.computeOperation1);
        operation2 = findViewById(R.id.computeOperation2);
        operation3 = findViewById(R.id.computeOperation3);


        goalNum.setText("Goal: " + TargetActivity.targetNumber);

        for (TextView element : numbers)
        {
            element.setText(TargetActivity.targetNumber);
        }

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //TODO logic for compute
            }
        });
    }

    public char getComputeOperation(Spinner spinner) {
        operationArray = spinner.getSelectedItem().toString().toCharArray();
        currentOperationString = operationArray[0];
    }
}
