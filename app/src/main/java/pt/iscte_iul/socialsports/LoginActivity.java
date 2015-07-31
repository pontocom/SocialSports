package pt.iscte_iul.socialsports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class LoginActivity extends ActionBarActivity {
    private static final String USER_DATA_FILE = "USERDATAFILE";

    protected EditText username_ET;
    protected EditText password_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_ET = (EditText) findViewById(R.id.emailLoginET);
        password_ET = (EditText) findViewById(R.id.passwordLoginET);
    }

    public void clickLoginButton(View view) {
        new logMeIn().execute(username_ET.getText().toString(), password_ET.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private class logMeIn extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Sending login data to server...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.loginUser(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            dialog.dismiss();

            if(result) {
                SharedPreferences sp = getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Date _timestamp = new Date();
                Long timestamp = _timestamp.getTime();

                editor.putLong("timestamp", timestamp);

                editor.commit();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Problems while processing the login...!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
