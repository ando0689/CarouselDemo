package com.example.andranikh.barcampdemo.decorated_mode;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andranikh.barcampdemo.DataProvider;
import com.example.andranikh.barcampdemo.R;

import am.andranik.semicirclelayoutmanger.SemiCircularLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DecoratedModeFragment extends Fragment implements DecoratedViewHolder.OnItemSelectedListener{

    private RecyclerView recyclerView;

    private DecoratedAdapter adapter;
    private SemiCircularLayoutManager layoutManager;

    public static DecoratedModeFragment newInstance() {
        DecoratedModeFragment fragment = new DecoratedModeFragment();
        return fragment;
    }

    public DecoratedModeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decorated_mode, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.carousel_rv);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DecoratedAdapter(DataProvider.getDecoratedItems(), this);
        layoutManager = new SemiCircularLayoutManager(recyclerView, true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(DecoratedItem item) {
        item.setSelected(!item.isSelected());
        adapter.notifyDataSetChanged();
    }
}
