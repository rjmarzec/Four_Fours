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

import java.util.ArrayList;
import java.util.Arrays;

public class TargetActivity extends AppCompatActivity {

    Button startButton;
    EditText enteredTarget;
    TextView historyTextView;
    CheckBox isCompletedLocally;
    public static int targetNumber;
    private String tempstr;
    private SharedPreferences preferences;
    String history;
    ArrayList<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        enteredTarget = findViewById(R.id.targetEnteredTarget);
        startButton = findViewById(R.id.targetStartButton);
        historyTextView = findViewById(R.id.targetHistory);
        isCompletedLocally = findViewById(R.id.targetCheckBox);
        isCompletedLocally.setClickable(false);
        isCompletedLocally.setChecked(false);

        //Pulling the history of solved values
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        history = preferences.getString("historyOf" + Integer.toString(preferences.getInt("selectedNum", 4)), "None!");
        historyList = new ArrayList<>(Arrays.asList(history.split(";;")));

        //Getting the history of solved values to be displayed in a nice format
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
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ComputeActivity.class));
                tempstr = enteredTarget.getText().toString();
                targetNumber = Integer.parseInt(tempstr);
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
                if (historyList.contains(enteredTarget.getText().toString()))
                    isCompletedLocally.setChecked(true);
                else
                    isCompletedLocally.setChecked(false);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), SelectActivity.class));
    }
}
