package rjmarzec.com.fourfours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    //Declaring our buttons here in case we need to access them in a method elsewhere later on
    Button startButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connecting our buttons from the layout to the java.
        startButton = findViewById(R.id.mainStartButton);
        aboutButton = findViewById(R.id.mainAboutButton);

        //Making our startButton take us to the next step of the game
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectActivity.class));
            }
        });

        //Making our aboutButton take us to a page with information about that app
        aboutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });
    }

    //Modifying the back button to take us one step out of our activities.
    //In this case, there is no step back, so we just close the app
    @Override
    public void onBackPressed()
    {
        System.exit(0);
    }
}
