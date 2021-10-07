package com.jamessc.countrypicker.util

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class llm_smooth(private val mContext: Context, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(mContext, orientation, reverseLayout) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?,
                                        position: Int) {
        val smoothScroller = object : TopSnappedSmoothScroller(recyclerView.context) {
            //This controls the direction in which smoothScroll looks for your view
//            override fun computeScrollVectorForPosition(targetPosition: Int): PointF {
//                return PointF(0f, 1f)
//            }

            //This returns the milliseconds it takes to scroll one pixel.
            protected override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)

    }

    private open inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@llm_smooth
                .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_START
        }
    }

    private open inner class CenterSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@llm_smooth
                .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_END
        }
    }

    // Scrolling speed
    companion object {
        val MILLISECONDS_PER_INCH = 110f
    }

}