package com.reservation.ui.cart;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.reservation.R;
import com.reservation.databinding.FragmentAlbumBinding;
import com.reservation.databinding.FragmentCartBinding;
import com.reservation.model.AlbumModel;
import com.reservation.ui.Utils;
import com.reservation.ui.album.AlbumViewModel;
import com.reservation.ui.album.AlbumsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements LifecycleOwner {
    View album_rootview;
    private CartViewModel dashboardViewModel;
    TextView album_empty;
    SwipeRefreshLayout refreshLay;
    FragmentCartBinding fragmentCartBinding;
    RecyclerView AlbumsList;
    AlbumsAdapter albumsadapter;
    List<AlbumModel> albumArrayLists = new ArrayList<>();
    LinearLayoutManager horizontalLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        //viewModel = new TransactionViewModel(getContext());
        fragmentCartBinding.setLifecycleOwner(this);
        album_rootview = fragmentCartBinding.getRoot();
        AlbumsList = fragmentCartBinding.albumList;
        refresh();
        intialization();
        refreshLay.setOnRefreshListener(() -> {
            refreshLay.setRefreshing(true);
            (new Handler()).postDelayed(() -> {
                refreshLay.setRefreshing(false);
                refresh();
            }, 1000);
        });
        return album_rootview;
    }

    public void refresh() {
        dashboardViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        dashboardViewModel.getSelectedAlbums().observe(getActivity(), albumListUpdateObserver);
    }

    private void intialization() {
        album_empty = album_rootview.findViewById(R.id.album_empty);
        refreshLay = album_rootview.findViewById(R.id.activity_main_swipe_refresh_layout);
        refreshLay.setColorScheme(R.color.red, R.color.green, R.color.blue);
    }

    public String CommonMessage(int Common_Msg) {
        return getActivity().getResources().getString(Common_Msg);
    }

    Observer<List<AlbumModel>> albumListUpdateObserver = new Observer<List<AlbumModel>>() {
        @Override
        public void onChanged(List<AlbumModel> albumArrayList) {
            albumArrayLists = albumArrayList;
            albumsadapter = new AlbumsAdapter(getActivity(), albumArrayList);
            horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            AlbumsList.setLayoutManager(horizontalLayoutManager);
            albumsadapter.notifyDataSetChanged();
            AlbumsList.setAdapter(albumsadapter);

            if (albumArrayList.size() > 0) {
                //album_empty.setVisibility(View.GONE);
                /*Onclick Item listener*/
                albumsadapter.setOnItemClickListener(onItemClickListener);
            } else {
                //album_empty.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener onItemClickListener = view -> {
        try {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            AlbumModel thisItem = albumArrayLists.get(position);
            Utils.SelectedCartItem.remove(thisItem);
            Toast.makeText(getActivity(), "Removed Sucessfully", Toast.LENGTH_SHORT).show();
            albumsadapter.notifyDataSetChanged();
        } catch (Exception ex) {

        }
    };
}