package com.flowcontrol.plugins.main.state;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.plugins.main.bean.MainInformationBean;
import com.flowcontrol.plugins.main.ui.MainListAdapterOnClickInterface;

public class FCMainView_AppListDelete extends FCMainViewBase {

	public FCMainView_AppListDelete(FCAppController controller) {
		super(controller);
	}

	@Override
	public void showView() {
		mMainController.mMainListAdapter.showOrHiddenCheckBox(true);

		if (mApp.getMainController().mMainListChang) {
			mApp.getMainController().mInformationBeans = mApp.getLocationContext().getAllInformation();
		}
		// mMainListAdapter = new MainListAdapter(mApp.getActivity(), mApp.getMainController().mInformationBeans, onClickInterface);
		ListView mainList = (ListView) mApp.getActivity().findViewById(R.id.main_list);

		mainList.setAdapter(mMainController.mMainListAdapter);
		// mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// mMainListAdapter.itemClickSelectCheckBox(position);
		// }
		// });
		//
		// mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		// public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// mApp.getMainController().changToDelete();
		//
		// return false;
		// }
		// });

		mApp.getActivity().findViewById(R.id.clean_checkbook).setVisibility(View.GONE);

		((TextView) mApp.getActivity().findViewById(R.id.main_setting)).setVisibility(View.GONE);
	}

	MainListAdapterOnClickInterface onClickInterface = new MainListAdapterOnClickInterface() {
		public void detailBtnOnClick(MainInformationBean bean) {
			mApp.getMainController().changToDetail(bean);
		}
	};

}
