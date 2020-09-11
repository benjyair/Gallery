package io.benjyair.gallery.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.benjyair.gallery.R;
import io.benjyair.gallery.net.Gallery;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Gallery.Picture> data = new ArrayList<>();

    public void addData(List<Gallery.Picture> data, boolean clearFlag) {
        if (clearFlag) {
            this.data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Gallery.Picture picture = data.get(position);
        Holder holder = (Holder)viewHolder;

        holder.tv_title.setText(picture.getTitle());
        Glide.with(holder.iv_picture)
                .load(picture.getThumb())
                .override(300, 300)
                .into(holder.iv_picture);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class Holder extends RecyclerView.ViewHolder {
        ImageView iv_picture;
        TextView tv_title;

        Holder(View itemView) {
            super(itemView);
            iv_picture = itemView.findViewById(R.id.iv_picture);
            tv_title = itemView.findViewById(R.id.tv_title);
        }

    }

}
