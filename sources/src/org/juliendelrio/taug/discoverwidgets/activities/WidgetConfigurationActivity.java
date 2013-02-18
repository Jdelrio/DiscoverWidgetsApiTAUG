package org.juliendelrio.taug.discoverwidgets.activities;

import org.juliendelrio.taug.discoverwidgets.R;
import org.juliendelrio.taug.discoverwidgets.tools.SharedPreferencesTools;
import org.juliendelrio.taug.discoverwidgets.tools.WidgetDescriptorConfiguration;
import org.juliendelrio.taug.discoverwidgets.widgets.WidgetActivity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class WidgetConfigurationActivity extends Activity {

	private int mAppWidgetId;
	private TextView tvName;
	private CheckBox cbHideBG;
	private EditText etCustomTxt;
	private Button bConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.conf_activity);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		tvName = (TextView) findViewById(R.id.confacti_name);
		cbHideBG = (CheckBox) findViewById(R.id.confacti_hidebg);
		etCustomTxt = (EditText) findViewById(R.id.confacti_tv_customtxt);
		bConfirm = (Button) findViewById(R.id.confacti_b_confirm);
		bConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Save Result
				int widgetId = mAppWidgetId;
				Class<? extends AppWidgetProvider> widgetClass = WidgetActivity.class;
				boolean isBgHidden = cbHideBG.isChecked();
				String customText = etCustomTxt.getText().toString();
				WidgetDescriptorConfiguration widget = new WidgetDescriptorConfiguration(
						widgetId, widgetClass, isBgHidden, customText);
				SharedPreferencesTools.set(v.getContext(),
						widget.getStorableString(),
						widget.getStorableStringForComplement());

				// Send result
				Intent resultValue = new Intent();
				resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
						mAppWidgetId);
				setResult(RESULT_OK, resultValue);
				finish();
			}
		});
	}
}
