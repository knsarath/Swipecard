package com.test.swipecard;

import android.animation.Animator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/**
 * Created by sarath on 13/2/17.
 */
public class SwipeHandler implements View.OnTouchListener {
    private float mCardDx;
    private float mCardWidth;
    private float mInitialCardX;
    private View mParent;
    private float mPercentageMoved = 1;
    private boolean mMovedRight;
    private static final String TAG = SwipeHandler.class.getSimpleName();
    private static int DURATION = 300;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mCardWidth = view.getWidth();
                final float viewsStartOffset = view.getX();
                mCardDx = -event.getRawX() + viewsStartOffset;
                mParent = (View) view.getParent();
                mInitialCardX = view.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float cardsNewX = mCardDx + event.getRawX();
                final float currentDistance = view.getX() - mInitialCardX;
                mMovedRight = cardsNewX > mInitialCardX;
                mPercentageMoved = mMovedRight ? (1 - ((view.getX() - mInitialCardX)) / (mParent.getWidth() - mInitialCardX)) : ((view.getX() + mCardWidth) / (mInitialCardX + mCardWidth));
                view.animate()
                        .alpha(mPercentageMoved)
                        .x(cardsNewX)
                        .setDuration(0)
                        .rotation(currentDistance * 0.05f)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                ViewPropertyAnimator releaseAnimation = (mPercentageMoved < 0.5) ? (mMovedRight) ? closeToRightAnimation(view) : closeToLeftAnimation(view) : resetTolOldPositionAnimation(view);
                releaseAnimation.start();
        }
        return true;
    }






    private ViewPropertyAnimator resetTolOldPositionAnimation(View view) {
        return view.animate().x(mInitialCardX).setDuration(DURATION).alpha(1).rotation(0);
    }

    private ViewPropertyAnimator closeToLeftAnimation(View view) {
        final ViewPropertyAnimator animator = view.animate().x(mParent.getX() - view.getWidth()).setDuration(DURATION).alpha(0);
        animator.setListener(new ViewDestroyer(view));
        return animator;
    }

    private ViewPropertyAnimator closeToRightAnimation(View view) {
        final ViewPropertyAnimator animator = view.animate().x(mParent.getWidth() + view.getWidth()).setDuration(DURATION).alpha(0);
        animator.setListener(new ViewDestroyer(view));
        return animator;
    }


    public static class ViewDestroyer implements Animator.AnimatorListener {
        private View mView;

        public ViewDestroyer(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mView != null) {
                final ViewGroup parent = (ViewGroup) mView.getParent();
                if (parent != null) {
                    parent.removeViewAt(parent.indexOfChild(mView));
                    Log.d(TAG, "Remaining cards = " + (parent).getChildCount());
                }
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
