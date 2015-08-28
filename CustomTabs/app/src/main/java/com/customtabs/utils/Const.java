package com.customtabs.utils;

import android.graphics.Bitmap;

/**
 * Created by tejoa on 20/05/2014.
 */
public class Const {

    private Const() {
    }

    // -------------------------------------------------
    // General
    // -------------------------------------------------
    public static final String LOG_TAG = "CUSTOM TABS";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String EXTRA_LOCATIONS_DETAILS = "location_details";
    // ===============================
    // Receipt Image Generation constants.
    // ===============================

    // Contains the image source pixel sample size used to reduce the size of a
    // receipt.
    public static final int RECEIPT_SOURCE_BITMAP_SAMPLE_SIZE = 2;
    // Contains the receipt output compression quality (value from 1 to 100).
    public static final int RECEIPT_COMPRESS_BITMAP_QUALITY = 90;
    // Contains the receipt output compression format.
    public static final Bitmap.CompressFormat RECEIPT_COMPRESS_BITMAP_FORMAT = Bitmap.CompressFormat.JPEG;

}

