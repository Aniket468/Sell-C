package com.example.aniketkumar.test;

import android.view.View;

/**
 * Created by Aniket Kumar on 27-Jan-18.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}