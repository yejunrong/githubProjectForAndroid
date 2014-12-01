package com.flowcontrol.plugins.setting.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.main.bean.MainInformationBean;

public class SettingFilterListAdapter extends BaseAdapter {
	List<MainInformationBean> beanList;
	FCAppController mApp;
	public Map<MainInformationBean, CheckBox> mCheckBoxs = new HashMap<MainInformationBean, CheckBox>();

	public SettingFilterListAdapter(FCAppController app, List<MainInformationBean> beans) {
		beanList = beans;
		mApp = app;
	}

	@Override
	public int getCount() {
		return beanList.size();
	}

	@Override
	public Object getItem(int position) {
		return beanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void initData() {
		for (int i = 0; i < beanList.size(); i++) {

		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SettingFilterAdapterHolder holer = null;
		final MainInformationBean informationBean = beanList.get(position);

		if (null == convertView) {
			holer = new SettingFilterAdapterHolder();
			convertView = LayoutInflater.from(mApp.getActivity()).inflate(R.layout.setting_filter_list_item, null);
			holer._Name = (TextView) convertView.findViewById(R.id.filter_name);
			holer._CheckBox = (CheckBox) convertView.findViewById(R.id.filter_select_checkbox);

			convertView.setTag(holer);
		} else {
			holer = (SettingFilterAdapterHolder) convertView.getTag();
		}

		mCheckBoxs.put(informationBean, holer._CheckBox);
		holer._Name.setText(informationBean.mAppName);

		// 判断是否已经处于被选中状态
		if (!informationBean.mIsNotClean) {
			holer._CheckBox.setChecked(true);
		} else {
			holer._CheckBox.setChecked(false);
		}

		holer._CheckBox.setTag(position);
		return convertView;
	}

	public void clickItemCheckBoxSelect(int position) {
		MainInformationBean itemBean = beanList.get(position);
		CheckBox checkBox = mCheckBoxs.get(itemBean);

		if (null == checkBox) {
			return;
		}

		if (checkBox.isChecked()) {
			checkBox.setChecked(false);
			itemBean.mIsNotClean = true;// 不需要清除
		} else {
			checkBox.setChecked(true);
			itemBean.mIsNotClean = false;// 不需要清除
		}

		FCLog.i("update isNotClean result=" + mApp.getLocationContext().updateAppInformationUserFlow(itemBean));
	}

	public final static class SettingFilterAdapterHolder {
		public TextView _Name;
		public CheckBox _CheckBox;
	}
}
