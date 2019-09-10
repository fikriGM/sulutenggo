package com.testproject.fikri;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.testproject.fikri.adapter.adapterList;
import com.testproject.fikri.appconstant.URLConstant;
import com.testproject.fikri.service.data.getList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ListView lst;
    private List<String>listArray;
    List<getList> dataC = new ArrayList<getList>();
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst = (ListView) findViewById(R.id.ls);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listArray = new ArrayList<String>();
        new LgetAr().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LgetAr extends AsyncTask<String,Void,List<getList>>{

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            dialog.setTitle("\t Please Wait");
            dialog.setMessage("\t Load Data From Serve");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected List<getList> doInBackground(String... strings) {
            try {
                return URLConstant.getapi().ListgetNews(MainActivity.this);
            }catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<getList> cat) {
            super.onPostExecute(cat);
            dialog.dismiss();
            try {
                if (cat!=null){
                    dataC =cat;
                    t = cat.size();
                    adapterList ada = new adapterList(MainActivity.this, dataC);
                    lst.setAdapter(ada);
                }else{
                    Log.e("ERROR","ERROR");
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    protected Bitmap loadImage(String utl2) {
        // TODO Auto-generated method stub

        Log.v("utl2--", utl2);
        URL imageURL = null;

        Bitmap bitmap = null;
        try {
            imageURL = new URL(utl2);
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpsURLConnection connection = (HttpsURLConnection) imageURL
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return bitmap;
    }
}
