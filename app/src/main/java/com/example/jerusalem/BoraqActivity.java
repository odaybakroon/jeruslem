package com.example.jerusalem;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
public class BoraqActivity extends AppCompatActivity {
    private PlayerView playerView;
    private ProgressBar loading;
    private SimpleExoPlayer player;
    FirebaseStorage storage;
    StorageReference storageReference;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boraq);

        playerView =findViewById(R.id.video_view);

        getVideo();
    }

    private void getVideo(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://jerusalem-e8f4a.appspot.com/اخطر مكان عند اليهود  ، حائط البراق (حائط المبكى) -صالح زغاري.mp4");

        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(BoraqActivity.this, new DefaultRenderersFactory(BoraqActivity.this),
                new DefaultTrackSelector(adaptiveTrackSelection),
                new DefaultLoadControl());

        playerView.setPlayer(player);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(BoraqActivity.this,
                Util.getUserAgent(BoraqActivity.this, "Exo2"), defaultBandwidthMeter);

        Handler mainHandler = new Handler();
        final DefaultHttpDataSourceFactory dataSource = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(this, "your-user-agent"));

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSource)
                        .createMediaSource(uri, null, null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playbackPosition);

            }
        });


    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 24) {
            releasePlayer();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 24) {
            releasePlayer();
        }
    }
}