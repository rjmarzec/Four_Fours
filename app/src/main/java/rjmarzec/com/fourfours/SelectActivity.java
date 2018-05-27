package rjmarzec.com.fourfours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity
{
    //Declaring widgets
    Button startButton;
    RadioGroup radioGroup;

    //Declaring other variables
    int selectedNumber;
    boolean numIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //Connecting our widgets from the layout to the java
        startButton = findViewById(R.id.selectConfirmButton);
        radioGroup = findViewById(R.id.selectRadioGroup);
        numIsSelected = false;

        //Loading up our RadioGroup with a number of numbers that act as selected numbers
        fillRadioGroup();

        //If one of the options from the RadioGroup is selected, save that
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                selectedNumber = Integer.parseInt((((RadioButton) (findViewById(checkedId))).getText()).toString());
                numIsSelected = true;
            }
        });

        //The onClickListener for the button that takes us to the target number selection activity
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Checks to make sure that the user has selected a number. If they have not, the button will not move them to the next activity
                if (numIsSelected)
                {
                    //If a number was selected by the user, store it and move them to the next activity
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("selectedNumber", selectedNumber);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), TargetActivity.class));
                } else
                {
                    Toast.makeText(SelectActivity.this, "Please Select a Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Fills up our RadioGroup with different options for the selected number (as in 4 X's, where X is the selected number)
    private void fillRadioGroup()
    {
        radioGroup.removeAllViews();
        for (int i = 1; i < 13; i++)
        {
            RadioButton temp = new RadioButton(getApplicationContext());
            temp.setTextColor(Color.BLACK);
            temp.setText(String.valueOf(i));
            temp.setTextSize(18f);
            radioGroup.addView(temp);
        }
    }

    //Modifying the back button to take us one step out of our activities
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
