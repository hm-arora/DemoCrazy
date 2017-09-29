package com.example.anmol.democrazy.PhoneNoSave;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;

import org.json.JSONException;


public class SavePhoneNo {

    private Context ctx;
    String phone;

    public SavePhoneNo(Context ctx) {
        this.ctx = ctx;
    }

    public SavePhoneNo(Context ctx, String phone) {

        this.ctx = ctx;
        this.phone = phone;

    }

    /**
     * Used to set Phone No
     */
    public void setPhoneNo() throws JSONException {

        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf(R.string.Phone_No_states_data_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.PhoneNoPrefKey), phone);
        editor.commit();

    }

    /**
     * Used to get Phone no
     *
     * @return Phone No
     */
    public String getPhoneNo() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.Phone_No_states_data_file)), Context.MODE_PRIVATE);
        return sharedPref.getString(String.valueOf(R.string.PhoneNoPrefKey), "");
    }
}
