package com.example.hara.learninguimusicapp.Music;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Song implements Serializable{
    private long id;
    private double duration;
    private int min, sec;
    private String name,
            artist, album, albumImage;

    public Song(long id, double duration, String name, String artist, String album) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.artist = artist;
        this.album = album;
        albumImage = null;
        calculateMinSec();
    }
    public Song(long id, double duration, String name, String artist, String album, String albumImage) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.albumImage = albumImage;
        calculateMinSec();
    }

    public Song(String name, String artist) {
        id = 0;
        duration = 0;
        this.name = name;
        this.artist = artist;
        this.album = null;
        min = 0;
        sec = 0;
        albumImage = null;

    }

    public void calculateMinSec() {
        min = (int) Math.floor(duration / 60);
        sec = (int) Math.round(duration % 60);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

//    @Override
//    public String toString() {
//        return "Song{" +
//                "name='" + name + '\'' +
//                ", artist='" + artist + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", duration=" + duration +
                ", min=" + min +
                ", sec=" + sec +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", albumImage='" + albumImage + '\'' +
                '}';
    }
}


