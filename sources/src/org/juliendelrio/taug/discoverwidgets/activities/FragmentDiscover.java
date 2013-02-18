package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.R;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools.PreferenceKey;
import org.juliendelrio.taug.discoverwidgets.widgets.WidgetBase;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentDiscover extends SherlockFragment {

	public static FragmentDiscover newInstance() {
		FragmentDiscover fragment = new FragmentDiscover();
		return fragment;
	}

	private TextView mTvAppInfo;
	private ToggleButton mActivatePart1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, null);
		mTvAppInfo = (TextView) view.findViewById(R.id.launcher_tv_app_infos);
		mActivatePart1 = (ToggleButton) view
				.findViewById(R.id.launcher_part1_toggle);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mTvAppInfo
				.setText(Html.fromHtml(getString(R.string.launcher_app_info)));

		mActivatePart1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						SharedPreferencesTools.get(getActivity(),
								PreferenceKey.Part1Activated, isChecked);

						WidgetBase.setEnabled(buttonView.getContext(),
								isChecked);
					}
				});

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		boolean activated = SharedPreferencesTools.get(getActivity(),
				PreferenceKey.Part1Activated, false);
		mActivatePart1.setChecked(activated);
		super.onResume();
	}
}
