package com.example.chenminyao.hootsuiteassignment.View;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chenminyao.hootsuiteassignment.BR;
import com.example.chenminyao.hootsuiteassignment.R;
import com.example.chenminyao.hootsuiteassignment.ViewModel.MainActivityViewModel;
import com.example.chenminyao.hootsuiteassignment.databinding.ContentMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentMainBinding binding = DataBindingUtil.setContentView(this, R.layout.content_main);
        binding.setVariable(BR.mainActivityVM, new MainActivityViewModel(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
