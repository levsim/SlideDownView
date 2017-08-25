package com.slidedownview.view;

import android.animation.IntEvaluator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Custom Evaluator
 */
public class HeightEvaluator extends IntEvaluator {
    private final View view;

    public HeightEvaluator(View dashboard) {
        this.view = dashboard;
    }

    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        ViewGroup.LayoutParams params = view.getLayoutParams();

        params.height = super.evaluate(fraction, startValue, endValue);
        view.setLayoutParams(params);

        return params.height;
    }
}
