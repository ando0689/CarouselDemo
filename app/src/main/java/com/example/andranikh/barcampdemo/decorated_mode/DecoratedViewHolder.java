package com.example.andranikh.barcampdemo.decorated_mode;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andranikh.barcampdemo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by andranikh on 5/27/17.
 */

public class DecoratedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private DecoratedItem data;

    private View root;
    private ImageView avatarIv;
    private ImageView avatarSelectedIv;
    private TextView distanceTv;

    private OnItemSelectedListener onItemSelectedListener;

    public DecoratedViewHolder(View itemView, OnItemSelectedListener onItemSelectedListener) {
        super(itemView);
        this.onItemSelectedListener = onItemSelectedListener;

        root = itemView.findViewById(R.id.carousel_parent_layout);
        avatarIv = (ImageView) itemView.findViewById(R.id.image_carousel_item);
        avatarSelectedIv = (ImageView) itemView.findViewById(R.id.image_selected);
        distanceTv = (TextView) itemView.findViewById(R.id.text_distance);


        root.setOnClickListener(this);
    }

    public void bind(DecoratedItem data){
        this.data = data;

        distanceTv.setText(data.getDistance());

        Picasso.with(itemView.getContext())
                .load(data.getPicResId())
                .placeholder(R.drawable.placeholder)
                .into(avatarIv);

        if (data.isSelected()) {
            avatarSelectedIv.setVisibility(View.VISIBLE);
        } else {
            avatarSelectedIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.carousel_parent_layout){
            if(onItemSelectedListener != null){
                onItemSelectedListener.onItemSelected(data);
            }
        }
    }

    public interface OnItemSelectedListener{
        void onItemSelected(DecoratedItem item);
    }
}
