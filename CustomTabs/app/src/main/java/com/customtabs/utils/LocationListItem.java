package com.customtabs.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.customtabs.R;
import com.customtabs.model.Location;
import com.customtabs.view.ListItem;

import java.io.Serializable;

/**
 * Created by tejoa on 20/05/2014.
 */
public class LocationListItem extends ListItem implements Serializable {

    /**
     * generated version id
     */
    private static final long serialVersionUID = 545199248045235189L;

    private static final String CLS_TAG = LocationListItem.class.getSimpleName();


    private Location location;

    public LocationListItem(Location location) {
        this.location = location;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public View buildView(final Context context, View convertView, ViewGroup parent) {
        View locationlView = null;
        LayoutInflater inflater = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            locationlView = inflater.inflate(R.layout.locations_result_row, null);
        } else {
            locationlView = convertView;
        }
        // Set the vendor image, or hide it and set the vendor name.
        ImageView thumbNailImg = (ImageView) locationlView.findViewById(R.id.image_id);
        if (thumbNailImg != null) {
            thumbNailImg.setVisibility(View.VISIBLE);
        } else {
            Log.e(Const.LOG_TAG, CLS_TAG + ".getView: can't locate image view!");
        }
        //TO-DO add images
//        if (location.imagePairs != null && location.imagePairs.size() > 0 && location.imagePairs.get(0).thumbnail != null) {
//
//            // Set the list item tag to the uri, this tag value is used in 'ListItemAdapter.refreshView'
//            // to refresh the appropriate view items once images have been loaded.
//            URI uri = URI.create(location.imagePairs.get(0).thumbnail);
//            this.listItemTag = uri;
//            // Attempt to load the image from the image cache, if not there, then the
//            // ImageCache will load it asynchronously and this view will be updated via
//            // the ImageCache broadcast receiver available in BaseActivity.
//            ImageCache imgCache = ImageCache.getInstance(context);
//            Bitmap bitmap = imgCache.getBitmap(uri, null);
//            if (bitmap != null) {
//                thumbNailImg.setImageBitmap(bitmap);
//            } else {
//                thumbNailImg.setImageResource(R.drawable.hotel_results_default_image);
//            }
//        } else {
//            thumbNailImg.setImageResource(R.drawable.hotel_results_default_image);
//        }
        // Set the location name.
        TextView txtView = (TextView) locationlView.findViewById(R.id.location_name);
        if (txtView != null) {
            if (location.name != null) {
                txtView.setText(location.name);
            } else {
                txtView.setText("");
            }
        } else {
            Log.e(Const.LOG_TAG, CLS_TAG + ".getView: unable to locate location name text view!");
        }
        // Set the location desc.
        txtView = (TextView) locationlView.findViewById(R.id.location_desc);
        if (txtView != null) {
            if (location.description != null) {
                txtView.setText(location.name);
            } else {
                txtView.setText("");
            }
        } else {
            Log.e(Const.LOG_TAG, CLS_TAG + ".getView: unable to locate location desc text view!");
        }

        return locationlView;
    }
}
