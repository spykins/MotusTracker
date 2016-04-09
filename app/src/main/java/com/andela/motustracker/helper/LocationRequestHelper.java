package com.andela.motustracker.helper;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by Spykins on 31/03/16.
 */
public class LocationRequestHelper {
    private PendingResult<LocationSettingsResult> result;
    private boolean requestingLocationUpdates;
    private GoogleApiClient googleApiClient;

    public LocationRequestHelper(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;
    }
    public LocationRequest createLocationRequest() {
        //create the location request and set the parameters as shown in this code sample
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //get the current location settings of a user's device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        //Next check whether the current location settings are satisfied:
        //if(GoogleClient.getGoogleApiClient() != null)
        result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.SUCCESS: ..");

                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        //*****************save this in system preference
                        requestingLocationUpdates = true;
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.RESOLUTION_REQUIRED: ..");

                        //
                        Intent intent = new Intent();
                        intent.setAction("com.andela.motustracker.LOCATION_SETTINGS");
                        intent.putExtra("status", status);
                        AppContext.get().sendBroadcast(intent);
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        //This has been copied to MotusService displaySettings method..use callback
                        //*****************save this in system preference
                        requestingLocationUpdates = false;
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.RESOLUTION_REQUIRED: ..");
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        requestingLocationUpdates = false;
                        break;
                }


            }
        });
        return mLocationRequest;
    }

    //vhanging user Location settings

  /*  public boolean checkUserLocationSettings() {

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.SUCCESS: ..");

                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        /*//*****************save this in system preference
                        requestingLocationUpdates = true;
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.RESOLUTION_REQUIRED: ..");

                        //
                        Intent intent = new Intent();
                        intent.setAction("com.andela.motustracker.LOCATION_SETTINGS");
                        intent.putExtra("status", status);
                        AppContext.get().sendBroadcast(intent);
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        //This has been copied to MotusService displaySettings method..use callback
                        /*//*****************save this in system preference
                        requestingLocationUpdates = false;
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("waleola","Called checkUserLocationSettings*** LocationSettingsStatusCodes.RESOLUTION_REQUIRED: ..");
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        requestingLocationUpdates = false;
                        break;
                }


            }
        });

        return requestingLocationUpdates;
    }
*/

}
