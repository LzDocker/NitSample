package com.docker.common.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by can on 2017/08/11.
 * 图片裁剪类
 */

public class BitmapCut {

    /**
     * 裁剪图片
     */
    public static Bitmap cutBitmap(Bitmap bm) {
        Bitmap bitmap = null;
        if (bm != null) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            DecimalFormat df = new DecimalFormat("0.0");
            float result = (float) height / (float) width;
            String resultformat = df.format(result);
            Log.i("gjw", "cutBitmap: " + "==" + resultformat);
            if (Float.valueOf(resultformat) > 0 && Float.valueOf(resultformat) <= 1.8) {
                bitmap = bm;
            } else {
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight() / 2);
            }
        }
        return bitmap;
    }

}

