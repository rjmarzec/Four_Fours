package rjmarzec.com.fourfours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TargetActivity extends AppCompatActivity {

    Button startButton;
    EditText targetNum;
    CheckBox getLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

    }
}
