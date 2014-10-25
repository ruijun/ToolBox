package com.tool.timing.message;

import java.util.ArrayList;

import com.tool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class Contact_Adapter extends BaseAdapter {

	private LayoutInflater mInflater;
	ArrayList<Contact_Info> itemList;

	public Contact_Adapter(Context context,ArrayList<Contact_Info> itemList) {
		mInflater = LayoutInflater.from(context);
		this.itemList = itemList;
	}

	public ArrayList<Contact_Info> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Contact_Info> itemList) {
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

		
		convertView = mInflater.inflate(R.layout.contact_list2, null);
		holder = new ViewHolder();
		holder.mname = (TextView) convertView.findViewById(R.id.mname);
		holder.msisdn = (TextView) convertView.findViewById(R.id.msisdn);
		holder.check = (CheckBox) convertView.findViewById(R.id.check);
		

	    convertView.setTag(holder);
		holder.mname.setText(itemList.get(position).getContactName());
		holder.msisdn.setText("联系方式: " + itemList.get(position).getUserNumber());
		holder.check.setChecked(itemList.get(position).getIsChecked());
		return convertView;
	}
	
	class ViewHolder {
		TextView mname;
		TextView msisdn;
		CheckBox check;
	}

	class ViewProgressHolder {
		TextView text;
	}

}