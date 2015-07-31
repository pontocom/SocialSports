package pt.iscte_iul.socialsports;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddFilterActivity extends ActionBarActivity {
    protected EditText etfiltersdate;
    protected EditText etfilteredate;
    protected EditText etfiltername;
    protected EditText etfilterdescription;
    protected EditText etfilterdetails;

    protected Context context;

    protected boolean sdatestate = false;
    protected boolean edatestate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filter);

        this.context = this.getApplicationContext();

        etfiltersdate = (EditText) findViewById(R.id.etfiltersdate);
        etfilteredate = (EditText) findViewById(R.id.etfilteredate);

        etfiltername = (EditText) findViewById(R.id.etfiltername);
        etfilterdescription = (EditText) findViewById(R.id.etfilterdescription);
        etfilterdetails = (EditText) findViewById(R.id.etfilterdetails);

        etfiltersdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(sdatestate == false) {
                    sdatestate = true;
                } else {
                    sdatestate = false;
                }

                if(sdatestate == false) {
                    final Dialog sdatedialog = new Dialog(AddFilterActivity.this);

                    sdatedialog.setContentView(R.layout.datepicker_dialog);
                    sdatedialog.setTitle("Select a start date:");
                    sdatedialog.show();

                    Button ok = (Button) sdatedialog.findViewById(R.id.buttonSelectDate);
                    final DatePicker sdp = (DatePicker) sdatedialog.findViewById(R.id.datePicker1);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("socialsports", "Day -> " + sdp.getDayOfMonth());
                            Log.i("socialsports", "Month -> " + sdp.getMonth());
                            Log.i("socialsports", "Year -> " + sdp.getYear());


                            //Date sdate = new Date(sdp.getYear(), sdp.getMonth(), sdp.getDayOfMonth());
                            GregorianCalendar sdate = new GregorianCalendar(sdp.getYear(), sdp.getMonth(), sdp.getDayOfMonth());
                            DateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

                            etfiltersdate.setText(FORMAT.format(sdate.getTime()));

                            sdatedialog.dismiss();
                        }
                    });

                }
                return false;
            }
        });

        etfilteredate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(edatestate == false) {
                    edatestate = true;
                } else {
                    edatestate = false;
                }

                if(edatestate == false) {
                    final Dialog edatedialog = new Dialog(AddFilterActivity.this);

                    edatedialog.setContentView(R.layout.datepicker_dialog);
                    edatedialog.setTitle("Select a end date:");
                    edatedialog.show();

                    Button ok = (Button) edatedialog.findViewById(R.id.buttonSelectDate);
                    final DatePicker edp = (DatePicker) edatedialog.findViewById(R.id.datePicker1);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("socialsports", "Day -> " + edp.getDayOfMonth());
                            Log.i("socialsports", "Month -> " + edp.getMonth());
                            Log.i("socialsports", "Year -> " + edp.getYear());

                            //etfilteredate.setText(edp.getDayOfMonth() + "-" + (edp.getMonth()+1) + "-" +  edp.getYear());
                            GregorianCalendar edate = new GregorianCalendar(edp.getYear(), edp.getMonth(), edp.getDayOfMonth());
                            DateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

                            etfilteredate.setText(FORMAT.format(edate.getTime()));

                            edatedialog.dismiss();
                        }
                    });

                }

                return false;

            }
        });

    }

    public void clickAddNewFilterButton(View view) {
        new addNewFilter().execute(etfiltername.getText().toString(), etfilterdescription.getText().toString(), etfilterdetails.getText().toString(), etfiltersdate.getText().toString(), etfilteredate.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_filter, menu);
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

    private class addNewFilter extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog dialog = new ProgressDialog(AddFilterActivity.this);

        @Override
        protected void onPreExecute()
        {
            dialog.setMessage("Adding a new filter...");
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params)
        {
            ApiRest api = new ApiRest(getApplicationContext());
            return api.addNewFilter(params[0], params[1], params[2], params[3], params[4]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            dialog.dismiss();

            Log.i("socialsports", "Received  = " + result.toString());

            try {
                if(result.getBoolean("result")) {
                    Toast.makeText(getApplicationContext(), "Filter added...!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occured ... no filter was added!", Toast.LENGTH_SHORT).show();
                }

                setResult(Activity.RESULT_OK);

                finish();
            } catch (JSONException e) {
                Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            }
        }
    }
}
