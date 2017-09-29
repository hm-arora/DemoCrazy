package com.example.anmol.democrazy.adapters;


import android.app.Activity;
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

import com.example.anmol.democrazy.BillActivity;
import com.example.anmol.democrazy.BillsData.States;
import com.example.anmol.democrazy.BillsData.TemporaryUserStates;
import com.example.anmol.democrazy.BillsData.UserStates;
import com.example.anmol.democrazy.BillsData.updateUserStates;
import com.example.anmol.democrazy.LoginActivity;
import com.example.anmol.democrazy.MainActivity;
import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.UserDetails;
import com.example.anmol.democrazy.login.LoginKey;

import org.json.JSONArray;
import org.json.JSONException;

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

            LoginKey loginKey=new LoginKey(ctx);

            String l=loginKey.getLoginKey();

            // if login is null or user is not logged in
            if (l.equals("")){
                saveChanges.setVisibility(View.GONE);
                skip.setVisibility(View.VISIBLE);
            }
            else{
                saveChanges.setVisibility(View.VISIBLE);
                skip.setVisibility(View.VISIBLE);
            }

            // Handling Skip Click Button
            onClickSkip();

            //Handling Click Save Changes;
            onClickSaveChanges();

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

                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<id.size();i++){
                    jsonArray.put(id.get(i));
                }

                LoginKey l=new LoginKey(ctx);

                String loginKey=l.getLoginKey();

                if (loginKey.equals("")){

                    //Setting Temporary User State
                    try {
                        TemporaryUserStates temporaryUserStates=new TemporaryUserStates(ctx,jsonArray);
                        temporaryUserStates.setTempUserState();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    BillActivity.getInstance().finish();

                    //Redirecting To Bill Activity
                    Intent i=new Intent(ctx, BillActivity.class);
                    ctx.startActivity(i);

                    ((Activity)ctx).finish();

                }

                // Setting User States
                else{
                    BillActivity.getInstance().finish();
                    try {
                        // Setting User States
                        UserStates userStates=new UserStates(ctx,jsonArray);
                        userStates.setUserState();

                        //Redirecting To Bill Activity
                        Intent i=new Intent(ctx, BillActivity.class);
                        ctx.startActivity(i);

                        ((Activity)ctx).finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //Save Changes Button
    public void onClickSaveChanges(){


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONArray jsonArray=new JSONArray();
                for (int i=0;i<id.size();i++){
                    jsonArray.put(id.get(i));
                }

                //checking LoginKey
                LoginKey loginKey=new LoginKey(ctx);

                //if login key is empty
                if (loginKey.getLoginKey()==""){
                    Toast.makeText(ctx,"You have to Login First for save Changes",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(ctx, LoginActivity.class);
                    ctx.startActivity(i);
                    ((Activity)ctx).finish();
                }
                //if login key is not empty
                else{
                    try {

                        BillActivity.getInstance().finish();

                        // Setting User States
                        UserStates userStates=new UserStates(ctx,jsonArray);
                        userStates.setUserState();

                        //Updating User States on server
                        updateUserStates updateUserStates=new updateUserStates(ctx);
                        updateUserStates.updateStates();
                        // updateUserStates

                        ((Activity)ctx).finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}
