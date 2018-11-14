package com.example.hara.learninguimusicapp;

import android.content.Context;
import android.net.Uri;
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

    private onMusicFragment mListener;

    private TabLayout tabLayout;
    public static ViewPager viewPager;

    ArrayList<Song> songs;
    public static String songKey = "song";
    public static String stringKey = "string";

    SongFragment songFragment;
    ArtistFragment artistFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

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
                Log.d("demo", "music current " + viewPager.getCurrentItem());
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
        mListener.setFragmentTitle("Music");



        artistFragment = new ArtistFragment();

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

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


        ArrayList<Song> songsToSongs = new ArrayList<>();
        songsToSongs.addAll(songs);
        songFragment = SongFragment.newInstance(songsToSongs);

        ArrayList<Song> songsToArtist = new ArrayList<>();
        songsToArtist.addAll(songs);
        artistFragment = ArtistFragment.newInstance(songsToArtist);


        adapter.addFragment(songFragment, "Song");
        adapter.addFragment(artistFragment, "Artist");

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.d("demo", "2i " + i);
                Bundle bundle;
                switch (i) {
                    case 0:
                        bundle = new Bundle();
                        ArrayList<Song> songs1 = new ArrayList<>();
//        songs1.addAll(songs);
                        songs1 = songs;
                        bundle.putSerializable(songKey, songs1);
                        bundle.putString(stringKey, "song");
                        songFragment.setArguments(bundle);
                        break;
                    case 1:
                        bundle = new Bundle();
                        ArrayList<Song> songs2 = new ArrayList<>();
//        songs2.addAll(songs);
                        songs2 = songs;
                        bundle.putSerializable(songKey, songs2);
                        bundle.putString(stringKey, "artist");
                        artistFragment.setArguments(bundle);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        Log.d("demo", "MusicFragment.onCreateView end");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onMusicFragment) {
            mListener = (onMusicFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onMusicFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onMusicFragment {
        // TODO: Update argument type and name
        void setFragmentTitle(String title);
    }
}