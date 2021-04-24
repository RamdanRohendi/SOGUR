package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.solusinganggur.adapter.IntroSlidePageAdapter;

import java.util.ArrayList;
import java.util.List;

public class SlideIntroActivity extends AppCompatActivity {
    Button signIn;
    Button signUp;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_intro);
        List<Fragment> list = new ArrayList<>();
        list.add(new IntroSatuFragment());
        list.add(new IntroDuaFragment());

        pager = findViewById(R.id.pager);
        pagerAdapter = new IntroSlidePageAdapter(getSupportFragmentManager(), list);

        pager.setAdapter(pagerAdapter);
    }
}
