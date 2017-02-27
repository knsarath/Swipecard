package com.test.swipecard;

import android.animation.Animator;
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
    private float mParentWidth;
    private float mPercentageMoved = 1;
    private boolean mMovedRight;
    private static final String TAG = SwipeHandler.class.getSimpleName();
    private static int DURATION = 300;
    private static int ON_RELEASE_ROTATE_BY = 30;

    private ViewSwipeOutListener mViewSwipeOutListener;

    public interface ViewSwipeOutListener {
        void onViewSwipedOut(View view);
    }

    public void setViewSwipeOutListener(ViewSwipeOutListener viewSwipeOutListener) {
        mViewSwipeOutListener = viewSwipeOutListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mCardWidth = view.getWidth();
                final float viewsStartOffset = view.getX();
                mCardDx = -event.getRawX() + viewsStartOffset;
                mParentWidth = ((ViewGroup) view.getParent()).getWidth();
                mInitialCardX = view.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float cardsNewX = mCardDx + event.getRawX();
                final float distanceMoved = view.getX() - mInitialCardX;
                mMovedRight = cardsNewX > mInitialCardX;
                mPercentageMoved = mMovedRight ? (1 - ((view.getX() - mInitialCardX)) / (mParentWidth - mInitialCardX)) : ((view.getX() + mCardWidth) / (mInitialCardX + mCardWidth));
                view.animate()
                        .alpha(mPercentageMoved)
                        .x(cardsNewX)
                        .setDuration(0)
                        .rotation(distanceMoved * 0.05f)
                        .scaleX(mPercentageMoved > 0.85f ? mPercentageMoved : view.getScaleX())
                        .scaleY(mPercentageMoved > 0.85f ? mPercentageMoved : view.getScaleY())
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                ViewPropertyAnimator releaseAnimation = (mPercentageMoved < 0.7) ? (mMovedRight) ? closeToRightAnimation(view) : closeToLeftAnimation(view) : resetTolOldPositionAnimation(view);
                releaseAnimation.start();
        }
        return true;
    }

    private ViewPropertyAnimator resetTolOldPositionAnimation(View view) {
        return view.animate().x(mInitialCardX).setDuration(DURATION).alpha(1).scaleX(1.0f).scaleY(1.0f).rotation(0);
    }

    private ViewPropertyAnimator closeToLeftAnimation(View view) {
        final ViewGroup parent = (ViewGroup) view.getParent();
        final ViewPropertyAnimator animator = view.animate().x(parent.getX() - view.getWidth()).setDuration(DURATION).alpha(0).rotationBy(-ON_RELEASE_ROTATE_BY);
        animator.setListener(new ViewDestroyer(view, mViewSwipeOutListener));
        return animator;
    }

    private ViewPropertyAnimator closeToRightAnimation(View view) {
        final ViewGroup parent = (ViewGroup) view.getParent();
        final ViewPropertyAnimator animator = view.animate().x(parent.getWidth() + view.getWidth()).setDuration(DURATION).alpha(0).rotationBy(ON_RELEASE_ROTATE_BY);
        animator.setListener(new ViewDestroyer(view, mViewSwipeOutListener));
        return animator;
    }


    public void closedToLeft(View view) {
        if (view != null) {
            closeToLeftAnimation(view).start();
        }
    }

    public void closedToRight(View view) {
        if (view != null) {
            closeToRightAnimation(view).start();
        }
    }


    /**
     * This will remove a view from its parent after onAnimationEnd
     */
    public static class ViewDestroyer implements Animator.AnimatorListener {
        private View mView;
        private ViewSwipeOutListener mViewSwipeOutListener;

        public ViewDestroyer(View view, ViewSwipeOutListener viewSwipeOutListener) {
            mView = view;
            mViewSwipeOutListener = viewSwipeOutListener;
        }

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
                    if (mViewSwipeOutListener != null) {
                        mViewSwipeOutListener.onViewSwipedOut(mView);
                    }
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
