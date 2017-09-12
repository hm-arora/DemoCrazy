package com.example.anmol.democrazy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.anmol.democrazy.BillsData.BillDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anmol on 12/9/17.
 */

public class BillDetailsActivity extends AppCompatActivity {


    TextView BillName,BillDate,SynopContent,Pros,ProsContent,Cons,ConsContent;


    String billName;
    String billDate;
    String synopContent;
    String prosContent;
    String consContent;
    String ActualBillLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_details);

        BillName= (TextView) findViewById(R.id.BillName);
        BillDate= (TextView) findViewById(R.id.BillDate);
        SynopContent= (TextView) findViewById(R.id.SynopContents);
        Pros= (TextView) findViewById(R.id.ProsText);
        ProsContent= (TextView) findViewById(R.id.ProsTextCont);
        Cons= (TextView) findViewById(R.id.ConsText);
        ConsContent= (TextView) findViewById(R.id.ConsTextCont);

        //getting id of bill
        int id=getIntent().getExtras().getInt("id");

        //getting Bill Details
        BillDetails billDetails=new BillDetails(BillDetailsActivity.this,id);

        billDetails.getBillD(new BillDetails.BillsCallBack() {
            @Override
            public void getBillDetails(JSONObject jsonObject) throws JSONException {

                System.out.println(jsonObject);
                boolean status=jsonObject.getBoolean("status");
                //checking status - true
                if (status){
                    JSONObject jsonObject1=jsonObject.getJSONObject("msg");
                    billName =jsonObject1.getString("name");
                    billDate=jsonObject1.getString("date").substring(0,10);
                    synopContent=jsonObject1.getString("synopsis").replace("\n","\n\n");
                    prosContent=jsonObject1.getString("pros").replace("\n","\n\n ->");
                    consContent=jsonObject1.getString("cons").replace("\n","\n\n ->");

                    BillName.setText(billName);
                    BillDate.setText(billDate);
                    SynopContent.setText(synopContent);
                    //if cons content is NA
                    if (consContent.equals("NA")){
                        //setting Visibilty Gone
                        Cons.setVisibility(View.GONE);
                        ConsContent.setVisibility(View.GONE);
                        //setting Text of Pros and Cons
                        Pros.setText("Pros & Cons");
                        ProsContent.setText(prosContent);

                    }
                    // if cons contents are there
                    else{
                        //setting Visibilty Gone
                        Cons.setVisibility(View.VISIBLE);
                        ConsContent.setVisibility(View.VISIBLE);
                        //setting Text of Pros and Cons
                        Pros.setText("Pros");
                        ProsContent.setText(prosContent);
                        Cons.setText("Cons");
                        ConsContent.setText(consContent);
                    }
                }
            }
        });
    }
}
