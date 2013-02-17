package org.juliendelrio.taug.discoverwidgets.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesTools {
	public static final String STORABLE_SEPARATOR = "#";
	private static final String GENERAL_PREFS_FILE_NAME = "general_prefs";
	private static final String PREF_WIDGETS_LIST = "pref_widgets_list";

	public static SharedPreferences getGeneralSettings(Context context) {
		return context.getSharedPreferences(GENERAL_PREFS_FILE_NAME, 0);
	}

	public static void set(final Context context, String key, final String value) {
		SharedPreferences settings = getGeneralSettings(context);
		final SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String get(final Context context, String key,
			final String defaultValue) {
		SharedPreferences settings = getGeneralSettings(context);
		return settings.getString(key, defaultValue);
	}

	public static void addWidget(Context context,
			WidgetDescriptor widgetDescriptor) {
		HashSet<WidgetDescriptor> savedWidgetsList = getWidgetsSet(context);
		int sizeBefore = savedWidgetsList.size();
		savedWidgetsList.add(widgetDescriptor);

		if (sizeBefore != savedWidgetsList.size()) {
			saveWidgetsStorableString(context, savedWidgetsList);
		}

	}

	public static void removeWidget(Context context,
			WidgetDescriptor widgetDescriptor) {
		HashSet<WidgetDescriptor> savedWidgetsList = getWidgetsSet(context);
		int sizeBefore = savedWidgetsList.size();
		savedWidgetsList.remove(widgetDescriptor);

		if (sizeBefore != savedWidgetsList.size()) {
			saveWidgetsStorableString(context, savedWidgetsList);
		}

	}

	public static HashSet<WidgetDescriptor> getWidgetsSet(Context context) {
		String[] stringsList = getStringListForSavedWidgets(context);
		HashSet<WidgetDescriptor> res = new HashSet<WidgetDescriptor>();
		for (int i = 0; i < stringsList.length; i++) {
			res.add(WidgetDescriptor
					.getWidgetDescriptionFromStorableString(stringsList[i]));
		}
		return res;
	}

	public static WidgetDescriptor[] getWidgetsArray(Context context) {
		String[] stringsList = getStringListForSavedWidgets(context);
		WidgetDescriptor[] res = new WidgetDescriptor[stringsList.length];

		for (int i = 0; i < stringsList.length; i++) {
			res[i] = WidgetDescriptor
					.getWidgetDescriptionFromStorableString(stringsList[i]);
		}

		return res;
	}

	private static String[] getStringListForSavedWidgets(Context context) {
		String savedString = getGeneralSettings(context).getString(
				PREF_WIDGETS_LIST, "");
		return savedString.split(STORABLE_SEPARATOR);
	}

	public static void checkWidgetsList(final Context context,
			final ArrayList<WidgetDescriptor> widgetsList) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				HashSet<WidgetDescriptor> savedWidgetsList = getWidgetsSet(context);

				int sizeBefore = savedWidgetsList.size();
				// Check is the widget exist
				for (int i = 0; i < widgetsList.size(); i++) {
					savedWidgetsList.add(widgetsList.get(i));
				}

				if (sizeBefore != savedWidgetsList.size()) {
					saveWidgetsStorableString(context, savedWidgetsList);
				}
			}

		});
		thread.start();

	}

	protected static void saveWidgetsStorableString(Context context,
			HashSet<WidgetDescriptor> savedWidgetsList) {
		String storableString = convertWidgetsSetToStorableString(savedWidgetsList);

		// Save String
		Editor editor = getGeneralSettings(context).edit();
		editor.putString(PREF_WIDGETS_LIST, storableString);
		editor.commit();

	}

	private static String convertWidgetsSetToStorableString(
			HashSet<WidgetDescriptor> widgetsList) {
		// Create String
		StringBuilder stringBuilder = new StringBuilder();
		for (Iterator<WidgetDescriptor> iterator = widgetsList.iterator(); iterator
				.hasNext();) {
			WidgetDescriptor widgetDescriptor = iterator.next();
			if (widgetDescriptor != null) {
				stringBuilder.append(widgetDescriptor.getStorableString());
				if (iterator.hasNext()) {
					stringBuilder.append(STORABLE_SEPARATOR);
				}
			}
		}
		return stringBuilder.toString();
	}
}
