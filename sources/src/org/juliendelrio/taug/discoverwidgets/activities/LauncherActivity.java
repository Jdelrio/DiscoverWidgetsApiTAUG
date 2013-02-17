package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class LauncherActivity extends SherlockFragmentActivity {

	private enum TabType {
		Discover(FragmentDiscover.class), WidgetsList(FragmentWidgetsList.class);

		private Class<? extends Fragment> mFragmentClass;

		private TabType(Class<? extends Fragment> fragmentClass) {
			mFragmentClass = fragmentClass;
		}

		public Class<? extends Fragment> getFragmentClass() {
			return mFragmentClass;
		}
	}

	private Tab tabDiscover;
	private Tab tabWidgetsList;
	private Fragment mCurrentFragment;

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

		// Find objects instance
		displayFragment(TabType.Discover);
	}

	class LauncherTabListener implements TabListener {

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (tab == tabDiscover) {
				displayFragment(TabType.Discover);
			} else if (tab == tabWidgetsList) {
				displayFragment(TabType.WidgetsList);
			}

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

	}

	private void displayFragment(TabType tab) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		Fragment newFragment;
		newFragment = fragmentManager.findFragmentByTag(tab.getFragmentClass()
				.getSimpleName());
		if (newFragment == null) {
			switch (tab) {
			case Discover:
				newFragment = FragmentDiscover.newInstance();
				break;
			case WidgetsList:
				newFragment = FragmentWidgetsList.newInstance();
				break;
			default:
				break;
			}
		}
		if (mCurrentFragment == null) {
			fragmentTransaction.add(R.id.launcher_fragments_container,
					newFragment, newFragment.getClass().getSimpleName());
		} else {
			fragmentTransaction.replace(R.id.launcher_fragments_container,
					newFragment, newFragment.getClass().getSimpleName());
		}
		mCurrentFragment = newFragment;
		fragmentTransaction.commit();
	}

}
