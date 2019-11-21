package com.bfhd.evaluate.view.popwindow;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * kxf -> 2019-10-11
 **/
@IntDef({
        VerticalPosition.CENTER,
        VerticalPosition.ABOVE,
        VerticalPosition.BELOW,
        VerticalPosition.ALIGN_TOP,
        VerticalPosition.ALIGN_BOTTOM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface VerticalPosition {
    int CENTER = 0;
    int ABOVE = 1;
    int BELOW = 2;
    int ALIGN_TOP = 3;
    int ALIGN_BOTTOM = 4;
}