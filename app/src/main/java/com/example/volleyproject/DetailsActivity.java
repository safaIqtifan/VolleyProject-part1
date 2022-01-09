package com.example.volleyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volleyproject.model.AlbumModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    AlbumModel albumModel;
    TextView albumId, id, title;
    ImageView albumImag;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        albumId = findViewById(R.id.albumId);
        id = findViewById(R.id.id);
        title = findViewById(R.id.title);
        albumImag = findViewById(R.id.albumImag);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//
//        }

        // here the modle form adapter
        albumModel = getIntent().getParcelableExtra("album_model");

        albumId.setText(String.valueOf(albumModel.getAlbumId()));
        id.setText(String.valueOf(albumModel.getId()));
        title.setText(albumModel.getTitle());
        Picasso.get().load(albumModel.getUrl()).into(albumImag);

    }
}