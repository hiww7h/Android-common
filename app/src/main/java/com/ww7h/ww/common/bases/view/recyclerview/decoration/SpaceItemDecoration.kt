package com.ww7h.ww.common.bases.view.recyclerview.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v7.widget.StaggeredGridLayoutManager



/**
 * Created by ww on 2018/7/29.
 */
class SpaceItemDecoration (space: Int,column:Int): RecyclerView.ItemDecoration() {

    private var mSpace: Int = 0
    private var mColumn: Int = 0

    /**
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        // 获取item在span中的下标
        val spanIndex = params.spanIndex

        outRect.bottom = mSpace
        if (spanIndex < mColumn) {
            outRect.top = mSpace
        } else {
            outRect.top = 0
        }
        if (mColumn == 1) {
            outRect.left = mSpace
            outRect.right = mSpace
        } else {

            when {
                spanIndex % mColumn == 0 -> {
                    outRect.left = mSpace
                    outRect.right = mSpace / 2
                }
                spanIndex % mColumn == mColumn - 1 -> {
                    outRect.left = mSpace / 2
                    outRect.right = mSpace
                }
                else -> {
                    outRect.left = mSpace / 2
                    outRect.right = mSpace / 2
                }
            }
        }

    }

    init {
        this.mSpace = space
        this.mColumn = column
    }

}