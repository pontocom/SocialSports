package pt.iscte_iul.socialsports;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginRegistrationActivity extends ActionBarActivity {

    protected UserInfo userInfo;

    protected TwitterLoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_registration);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                String username = session.getUserName();

                Log.i("Twitter", "Username -> " + username);
                Log.i("Twitter", "Token -> " + token);
                Log.i("Twitter", "Secret -> " + secret);

                new registerNewUser().execute(username, token, secret);

            }

            @Override
            public void failure(TwitterException e) {
                Log.i("Twitter", "Something went wrong -> " + e.getMessage());
                Toast.makeText(LoginRegistrationActivity.this, "There was an error trying to authorize your application! Please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class registerNewUser extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog = new ProgressDialog(LoginRegistrationActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Sending registration data to server...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            UserInfo userInfo = new UserInfo();
            return userInfo.handleUserRegistration(getApplicationContext(), params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            dialog.dismiss();

            if(result) {
                Toast.makeText(getApplicationContext(), "Registration concluded with success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginRegistrationActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Registration: an ERROR has occurred!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
