package com.okorana.documentext.ui.wrapper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eefret on 5/5/16.
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {
    private V view;

    public ViewWrapper(V itemview){
        super(itemview);
        view = itemview;
    }

    public V getView(){
        return view;
    }
}
