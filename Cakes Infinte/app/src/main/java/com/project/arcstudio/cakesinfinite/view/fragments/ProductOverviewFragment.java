package com.project.arcstudio.cakesinfinite.view.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.project.arcstudio.cakesinfinite.R;
import com.project.arcstudio.cakesinfinite.domain.mock.FakeWebServer;
import com.project.arcstudio.cakesinfinite.model.GlobaDataHolder;
import com.project.arcstudio.cakesinfinite.util.AppConstants;
import com.project.arcstudio.cakesinfinite.view.activities.MainActivity;
import com.project.arcstudio.cakesinfinite.view.adapter.ProductOverViewPagerAdapter;

import java.util.Set;

public class ProductOverviewFragment extends Fragment {

	// SimpleRecyclerAdapter adapter;
	private KenBurnsView header;
	private Bitmap bitmap;
	private Toolbar mToolbar;
	private ViewPager viewPager;
	private CollapsingToolbarLayout collapsingToolbarLayout;
	private TabLayout tabLayout;

	/** The m handler. */
	private Handler mHandler = new Handler();
	private boolean doubleBackToExitPressedOnce;

	/** The m runnable. */
	private final Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			doubleBackToExitPressedOnce = false;
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_category_details,
				container, false);

		getActivity().setTitle("Products");

		// Simulate Web service calls
		FakeWebServer.getFakeWebServer().getAllProducts(
				AppConstants.CAKE_CATEGORY);

		// TODO We Can use Async task But pallete creation is problemitic job
		// will
		// get back to it later

		// new ProductLoaderTask(null, getActivity(), viewPager, tabLayout);

		// Volley can be used here very efficiently but Fake JSON creation is
		// time consuming Leain it now

		viewPager = (ViewPager) view.findViewById(R.id.htab_viewpager);

		collapsingToolbarLayout = (CollapsingToolbarLayout) view
				.findViewById(R.id.htab_collapse_toolbar);
		collapsingToolbarLayout.setTitleEnabled(false);

		header = (KenBurnsView) view.findViewById(R.id.htab_header);
		header.setImageResource(R.drawable.header);

		tabLayout = (TabLayout) view.findViewById(R.id.htab_tabs);

		mToolbar = (Toolbar) view.findViewById(R.id.htab_toolbar);
		if (mToolbar != null) {
			((MainActivity) getActivity()).setSupportActionBar(mToolbar);
		}

		if (mToolbar != null) {
			((MainActivity) getActivity()).getSupportActionBar()
					.setDisplayHomeAsUpEnabled(true);

			mToolbar.setNavigationIcon(R.drawable.ic_drawer);

		}

		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).getmDrawerLayout()
						.openDrawer(GravityCompat.START);
			}
		});

		setUpUi();

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP
						&& keyCode == KeyEvent.KEYCODE_BACK) {

					if (doubleBackToExitPressedOnce) {
						// super.onBackPressed();

						if (mHandler != null) {
							mHandler.removeCallbacks(mRunnable);
						}

						getActivity().finish();

						return true;
					}

					doubleBackToExitPressedOnce = true;
					Toast.makeText(getActivity(),
							"Please click BACK again to exit",
							Toast.LENGTH_SHORT).show();

					mHandler.postDelayed(mRunnable, 2000);

				}
				return true;
			}
		});

		return view;
	}

	private void setUpUi() {

		setupViewPager(viewPager);

		tabLayout.setupWithViewPager(viewPager);

		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.header);

		Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
			@SuppressWarnings("ResourceType")
			@Override
			public void onGenerated(Palette palette) {

				int vibrantColor = palette.getVibrantColor(R.color.primary_500);
				int vibrantDarkColor = palette
						.getDarkVibrantColor(R.color.primary_700);
				collapsingToolbarLayout.setContentScrimColor(vibrantColor);
				collapsingToolbarLayout
						.setStatusBarScrimColor(vibrantDarkColor);
			}
		});

		tabLayout
				.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
					@Override
					public void onTabSelected(TabLayout.Tab tab) {

						viewPager.setCurrentItem(tab.getPosition());

						switch (tab.getPosition()) {
						case 0:

							header.setImageResource(R.drawable.header);

							bitmap = BitmapFactory.decodeResource(
									getResources(), R.drawable.header);

							Palette.from(bitmap).generate(
									new Palette.PaletteAsyncListener() {
										@SuppressWarnings("ResourceType")
										@Override
										public void onGenerated(Palette palette) {

											int vibrantColor = palette
													.getVibrantColor(R.color.primary_500);
											int vibrantDarkColor = palette
													.getDarkVibrantColor(R.color.primary_700);
											collapsingToolbarLayout
													.setContentScrimColor(vibrantColor);
											collapsingToolbarLayout
													.setStatusBarScrimColor(vibrantDarkColor);
										}
									});
							break;
						}
					}

					@Override
					public void onTabUnselected(TabLayout.Tab tab) {

					}

					@Override
					public void onTabReselected(TabLayout.Tab tab) {

					}
				});

	}

	private void setupViewPager(ViewPager viewPager) {
		ProductOverViewPagerAdapter adapter = new ProductOverViewPagerAdapter(
				getActivity().getSupportFragmentManager());
		Set<String> keys = GlobaDataHolder.getGlobaDataHolder().getProductMap()
				.keySet();

		for (String string : keys) {
			adapter.addFrag(new ProductListFragment(string), string);
		}

		viewPager.setAdapter(adapter);
//		viewPager.setPageTransformer(true,
//				Utils.currentPageTransformer(getActivity()));
	}
	
	
	
	// TODO
	//Below Code Work Well But requires JSOn to work
	// Below line of code does caching for offline usage
	
	
	/*void fillProductMapFromCache() {

		String cached_ProductMapJSON = PreferenceHelper
				.getPrefernceHelperInstace().getString(
						PreferenceHelper.ALL_PRODUCT_LIST_RESPONSE_JSON, null);

		if (null != cached_ProductMapJSON) {
			new JSONParser(NetworkConstants.GET_ALL_PRODUCT,
					cached_ProductMapJSON).parse();

			adapter.notifyDataSetChanged();

		}

	}

	public void fillCategoryData() {

		loadingIndicator.setVisibility(View.VISIBLE);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				NetworkConstants.URL_GET_PRODUCTS_MAP,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						if (getView() != null && getView().isShown()) {

							new JSONParser(NetworkConstants.GET_ALL_PRODUCT,
									response.toString()).parse();

							PreferenceHelper
									.getPrefernceHelperInstace()
									.setString(
											PreferenceHelper.ALL_PRODUCT_LIST_RESPONSE_JSON,
											response.toString());
							
							setUpPager();


							if (null != loadingIndicator) {
								loadingIndicator.setVisibility(View.GONE);
							}

						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						fillProductMapFromCache();


						if (null != loadingIndicator) {
							loadingIndicator.setVisibility(View.GONE);
						}
						if (error instanceof TimeoutError
								|| error instanceof NoConnectionError) {


							if (null != getActivity())
								((ECartHomeActivity) getActivity())
										.ShowErrorMessage(Errorhandler.OFFLINE_MODE, true);

						} else if (error instanceof AuthFailureError) {
							// TODO
						} else if (error instanceof ServerError) {

							
							if (null != getActivity())
								((ECartHomeActivity) getActivity())
										.ShowErrorMessage(Errorhandler.SERVER_ERROR, true);
							// TODO
						} else if (error instanceof NetworkError) {

							
							if (null != getActivity())
								((ECartHomeActivity) getActivity())
										.ShowErrorMessage(Errorhandler.NETWORK_ERROR, true);

						} else if (error instanceof ParseError) {

							if (null != getActivity())
								Toast.makeText(
										getActivity(),
										"Parsing Error" + error.networkResponse
												+ error.getLocalizedMessage(),
										Toast.LENGTH_LONG).show();

						}
					}

				}) {

		};

		// jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000 * 2, 0, 0));

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS
				.toMillis(60), DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		AppController.getInstance().addToRequestQueue(jsonObjReq, tagJSONReq);

	}
*/
}