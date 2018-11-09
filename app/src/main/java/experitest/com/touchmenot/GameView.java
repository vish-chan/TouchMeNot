package experitest.com.touchmenot;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    final String TAG = "GAMEVIEW<TouchMeNot>";
    GameActivity mParent;
    int mHeightPadding = 0;
    Rect mActualScreenRect;
    int mPadding = 0;
    private GameThread mGameThread;
    DotSprite mDot;
    int mScore = 0;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setParent(GameActivity theParent) {
        this.mParent = theParent;
    }

    public void init() {
        getHolder().addCallback(this);
        this.mGameThread = new GameThread(this.getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hLogWrapper.write('V', TAG, "Surface view created with "+getWidth()+"x"+getHeight()+" dimension.");
        while((mHeightPadding = mParent.getFragmentHeight())<=0);
        mActualScreenRect = new Rect(this.getLeft()+mPadding, this.getTop()+mPadding, this.getRight()-mPadding, this.getBottom() - mHeightPadding);
        mDot = new DotSprite(this, BitmapFactory.decodeResource(getResources(), R.drawable.dot));
        mDot.setScreenXY(this.getLeft()+mPadding, this.getTop()+mPadding, this.getRight()-mPadding, this.getBottom() - mHeightPadding);
        mDot.init();
        mGameThread.setRunning(true);
        mGameThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                mGameThread.setRunning(false);
                mGameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        hLogWrapper.write('D', TAG, "Touchevent at "+x+","+y);
        Rect dotBoundingBox = mDot.getBoundingRect();
        if(dotBoundingBox.contains(x, y)) {
            hLogWrapper.write('D', TAG, "Touchevent inside dot!");
            mDot.resetDot();
            mScore++;
        }
        return true;
    }

    public void update() {
        if(mDot!=null)
            mDot.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(255,255,255);
        if(mDot!=null)
            mDot.draw(canvas);
    }

    public void setGameOver() {
        hLogWrapper.write('D', TAG, "Game Over");
        sendGameEnd("GAMEOVER");
    }

    public void setPlayerWon() {
        hLogWrapper.write('D', TAG, "Player won!");
        sendGameEnd("WON");
    }

    protected void sendGameEnd(String theEndString) {
        mGameThread.setRunning(false);
        Bundle b = new  Bundle();
        b.putInt(theEndString, mScore);
        List<Pair<Integer, Integer>> movesList = mDot.getMovesList();
        StringBuilder movesListSb = new StringBuilder();
        for(int i=0;i<movesList.size();i++) {
            movesListSb.append("(").append(movesList.get(i).first).append(",").append(movesList.get(i).second).append(")");
            movesListSb.append(" , ");
        }
        b.putString("MOVES", movesListSb.toString());
        Message m = new Message();
        m.setData(b);
        mParent.mHandler.sendMessage(m);
    }
}
