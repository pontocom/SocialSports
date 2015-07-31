package pt.iscte_iul.socialsports;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by cserrao on 05/07/15.
 */
public class UserInfo {
    protected String username;
    //protected String password;
    //protected String twitter;
    protected String authorizationToken;
    protected String secretToken;
    protected String uniqueID;
    protected Boolean isUserRegistered;

    private static final String USER_DATA_FILE = "USERDATAFILE";

    public UserInfo() {
    }

    public Boolean isUserRegistered(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        uniqueID = sp.getString("USERID", null);
        if(uniqueID == null ) { // either no shared preferences or the id is not created yet
            return false;
        } else {
            return true;
        }
    }

    public Boolean sessionHasExpired(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        Long savedtimestamp = sp.getLong("timestamp", 0);

        Date _timestamp = new Date();
        Long currenttimestamp = _timestamp.getTime();

        Log.i("socialsports", "Saved Timestamp - " + savedtimestamp);
        Log.i("socialsports", "Curr Timestamp - " + currenttimestamp);
        Log.i("socialsports", "Curr - Saved = " + (currenttimestamp - savedtimestamp));

        if(currenttimestamp - savedtimestamp < (24*60*60*1000)) { // 24 hours for testing
            return false;
        } else {
            return true;
        }
    }

    public Boolean loadUserData(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        uniqueID = sp.getString("USERID", null);
        if(uniqueID == null ) { // either no shared preferences or the id is not created yet
            return false;
        } else {
            username = sp.getString("USERNAME", null);
            authorizationToken = sp.getString("AUTHTOKEN", null);
            secretToken = sp.getString("SECRETTOKEN", null);
            isUserRegistered = true;
            return true;
        }
    }

    public Boolean handleUserRegistration(Context ctx, String username, String authorizationToken, String secretToken) {
        SharedPreferences sp = ctx.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        this.username = username;
        this.authorizationToken = authorizationToken;
        this.secretToken = secretToken;

        // send data to be registered on the server-side
        ApiRest api = new ApiRest();
        JSONObject result = api.registerNewUser(username, authorizationToken, secretToken);

        try {
            if(result != null) {
                if(result.getBoolean("result") == true) {
                    // save all data to SharedPreferences
                    editor.putString("USERNAME", username);
                    editor.putString("AUTHTOKEN", authorizationToken);
                    editor.putString("SECRETTOKEN", secretToken);

                    editor.putString("USERID", result.getString("uuid"));

                    Date _timestamp = new Date();
                    Long timestamp = _timestamp.getTime();

                    editor.putLong("timestamp", timestamp);

                    editor.commit();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (JSONException e) {
            Log.i("socialsports", "An exception has occured in the connection - " + e.toString());
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Boolean getIsUserRegistered() {
        return isUserRegistered;
    }

    public void setIsUserRegistered(Boolean isUserRegistered) {
        this.isUserRegistered = isUserRegistered;
    }
}
