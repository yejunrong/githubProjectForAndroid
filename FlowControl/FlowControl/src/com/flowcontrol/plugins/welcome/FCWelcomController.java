package com.flowcontrol.plugins.welcome;

import android.view.View;
import android.widget.Button;

import com.flowcontrol.FCAppController;
import com.flowcontrol.R;
import com.flowcontrol.plugins.FCPlugin;

public class FCWelcomController extends FCPlugin {
	public static final String NAME = "welcome";

	public FCWelcomController(FCAppController app) {
		super(app);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void enable() {
		mApp.getActivity().setContentView(R.layout.welcome);

		Button nextBtn = (Button) mApp.getActivity().findViewById(R.id.welcome_next_button);

		nextBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mApp.getMainController().enable();
			}
		});
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

}
