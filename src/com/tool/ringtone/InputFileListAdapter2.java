package com.tool.ringtone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

//自定义Adapter
public class InputFileListAdapter2 extends BaseAdapter {

	// 用来记录CheckBox状态
	Map<Integer, Boolean> map;
	Context context;
	LayoutInflater mInflater;
	
	private List<InputFileListItem> mList;

	public InputFileListAdapter2(Context context, List<InputFileListItem> data) {
		super();
		this.context = context;
		map = new HashMap<Integer, Boolean>();
		mInflater = LayoutInflater.from(context);
		mList = data;
		for (int i = 0; i < data.size(); i++) {
			map.put(i, false);
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView==null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.input_file_list_item, null);
			holder.tN = (TextView) convertView.findViewById(R.id.multiple_name);// 文件名
			holder.tP = (TextView) convertView.findViewById(R.id.multiple_path);// 文件路径
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.multiple_checkbox);
			//holder.checkbox = (ImageView)convertView.findViewById(R.id.contact_checkbox);
			holder.im = (ImageView) convertView.findViewById(R.id.tag);
					
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tN.setText((String) mList.get(position).getName());
		holder.tP.setText((String) mList.get(position).getDir());
		holder.im.setBackgroundDrawable(mList.get(position).getIcon());
		holder.checkBox.setChecked(map.get(position));	
//		if (map.get(position)==true) {
//			holder.checkBox.setBackgroundDrawable(context.getResources()
//											.getDrawable(R.drawable.contact_check));
//		} 
		if (mList.get(position).isFolder()) {
			holder.checkBox.setVisibility(View.GONE);
			//holder.checkbox.setVisibility(View.GONE);
		}

		return convertView;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public class ViewHolder {
		public TextView tN;
		public TextView tP;
		public CheckBox checkBox;
		public ImageView im;
	}
}