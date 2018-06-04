package me.hexian000.nativeprocess;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import me.hexian000.nativeprocess.api.ProcessInfo;

import java.util.List;
import java.util.Locale;

public class ProcessAdapter extends ArrayAdapter<ProcessInfo> {
	private LayoutInflater layoutInflater;
	private Drawable defaultIcon;

	ProcessAdapter(Activity activity, int textViewResourceId,
	               List<ProcessInfo> processList) {
		super(activity, textViewResourceId, processList);
		layoutInflater = activity.getLayoutInflater();
		defaultIcon = activity.getDrawable(R.mipmap.ic_launcher);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			view = layoutInflater.inflate(R.layout.snippet_list_row, parent, false);
		}

		if (position < getCount()) {
			ProcessInfo info = getItem(position);
			if (null != info) {
				TextView appName = view.findViewById(R.id.app_name);
				TextView packageName = view.findViewById(R.id.app_package);
				ImageView iconView = view.findViewById(R.id.app_icon);

				if (info.app != null) {
					appName.setText(String.format(Locale.getDefault(), "[%d] %s", info.pid, info.app.label));
					packageName.setText(String.format(Locale.getDefault(),
							"%s %s %s%%", info.command, NativeProcess.formatSize(info.resident * 1024), NativeProcess.formatDecimal(info.cpu)));
					iconView.setImageDrawable(info.app.icon);
				} else {
					appName.setText(String.format(Locale.getDefault(), "[%d] %s", info.pid, info.name));
					packageName.setText(String.format(Locale.getDefault(),
							"%s %s %s%%", info.command, NativeProcess.formatSize(info.resident * 1024), NativeProcess.formatDecimal(info.cpu)));
					iconView.setImageDrawable(defaultIcon);
				}
			}
		}
		return view;
	}
}
