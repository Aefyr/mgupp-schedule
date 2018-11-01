package com.aefyr.mgupp.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

public class ViewChangeAlphaTransition {
    private ObjectAnimator mToZeroAnimator;
    private ObjectAnimator mToOneAnimator;
    private Runnable mApplyChangeRunnable;

    public ViewChangeAlphaTransition(View v, long transitionDuration) {
        mToZeroAnimator = ObjectAnimator.ofFloat(v, View.ALPHA, 0);
        mToZeroAnimator.setDuration(transitionDuration / 2);
        mToZeroAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mApplyChangeRunnable.run();
                mToOneAnimator.setupStartValues();
                mToOneAnimator.start();
            }
        });

        mToOneAnimator = ObjectAnimator.ofFloat(v, View.ALPHA, 1f);
        mToOneAnimator.setDuration(transitionDuration / 2);
    }

    public void animateViewChange(Runnable applyChangeRunnable) {
        mApplyChangeRunnable = applyChangeRunnable;

        if (mToZeroAnimator.isRunning()) {
            return;
        }

        if (mToOneAnimator.isRunning())
            mToOneAnimator.cancel();

        mToZeroAnimator.setupStartValues();
        mToZeroAnimator.start();
    }
}
