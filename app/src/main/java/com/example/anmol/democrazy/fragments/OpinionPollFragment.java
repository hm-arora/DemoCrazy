package com.example.anmol.democrazy.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.opinion.OpinionPoll;

import java.util.HashMap;


public class OpinionPollFragment extends Fragment {
    private static final String POLL_KEY = "POLL_KEY";

    // Persisting the value of Poll
    public static HashMap<String,String> hashMap=new HashMap<>();

    private static final String TAG = OpinionPollFragment.class.getSimpleName();
    Button btn1, btn2, btn3,mShowButton;
    OpinionPoll mOpinionPoll;
    private boolean isShowButton;

    // Opinion Poll Activity
    private String id;

    /**
     * Used to initialize OpinionPollFragment object
     *
     * @param object add to object list
     * @return OpinionPollFragment object
     */
    public static OpinionPollFragment newInstance(OpinionPoll object) {
        OpinionPollFragment opinionPollFragment = new OpinionPollFragment();
        Bundle args = new Bundle();
        String question = object.getQuestion();
        Log.d(TAG, question + " ");
        args.putSerializable(POLL_KEY, object);
        opinionPollFragment.setArguments(args);
        return opinionPollFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.opinion_poll_fragment, container, false);
        mOpinionPoll = (OpinionPoll) getArguments().getSerializable(POLL_KEY);
        id = mOpinionPoll.getID();
        String question = mOpinionPoll.getQuestion();
        String stateCentralId = mOpinionPoll.getStateCentralID();
        String startDate = mOpinionPoll.getStartDate();
        String endDate = mOpinionPoll.getEndData();
        boolean isShowButton = mOpinionPoll.isShowButton();
        TextView descTextView = (TextView) view.findViewById(R.id.text_ques);
        btn1 = (Button) view.findViewById(R.id.button1);
        btn2 = (Button) view.findViewById(R.id.button2);
        btn3 = (Button) view.findViewById(R.id.button3);
        mShowButton = (Button) view.findViewById(R.id.submitButton);


        // Yes Clicking
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickingPoll(1);
            }
        });

        //No Clicking
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickingPoll(2);
            }
        });


        // Dont Know Cliking
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickingPoll(3);
            }
        });

        descTextView.setText(question);
        descTextView.setTypeface(null, Typeface.BOLD);


        if(isShowButton) {
            mShowButton.setOnClickListener(mShowButtonListener);
            mShowButton.setVisibility(View.VISIBLE);
        }
        return view;
    }


    // Clicking Submit Poll Button
    View.OnClickListener mShowButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            System.out.println(hashMap);

        }
    };


    // Cliking Poll
    void ClickingPoll(int i){

        switch (i){

            // yes
            case 1:
                btn1.setPressed(true);
                btn2.setPressed(false);
                btn3.setPressed(false);

                hashMap.put(id,"1");

                break;

            // No
            case 2:

                btn2.setPressed(true);
                btn1.setPressed(false);
                btn3.setPressed(false);

                hashMap.put(id,"0");

                break;

            // Dont Know
            case 3:
                btn3.setPressed(true);
                btn1.setPressed(false);
                btn2.setPressed(false);

                hashMap.put(id,"2");

                break;
        }

    }
}