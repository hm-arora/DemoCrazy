package com.example.anmol.democrazy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anmol.democrazy.Bills;
import com.example.anmol.democrazy.R;
import com.squareup.picasso.Picasso;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder> {

    String s[] = {"Bills", "Financial Inclusions", "Opinion Polls"};

    String url[] = {"http://www.fdrindia.org/wp-content/uploads/2015/07/Parliament-of-India.png",
            "http://media.gettyimages.com/videos/indian-rupee-currency-bills-banknote-falling-video-id451311393?s=256x256"
            , "http://www.differencebetween.info/sites/default/files/images/5/exit-polls.jpg"};

    Context ctx;

    public RecyclerAdapterMain(Context ctx) {
        this.ctx = ctx;

    }

    @Override
    public RecyclerAdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterMain.ViewHolder holder, int position) {

        Picasso.with(ctx).load(url[position]).fit().into(holder.im);
        holder.tx.setText(s[position]);


    }

    @Override
    public int getItemCount() {
        return s.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel;
        TextView tx;
        ImageView im;

        public ViewHolder(View itemView) {
            super(itemView);

            tx = itemView.findViewById(R.id.text);
            im = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos == 0) {
                        Intent i = new Intent(ctx, Bills.class);
                        ctx.startActivity(i);
                    }

                }
            });

        }
    }
}
