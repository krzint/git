package umik.mygps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowResult extends Activity {
	private TextView text;
	private GoogleMap mMap;
	private LatLng location;
	GoogleMapOptions googleOptions = new GoogleMapOptions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_result_1);
		googleOptions.mapType(GoogleMap.MAP_TYPE_SATELLITE)
				.compassEnabled(true);
		MapFragment.newInstance(googleOptions);
		// text = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		double[] loc = intent.getDoubleArrayExtra("Location");
		location = new LatLng(loc[0], loc[1]);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.addMarker(new MarkerOptions().position(location));

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(location).zoom(17).build();
		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_result, menu);
		return true;
	}

}
