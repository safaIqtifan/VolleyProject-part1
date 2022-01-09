package com.example.volleyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volleyproject.Adapter.AlbumAdapter;
import com.example.volleyproject.Adapter.PostsAdapter;
import com.example.volleyproject.app.AppController;
import com.example.volleyproject.model.AlbumModel;
import com.example.volleyproject.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    final String URL = "https://jsonplaceholder.typicode.com/photos";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (isNetworkConnected()) {
            getJSONArray();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }


    }

    public void getJSONArray() {
        showDialog();
        ArrayList<AlbumModel> data = new ArrayList<>();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        int albumId = jsonObject.getInt("albumId");
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String url = jsonObject.getString("url");
                        String thumbnailUrl = jsonObject.getString("thumbnailUrl");

                        data.add(new AlbumModel(String.valueOf(albumId), String.valueOf(id), title, url, thumbnailUrl));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                AlbumAdapter albumAdapter = new AlbumAdapter(AlbumActivity.this, data);
                recyclerView.setAdapter(albumAdapter);

                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(AlbumActivity.this);
        progressDialog.setMessage("Loading ......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}