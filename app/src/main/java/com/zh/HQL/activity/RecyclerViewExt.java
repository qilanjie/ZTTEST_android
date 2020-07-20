package com.zh.HQL.activity;


import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 支持添加头部View 、尾部View 、
 * 设置item单击和长按事件和上拉加载
 * 更多的RecyclerView
 *
 */
public class RecyclerViewExt extends RecyclerView {

    private Adapter<ViewHolder> mAdapter;
//    private List<View> mHeaderViews;
//    private List<View> mFooterViews;

    private boolean isLoadingMore = false;

    public RecyclerViewExt(Context context) {
        super(context);
        init();
    }

    public RecyclerViewExt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewExt(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//        mHeaderViews = new ArrayList<View>();
//        mFooterViews = new ArrayList<View>();
        registerOnScrollListener();
    }

    /** for OnItemClickListener start */
    private OnItemClickListener mItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        removeOnItemTouchListener(mOnRecyclerViewItemClickListener);
        addOnItemTouchListener(mOnRecyclerViewItemClickListener);
    }

    public interface OnItemClickListener {

//        public void onItemClick(RecyclerView.ViewHolder vh, int position);

        public void onItemLongClick(ViewHolder vh, int position);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener(this);
    private class OnRecyclerViewItemClickListener implements    OnItemTouchListener {

        private RecyclerView mRecyclerView;
        private GestureDetectorCompat mGestureDetectorCompat;

        public OnRecyclerViewItemClickListener(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
            mGestureDetectorCompat = new GestureDetectorCompat(
                    recyclerView.getContext(),
                    new ItemTouchGestureDetectorListener());
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView,
                                             MotionEvent motionEvent) {
            mGestureDetectorCompat.onTouchEvent(motionEvent);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView,
                                 MotionEvent motionEvent) {
            mGestureDetectorCompat.onTouchEvent(motionEvent);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        private class ItemTouchGestureDetectorListener extends
                GestureDetector.SimpleOnGestureListener {

 //           @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                if (mItemClickListener == null) {
//                    return false;
//                }
//                View clickedChild = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (clickedChild != null) {
//                    RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(clickedChild);
//                    if (vh != null) {
//                        if (vh.getItemViewType() != RecyclerViewAdapterWrapper.VIEW_TYPE_HEADER
//                                && vh.getItemViewType() != RecyclerViewAdapterWrapper.VIEW_TYPE_FOOTER) {
////                            if (hasHeader()) {
////                                mItemClickListener.onItemClick(vh, vh.getAdapterPosition() - getHeadersCount());
////                            } else {
////                                mItemClickListener.onItemClick(vh, vh.getAdapterPosition());
////                            }
//                            return true;
//                        }
//                    }
//                }
//                return false;
//            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mItemClickListener == null) {
                    return;
                }
                View clickedChild = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (clickedChild != null) {
                    ViewHolder vh = mRecyclerView .getChildViewHolder(clickedChild);
                    if (vh != null) {
                        if (vh.getItemViewType() != RecyclerViewAdapterWrapper.VIEW_TYPE_HEADER
                                && vh.getItemViewType() != RecyclerViewAdapterWrapper.VIEW_TYPE_FOOTER) {
//                            if (hasHeader()) {
//                                mItemClickListener.onItemLongClick(vh, vh.getAdapterPosition() - getHeadersCount());
//                            } else {
                                mItemClickListener.onItemLongClick(vh, vh.getAdapterPosition());
//                            }

                        }
                    }
                }
            }
        }

    }

    /** for OnItemClickListener end */

    /** for header view and footer view start */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setAdapter(Adapter adapter) {
//        if (mHeaderViews.isEmpty() && mFooterViews.isEmpty()) {
//        if ( mFooterViews.isEmpty()) {
            super.setAdapter(adapter);
//        } else {
//            adapter = new RecyclerViewAdapterWrapper( mFooterViews, adapter);
//            super.setAdapter(adapter);
//        }
        mAdapter = adapter;
    }

    /**
     *
     * */
//    public void addHeaderView(View headerView) {
//       mHeaderViews.clear();
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT) ;
//        headerView.setLayoutParams(params);
//        mHeaderViews.add(headerView);
//        if (mAdapter != null) {
//            if (!(mAdapter instanceof RecyclerViewAdapterWrapper)) {
//                mAdapter =  new RecyclerViewAdapterWrapper( mFooterViews, mAdapter);
//            }
//        }
//    }

