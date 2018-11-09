package com.example.hara.learninguimusicapp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Song implements Serializable{
    private long id;
    private String name,
            artist, album, genre;

    public Song(long id, String name, String artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.album = null;
        this.genre = null;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }


    public int compareTitle(Song a) {
        return this.getTitle().compareTo(a.getTitle());
    }
    public int compareArtist(Song a) {
        return this.getArtist().compareTo(a.getArtist());
    }
    public int compareAlbum(Song a) {
        return this.getAlbum().compareTo(a.getAlbum());
    }
    public int compareGenre(Song a) {
        return this.getGenre().compareTo(a.getGenre());
    }
}


