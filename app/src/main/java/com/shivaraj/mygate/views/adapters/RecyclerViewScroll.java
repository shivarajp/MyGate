package com.shivaraj.mygate.views.adapters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class RecyclerViewScroll extends RecyclerView.OnScrollListener {

    private int visibleThreshold;
    private int previousTotal = 0;
    private long jobsCount = 0;
    private boolean isLoading = true;
    private OnScroll list;

    public RecyclerViewScroll(OnScroll list, long jobsCount, int visibleThreshold) {
        this.list = list;
        this.jobsCount = jobsCount;
        this.visibleThreshold = visibleThreshold;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        loadMoreIfneeded(recyclerView);
    }

    public void loadMoreIfneeded(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = 0;

        //TODO For some reason last visible item and total are same so it is going infinte call.
        //Fixing this
        if(layoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        } else if(layoutManager instanceof StaggeredGridLayoutManager){
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager)layoutManager).findLastVisibleItemPositions(firstVisibleItems);
            if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                lastVisibleItem = firstVisibleItems[0];
            }
        } else if(layoutManager instanceof GridLayoutManager){
            lastVisibleItem = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
        }


        if(totalItemCount >= jobsCount){
            isLoading = false;
            return;
        }
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        } else if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            if (list != null) {
                list.loadMore();
            }
            isLoading = true;
        }
    }

}
