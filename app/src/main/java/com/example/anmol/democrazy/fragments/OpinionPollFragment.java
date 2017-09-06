package com.example.anmol.democrazy.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anmol.democrazy.R;


public class OpinionPollFragment extends Fragment {
    private static final String QUESTION = "QUESTION";

    /**
     * Used to initialize OpinionPollFragment object
     *
     * @param question add to object list
     * @return OpinionPollFragment object
     */
    public static OpinionPollFragment newInstance(String question) {
        OpinionPollFragment opinionPollFragment = new OpinionPollFragment();
        Bundle args = new Bundle();
        args.putCharSequence(QUESTION, question);
        opinionPollFragment.setArguments(args);
        return opinionPollFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opinion_poll_fragment, container, false);
        String question_text = getArguments().getString(QUESTION);
        TextView descTextView = (TextView) view.findViewById(R.id.text_ques);
        descTextView.setText(question_text);
        descTextView.setTypeface(null, Typeface.BOLD);
        return view;
    }
}
