package com.reservation.ui.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.reservation.R;
import com.reservation.databinding.AlbumRowBinding;
import com.reservation.model.AlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder> implements Filterable {

        Context mCtx;
        List<AlbumModel> AlbumList;
        List<AlbumModel> AlbumFilterList;
private View.OnClickListener mOnItemClickListener;
        ValueFilter valueFilter;

public AlbumsAdapter(Context mCtx, List<AlbumModel> AlbumList) {
        this.mCtx = mCtx;
        this.AlbumList = AlbumList;
        this.AlbumFilterList = AlbumList;
        }

@NonNull
@Override
public AlbumsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.album_row, parent, false);
        return new AlbumsViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull AlbumsViewHolder holder, int position) {
        AlbumModel albumpojo = AlbumFilterList.get(position);
        //Glide.with(mCtx).load(hero.getImageurl()).into(holder.imageView);
        holder.bind(albumpojo);

        }

public void setAlbumDetials(List<AlbumModel> AlbumListsa) {
        this.AlbumFilterList = AlbumListsa;
        notifyDataSetChanged();
        }

@Override
public int getItemCount() {
        if (AlbumFilterList == null) {
        return 0;
        }
        return AlbumFilterList.size();
        }

public class AlbumsViewHolder extends RecyclerView.ViewHolder {
    private AlbumRowBinding binding;

    public AlbumsViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        itemView.setTag(this);
        /*Onclick Item listener*/
        itemView.setOnClickListener(mOnItemClickListener);
    }

    public void bind(AlbumModel item) {
        binding.setAlbumModel(new AlbumOutput(mCtx,item));
    }
}

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

private class ValueFilter extends Filter {
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
            AlbumFilterList = AlbumList;
        } else {
            List<AlbumModel> filteredList = new ArrayList<>();
            for (AlbumModel row : AlbumFilterList) {
                if (row.getArtistName().toLowerCase().contains(charString.toLowerCase()) || row.getTrackName().contains(charSequence)) {
                    filteredList.add(row);
                }
            }
            AlbumFilterList = filteredList;
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values = AlbumFilterList;
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint,
                                  FilterResults results) {
        AlbumFilterList = (ArrayList<AlbumModel>) results.values;
        notifyDataSetChanged();
    }
}
}
