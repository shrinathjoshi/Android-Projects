/*
 * Copyright 2013 Priboi Tiberiu
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hp.recipeapp.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.project.arcstudio.cakesinfinite.R;
import com.project.arcstudio.cakesinfinite.model.GlobaDataHolder;
import com.project.arcstudio.cakesinfinite.model.entities.Money;
import com.project.arcstudio.cakesinfinite.model.entities.Product;
import com.project.arcstudio.cakesinfinite.util.ColorGenerator;
import com.project.arcstudio.cakesinfinite.util.Utils;
import com.project.arcstudio.cakesinfinite.view.activities.MainActivity;
import com.project.arcstudio.cakesinfinite.view.adapter.ProductsPagerAdapter;
import com.project.arcstudio.cakesinfinite.view.cutomview.ClickableViewPager;
import com.project.arcstudio.cakesinfinite.view.cutomview.LabelView;
import com.project.arcstudio.cakesinfinite.view.cutomview.TextDrawable;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

public class ProductDetailsFragment extends Fragment {

	private int productListNumber;
	private ImageView itemImage;
	private TextView itemSellPrice;
	private TextView itemName;
	private TextView quanitity;
	private TextView itemdescription;
	private TextDrawable.IBuilder mDrawableBuilder;
	private TextDrawable drawable;
	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private String subcategoryKey;
	private boolean isFromCart;
	private ClickableViewPager similarProductsPager;
	private ClickableViewPager topSellingPager;
	private Toolbar mToolbar;

	/**
	 * Instantiates a new product details fragment.
	 */
	public ProductDetailsFragment(String subcategoryKey, int productNumber,
								  boolean isFromCart) {

		this.subcategoryKey = subcategoryKey;
		this.productListNumber = productNumber;
		this.isFromCart = isFromCart;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_product_detail,
				container, false);

		mToolbar = (Toolbar) rootView.findViewById(R.id.htab_toolbar);
		if (mToolbar != null) {
			((MainActivity) getActivity()).setSupportActionBar(mToolbar);
		}

		if (mToolbar != null) {
			((MainActivity) getActivity()).getSupportActionBar()
					.setDisplayHomeAsUpEnabled(true);

			mToolbar.setNavigationIcon(R.drawable.ic_drawer);

		}

		mToolbar.setTitleTextColor(Color.WHITE);

//		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				((MainActivity) getActivity()).getmDrawerLayout()
//						.openDrawer(GravityCompat.START);
//			}
//		});

		((MainActivity) getActivity()).getSupportActionBar()
				.setDisplayHomeAsUpEnabled(true);

//		similarProductsPager = (ClickableViewPager) rootView
//				.findViewById(R.id.similar_products_pager);

