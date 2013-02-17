package org.juliendelrio.taug.discoverwidgets.tools;

import android.appwidget.AppWidgetProvider;
import android.os.Parcel;
import android.os.Parcelable;

public class WidgetDescriptor implements Parcelable {
	public static final String STORABLE_SEPARATOR = "@";

	int mWidgetId;
	String mWidgetClass;

	public WidgetDescriptor(int widgetId,
			Class<? extends AppWidgetProvider> widgetClass) {
		super();
		this.mWidgetId = widgetId;
		this.mWidgetClass = widgetClass.getName();
	}

	@SuppressWarnings("unchecked")
	public WidgetDescriptor(int widgetId, String widgetClass)
			throws ClassNotFoundException {
		this(widgetId, (Class<? extends AppWidgetProvider>) Class
				.forName(widgetClass));
	}

	public int getWidgetId() {
		return mWidgetId;
	}

	@SuppressWarnings("unchecked")
	public Class<AppWidgetProvider> getWidgetClass()
			throws ClassNotFoundException {
		return (Class<AppWidgetProvider>) Class.forName(mWidgetClass);
	}

	protected WidgetDescriptor(Parcel in) {
		mWidgetId = in.readInt();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mWidgetId);
	}

	public static final Parcelable.Creator<WidgetDescriptor> CREATOR = new Parcelable.Creator<WidgetDescriptor>() {
		public WidgetDescriptor createFromParcel(Parcel in) {
			return new WidgetDescriptor(in);
		}

		public WidgetDescriptor[] newArray(int size) {
			return new WidgetDescriptor[size];
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mWidgetClass == null) ? 0 : mWidgetClass.hashCode());
		result = prime * result + mWidgetId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WidgetDescriptor other = (WidgetDescriptor) obj;
		if (mWidgetClass == null) {
			if (other.mWidgetClass != null)
				return false;
		} else if (!mWidgetClass.equals(other.mWidgetClass))
			return false;
		if (mWidgetId != other.mWidgetId)
			return false;
		return true;
	}

	public String getStorableString() {
		return mWidgetClass + STORABLE_SEPARATOR + mWidgetId;
	}

	public static WidgetDescriptor getWidgetDescriptionFromStorableString(
			String source) {
		String[] separateString = source.split(STORABLE_SEPARATOR);

		if (separateString.length == 2) {
			int widgetId = Integer.parseInt(separateString[1]);
			String widgetClass = separateString[0];
			try {
				return new WidgetDescriptor(widgetId, widgetClass);
			} catch (ClassNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
