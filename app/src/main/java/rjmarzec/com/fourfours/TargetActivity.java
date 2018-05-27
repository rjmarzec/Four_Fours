package rjmarzec.com.fourfours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TargetActivity extends AppCompatActivity {

    Button startButton;
    EditText enteredTarget;
    CheckBox getLocal;
    public static int targetNumber;
    private String tempstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        enteredTarget = findViewById(R.id.targetEnteredTarget);
        startButton = findViewById(R.id.targetStartButton);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ComputeActivity.class));
                tempstr = enteredTarget.getText().toString();
                targetNumber = Integer.parseInt(tempstr);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
