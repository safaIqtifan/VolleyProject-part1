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
import com.example.volleyproject.Adapter.PostsAdapter;
import com.example.volleyproject.app.AppController;
import com.example.volleyproject.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    final String URL = "https://jsonplaceholder.typicode.com/posts";
    ArrayList<Post> data;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<>();
        rv = findViewById(R.id.recyclerView);
        if (isNetworkConnected()) {
            getJSONArray();
                    Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

}

    public void getJSONArray() {
        showDialog();
        ArrayList<Post> data = new ArrayList<>();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

//                Log.e("hzm", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        int userId = jsonObject.getInt("userId");
                        int id = jsonObject.getInt("id");
                        String title = jsonObject.getString("title");
                        String body = jsonObject.getString("body");

                        data.add(new Post(userId+"", id+"", title, body));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                PostsAdapter postsAdapter = new PostsAdapter(getApplicationContext(), data);
                rv.setAdapter(postsAdapter);

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


//    public void getJSONObject() {
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        Log.e("hzm", response.toString());
//                        try {
//                            String name = response.getString("name");
//                            String email = response.getString("email");
//                            JSONObject phone = response.getJSONObject("phone");
//                            String home = phone.getString("home");
//                            String mobile = phone.getString("mobile");
//
//                            data.add(new Post(name, email, phone));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.e("hzm", error.getMessage());
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(objectRequest);
//    }



//    public void getStringRequest() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("hzm", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("hzm", error.getMessage());
//            }
//        }) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                HashMap<String, String> map = new HashMap<>();
//                map.put("title", "aaaaaaaaaaaaaaa");
//                map.put("body", "bbbbbbbb");
//                return map;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("apiKey", "xxxxxxxxxxxxxxx");
//                return headers;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading ......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}