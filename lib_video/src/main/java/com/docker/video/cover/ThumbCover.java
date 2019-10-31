package com.docker.video.cover;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.ArrayUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.docker.video.AlivcLiveRoom.ScreenUtils;
import com.docker.video.R;
import com.docker.video.event.OnPlayerEventListener;
import com.docker.video.player.IPlayer;
import com.docker.video.receiver.BaseCover;
import com.docker.video.receiver.PlayerStateGetter;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Taurus on 2018/4/15.
 */

public class ThumbCover extends BaseCover {
    private RequestOptions options = new RequestOptions();
    private String url;
    private ImageView imageView;

    public ThumbCover(Context context, String thumburl) {
        super(context);
        this.url = thumburl;
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform();
               /* .placeholder(resHolderId)
                .error(resErrId)*/
        ;
        Glide.with(imageView).applyDefaultRequestOptions(options).load(url).transition(withCrossFade()).into(imageView);
        PlayerStateGetter playerStateGetter = getPlayerStateGetter();
        if (playerStateGetter != null && isInPlaybackState(playerStateGetter)) {
            setLoadingState(playerStateGetter.getCurrentPosition() == 0);
        }
    }

    private boolean isInPlaybackState(PlayerStateGetter playerStateGetter) {
        int state = playerStateGetter.getState();
        return state != IPlayer.STATE_END
                && state != IPlayer.STATE_ERROR
                && state != IPlayer.STATE_IDLE
                && state != IPlayer.STATE_INITIALIZED
                && state != IPlayer.STATE_STOPPED;
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_START:
                setLoadingState(true);
                break;

            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END:
            case OnPlayerEventListener.PLAYER_EVENT_ON_STOP:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_ERROR:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                setLoadingState(false);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        setLoadingState(false);
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    private void setLoadingState(boolean show) {
        setCoverVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateCoverView(Context context) {
        View view = View.inflate(context, R.layout.layout_thumb_cover, null);
        imageView = view.findViewById(R.id.iv_thumb);
        return view;
    }

    @Override
    public int getCoverLevel() {
        return levelMedium(1);
    }
}
