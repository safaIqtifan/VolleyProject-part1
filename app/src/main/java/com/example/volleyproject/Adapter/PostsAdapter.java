package com.example.volleyproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.volleyproject.databinding.ItemPostBinding;
import com.example.volleyproject.model.Post;
import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.FarmViewHolder> {

    Context activity;
    ArrayList<Post> list;

    public PostsAdapter(Context activity, ArrayList<Post> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public FarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new FarmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmViewHolder holder, int position) {

        Post post = list.get(position);

        holder.binding.userId.setText(post.getUserId());
        holder.binding.id.setText(post.getId());
        holder.binding.title.setText(post.getTitle());
        holder.binding.body.setText(post.getBody());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public void setPosts(ArrayList<Post> posts){
//        notifyDataSetChanged();
//    }

    public class FarmViewHolder extends RecyclerView.ViewHolder {
        ItemPostBinding binding;
        public FarmViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