//		topSellingPager = (ClickableViewPager) rootView
//				.findViewById(R.id.top_selleing_pager);

		itemSellPrice = ((TextView) rootView
				.findViewById(R.id.category_discount));

		quanitity = ((TextView) rootView.findViewById(R.id.iteam_amount));

		itemName = ((TextView) rootView.findViewById(R.id.product_name));

		itemdescription = ((TextView) rootView
				.findViewById(R.id.product_description));

		itemImage = (ImageView) rootView.findViewById(R.id.product_image);

		fillProductData();

		rootView.findViewById(R.id.add_item).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (isFromCart) {
							
							//Update Quantity on shopping List
							GlobaDataHolder
									.getGlobaDataHolder()
									.getShoppingList()
									.get(productListNumber)
									.setQuantity(
											String.valueOf(

											Integer.valueOf(GlobaDataHolder
													.getGlobaDataHolder()
													.getShoppingList()
													.get(productListNumber)
													.getQuantity()) + 1));

							
							//Update Ui
							quanitity.setText(GlobaDataHolder
									.getGlobaDataHolder().getShoppingList()
									.get(productListNumber).getQuantity());

						//	Utils.vibrate(getActivity());

							//Update checkout amount on screen
							((MainActivity) getActivity()).updateCheckOutAmount(
									BigDecimal.valueOf(Long
											.valueOf(GlobaDataHolder
													.getGlobaDataHolder()
													.getShoppingList()
													.get(productListNumber)
													.getSellMRP())), true);

						} else {

							// current object
							Product tempObj = GlobaDataHolder
									.getGlobaDataHolder().getProductMap()
									.get(subcategoryKey).get(productListNumber);

							// if current object is lready in shopping list
							if (GlobaDataHolder.getGlobaDataHolder()
									.getShoppingList().contains(tempObj)) {

								// get position of current item in shopping list
								int indexOfTempInShopingList = GlobaDataHolder
										.getGlobaDataHolder().getShoppingList()
										.indexOf(tempObj);

								// increase quantity of current item in shopping
								// list
								if (Integer.parseInt(tempObj.getQuantity()) == 0) {

									((MainActivity) getContext())
											.updateItemCount(true);

								}

								// update quanity in shopping list
								GlobaDataHolder
										.getGlobaDataHolder()
										.getShoppingList()
										.get(indexOfTempInShopingList)
										.setQuantity(
												String.valueOf(Integer
														.valueOf(tempObj
																.getQuantity()) + 1));

								// update checkout amount
								((MainActivity) getContext()).updateCheckOutAmount(
										BigDecimal.valueOf(Long
												.valueOf(GlobaDataHolder
														.getGlobaDataHolder()
														.getProductMap()
														.get(subcategoryKey)
														.get(productListNumber)
														.getSellMRP())), true);

								// update current item quanitity
								quanitity.setText(tempObj.getQuantity());

							} else {

								((MainActivity) getContext())
										.updateItemCount(true);

								tempObj.setQuantity(String.valueOf(1));

								quanitity.setText(tempObj.getQuantity());

								GlobaDataHolder.getGlobaDataHolder()
										.getShoppingList().add(tempObj);

								((MainActivity) getContext()).updateCheckOutAmount(
										BigDecimal.valueOf(Long
												.valueOf(GlobaDataHolder
														.getGlobaDataHolder()
														.getProductMap()
														.get(subcategoryKey)
														.get(productListNumber)
														.getSellMRP())), true);

							}

//							Utils.vibrate(getContext());

						}
					}

				});

		rootView.findViewById(R.id.remove_item).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (isFromCart)

						{

							if (Integer.valueOf(GlobaDataHolder
									.getGlobaDataHolder().getShoppingList()
									.get(productListNumber).getQuantity()) > 2) {

								GlobaDataHolder
										.getGlobaDataHolder()
										.getShoppingList()
										.get(productListNumber)
										.setQuantity(
												String.valueOf(

												Integer.valueOf(GlobaDataHolder
														.getGlobaDataHolder()
														.getShoppingList()
														.get(productListNumber)
														.getQuantity()) - 1));

								quanitity.setText(GlobaDataHolder
										.getGlobaDataHolder().getShoppingList()
										.get(productListNumber).getQuantity());

								((MainActivity) getActivity()).updateCheckOutAmount(
										BigDecimal.valueOf(Long
												.valueOf(GlobaDataHolder
														.getGlobaDataHolder()
														.getShoppingList()
														.get(productListNumber)
														.getSellMRP())), false);

								//Utils.vibrate(getActivity());
							}

							else if (Integer.valueOf(GlobaDataHolder
									.getGlobaDataHolder().getShoppingList()
									.get(productListNumber).getQuantity()) == 1) {
								((MainActivity) getActivity())
										.updateItemCount(false);

								((MainActivity) getActivity()).updateCheckOutAmount(
										BigDecimal.valueOf(Long
												.valueOf(GlobaDataHolder
														.getGlobaDataHolder()
														.getShoppingList()
														.get(productListNumber)
														.getSellMRP())), false);

								GlobaDataHolder.getGlobaDataHolder()
										.getShoppingList()
										.remove(productListNumber);

								if (Integer
										.valueOf(((MainActivity) getActivity())
												.getItemCount()) == 0) {

									MyCartFragment.updateMyCartFragment(false);

								}

								//Utils.vibrate(getActivity());

							}

						}

						else {

							Product tempObj = GlobaDataHolder
									.getGlobaDataHolder().getProductMap()
									.get(subcategoryKey).get(productListNumber);

							if (GlobaDataHolder.getGlobaDataHolder()
									.getShoppingList().contains(tempObj)) {

								int indexOfTempInShopingList = GlobaDataHolder
										.getGlobaDataHolder().getShoppingList()
										.indexOf(tempObj);

								if (Integer.valueOf(tempObj.getQuantity()) != 0) {

									GlobaDataHolder
											.getGlobaDataHolder()
											.getShoppingList()
											.get(indexOfTempInShopingList)
											.setQuantity(
													String.valueOf(Integer.valueOf(tempObj
															.getQuantity()) - 1));

									((MainActivity) getContext()).updateCheckOutAmount(
											BigDecimal.valueOf(Long
													.valueOf(GlobaDataHolder
															.getGlobaDataHolder()
															.getProductMap()
															.get(subcategoryKey)
															.get(productListNumber)
															.getSellMRP())),
											false);

									quanitity.setText(GlobaDataHolder
											.getGlobaDataHolder()
											.getShoppingList()
											.get(indexOfTempInShopingList)
											.getQuantity());

								//	Utils.vibrate(getContext());

									if (Integer.valueOf(GlobaDataHolder
											.getGlobaDataHolder()
											.getShoppingList()
											.get(indexOfTempInShopingList)
											.getQuantity()) == 0) {

										GlobaDataHolder
												.getGlobaDataHolder()
												.getShoppingList()
												.remove(indexOfTempInShopingList);

										((MainActivity) getContext())
												.updateItemCount(false);

									}

								}

							} else {

							}

						}

					}

				});

		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP
						&& keyCode == KeyEvent.KEYCODE_BACK) {

					if (isFromCart) {

						Utils.switchContent(R.id.frag_container,
								Utils.SHOPPING_LIST_TAG,
								((MainActivity) (getActivity())),
								Utils.AnimationType.SLIDE_UP);
					} else {

						Utils.switchContent(R.id.frag_container,
								Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
								((MainActivity) (getActivity())),
								Utils.AnimationType.SLIDE_RIGHT);
					}

				}
				return true;
			}
		});

		if (isFromCart) {

			similarProductsPager.setVisibility(View.GONE);

			topSellingPager.setVisibility(View.GONE);

		} else {
			showRecomondation();
		}

		return rootView;
	}

	private void showRecomondation() {

		ProductsPagerAdapter mCustomPagerAdapter = new ProductsPagerAdapter(
				getActivity(), subcategoryKey);

//		similarProductsPager.setAdapter(mCustomPagerAdapter);

//		similarProductsPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(int position) {
//
//				productListNumber = position;
//
//				fillProductData();
//
//				Utils.vibrate(getActivity());
//
//			}
//		});
//
//		topSellingPager.setAdapter(mCustomPagerAdapter);
//
//		topSellingPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(int position) {
//
//				productListNumber = position;
//
//				fillProductData();
//
//				Utils.vibrate(getActivity());
//
//			}
//		});
	}

	public void fillProductData() {

		if (!isFromCart) {
			
			
			//Fetch and display item from Gloabl Data Model

			itemName.setText(GlobaDataHolder.getGlobaDataHolder()
					.getProductMap().get(subcategoryKey).get(productListNumber)
					.getItemName());

			quanitity.setText(GlobaDataHolder.getGlobaDataHolder()
					.getProductMap().get(subcategoryKey).get(productListNumber)
					.getQuantity());

			itemdescription.setText(GlobaDataHolder.getGlobaDataHolder()
					.getProductMap().get(subcategoryKey).get(productListNumber)
					.getItemDetail());

			String sellCostString = Money.rupees(
					BigDecimal.valueOf(Long.valueOf(GlobaDataHolder
							.getGlobaDataHolder().getProductMap()
							.get(subcategoryKey).get(productListNumber)
							.getSellMRP()))).toString()
					+ "  ";

			String buyMRP = Money.rupees(
					BigDecimal.valueOf(Long.valueOf(GlobaDataHolder
							.getGlobaDataHolder().getProductMap()
							.get(subcategoryKey).get(productListNumber)
							.getMRP()))).toString();

			String costString = sellCostString + buyMRP;

			itemSellPrice.setText(costString, BufferType.SPANNABLE);

			Spannable spannable = (Spannable) itemSellPrice.getText();

			spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
					costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			mDrawableBuilder = TextDrawable.builder().beginConfig()
					.withBorder(4).endConfig().roundRect(10);

			drawable = mDrawableBuilder.build(
					String.valueOf(GlobaDataHolder.getGlobaDataHolder()
							.getProductMap().get(subcategoryKey)
							.get(productListNumber).getItemName().charAt(0)),
					mColorGenerator.getColor(GlobaDataHolder
							.getGlobaDataHolder().getProductMap()
							.get(subcategoryKey).get(productListNumber)
							.getItemName()));

			Picasso.with(getActivity())
					.load(GlobaDataHolder.getGlobaDataHolder().getProductMap()
							.get(subcategoryKey).get(productListNumber)
							.getImageURL()).placeholder(drawable)
					.error(drawable).fit().centerCrop()
					.networkPolicy(NetworkPolicy.OFFLINE)
					.into(itemImage, new Callback() {
						@Override
						public void onSuccess() {

						}

						@Override
						public void onError() {
							// Try again online if cache failed

							Picasso.with(getActivity())
									.load(GlobaDataHolder.getGlobaDataHolder()
											.getProductMap()
											.get(subcategoryKey)
											.get(productListNumber)
											.getImageURL())
									.placeholder(drawable).error(drawable)
									.fit().centerCrop().into(itemImage);
						}
					});

			LabelView label = new LabelView(getActivity());

			label.setText(GlobaDataHolder.getGlobaDataHolder().getProductMap()
					.get(subcategoryKey).get(productListNumber).getDiscount());
			label.setBackgroundColor(0xffE91E63);

			label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);
		} else {

			
			//Fetch and display products from Shopping list
			
			itemName.setText(GlobaDataHolder.getGlobaDataHolder()
					.getShoppingList().get(productListNumber).getItemName());

			quanitity.setText(GlobaDataHolder.getGlobaDataHolder()
					.getShoppingList().get(productListNumber).getQuantity());

			itemdescription.setText(GlobaDataHolder.getGlobaDataHolder()
					.getShoppingList().get(productListNumber).getItemDetail());

			String sellCostString = Money.rupees(
					BigDecimal.valueOf(Long.valueOf(GlobaDataHolder
							.getGlobaDataHolder().getShoppingList()
							.get(productListNumber).getSellMRP()))).toString()
					+ "  ";

			String buyMRP = Money.rupees(
					BigDecimal.valueOf(Long.valueOf(GlobaDataHolder
							.getGlobaDataHolder().getShoppingList()
							.get(productListNumber).getMRP()))).toString();

			String costString = sellCostString + buyMRP;

			itemSellPrice.setText(costString, BufferType.SPANNABLE);

			Spannable spannable = (Spannable) itemSellPrice.getText();

			spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
					costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			mDrawableBuilder = TextDrawable.builder().beginConfig()
					.withBorder(4).endConfig().roundRect(10);

			drawable = mDrawableBuilder.build(
					String.valueOf(GlobaDataHolder.getGlobaDataHolder()
							.getShoppingList().get(productListNumber)
							.getItemName().charAt(0)),
					mColorGenerator.getColor(GlobaDataHolder
							.getGlobaDataHolder().getShoppingList()
							.get(productListNumber).getItemName()));

			Picasso.with(getActivity())
					.load(GlobaDataHolder.getGlobaDataHolder()
							.getShoppingList().get(productListNumber)
							.getImageURL()).placeholder(drawable)
					.error(drawable).fit().centerCrop()
					.networkPolicy(NetworkPolicy.OFFLINE)
					.into(itemImage, new Callback() {
						@Override
						public void onSuccess() {

						}

						@Override
						public void onError() {
							// Try again online if cache failed

							Picasso.with(getActivity())
									.load(GlobaDataHolder.getGlobaDataHolder()
											.getShoppingList()
											.get(productListNumber)
											.getImageURL())
									.placeholder(drawable).error(drawable)
									.fit().centerCrop().into(itemImage);
						}
					});

			LabelView label = new LabelView(getActivity());

			label.setText(GlobaDataHolder.getGlobaDataHolder()
					.getShoppingList().get(productListNumber).getDiscount());
			label.setBackgroundColor(0xffE91E63);

			label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);

		}
	}


}
