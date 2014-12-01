package com.flowcontrol.plugins.main.state;

import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.help.FFAppHelp;
import com.flowcontrol.plugins.main.bean.MainInformationBean;

public class FCMainView_Detail extends FCMainViewBase {
	MainInformationBean mInformationBean;
	boolean mFirstInto = true;

	public FCMainView_Detail(FCAppController controller, MainInformationBean bean) {
		super(controller);
		mInformationBean = bean;
	}

	@Override
	public void showView() {
		mApp.getActivity().setContentView(R.layout.app_detail);

		long userFlow = FFAppHelp.getUserFlow(mInformationBean.mUid);
		userFlow = userFlow + mInformationBean.mUserFlow;
		String flow = Formatter.formatFileSize(mApp.getActivity(), userFlow);

		((TextView) mApp.getActivity().findViewById(R.id.user_flow)).setText("" + flow);
		((TextView) mApp.getActivity().findViewById(R.id.name)).setText("" + mInformationBean.mAppName);
		TextView backTextView = (TextView) mApp.getActivity().findViewById(R.id.detail_back);
		backTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mApp.getMainController().changToMainList();
			}
		});

		TextView detail_refresh = (TextView) mApp.getActivity().findViewById(R.id.detail_refresh);
		detail_refresh.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				long userFlow = FFAppHelp.getUserFlow(mInformationBean.mUid);
				userFlow = userFlow + mInformationBean.mUserFlow;
				String flow = Formatter.formatFileSize(mApp.getActivity(), userFlow);
				((TextView) mApp.getActivity().findViewById(R.id.user_flow)).setText("" + flow);
			}
		});

		final String[] limitFlowData = mApp.getActivity().getResources().getStringArray(R.array.limit_flow_number);
		Spinner limitFlow = (Spinner) mApp.getActivity().findViewById(R.id.limit_flow);

		if (mInformationBean.mLimitUserFlow == 0) {
			limitFlow.setContentDescription(mApp.getActivity().getResources().getString(R.string.no_limit));
		} else {
			// init limit value
			for (int i = 1; i < limitFlowData.length; i++) {
				String item = limitFlowData[i];
				if ((mInformationBean.mLimitUserFlow + "").equals(item.split("M")[0])) {
					limitFlow.setSelection(i);
				}
			}
		}

		mFirstInto = true;

		limitFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				int limit = 0;
				if (position != 0) {
					String limitFlow = limitFlowData[position];
					limit = Integer.parseInt(limitFlow.split("M")[0]);
				} else {
					limit = 0;
				}

				if (mFirstInto) {
					mFirstInto = false;

					return;
				} else {
					mApp.getLocationContext().updateAppInformationLimitFlow(mInformationBean.mAppName, mInformationBean.mUid, limit);
					mApp.getMainController().mMainListChang = true;
					mFirstInto = false;
				}

			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}
