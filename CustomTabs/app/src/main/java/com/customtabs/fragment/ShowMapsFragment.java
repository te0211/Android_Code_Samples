package com.customtabs.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customtabs.R;
import com.customtabs.utils.Const;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by tejoa on 20/05/2014..
 */
public class ShowMapsFragment extends Fragment implements OnMapReadyCallback {

    private static GoogleMap googleMap;
    private LatLng position;
    private MapFragment mapFragment;
    public Bundle args;
    private double latitude;
    private double longitude;
    private boolean searchNearMe;


    public ShowMapsFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        args = getArguments();

        // inflate the details fragment
        View mainView = inflater.inflate(R.layout.maps_layout, container, false);

        setUpMap();
        if (googleMap != null) {

            addMarkers();

            googleMap.getUiSettings().setZoomControlsEnabled(false);
        }

        return mainView;
    }


    private void setUpMap() {
        if (googleMap == null) {
            mapFragment = null;
            FragmentManager fm = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                fm = getFragmentManager();
            } else {
                fm = getChildFragmentManager();
            }
            mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void addMarkers() {
        if (googleMap != null) {
            latitude = Double.valueOf(args.getString(Const.LONGITUDE));
            longitude = Double.valueOf(args.getString(Const.LATITUDE));
            position = new LatLng(latitude, longitude);
            MarkerOptions marker = new MarkerOptions().position(position);
            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_pin_red));
            googleMap.addMarker(marker);
            if (searchNearMe) {
                googleMap.setMyLocationEnabled(true);
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (googleMap != null)
            addMarkers();

        if (googleMap == null) {
            setUpMap();
        }
    }

    // @Override
    // public void onClick(View arg0) {
    // // TODO Auto-generated method stub
    //
    // }

    /**
     * *
     * The mapfragment's id must be removed from the FragmentManager or else if the same it is passed on the next time then app
     * will crash
     * **
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapFragment != null) {
            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            ft.remove(mapFragment);
            ft.commit();
        }

        googleMap = null;

    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        addMarkers();

    }

}

