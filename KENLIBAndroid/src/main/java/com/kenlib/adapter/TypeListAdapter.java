package com.kenlib.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kenlib.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TypeListAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	public TypeListAdapter(Context context, List<Map<String, String>> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list1,
					null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.title);
			viewHolder.id = (TextView) convertView.findViewById(R.id.id);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();

		}

		viewHolder.mTextView.setText("" + list.get(position).get("type"));
//		viewHolder.id.setText("" + list.get(position).get("typeid"));
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView,id;


	}


}
