package com.example.andranikh.barcampdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.andranikh.barcampdemo.decorated_mode.DecoratedModeFragment;
import com.example.andranikh.barcampdemo.simple_mode.SimpleModeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_xray_mode).setOnClickListener(this);
        findViewById(R.id.main_decor_mode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_xray_mode:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, SimpleModeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.main_decor_mode:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, DecoratedModeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!getSupportFragmentManager().popBackStackImmediate()){
            super.onBackPressed();
        }
    }
}
