package com.flowcontrol.plugins.setting.bean;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;

public class FCSettingBean {
	public Boolean mIsMessageRemind;// 是否消息提醒
	public Integer mOverstepFlow;// 超过流量提醒
	public Integer mCheckFlowMinute;// 隔几分钟检测

	/**
	 * 
	 * @param isMessageRemind
	 *            是否消息提醒
	 * @param outOfFlow
	 *            超过流量提醒
	 * @param checkFlowMinute
	 *            隔几分钟检测
	 */
	public FCSettingBean(Boolean isMessageRemind, Integer outOfFlow, Integer checkFlowMinute) {
		mIsMessageRemind = isMessageRemind;
		mOverstepFlow = outOfFlow;
		mCheckFlowMinute = checkFlowMinute;
	}

	public FCSettingBean() {
	}

	/**
	 * 
	 * @param mApp
	 * @param position
	 *            下标
	 * @return 根据下标获取超过 流量提醒数组 中的数据
	 */
	public static int getOutOfFlow(FCAppController mApp, int position) {
		String[] remindFlowArray = mApp.getActivity().getResources().getStringArray(R.array.remind_flow);
		String remindFlowString = remindFlowArray[position];
		return Integer.parseInt(remindFlowString.substring(0, 2));
	}

	/**
	 * 
	 * @param mApp
	 * @param position
	 *            下标
	 * @return 根据下标获取超过 检查分钟数组 中的数据
	 */
	public static int getCheckFlowMinute(FCAppController mApp, int position) {
		String[] checkFlowArray = mApp.getActivity().getResources().getStringArray(R.array.every_minute_check_flow);
		String checkFlowString = checkFlowArray[position];
		return Integer.parseInt(checkFlowString.substring(0, 1));
	}

	/**
	 * 根据超出流量 返回数组对应的下标
	 * 
	 * @param mApp
	 * @param outOfFlow
	 * @return
	 */
	public static int getOutOfFlowPosition(FCAppController mApp, int outOfFlow) {
		int result = 0;
		String[] remindFlowArray = mApp.getActivity().getResources().getStringArray(R.array.remind_flow);
		for (int i = 0; i < remindFlowArray.length; i++) {
			String itemFlowString = remindFlowArray[i];
			int itemFlowInt = Integer.parseInt(itemFlowString.substring(0, 2));

			if (itemFlowInt == outOfFlow) {
				result = i;
				break;
			}
		}
		return result;
	}

	/**
	 * 根据检测时间 返回数组对应的下标
	 * 
	 * @param mApp
	 * @param checkFlow
	 * @return
	 */
	public static int getCheckFlowPosition(FCAppController mApp, int checkFlow) {
		int result = 0;
		String[] checkFlowArray = mApp.getActivity().getResources().getStringArray(R.array.every_minute_check_flow);
		for (int i = 0; i < checkFlowArray.length; i++) {
			String itemCheckFlowString = checkFlowArray[i];
			int itemCheckFlowInt = Integer.parseInt(itemCheckFlowString.substring(0, 1));

			if (itemCheckFlowInt == checkFlow) {
				result = i;
				break;
			}
		}
		return result;
	}

}
