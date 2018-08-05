package com.marche.audiobookier.util

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue



object ViewUtil {

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun getActionBarHeight(activity: Activity): Float{
        val tv = TypedValue()
        if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, Resources.getSystem().displayMetrics).toFloat()
        }

        return 0f
    }

}
