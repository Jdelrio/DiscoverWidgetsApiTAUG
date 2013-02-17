package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentDiscover extends SherlockFragment {

	public static FragmentDiscover newInstance() {
		FragmentDiscover fragment = new FragmentDiscover();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_discover, null);
		return view;
	}
}
