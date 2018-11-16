package com.example.hara.learninguimusicapp.Music;

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

import com.example.hara.learninguimusicapp.MainActivity;
import com.example.hara.learninguimusicapp.R;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    MainActivity thing;

    public SongAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
        thing = (MainActivity) context;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Song currentSong = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.song_title);
            viewHolder.artist = convertView.findViewById(R.id.song_artist);
            viewHolder.options = convertView.findViewById(R.id.songOptions);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(currentSong.getTitle());
        viewHolder.artist.setText(currentSong.getArtist());
        viewHolder.options.setImageResource(0);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked " + currentSong.getTitle());
                thing.playSong(position);

            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("demo", "long clicked " + currentSong.getTitle());
                return false;
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView title, artist;
        ImageView options;
    }
}
