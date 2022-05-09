package com.kenlib.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kenlib.android.R;

public class SpinnerListAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	
	public SpinnerListAdapter(Context context,List<Map<String, String>> list) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder=null;
		if (arg1==null) {
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.list_spinner, null);
			viewHolder=new ViewHolder();
			viewHolder.mTextView=(TextView) arg1.findViewById(R.id.sp_item);
			arg1.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder)arg1.getTag();
		}
		
		viewHolder.mTextView.setText(list.get(arg0).get("type"));

		return arg1;
	}
	static class ViewHolder {
		TextView mTextView;
	
	}

}
