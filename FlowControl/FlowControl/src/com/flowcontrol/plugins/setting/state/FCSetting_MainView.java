package com.flowcontrol.plugins.setting.state;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.log_manager.FCLog;
import com.flowcontrol.plugins.setting.FCSettingBase;
import com.flowcontrol.plugins.setting.bean.FCSettingBean;

public class FCSetting_MainView extends FCSettingBase {
	String mogoID = "";
	Switch isOpenSwitch_;
	Spinner flowSpinner_;
	Spinner checkFlowSpinner_;

	public FCSetting_MainView(FCAppController controller) {
		super(controller);
		mogoID = controller.getActivity().getResources().getString(R.string.mogo_app_id);

	}

	public void showView() {
		mApp.getActivity().setContentView(R.layout.setting_main);
		mApp.getActivity().findViewById(R.id.special_setting).setOnClickListener(onClickListener);

		isOpenSwitch_ = (Switch) mApp.getActivity().findViewById(R.id.is_open_switch);
		isOpenSwitch_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mApp.getLocationContext().updateSettingInfo(isChecked, null, null);
				if (isChecked) {
					FCLog.i("打开");
				} else {
					FCLog.i("关闭");
				}
			}
		});

		flowSpinner_ = (Spinner) mApp.getActivity().findViewById(R.id.out_of_flow_spinner);
		flowSpinner_.setOnItemSelectedListener(spinnerSelectListener);

		checkFlowSpinner_ = (Spinner) mApp.getActivity().findViewById(R.id.check_flow_spinner);
		checkFlowSpinner_.setOnItemSelectedListener(spinnerSelectListener);

		mApp.getActivity().findViewById(R.id.detail_back).setOnClickListener(onClickListener);

		initData();
	}

	private void initData() {
		FCSettingBean bean = mApp.getLocationContext().getSettingInfo();
		if (bean.mIsMessageRemind != null) {
			isOpenSwitch_.setChecked(bean.mIsMessageRemind);
		}
		if (bean.mCheckFlowMinute != null) {
			int position = FCSettingBean.getCheckFlowPosition(mApp, bean.mCheckFlowMinute);
			flowSpinner_.setSelection(position);
		}

		if (bean.mOverstepFlow != null) {
			int position = FCSettingBean.getOutOfFlowPosition(mApp, bean.mOverstepFlow);
			checkFlowSpinner_.setSelection(position);
		}
	}

	Spinner.OnItemSelectedListener spinnerSelectListener = new Spinner.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			switch (parent.getId()) {
			case R.id.out_of_flow_spinner:

				int remindFlowInt = FCSettingBean.getOutOfFlow(mApp, position);
				mApp.getLocationContext().updateSettingInfo(null, remindFlowInt, null);
				FCLog.i("select remind flow = " + remindFlowInt);

				break;
			case R.id.check_flow_spinner:
				int checkFlowInt = FCSettingBean.getCheckFlowMinute(mApp, position);
				mApp.getLocationContext().updateSettingInfo(null, null, checkFlowInt);
				FCLog.i("select remind flow = " + checkFlowInt);

				break;
			default:
				break;
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			FCLog.i(" onNothingSelected ");
		}
	};

	OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.special_setting:
				FCLog.i("特殊设置。。。。。。");
				mApp.getSettingControll().toSettingFilterPage();

				break;

			case R.id.detail_back:
				mApp.getMainController().enable();
			default:
				break;
			}
		}
	};

}
