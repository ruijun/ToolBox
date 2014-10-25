package com.tool.timing.call;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tool.R;

public class ContactAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	ArrayList<ContactInfo> itemList;

	public ContactAdapter(Context context,ArrayList<ContactInfo> itemList) {
		mInflater = LayoutInflater.from(context);
		this.itemList = itemList;
	}

	public ArrayList<ContactInfo> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ContactInfo> itemList) {
		this.itemList = itemList;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	
	//这个比较特殊,adapter是在页面变化的时候,重新获取当前页面的数据
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		
		convertView = mInflater.inflate(R.layout.contact_list, null);
		holder = new ViewHolder();
		holder.mname = (TextView) convertView.findViewById(R.id.mname);
		holder.msisdn = (TextView) convertView.findViewById(R.id.msisdn);
		
		

	    convertView.setTag(holder);
		holder.mname.setText(itemList.get(position).getContactName());
		holder.msisdn.setText("联系方式: " + itemList.get(position).getUserNumber());
		return convertView;
	}
	
	class ViewHolder {
		TextView mname;
		TextView msisdn;
	}

	class ViewProgressHolder {
		TextView text;
	}

}