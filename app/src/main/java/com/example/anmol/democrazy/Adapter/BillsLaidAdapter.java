package com.example.anmol.democrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anmol.democrazy.Bills;
import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.fragments.BillsLaid;
import com.squareup.picasso.Picasso;

/**
 * Created by anmol on 27/8/17.
 */

public class BillsLaidAdapter extends RecyclerView.Adapter<BillsLaidAdapter.ViewHolder> {

    Context ctx;

    public BillsLaidAdapter(Context ctx) {

        this.ctx=ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView)
        {
            super(itemView);


        }
    }


    @Override
    public BillsLaidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BillsLaidAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bills_laid_card,parent,false));
    }

    @Override
    public void onBindViewHolder(final BillsLaidAdapter.ViewHolder holder, int position) {


//        TranslateAnimation translateAnimation=new TranslateAnimation();

        //holder.itemView

    }

    @Override
    public int getItemCount()
    {
        return 10;
    }
}

