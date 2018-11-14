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
    private static final String ARG_PARAM1 = "param1";

    public static SongFragment newInstance(ArrayList<Song> songs) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, songs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songs = (ArrayList<Song>) getArguments().getSerializable(ARG_PARAM1);
        } else {
            songs = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        Log.d("demo", "SongFragment.onCreateView");

        ListView listView = view.findViewById(R.id.songListview);

        Log.d("demo", "a " + songs.toString());

        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Log.d("demo", "a2 " + songs.toString());

        songAdapter = new SongAdapter(getContext(), R.layout.song_list_item, songs);
        listView.setAdapter(songAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
