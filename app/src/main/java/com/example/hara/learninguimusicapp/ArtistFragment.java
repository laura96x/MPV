package com.example.hara.learninguimusicapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
    }

    ArrayList<Song> songs;
    ArtistAdapter artistAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        Log.d("demo", "ArtistFragment.onCreateView");
        if (getArguments() != null) {
            Log.d("demo", "quackkk zzzz " + getArguments().getString(MusicFragment.stringKey));

            if (getArguments().getSerializable(MusicFragment.songKey) != null) {
                songs = (ArrayList<Song>) getArguments().getSerializable(MusicFragment.songKey);
                Log.d("demo", "b" + songs.toString());
                ListView listView = view.findViewById(R.id.artistListview);

                Collections.sort(songs, new Comparator<Song>() {
                    @Override
                    public int compare(Song o1, Song o2) {
                        return o1.compareArtist(o2);
                    }
                });
                Log.d("demo", "b2" + songs.toString());
                artistAdapter = new ArtistAdapter(getContext(), R.layout.artist_list_item, songs);
                listView.setAdapter(artistAdapter);

            }
        }
        return view;
    }

}
