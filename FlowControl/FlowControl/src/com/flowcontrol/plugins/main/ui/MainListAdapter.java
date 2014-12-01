package com.flowcontrol.plugins.main.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.flowcontrol.R;
import com.flowcontrol.plugins.main.bean.MainInformationBean;

public class MainListAdapter extends BaseAdapter {
	Context mContext;
	public List<MainInformationBean> informationBeans;
	MainListAdapterOnClickInterface mOnClickInterface;
	public Map<Integer, Map<CheckBox, Boolean>> mCheckBoxs = new HashMap<Integer, Map<CheckBox, Boolean>>();

	public MainListAdapter(Context context, List<MainInformationBean> beans, MainListAdapterOnClickInterface onClickInterface) {
		mContext = context;
		informationBeans = beans;
		mOnClickInterface = onClickInterface;

		initData();
	}

	public void updata(List<MainInformationBean> beans) {
		informationBeans = beans;
		initData();
		notifyDataSetChanged();
	}

	private void initData() {
		List<MainInformationBean> beans = new ArrayList<MainInformationBean>();
		for (int i = 0; i < informationBeans.size(); i++) {
			if (informationBeans.get(i).mIsNotClean) {
				beans.add(informationBeans.get(i));
			}
		}

		informationBeans = beans;
	}

	@Override
	public int getCount() {
		return informationBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 是否全选checkBox
	public void isAllSelectCheckBox(boolean isAllSelect) {
		for (int i = 0; i < mCheckBoxs.size(); i++) {
			Map<CheckBox, Boolean> checkBox = mCheckBoxs.get(i);
			Iterator<CheckBox> item = checkBox.keySet().iterator();
			item.next().setChecked(isAllSelect);
		}
	}

	// 显示或者隐藏checkBox
	public void showOrHiddenCheckBox(boolean isShow) {
		for (int i = 0; i < mCheckBoxs.size(); i++) {
			Map<CheckBox, Boolean> checkBox = mCheckBoxs.get(i);
			Iterator<CheckBox> item = checkBox.keySet().iterator();
			if (isShow) {
				item.next().setVisibility(View.VISIBLE);
			} else {
				item.next().setVisibility(View.GONE);
			}
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainListAdapterHolder mainHolder = null;
		final MainInformationBean informationBean = informationBeans.get(position);
		if (null == convertView) {
			mainHolder = new MainListAdapterHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.mian_list_item, null);
			mainHolder._Name = (TextView) convertView.findViewById(R.id.name_textview);
			mainHolder._Detail = (TextView) convertView.findViewById(R.id.details);
			mainHolder._CheckBox = (CheckBox) convertView.findViewById(R.id.select_checkbox);

			convertView.setTag(mainHolder);

		} else {
			mainHolder = (MainListAdapterHolder) convertView.getTag();
		}

		mainHolder._Name.setText(informationBean.mAppName);
		mainHolder._Detail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mOnClickInterface.detailBtnOnClick(informationBean);
			}
		});

		if (null != mCheckBoxs.get(position)) {
			Map<CheckBox, Boolean> checkBoxMap = mCheckBoxs.get(position);
			Iterator<CheckBox> item = checkBoxMap.keySet().iterator();
			CheckBox checkBox = item.next();
			boolean isCheck = checkBoxMap.get(checkBox);

			mainHolder._CheckBox.setChecked(isCheck);

			Map<CheckBox, Boolean> setItem = new HashMap<CheckBox, Boolean>();
			setItem.put(mainHolder._CheckBox, isCheck);
			mCheckBoxs.put(position, setItem);

		} else {
			Map<CheckBox, Boolean> item = new HashMap<CheckBox, Boolean>();
			item.put(mainHolder._CheckBox, false);
			mainHolder._CheckBox.setChecked(false);
			mCheckBoxs.put(position, item);
		}

		return convertView;
	}

	public void itemClickSelectCheckBox(int position) {
		Map<CheckBox, Boolean> checkBoxMap = mCheckBoxs.get(position);
		Iterator<CheckBox> item = checkBoxMap.keySet().iterator();
		while (item.hasNext()) {
			CheckBox checkBox = item.next();
			boolean isCheck = checkBoxMap.get(checkBox);

			Map<CheckBox, Boolean> setItem = new HashMap<CheckBox, Boolean>();
			setItem.put(checkBox, !isCheck);
			checkBox.setChecked(!isCheck);
			mCheckBoxs.put(position, setItem);
		}

	}

	public final static class MainListAdapterHolder {
		public TextView _Name;
		public TextView _Detail;
		public CheckBox _CheckBox;
	}
}
