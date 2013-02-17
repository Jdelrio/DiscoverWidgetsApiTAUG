package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.R;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools;
import org.juliendelrio.taug.discoverwidgets.tools.WidgetDescriptor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentWidgetsList extends SherlockFragment {

	public static FragmentWidgetsList newInstance() {
		FragmentWidgetsList fragment = new FragmentWidgetsList();
		return fragment;
	}

	private TextView mTvAvailableCount;
	private ListView mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_widgetslist, null);
		mTvAvailableCount = (TextView) view
				.findViewById(R.id.widgetslist_tv_availablecount);
		mList = (ListView) view.findViewById(R.id.list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		WidgetDescriptor[] widgetsArray = SharedPreferencesTools
				.getWidgetsArray(getActivity());
		mList.setAdapter(new LocalAdapter(widgetsArray));

		StringBuilder builder = new StringBuilder();
		builder.append(getString(R.string.launcher_widgetslist_available));
		builder.append(" ");
		builder.append(widgetsArray.length);
		mTvAvailableCount.setText(builder.toString());
		super.onActivityCreated(savedInstanceState);
	}

	private class LocalAdapter extends BaseAdapter {

		private WidgetDescriptor[] mWidgets;

		public LocalAdapter(WidgetDescriptor[] objects) {
			mWidgets = objects;
		}

		@Override
		public int getCount() {
			return mWidgets.length;
		}

		@Override
		public Object getItem(int position) {
			return mWidgets[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						android.R.layout.simple_list_item_1, null);
				holder = new Holder();
				holder.tv = (TextView) convertView
						.findViewById(android.R.id.text1);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			WidgetDescriptor currentWidget = mWidgets[position];
			StringBuilder builder = new StringBuilder();
			try {
				builder.append(currentWidget.getWidgetClass().getSimpleName());
			} catch (ClassNotFoundException e) {
				builder.append("ClassNotFound");
			}
			builder.append(" ");
			builder.append(currentWidget.getWidgetId());
			holder.tv.setText(builder.toString());
			return convertView;
		}

		private class Holder {
			TextView tv;
		}

	}
}
