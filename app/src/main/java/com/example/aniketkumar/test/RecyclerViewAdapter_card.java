package com.example.aniketkumar.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aniket Kumar on 28-Jan-18.
 */

public class RecyclerViewAdapter_card extends RecyclerView.Adapter<View_Holder_Cardview> {

    List<Item_Data_Card> list = Collections.emptyList();
    Context context;
    RecyclerView rc;

    public RecyclerViewAdapter_card(List<Item_Data_Card> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder_Cardview onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
          View_Holder_Cardview holder = new View_Holder_Cardview(v);
          return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder_Cardview holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).title);
        ImageLoadTask imageLoadTask = new ImageLoadTask(list.get(position).imageId, holder.imageView);
        imageLoadTask.execute();
        holder.description.setText(list.get(position).description);
        holder.price.setText(list.get(position).price);
        holder.id.setText(list.get(position).id);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPopupMenuClick(view, position);
            }
        });

        // animate(holder);

    }


    public void onPopupMenuClick(View view, final int pos) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.pop_up, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
                        String name=list.get(pos).title;
                        String brand=list.get(pos).description;
                        String price=list.get(pos).price;
                        String image_url=list.get(pos).imageId;
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Cycle_Name: "+name+"\n"+"Brand: "+brand+"\n"+"Price: "+price+"\n"+"Image_url: "+image_url);
                        sendIntent.setType("text/plain");
                        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(sendIntent);

                        return true;

                    case R.id.menu_delete:
                        new BackgroundTask1().execute(list.get(pos).id,""+pos);
                        return true;

                    default:
                        return false;
                }
            }
        });
        popup.show();
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
                Log.d("tag", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.d("Tag", "not");
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
        rc=recyclerView;
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Item_Data_Card data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(ItemData data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public class BackgroundTask1 extends AsyncTask<String, Void, Void> {

        String res;
        String ID;
        int pos;
        HashMap<String, String> contact = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String id = params[0];
            pos=Integer.parseInt(params[1]);
            //ID=id;
            String user_url = "http://192.168.43.210/test_connection/deleteProduct.php";

            //  Log.d("TAGG::",id);
            try {
                URL url = new URL(user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                Log.d("TAG", "user");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG", "user");
                OutputStream os = httpURLConnection.getOutputStream();
                Log.d("TAG", "user");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Log.d("TAG", "user");
                String data = URLEncoder.encode("sr", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(is);
                Log.d("results", "res=" + res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::", "error11=" + e.toString());
            } catch (IOException e) {
                Log.d("TAGG::", "error22=" + e.toString());
            }

            return null;
        }


        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(res!=null)
            {
                if(res.contains("Success"))
                {
                    Toast.makeText(context,"Element Deleted successfully",Toast.LENGTH_LONG).show();
                    list.remove(pos);
                    rc.removeViewAt(pos);
                    RecyclerViewAdapter_card recyclerViewAdapter_card=new RecyclerViewAdapter_card(list,context);
                    recyclerViewAdapter_card.notifyDataSetChanged();


                }
                else
                {
                    Toast.makeText(context,"Element Deletion Failed ",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(context,"No internet Connection",Toast.LENGTH_LONG).show();
            }
        }
    }
}