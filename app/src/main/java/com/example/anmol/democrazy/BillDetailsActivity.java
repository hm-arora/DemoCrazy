package com.example.anmol.democrazy;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anmol.democrazy.BillsData.BillDetails;
import com.example.anmol.democrazy.BillsData.sendPoll;
import com.example.anmol.democrazy.login.LoginKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BillDetailsActivity extends AppCompatActivity {


    TextView BillName,BillDate,Synop,SynopContent,Pros,ProsContent,Cons,ConsContent,NewspaperLink;

    // For QuotionPoll
    TextView QuotionPoll;

    //Like
    RelativeLayout yes;

    //Dislike;
    RelativeLayout no;

    //WholePole
    RelativeLayout wholepol;

    Button ActualBillLinkButt;

    String billName;
    String billDate;
    String synopContent;
    String prosContent;
    String consContent;
    String newspaperlink;
    String ActualBillLink;
    String quotion="";
    //int PollStatus;
    int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_details);

        ActualBillLinkButt= (Button) findViewById(R.id.ActualBillLink);
        BillName= (TextView) findViewById(R.id.BillName);
        Synop= (TextView) findViewById(R.id.Synop);
        BillDate= (TextView) findViewById(R.id.BillDate);
        SynopContent= (TextView) findViewById(R.id.SynopContents);
        Pros= (TextView) findViewById(R.id.ProsText);
        ProsContent= (TextView) findViewById(R.id.ProsTextCont);
        Cons= (TextView) findViewById(R.id.ConsText);
        ConsContent= (TextView) findViewById(R.id.ConsTextCont);
        NewspaperLink= (TextView) findViewById(R.id.NewspaperLink);
        QuotionPoll= (TextView) findViewById(R.id.QuotionPoll);
        yes= (RelativeLayout) findViewById(R.id.Yes);
        no= (RelativeLayout) findViewById(R.id.No);
        wholepol= (RelativeLayout) findViewById(R.id.WholePoll);

        //getting id of bill
        id=getIntent().getExtras().getInt("id");

        //getting Bill Details
        BillDetails billDetails=new BillDetails(BillDetailsActivity.this,id);

        billDetails.getBillD(new BillDetails.BillsCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void getBillDetails(JSONObject jsonObject) throws JSONException {

                SpannableString spannable=new SpannableString("Synopsis");
                spannable.setSpan(new UnderlineSpan(),0,spannable.length(),0);
                Synop.setText(spannable);

                System.out.println("Bill Details : " + jsonObject);
                boolean status=jsonObject.getBoolean("status");
                //checking status - true
                if (status){
                    JSONObject jsonObject1=jsonObject.getJSONObject("msg");
                    billName =jsonObject1.getString("name");
                    billDate=jsonObject1.getString("date").substring(0,10);
                    synopContent=jsonObject1.getString("synopsis").replace("\n","\n\n");
                    prosContent=jsonObject1.getString("pros").replace("\n","\n\n");
                    consContent=jsonObject1.getString("cons").replace("\n","\n\n");

                    newspaperlink=jsonObject1.getString("newspaper_articles_links");

                    ActualBillLink=jsonObject1.getString("actual_bill_link");



                    BillName.setText(billName);
                    BillDate.setText(billDate);
                    SynopContent.setText(synopContent);
                    //if cons content is NA
                    if (consContent.equals("NA")){
                        //setting Visibilty Gone
                        Cons.setVisibility(View.GONE);
                        ConsContent.setVisibility(View.GONE);
                        //setting Text of Pros and Cons
                    //    SpannableString spannableString=new SpannableString("Pros & Cons");
                    //    spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
                        Pros.setText("Pros & Cons");
                        ProsContent.setText(prosContent);

                    }
                    // if cons contents are there
                    else{
                        //setting Visibilty Gone
                        Cons.setVisibility(View.VISIBLE);
                        ConsContent.setVisibility(View.VISIBLE);
                        //setting Text of Pros and Cons
                   //     SpannableString spannableString=new SpannableString("Pros");
                    //    spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
                        Pros.setText("Pros");
                        ProsContent.setText(prosContent);
                   //     SpannableString spannableString1=new SpannableString("Cons");
                  //      spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
                        Cons.setText("Cons");
                        ConsContent.setText(consContent);
                    }

                    //Taking empty string
                    String s1="";
                    //Newspaper-Links
                    String[] s=newspaperlink.split("\n");
                    for (int i=0;i<s.length;i++){
                          System.out.println(s[i]);
                         s1+="<a href="+s[i]+">"+"NewspaperArticle Link-"+(i+1)+"</a><br><br>";
                    }

                    NewspaperLink.setMovementMethod(LinkMovementMethod.getInstance());
                    NewspaperLink.setText(Html.fromHtml(s1,0));
                    NewspaperLink.setLinkTextColor(Color.WHITE);


                    LoginKey l=new LoginKey(BillDetailsActivity.this);
                    String loginKey=l.getLoginKey();

                    // If user is logged in means question will be there
                    if (loginKey!="" && getIntent().getExtras().getInt("whichAdap")!=4){

                        // Setting Visibilty Visible
                        wholepol.setVisibility(View.VISIBLE);

                        // Getting Question
                        quotion=jsonObject1.getString("question");
                        System.out.println("Quotion : " + quotion);
                        QuotionPoll.setText(quotion);

                        //Checking if pollstatus exist
                        // If Exist
                        if (jsonObject1.has("pollStatus")){
                            ContainPollStatus();
                        }
                        //if not exist
                        else{
                            NotContainPollStatus();
                        }
                    }

                    //if user is not logged in
                    else{
                        wholepol.setVisibility(View.GONE);
                    }

                }
            }
        });

        ActualBillLinkButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DownloadBill();
            }
        });
    }


    // Downloading PDF
    public void DownloadBill(){

        String href="http://139.59.86.83:4000"+ActualBillLink;

        File direct=new File(Environment.getExternalStorageDirectory() + "/Democrazy");

        // MAking Directory if not exists
        if (!direct.exists()){
            direct.mkdir();
        }

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(href));
        request.setTitle("Downloading pdf");
        request.setDescription("Bill : "+billName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String filename = URLUtil.guessFileName(href, null, MimeTypeMap.getFileExtensionFromUrl(href));
        request.setDestinationInExternalPublicDir("/Democrazy", filename);
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    // Containg Poll Status
    private void ContainPollStatus(){

        // Making Whole Pole Grey
        wholepol.setBackgroundColor(Color.GRAY);
        wholepol.setAlpha(0.3f);
        //Making Like and dislike Buttons Not Functionable
        no.setEnabled(false);
        yes.setEnabled(false);
    }

    // Not Containing Poll Status then user can send the poll
    private void NotContainPollStatus(){

        //clicking yes
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPollValue(1);
            }
        });

        //clicking no
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPollValue(0);
            }
        });
    }

    // Sending poll
    private void sendPollValue(int vote){

        sendPoll s=new sendPoll(BillDetailsActivity.this,id,vote);
        s.sendingVote(new sendPoll.callBack() {
            @Override
            public void getResult(JSONObject jsonObject) throws JSONException {
                boolean status=jsonObject.getBoolean("status");
                //status - true
                if (status){
                    new SweetAlertDialog(BillDetailsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Successfully Polled")
                            .show();
                }
            }
        });
    }
}
