/*
 SDL - Simple DirectMedia Layer
 Copyright (C) 1997-2011 Sam Lantinga
 Java source code (C) 2009-2011 Sergii Pylypenko

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 */

package org.hystudio.android.dosbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static DemoGLSurfaceView mGLView;

	private boolean _isPaused = false;
	private boolean sdlInited = false;
	private InputMethodManager mgr;
	private FrameLayout _videoLayout;
	public Settings.TouchEventsListener touchListener = null;
	public Settings.KeyEventsListener keyListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		if (Globals.InhibitSuspend)
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		if (!sdlInited) {
			for (String l : Globals.AppLibraries) {
				System.loadLibrary(l);
			}
			new AudioThread(this);
			Settings.Load(this);
		}
	}

	public void showMessage(String messge) {
		Toast t = Toast.makeText(this, messge, 3000);
		t.show();
	}

	public void startDosBox(boolean settingsLoaded) {
		if (sdlInited) {
			showMessage("Config changes may require aDosBox restart to take effect!");
			return;
		}

		if (!settingsLoaded) {
			Settings.settingsLoaded = false;
			Settings.Load2(this); // Load settings again
			return;
		}

		initSDL();
	}

	private void initSDL() {
		if (Globals.UseAccelerometerAsArrowKeys)
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		mGLView = new DemoGLSurfaceView(this);
		// Receive keyboard events
		mGLView.setFocusableInTouchMode(true);
		mGLView.setFocusable(true);
		mGLView.requestFocus();
		_videoLayout = new FrameLayout(this);
		_videoLayout.addView(mGLView);
		addContentView(_videoLayout, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		sdlInited = true;
	}

	@Override
	protected void onPause() {
		_isPaused = true;
		if (mGLView != null)
			mGLView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mGLView != null)
			mGLView.onResume();
		_isPaused = false;
	}

	public boolean isPaused() {
		return _isPaused;
	}

	@Override
	protected void onDestroy() {
		mgr.hideSoftInputFromWindow(mGLView.getWindowToken(), 0);
		if (mGLView != null)
			mGLView.exitApp();
		super.onDestroy();
		System.exit(0);
	}

	public void showScreenKeyboard() {
		mgr.showSoftInput(mGLView, InputMethodManager.SHOW_FORCED);
		mGLView.requestFocus();
	};

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.quit_app:
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setIcon(R.drawable.alert_dialog_icon);
			String confirimationTitle = getString(R.string.quit_confirmation_title);
			alertDialog.setTitle(confirimationTitle);
			alertDialog.setButton(getString(R.string.ok_button),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			alertDialog.setButton2(getString(R.string.cancel_button),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			alertDialog.show();
			return true;
    case R.id.dosbox_settings:
      return true;
		case R.id.joystick:
			Settings.toggleJoyStick();
			return true;
		case R.id.settings:
			Settings.showConfig(this);
			return true;
		case R.id.keyboard:
			showScreenKeyboard();
			return true;
		}

		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, final KeyEvent event) {
		if (keyListener != null) {
			keyListener.onKeyEvent(keyCode);
			return true;
		}
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		if (mGLView != null)
			mGLView.onTouchEvent(ev);
		else if (touchListener != null)
			touchListener.onTouchEvent(ev);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Do nothing here
	}

	public FrameLayout getVideoLayout() {
		return _videoLayout;
	}
}
