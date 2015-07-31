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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeFilterActivity extends ActionBarActivity {
    protected TextView tv_filtername, tv_filterdescription, tv_filterstartdate, tv_filterenddate;
    protected EditText et_filterdetails;

    protected String filterID = null;

    private static final int MODIFYFILTER = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_filter);

        tv_filtername = (TextView) findViewById(R.id.tvFilterName);
        tv_filterdescription = (TextView) findViewById(R.id.tvFilterDescription);
        et_filterdetails = (EditText) findViewById(R.id.etFilterDetails);
        tv_filterstartdate = (TextView) findViewById(R.id.tvFilterStartDate);
        tv_filterenddate = (TextView) findViewById(R.id.tvFilterEndDate);

        Intent i = getIntent();
        filterID = i.getStringExtra("filterid");
        tv_filtername.setText(i.getStringExtra("filtername"));
        tv_filterdescription.setText(i.getStringExtra("filterdescription"));
        et_filterdetails.setText(i.getStringExtra("filterdetails"));
        tv_filterstartdate.setText(i.getStringExtra("filterstartdate"));
        tv_filterenddate.setText(i.getStringExtra("filterenddate"));
    }

    public void clickDoChangeFilterButton(View view){
        new changeFilter().execute(filterID, et_filterdetails.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_filter, menu);
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

    private class changeFilter extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog = new ProgressDialog(ChangeFilterActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Updating filter...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.changeFilter(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            Log.i("socialsports", "Received  = " + result.toString());

            if(result) {
                Toast.makeText(getApplicationContext(), "Filter updated with success!", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Problems while trying to update the filter!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
