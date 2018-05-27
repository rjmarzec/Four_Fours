package rjmarzec.com.fourfours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button startButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.mainStartButton);
        aboutButton = findViewById(R.id.mainAboutButton);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectActivity.class));
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
