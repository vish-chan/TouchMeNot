package experitest.com.touchmenot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainMenuActivity extends AppCompatActivity {
    final private String TAG = "MAINMENU<TouchMeNot>";
    Button mGameBtn;
    Button mRecordsBtn;
    Button mLogsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("USERNAME");
        mGameBtn = findViewById(R.id.game);
        mGameBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hLogWrapper.write('V', TAG, "Starting GameActivty for user: "+username);
                Bundle b = new Bundle();
                b.putString("USERNAME", username);
                b.putString("DATE",new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime()));
                Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        mRecordsBtn = findViewById(R.id.records);
        mRecordsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hLogWrapper.write('V', TAG, "Starting RecordsActivity");
                Intent intent = new Intent(MainMenuActivity.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        mLogsBtn = findViewById(R.id.logs);
        mLogsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hLogWrapper.write('V', TAG, "Starting LogsActivity");
                Intent intent = new Intent(MainMenuActivity.this, LogsActivity.class);
                startActivity(intent);
            }
        });
    }
}
