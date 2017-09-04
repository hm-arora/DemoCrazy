package com.example.anmol.democrazy.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.anmol.democrazy.R;

/**
 * Created by anmol on 4/9/17.
 */

public class loginKey {

    Context ctx;
    String key;

    public loginKey(Context ctx){
        this.ctx=ctx;
    }

    public loginKey(Context ctx,String key){

        this.ctx=ctx;
        this.key=key;

    }

    // Setting Login Key

    public void setLoginKey(){

        SharedPreferences sharedPref=ctx.getSharedPreferences(String.valueOf(R.string.Login_key_File),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString(String.valueOf(R.string.LOGIN_KEY),key);
        editor.commit();

    }

    // Getting login Key

    public String getLoginKey(){

        SharedPreferences sharedPref = ctx.getSharedPreferences(String.valueOf((R.string.Login_key_File)), Context.MODE_PRIVATE);
        String key = sharedPref.getString(String.valueOf(R.string.LOGIN_KEY),"");
        return key;

    }



}
