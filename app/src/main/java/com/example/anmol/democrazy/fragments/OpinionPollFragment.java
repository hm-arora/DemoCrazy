package com.example.anmol.democrazy.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.opinion.OpinionPoll;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class OpinionPollFragment extends Fragment {
    private static final String POLL_KEY = "POLL_KEY";
    private static final String PAGE_NUMBER = "PAGE";

    public interface OnSubmitListener {
        void onSubmitButtonListener(Map<String, Integer> object);
    }


    public interface OnButtonListener {
        void onAnyButtonListener(int pos);
    }

    OnSubmitListener onSubmitListener;
    OnButtonListener onButtonListener;
    // Persisting the value of Poll
    public static HashMap<String, Integer> hashMap = new HashMap<>();

    private static final String TAG = OpinionPollFragment.class.getSimpleName();
    Button btn1, btn2, btn3, mShowButton;
    OpinionPoll mOpinionPoll;
    int page_number;
    private boolean isShowButton;

    // Opinion Poll Activity
    private String id;

    /**
     * Used to initialize OpinionPollFragment object
     *
     * @param object add to object list
     * @return OpinionPollFragment object
     */
    public static OpinionPollFragment newInstance(OpinionPoll object, int page_number) {
        OpinionPollFragment opinionPollFragment = new OpinionPollFragment();
        Bundle args = new Bundle();
        String question = object.getQuestion();
        Log.d(TAG, question + " ");
        args.putSerializable(POLL_KEY, object);
        args.putInt(PAGE_NUMBER, page_number);
        opinionPollFragment.setArguments(args);
        return opinionPollFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        a = (Activity) context;
        try {
            onButtonListener = (OnButtonListener) a;
            onSubmitListener = (OnSubmitListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString()
                    + " must implement OnSubmitListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.opinion_poll_fragment, container, false);
        mOpinionPoll = (OpinionPoll) getArguments().getSerializable(POLL_KEY);
        page_number = getArguments().getInt(PAGE_NUMBER);
        id = mOpinionPoll.getID();
        // Put by default don't know as answer
        hashMap.put(id, 2);
        String question = mOpinionPoll.getQuestion();
        String stateCentralId = mOpinionPoll.getStateCentralID();
        String startDate = mOpinionPoll.getStartDate();
        String endDate = mOpinionPoll.getEndData();
        isShowButton = mOpinionPoll.isShowButton();
        TextView descTextView = (TextView) view.findViewById(R.id.text_ques);
        btn1 = (Button) view.findViewById(R.id.button1);
        btn2 = (Button) view.findViewById(R.id.button2);
        btn3 = (Button) view.findViewById(R.id.button3);
        mShowButton = (Button) view.findViewById(R.id.submitButton);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        descTextView.setText(question);
        descTextView.setTypeface(null, Typeface.BOLD);


        // Used to show button when it's a last page of polls
        if (isShowButton) {
            mShowButton.setOnClickListener(mShowButtonListener);
            mShowButton.setVisibility(View.VISIBLE);
            mShowButton.setPressed(true);
        }


        return view;
    }


    // Clicking Submit Poll Button
    View.OnClickListener mShowButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("Are you sure?")
                    .setContentText("Won't be able to change later!")
                    .setConfirmText("Yes,Submit it!")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            onSubmitListener.onSubmitButtonListener(hashMap);
                            hashMap.clear();
                            sDialog.cancel();
                        }
                    })
                    .show();
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which) {
//                        case DialogInterface.BUTTON_POSITIVE:
//                            //Yes button clicked
//                            onSubmitListener.onSubmitButtonListener(hashMap);
//                            hashMap.clear();
//                            break;
//
//                        case DialogInterface.BUTTON_NEGATIVE:
//                            //No button clicked
//                            break;
//                    }
//                }
//            };
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Submit ? ").setMessage("Are you sure ?").setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
        }
    };


    // Use to for showing submit button on the last poll
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mShowButton != null) {
            if (isVisibleToUser && isShowButton) {
                mShowButton.setVisibility(View.VISIBLE);
                mShowButton.setPressed(true);
            }
            if (!isVisibleToUser && isShowButton)
                mShowButton.setVisibility(View.GONE);
        }
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser + id);
    }

    // Clicking Poll
    private void ClickingPoll(int i) {

        Log.e(TAG, "ClickingPoll: " + i);
        switch (i) {
            // yes case
            case 1:
                int green_color = ContextCompat.getColor(getActivity(), R.color.light_green_700);
                btn1.setSelected(true);
                btn1.setTextColor(Color.WHITE);
                btn1.setBackgroundColor(green_color);
                btn2.setSelected(false);
                btn2.setBackgroundResource(R.drawable.button_style_blue);
                btn2.setTextColor(Color.BLACK);
                btn3.setSelected(false);
                btn3.setTextColor(Color.BLACK);
                btn3.setBackgroundResource(R.drawable.button_style_blue);

                hashMap.put(id, 1);
                break;
            // No case
            case 2:
                int red_color = ContextCompat.getColor(getActivity(), R.color.red_400);
                btn2.setSelected(true);
                btn2.setTextColor(Color.WHITE);
                btn2.setBackgroundColor(red_color);
                btn1.setSelected(false);
                btn1.setTextColor(Color.BLACK);
                btn1.setBackgroundResource(R.drawable.button_style_blue);
                btn3.setSelected(false);
                btn3.setTextColor(Color.BLACK);
                btn3.setBackgroundResource(R.drawable.button_style_blue);
                hashMap.put(id, 0);
                break;

            // Don't Know case
            case 3:

                int blue_color = Color.parseColor("#3885F9");
                btn3.setSelected(true);
                btn3.setTextColor(Color.WHITE);
                btn3.setBackgroundColor(blue_color);
                btn1.setSelected(false);
                btn1.setTextColor(Color.BLACK);
                btn1.setBackgroundResource(R.drawable.button_style_blue);
                btn2.setSelected(false);
                btn2.setTextColor(Color.BLACK);
                btn2.setBackgroundResource(R.drawable.button_style_blue);
                hashMap.put(id, 2);
                break;
        }

        onButtonListener.onAnyButtonListener(page_number);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int value = 0;
            switch (v.getId()) {
                case R.id.button1:
                    value = 1;
                    break;
                case R.id.button2:
                    value = 2;
                    break;
                case R.id.button3:
                    value = 3;
                    break;
            }
            ClickingPoll(value);
        }
    };
}