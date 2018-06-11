package com.brianmk.pt6pat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private double OAT = 10;
    private double PA = -150;
    private TargetAndLimits currentTAL = new TargetAndLimits();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTAL.getValues(OAT, PA);
            }
        });

        EditText oatText = findViewById(R.id.oat_text);
        oatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    OAT = Double.parseDouble(v.getText().toString());
                    setTargetAndLimits();
                }
                return false;
            }
        });

        EditText paText = findViewById(R.id.pa_text);
        paText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    PA = Double.parseDouble(v.getText().toString());
                    setTargetAndLimits();
                }
                return false;
            }
        });
    }

    public void setTargetAndLimits() {
        double [] values = currentTAL.getValues(OAT, PA);
        TextView targetTq = findViewById(R.id.target_tq);
        String text = String.format(Locale.getDefault(),"%.1f", values[0]);
        targetTq.setText(text);

    }
}
