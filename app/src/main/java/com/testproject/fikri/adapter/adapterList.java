package com.testproject.fikri.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.testproject.fikri.R;
import com.testproject.fikri.service.data.getList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class adapterList  extends ArrayAdapter<getList> {

    Context context;
    ArrayList<getList> dataTrx;
    String tes = "";
    private List<getList> listDatatrx = new ArrayList<>();

    public adapterList(Context context, List<getList> objects) {
        super(context, R.layout.listview_cat, objects);
        this.context = context;
        listDatatrx = objects;

    }

    public int getCount() {
        return listDatatrx.size();
    }


    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.listview_cat, parent, false);


        TextView a1 = (TextView) view.findViewById(R.id.id1);
        TextView a2 = (TextView) view.findViewById(R.id.id2);






        getList trxData = (getList) this.getItem(i);
        a1.setText(trxData.getTitle());
        a2.setText(trxData.getDescription());



        return view;
    }

}