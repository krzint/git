package umik.mygps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MyGps extends Activity implements
		android.view.View.OnClickListener, OnClickListener, LocationListener {

	private Button buttonStartLocation;
	private EditText editTextShowLocation;
	private LocationManager locManager;
	private boolean gps_enabled = false;
	private boolean internet_enabled = false;
	private int google_enabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygps);
		buttonStartLocation = (Button) findViewById(R.id.buttonStartLocation);
		buttonStartLocation.setOnClickListener(this);
		editTextShowLocation = (EditText) findViewById(R.id.editTextShowLocation);
		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.buttonStartLocation:
			buttonStartLocation.setActivated(false);
			try {
				google_enabled = GooglePlayServicesUtil
						.isGooglePlayServicesAvailable(getApplicationContext());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				gps_enabled = locManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
			} catch (Exception e) {
			}
			try {
				internet_enabled = locManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			} catch (Exception e) {
			}

			if (google_enabled == ConnectionResult.SUCCESS) {
				if (!gps_enabled & !internet_enabled) {
					AlertDialog.Builder builder = new Builder(this);
					builder.setTitle("Attention!");
					builder.setMessage("Sorry, location is not determined. Please enable location providers");
					builder.setPositiveButton("OK", this);
					builder.setNeutralButton("Cancel", this);
					builder.create().show();

				}
				if (gps_enabled) {
					locManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 0, 0, this);
				}
				if (internet_enabled) {
					locManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0, this);
				}
			} else {
				GooglePlayServicesUtil.getErrorDialog(google_enabled, this, 1);
			}

			break;

		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		if (which == DialogInterface.BUTTON_NEUTRAL) {
			editTextShowLocation
					.setText("Sorry, location is not determined. To fix this please enable location providers");
		} else if (which == DialogInterface.BUTTON_POSITIVE) {
			startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			locManager.removeUpdates(this);
			double[] loc = new double[2];

			double lat = location.getLatitude();
			double lon = location.getLongitude();
			loc[0] = lat;
			loc[1] = lon;
			Log.i("lokalizacja",
					String.valueOf(loc[0]) + String.valueOf(loc[1]));
			Intent showResult = new Intent(this, ShowResult.class);
			showResult.putExtra("Location", loc);
			buttonStartLocation.setActivated(true);
			startActivity(showResult);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
