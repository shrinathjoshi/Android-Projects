package com.project.arcstudio.cakesinfinite.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.project.arcstudio.cakesinfinite.R;
import com.project.arcstudio.cakesinfinite.model.GlobaDataHolder;
import com.project.arcstudio.cakesinfinite.util.Utils;
import com.project.arcstudio.cakesinfinite.view.activities.MainActivity;
import com.project.arcstudio.cakesinfinite.view.adapter.ShoppingListAdapter;
import com.project.arcstudio.cakesinfinite.view.cutomview.OnStartDragListener;
import com.project.arcstudio.cakesinfinite.view.cutomview.SimpleItemTouchHelperCallback;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.HomeFragment;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

public class MyCartFragment extends Fragment implements OnStartDragListener {

	public MyCartFragment() {
	}

	private ItemTouchHelper mItemTouchHelper;
	private static FrameLayout noItemDefault;
	private static RecyclerView recyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_product_list_fragment, container,
				false);

		view.findViewById(R.id.slide_down).setVisibility(View.VISIBLE);
		view.findViewById(R.id.slide_down).setOnTouchListener(
				new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						Utils.switchFragmentWithAnimation(R.id.frag_container,
								new HomeFragment(),
								((MainActivity) (getContext())),
								Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG, Utils.AnimationType.SLIDE_DOWN);

						return false;
					}
				});

		// Fill Recycler View

		noItemDefault = (FrameLayout) view.findViewById(R.id.default_nodata);

		recyclerView = (RecyclerView) view
				.findViewById(R.id.product_list_recycler_view);

		if (GlobaDataHolder.getGlobaDataHolder().getShoppingList().size() != 0) {

			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
					getActivity().getBaseContext());

			recyclerView.setLayoutManager(linearLayoutManager);
			recyclerView.setHasFixedSize(true);

			ShoppingListAdapter shoppinListAdapter = new ShoppingListAdapter(
					getActivity(), this);

			recyclerView.setAdapter(shoppinListAdapter);

			JazzyRecyclerViewScrollListener jazzyScrollListener = new JazzyRecyclerViewScrollListener();
			recyclerView.setOnScrollListener(jazzyScrollListener);
			jazzyScrollListener.setTransitionEffect(11);

			shoppinListAdapter
					.SetOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {

						@Override
						public void onItemClick(View view, int position) {

							Utils.switchFragmentWithAnimation(
									R.id.frag_container,
									new ProductDetailsFragment("", position,
											true),
									((MainActivity) (getContext())), null,
									Utils.AnimationType.SLIDE_LEFT);

						}
					});

			ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(
					shoppinListAdapter);
			mItemTouchHelper = new ItemTouchHelper(callback);
			mItemTouchHelper.attachToRecyclerView(recyclerView);

		}

		else {

			updateMyCartFragment(false);

		}

		view.findViewById(R.id.start_shopping).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Utils.switchContent(R.id.frag_container,
								Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
								(getActivity()),
								Utils.AnimationType.SLIDE_UP);

					}
				});

		// Handle Back press
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP
						&& keyCode == KeyEvent.KEYCODE_BACK) {

					Utils.switchContent(R.id.frag_container,
							Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
							((MainActivity) (getContext())),
							Utils.AnimationType.SLIDE_UP);

				}
				return true;
			}
		});

		return view;
	}

	@Override
	public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
		mItemTouchHelper.startDrag(viewHolder);
	}

	public static void updateMyCartFragment(boolean showList) {

		if (showList) {

			if (null != recyclerView && null != noItemDefault) {
				recyclerView.setVisibility(View.VISIBLE);

				noItemDefault.setVisibility(View.GONE);
			}
		} else {
			if (null != recyclerView && null != noItemDefault) {
				recyclerView.setVisibility(View.GONE);

				noItemDefault.setVisibility(View.VISIBLE);
			}
		}
	}

}