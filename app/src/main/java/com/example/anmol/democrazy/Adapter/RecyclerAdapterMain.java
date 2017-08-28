package com.example.anmol.democrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.anmol.democrazy.Bills;
import com.example.anmol.democrazy.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by anmol on 26/8/17.
 */

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder> {

    String s[]={"Bills","Financial Inclusions","Opinion Polls"};

    String url[]={"http://www.fdrindia.org/wp-content/uploads/2015/07/Parliament-of-India.png",
                   "https://www.oswaldata.com/images/finan.png"
                    ,"http://www.differencebetween.info/sites/default/files/images/5/exit-polls.jpg"};

    Context ctx;

    public RecyclerAdapterMain(Context ctx) {

        this.ctx=ctx;

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rel;
        TextView tx;
        ImageView im;

        public ViewHolder(View itemView)
        {
            super(itemView);

            rel=itemView.findViewById(R.id.RelativeCardMain);
            tx=itemView.findViewById(R.id.TextViewMain);
            im=itemView.findViewById(R.id.ImageViewMain);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();

                    if (pos==0){
                        Intent i=new Intent(ctx, Bills.class);
                        ctx.startActivity(i);
                    }

                }
            });

        }
    }


    @Override
    public RecyclerAdapterMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterMain.ViewHolder holder, int position) {

        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        Picasso.with(ctx).load(url[position]).fit().into(holder.im);

        holder.im.setAlpha(0.5f);

        holder.tx.setText(s[position]);
        scaleAnimation.setDuration(300*(position+1));
        holder.itemView.startAnimation(scaleAnimation);


    }

    @Override
    public int getItemCount()
    {
        return s.length;
    }
}
