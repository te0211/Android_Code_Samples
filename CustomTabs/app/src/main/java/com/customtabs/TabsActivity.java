package com.customtabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.customtabs.fragment.AttractionDetailFragment;
import com.customtabs.fragment.ImagesFragment;
import com.customtabs.fragment.LocationDetailsFragment;
import com.customtabs.fragment.ShowMapsFragment;
import com.customtabs.model.Location;
import com.customtabs.utils.Const;
import com.customtabs.utils.LocationListItem;
import com.customtabs.view.ParallaxScollView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabsActivity extends Activity implements TabsActivityFragment.TabFragmentListener, OnMapReadyCallback {

    public static final String CLS_TAG = TabsActivity.class.getSimpleName();
    public static final String FRAGMENT_MAP = "FRAGMENT_MAP";
    public static final String FRAGMENT_LOCATION_DETAILS = "FRAGMENT_LOCATION_DETAILS";
    public static final String LONDON_LATITUDE = "51.5072";
    public static final String LONDON_LONGITUDE = "0.1275";
    public static final String TAB_DETAILS = "DETAILS";
    public static final String TAB_ATTRACTIONS = "ATTRACTIONS";
    public static final String TAB_IMAGES = "IMAGES";
    private ShowMapsFragment mapsFragment;
    private LocationListItem locationListItem;
    private Location location;
    private TabsActivityFragment locationDetailsFrag;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
//        Intent i = this.getIntent();
//        final Bundle bundle = i.getExtras();
        //  locationListItem = (LocationListItem) bundle.getSerializable(Const.EXTRA_LOCATIONS_DETAILS);
        location = new Location();
        location.setLatitude(Double.valueOf(LONDON_LATITUDE));
        location.setLongitude(Double.valueOf(LONDON_LONGITUDE));
        location.setName("London");
        location.setDescription("The capital and most populous city of England and the United Kingdom.Standing on the River Thames.");

        locationDetailsFrag = (TabsActivityFragment) getFragmentManager().findFragmentByTag(FRAGMENT_LOCATION_DETAILS);
        if (locationDetailsFrag == null) {
            locationDetailsFrag = new TabsActivityFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.tabcontainer, locationDetailsFrag, FRAGMENT_LOCATION_DETAILS);
            ft.commit();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && locationDetailsFrag != null && locationDetailsFrag.mListView != null) {
            locationDetailsFrag.mListView.setViewsBounds(ParallaxScollView.ZOOM_X2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.map) {
            onMapsClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTab(String tabId, int placeholder) {
        FragmentManager fm = locationDetailsFrag.getFragmentManager();
        if (fm.findFragmentByTag(tabId) == null) {
            if (TAB_DETAILS.equals(tabId)) {
                fm.beginTransaction().replace(placeholder, new LocationDetailsFragment(), tabId).commit();
            }
            if (TAB_ATTRACTIONS.equals(tabId)) {
                AttractionDetailFragment detailFrag = new AttractionDetailFragment();
                //new AttractionDetailFragment(location.attractions);
                fm.beginTransaction().replace(placeholder, detailFrag, tabId).commit();
            }
            if (TAB_IMAGES.equals(tabId)) {
                fm.beginTransaction().replace(placeholder, new ImagesFragment(), tabId).commit();
            }
        }
    }

    @Override
    public void onImageClicked(View v, int id) {

    }

    @Override
    public void onFindRoomsClicked() {

    }

    @Override
    public void roomItemClicked(LocationListItem roomListItem) {

    }

    public void onMapsClicked() {
        mapsFragment = (ShowMapsFragment) getFragmentManager().findFragmentByTag(FRAGMENT_MAP);
        //  LatLng post = new LatLng(hotel.latitude, hotel.longitude);
        if (mapsFragment == null) {
            mapsFragment = new ShowMapsFragment();

        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (!mapsFragment.isVisible()) {
            Bundle args = new Bundle();
            args.putString(Const.LATITUDE, LONDON_LATITUDE);
            args.putString(Const.LONGITUDE, LONDON_LONGITUDE);
            mapsFragment.setArguments(args);

            ft.hide(locationDetailsFrag);

            ft.add(R.id.tabcontainer, mapsFragment, FRAGMENT_MAP);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            ft.hide(mapsFragment);
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void setHeaderImageURL(String headerImageURL) {

    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.getUiSettings().setZoomControlsEnabled(false);
        LatLng position = new LatLng(location.latitude, location.longitude);
        MarkerOptions marker = new MarkerOptions().position(position);
        map.addMarker(marker);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

}
