package com.project.arcstudio.cakesinfinite.view.adapter;

public interface ItemTouchHelperAdapter {


    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}