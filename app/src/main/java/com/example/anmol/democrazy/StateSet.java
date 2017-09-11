package com.example.anmol.democrazy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.anmol.democrazy.BillsData.UserStates;
import com.example.anmol.democrazy.BillsData.statesData;
import com.example.anmol.democrazy.adapters.StateSelectAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class StateSet extends AppCompatActivity{

    List<Integer> id;
    List<String> name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.states_select);

        id=new ArrayList<>();
        name=new ArrayList<>();

        // All States data
        statesData statesData=new statesData(StateSet.this);
        System.out.println("Bills Activity : " + statesData.getStates());

        try {
            JSONArray jsonArray=new JSONArray(statesData.getStates());
            for (int i=0;i<jsonArray.length();i++){
                id.add(jsonArray.getJSONObject(i).getInt("id"));
                name.add(jsonArray.getJSONObject(i).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //User States Data
        UserStates userStates = new UserStates(StateSet.this);
        System.out.println("User States : "+userStates.getUserStates());

        //setting up recycler view
        RecyclerView rv= (RecyclerView) findViewById(R.id.StatesSelectRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(StateSet.this);
        RecyclerView.Adapter adapter=new StateSelectAdapter(StateSet.this,id,name);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

    }
}
