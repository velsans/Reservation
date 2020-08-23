package com.reservation.ui.album;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.reservation.data.api.ApiClient;
import com.reservation.data.api.ApiInterface;
import com.reservation.model.AlbumModel;
import com.reservation.model.AlbumResponceModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<List<AlbumModel>> transactionListData;
    ArrayList<AlbumModel> list = new ArrayList<>();
    ApiInterface ClientInfoApi;

    public AlbumViewModel() {
        ClientInfoApi = ApiClient.getInstance().getUserService();
        transactionListData = new MutableLiveData<>();
    }

    public MutableLiveData<List<AlbumModel>> getAlbums() {
        try {
            ClientInfoApi = ApiClient.getApiInterface();
            ClientInfoApi.GetAlbumInformation().enqueue(new Callback<AlbumResponceModel>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<AlbumResponceModel> call, Response<AlbumResponceModel> response) {
                    if (response.body() != null) {
                        if (response.body().getResults() != null) {
                            list.addAll(response.body().getResults());
                            // Sorting the list
                            Collections.sort(list);
                            transactionListData.setValue(response.body().getResults());
                        }
                    }
                }
                @Override
                public void onFailure(Call<AlbumResponceModel> call, Throwable t) {
                    Log.e("getMessage", ">>>>>>>" + t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("getMessage", ">>>>>>>" + ex.toString());
        }
        return transactionListData;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.equals("null");
    }

}