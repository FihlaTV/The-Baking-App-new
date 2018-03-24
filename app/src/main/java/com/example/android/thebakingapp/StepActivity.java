package com.example.android.thebakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
    String LOG_TAG;
    @BindView(R.id.step_video)
    SimpleExoPlayerView videoView;

    SimpleExoPlayer player;

    @BindView(R.id.step_thumb)
    ImageView stepThumb;

    @BindView(R.id.step_desc_long)
    TextView step_desc;

    @BindView(R.id.prev_button)
    Button prev_button;

    @BindView(R.id.next_button)
    Button next_button;

//    @BindView(R.id.scroll)
//    ScrollView scroll;

    ArrayList<Integer> allIDs;
    ArrayList<String> allDesc;
    ArrayList<String> allVid;
    ArrayList<String> allThumb;
    int current;
    int currentWindow;
    long playbackPosition;
    Boolean playWhenReady = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        if(savedInstanceState!=null){
            currentWindow = savedInstanceState.getInt("EXO_WIN");
            playbackPosition = savedInstanceState.getLong("EXO_POS");
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        try {
            current = extras.getInt("THIS_ID");
            allIDs = extras.getIntegerArrayList("ALL_IDS");
            allDesc = extras.getStringArrayList("ALL_DESC");
            allVid = extras.getStringArrayList("ALL_VID");
            allThumb = extras.getStringArrayList("ALL_THUMB");

            step_desc.setText(allDesc.get(current));

            if(current==(allIDs.size()-1)){
                next_button.setVisibility(View.GONE);
            }

            if(current==0){
                prev_button.setVisibility(View.GONE);
            }


            if(!allVid.get(current).equals("")){
                initializePlayer();
                stepThumb.setVisibility(View.GONE);
            }

            else if(!allThumb.get(current).equals("")){
                Picasso.with(this).load(allThumb.get(current)).into(stepThumb);
                videoView.setVisibility(View.GONE);
            }

            else{
                videoView.setVisibility(View.GONE);
            }


        }
        catch(NullPointerException e)
        {
            Log.e(LOG_TAG,"Null pointer in intent");
            e.printStackTrace();
        }

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player!=null){
                    player.stop();
                }
                setViews(false);

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player!=null){
                    player.stop();
                }
                setViews(true);
            }
        });
    }

    private void initializePlayer() {
        if(player!=null){
            player.stop();
            player = null;
        }

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            videoView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(allVid.get(current));
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }

    private void setViews(Boolean next) {
        next_button.setVisibility(View.VISIBLE);
        prev_button.setVisibility(View.VISIBLE);

        if(next)
        current++;

        if(!next)
        current--;

        if(current==(allIDs.size()-1)){
            next_button.setVisibility(View.GONE);
        }

        if(current==0){
            prev_button.setVisibility(View.GONE);
        }

        step_desc.setText(allDesc.get(current));

        if (!allVid.get(current).equals("")) {
            initializePlayer();
            stepThumb.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
        }

        else if (!allThumb.get(current).equals("")) {
            stepThumb.setVisibility(View.VISIBLE);
            Picasso.with(this).load(allThumb.get(current)).into(stepThumb);
            videoView.setVisibility(View.GONE);
        }
        else {
            videoView.setVisibility(View.GONE);
            stepThumb.setVisibility(View.VISIBLE);
        }

    }

    private void saveState() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveState();
        outState.putInt("EXO_WIN", currentWindow);
        outState.putLong("EXO_POS", playbackPosition);
        outState.putBoolean("EXO_PLAY", playWhenReady);
        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentWindow = savedInstanceState.getInt("EXO_WIN");
        playbackPosition = savedInstanceState.getLong("EXO_POS");
        playWhenReady = savedInstanceState.getBoolean("EXO_PLAY");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            currentWindow = extras.getInt("EXO_WIN");
            playbackPosition = extras.getLong("EXO_POS");
        }
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle extras = new Bundle();
        extras.putInt("EXO_WIN", player.getCurrentWindowIndex());
        extras.putLong("EXO_POS", player.getCurrentPosition());
        getIntent().putExtras(extras);
        player.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
