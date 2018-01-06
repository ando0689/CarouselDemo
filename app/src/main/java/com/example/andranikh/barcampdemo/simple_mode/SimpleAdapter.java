package com.example.andranikh.barcampdemo.simple_mode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andranikh.barcampdemo.R;

import java.util.List;

/**
 * Created by andranikh on 5/27/17.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleViewHolder>{

    private List<SimpleItem> data;

    public SimpleAdapter(List<SimpleItem> data) {
        this.data = data;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
