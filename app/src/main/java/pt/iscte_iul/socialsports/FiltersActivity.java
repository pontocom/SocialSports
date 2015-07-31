package pt.iscte_iul.socialsports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiltersActivity extends ActionBarActivity {
    protected ListView lv;

    private static final int ADDFILTER = 100;
    private static final int DETAILSFILTER = 200;


    int currPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        lv = (ListView) findViewById(R.id.lvFilters);

        new getFilters().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
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

    public void clickAddFilterButton(View view) {
        startActivityForResult(new Intent(FiltersActivity.this, AddFilterActivity.class), ADDFILTER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDFILTER && resultCode == Activity.RESULT_OK) {
            Log.i("socialsports", "===========================> AFTER ADDING, RELOAD THE FILTERS!!!");
            new getFilters().execute();
        }
        if(requestCode == DETAILSFILTER && resultCode == Activity.RESULT_OK){
            Log.i("socialsports", "===========================> AFTER DELETION/MODIFICATION, RELOAD THE FILTERS!!!");
            new getFilters().execute();
        }
    }

    public void clickChangeFilterButton(View view) {

    }

    private class getFilters extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(FiltersActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Updating the filters list...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.getAllFilters();
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            dialog.dismiss();

            Log.i("socialsports", "Received  = " + result.toString());

            ArrayList<Filter> filters = new ArrayList<Filter>();

            try {
                JSONArray afilters = result.getJSONArray("filters");
                for(int i = 0; i < afilters.length(); i++) {
                    JSONObject jfilter = afilters.getJSONObject(i);
                    filters.add(new Filter(jfilter.getString("filterid"), jfilter.getString("filtername"), jfilter.getString("filterdescription"), "", "", ""));
                }

                final FilterAdapter adapter = new FilterAdapter(getApplicationContext(), filters);

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Context ctx = getApplicationContext();

                        Intent i = new Intent(ctx, FilterDetailsActivity.class);

                        currPos = position;

                        Filter f = (Filter) adapter.getItem(position);
                        i.putExtra("filterid", f.getFilterid());

                        Log.i("socialsports", "FILTER ID = " + f.getFilterid());

                        startActivityForResult(i, DETAILSFILTER);
                    }
                });


            } catch (JSONException e) {
                Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            }
        }
    }
}
