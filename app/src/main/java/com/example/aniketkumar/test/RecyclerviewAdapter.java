package com.example.aniketkumar.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aniket Kumar on 24-Jan-18.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter<View_Holder> {

    List<ItemData> list = Collections.emptyList();
    Context context;

    public RecyclerviewAdapter(List<ItemData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_style, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).title);
        if(position%2==0)
        holder.cv.setCardBackgroundColor(Color.parseColor("#58D68D"));
        else
            holder.cv.setCardBackgroundColor(Color.parseColor("#FAD7A0"));

//        Glide.with(context).load(list.get(position).imageId)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(context))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.imageView);
        ImageLoadTask imageLoadTask =new ImageLoadTask(list.get(position).imageId,holder.imageView);
        imageLoadTask.execute();
      //  holder.imageView.setImageBitmap(getBitmapFromURL(list.get(position).imageId));

       // holder.imageView.setImageResource(list.get(position).imageId);

        holder.description.setText(list.get(position).description);
        holder.price.setText(list.get(position).price);
        holder.id.setText(list.get(position).id);
        holder.sr.setText(list.get(position).sr);

       // animate(holder);

    }
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;


        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                Log.d("tag",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.d("Tag","not");
            imageView.setImageBitmap(result);
        }

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, ItemData data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(ItemData data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.slide);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }

}