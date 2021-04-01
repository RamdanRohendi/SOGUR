package com.example.solusinganggur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class  MenuActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_menu);

        navHome = findViewById(R.id.nav_home);
        navSearch = findViewById(R.id.nav_search);
        navMessage = findViewById(R.id.nav_message);
        navProfile = findViewById(R.id.nav_profile);

        txtHome = findViewById(R.id.txthome);
        txtSearch = findViewById(R.id.txtsearch);
        txtMessage = findViewById(R.id.txtmessage);
        txtProfile = findViewById(R.id.txtprofile);

        navDefault();
        txtHome.setText("Home");
        loadFragment(new HomeFragment());
    }

    public void keNotif(View view) {
        navDefault();
//        btnNotif.setImageResource(R.drawable.notifhitam);
        Fragment fragment = new NotificationsFragment();
        loadFragment(fragment);
    }

    public void keHome(View view) {
        navDefault();
        txtHome.setText("Home");
        Fragment fragment = new HomeFragment();
        loadFragment(fragment);
    }

    public void keSearch(View view) {
        navDefault();
        txtSearch.setText("Search");
        Fragment fragment = new SearchFragment();
        loadFragment(fragment);
    }

    public void keChat(View view) {
        navDefault();
        txtMessage.setText("Message");
        Fragment fragment = new ChatFragment();
        loadFragment(fragment);
    }

    public void keProfile(View view) {
        navDefault();
        txtProfile.setText("Profile");
        Fragment fragment = new ProfileFragment();
        loadFragment(fragment);
    }

    public void navDefault(){
        txtHome.setText("");
        txtSearch.setText("");
        txtMessage.setText("");
        txtProfile.setText("");
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