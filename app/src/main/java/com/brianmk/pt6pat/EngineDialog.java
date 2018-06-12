package com.brianmk.pt6pat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class EngineDialog extends DialogFragment {
    private static final String LOG_TAG = EngineDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View content = inflater.inflate(R.layout.engine_dialog, null);
        builder.setView(content);

        double tq = getArguments().getDouble("TQ");
        final int t5 = (int) Math.round(getArguments().getDouble("T5"));
        final double n1 = (double) Math.round(getArguments().getDouble("N1"));

        //Log.d(LOG_TAG, "tq: " + tq + " t5: " + t5 + " n1: " + n1);

        TextView tv = content.findViewById(R.id.eng_tq);
        tv.setText(String.format(Locale.getDefault(), "%.1f", tq));

        tv = content.findViewById(R.id.eng_t5_limit);
        tv.setText(String.format(Locale.getDefault(), "%d", t5));

        tv = content.findViewById(R.id.eng_n1_limit);
        tv.setText(String.format(Locale.getDefault(), "%.1f", n1));

        // T5
        EditText t5Text = content.findViewById(R.id.indicated_t5);
        t5Text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    int indT5 = (int) Math.round(Double.parseDouble(v.getText().toString()));

                    TextView t = content.findViewById(R.id.adjusted_t5);
                    int aT5 = indT5 - 1;
                    t.setText(String.format(Locale.getDefault(), "%d", aT5));

                    int mT5 = t5 - aT5;
                    t = content.findViewById(R.id.margin_t5);
                    if (mT5 >= 0) {
                        t.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    } else {
                        t.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                    }
                    t.setText(String.format(Locale.getDefault(), "%d", mT5));
                }
                return false;
            }
        });

        // N1
        EditText n1Text = content.findViewById(R.id.indicated_n1);
        n1Text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    double indN1 = (double) Math.round(Double.parseDouble(v.getText().toString()));

                    TextView n = content.findViewById(R.id.adjusted_n1);
                    double aN1 = (double) Math.round(indN1 - 0.4);
                    n.setText(String.format(Locale.getDefault(), "%.1f", aN1));

                    double mN1 = (double) Math.round(n1 - aN1);
                    n = content.findViewById(R.id.margin_n1);
                    if (mN1 >= 0) {
                        n.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    } else {
                        n.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                    }
                    n.setText(String.format(Locale.getDefault(), "%.1f", mN1));
                }
                return false;
            }
        });

        return builder.create();
    }
}
