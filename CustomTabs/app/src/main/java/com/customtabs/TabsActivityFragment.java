package com.customtabs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.customtabs.model.ImagePair;
import com.customtabs.model.Location;
import com.customtabs.utils.Const;
import com.customtabs.utils.ImageCache;
import com.customtabs.utils.LocationListItem;
import com.customtabs.view.LoaderImageView;
import com.customtabs.view.ParallaxScollView;

import java.net.URI;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabsActivityFragment extends Fragment implements TabHost.OnTabChangeListener {
    public static final String CLS_TAG = TabsActivityFragment.class.getSimpleName();

    public static final String TAB_DETAILS = "DETAILS";
    public static final String TAB_ATTRACTIONS = "ATTRACTIONS";
    public static final String TAB_IMAGES = "IMAGES";
    private LocationListItem locationListItem;
    private TabFragmentListener callBackListener;

    private View mRoot;
    public TabHost mTabHost;
    public int mCurrentTab;
    public ParallaxScollView mListView;
    private ImageView mImageView;
    private Bitmap bitmap;

    // empty constructor
    public TabsActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callBackListener = (TabFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TabFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mTabHost.setOnTabChangedListener(this);
        mCurrentTab = 1;
        mTabHost.setCurrentTab(mCurrentTab);
        Log.d(Const.LOG_TAG, " ***** TabsActivityFragment, in onSaveInstanceState *****  ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // inflate the details fragment
        mRoot = inflater.inflate(R.layout.location_choice_list_item, container, false);
        Intent i = getActivity().getIntent();
        final Bundle bundle = i.getExtras();
        // hotel = new HotelSearchResultListItem();
//        hotelListItem = (HotelSearchResultListItem) bundle.getSerializable(Const.EXTRA_HOTELS_DETAILS);

        // adding parallax view for the image header
        mListView = (ParallaxScollView) mRoot.findViewById(R.id.layout_listview);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.choice_header, null);
        mImageView = (ImageView) header.findViewById(R.id.travelCityscape);
        mImageView.setVisibility(View.GONE);


        Activity activity = getActivity();


        try {
            showHideHomeImage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        if (mImageView.getVisibility() == View.VISIBLE) {
            mListView.setParallaxImageView(mImageView);
            header.setMinimumWidth(screenHeight / 4);

        }
        mListView.addHeaderView(header);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_expandable_list_item_1, new String[]{});
        mListView.setAdapter(adapter);

        setActionBar();
        setupTabs();

        return mRoot;
    }

    private void setActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setTitle("Overview");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        Log.d(Const.LOG_TAG, " ***** TabsActivityFragment, in onSaveInstanceState *****  ");
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        Log.d(Const.LOG_TAG, " ***** TabsActivityFragment, in onPause *****  ");

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mListView.requestFocus();

    }

    private void setupTabs() {
        mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);

        mTabHost.addTab(newTab(TAB_DETAILS, R.string.location_tab_details, R.id.tab_details));
        mTabHost.addTab(newTab(TAB_ATTRACTIONS, R.string.location_tab_attractions, R.id.tab_attractions));
        mTabHost.addTab(newTab(TAB_IMAGES, R.string.location_tab_images, R.id.tab_images));
    }

    private TabHost.TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(CLS_TAG, "buildTab(): tag=" + tag);

        View indicator = LayoutInflater.from(getActivity())
                .inflate(R.layout.tab, (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        TextView tv = ((TextView) indicator.findViewById(R.id.text));
        tv.setText(labelId);
        tv.setAllCaps(true);
        // setting tabs width to 1/3
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        indicator.setMinimumWidth(screenWidth / 3);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(indicator);
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    // Container Activity must implement this call back interface
    public interface TabFragmentListener {

        public void updateTab(String tabId, int placeholder);

        public void onImageClicked(View v, int id);

        public void onFindRoomsClicked();

        public void roomItemClicked(LocationListItem roomListItem);

        public void onMapsClicked();//LatLng post

        public void setHeaderImageURL(String headerImageURL);

    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(CLS_TAG, "onTabChanged(): tabId=" + tabId);
        if (TAB_DETAILS.equals(tabId)) {
            callBackListener.updateTab(tabId, R.id.tab_details);
            mCurrentTab = 0;
            return;
        }
        if (TAB_ATTRACTIONS.equals(tabId)) {
            callBackListener.updateTab(tabId, R.id.tab_attractions);
            mCurrentTab = 1;
            return;
        }
        if (TAB_IMAGES.equals(tabId)) {
            callBackListener.updateTab(tabId, R.id.tab_images);
            mCurrentTab = 2;
            return;
        }

    }

    /**
     * Show or Hide Home Image based on orientation change.
     */
    private void showHideHomeImage() throws InterruptedException {
        // Setup the cityscape image switcher and put the placeholder image in place
        Location location = locationListItem.getLocation();
        List<ImagePair> imagePairs = (location != null ? location.imagePairs : null);
        int orientation = getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                if (mImageView != null && imagePairs != null && imagePairs.size() > 0) {
                    ImagePair image2 = null;
                    bitmap = null;
                    image2 = imagePairs.get(0);
                    String Url = image2.image;
                    URI uri = URI.create(image2.image);
                    ImageCache imgCache = ImageCache.getInstance(getActivity());

                    // set the image uri in the activity associated with this fragment
                    callBackListener.setHeaderImageURL(Url);
                    bitmap = imgCache.getBitmapFromCache(uri);
                    if (bitmap == null) {
                        new LoaderImageView(getActivity(), Url, mImageView, uri);
                        mListView.requestFocus();

                    } else {
                        mImageView.setImageBitmap(bitmap);
                        mImageView.setVisibility(View.VISIBLE);
                    }

                } else {
                    mImageView.setVisibility(View.GONE);

                }

                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                mImageView.setVisibility(View.GONE);
                break;
        }
    }

}
