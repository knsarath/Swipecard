package com.test.swipecard;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sarath on 13/2/17.
 */
public class SwipeHandler implements View.OnTouchListener {
    private static final int CLOSE_TO_RIGHT = 1;
    private static final int CLOSE_TO_LEFT = 2;
    private float mFloatingWindowDx;
    private float mDialogViewWidth;
    private float mInitialDialogX;
    private View mDialogView;
    private boolean FINISH_FLAG = false;
    private static final String TAG = "SwipeHandler";

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDialogViewWidth = view.getWidth();
                mFloatingWindowDx = view.getX() - event.getRawX();
                mInitialDialogX = view.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dialogViewsStartPoint = event.getRawX() + mFloatingWindowDx;
                float dialogViewsEndPoint = dialogViewsStartPoint + mDialogViewWidth;
                float point = (dialogViewsStartPoint > 0) ? dialogViewsStartPoint : dialogViewsEndPoint;
                float alpha = (point / mDialogViewWidth);
                alpha = (dialogViewsStartPoint > 0) ? 1 - alpha : alpha;
                final float currentDistance = view.getX() - mInitialDialogX;
                Log.d(TAG, "Initial = " + mInitialDialogX + " , current : = " + view.getX() + "Current distance = " + currentDistance);
                view.animate()
                        .x(dialogViewsStartPoint)
                        .alpha(alpha)
                        .setDuration(0)
                        .rotation(currentDistance * 0.05f)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
              /*  float releasePoint = event.getRawX() + mFloatingWindowDx;
                float movedDistance = Math.abs(releasePoint);
                float quarterPoint = mDialogViewWidth / 5;
                if (movedDistance > quarterPoint) {
                    int closeDirection = (releasePoint > 0) ? CLOSE_TO_RIGHT : CLOSE_TO_LEFT;
                    closeDialog(view, closeDirection);
                } else {
                    view.animate().x(mInitialDialogX).setDuration(200).start();
                }*/
                view.animate().x(mInitialDialogX).setDuration(200).rotation(0).alpha(1).start();
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

    }

    private void closeToRight(View view) {
        view.animate()
                .x(view.getWidth())
                .yBy((float) 0.25)
                .rotationBy(1)
                .setDuration(200)
                .start();
    }
}
