package com.geochat;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.BottomNotifications:
                    setTitle("Fragment Notifications");
                    NotificationFragment notifications = new NotificationFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransactionNotifications = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionNotifications.replace(R.id.frame,notifications,"Fragment Notifications");
                    fragmentTransactionNotifications.commit();
                    return true;
                case R.id.BottomProfile:
                    setTitle("Fragment Profile");
                    ProfileFragment profile = new ProfileFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransactionProfile = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionProfile.replace(R.id.frame,profile,"Fragment Profile");
                    fragmentTransactionProfile.commit();
                    return true;
                case R.id.BottomNearby:
                    setTitle("Fragment Nearby");
                    NearbyFragment nearby = new NearbyFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransactionNearby = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionNearby.replace(R.id.frame,nearby,"Fragment Nearby");
                    fragmentTransactionNearby.commit();
                    return true;
                case R.id.BottomMessages:
                    setTitle("Fragment Messages");
                    MessagesFragment messages = new MessagesFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransactionMessages = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionMessages.replace(R.id.frame,messages,"Fragment Messages");
                    fragmentTransactionMessages.commit();
                    return true;
                case R.id.BottomSettings:
                    setTitle("Fragment Settings");
                    SettingsFragment settings = new SettingsFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransactionSettings = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionSettings.replace(R.id.frame,settings,"Fragment Settings");
                    fragmentTransactionSettings.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.BottomNearby);
    }

}
