package com.ww7h.ww.common.bases.view.recyclerview.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by ww on 2018/7/29.
 */
class SpaceItemDecoration (space: Int,column:Int): RecyclerView.ItemDecoration() {

    private var mSpace: Int = 0
    private var mColumn: Int = 0

    /**
     * Retrieve any offsets for the given item. Each field of `outRect` specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     *
     *
     *
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of `outRect` (left, top, right, bottom) to zero
     * before returning.
     *
     *
     *
     *
     * If you need to access Adapter for additional data, you can call
     * [RecyclerView.getChildAdapterPosition] to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = mSpace
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace
        } else {
            outRect.top = 0
        }
        if (mColumn == 1) {
            outRect.left = mSpace
            outRect.right = mSpace
        } else {

            if (parent.getChildAdapterPosition(view) % mColumn == 0) {
                outRect.left = mSpace
                outRect.right = mSpace / 2
            } else if (parent.getChildAdapterPosition(view) % mColumn == mColumn - 1) {
                outRect.left = mSpace / 2
                outRect.right = mSpace
            } else {
                outRect.left = mSpace / 2
                outRect.right = mSpace / 2
            }
        }

    }

    init {
        this.mSpace = space
        this.mColumn = column
    }

}