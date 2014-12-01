package com.flowcontrol.plugins.main.state;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.plugins.main.bean.MainInformationBean;
import com.flowcontrol.plugins.main.ui.MainListAdapter;
import com.flowcontrol.plugins.main.ui.MainListAdapterOnClickInterface;

public class FCMainView_AppList extends FCMainViewBase {

	public FCMainView_AppList(FCAppController controller) {
		super(controller);
	}

	@Override
	public void showView() {
		mApp.getActivity().setContentView(R.layout.app_list);
		if (mApp.getMainController().mMainListChang) {
			mApp.getMainController().mInformationBeans = mApp.getLocationContext().getAllInformation();
		}

		mMainController.mMainListAdapter = new MainListAdapter(mApp.getActivity(), mApp.getMainController().mInformationBeans, onClickInterface);
		ListView mainList = (ListView) mApp.getActivity().findViewById(R.id.main_list);
		mainList.setAdapter(mMainController.mMainListAdapter);
		mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mMainController.mMainListAdapter.itemClickSelectCheckBox(position);
			}
		});
		mMainController.mMainListAdapter.showOrHiddenCheckBox(false);

		// mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		// public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// mApp.getMainController().changToDelete();
		// return false;
		// }
		// });

		CheckBox cleanCheckbook = (CheckBox) mApp.getActivity().findViewById(R.id.clean_checkbook);
		cleanCheckbook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mMainController.mMainListAdapter.isAllSelectCheckBox(isChecked);
			}
		});

		((TextView) mApp.getActivity().findViewById(R.id.main_setting)).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mApp.getSettingControll().enable();
			}
		});

	}

	MainListAdapterOnClickInterface onClickInterface = new MainListAdapterOnClickInterface() {
		public void detailBtnOnClick(MainInformationBean bean) {
			mApp.getMainController().changToDetail(bean);
		}
	};

	public void refreshView() {
		if (null != mMainController.mMainListAdapter) {
			mApp.getMainHandler().post(new Runnable() {
				@Override
				public void run() {

					mMainController.mMainListAdapter.updata(mApp.getMainController().mInformationBeans);
				}
			});
		}
	};
}
