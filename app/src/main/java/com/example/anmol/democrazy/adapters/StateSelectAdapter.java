package com.example.anmol.democrazy.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anmol.democrazy.BillsData.States;
import com.example.anmol.democrazy.R;

import java.util.ArrayList;
import java.util.List;

public class StateSelectAdapter extends RecyclerView.Adapter<StateSelectAdapter.ViewHolder> {

    private Context ctx;
    private List<States> list;
    //Skip Button
    Button skip;
    //SaveChanges Button
    Button saveChanges;

    // List That the user selects (Updated List)
    List<Integer> id;

    public StateSelectAdapter(Context ctx, Button skip, Button saveChanges, List<States> list) {

        this.ctx=ctx;
        this.list=list;
        this.skip=skip;
        this.saveChanges=saveChanges;
        id=new ArrayList<>();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox ch;
        TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            ch= (CheckBox) itemView.findViewById(R.id.CheckBoxStates);
            tx= (TextView) itemView.findViewById(R.id.TextViewStates);

            // Handling Skip Click Button
            onClickSkip();

        }
    }


    @Override
    public StateSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new StateSelectAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_states_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final StateSelectAdapter.ViewHolder holder, final int position) {

        holder.tx.setText(list.get(position).getName());

        holder.ch.setChecked(list.get(position).isChecked());

        holder.ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb= (CheckBox) view;
                list.get(position).setChecked(cb.isChecked());

                // If CheckBox is Checked
                if (cb.isChecked()){
                    id.add(list.get(position).getId());
                }
                /*
                  list.remove(1) removes the object at position 1
                  and remove(new Integer(1)) removes the first occurrence
                  of the specified element from this list.
                 */
                else{
                    id.remove(new Integer(list.get(position).getId()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Skip Click Button
    public void onClickSkip(){

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

}
