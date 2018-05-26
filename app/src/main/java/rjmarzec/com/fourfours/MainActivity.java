package rjmarzec.com.fourfours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button tartButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.mainStartButton);
        aboutButton = findViewById(R.id.mainAboutButton);
    }
}
