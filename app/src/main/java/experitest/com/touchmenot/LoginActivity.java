package experitest.com.touchmenot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LOGIN<TouchMeNot>";
    EditText mUsername;
    EditText mReUsername;
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hLogWrapper.write('V', TAG, "Entered Login!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.usernametext);
        mReUsername = findViewById(R.id.re_usernametext);
        mLoginBtn = findViewById(R.id.loginbtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String re_username = mReUsername.getText().toString();
                if (username != null && username.length() != 0 && username.equals(re_username)) {
                    hLogWrapper.write('V', TAG, "Successful login for username: " + username);
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
