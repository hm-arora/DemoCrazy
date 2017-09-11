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


public class OpinionPollFragment extends Fragment {
    private static final String POLL_KEY = "POLL_KEY";
    private static final String TAG = OpinionPollFragment.class.getSimpleName();
    Button btn1, btn2, btn3;
    OpinionPoll mOpinionPoll;

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
        String id = mOpinionPoll.getID();
        String question = mOpinionPoll.getQuestion();
        String stateCentralId = mOpinionPoll.getStateCentralID();
        String startDate = mOpinionPoll.getStartDate();
        String endDate = mOpinionPoll.getEndData();
        TextView descTextView = (TextView) view.findViewById(R.id.text_ques);
        btn1 = (Button) view.findViewById(R.id.button1);
        btn2 = (Button) view.findViewById(R.id.button2);
        btn3 = (Button) view.findViewById(R.id.button3);

        btn1.setOnTouchListener(listener);
        btn2.setOnTouchListener(listener);
        btn3.setOnTouchListener(listener);
        descTextView.setText(question);
        descTextView.setTypeface(null, Typeface.BOLD);
        return view;
    }

    private void checkValue(int value) {
        if (value == 1) {
            btn1.setPressed(true);
            btn2.setPressed(false);
            btn3.setPressed(false);
        } else if (value == 2) {
            btn2.setPressed(true);
            btn1.setPressed(false);
            btn3.setPressed(false);
        } else {
            btn3.setPressed(true);
            btn1.setPressed(false);
            btn2.setPressed(false);
        }
    }

    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
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
            checkValue(value);
            return true;
        }
    };
}
