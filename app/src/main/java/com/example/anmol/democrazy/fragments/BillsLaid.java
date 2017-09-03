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

import com.example.anmol.democrazy.adapters.BillsLaidAdapter;
import com.example.anmol.democrazy.R;

@SuppressLint("ValidFragment")
public class BillsLaid extends Fragment {
    private Context ctx;
    @SuppressLint("ValidFragment")
    public BillsLaid(Context ctx) {
        this.ctx=ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bills_laid_fragment, container, false);

        RecyclerView rv= (RecyclerView) v.findViewById(R.id.BillsLaidRv);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ctx);
        RecyclerView.Adapter adapter=new BillsLaidAdapter(ctx);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        return v;

    }

}