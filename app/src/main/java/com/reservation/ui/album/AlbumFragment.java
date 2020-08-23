package com.reservation.ui.album;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.reservation.model.AlbumModel;
import com.reservation.ui.Utils;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment implements LifecycleOwner {
    View album_rootview;
    private AlbumViewModel dashboardViewModel;
    TextView album_empty;
    SwipeRefreshLayout refreshLay;
    FragmentAlbumBinding fragmentAlbumBinding;
    RecyclerView AlbumsList;
    AlbumsAdapter albumsadapter;
    List<AlbumModel> albumArrayLists = new ArrayList<>();
    LinearLayoutManager horizontalLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentAlbumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);
        //viewModel = new TransactionViewModel(getContext());
        fragmentAlbumBinding.setLifecycleOwner(this);
        album_rootview = fragmentAlbumBinding.getRoot();
        AlbumsList = fragmentAlbumBinding.albumList;
        refresh();
        intialization();
        refreshLay.setOnRefreshListener(() -> {
            refreshLay.setRefreshing(true);
            (new Handler()).postDelayed(() -> {
                refreshLay.setRefreshing(false);
                refresh();
            }, 1000);
        });
        fragmentAlbumBinding.search.setActivated(true);
        fragmentAlbumBinding.search.setQueryHint(CommonMessage(R.string.Type_your_keyword_here));
        fragmentAlbumBinding.search.onActionViewExpanded();
        fragmentAlbumBinding.search.setIconified(false);
        fragmentAlbumBinding.search.clearFocus();

        fragmentAlbumBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                albumsadapter.getFilter().filter(newText);
                return false;
            }
        });

        return album_rootview;
    }

    public void refresh() {
        dashboardViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        dashboardViewModel.getAlbums().observe(getActivity(), albumListUpdateObserver);
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
                album_empty.setVisibility(View.GONE);
                /*Onclick Item listener*/
                albumsadapter.setOnItemClickListener(onItemClickListener);
            } else {
                album_empty.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener onItemClickListener = view -> {
        try {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            AlbumModel thisItem = albumArrayLists.get(position);
            boolean duplicates = Utils.SelectedCartItem.contains(thisItem);
            if (duplicates) {
                Toast.makeText(getActivity(), "Already added", Toast.LENGTH_SHORT).show();
            } else {
                Utils.SelectedCartItem.add(thisItem);
                Toast.makeText(getActivity(), "Album Added Sucessfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {

        }
    };
}