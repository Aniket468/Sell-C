package com.example.aniketkumar.test;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aniket Kumar on 28-Jan-18.
 */

public class View_Holder_Cardview extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    TextView description;
    TextView price,sr,id,rupee;
    ImageView imageView;
    ImageButton imageButton;
    View_Holder_Cardview(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.card);
        title = (TextView) itemView.findViewById(R.id.name);
        price=itemView.findViewById(R.id.price);
        id=itemView.findViewById(R.id.id);
        id.setVisibility(View.GONE);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        imageButton=itemView.findViewById(R.id.vertical);
    }
}