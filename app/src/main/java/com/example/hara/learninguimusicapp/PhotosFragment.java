package com.example.hara.learninguimusicapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {

    ArrayList<String> albums;
    ArrayAdapter<String> albumListAdapter;

    private onPhotoFragment mListener;

    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        getActivity().setTitle("Photos");
        ListView listView = view.findViewById(R.id.photoAlbumListView);
        albums = new ArrayList<>();
        albums.add("Camera");
        albums.add("Screenshots");
        albums.add("Beach");
        albums.add("Trip 2015");
        albums.add("School 2017");
        albums.add("Hidden");
        albumListAdapter = new ArrayAdapter<>(getContext(), R.layout.photo_album_list_item, R.id.textPhotoAlbum, albums);
        listView.setAdapter(albumListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", "clicked " + albums.get(position));
                mListener.fromAlbumToPictures(albums.get(position));
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_item_sort).setVisible(false);
        menu.findItem(R.id.menu_item_switch).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("demo", "MusicFragment clicked " + item.getTitle());
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onPhotoFragment) {
            mListener = (onPhotoFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onPhotoFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onPhotoFragment {
        void fromAlbumToPictures(String title);
    }
}
