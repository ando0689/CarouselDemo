package com.example.andranikh.barcampdemo.decorated_mode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andranikh.barcampdemo.R;

import java.util.List;

/**
 * Created by andranikh on 5/27/17.
 */

public class DecoratedAdapter extends RecyclerView.Adapter<DecoratedViewHolder>{

    private List<DecoratedItem> data;

    private DecoratedViewHolder.OnItemSelectedListener onItemSelectedListener;

    public DecoratedAdapter(List<DecoratedItem> data, DecoratedViewHolder.OnItemSelectedListener onItemSelectedListener) {
        this.data = data;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public DecoratedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_decorated, parent, false);
        return new DecoratedViewHolder(view, onItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(DecoratedViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
