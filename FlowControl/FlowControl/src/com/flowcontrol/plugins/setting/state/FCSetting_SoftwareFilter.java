package com.flowcontrol.plugins.setting.state;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.plugins.setting.FCSettingBase;
import com.flowcontrol.plugins.setting.ui.SettingFilterListAdapter;

public class FCSetting_SoftwareFilter extends FCSettingBase {
	SettingFilterListAdapter mFilterListAdapter;

	public FCSetting_SoftwareFilter(FCAppController controller) {
		super(controller);
	}

	public void showView() {
		mApp.getActivity().setContentView(R.layout.setting_filter);
		mApp.getActivity().findViewById(R.id.filter_back).setOnClickListener(onClickListener);
		ListView filterList = (ListView) mApp.getActivity().findViewById(R.id.filter_listview);

		mFilterListAdapter = new SettingFilterListAdapter(mApp, mApp.getSettingControll().mInformationBeans);
		filterList.setAdapter(mFilterListAdapter);

		filterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mFilterListAdapter.clickItemCheckBoxSelect(position);
			}
		});

	}

	OnClickListener onClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.filter_back:
				mApp.getSettingControll().toSettingMainPage();
				break;

			default:
				break;
			}
		}

	};
}
