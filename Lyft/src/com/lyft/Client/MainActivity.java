package com.lyft.Client;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lyft.Client.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Bitmap;

public class MainActivity extends Activity {

	private BMapManager mapManager = null;
	private MapView mapView = null;
	private MapController mapController = null;

	private LocationClient locationClient = null;
	private LocationData locationData = null;

	private boolean firstRequest = true;

	private LyftLocationOverlay locationOverlay = null;

	private PopupOverlay popupOverlay = null;
	private TextView popupView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 

		mapManager = new BMapManager(getApplication());
		mapManager.init(null);

		setContentView(R.layout.activity_main);

		mapView = (MapView) findViewById(R.id.mapView);
		mapController = mapView.getController();
		mapController.setZoom((float) 12.0);
		mapController.enableClick(true);
		mapController.setOverlookingGesturesEnabled(false);

//		GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6)); 
//		mapController.setCenter(point);

		locationData = new LocationData();
		locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);
		locationClientOption.setCoorType("bd09ll");
		locationClientOption.setScanSpan(10);
		locationClient.setLocOption(locationClientOption);
		locationClient.start();
		
		locationClient.requestLocation();
		
		locationOverlay = new LyftLocationOverlay(mapView);
		locationOverlay.setData(locationData);
		locationOverlay.enableCompass();
		mapView.getOverlays().add(locationOverlay);
		mapView.refresh();
		
		ImageButton locateButton = (ImageButton) findViewById(R.id.locate_button);
		locateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (firstRequest == false) {
					mapController.animateTo(new GeoPoint((int)(locationData.latitude* 1e6), 
							(int)(locationData.longitude *  1e6)));
				}
			}
		});
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			locationData.latitude = location.getLatitude();
			locationData.longitude = location.getLongitude();
			locationData.accuracy = location.getRadius();
			locationData.direction = location.getDerect();
			//System.out.println(location.getLatitude() + ", " + location.getLongitude());

			locationOverlay.setData(locationData);
			mapView.refresh();

			if (firstRequest) {
				mapController.animateTo(new GeoPoint((int)(locationData.latitude* 1e6), 
						(int)(locationData.longitude *  1e6)));
				firstRequest = false;
				//locationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ;
			}
		}
	}

	public class LyftLocationOverlay extends MyLocationOverlay {
		public LyftLocationOverlay(MapView mapView) {
			super(mapView);
		}
		protected boolean dispatchTap() {
			popupView.setBackgroundResource(R.drawable.popup);
			popupView.setText("My Location");
			popupOverlay.showPopup(getBitmapFromView(popupView),
					new GeoPoint((int)(locationData.latitude*1e6), (int)(locationData.longitude*1e6)),
					8);
			return true;
		}
	}

	private Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	@Override
	protected void onDestroy() {
		if (mapView != null) {
			mapView.destroy();
			mapView = null;
		}
		if (mapManager != null) {
			mapManager.destroy();
			mapManager = null;
		}
		if (locationClient != null) {
			locationClient.stop();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (mapManager != null) {
			mapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		if (mapManager != null) {
			mapManager.start();
		}
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);

	}
}
