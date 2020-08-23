package com.reservation.ui.album;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.reservation.model.AlbumModel;

public class AlbumOutput extends BaseObservable {

    private AlbumModel album;
    private Context context;


    public AlbumOutput(Context context, AlbumModel album)
    {
        this.album = album;
        this.context = context;
    }

    public String getArtistName()
    {
        return this.album.getArtistName();
    }


    public String getTrackName()
    {
        return this.album.getTrackName();
    }

    public String getArtworkUrl()
    {
        return this.album.getArtworkUrl100();
    }
    public String getCollectionName()
    {
        return this.album.getCollectionName();
    }
    public Double getCollectionprice()
    {
        return this.album.getCollectionPrice();
    }
    public String getReleasedate ()
    {
        return this.album.getReleaseDate();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url)
    {
        if (!url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    public void setAlbum(AlbumModel news)
    {
        this.album = news;
        notifyChange();
    }
}
