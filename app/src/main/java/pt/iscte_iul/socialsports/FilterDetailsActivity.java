package pt.iscte_iul.socialsports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class FilterDetailsActivity extends ActionBarActivity {
    protected TextView tv_filtername, tv_filterdescription, tv_filterdetails, tv_filterstartdate, tv_filterenddate;
    protected String s_filtername, s_filterdescription, s_filterdetails, s_filterstartdate, s_filterenddate;

    String filterID = null;

    private static final int MODIFYFILTER = 200;
    private static final int DELETEFILTER = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_details);

        tv_filtername = (TextView) findViewById(R.id.tvFilterName);
        tv_filterdescription = (TextView) findViewById(R.id.tvFilterDescription);
        tv_filterdetails = (TextView) findViewById(R.id.tvFilterDetails);
        tv_filterstartdate = (TextView) findViewById(R.id.tvFilterStartDate);
        tv_filterenddate = (TextView) findViewById(R.id.tvFilterEndDate);

        Intent i = getIntent();

        filterID = i.getStringExtra("filterid");

        new getFilter().execute(i.getStringExtra("filterid"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_details, menu);
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

    public void clickDeleteFilterButton(View view) {
        new deleteFilter().execute(filterID);
    }

    public void clickChangeFilterButton(View view){
        Intent i = new Intent(FilterDetailsActivity.this, ChangeFilterActivity.class);

        i.putExtra("filterid", filterID);
        i.putExtra("filtername", s_filtername);
        i.putExtra("filterdescription", s_filterdescription);
        i.putExtra("filterdetails", s_filterdetails);
        i.putExtra("filterstartdate", s_filterstartdate);
        i.putExtra("filterenddate", s_filterenddate);

        startActivityForResult(i, MODIFYFILTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFYFILTER && resultCode == Activity.RESULT_OK) {
            Log.i("socialsports", "===========================> IN \"FilterDetailsActivity.java\" AFTER MODIFICATION, FINISH THE ACTIVITY");
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private class getFilter extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(FilterDetailsActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Getting filter details...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.getFilter(params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            dialog.dismiss();

            Log.i("socialsports", "Received  = " + result.toString());

            try {
                tv_filtername.setText(result.getString("filtername"));
                s_filtername = result.getString("filtername");
                tv_filterdescription.setText(result.getString("filterdescription"));
                s_filterdescription = result.getString("filterdescription");
                tv_filterdetails.setText(result.getString("filterdetails"));
                s_filterdetails = result.getString("filterdetails");
                tv_filterstartdate.setText(result.getString("filterstartdate"));
                s_filterstartdate = result.getString("filterstartdate");
                tv_filterenddate.setText(result.getString("filterenddate"));
                s_filterenddate = result.getString("filterenddate");

            } catch (JSONException e) {
                Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            }
        }
    }

    private class deleteFilter extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog = new ProgressDialog(FilterDetailsActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Deleting filter...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.deleteFilter(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            Log.i("socialsports", "Received  = " + result.toString());

            if(result) {
                Toast.makeText(getApplicationContext(), "Filter deleted with success!", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Problems while trying to delete the filter!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
