package com.example.andranikh.barcampdemo.simple_mode;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andranikh.barcampdemo.R;

/**
 * Created by andranikh on 5/27/17.
 */

public class SimpleViewHolder extends RecyclerView.ViewHolder{

    private TextView itemTv;

    public SimpleViewHolder(View itemView) {
        super(itemView);

        itemTv = (TextView) itemView.findViewById(R.id.item_simple_tv);
    }

    public void bind(SimpleItem data) {
        itemTv.setText(String.valueOf(data.getIndex()));
    }
}
