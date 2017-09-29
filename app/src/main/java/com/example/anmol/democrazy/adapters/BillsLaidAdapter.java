package com.example.anmol.democrazy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anmol.democrazy.BillDetailsActivity;
import com.example.anmol.democrazy.R;

import java.util.List;


public class BillsLaidAdapter extends RecyclerView.Adapter<BillsLaidAdapter.ViewHolder> {

    private Context ctx;
    private List<Integer> id;
    private List<String> name;
    private List<String> centeralName;
    private List<String> date;

    private int i;

    public BillsLaidAdapter(Context ctx, List<Integer> id, List<String> name, List<String> centeralName, List<String> date,int i) {
        this.id=id;
        this.ctx=ctx;
        this.name=name;
        this.centeralName=centeralName;
        this.date=date;
        this.i=i;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Nametx,centeralNametx,datetx;

        public ViewHolder(View itemView)
        {
            super(itemView);

            Nametx= (TextView) itemView.findViewById(R.id.Name_of_Bill);
            centeralNametx= (TextView) itemView.findViewById(R.id.Central_Name_of_Bill);
            datetx= (TextView) itemView.findViewById(R.id.date_of_Bill);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos=getAdapterPosition();
                    Intent in=new Intent(ctx, BillDetailsActivity.class);
                    in.putExtra("id",id.get(pos));
                    in.putExtra("whichAdap",i);
                    ctx.startActivity(in);

                }
            });

        }
    }


    @Override
    public BillsLaidAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new BillsLaidAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bills_laid_card,parent,false));
    }

    @Override
    public void onBindViewHolder(final BillsLaidAdapter.ViewHolder holder, int position) {

        holder.Nametx.setText(name.get(position));
        holder.centeralNametx.setText(centeralName.get(position));
        holder.datetx.setText(date.get(position));

    }

    @Override
    public int getItemCount()
    {
        return id.size();
    }

}

