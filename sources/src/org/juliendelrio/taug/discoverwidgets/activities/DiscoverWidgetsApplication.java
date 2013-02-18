package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools.PreferenceKey;
import org.juliendelrio.taug.discoverwidgets.widgets.WidgetBase;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;

public class DiscoverWidgetsApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// On Init, hide widgets
		if (!SharedPreferencesTools.get(this, PreferenceKey.Initialized, false)) {
			WidgetBase.setEnabled(this, false);
			SharedPreferencesTools.set(this, PreferenceKey.Initialized, true);
		}

		int versionCode = 1;
		try {
			versionCode = getPackageManager().getPackageInfo(getPackageName(),
					0).versionCode;
		} catch (NameNotFoundException e) {
		}
		SharedPreferencesTools.set(this, PreferenceKey.InstalledVersion,
				versionCode);
	}
}
