package com.example.anmol.democrazy;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anmol.democrazy.BillsData.States;
import com.example.anmol.democrazy.BillsData.UserStates;
import com.example.anmol.democrazy.BillsData.statesData;
import com.example.anmol.democrazy.adapters.StateSelectAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class StateSet extends AppCompatActivity{


    List<States> list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = StateSet.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(StateSet.this,R.color.grey_black_1000));

        setContentView(R.layout.states_select);

        Button skip= (Button) findViewById(R.id.skipStateSelect);
        Button SaveChanges= (Button) findViewById(R.id.SaveChangesButt);

        list=new ArrayList<>();

        // All States data
        statesData statesData=new statesData(StateSet.this);
        System.out.println("Bills Activity : " + statesData.getStates());

        try {
            JSONArray jsonArray=new JSONArray(statesData.getStates());
            for (int i=0;i<jsonArray.length();i++){
                int id=jsonArray.getJSONObject(i).getInt("id");
                String name=jsonArray.getJSONObject(i).getString("name");

                //States is a getter and setter class
                States states=new States(id,name,false);
                list.add(states);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //User States Data
        UserStates userStates = new UserStates(StateSet.this);
        System.out.println("User States : "+userStates.getUserStates());

        //setting up recycler view
        RecyclerView rv= (RecyclerView) findViewById(R.id.StatesSelectRecyclerView);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(StateSet.this);
        RecyclerView.Adapter adapter=new StateSelectAdapter(StateSet.this,skip,SaveChanges,list);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StateSet.this.finish();
    }
}
