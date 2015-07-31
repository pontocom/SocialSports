package pt.iscte_iul.socialsports;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by cserrao on 17/07/15.
 */
public class ApiRest {
    protected  Context ctx;
    private static final String USER_DATA_FILE = "USERDATAFILE";
    //private static final String URL = "http://192.168.1.100:3000";
    private static final String URL = "http://192.168.1.113:3000";
    //private static final String URL = "http://192.168.1.102:8080/SocialSportsPlatform/webresources/process";

    public ApiRest() {
    }

    public ApiRest(Context ctx) {
        this.ctx = ctx;
    }

    public JSONObject registerNewUser(String username, String authorizationToken, String secretToken) {
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            JSONObject JSONpoi = new JSONObject();
            JSONpoi.put("username", username);
            JSONpoi.put("authorizationToken", authorizationToken);
            JSONpoi.put("secretToken", secretToken);

            HttpPost request = new HttpPost(new URI(URL + "/register/"));
            StringEntity se = new StringEntity(JSONpoi.toString());
            request.setEntity(se);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json");

            Log.i("socialsports", "Sending  = " + URL + "/register/");

            HttpResponse httpResponse = httpclient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);

            return jobj;

        } catch(Exception e) {
            Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            return null;
        }
    }

    public Boolean loginUser(String username, String password) {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            JSONObject JSONpoi = new JSONObject();
            JSONpoi.put("uuid", uniqueID);
            JSONpoi.put("username", username);
            JSONpoi.put("password", password);

            HttpPost request = new HttpPost(new URI(URL + "/logmein"));
            StringEntity se = new StringEntity(JSONpoi.toString());
            request.setEntity(se);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json");

            Log.i("socialsports", "Sending  = " + URL + "/logmein");

            HttpResponse httpResponse = httpclient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);
            return jobj.getBoolean("result");

        } catch(Exception e) {
            Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            return false;
        }
    }

    public JSONObject getFeed() {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        Date _timestamp = new Date();
        //Long timestamp = _timestamp.getTime()-(24*60*60*1000);
        Long timestamp = _timestamp.getTime();

        DateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        String s_timestamp = FORMAT.format(timestamp);


        try {
            HttpClient httpclient = new DefaultHttpClient();
            Log.i("socialsports", "Asking  = " + URL + "/feed/"+uniqueID+"/"+ s_timestamp);

            HttpResponse httpResponse = httpclient.execute(new HttpGet(new URI(URL + "/feed/"+uniqueID+"/" + s_timestamp)));

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            String myresponse = response.replaceAll("\n", " ");

            Log.i("socialsports", "Response = " + myresponse);

            JSONObject jobj = new JSONObject(response);
            return jobj;

        } catch(Exception e) {
            Log.i("socialsports", "An exception has occured in the connection while getting the feed - " + e.toString());
            return null;
        }
    }

    public JSONObject getAllFilters() {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            Log.i("socialsports", "Asking  = " + URL + "/filter/"+uniqueID);

            HttpResponse httpResponse = httpclient.execute(new HttpGet(new URI(URL + "/filter/"+uniqueID)));

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);
            return jobj;

        } catch (Exception e) {
            Log.i("socialsports", "An exception has occured in the connection while getting the filters - " + e.toString());
            return null;
        }
    }

    public JSONObject addNewFilter(String filtername, String filterdescription, String filterdetails, String filterstartdate, String filterenddate){
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            JSONObject JSONpoi = new JSONObject();
            JSONpoi.put("uuid", uniqueID);
            JSONpoi.put("filtername", filtername);
            JSONpoi.put("filterdescription", filterdescription);
            JSONpoi.put("filterdetails", filterdetails);
            JSONpoi.put("filterstartdate", filterstartdate);
            JSONpoi.put("filterenddate", filterenddate);

            HttpPost request = new HttpPost(new URI(URL + "/filter"));
            StringEntity se = new StringEntity(JSONpoi.toString());
            request.setEntity(se);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json");

            Log.i("socialsports", "Sending  = " + URL + "/filter");

            HttpResponse httpResponse = httpclient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);
            return jobj;

        } catch(Exception e) {
            Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            return null;
        }
    }

    public JSONObject getFilter(String filterid) {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            Log.i("socialsports", "Asking  = " + URL + "/filter/"+filterid+"/"+uniqueID);

            HttpResponse httpResponse = httpclient.execute(new HttpGet(new URI(URL + "/filter/"+filterid+"/"+uniqueID)));

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);
            return jobj;

        } catch (Exception e) {
            Log.i("socialsports", "An exception has occured in the connection while getting the filter - " + e.toString());
            return null;
        }
    }

    public Boolean deleteFilter(String filterid) {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            Log.i("socialsports", "Asking  = " + URL + "/filter/"+filterid+"/"+uniqueID);

            HttpResponse httpResponse = httpclient.execute(new HttpDelete(new URI(URL + "/filter/"+filterid+"/"+uniqueID)));

            //HttpResponse httpResponse = httpclient.execute(new HttpGet(new URI(URL + "/filter/"+filterid+"/"+uniqueID)));

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);

            if(jobj.getBoolean("result")==true) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.i("socialsports", "An exception has occured in the connection while deleting the filter - " + e.toString());
            return false;
        }
    }

    public Boolean changeFilter(String filterid, String filterdetails) {
        SharedPreferences sp = this.ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        String uniqueID = sp.getString("USERID", null);
        String response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            JSONObject JSONpoi = new JSONObject();
            JSONpoi.put("filterdetails", filterdetails);

            HttpPut request = new HttpPut(new URI(URL + "/filter/"+filterid+"/"+uniqueID));
            StringEntity se = new StringEntity(JSONpoi.toString());
            request.setEntity(se);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-Type", "application/json");

            Log.i("socialsports", "Sending  = " + URL + "/filter/"+filterid+"/"+uniqueID);

            HttpResponse httpResponse = httpclient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            response = reader.readLine();

            Log.i("socialsports", "Response = " + response);

            JSONObject jobj = new JSONObject(response);

            if(jobj.getBoolean("result")==true) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.i("socialsports", "An exception has occured in the connection while updating the filter - " + e.toString());
            return false;
        }
    }
}
