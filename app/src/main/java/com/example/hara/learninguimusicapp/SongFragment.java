package com.example.hara.learninguimusicapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongFragment extends Fragment {

    public SongFragment() {
        // Required empty public constructor
    }

    ArrayList<Song> songs;
    SongAdapter songAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        Log.d("demo", "SongFragment.onCreateView");

        if (getArguments() != null) {
            Log.d("demo", "quackkk " + getArguments().getString(MusicFragment.stringKey));

            if (getArguments().getSerializable(MusicFragment.songKey) != null) {
                songs = (ArrayList<Song>) getArguments().getSerializable(MusicFragment.songKey);
                Log.d("demo", "a" + songs.toString());
                ListView listView = view.findViewById(R.id.songListview);

                Collections.sort(songs, new Comparator<Song>() {
                    @Override
                    public int compare(Song o1, Song o2) {
                        return o1.compareTitle(o2);
                    }
                });
                Log.d("demo", "a2" + songs.toString());
                songAdapter = new SongAdapter(getContext(), R.layout.song_list_item, songs);
                listView.setAdapter(songAdapter);

            }
        }
        
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo", "SongFragment.onCreate");
    }

    public void testMethod() {
        Log.d("demo", "testMethod");
    }
}
