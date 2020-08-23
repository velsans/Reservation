package com.reservation.ui.album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.reservation.R;
import com.reservation.databinding.AlbumRowBinding;
import com.reservation.model.AlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsRecyclerAdapter extends RecyclerView.Adapter<AlbumsRecyclerAdapter.NewsViewHolder> {

    private List<AlbumModel> NewsList;

    private View.OnClickListener mOnItemClickListener;

    public AlbumsRecyclerAdapter() {
        this.NewsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumRowBinding itenBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.album_row, parent, false);

        return new NewsViewHolder(itenBinding);
    }

    public AlbumModel getNews(int position) {
        return NewsList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bindNews(NewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public void setNewsList(List<AlbumModel> newsList) {
        this.NewsList.clear();
        this.NewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private AlbumRowBinding binding;

        NewsViewHolder(AlbumRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        void bindNews(AlbumModel news) {
            if (binding.getAlbumModel() == null) {
                binding.setAlbumModel( new AlbumOutput(itemView.getContext(), news));
            } else {
                binding.getAlbumModel().setAlbum(news);
            }
        }
    }

}
