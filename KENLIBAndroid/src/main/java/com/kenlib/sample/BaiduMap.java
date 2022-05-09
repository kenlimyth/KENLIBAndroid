package com.kenlib.sample;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.SDKInitializer;
//import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
//import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.InfoWindow;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.Marker;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.MyLocationConfiguration;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.map.OverlayOptions;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.overlayutil.PoiOverlay;

import android.app.Activity;
        import android.os.Bundle;
        import android.widget.RadioGroup.OnCheckedChangeListener;
        import android.widget.Button;

public class BaiduMap extends Activity {
//	MapView mMapView = null;
//	com.baidu.mapapi.map.BaiduMap mBaiduMap = null;
//	private InfoWindow mInfoWindow;
//	// 定位相关
//	LocationClient mLocClient;
//	public MyLocationListenner myListener = new MyLocationListenner();
//	private LocationMode mCurrentMode;
//	BitmapDescriptor mCurrentMarker;

	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
//		SDKInitializer.initialize(getApplicationContext());
//		setContentView(R.layout.sign_list);
//		// 获取地图控件引用
//		mMapView = (MapView) findViewById(R.id.bmapView);
//
//		mBaiduMap = mMapView.getMap();
//
//		// 开启定位图层
//		mBaiduMap.setMyLocationEnabled(true);
//		// 定位初始化
//		mLocClient = new LocationClient(this);
//		mLocClient.registerLocationListener(myListener);
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);// 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setScanSpan(1000);
//		mLocClient.setLocOption(option);
//		mLocClient.start();
//
//		 //定义Maker坐标点
//		 LatLng point = new LatLng(39.963175, 116.400244);
//		 //构建Marker图标
//		 BitmapDescriptor bitmap = BitmapDescriptorFactory
//		 .fromResource(R.drawable.ic_launcher);
//		 //构建MarkerOption，用于在地图上添加Marker
//		 OverlayOptions overlayOptions = new MarkerOptions()
//		 .position(point)
//		 .icon(bitmap);
//		 //在地图上添加Marker，并显示
//		 Marker marker=(Marker)mBaiduMap.addOverlay(overlayOptions);
		//

		//
		//
//		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//
//			@Override
//			public boolean onMarkerClick(Marker arg0) {
//				// TODO Auto-generated method stub
//
//				// 创建InfoWindow展示的view
//				Button button = new Button(getApplicationContext());
//				button.setBackgroundResource(R.drawable.ic_launcher);
//				// 定义用于显示该InfoWindow的坐标点
//				// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//				InfoWindow mInfoWindow = new InfoWindow(button, arg0.getPosition(), -47);
//				// 显示InfoWindow
//				mBaiduMap.showInfoWindow(mInfoWindow);
//				
//			
//				
//				return false;
//			}
//		});
		//

	}

	/**
	 * 定位SDK监听函数
	 */
//	public class MyLocationListenner implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			// map view 销毁后不在处理新接收的位置
//			if (location == null || mMapView == null)
//				return;
//			MyLocationData locData = new MyLocationData.Builder()
//					.accuracy(location.getRadius())
//					// 此处设置开发者获取到的方向信息，顺时针0-360
//					.direction(100).latitude(location.getLatitude())
//					.longitude(location.getLongitude()).build();
//			mBaiduMap.setMyLocationData(locData);
//			if (isFirstLoc) {
//				isFirstLoc = false;
//				LatLng ll = new LatLng(location.getLatitude(),
//						location.getLongitude());
//				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//				mBaiduMap.animateMapStatus(u);
//			}
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//		mMapView.onDestroy();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//		mMapView.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//		mMapView.onPause();
//	}

}
