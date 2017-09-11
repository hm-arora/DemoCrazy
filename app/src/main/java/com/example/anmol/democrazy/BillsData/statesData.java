package com.example.anmol.democrazy.BillsData;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class statesData {

    private Context ctx;
    private int id;
    private String name;
    //making Static JsonArray
    private static JSONArray jsonArray=new JSONArray();

    public statesData(Context ctx) {
        this.ctx = ctx;
    }

    public statesData(Context ctx,int id,String name) {

        this.ctx = ctx;
        this.name=name;
        this.id=id;

    }

    /**
     * Used to set state
     */
    public void setState() throws JSONException {

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("name",name);
        jsonArray.put(jsonObject);
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf(R.string.States_data_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.States),jsonArray.toString());
        editor.commit();

    }

    /**
     * Used to get states
     * @return STATES
     */
    public String getStates() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.States_data_file)), Context.MODE_PRIVATE);
        return sharedPref.getString(String.valueOf(R.string.States), "");
    }

}
