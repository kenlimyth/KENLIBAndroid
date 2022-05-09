package com.kenlib.fragmentviewpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.ViewPager;
import com.kenlib.http.HttpUtil;
import com.kenlib.android.R;
import com.kenlib.util.JsonUtil;
import com.kenlib.util.KENConfig;
import com.kenlib.util.Util;
import com.kenlib.view.xlistview.XListView;
import com.kenlib.view.xlistview.XListView.IXListViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 新闻列表页
 */
public class NewList extends MyFragment {

	private static final String TAG = "TestFragment";
	ViewPager viewPager;
	private XListView mListView;

	private ProgressDialog ing;
	Activity activity;
	LinearLayout noGoods;
	ProgressBar pro;
	List<Map<String, String>> dbList = new ArrayList<Map<String, String>>();
	List<Map<String, String>> imgList = new ArrayList<Map<String, String>>();
	boolean isLanjiazai = false;// 懒加载
	private String key = "";// ID
	LinearLayout linearLayout;


	public static final NewList newInstance(String key, boolean message)
	{
		NewList fragment = new NewList();
		Bundle bundle = new Bundle();
		bundle.putString("key",key);
		bundle.putBoolean("isLanjiazai", message);
		fragment.setArguments(bundle);

		return fragment ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		key=getArguments().getString("key");
		isLanjiazai=getArguments().getBoolean("isLanjiazai");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		activity = getActivity();


		View view = inflater.inflate(R.layout.listview, container, false);// listview
		viewPager = (ViewPager) view.findViewById(R.id.vPager11);
		linearLayout = (LinearLayout) view.findViewById(R.id.l1);

		mListView = (XListView) view.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(false);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);

		mListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				getDBByList();
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				// getDBByList();
			}
		});

		// test db
		Map<String, String> map= new HashMap<>();
		map.put("1","1");
		dbList.add(map);

		mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						Log.i("a---", position + "");
						if (position > 0) {

							TextView idtTextView = (TextView) view
									.findViewById(R.id.id);

							if ("1".equals(key)) {

								// Intent it = new Intent(activity,
								// LTInfo.class);
								// it.putExtra("id", idtTextView.getText());
								// startActivity(it);

							}
							// else if ("2".equals(key)) {
							//
							// Intent it = new Intent(activity, MSInfo.class);
							// it.putExtra("id", idtTextView.getText());
							// startActivity(it);
							//
							// } else if ("3".equals(key)) {
							//
							// Intent it = new Intent(activity, JZInfo.class);
							// it.putExtra("id", idtTextView.getText());
							// startActivity(it);
							//
							// }

						}

					}
				});

		if (isLanjiazai) {
			Lanjiazai();
		} else {

			getDataAll();
		}

		return view;

	}

	void getDataAll() {
		getDBByList();
		getLBIMG();
	}

	private void setListView() {
		// TODO Auto-generated method stub
		if (dbList != null && dbList.size() > 0) {
			NewListAdapter hc = new NewListAdapter(getActivity(), dbList);
			mListView.setAdapter(hc);
			// noGoods.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
		} else {

			mListView.setVisibility(View.GONE);
		}

	}

	
	void getDBByList() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String sql = "";
				if ("1".equals(key)) {

					sql = "select * from [lt] where newtypeid <> 463 order by newaddtime desc";

				} else if ("2".equals(key)) {

					sql = "select * from [meishi] where newtypeid <> 463 order by newaddtime desc";

				} else if ("3".equals(key)) {

					sql = "select * from [jiangzuo] where newtypeid <> 463 order by newaddtime desc";

				}

				try {
					JSONObject jsonObject1 = new JSONObject();
					jsonObject1.put("Sql", sql);
					jsonObject1.put("Todo", "Select");
					String jsonObject = HttpUtil.sendPost(KENConfig.ServerURL, jsonObject1);
					imgList= JsonUtil.psJsonDataList(jsonObject);
				
						handler2.sendEmptyMessage(1);
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}



	void getLBIMG() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String sql = "";
				if ("1".equals(key)) {

					sql = "select imgurl,newid from [lt] where newtypeid=463 order by newaddtime desc";

				} else if ("2".equals(key)) {

					sql = "select imgurl,newid from [meishi] where newtypeid=463 order by newaddtime desc";

				} else if ("3".equals(key)) {

					sql = "select imgurl,newid from [jiangzuo] where newtypeid=463 order by newaddtime desc";

				}

				try {
					JSONObject jsonObject1 = new JSONObject();
					jsonObject1.put("Sql", sql);
					jsonObject1.put("Todo", "Select");
					String jsonObject = HttpUtil.sendPost(KENConfig.ServerURL, jsonObject1);
					imgList= JsonUtil.psJsonDataList(jsonObject);
					
					handler2.sendEmptyMessage(2);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	Handler handler2 = new Handler() {// ����handler
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				mListView.reSet();// ����listView
				setListView();
			}
			if (msg.what == 2) {

				if (imgList != null && imgList.size() > 0) {

					//LB lb = new LB(activity, viewPager, linearLayout);
					// lb.setViewList(new IViewList() {
					//
					// @Override
					// public ArrayList<View> addViewList() {
					// // TODO Auto-generated method stub
					// ArrayList<View> arrayList = new ArrayList<View>();
					// for (final Map<String, String> map : imgList) {
					// View view = LayoutInflater.from(activity)
					// .inflate(R.layout.lay1, null);
					// ImageView imageView = (ImageView) view
					// .findViewById(R.id.imageView1);
					// new ImageLoader(activity).DisplayImage(
					// KENConfig.IP + map.get("imgurl"),
					// imageView, false);
					//
					// imageView
					// .setOnClickListener(new OnClickListener() {
					//
					// @Override
					// public void onClick(View v) {
					// // TODO Auto-generated method
					// // stub
					//
					// if ("1".equals(key)) {
					//
					// Intent it = new Intent(
					// activity,
					// LTInfo.class);
					// it.putExtra("id",
					// map.get("newid"));
					// startActivity(it);
					//
					// } else if ("2".equals(key)) {
					//
					// Intent it = new Intent(
					// activity,
					// MSInfo.class);
					// it.putExtra("id",
					// map.get("newid"));
					// startActivity(it);
					//
					// } else if ("3".equals(key)) {
					//
					// Intent it = new Intent(
					// activity,
					// JZInfo.class);
					// it.putExtra("id",
					// map.get("newid"));
					// startActivity(it);
					//
					// }
					//
					// }
					// });
					//
					// arrayList.add(view);
					//
					// }
					// return arrayList;
					// }
					// });
					//lb.create();
				}
			}

		}

	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "TestFragment-----onDestroy--" + key);
	}

	@Override
	protected void Lanjiazai() {
		// TODO Auto-generated method stub
		Util.showLogDebug("lazyLoad-isPrepared=" + isLanjiazai + "-"
				+ isVisible + "--zt=" + key);
		if (!isLanjiazai || !isVisible) {
			return;
		}
		
		Util.showLogDebug("lazyLoad");

		getDataAll();
	}

	


}
