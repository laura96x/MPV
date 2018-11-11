package com.example.hara.learninguimusicapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.onHomeFragment,
        PhotosFragment.onPhotoFragment,
        View.OnClickListener,
        AlbumPicturesFragment.onAlbumPicturesFragment {

    private DrawerLayout drawer;
    NavigationView navigationView;

    HomeFragment homeFragment;
    MusicFragment musicFragment;
    VideoFragment videoFragment;
    PhotosFragment photosFragment;
    AlbumPicturesFragment albumPicturesFragment;

    int container = R.id.fragment_container;
    public static String albumNameKey = "album name";
    public static String albumListKey = "album list";
    public static String galleryPathKey = "path";

    static final int REQUEST_PERMISSION_KEY = 1;
    LoadAlbum loadAlbumTask;
    GridView galleryGridView;
    ArrayList<HashMap<String, String>> albumList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        musicFragment = new MusicFragment();
        videoFragment = new VideoFragment();
        photosFragment = new PhotosFragment();
        albumPicturesFragment = new AlbumPicturesFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(container, homeFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
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
                getSupportFragmentManager().beginTransaction().replace(container, homeFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_music:
                getSupportFragmentManager().beginTransaction().replace(container, musicFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_videos:
                getSupportFragmentManager().beginTransaction().replace(container, videoFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_photos:
                getSupportFragmentManager().beginTransaction().replace(container, photosFragment).addToBackStack(null).commit();
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
                getSupportFragmentManager().beginTransaction().replace(container, new MusicFragment()).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_music);
                break;
            case 1: // video
                getSupportFragmentManager().beginTransaction().replace(container, videoFragment).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_videos);
                break;
            case 2: // photos
                getSupportFragmentManager().beginTransaction().replace(container, photosFragment).addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_photos);
                Bundle bundle = new Bundle();
                bundle.putSerializable(albumListKey, albumList);
                photosFragment.setArguments(bundle);
                break;
            default:
                break;
        }
    }

    @Override
    public void fromAlbumToPictures(String title) {
        Log.d("demo", "in main fromAlbumToPictures " + title);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, albumPicturesFragment)
                .addToBackStack(null)
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString(albumNameKey, title);
        albumPicturesFragment.setArguments(bundle);
    }

    @Override
    public void onClick(View v) {
        Log.d("demo", "in main onClick");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, homeFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void fromPictureToGallery(String path) {
        Intent intent = new Intent(MainActivity.this, GalleryPreview.class);
        intent.putExtra(galleryPathKey, path);
        startActivity(intent);
    }

    class LoadAlbum extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumList.clear();
        }

        protected String doInBackground(String... args) {
            String xml = "";

            String path = null;
            String album = null;
            String timestamp = null;
            String countPhoto = null;
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;


            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.MediaColumns.DATE_MODIFIED };
            Cursor cursorExternal = getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                    null, null);
            Cursor cursorInternal = getContentResolver().
                    query(uriInternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                            null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});

            while (cursor.moveToNext()) {

                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
                countPhoto = Function.getCount(getApplicationContext(), album);

                albumList.add(Function.mappingInbox(album, path, timestamp, Function.convertToTime(timestamp), countPhoto));
            }
            cursor.close();
            Collections.sort(albumList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
            return xml;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("demo", "onRequestPermissionsResult Yes");
                    loadAlbumTask = new LoadAlbum();
                    loadAlbumTask.execute();
                } else
                {
                    Log.d("demo", "Noooo");
                    Toast.makeText(MainActivity.this, "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }else{
            Log.d("demo", "onResume Yes");
            loadAlbumTask = new LoadAlbum();
            loadAlbumTask.execute();
        }
    }


}
