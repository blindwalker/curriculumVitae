package at.kropf.curriculumvitae.augmented;


import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView.CaptureScreenCallback;
import com.wikitude.architect.ArchitectView.SensorAccuracyChangeListener;
import com.wikitude.architect.StartupConfiguration.CameraPosition;

import java.io.File;
import java.io.FileOutputStream;

import at.kropf.curriculumvitae.R;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SampleCamActivity extends AbstractArchitectCamActivity {

	/**
	 * last time the calibration toast was shown, this avoids too many toast shown when compass needs calibration
	 */
	private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();

	public static final String EXTRAS_KEY_ACTIVITY_TITLE_STRING = "activityTitle";
	public static final String EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL = "activityArchitectWorldUrl";

	public static final String EXTRAS_KEY_ACTIVITY_IR = "activityIr";
	public static final String EXTRAS_KEY_ACTIVITY_GEO = "activityGeo";

	public static final String EXTRAS_KEY_ACTIVITIES_ARCHITECT_WORLD_URLS_ARRAY = "activitiesArchitectWorldUrls";
	public static final String EXTRAS_KEY_ACTIVITIES_TILES_ARRAY = "activitiesTitles";
	public static final String EXTRAS_KEY_ACTIVITIES_CLASSNAMES_ARRAY = "activitiesClassnames";

	public static final String EXTRAS_KEY_ACTIVITIES_IR_ARRAY = "activitiesIr";
	public static final String EXTRAS_KEY_ACTIVITIES_GEO_ARRAY = "activitiesGeo";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setElevation(0);

		Crouton crouton = Crouton.makeText(this, getResources().getString(R.string.ar_hint), Style.CONFIRM)
				.setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_LONG).build());

		crouton.show();
	}

	@Override
	public String getARchitectWorldPath() {
		return getIntent().getExtras().getString(
				EXTRAS_KEY_ACTIVITY_ARCHITECT_WORLD_URL);
	}

	@Override
	public String getActivityTitle() {
		return (getIntent().getExtras() != null && getIntent().getExtras().get(
				EXTRAS_KEY_ACTIVITY_TITLE_STRING) != null) ? getIntent()
				.getExtras().getString(EXTRAS_KEY_ACTIVITY_TITLE_STRING)
				: "Test-World";
	}

	@Override
	public int getContentViewId() {
		return R.layout.sample_cam;
	}

	@Override
	public int getArchitectViewId() {
		return R.id.architectView;
	}
	
	@Override
	public String getWikitudeSDKLicenseKey() {
		return WikitudeSDKConstants.WIKITUDE_SDK_KEY;
	}
	
	@Override
	public SensorAccuracyChangeListener getSensorAccuracyListener() {
		return new SensorAccuracyChangeListener() {
			@Override
			public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
				if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && SampleCamActivity.this != null && !SampleCamActivity.this.isFinishing() && System.currentTimeMillis() - SampleCamActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
					Toast.makeText(SampleCamActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG).show();
					SampleCamActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
				}
			}
		};
	}

	@Override
	public ArchitectUrlListener getUrlListener() {
		return new ArchitectUrlListener() {

			@Override
			public boolean urlWasInvoked(String uriString) {
				Uri invokedUri = Uri.parse(uriString);
				
				// pressed snapshot button. check if host is button to fetch e.g. 'architectsdk://button?action=captureScreen', you may add more checks if more buttons are used inside AR scene
				if ("button".equalsIgnoreCase(invokedUri.getHost())) {
					SampleCamActivity.this.architectView.captureScreen(ArchitectView.CaptureScreenCallback.CAPTURE_MODE_CAM_AND_WEBVIEW, new CaptureScreenCallback() {
						
						@Override
						public void onScreenCaptured(final Bitmap screenCapture) {
                            Toast.makeText(SampleCamActivity.this, "Earned Credits", Toast.LENGTH_SHORT).show();
						}
					});
				}
				return true;
			}
		};
	}

	@Override
	public ILocationProvider getLocationProvider(final LocationListener locationListener) {
		return new LocationProvider(this, locationListener);
	}
	
	@Override
	public float getInitialCullingDistanceMeters() {
		// you need to adjust this in case your POIs are more than 50km away from user here while loading or in JS code (compare 'AR.context.scene.cullingDistance')
		return ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS;
	}

	@Override
	protected boolean hasGeo() {
		return getIntent().getExtras().getBoolean(
				EXTRAS_KEY_ACTIVITY_GEO);
	}

	@Override
	protected boolean hasIR() {
		return getIntent().getExtras().getBoolean(
				EXTRAS_KEY_ACTIVITY_IR);
	}

	@Override
	protected CameraPosition getCameraPosition() {
		return CameraPosition.DEFAULT;
	}
}
