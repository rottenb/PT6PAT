package com.brianmk.pt6pat;

import android.app.DialogFragment;
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

    private final static int NA_VALUE = -1000;
    private double OAT = NA_VALUE;
    private double PA = NA_VALUE;

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

        // OAT text input
        EditText oatText = findViewById(R.id.oat_text);
        oatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (v.getText().toString().isEmpty()) {
                        OAT = NA_VALUE;
                    } else {
                        OAT = Double.parseDouble(v.getText().toString());
                    }

                    setTargetAndLimits();
                }
                return false;
            }
        });

        // PA text input
        EditText paText = findViewById(R.id.pa_text);
        paText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (v.getText().toString().isEmpty()) {
                        PA = NA_VALUE;
                    } else {
                        PA = Double.parseDouble(v.getText().toString());
                    }

                    setTargetAndLimits();
                }
                return false;
            }
        });

        // Engine 1 parameter dialog
        View eng1Params = findViewById(R.id.eng_1);
        eng1Params.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                double values[] = currentTAL.getValues(OAT, PA);
                DialogFragment engDialog = new EngineDialog();

                Bundle args = new Bundle();
                args.putDouble("TQ", values[0]);
                args.putDouble("T5", values[1]);
                args.putDouble("N1", values[2]);
                engDialog.setArguments(args);

                engDialog.show(getFragmentManager(), null);
            }
        });
    }

    public void setTargetAndLimits() {
        double [] values = currentTAL.getValues(OAT, PA);

        TextView view = findViewById(R.id.target_tq);
        if ((OAT <= NA_VALUE) || (PA <= NA_VALUE)) {
            view.setText("--.-");
        } else {
            view.setText(String.format(Locale.getDefault(), "%.1f", values[0]));
        }

        view = findViewById(R.id.t5_limit);
        if ((OAT <= NA_VALUE) || (PA <= NA_VALUE)) {
            view.setText("---");
        } else {
            view.setText(String.format(Locale.getDefault(), "%.0f", values[1]));
        }

        view = findViewById(R.id.n1_limit);
        if ((OAT <= NA_VALUE) || (PA <= NA_VALUE)) {
            view.setText("--.-");
        } else {
            view.setText(String.format(Locale.getDefault(), "%.1f", values[2]));
        }

    }
}
