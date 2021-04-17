package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PerusahaanMenuActivity extends AppCompatActivity {
    LinearLayout navHome;
    LinearLayout navAdd;
    LinearLayout navMessage;
    LinearLayout navProfile;
    TextView txtHome;
    TextView txtAdd;
    TextView txtMessage;
    TextView txtProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan_menu);

        navHome = findViewById(R.id.nav_home);
        navAdd = findViewById(R.id.nav_add);
        navMessage = findViewById(R.id.nav_message);
        navProfile = findViewById(R.id.nav_profile);

        txtHome = findViewById(R.id.txthome);
        txtAdd = findViewById(R.id.txtadd);
        txtMessage = findViewById(R.id.txtmessage);
        txtProfile = findViewById(R.id.txtprofile);

        navDefault();
        txtHome.setVisibility(View.VISIBLE);
        loadFragment(new PerusahaanHomeFragment());
    }

    public void keHome(View view) {
        navDefault();
        txtHome.setVisibility(View.VISIBLE);
        Fragment fragment = new PerusahaanHomeFragment();
        loadFragment(fragment);
    }

    public void keAdd(View view) {
        navDefault();
        txtAdd.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(), PerusahaanAddLowonganActivity.class));
        txtAdd.setVisibility(View.GONE);
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
        Fragment fragment = new UserProfileFragment();
        loadFragment(fragment);
    }

    public void navDefault() {
        txtHome.setVisibility(View.GONE);
        txtAdd.setVisibility(View.GONE);
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