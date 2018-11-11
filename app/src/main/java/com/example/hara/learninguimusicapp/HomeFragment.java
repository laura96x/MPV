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
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button goToMusic, goToVideo, goToPhotos;

    private onHomeFragment mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        goToMusic = view.findViewById(R.id.buttonToMusic);
        goToVideo = view.findViewById(R.id.buttonToVideo);
        goToPhotos = view.findViewById(R.id.buttonToPhotos);

        goToMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked music");
                mListener.fromHomeToOther(0);
            }
        });
        goToVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked video");
                mListener.fromHomeToOther(1);
            }
        });
        goToPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked photos");
                mListener.fromHomeToOther(2);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_switch);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
//        menuItem.setVisible(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onHomeFragment) {
            mListener = (onHomeFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onHomeFragment{
        void fromHomeToOther(int num);

    }
}