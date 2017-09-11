package com.example.anmol.democrazy.BillsData;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;

import org.json.JSONArray;
import org.json.JSONException;

public class TemporaryUserStates {

    private Context ctx;
    JSONArray jsonArray;

    public TemporaryUserStates(Context ctx) {
        this.ctx = ctx;
    }

    public TemporaryUserStates(Context ctx, JSONArray jsonArray) {

        this.ctx = ctx;
        this.jsonArray=jsonArray;

    }

    /**
     * Used to set Temp User states
     */
    public void setTempUserState() throws JSONException {

        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf(R.string.Temp_User_states_data_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.TempUserStates),jsonArray.toString());
        editor.commit();

    }

    /**
     * Used to get Temp states
     * @return STATES
     */
    public String getTempUserStates() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.Temp_User_states_data_file)), Context.MODE_PRIVATE);
        return sharedPref.getString(String.valueOf(R.string.TempUserStates), "[1]");
    }
}
