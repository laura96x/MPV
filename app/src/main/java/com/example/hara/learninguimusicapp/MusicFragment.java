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
    public ViewPager viewPager;
    ViewPagerAdapter adapter;

    ArrayList<Song> songs;
    public static String songKey = "song";
    public static String stringKey = "string";
    int currentSort = 1; // 0 = DESC, 1 = ASC
    // default = ASC

    SongFragment songFragment;
    ArtistFragment artistFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setHasOptionsMenu(true);
    }
    Menu menu2;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu2 = menu;
        // set visibility of certain options to false
        menu.findItem(R.id.menu_item_switch).setVisible(false);
        menu.findItem(R.id.menu_item_delete).setVisible(false);
        if (viewPager.getCurrentItem() == 0) { // in song tab
            menu.findItem(R.id.menu_item_sort).setVisible(true);
        } else if (viewPager.getCurrentItem() == 1) { // in artist tab
            menu.findItem(R.id.menu_item_sort).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("demo", "MusicFragment clicked " + item.getTitle());
        switch (item.getItemId()) {
            case R.id.menu_item_sort:
                if (item.getTitle().toString().contains("ASC")) {
                    // now sorting by ASC
                    item.setTitle("Sort DESC");
                    currentSort = 1;
                } else {
                    // now sorting by DESC
                    item.setTitle("Sort ASC");
                    currentSort = 0;
                }
                break;
        }
//        resetSongs();

        Log.d("demo", "MusicFragment currentSort " + currentSort);
        return true;
    }
    public ArrayList<Song> sortLists(ArrayList<Song> songs, int sort) {
        if (sort == 1) { // ASC
            Collections.sort(songs, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else { // DESC
            Collections.sort(songs, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });
        }
        return songs;
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

        adapter = new ViewPagerAdapter(getFragmentManager());

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

//        songFragment = SongFragment.newInstance(sortLists(songsToSongs, 0), currentSort);
        songFragment = SongFragment.newInstance(songsToSongs, currentSort);

        Log.d("demo", "music frag, in middle of onCreateView");

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
                onPrepareOptionsMenu(menu2); // show or hide menu options
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        tabLayout.setupWithViewPager(viewPager);
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