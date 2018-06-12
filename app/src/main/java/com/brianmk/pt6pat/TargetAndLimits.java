package com.brianmk.pt6pat;

import android.util.Log;

import java.util.Locale;

public class TargetAndLimits {
    private final static String LOG_TAG = TargetAndLimits.class.getSimpleName();

    private static final int TQ = 0;
    private static final int T5 = 1;
    private static final int N1 = 2;

    // Basic limits at 0C and 0ft
    private static final double baseTQ = 72.5;
    private static final double baseT5 = 655;
    private static final double baseN1 = 91.2;

    public TargetAndLimits() {
    }

    public double[] getValues(double oat, double pa) {
        double[] chartValues = {0, 0, 0};

        // This indicates that the current oat or pa values aren't valid
        if ((oat <= -1000) || (pa <= -1000)) {
            chartValues[TQ] = -1000;
            chartValues[T5] = -1000;
            chartValues[N1] = -1000;

            return chartValues;
        }

        // Target torque
        double paOffsetFactorTQ = 0.144 + (-1.88e-4 * pa) + (1.44e-7 * (pa*pa)); // trendline (polynomial)
        double oatBaseTQ = baseTQ - (2e-3 * pa);  // tendline (linear)
        chartValues[TQ] = oatBaseTQ + (paOffsetFactorTQ * oat);

        // T5 limit
        double paOffsetFactorT5 = 3.34 + (4e-4 * pa) - (3.2e-7 * (pa*pa)); // trendline (polynomial)
        double oatBaseT5 = baseT5 + (0.012 * pa) + (8e-6 * (pa*pa)); // trendline (linear)
        chartValues[T5] = oatBaseT5 + (paOffsetFactorT5 * oat);

        // N1 limit
        double paOffsetFactorN1 = 0;
        double oatBaseN1 = baseN1;
        chartValues[N1] = oatBaseN1 + (paOffsetFactorN1 * oat);

        String logmsg = String.format(Locale.getDefault(),
                "OAT: %.1f  PA: %.1f\n" +
                        "paOffsetFactorTQ: %.2f  oatBaseTQ: %.1f  targetTq: %.1f\n" +
                        "paOffsetFactorT5: %.2f  oatBaseT5: %.0f    limitT5: %.0f\n" +
                        "paOffsetFactorN1: %.2f  oatBaseN1: %.1f   limitN1: %.1f\n",
                        oat, pa,
                        paOffsetFactorTQ, oatBaseTQ, chartValues[TQ],
                        paOffsetFactorT5, oatBaseT5, chartValues[T5],
                        paOffsetFactorN1, oatBaseN1, chartValues[N1]);
        Log.d(LOG_TAG, logmsg);


        return chartValues;
    }
}