//    public void addFooterView(View footetView) {
//        mFooterViews.clear();
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT) ;
//        footetView.setLayoutParams(params);
//        mFooterViews.add(footetView);
//        if (mAdapter != null) {
//            if (!(mAdapter instanceof RecyclerViewAdapterWrapper)) {
//                mAdapter =  new RecyclerViewAdapterWrapper(mFooterViews, mAdapter);
//            }
//        }
//    }

    public static class RecyclerViewAdapterWrapper extends Adapter<ViewHolder> {

        private static final ArrayList<View> EMPTY_INFO_VIEW_LIST = new ArrayList<View>();

        private static final int VIEW_TYPE_HEADER = RecyclerView.INVALID_TYPE;
        private static final int VIEW_TYPE_FOOTER = VIEW_TYPE_HEADER - 1;

        private Adapter<ViewHolder> mWrapedAdapter;
//        private List<View> mHeaderViews;
//        private List<View> mFooterViews;

//        public RecyclerViewAdapterWrapper(List<View> headerViews,
public RecyclerViewAdapterWrapper(Adapter<ViewHolder> adapter) {
            mWrapedAdapter = adapter;
//            if (headerViews == null) {
//                mHeaderViews = EMPTY_INFO_VIEW_LIST;
//            } else {
//                mHeaderViews = headerViews;
//            }

//            if (footerViews == null) {
//                mFooterViews = EMPTY_INFO_VIEW_LIST;
//            } else {
//                mFooterViews = footerViews;
//            }

            mWrapedAdapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    RecyclerViewAdapterWrapper.this.notifyDataSetChanged();
                }
            });

        }

//        public int getHeadersCount() {
//            return mHeaderViews.size();
//        }

//        public int getFootersCount() {
//            return mFooterViews.size();
//        }

        public Adapter<ViewHolder> getWrapedAdapter() {
            return mWrapedAdapter;
        }

        @Override
        public int getItemViewType(int position) {

//            int headersCount = getHeadersCount();
//
//            if (position < headersCount) {
//                return VIEW_TYPE_HEADER;
//            }

//            int adjPosition = position - headersCount;
            int adjPosition = position;
            int adapterCount = 0;
            if (mWrapedAdapter != null) {
                adapterCount = mWrapedAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mWrapedAdapter.getItemViewType(adjPosition);
                }
            }
//            return VIEW_TYPE_FOOTER;
            return 0;
        }

        @Override
        public int getItemCount() {
//            if (mWrapedAdapter == null) {
//                return getFootersCount();
//            }
            if (mWrapedAdapter == null) {
                return mWrapedAdapter.getItemCount();
            }
            return 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if (viewType == VIEW_TYPE_HEADER) {
//                return new HeaderOrFooterViewHolder(mHeaderViews.get(0));
//            } else if (viewType == VIEW_TYPE_FOOTER) {
//                return new HeaderOrFooterViewHolder(mFooterViews.get(0));
//            }
            return mWrapedAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            int headersCount =0;
            /** header */
            if (position < headersCount) {
                return;
            }
            int adjPosition = position - headersCount;
            int adapterCount = 0;
            if (mWrapedAdapter != null) {
                adapterCount = mWrapedAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mWrapedAdapter.onBindViewHolder(viewHolder, adjPosition);
                }
            }

        }

        @Override
        public long getItemId(int position) {
            int headersCount = 0;
            if (mWrapedAdapter != null && position >= headersCount) {
                int adjPosition = position - headersCount;
                int adapterCount = mWrapedAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mWrapedAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        /** header or footer view holder */
        public static class HeaderOrFooterViewHolder extends ViewHolder {

            public HeaderOrFooterViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

//    public boolean hasHeader() {
//        return !mHeaderViews.isEmpty();
//    }

//    public boolean hasFooter() {
//        return ! mFooterViews.isEmpty();
//    }

//    public int getHeadersCount() {
//        return mHeaderViews.size();
//    }

//    public int getFootersCount() {
//        return mFooterViews.size();
//    }

    public int getAdapterItemCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getItemCount();
    }

    public Adapter<ViewHolder> getWrapedAdapter() {
        if (mAdapter == null) {
            return null;
        }
        if (!(mAdapter instanceof RecyclerViewAdapterWrapper)) {
            return mAdapter;
        }
        RecyclerViewAdapterWrapper adapter = (RecyclerViewAdapterWrapper) mAdapter;
        return adapter.getWrapedAdapter();
    }

    public int getWrapedAdapterItemCount() {
        return getWrapedAdapter().getItemCount();
    }

    public void registerAdapterDataObserver(AdapterDataObserver observer) {
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(observer);
        }
    }

    public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(observer);
        }
    }

    /** for header view and footer view end */


    /** for OnLoadMoreListener start */
    public interface OnLoadMoreListener {
        public void onLoadMore(int begin);
    }

    private OnLoadMoreListener mOnMoreListener;

    public OnLoadMoreListener getOnMoreListener() {
        return mOnMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    private void registerOnScrollListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mOnMoreListener != null && !isLoadingMore && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
//                    if (lastVisiblePosition == (hasHeader() ? getWrapedAdapterItemCount()
//                            : getWrapedAdapterItemCount() - 1)) {
                        if (lastVisiblePosition ==( getWrapedAdapterItemCount() - 1)) {
                        isLoadingMore = true;
                        mOnMoreListener.onLoadMore(getWrapedAdapterItemCount() - 1);
                    }
                }
            }

        });

    }

    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    /** for OnLoadMoreListener end */

}

