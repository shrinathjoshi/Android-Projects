package com.project.arcstudio.cakesinfinite.view.activities;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.arcstudio.cakesinfinite.R;
import com.project.arcstudio.cakesinfinite.model.GlobaDataHolder;
import com.project.arcstudio.cakesinfinite.model.entities.Money;
import com.project.arcstudio.cakesinfinite.model.entities.Product;
import com.project.arcstudio.cakesinfinite.util.Utils;
import com.project.arcstudio.cakesinfinite.view.fragments.ProductOverviewFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.AboutUsFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.BiscuitsFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.BlogFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.BrowniesFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.ContactUsFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.CupCakesFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.CustomCakesFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.DeliveryPolicyFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.DoughnutsFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.ExpressDeliveryFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.FondantCakesFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.IceCreamCakesFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.PrivacyPolicyFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.SweetsFragment;
import com.project.arcstudio.cakesinfinite.view.fragments.navigationFragments.TermsAndConditionsFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_Fondant_Cakes = "fondant_cakes";
    private static final String TAG_Custom_Cakes = "custom_cakes";
    private static final String TAG_Express_Delivery = "express_delivery";
    private static final String TAG_Ice_Cream_Cakes = "ice_cream_cakes";
    private static final String TAG_Cup_Cakes = "cup_cakes";
    private static final String TAG_Sweets = "sweets";
    private static final String TAG_Doughnuts = "doughnuts";
    private static final String TAG_Biscuits = "biscuits";
    private static final String TAG_Brownies = "brownies";
    private static final String TAG_Blog = "blog";
    private static final String TAG_About_Us = "about_us";
    private static final String TAG_Delivery_Policy = "delivery_policy";
    private static final String TAG_Privacy_Policy = "privacy_policy";
    private static final String TAG_Terms_And_Conditions = "terms_and_conditions";
    private static final String TAG_Contact_Us = "contact_us";

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private int itemCount = 0;
    private BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
    private TextView checkOutAmount, itemCountTextView;
    private AVLoadingIndicatorView progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        itemCountTextView = (TextView) findViewById(R.id.item_count);
        itemCountTextView.setSelected(true);
        itemCountTextView.setText(String.valueOf(itemCount));

        checkOutAmount = (TextView) findViewById(R.id.checkout_amount);
        checkOutAmount.setSelected(true);
        checkOutAmount.setText(Money.rupees(checkoutAmount).toString());

//        fab = (FloatingActionButton) findViewById(R.id.fab);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);

        checkOutAmount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Utils.vibrate(getApplicationContext());

                Utils.switchContent(R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG, MainActivity.this,
                        Utils.AnimationType.SLIDE_UP);

            }
        });

        if (itemCount != 0) {
            for (Product product : GlobaDataHolder.getGlobaDataHolder()
                    .getShoppingList()) {

                updateCheckOutAmount(
                        BigDecimal.valueOf(Long.valueOf(product.getSellMRP())),
                        true);
            }
        }

        findViewById(R.id.item_counter).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

//                        Utils.vibrate(getApplicationContext());
                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                MainActivity.this, Utils.AnimationType.SLIDE_UP);

                    }
                });

        findViewById(R.id.checkout_envelop).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

//                        Utils.vibrate(getApplicationContext());

                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                MainActivity.this, Utils.AnimationType.SLIDE_UP);

                    }
                });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }



    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
//            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
//                HomeFragment homeFragment = new HomeFragment();
//                return homeFragment;
                Utils.switchFragmentWithAnimation(
                        R.id.frag_container,
                        new ProductOverviewFragment(),
                        (this), null,
                        Utils.AnimationType.SLIDE_LEFT);
            case 1:
                // Fondant Cakes
                FondantCakesFragment fondantCakesFragment = new FondantCakesFragment();
                return fondantCakesFragment;
            case 2:
                // Custom Cakes
                CustomCakesFragment customCakesFragment = new CustomCakesFragment();
                return customCakesFragment;
            case 3:
                // Express Delivery
                ExpressDeliveryFragment expressDeliveryFragment = new ExpressDeliveryFragment();
                return expressDeliveryFragment;

            case 4:
                // Ice Cream Cakes
                IceCreamCakesFragment iceCreamCakesFragment = new IceCreamCakesFragment();
                return iceCreamCakesFragment;

            case 5:
                // Cup Cakes
                CupCakesFragment cupCakesFragment = new CupCakesFragment();
                return cupCakesFragment;

            case 6:
                // Sweets
                SweetsFragment sweetsFragment = new SweetsFragment();
                return sweetsFragment;

            case 7:
                // Doughnuts
                DoughnutsFragment doughnutsFragment = new DoughnutsFragment();
                return doughnutsFragment;

            case 8:
                // Biscuits
                BiscuitsFragment biscuitsFragment = new BiscuitsFragment();
                return biscuitsFragment;

            case 9:
                // Brownies
                BrowniesFragment browniesFragment = new BrowniesFragment();
                return browniesFragment;

            case 10:
                // Blog
                BlogFragment blogFragment = new BlogFragment();
                return blogFragment;

            case 11:
                // About Us
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                return aboutUsFragment;

            case 12:
                // Delivery Policy
                DeliveryPolicyFragment deliveryPolicyFragment = new DeliveryPolicyFragment();
                return deliveryPolicyFragment;

            case 13:
                // Privacy Policy
                PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
                return privacyPolicyFragment;

            case 14:
                // Terms &amp; Conditions
                TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment();
                return termsAndConditionsFragment;
            case 15:

                // Contact Us
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                return contactUsFragment;

            default:
                return new ProductOverviewFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_fondant_cakes:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_Fondant_Cakes;
                        break;
                    case R.id.nav_custom_cakes:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_Custom_Cakes;
                        break;
                    case R.id.nav_express_delivery:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_Express_Delivery;
                        break;
                    case R.id.nav_ice_cream_cakes:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_Ice_Cream_Cakes;
                        break;
                    case R.id.nav_cup_cakes:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_Cup_Cakes;
                        break;
                    case R.id.nav_sweets:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_Sweets;
                        break;
                    case R.id.nav_doughnuts:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_Doughnuts;
                        break;
                    case R.id.nav_biscuits:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_Biscuits;
                        break;
                    case R.id.nav_brownies:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_Brownies;
                        break;
                    case R.id.nav_blog:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_delivery_policy:
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_terms_and_conditions:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_contact_us:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateItemCount(boolean ifIncrement) {
        if (ifIncrement) {
            itemCount++;
            itemCountTextView.setText(String.valueOf(itemCount));

        } else {
            itemCountTextView.setText(String.valueOf(itemCount <= 0 ? 0
                    : --itemCount));
        }
    }

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            checkoutAmount = checkoutAmount.add(amount);
        } else {
            if (checkoutAmount.signum() == 1)
                checkoutAmount = checkoutAmount.subtract(amount);
        }

        checkOutAmount.setText(Money.rupees(checkoutAmount).toString());
    }

    public int getItemCount() {
        return itemCount;
    }

    public DrawerLayout getmDrawerLayout() {
        return drawer;
    }

    public AVLoadingIndicatorView getProgressBar() {
        return progressBar;
    }


    // show or hide the fab
//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }
}
