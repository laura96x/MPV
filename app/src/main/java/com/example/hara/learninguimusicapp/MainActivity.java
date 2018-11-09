package com.example.hara.learninguimusicapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.onHomeFragment, PhotosFragment.onPhotoFragment{
    private DrawerLayout drawer;
    NavigationView navigationView;

    HomeFragment homeFragment;
    MusicFragment musicFragment;
    VideoFragment videoFragment;
    PhotosFragment photosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        musicFragment = new MusicFragment();
        videoFragment = new VideoFragment();
        photosFragment = new PhotosFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Log.d("demo", "onBackPressed");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Log.d("demo", "getSupportFragmentManager().getBackStackEntryCount() " + getSupportFragmentManager().getBackStackEntryCount());
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
//                Toast.makeText(this, "Home is in your heart, fag.", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_music:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, musicFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_videos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, videoFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_photos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, photosFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            default:

                navigationView.setCheckedItem(menuItem.getItemId());
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void fromHomeToOther(int num) {
        Log.d("demo", "in main fromHomeToOther" + num);
        switch (num) {
            case 0: // music
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, musicFragment).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_music);
                break;
            case 1: // video
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, videoFragment).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_videos);
                break;
            case 2: // photos
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, photosFragment).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_photos);
                break;
            default:
                break;
        }
    }

    @Override
    public void fromAlbumToPictures(String title) {
        Log.d("demo", "in main fromAlbumToPictures " + title);
    }
}
