package com.example.anmol.democrazy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anmol.democrazy.BillActivity;
import com.example.anmol.democrazy.FinancialInclusionsActivity;
import com.example.anmol.democrazy.LoginActivity;
import com.example.anmol.democrazy.OpinionPollActivity;
import com.example.anmol.democrazy.R;
import com.example.anmol.democrazy.login.LoginKey;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.squareup.picasso.Picasso;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder> {
    private Context ctx;
    String s[] = {"Bills Data", "Financial Inclusions", "Opinion Polls"};
    String url[] = {"http://www.fdrindia.org/wp-content/uploads/2015/07/Parliament-of-India.png",
            "http://media.gettyimages.com/videos/indian-rupee-currency-bills-banknote-falling-video-id451311393?s=256x256"
            , "http://www.differencebetween.info/sites/default/files/images/5/exit-polls.jpg"};

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

            tx = (TextView) itemView.findViewById(R.id.text);
            im = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    switch (pos) {
                        case 0:
                            Intent i = new Intent(ctx, BillActivity.class);
                            ctx.startActivity(i);
                            break;
                        case 1:

                            SuperActivityToast.create(ctx).setText("Coming Soon").setDuration(2000)
                                    .setColor(Color.MAGENTA) .setFrame(Style.FRAME_LOLLIPOP)
                                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                    .setAnimations(Style.ANIMATIONS_POP).show();

                            break;
                        case 2:
                            LoginKey loginKey = new LoginKey(ctx);
                            if(loginKey.getLoginKey().length() == 0){
                                Toast.makeText(ctx, "You should have login first",Toast.LENGTH_SHORT).show();
                                i = new Intent(ctx, LoginActivity.class);
                            }
                            else
                                i = new Intent(ctx, OpinionPollActivity.class);
                            ctx.startActivity(i);
                            break;
                    }

                }
            });

        }
    }
}
