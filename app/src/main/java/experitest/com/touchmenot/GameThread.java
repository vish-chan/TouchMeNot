package experitest.com.touchmenot;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private GameView mGameView;
    private boolean mRunning;
    public static Canvas sCanvas;

    public GameThread(SurfaceHolder theSurfaceHolder, GameView theGameView) {
        super();
        this.mSurfaceHolder = theSurfaceHolder;
        this.mGameView = theGameView;
    }

    public void setRunning(boolean theRunning) {
        this.mRunning = theRunning;
    }

    @Override
    public void run() {
        while(mRunning) {
            sCanvas = null;
            try {
                sCanvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    this.mGameView.update();
                    this.mGameView.draw(sCanvas);
                }
            } catch(Exception e) {
            } finally {
                if(sCanvas!=null) {
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(sCanvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
