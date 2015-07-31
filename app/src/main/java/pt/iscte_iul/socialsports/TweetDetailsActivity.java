package pt.iscte_iul.socialsports;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.*;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

public class TweetDetailsActivity extends ActionBarActivity {
    protected ImageView iv_tweetuserpicture;
    protected ImageView iv_tweetextracontent;
    protected TextView tv_tweeusername;
    protected TextView tv_tweetcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.tweetlayout);

        /*
        iv_tweetuserpicture = (ImageView) findViewById(R.id.ivTweetAuthorImage);
        iv_tweetextracontent = (ImageView) findViewById(R.id.ivTweetOptionalAditionalContent);
        tv_tweeusername = (TextView) findViewById(R.id.tvTweeterAuthorName);
        tv_tweetcontent = (TextView) findViewById(R.id.tvTweetContent);
        */

        Intent i = getIntent();
        /*
        tv_tweeusername.setText(i.getStringExtra("tweetusername"));
        tv_tweetcontent.setText(i.getStringExtra("tweetcontent"));

        ImageLoader.getInstance().displayImage(i.getStringExtra("tweetuserpicture"), iv_tweetuserpicture);
        ImageLoader.getInstance().displayImage(i.getStringExtra("tweetextracontent"), iv_tweetextracontent);
        */


        TweetUtils.loadTweet(i.getLongExtra("tweetid", 0), new Callback<com.twitter.sdk.android.core.models.Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                myLayout.addView(new TweetView(TweetDetailsActivity.this, result.data));
            }

            @Override
            public void failure(TwitterException e) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_details, menu);
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

    public void clickCloseButton(View v) {
        finish();
    }
}
