package com.example.hara.learninguimusicapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {


    //Handler handler;


    private VideoView video;
    private MediaController ctlr;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.videoplayer);
        String value = null;
        Log.d("demo", "Created ");
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("KEY");
        }
        Log.d("demo", "We should have the key ");
        Log.d("demo", value);




        Cursor cursor = null;
        if(value!=null) {

            cursor = getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null,
                    MediaStore.Video.Media.TITLE + " LIKE ?",
                    new String[]{"%" + value + "%"},
                    MediaStore.Video.Media.TITLE + " ASC");

        }
        String path = null;
        Log.d("demo", "Did it break now ");

        cursor.moveToNext();
        Log.d("demo", "Or now ");
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        Log.d("demo", path);
    /*
        Uri uri= null;

        uri= ContentUris .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)));



        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
*/
        Uri uri = Uri.parse(path);

        Log.d("demo", "URI WORKS ");

        video=(VideoView)findViewById(R.id.videoView);
        video.setVideoURI(uri);
        ctlr=new MediaController(this);
        ctlr.setMediaPlayer(video);
        video.setMediaController(ctlr);


        //video.requestFocus();




        //video.setZOrderOnTop(true);
        //video.setZOrderOnTop(false);

        video.bringToFront();

        video.start();



    }



}
