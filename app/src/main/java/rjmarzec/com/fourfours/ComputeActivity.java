package rjmarzec.com.fourfours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ComputeActivity extends AppCompatActivity {


    Button submitButton;
    TextView number1, number2, number3, number4, goalNum, output, currentNumber;
    Spinner operation1, operation2, operation3;


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
        cu

                operation1 = findViewById(R.id.computeOperation1);
        operation2 = findViewById(R.id.computeOperation2);
        operation3 = findViewById(R.id.computeOperation3);

        goalNum.setText("Goal: " + TargetActivity.targetNumber);

    }
}
