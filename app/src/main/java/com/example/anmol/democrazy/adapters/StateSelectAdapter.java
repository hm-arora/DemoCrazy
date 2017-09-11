package com.example.anmol.democrazy.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.StateSet;

import java.util.List;

public class StateSelectAdapter extends RecyclerView.Adapter<StateSelectAdapter.ViewHolder> {

    private Context ctx;
    private List<Integer> id;
    private List<String> name;

    public StateSelectAdapter(Context ctx, List<Integer> id, List<String> name) {

        this.ctx=ctx;
        this.id=id;
        this.name=name;

    }
    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox ch;
        TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            ch= (CheckBox) itemView.findViewById(R.id.CheckBoxStates);
            tx= (TextView) itemView.findViewById(R.id.TextViewStates);
        }
    }


    @Override
    public StateSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new StateSelectAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_states_select, parent, false));
    }

    @Override
    public void onBindViewHolder(final StateSelectAdapter.ViewHolder holder, int position) {

        holder.tx.setText(name.get(position));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

}
