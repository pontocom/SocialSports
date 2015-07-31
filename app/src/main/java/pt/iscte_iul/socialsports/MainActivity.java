package pt.iscte_iul.socialsports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    protected TweetAdapter adapter;
    protected ListView lv;

    int currPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listContentView);

        new getFeed().execute();

    }

    public void clickButtonFilters(View view) {
        startActivity(new Intent(MainActivity.this, FiltersActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new getFeed().execute();
        }

        return super.onOptionsItemSelected(item);
    }

    private class getFeed extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Updating the feed...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.getFeed();
        }

        @Override
        protected void onPostExecute(JSONObject result)
        {
            dialog.dismiss();


            if(result == null) {
                // in case something bad just happened!!! :-)
                // simply don't try to fetch tweets
                Toast.makeText(getApplicationContext(), "No tweets, or some error occurred!", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("socialsports", "Received  = " + result.toString());

                ArrayList<Tweet> tweets = new ArrayList<Tweet>();

                //List<Long> tweetIds = new ArrayList<Long>();

                try {
                    String timestamp = result.getString("timestamp");
                    JSONArray atweets = result.getJSONArray("tweets");
                    for(int i = 0; i < atweets.length(); i++) {
                        JSONObject jtweet = atweets.getJSONObject(i);
                        //tweets.add(new Tweet(jtweet.getString("timestamp"), jtweet.getString("twitteruserimage"), jtweet.getString("twitterusername"), jtweet.getString("twitterdata"), jtweet.getString("optionalcontent")));
                        tweets.add(new Tweet(Long.parseLong(jtweet.getString("tweetid")), jtweet.getString("timestamp"), jtweet.getString("twitteruserimage"), jtweet.getString("twitterusername"), jtweet.getString("twitterdata"), ""));
                        //tweetIds.add(Long.parseLong(jtweet.getString("tweetid")));
                    }

                    final TweetAdapter adapter = new TweetAdapter(getApplicationContext(), tweets);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            Log.i("socialsports", "CLICKKKKKKKKKKK!!!!");
                            Context ctx = getApplicationContext();
                            Intent i = new Intent(ctx, TweetDetailsActivity.class);

                            currPos = position;

                            Tweet t = (Tweet) adapter.getItem(position);
                            i.putExtra("tweetid", t.getTweetid());
                            i.putExtra("tweetusername", t.getTweetUsername());
                            i.putExtra("tweetcontent", t.getTweetContent());
                            i.putExtra("tweetuserpicture", t.getTweetUserPictureURL());
                            i.putExtra("tweetextracontent", t.getTweetOptionalContentURL());

                            startActivity(i);

                        }
                    });


                } catch (JSONException e) {
                    Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
                }

            }
        }
    }
}
