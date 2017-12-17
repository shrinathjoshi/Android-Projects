package com.example.hp.recipeapp.view.adapter;

public interface ItemTouchHelperAdapter {


    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}