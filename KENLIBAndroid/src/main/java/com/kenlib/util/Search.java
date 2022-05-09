package com.kenlib.util;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import com.kenlib.android.R;
import com.kenlib.http.HttpUtil;

/**
 * 搜索
 */
public class Search {

	List<Map<String, String>> listSearch = new ArrayList<Map<String, String>>();
	PopupWindow popupWindow;
	private ImageView searchImg;
	FragmentActivity fragmentActivity;
	Isearch isearch;

	public Search(ImageView searchImg, FragmentActivity fragmentActivity) {

		this.searchImg = searchImg;
		this.fragmentActivity = fragmentActivity;

	}

	public void createSearch() {

		if (searchImg == null) {
			return;
		}
		searchImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Utility.showToast(fragmentActivity, "a");

				DialogUtil.showPW(fragmentActivity,
						R.layout.auto_search_list,600, new DialogUtil.IviewP() {

							@Override
							public void setView(View view,final PopupWindow popupWindow) {
								// TODO Auto-generated method stub
								final EditText key = (EditText) view
										.findViewById(R.id.key);// 设置view事件属性
								ListView listView = (ListView) view
										.findViewById(R.id.listView1);
//								ImageView qc = (ImageView) view
//										.findViewById(R.id.qc);
//								qc.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										key.setText("");
//									}
//								});
								TextView qx = (TextView) view
										.findViewById(R.id.qx);
								qx.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (popupWindow != null
												&& popupWindow.isShowing()) {
											popupWindow.dismiss();
										}
									}
								});

								key.addTextChangedListener(new textWatcher(
										listView, key));

								listView.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										// TODO Auto-generated method stub

										TextView idtTextView = (TextView) view
												.findViewById(R.id.id);

										// Intent it = new Intent(
										// fragmentActivity, LTInfo.class);
										// it.putExtra("id",
										// idtTextView.getText());
										// fragmentActivity.startActivity(it);

									}
								});
							}
						});

			}
		});
	}

	/**
	 * EditTex输入事件监控
	 * 
	 * @author Administrator
	 * 
	 */
	private class textWatcher implements TextWatcher {
		private ListView listView;
		private EditText editText;

		public textWatcher(ListView listView, EditText editText) {
			this.listView = listView;
			this.editText = editText;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

			listView.setAdapter(null);

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {

						JSONObject jsonObject1 = new JSONObject();

						jsonObject1.put("Sql",
								"select top 10 * from lt where newtitle like '%"
										+ editText.getText() + "%'");

						jsonObject1.put("Todo", "Select");

						
						String jsonObject = HttpUtil.sendPost(KENConfig.ServerURL, jsonObject1);
						listSearch= JsonUtil.psJsonDataList(jsonObject);
					
						

						if (listSearch != null && listSearch.size() > 0) {

							Activity activity = fragmentActivity;
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									// SearchListAdapter searchListAdapter = new
									// SearchListAdapter(
									// fragmentActivity, listSearch);
									// listView.setAdapter(searchListAdapter);
								}
							});

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

	public interface Isearch {

		Void go();

		Void setData();

	}

}
