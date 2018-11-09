package experitest.com.touchmenot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends FragmentActivity {
    final private String TAG = "GAMEACTIVITY<TouchMeNot>";
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        GameView gameView = findViewById(R.id.gameview);
        gameView.setParent(this);
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                hLogWrapper.write('D', TAG, "Handler called");
                Bundle b = msg.getData();
                if (b.getInt("GAMEOVER", -1) >= 0) {
                    performFinalTasks("Game over!", b.getInt("GAMEOVER"), b.getString("MOVES"));
                } else if (b.getInt("WON", -1) >= 0) {
                    performFinalTasks("You Won!", b.getInt("WON"), b.getString("MOVES"));
                }
            }

        };
    }

    public int getFragmentHeight() {
        ExtraInfoFragment extrainfo = (ExtraInfoFragment) getSupportFragmentManager().findFragmentById(R.id.extrainfogame);
        return extrainfo.getView().getHeight();
    }

    public void performFinalTasks(String theTitle, final int theScore, final String theMoveItem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = getIntent().getExtras();
                String username = bundle.getString("USERNAME");
                String date = bundle.getString("DATE");
                RecordItem recordItem = new RecordItem(username, "" + theScore, date);
                hLogWrapper.write('V', TAG, "Record: " + username + "|" + theScore + "|" + date);
                ObjectOutputStream oos = null;
                FileOutputStream fos = null;
                ObjectInputStream ois = null;
                FileInputStream fis = null;
                List<RecordItem> recordsList = new ArrayList<>();
                List<String> movesList = new ArrayList<>();
                try {
                    fis = openFileInput("R");
                    ois = new ObjectInputStream(fis);
                    try {
                        recordsList = (ArrayList<RecordItem>) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } finally {
                    try {
                        if (ois != null)
                            ois.close();
                        if (fis != null)
                            fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                recordsList.add(0, recordItem);
                try {
                    fos = openFileOutput("R", MODE_PRIVATE);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(recordsList);

                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } finally

                {
                    try {
                        if (oos != null)
                            oos.close();
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    fis = openFileInput("M");
                    ois = new ObjectInputStream(fis);
                    try {
                        movesList = (ArrayList<String>) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } finally {
                    try {
                        if (ois != null)
                            ois.close();
                        if (fis != null)
                            fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                movesList.add(0, theMoveItem);
                try {
                    fos = openFileOutput("M", MODE_PRIVATE);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(movesList);

                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                } finally

                {
                    try {
                        if (oos != null)
                            oos.close();
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        AlertDialog dialog = new AlertDialog.Builder(GameActivity.this)
                .setTitle(theTitle)
                .setMessage("Your Score is " + theScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { finish();
                            }
                        })
                .show();
        dialog.setCanceledOnTouchOutside(false);
    }

}
