package org.juliendelrio.taug.discoverwidgets.tools;

import org.juliendelrio.taug.discoverwidgets.widgets.WidgetActivity;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Parcel;

public class WidgetDescriptorConfiguration extends WidgetDescriptor {

	private boolean mIsBgHidden;
	private String mCustomText;

	public WidgetDescriptorConfiguration(int widgetId,
			Class<? extends AppWidgetProvider> widgetClass, boolean isBgHidden,
			String customText) {
		super(widgetId, widgetClass);
		mIsBgHidden = isBgHidden;
		mCustomText = customText;
	}

	public WidgetDescriptorConfiguration(int widgetId, String widgetClass,
			boolean isBgHidden, String customText)
			throws ClassNotFoundException {
		super(widgetId, widgetClass);
		mIsBgHidden = isBgHidden;
		mCustomText = customText;
	}

	@Override
	public String getStorableStringForComplement() {
		StringBuilder builder = new StringBuilder();
		builder.append(mIsBgHidden);
		builder.append(WidgetDescriptor.STORABLE_SEPARATOR);
		builder.append(mCustomText);
		return builder.toString();
	}

	public String getCustomText() {
		return mCustomText;
	}

	public boolean isBgHidden() {
		return mIsBgHidden;
	}

	public WidgetDescriptorConfiguration(Parcel in) {
		super(in);
		mIsBgHidden = in.readByte() == 1;
		mCustomText = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeByte((byte) (mIsBgHidden ? 1 : 0));
		dest.writeString(mCustomText);
	}

	public static WidgetDescriptorConfiguration restoreFromId(Context context,
			int appWidgetId) {
		int widgetId = appWidgetId;
		Class<? extends AppWidgetProvider> widgetClass = WidgetActivity.class;
		boolean isBgHidden = false;
		String customText = "unknown";

		WidgetDescriptor workingWidget = new WidgetDescriptor(widgetId,
				widgetClass);

		String source = SharedPreferencesTools.get(context,
				workingWidget.getStorableString(), "");
		String[] separateString = source.split(STORABLE_SEPARATOR);

		if (separateString.length > 2) {
			isBgHidden = Boolean.parseBoolean(separateString[1]);
			customText = separateString[0];
		} else {
			return null;
		}
		WidgetDescriptorConfiguration res = new WidgetDescriptorConfiguration(
				widgetId, widgetClass, isBgHidden, customText);
		return res;
	}
}
