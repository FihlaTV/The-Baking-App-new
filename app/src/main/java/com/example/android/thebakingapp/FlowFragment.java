package com.example.android.thebakingapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import static android.view.View.GONE;

/**
 * Created by Arjun Vidyarthi on 24-Mar-18.
 */

public class FlowFragment extends android.support.v4.app.Fragment {
    String LOG_TAG;
    Activity context;
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

    ArrayList<Integer> allIDs;
    ArrayList<String> allDesc;
    ArrayList<String> allVid;
    ArrayList<String> allThumb;
    Boolean clicked;
    int current;
    int currentWindow;
    long playbackPosition;
    Boolean playWhenReady = true;


    public FlowFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        final View rootView = inflater.inflate(R.layout.fragment_flow, container, false);
        ButterKnife.bind(this, rootView);

        Bundle extras = this.getArguments();
        try {
            clicked = extras.getBoolean("CLICKED");
            current = extras.getInt("THIS_ID");
            allIDs = extras.getIntegerArrayList("ALL_IDS");
            allDesc = extras.getStringArrayList("ALL_DESC");
            allVid = extras.getStringArrayList("ALL_VID");
            allThumb = extras.getStringArrayList("ALL_THUMB");

            step_desc.setText(allDesc.get(current));

            if (current == (allIDs.size() - 1)) {
                next_button.setVisibility(GONE);
            }

            if (current == 0) {
                prev_button.setVisibility(GONE);
            }


            if (!allVid.get(current).equals("")) {
                initializePlayer();
                stepThumb.setVisibility(GONE);
            } else if (!allThumb.get(current).equals("")) {
                Picasso.with(context).load(allThumb.get(current)).into(stepThumb);
                videoView.setVisibility(GONE);
            } else {
                videoView.setVisibility(GONE);
            }


        } catch (NullPointerException e) {
            videoView.setVisibility(GONE);
            stepThumb.setVisibility(GONE);
            prev_button.setVisibility(GONE);
            next_button.setVisibility(GONE);
            step_desc.setVisibility(GONE);
            Log.e(LOG_TAG, "Null pointer in intent");
            e.printStackTrace();
        }

        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt("EXO_WIN");
            playbackPosition = savedInstanceState.getLong("EXO_POS");
            current = savedInstanceState.getInt("STEP_POS");
            setViewsOnRotate();
        }

        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    playbackPosition = 0;
                    currentWindow = 0;
                    player.stop();
                }
                setViews(false);

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    playbackPosition = 0;
                    currentWindow = 0;
                    player.stop();
                }
                setViews(true);
            }
        });

        return rootView;

    }

    void setViewsOnRotate() {
        next_button.setVisibility(View.VISIBLE);
        prev_button.setVisibility(View.VISIBLE);
        try {

            if (current == (allIDs.size() - 1)) {
                next_button.setVisibility(View.GONE);
            }

            if (current == 0) {
                prev_button.setVisibility(View.GONE);
            }

            step_desc.setText(allDesc.get(current));

            if (!allVid.get(current).equals("")) {
                initializePlayer();
                stepThumb.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
            } else if (!allThumb.get(current).equals("")) {
                stepThumb.setVisibility(View.VISIBLE);
                Picasso.with(context).load(allThumb.get(current)).into(stepThumb);
                videoView.setVisibility(View.GONE);
            } else {
                videoView.setVisibility(View.GONE);
                stepThumb.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }

    private void initializePlayer() {
        if (player != null) {
            player.stop();
            player = null;
        }

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
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

        if (next)
            current++;

        if (!next)
            current--;

        if (current == (allIDs.size() - 1)) {
            next_button.setVisibility(GONE);
        }

        if (current == 0) {
            prev_button.setVisibility(GONE);
        }

        step_desc.setText(allDesc.get(current));

        if (!allVid.get(current).equals("")) {
            initializePlayer();
            stepThumb.setVisibility(GONE);
            videoView.setVisibility(View.VISIBLE);
        } else if (!allThumb.get(current).equals("")) {
            stepThumb.setVisibility(View.VISIBLE);
            Picasso.with(context).load(allThumb.get(current)).into(stepThumb);
            videoView.setVisibility(GONE);
        } else {
            videoView.setVisibility(GONE);
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
        outState.putInt("STEP_POS", current);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = context.getIntent().getExtras();

        if (extras != null) {
            currentWindow = extras.getInt("EXO_WIN");
            playbackPosition = extras.getLong("EXO_POS");
        }
        if (allVid != null)
            initializePlayer();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle extras = new Bundle();
        try {
            extras.putInt("EXO_WIN", player.getCurrentWindowIndex());
            extras.putLong("EXO_POS", player.getCurrentPosition());
            context.getIntent().putExtras(extras);
            player.stop();
            releasePlayer();
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
