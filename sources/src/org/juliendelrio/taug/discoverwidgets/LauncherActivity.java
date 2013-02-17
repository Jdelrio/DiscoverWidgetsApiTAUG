package org.juliendelrio.taug.discoverwidgets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class LauncherActivity extends SherlockFragmentActivity {

	private Tab tabDiscover;
	private Tab tabWidgetsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Prepare ActionBar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.app_name);

		setContentView(R.layout.activity_launcher);

		// generate tabs

		// Tab discover
		tabDiscover = actionBar.newTab()
				.setText(R.string.launcher_tab_discover)
				.setTabListener(new LauncherTabListener());
		actionBar.addTab(tabDiscover);

		// Tab widgets list
		tabWidgetsList = actionBar.newTab()
				.setText(R.string.launcher_tab_widgetslist)
				.setTabListener(new LauncherTabListener());
		actionBar.addTab(tabWidgetsList);

	}

	class LauncherTabListener implements TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}

	}

}
