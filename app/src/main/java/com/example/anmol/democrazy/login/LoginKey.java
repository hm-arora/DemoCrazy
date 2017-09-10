package com.example.anmol.democrazy.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;
public class LoginKey {
    private Context ctx;
    private String key;

    public LoginKey(Context ctx) {
        this.ctx = ctx;
    }

    public LoginKey(Context ctx, String key) {

        this.ctx = ctx;
        this.key = key;

    }

    /**
     * Used to set login key
     */
    public void setLoginKey() {

        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf(R.string.Login_key_File), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.LOGIN_KEY), key);
        editor.commit();

    }

    /**
     * Used to get key
     * @return Login key
     */
    public String getLoginKey() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.Login_key_File)), Context.MODE_PRIVATE);
        return sharedPref.getString(String.valueOf(R.string.LOGIN_KEY), "");
    }


}
