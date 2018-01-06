package com.example.andranikh.barcampdemo.simple_mode;


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
public class SimpleModeFragment extends Fragment {

    private RecyclerView recyclerView;

    private SimpleAdapter adapter;
    private SemiCircularLayoutManager layoutManager;

    public static SimpleModeFragment newInstance() {
        SimpleModeFragment fragment = new SimpleModeFragment();
        return fragment;
    }

    public SimpleModeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_mode, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.carousel_rv);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new SimpleAdapter(DataProvider.getSimpleItems());
        layoutManager = new SemiCircularLayoutManager(recyclerView, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
