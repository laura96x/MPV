package com.example.hara.learninguimusicapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ArrayList<Song> songs;
    public static String songKey = "song";
    public static String stringKey = "string";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle("Music");
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_switch);
        menuItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("demo", "MusicFragment clicked " + item.getTitle());
        switch (item.getItemId()) {
            case R.id.menu_item_sort:
                if (item.getTitle().toString().contains("ASC")) {
                    item.setTitle("Sort DESC");
                } else {
                    item.setTitle("Sort ASC");
                }
                break;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        tabLayout = view.findViewById(R.id.music_tab);
        viewPager = view.findViewById(R.id.viewpager_id);
        Log.d("demo", "MusicFragment.onCreateView");
        getActivity().setTitle("Music");


        SongFragment songFragment = new SongFragment();
        ArtistFragment artistFragment = new ArtistFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        songs = new ArrayList<>();

        songs.add(new Song("song 1", "artist F"));
        songs.add(new Song("song 2", "artist T"));
        songs.add(new Song("song 3", "artist I"));
        songs.add(new Song("fsong 4", "artist E"));
        songs.add(new Song("song 5", "artist Q"));
        songs.add(new Song("song 6", "artist A"));
        songs.add(new Song("bsong 7", "artist Z"));
        songs.add(new Song("song 8", "artist C"));
        songs.add(new Song("song 9", "artist M"));

        Bundle bundle = new Bundle();
        ArrayList<Song> songs1 = songs;
        bundle.putSerializable(songKey, songs1);
        bundle.putString(stringKey, "song");
        songFragment.setArguments(bundle);

        bundle = new Bundle();
        ArrayList<Song> songs2 = songs;
        bundle.putSerializable(songKey, songs2);
        bundle.putString(stringKey, "artist");
        artistFragment.setArguments(bundle);

        adapter.addFragment(songFragment, "Song");
        adapter.addFragment(artistFragment, "Artist");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Log.d("demo", "MusicFragment.onCreateView end");
        return view;
    }
}