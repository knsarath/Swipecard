package com.test.swipecard;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sarath on 13/2/17.
 */
public class SwipeHandler implements View.OnTouchListener {
    private static final int CLOSE_TO_RIGHT = 1;
    private static final int CLOSE_TO_LEFT = 2;
    private float mCardDx;
    private float mCardWidth;
    private float mInitialCardX;
    private View mParent;
    private boolean FINISH_FLAG = false;
    private static final String TAG = "SwipeHandler";

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final float viewsStartOffset = view.getX();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mCardWidth = view.getWidth();
                mCardDx = -event.getRawX() + viewsStartOffset;
                mParent = (View) view.getParent();
                mInitialCardX = viewsStartOffset;
                break;
            case MotionEvent.ACTION_MOVE:
                float cardsNewX = mCardDx + event.getRawX();
                final float currentDistance = viewsStartOffset - mInitialCardX;
                view.animate()
                        .x(cardsNewX)
                        .setDuration(0)
                        .rotation(currentDistance * 0.05f)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                float releasePoint = event.getRawX() + mCardDx;
                float movedDistance = Math.abs(releasePoint);
                float quarterPoint = mCardWidth / 4;
                if (movedDistance > quarterPoint) {
                    int closeDirection = (releasePoint > 0) ? CLOSE_TO_RIGHT : CLOSE_TO_LEFT;
                    closeDialog(view, closeDirection);
                } else {
                    view.animate().x(mInitialCardX).setDuration(200).rotation(0).alpha(1).start();
                }
                break;
        }
        return true;
    }

    private void closeDialog(View view, int dialogeCloseDirection) {
        switch (dialogeCloseDirection) {
            case CLOSE_TO_LEFT:
                closeToLeft(view);
                break;
            case CLOSE_TO_RIGHT:
                closeToRight(view);
                break;
        }
    }

    private void closeToLeft(View view) {
        view.animate().x(mInitialCardX - view.getWidth()).setDuration(500).alpha(0).start();
    }

    private void closeToRight(View view) {
        view.animate().x(mInitialCardX + view.getWidth() * 2).setDuration(500).alpha(0).start();
    }
}
