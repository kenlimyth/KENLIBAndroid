package com.kenlib.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kenlib.imgloader.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kenlib.android.R;
import com.kenlib.util.KENConfig;

public class TJListAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private ImageLoader mImageLoader;
	private boolean mBusy = false;
	public TJListAdapter(Context context, List<Map<String, String>> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		mImageLoader=new ImageLoader(context);
	}
	

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list, null);
			viewHolder = new ViewHolder();
			viewHolder.id = (TextView) convertView.findViewById(R.id.id);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.sj = (TextView) convertView.findViewById(R.id.sj);
			viewHolder.pl = (TextView) convertView.findViewById(R.id.pl);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);


			
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();

		}
		viewHolder.id.setText(list.get(position).get("NewId"));
		viewHolder.title.setText(list.get(position).get("NewTitle"));
		viewHolder.sj.setText(list.get(position).get("NewAddTime"));
		viewHolder.pl.setText(list.get(position).get("hfcount")+"回复");
		String url=list.get(position).get("ImgUrl");
		mImageLoader.DisplayImage(KENConfig.IP+ url, viewHolder.imageView, false);
		


		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView sj;
		TextView pl,id;
		
		ImageView imageView;

	}

}
