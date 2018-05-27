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
    Button startButton;
    RadioGroup radioGroup;
    int selectedNumber;
    boolean numIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        startButton = findViewById(R.id.selectConfirmButton);
        radioGroup = findViewById(R.id.selectRadioGroup);
        numIsSelected = false;

        for (int i = 1; i < 11; i++)
        {
            RadioButton temp = new RadioButton(getApplicationContext());
            temp.setTextColor(Color.BLACK);
            temp.setText("" + i);
            temp.setTextSize(18f);
            radioGroup.addView(temp);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                selectedNumber = Integer.parseInt((((RadioButton) (findViewById(checkedId))).getText()).toString());
                numIsSelected = true;
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (numIsSelected)
                {
                    //Pulling the selected Number and storing it to the shared preferences
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
