package com.slidedownview.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    /**
     * Whole slide down view.
     */
    private RelativeLayout mSlideDownView;

    /**
     * View that accepts the touch events.
     */
    private View mHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideDownView = (RelativeLayout) findViewById(R.id.slideDownView);
        mHandle = findViewById(R.id.handle);
    }

    protected void onResume() {
        super.onResume();

        mHandle.setOnTouchListener(new View.OnTouchListener() {
            /* Starting Y point (where touch started). */
            float yStart = 0;
            /* Default height when in the open state. */
            float closedHeight = mHandle.getHeight();
            /* Default height when in the closed state. */
            float openHeight = 600;
            float currentHeight;
            float lastY = 0;
            boolean directionDown = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yStart = event.getRawY();
                        lastY = event.getRawY();
                        currentHeight = mSlideDownView.getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float totalHeightDiff = event.getRawY() - yStart;
                        LayoutParams params = mSlideDownView.getLayoutParams();
                        params.height = (int) (currentHeight + totalHeightDiff);

                        mSlideDownView.setLayoutParams(params);

                        if (event.getRawY() > lastY) {
                            directionDown = true;
                        } else {
                            directionDown = false;
                        }

                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (directionDown) {
                            // Open the sliding view.
                            int startHeight = mSlideDownView.getHeight();
                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(mSlideDownView),
                                    startHeight,
                                    (int) openHeight).setDuration(300);

                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();

                        } else {
                            // Close the sliding view.
                            int startHeight = mSlideDownView.getHeight();
                            ValueAnimator animation = ValueAnimator.ofObject(
                                    new HeightEvaluator(mSlideDownView),
                                    startHeight,
                                    (int) closedHeight).setDuration(300);

                            animation.setInterpolator(new OvershootInterpolator(1));
                            animation.start();
                        }
                        break;

                }
                return true;
            }
        });
    }
}
