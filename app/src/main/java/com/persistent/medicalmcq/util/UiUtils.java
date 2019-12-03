package com.persistent.medicalmcq.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.security.PublicKey;

/**
 * Created by ahmed_anwar on 11-May-15.
 */
public class UiUtils {
    public static int getDIP(Context context,int pixel)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, dm);
        return (int)dpInPx;
    }
    public static int getSIP(Context context,int pixel)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float spInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixel, dm);
        return (int)spInPx;
    }

    public static void disableEnableControls(boolean enable, ViewGroup vg){


        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }
}
