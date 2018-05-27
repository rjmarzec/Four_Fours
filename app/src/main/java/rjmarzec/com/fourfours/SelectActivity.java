package rjmarzec.com.fourfours;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    int selectedNum;
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
                selectedNum = Integer.parseInt((((RadioButton) (findViewById(checkedId))).getText()).toString());
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
                    // TODO: Store the selected number to shared preferences for use later.
                    //something something shared preferences
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
