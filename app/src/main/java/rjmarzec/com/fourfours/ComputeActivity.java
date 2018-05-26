package rjmarzec.com.fourfours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ComputeActivity extends AppCompatActivity {


    Button submitButton;
    TextView number1, number2, number3, number4;
    Spinner op1, op2, op3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        submitButton = findViewById(R.id.computeComputeButton);

        number1 = findViewById(R.id.computeNumber1);
        number2 = findViewById(R.id.computeNumber2);
        number3 = findViewById(R.id.computeNumber3);
        number4 = findViewById(R.id.computeNumber4);

        op1 = findViewById(R.id.computeOperation1);
        op2 = findViewById(R.id.computeOperation2);
        op3 = findViewById(R.id.computeOperation3);

    }
}
