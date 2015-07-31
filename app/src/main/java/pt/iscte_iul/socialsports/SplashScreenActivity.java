package pt.iscte_iul.socialsports;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;


public class SplashScreenActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "9QUIJHbO9dco5bSGLkefbnSSY";
    private static final String TWITTER_SECRET = "G430YviUIEiL11Wmge1KriTxxlD8Iptauy3nGZHuMTSac2O7U5";


    private static int SPLASH_TIME_OUT = 1000;
    private static int REGWINDOW = 1;
    //private static int LOGINWINDOW = 2;
    private static int MAINWINDOW = 3;
    protected UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_splash_screen);

        userInfo = new UserInfo();

        if(userInfo.loadUserData(getApplicationContext())) {
            // userInfo data can be read
            // check if session has expired
            /*
            if(userInfo.sessionHasExpired(getApplicationContext())) {
                changeWindow(LOGINWINDOW);
            } else {
                changeWindow(MAINWINDOW);
            }
            */

            changeWindow(MAINWINDOW);
        } else {
            // we need to register the userInfo
            changeWindow(REGWINDOW);
        }


    }

    public void changeWindow(final int whatWindow) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = null;
                /*
                if (whatWindow == LOGINWINDOW) {
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }
                */
                if (whatWindow == REGWINDOW) {
                    i = new Intent(SplashScreenActivity.this, LoginRegistrationActivity.class);
                }
                if (whatWindow == MAINWINDOW) {
                    i = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                startActivity(i);
                //overridePendingTransition(R.anim.abc_fade_out, R.anim.abc_fade_out);

                // termina a activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
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
}
