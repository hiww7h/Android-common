package com.ww7h.ww.common.bases.view.recyclerview

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet

abstract class LoadMoreRecyclerView(context: Context, attrs: AttributeSet?): RecyclerView(context,attrs) {
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var loadMoreListener: LoadMoreListener? = null
    private var onScrollListener: OnScrollListener? = null

    fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout
    }

    fun setLoadMoreListener(loadMoreListener: LoadMoreListener) {
        if (swipeRefreshLayout == null) {
            swipeRefreshLayout = parent as SwipeRefreshLayout
        }
        this.loadMoreListener = loadMoreListener
        super.setOnScrollListener(
            RecyclerViewOnScrollListener(
                swipeRefreshLayout,
                onScrollListener
            )
        )

    }

    class RecyclerViewOnScrollListener(private var swipeRefreshLayout: SwipeRefreshLayout?
                                       , private var onScrollListener: OnScrollListener?): RecyclerView.OnScrollListener() {
        private var lastVisibleItemPosition: Int = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == recyclerView.adapter!!.itemCount) {
                val isRefreshing = swipeRefreshLayout?.isRefreshing
                if (isRefreshing!!) {
                    recyclerView.adapter!!.notifyItemRemoved((recyclerView as LoadMoreRecyclerView).adapter!!.itemCount)
                    return
                }
                (recyclerView as LoadMoreRecyclerView).loadMoreListener!!.loadData()
            }
            onScrollListener?.onScroll()
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        }
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }

    interface LoadMoreListener {
        fun loadData()
    }

    interface OnScrollListener {
        fun onScroll()
    }
}