package com.reservation.data.api;

import com.reservation.model.AlbumResponceModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/search?term=all")
    Call<AlbumResponceModel> GetAlbumInformation();
}
