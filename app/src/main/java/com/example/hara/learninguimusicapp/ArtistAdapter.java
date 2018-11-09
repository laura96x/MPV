package com.example.hara.learninguimusicapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<Song>{

    public ArtistAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
        Log.d("demo", "context " + context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Song currentSong = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.artist = convertView.findViewById(R.id.artistName);
            viewHolder.options = convertView.findViewById(R.id.artistOptions);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.artist.setText(currentSong.getArtist());
        viewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked " + currentSong.getTitle());
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView artist;
        ImageView options;
    }
}
