package com.example.volleyproject.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.volleyproject.DetailsActivity;
import com.example.volleyproject.MainActivity;
import com.example.volleyproject.app.AppController;
import com.example.volleyproject.databinding.ItemAlbumBinding;
import com.example.volleyproject.databinding.ItemPostBinding;
import com.example.volleyproject.model.AlbumModel;
import com.example.volleyproject.model.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    Activity activity;
    ArrayList<AlbumModel> list;

    public AlbumAdapter(Activity activity, ArrayList<AlbumModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemAlbumBinding binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AlbumModel albumModel = list.get(position);

        holder.binding.title.setText(albumModel.getTitle());
        Picasso.get().load(albumModel.getThumbnailUrl()).into(holder.binding.thumbnailImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAlbumBinding binding;

        public ViewHolder(@NonNull ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.linearLayout.setOnClickListener(v -> {
                AlbumModel albumModel = list.get(getAdapterPosition());

                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra("album_model", albumModel);
                activity.startActivity(intent);
            });

        }
    }
}
