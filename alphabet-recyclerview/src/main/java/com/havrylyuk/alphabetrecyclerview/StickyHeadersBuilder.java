package com.havrylyuk.alphabetrecyclerview;

import android.support.v7.widget.RecyclerView;


@SuppressWarnings({"WeakerAccess", "unused"})
public class StickyHeadersBuilder {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private StickyHeadersAdapter headersAdapter;
    private OnHeaderClickListener headerClickListener;
    private boolean overlay;
    private boolean isSticky;
    private DrawOrder drawOrder;


    public StickyHeadersBuilder() {
        this.isSticky = true;
        this.drawOrder = DrawOrder.OVER_ITEMS;
    }

    public StickyHeadersBuilder setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setAdapter(adapter);
        if (adapter instanceof StickyHeadersAdapter) {
            setStickyHeadersAdapter((StickyHeadersAdapter) adapter);
        } else {
            throw new IllegalArgumentException("Adapter must implements StickyHeadersAdapter");
        }
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public StickyHeadersBuilder setStickyHeadersAdapter(StickyHeadersAdapter adapter) {
        return setStickyHeadersAdapter(adapter, false);
    }

    @SuppressWarnings("SameParameterValue")
    public StickyHeadersBuilder setStickyHeadersAdapter(StickyHeadersAdapter adapter, boolean overlay) {
        this.headersAdapter = adapter;
        this.overlay = overlay;
        return this;
    }

    public StickyHeadersBuilder setAdapter(RecyclerView.Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("Adapter must have stable ids");
        }
        this.adapter = adapter;
        return this;
    }

    public StickyHeadersBuilder setOnHeaderClickListener(OnHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
        return this;
    }

    public StickyHeadersBuilder setSticky(boolean isSticky) {
        this.isSticky = isSticky;
        return this;
    }

    public StickyHeadersBuilder setDrawOrder(DrawOrder drawOrder) {
        this.drawOrder = drawOrder;
        return this;
    }

    public StickyHeadersItemDecoration build() {
        HeaderStore store = new HeaderStore(recyclerView, headersAdapter, isSticky);
        StickyHeadersItemDecoration decoration =  new StickyHeadersItemDecoration(store, overlay, drawOrder);
        decoration.registerAdapterDataObserver(adapter);
        if (headerClickListener != null) {
            StickyHeadersTouchListener touchListener = new StickyHeadersTouchListener(recyclerView, store);
            touchListener.setListener(headerClickListener);
            recyclerView.addOnItemTouchListener(touchListener);
        }
        return decoration;
    }
}
