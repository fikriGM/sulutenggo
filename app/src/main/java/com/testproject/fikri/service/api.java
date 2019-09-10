package com.testproject.fikri.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testproject.fikri.MainActivity;
import com.testproject.fikri.appconstant.URLConstant;
import com.testproject.fikri.service.data.getList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class api {
    public static final String BASE_URL = URLConstant.BASE_URL;


    public HttpsURLConnection getHttpURLConnection(Context context, String endPoint) throws IOException{
        URL url = new URL(BASE_URL+endPoint);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setReadTimeout(URLConstant.READ_TIMEOUT);
        connection.setConnectTimeout(URLConstant.CONNECTION_TIMEOUT);
        return connection;

    }

    private String readResponse(HttpsURLConnection connection) {
        try {
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            String data = "";
            while (line != null) {
                line = reader.readLine();
                data = data + line;

            }
            return data.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getNews(Context context) throws  IOException{

      try{
          HttpsURLConnection urlConnection = this.getHttpURLConnection(context,"v2/everything?q=bitcoin&from=" +
                  "2019-08-10&sortBy=publishedAt&apiKey=89142a5c40d642bea88f0b6324abf451");
          urlConnection.setRequestMethod("GET");
          urlConnection.setConnectTimeout(URLConstant.CONNECTION_TIMEOUT);
          urlConnection.setReadTimeout(URLConstant.READ_TIMEOUT);
          if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
              Log.v("Response", "Respons= " + urlConnection.getResponseCode());
              String response = this.readResponse(urlConnection);
              Log.v("Response", "Response= " + response);
              return response;
          } else  if(urlConnection.getResponseCode() == HttpsURLConnection.HTTP_INTERNAL_ERROR){
              Log.v("Response", "Response= " + urlConnection.getResponseCode());
              String response = this.readResponse(urlConnection);
              Log.v("Response", "Response= " + response);
              return response;
          }
      } catch (SocketTimeoutException e){
          e.printStackTrace();
      }catch (NullPointerException e){
          e.printStackTrace();
      }catch (Exception e){
          e.printStackTrace();
      }
        return null;
    }

    public List<getList> ListgetNews(Context context) throws  IOException{

        try{
            HttpsURLConnection urlConnection = this.getHttpURLConnection(context,"v2/everything?q=bitcoin&from=" +
                    "2019-08-10&sortBy=publishedAt&apiKey=89142a5c40d642bea88f0b6324abf451");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(URLConstant.CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(URLConstant.READ_TIMEOUT);
            List<getList>listGet =new ArrayList<getList>();
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                Log.v("Response", "Respons= " + urlConnection.getResponseCode());
                String response = this.readResponse(urlConnection);
                Log.v("Response", "Response= " + response);
                try {
                    JSONObject jso = new JSONObject(response);
                    getList gets = new getList();
                    gets.setStatus(jso.getString("status"));
                    gets.setStatus(jso.getString("totalResults"));
                    JSONArray jsA1 = jso.getJSONArray("articles");
                    for (int z=0; z<jsA1.length(); z++){
                        JSONObject jso2 = jsA1.getJSONObject(z);
                        JSONObject jso3 = jso2.getJSONObject("source");
                        getList get = new getList();
                        get.setId(jso3.getString("id"));
                        get.setName(jso3.getString("name"));
                        get.setAuthor(jso2.getString("author"));
                        get.setTitle(jso2.getString("title"));
                        get.setDescription(jso2.getString("description"));
                        get.setUrl(jso2.getString("url"));
                        get.setUrlToImage(jso2.getString("urlToImage"));
                        get.setPublishedAt(jso2.getString("publishedAt"));
                        get.setContent(jso2.getString("content"));
                        listGet.add(get);

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Log.v("JSON ERROR","JSON=> "+e);
                }
                return listGet;
            }
        } catch (SocketTimeoutException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
