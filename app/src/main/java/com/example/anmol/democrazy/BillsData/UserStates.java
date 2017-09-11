package com.example.anmol.democrazy.BillsData;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anmol on 11/9/17.
 */

public class UserStates {

    private Context ctx;
    JSONArray jsonArray;

    public UserStates(Context ctx) {
        this.ctx = ctx;
    }

    public UserStates(Context ctx,JSONArray jsonArray) {

        this.ctx = ctx;
        this.jsonArray=jsonArray;

    }

    /**
     * Used to set User states
     */
    public void setUserState() throws JSONException {

        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf(R.string.User_states_data_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.UserStates),jsonArray.toString());
        editor.commit();

    }

    /**
     * Used to get states
     * @return STATES
     */
    public String getUserStates() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.User_states_data_file)), Context.MODE_PRIVATE);
        return sharedPref.getString(String.valueOf(R.string.UserStates), "[1]");
    }
}
