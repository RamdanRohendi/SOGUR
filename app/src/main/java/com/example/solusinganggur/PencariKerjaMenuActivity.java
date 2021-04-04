package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PencariKerjaMenuActivity extends AppCompatActivity {
    LinearLayout navHome;
    LinearLayout navSearch;
    LinearLayout navMessage;
    LinearLayout navProfile;
    TextView txtHome;
    TextView txtSearch;
    TextView txtMessage;
    TextView txtProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_menu);

        navHome = findViewById(R.id.nav_home);
        navSearch = findViewById(R.id.nav_search);
        navMessage = findViewById(R.id.nav_message);
        navProfile = findViewById(R.id.nav_profile);

        txtHome = findViewById(R.id.txthome);
        txtSearch = findViewById(R.id.txtsearch);
        txtMessage = findViewById(R.id.txtmessage);
        txtProfile = findViewById(R.id.txtprofile);

        navDefault();
        txtHome.setVisibility(View.VISIBLE);
        loadFragment(new PencariKerjaHomeFragment());
    }

    public void keHome(View view) {
        navDefault();
        txtHome.setVisibility(View.VISIBLE);
        Fragment fragment = new PencariKerjaHomeFragment();
        loadFragment(fragment);
    }

    public void keSearch(View view) {
        navDefault();
        txtSearch.setVisibility(View.VISIBLE);
        Fragment fragment = new PencariKerjaSearchFragment();
        loadFragment(fragment);
    }

    public void keChat(View view) {
        navDefault();
        txtMessage.setVisibility(View.VISIBLE);
        Fragment fragment = new UserMessageFragment();
        loadFragment(fragment);
    }

    public void keProfile(View view) {
        navDefault();
        txtProfile.setVisibility(View.VISIBLE);
        Fragment fragment = new PencariKerjaProfileFragment();
        loadFragment(fragment);
    }

    public void navDefault() {
        txtHome.setVisibility(View.GONE);
        txtSearch.setVisibility(View.GONE);
        txtMessage.setVisibility(View.GONE);
        txtProfile.setVisibility(View.GONE);
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