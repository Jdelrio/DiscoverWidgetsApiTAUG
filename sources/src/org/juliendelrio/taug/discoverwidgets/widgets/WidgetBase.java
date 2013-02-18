package org.juliendelrio.taug.discoverwidgets.widgets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.juliendelrio.taug.discoverwidgets.R;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools;
import org.juliendelrio.taug.discoverwidgets.tools.WidgetDescriptor;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;

public class WidgetBase extends AppWidgetProvider {

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		ArrayList<WidgetDescriptor> widgetsList = new ArrayList<WidgetDescriptor>();
		for (int i = 0; i < appWidgetIds.length; i++) {
			widgetsList.add(new WidgetDescriptor(appWidgetIds[i], this
					.getClass()));
			RemoteViews rview = buildUpdate(context, appWidgetIds[i]);
			appWidgetManager.updateAppWidget(appWidgetIds[i], rview);

		}
		SharedPreferencesTools.checkWidgetsList(context, widgetsList);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		for (int i = 0; i < appWidgetIds.length; i++) {
			WidgetDescriptor widgetDescriptor = new WidgetDescriptor(
					appWidgetIds[i], this.getClass());
			SharedPreferencesTools.removeWidget(context, widgetDescriptor);
		}
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	public RemoteViews buildUpdate(Context context, int appWidgetId) {
		RemoteViews rviews = new RemoteViews(context.getPackageName(),
				R.layout.widgets_base_layout);
		CharSequence textLabel = context.getText(R.string.widget_base_label)
				+ " " + appWidgetId;
		rviews.setTextViewText(R.id.widget_base_tvlabel, textLabel);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM hh:mm",
				Locale.getDefault());
		String retour = dateFormat.format(new Date());
		String textRefresh = context.getText(R.string.widget_base_lastrefresh)
				+ " " + retour;
		rviews.setTextViewText(R.id.widget_base_tvlastrefresh, textRefresh);
		return rviews;
	}

	public static void setEnabled(Context context, boolean isChecked) {
		PackageManager packageManager = context.getPackageManager();

		// disable WidgetBaseBeforeICS
		ComponentName componentNameBeforeICS = new ComponentName(context,
				WidgetBaseBeforeICS.class);
		int stateBeforeICS;
		if (isChecked) {
			stateBeforeICS = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
		} else {
			stateBeforeICS = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
		}
		packageManager.setComponentEnabledSetting(componentNameBeforeICS,
				stateBeforeICS, PackageManager.DONT_KILL_APP);

		// disable WidgetBaseAfterICS
		ComponentName componentNameAfterICS = new ComponentName(context,
				WidgetBaseAfterICS.class);
		int stateAfterICS;
		if (isChecked) {
			stateAfterICS = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
		} else {
			stateAfterICS = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
		}
		packageManager.setComponentEnabledSetting(componentNameAfterICS,
				stateAfterICS, PackageManager.DONT_KILL_APP);

	}
}
