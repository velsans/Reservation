package com.reservation.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.reservation.model.AlbumModel;
import com.reservation.ui.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private MutableLiveData<List<AlbumModel>> transactionListData=new MutableLiveData<>();
    ArrayList<AlbumModel> list = new ArrayList<>();

    public MutableLiveData<List<AlbumModel>> getSelectedAlbums() {
        if(Utils.SelectedCartItem.size()>0) {
            transactionListData.setValue(Utils.SelectedCartItem);
        }
        return transactionListData;
    }
}