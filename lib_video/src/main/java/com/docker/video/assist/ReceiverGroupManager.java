package com.docker.video.assist;

import android.content.Context;

import com.docker.video.cover.CompleteCover;
import com.docker.video.cover.ControllerCover;
import com.docker.video.cover.ErrorCover;
import com.docker.video.cover.GestureCover;
import com.docker.video.cover.LoadingCover;
import com.docker.video.cover.ThumbCover;
import com.docker.video.receiver.GroupValue;
import com.docker.video.receiver.ReceiverGroup;

import static com.docker.video.assist.DataInter.ReceiverKey.KEY_COMPLETE_COVER;
import static com.docker.video.assist.DataInter.ReceiverKey.KEY_CONTROLLER_COVER;
import static com.docker.video.assist.DataInter.ReceiverKey.KEY_ERROR_COVER;
import static com.docker.video.assist.DataInter.ReceiverKey.KEY_GESTURE_COVER;
import static com.docker.video.assist.DataInter.ReceiverKey.KEY_LOADING_COVER;
import static com.docker.video.assist.DataInter.ReceiverKey.KEY_THUMB_COVER;


/**
 * Created by Taurus on 2018/4/18.
 */

public class ReceiverGroupManager {

    private static ReceiverGroupManager i;

    private ReceiverGroupManager() {
    }

    public static ReceiverGroupManager get() {
        if (null == i) {
            synchronized (ReceiverGroupManager.class) {
                if (null == i) {
                    i = new ReceiverGroupManager();
                }
            }
        }
        return i;
    }

    public ReceiverGroup getLittleReceiverGroup(Context context) {
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLittleReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getLiteReceiverGroup(Context context) {
        return getLiteReceiverGroup(context, null);
    }

    public ReceiverGroup getLiteReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context, false));
//        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }


    public ReceiverGroup getDyRecreceiveGroup(Context context, String thumburl) {
        ReceiverGroup receiverGroup = new ReceiverGroup(null);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        receiverGroup.addReceiver(KEY_THUMB_COVER, new ThumbCover(context, thumburl));
        return receiverGroup;
    }


    public ReceiverGroup getReceiverGroup(Context context) {
        return getReceiverGroup(context, null);
    }

    public ReceiverGroup getReceiverGroup(Context context, GroupValue groupValue) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context, true));
        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        return receiverGroup;
    }

    public ReceiverGroup getReceiverGroup(Context context, GroupValue groupValue, String thumburl) {
        ReceiverGroup receiverGroup = new ReceiverGroup(groupValue);
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(context, true));
        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(context));
        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(context));
        receiverGroup.addReceiver(KEY_THUMB_COVER, new ThumbCover(context, thumburl));
        return receiverGroup;
    }

}
