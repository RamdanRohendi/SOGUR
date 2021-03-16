package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DashboardActivity extends AppCompatActivity {
    ImageView btnNotif;
    ImageView btnHome;
    ImageView btnSearch;
    ImageView btnChat;
    ImageView btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnNotif = findViewById(R.id.notif);
        btnHome = findViewById(R.id.home);
        btnSearch = findViewById(R.id.search);
        btnChat = findViewById(R.id.chat);
        btnProfile = findViewById(R.id.akun);

        btnHome.setImageResource(R.drawable.homehitam);
        loadFragment(new HomeFragment());
    }

    public void keNotif(View view) {
        gbrDefault();
        btnNotif.setImageResource(R.drawable.notifhitam);
        Fragment fragment = new NotificationsFragment();
        loadFragment(fragment);
    }

    public void keHome(View view) {
        gbrDefault();
        btnHome.setImageResource(R.drawable.homehitam);
        Fragment fragment = new HomeFragment();
        loadFragment(fragment);
    }

    public void keSearch(View view) {
        gbrDefault();
        btnSearch.setImageResource(R.drawable.searchhitam);
        Fragment fragment = new SearchFragment();
        loadFragment(fragment);
    }

    public void keChat(View view) {
        gbrDefault();
        btnChat.setImageResource(R.drawable.chathitam);
        Fragment fragment = new ChatFragment();
        loadFragment(fragment);
    }

    public void keProfile(View view) {
        gbrDefault();
        btnProfile.setImageResource(R.drawable.accounthitam);
        Fragment fragment = new ProfileFragment();
        loadFragment(fragment);
    }

    public void gbrDefault(){
        btnNotif.setImageResource(R.drawable.notif);
        btnHome.setImageResource(R.drawable.homeputih);
        btnSearch.setImageResource(R.drawable.searchputih);
        btnChat.setImageResource(R.drawable.chatputih);
        btnProfile.setImageResource(R.drawable.accountputih);
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}