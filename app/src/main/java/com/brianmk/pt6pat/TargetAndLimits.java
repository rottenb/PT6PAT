package com.brianmk.pt6pat;

import android.util.Log;

public class TargetAndLimits {
    private final static String LOG_TAG = TargetAndLimits.class.getSimpleName();

    private static final int TQ = 0;
    private static final int T5 = 1;
    private static final int N1 = 2;

    // Basic limits at 0C and 0ft
    private static final double baseTQ = 72.5;
    private static final double baseT5 = 655;
    private static final double baseN1 = 91.2;

    // Limits increase in 1C steps
    private static final double oatStepTQ = 1.5;
    private static final double oatStepT5 = 3.2;
    private static final double oatStepN1 = 0.16;

    // Limits increase in 100ft steps
    private static final double paStepTQ = -0.01;
    private static final double paStepT5 = 0;


    public TargetAndLimits() {
    }

    public double[] getValues(double oat, double pa) {
        double[] chartValues = {0, 0, 0};

        double paOffset = 0.144 + (-1.88e-4 * pa) + (1.44e-7 * (pa * pa));
        double oatOffset = 72.5 + (-2e-3 * pa);
        double tqOffset = oatOffset + (paOffset * oat);

        Log.d(LOG_TAG, "paOffset: " + paOffset + "  oatOffset: " + oatOffset + "  tqOffset: " + tqOffset);

        chartValues[TQ] = tqOffset;

        return chartValues;
    }
}
