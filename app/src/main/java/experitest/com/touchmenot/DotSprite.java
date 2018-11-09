package experitest.com.touchmenot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DotSprite {
    final String TAG = "DOTSPRITE";
    final String REMAININGTEXT = "Remaining time: ";
    final Double mResFactor = 0.05;
    final long MAXTIME = 3*(long)Math.pow(10.0, 9.0);
    final int TEXTPADDING = 15;
    GameView mGameView;
    Integer mX, mY;
    long mStartTime;
    long mTotalTime ;
    long mRemainingTime;
    List<Pair<Integer, Integer>> mMovesList;
    Integer SCREEN_START_X, SCREEN_START_Y, SCREEN_END_X, SCREEN_END_Y;
    Bitmap mBitmap;
    Rect mBoundingRect;
    int mWidth;
    int mHeight;
    final float mScalingFactor = 0.1f;
    int mTopPadding;
    final float mTextSize = 40f;
    Paint mPaint;

    public DotSprite(GameView theGameView, Bitmap theBitmap) {
        this.mGameView = theGameView;
        this.mBitmap = Bitmap.createScaledBitmap(theBitmap, (int)(theBitmap.getWidth()*mScalingFactor), (int)(theBitmap.getHeight()*mScalingFactor), true);
        this.mStartTime = System.nanoTime();
        mBoundingRect = new Rect();
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        mMovesList = new ArrayList<>();
        mPaint = new Paint();
        Paint p = new Paint();
        p.setTextSize(mTextSize);
        Rect textBounds = new Rect();
        String text = REMAININGTEXT;
        p.getTextBounds(text, 0, text.length(), textBounds);
        mTopPadding = textBounds.height()+TEXTPADDING;
    }

    public void init() {
        Random rand = new Random();
        mX = SCREEN_START_X + rand.nextInt(SCREEN_END_X+1);
        mY = SCREEN_START_Y + rand.nextInt(SCREEN_END_Y+1);
        mBoundingRect.set(mX, mY, mX+mWidth, mY+mHeight);
        mTotalTime = MAXTIME;
        mRemainingTime = mTotalTime;
        mStartTime = System.nanoTime();
        mMovesList.add(new Pair<>(mX, mY));
    }

    public void resetDot() {
        Random rand = new Random();
        mX = SCREEN_START_X + rand.nextInt(SCREEN_END_X+1);
        mY = SCREEN_START_Y + rand.nextInt(SCREEN_END_Y+1);
        mBoundingRect.set(mX, mY, mX+mWidth, mY+mHeight);
        mTotalTime = (long)(mTotalTime - mResFactor*mTotalTime);
        if(mTotalTime<=5000)
            mGameView.setPlayerWon();
        mRemainingTime = mTotalTime;
        mStartTime = System.nanoTime();
        mMovesList.add(new Pair<>(mX, mY));
    }

    public void update() {
        long elapsedTime = System.nanoTime() - mStartTime;
        mRemainingTime = (mTotalTime-elapsedTime)>0?(mTotalTime-elapsedTime):0;
        if(mRemainingTime==0) {
            hLogWrapper.write('D', TAG, "Game Over!");
            mGameView.setGameOver();
        }
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setTextSize(50f);
        p.setAntiAlias(true);
        Rect textBounds = new Rect();
        String text = "Remaining time:"+Math.round(mRemainingTime/1000000);
        p.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, -textBounds.left+TEXTPADDING, -textBounds.top+TEXTPADDING, p);

        canvas.drawBitmap(mBitmap, mX, mY, mPaint );
    }

    public void setScreenXY(Integer sx, Integer sy, Integer ex, Integer ey) {
        SCREEN_START_X = sx;
        SCREEN_START_Y = sy+mTopPadding;
        SCREEN_END_X = ex-mWidth;
        SCREEN_END_Y = ey-mHeight;
    }

    public Rect getBoundingRect() {
        return mBoundingRect;
    }

    public List<Pair<Integer, Integer>> getmMovesList() {
        return mMovesList;
    }
}
