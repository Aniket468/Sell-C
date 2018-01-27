package com.example.aniketkumar.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Selling_Activity extends AppCompatActivity {

   ImageView i1,i2,i3;
   EditText name,year,price,hostel,brand,othertext;
   CheckBox suspension,gear,disc,other;
   Button submit;
   int status=0;
   int sta1=0,sta2=0,sta3=0;
   int pta1=0,pta2=0,pta3=0;
   String res;
LinearLayout container,progress;
ProgressBar progressBar;
   String id;
SharedPreferences sp;

   String features="";
Bitmap bit1,bit2,bit3;
    String nam,bran,yea,hoste,pric;
   int p=0;


   String encoded_string,encoded_string1,encoded_string2;
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Warning!!!");
        builder.setMessage("Are you really want to Leave this Form");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
         AlertDialog alert=builder.create();
        alert.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_);
        i1=findViewById(R.id.im1);
        i2=findViewById(R.id.im2);
        i3=findViewById(R.id.im3);
        name=findViewById(R.id.name);
        brand=findViewById(R.id.brand);
        year=findViewById(R.id.year);
        price=findViewById(R.id.price);
        hostel=findViewById(R.id.hostel);
        suspension=findViewById(R.id.suspension);
        gear=findViewById(R.id.gear);
        disc=findViewById(R.id.brake);
        other=findViewById(R.id.other);
        othertext=findViewById(R.id.othertext);
        submit=findViewById(R.id.submit);
        container=findViewById(R.id.container);
        progress=findViewById(R.id.progress);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nam=name.getText().toString();
                bran=brand.getText().toString();
                yea=year.getText().toString();
                hoste=hostel.getText().toString();
                pric=price.getText().toString();
                if(nam.equals("")||bran.equals("")||yea.equals("")||hoste.equals("")||pric.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter all the Fields",Toast.LENGTH_SHORT).show();
                }
              else if((pta1+sta1)<1||(pta2+sta2)<1||(pta3+sta3)<1)
                {
                    Toast.makeText(getApplicationContext(),"you must have to upload all Three images",Toast.LENGTH_SHORT).show();
                }
      else {
                    String temp="";
                    if (suspension.isChecked()) {
                        temp="Suspension";
                        if(features.equals(""))
                        {
                            features=temp;
                        }
                        else
                        {
                            features+="\n"+temp;
                        }
                    }
                    if (gear.isChecked()) {
                        temp="Gear";
                        if(features.equals(""))
                        {
                            features=temp;
                        }
                        else
                        {
                            features+="\n"+temp;
                        }
                    }
                    if (disc.isChecked()) {
                        temp="Disc";
                        if(features.equals(""))
                        {
                            features=temp;
                        }
                        else
                        {
                            features+="\n"+temp;
                        }
                    }
                    if (other.isChecked()) {
                        String s1 = othertext.getText().toString();
                        if (s1.equals("")) {
                            Toast.makeText(getApplicationContext(), "Mention other Features...", Toast.LENGTH_LONG).show();
                            return;
                        } else
                            features+="\n"+s1;
                    }

                    Log.d("TAG",features);
                    bit1 = ((BitmapDrawable) i1.getDrawable()).getBitmap();
                    bit2 = ((BitmapDrawable) i2.getDrawable()).getBitmap();
                    bit3 = ((BitmapDrawable) i3.getDrawable()).getBitmap();

                    new Encode_image().execute();

                }




            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Selling_Activity.this,R.style.AppTheme_Dark_Dialog);
                builder.setTitle("Select upload option..");
                builder.setMessage("Choose one......")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                p=1;
                                dialog.cancel();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                p=1;
                                Intent intent = new Intent();

                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);



                            }
                        });
              AlertDialog alert = builder.create();
                alert.show();

            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Selling_Activity.this,R.style.AppTheme_Dark_Dialog);
                builder.setTitle("Select upload option..");
                builder.setMessage("Choose one......")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                p=2;
                                dialog.cancel();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                 p=2;
                                Intent intent = new Intent();

                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Selling_Activity.this,R.style.AppTheme_Dark_Dialog);
                builder.setTitle("Select upload option..");
                builder.setMessage("Choose one......")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                p=3;
                                dialog.cancel();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                p=3;
                                Intent intent = new Intent();

                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }





    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            container.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
            id=sp.getString("id",null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bit1.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            //bit1.recycle();
           // bit1=null;

            bit2.compress(Bitmap.CompressFormat.JPEG, 20, stream1);
         //   bit2.recycle();
          //  bit2=null;

            bit3.compress(Bitmap.CompressFormat.JPEG, 20, stream2);
           // bit3.recycle();
        //    bit3=null;


            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            Log.d("TAG",encoded_string);

            byte[] array1 = stream1.toByteArray();
            encoded_string1 = Base64.encodeToString(array1,0);

            byte[] array2 = stream2.toByteArray();
            encoded_string2 = Base64.encodeToString(array2, 0);


           String user_url="http://192.168.43.210/test_connection/sell.php";

            try {
                URL url =new URL(user_url);
                Log.d("TAG","user");
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                Log.d("TAG","user");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG","user");
                OutputStream os=httpURLConnection.getOutputStream();
                Log.d("TAG","user");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                Log.d("TAG","user");


                        String data= URLEncoder.encode("encoded_string","UTF-8")+"="+ URLEncoder.encode(encoded_string,"UTF-8")+"&"+
                        URLEncoder.encode("encoded_string1","UTF-8")+"="+ URLEncoder.encode(encoded_string1,"UTF-8")+"&"+
                        URLEncoder.encode("encoded_string2","UTF-8")+"="+ URLEncoder.encode(encoded_string2,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(nam,"UTF-8")+"&"+
                        URLEncoder.encode("brand_string","UTF-8")+"="+ URLEncoder.encode(bran,"UTF-8")+"&"+
                        URLEncoder.encode("year","UTF-8")+"="+ URLEncoder.encode(yea,"UTF-8")+"&"+
                        URLEncoder.encode("price","UTF-8")+"="+ URLEncoder.encode(pric,"UTF-8")+"&"+
                        URLEncoder.encode("hostel","UTF-8")+"="+ URLEncoder.encode(hoste,"UTF-8")+"&"+
                        URLEncoder.encode("features","UTF-8")+"="+ URLEncoder.encode(features,"UTF-8")+"&"+
                        URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");




                Log.d("TAG","user1213");
                Log.d("TAGG","data="+data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is=new BufferedInputStream(httpURLConnection.getInputStream());
                res=convertStreamToString(is);
                Log.d("results","res="+res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::","error1="+ e.toString());
            } catch (Exception e) {
                Log.d("TAGG::","error2="+ e.toString());
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

            container.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            features="";
            Log.d("TAG",""+res);
            if(res!=null)
            {
                if(res.length()==8) {
                    Toast.makeText(getApplicationContext(), "Data Updated Successfully",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data Failed to upload",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Snackbar.make(container,"No internet Connection or \nServer not responding ",Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       new Encode_image().execute();
                    }
                }).setActionTextColor(Color.RED).show();
            }
        }
    }



























    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1",""+resultCode);
        if(data!=null&&resultCode==RESULT_OK&&requestCode==0) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap b = (Bitmap) data.getExtras().get("data");

            if (p == 1) {
                i1.setImageBitmap(b);
                sta1++;
            }
            if (p == 2) {
                i2.setImageBitmap(b);
                sta2++;
            }
            if (p == 3) {
                i3.setImageBitmap(b);
                sta3++;
            }
            p=0;
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {


              Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if(p==1) {
                    i1.setImageBitmap(bitmap);
                   pta1++;
                }
                if(p==2) {
                    i2.setImageBitmap(bitmap);
                    pta2++;
                }
                if(p==3) {
                    i3.setImageBitmap(bitmap);
                    pta3++;
                }
                p=0;
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
