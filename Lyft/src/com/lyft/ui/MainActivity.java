package com.lyft.ui;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private MyLocationMapView mapView = null;
	private MapController mapController = null;
	
	private LocationClient locationClient = null;
	private LocationData locationData = null;
	
	private LyftLocationOverlay locationOverlay = null;

	private PopupOverlay popupOverlay = null;
	private TextView popupView = null;

	private boolean mannulRequest = false;
	private boolean automticRequest = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowHomeEnabled(false); 
		setContentView(R.layout.activity_main);
		
		mapView = (MyLocationMapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		mapController.setZoom((float) 14.0);
		mapController.enableClick(true);
		
		locationData = new LocationData();
		locationClient = new LocationClient(this);
		locationClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption locationClientOption = new LocationClientOption();
		locationClientOption.setOpenGps(true);
		locationClientOption.setCoorType("bd09ll");
		locationClientOption.setScanSpan(1000);
		locationClient.setLocOption(locationClientOption);
		locationClient.start();
		
		locationOverlay = new LyftLocationOverlay(mapView);
		locationOverlay.setData(locationData);
		mapView.getOverlays().add(locationOverlay);
		locationOverlay.enableCompass();
		mapView.refresh();
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locationData.latitude = location.getLatitude();
			locationData.longitude = location.getLongitude();
			locationData.accuracy = location.getRadius();
			locationData.direction = location.getDerect();

			locationOverlay.setData(locationData);
			mapView.refresh();
			
			if (mannulRequest || automticRequest) {
				mapController.animateTo(new GeoPoint((int)(locationData.latitude* 1e6), (int)(locationData.longitude *  1e6)));
				mannulRequest = false;
				locationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			automticRequest = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null){
				return ;
			}
		}
	}

	public class LyftLocationOverlay extends MyLocationOverlay{
		public LyftLocationOverlay(MapView mapView) {
			super(mapView);
		}
		protected boolean dispatchTap() {
			popupView.setBackgroundResource(R.drawable.popup);
			popupView.setText("My Location");
			popupOverlay.showPopup(BMapUtil.getBitmapFromView(popupView),
					new GeoPoint((int)(locationData.latitude*1e6), (int)(locationData.longitude*1e6)),
					8);
			return true;
		}
	}
	
	@Override
	protected void onDestroy() {
		if (locationClient != null) {
			locationClient.stop();
		}
		mapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
}

class MyLocationMapView extends MapView{
	static PopupOverlay popupOverlay = null;
	public MyLocationMapView(Context context) {
		super(context);
	}
	public MyLocationMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			if (popupOverlay != null && event.getAction() == MotionEvent.ACTION_UP)
				popupOverlay.hidePop();
		}
		return true;
	}
}
