package com.okorana.documentext.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.okorana.documentext.ui.wrapper.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eefret on 5/5/16.
 */
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<T>();

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {
        return items.size();
    }
}
