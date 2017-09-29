package com.example.anmol.democrazy.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anmol.democrazy.BillsData.getAllBills;
import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.adapters.BillsLaidAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class OrdinancesEllapsed extends Fragment {

    private Context ctx;

    private String userstates;

    //If no User States are there ==== User is not logeed in
    public OrdinancesEllapsed(Context ctx){

        this.ctx=ctx;
        // default centeral bills
        userstates="[1]";
    }

    // if User State is Present
    @SuppressLint("ValidFragment")
    public OrdinancesEllapsed(Context ctx, String userstates) {

        this.ctx=ctx;
        this.userstates=userstates;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.bills_laid_fragment, container, false);

        getAllBills getAllBills=new getAllBills(ctx,3,0,userstates);

        // Getting All Bills of BillsPassed
        getAllBills.getData(new getAllBills.BillsCallBack() {
            @Override
            public void getAll(boolean status, JSONArray jsonArray) throws JSONException {

                List<Integer> id=new ArrayList<Integer>();
                List<String> Name=new ArrayList<String>();
                List<String> centeralName=new ArrayList<String>();
                List<String> date=new ArrayList<String>();

                for (int i=0;i<jsonArray.length();i++){
                    id.add(jsonArray.getJSONObject(i).getInt("id"));
                    Name.add(jsonArray.getJSONObject(i).getString("name"));
                    centeralName.add(jsonArray.getJSONObject(i).getString("state"));
                    date.add(jsonArray.getJSONObject(i).getString("date").substring(0,10));
                }
                System.out.println("id : "+id);
                System.out.println("name : "+Name);
                System.out.println("date : "+date);
                // RecyclerView for Bills Laid
                RecyclerView rv= (RecyclerView) v.findViewById(R.id.BillsLaidRv);

                //TextView No Data
                TextView tx= (TextView) v.findViewById(R.id.NoData);

                //checking if there is not an empty array
                if (id.isEmpty()){
                    tx.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
                // If not emty then list will come
                else{
                    tx.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ctx);
                    RecyclerView.Adapter adapter=new BillsLaidAdapter(ctx,id,Name,centeralName,date,4);
                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(adapter);
                }
            }
        });

        return v;
    }
}