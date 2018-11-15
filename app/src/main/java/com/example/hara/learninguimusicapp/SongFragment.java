package com.example.hara.learninguimusicapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private static final String ARG_PARAM2 = "param2";

    public static SongFragment newInstance(ArrayList<Song> songs, int sort) {
        Log.d("demo", "SongFragment.newInstance");
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, songs);
        args.putInt(ARG_PARAM2, sort); // 0 = DESC, 1 = ASC
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("demo", "SongFragment.onCreate");
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

//        Log.d("demo", "a " + songs.toString());

        assert getArguments() != null;
        if (getArguments().getInt(ARG_PARAM2) == 1) { // ASC
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

//        Log.d("demo", "a2 " + songs.toString());

        songAdapter = new SongAdapter(getContext(), R.layout.song_list_item, songs);
        listView.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.song_options, menu);
        Log.d("demo", "v id " + v.getId());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.song_options_delete:
                Log.d("demo", "clicked " + item.getTitle());
                return true;
            case R.id.song_options_add_to_playlist:
                Log.d("demo", "clicked " + item.getTitle());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
