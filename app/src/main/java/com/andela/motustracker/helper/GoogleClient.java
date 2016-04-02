package com.andela.motustracker.helper;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Spykins on 31/03/16.
 *
 */
public class GoogleClient {

    private static GoogleApiClient mGoogleApiClient ;
    private static GoogleClient googleClient;
    private static Context context;
    private static GoogleApiClient.ConnectionCallbacks listener;
    private static GoogleApiClient.OnConnectionFailedListener secondListener;

    private GoogleClient(Context context, GoogleApiClient.ConnectionCallbacks listener,
                         GoogleApiClient.OnConnectionFailedListener secondListener) {
        this(context);
        GoogleClient.secondListener = secondListener;
        GoogleClient.listener = listener;
    }
    private GoogleClient(Context context) {
        GoogleClient.context = context;
    }

    public static GoogleClient getInstance(Context context, GoogleApiClient.ConnectionCallbacks listener,
                                           GoogleApiClient.OnConnectionFailedListener secondListener) {
       return (googleClient == null) ? new GoogleClient(context,listener,secondListener) : googleClient;
    }

    public static GoogleApiClient getGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(listener)
                    .addOnConnectionFailedListener(secondListener)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
            return mGoogleApiClient;
        }
       return mGoogleApiClient;
    }
}
