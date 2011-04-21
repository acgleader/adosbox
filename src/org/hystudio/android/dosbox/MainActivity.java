package org.hystudio.android.dosbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//
//        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//            Globals.DownloadToSdcard = false;
//        }
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                
		if(Globals.InhibitSuspend)
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		initSDL();
		setContentView(_videoLayout);

		if(mAudioThread == null) // Starting from background (should not happen)
		{
			System.out.println("libSDL: Loading libraries");
			LoadLibraries();
			mAudioThread = new AudioThread(this);
			System.out.println("libSDL: Loading settings");
			Settings.Load(this);
		}
		
		mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	public void showSDLSettings() {
		Settings.showConfig(this);
	}
	
	public void setUpStatusLabel()
	{
		MainActivity Parent = this; // Too lazy to rename
		if( Parent._btn != null )
		{
			Parent._layout2.removeView(Parent._btn);
			Parent._btn = null;
		}
		if( Parent._layout2 != null && Parent._tv == null )
		{
			Parent._tv = new TextView(Parent);
			Parent._tv.setMaxLines(1);
			Parent._tv.setText(R.string.init);
			Parent._layout2.addView(Parent._tv);
		}
	}

	private void initSDL()
	{
		if(sdlInited)
			return;
		System.out.println("libSDL: Initializing video and SDL application");
		sdlInited = true;
		if(Globals.UseAccelerometerAsArrowKeys)
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_videoLayout = new FrameLayout(this);
		setContentView(_videoLayout);
		mGLView = new DemoGLSurfaceView(this);
		_videoLayout.addView(mGLView);
		// Receive keyboard events
		mGLView.setFocusableInTouchMode(true);
		mGLView.setFocusable(true);
		mGLView.requestFocus();
	}

	@Override
	protected void onPause() {
		_isPaused = true;
		if( mGLView != null )
			mGLView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if( mGLView != null )
			mGLView.onResume();

		_isPaused = false;
	}
	
	public boolean isPaused()
	{
		return _isPaused;
	}

	@Override
	protected void onDestroy() 
	{
		mgr.hideSoftInputFromWindow(mGLView.getWindowToken(), 0);
		if( mGLView != null )
			mGLView.exitApp();
		super.onDestroy();
		System.exit(0);
	}

	private InputMethodManager mgr;
	private boolean softKeyboardVisible;
	
	public void showScreenKeyboard(final String oldText, boolean sendBackspace)
	{
		//toggle soft keyboard
		if (softKeyboardVisible) {
			mgr.hideSoftInputFromWindow(mGLView.getWindowToken(), 0);
		}
		else {
			mgr.showSoftInput(mGLView, InputMethodManager.SHOW_FORCED);
			mGLView.requestFocus();
		}
		softKeyboardVisible = !softKeyboardVisible;
	};

	@Override
	public boolean onKeyDown(int keyCode, final KeyEvent event) {
		// Overrides Back key to use in our app
		if(_screenKeyboard != null)
			_screenKeyboard.onKeyDown(keyCode, event);
		else
		if( mGLView != null )
			 mGLView.nativeKey( keyCode, 1 );
		else
		if( keyListener != null )
		{
			keyListener.onKeyEvent(keyCode);
		}
		return true;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, final KeyEvent event) {
		if(_screenKeyboard != null) {
			_screenKeyboard.onKeyUp(keyCode, event);
		} else
		if( mGLView != null )
			mGLView.nativeKey( keyCode, 0 );
		return true;
	}
	
	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		if(_screenKeyboard != null)
			_screenKeyboard.dispatchTouchEvent(ev);
		else
		if(mGLView != null)
			mGLView.onTouchEvent(ev);
		else
		if( _btn != null )
			return _btn.dispatchTouchEvent(ev);
		else
		if( touchListener != null )
			touchListener.onTouchEvent(ev);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Do nothing here
	}
	
	public void setText(final String t)
	{
		class Callback implements Runnable
		{
			MainActivity Parent;
			public String text;
			public void run()
			{
				Parent.setUpStatusLabel();
				if(Parent._tv != null)
					Parent._tv.setText(text);
			}
		}
		Callback cb = new Callback();
		cb.text = new String(t);
		cb.Parent = this;
		this.runOnUiThread(cb);
	}

	public void showTaskbarNotification()
	{
		showTaskbarNotification("SDL application paused", "SDL application", "Application is paused, click to activate");
	}

	// Stolen from SDL port by Mamaich
	public void showTaskbarNotification(String text0, String text1, String text2)
	{
		NotificationManager NotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification n = new Notification(R.drawable.icon, text0, System.currentTimeMillis());
		n.setLatestEventInfo(this, text1, text2, pendingIntent);
		NotificationManager.notify(NOTIFY_ID, n);
	}

	public void hideTaskbarNotification()
	{
		NotificationManager NotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationManager.cancel(NOTIFY_ID);
	}
	
	public void LoadLibraries()
	{
		try
		{
			for(String l : Globals.AppLibraries)
			{
				System.loadLibrary(l);
			}
		}
		catch ( UnsatisfiedLinkError e )
		{
			try {
				System.out.println("libSDL: Extracting APP2SD-ed libs");
				
				InputStream in = null;
				try
				{
					for( int i = 0; ; i++ )
					{
						InputStream in2 = getAssets().open("bindata" + String.valueOf(i));
						if( in == null )
							in = in2;
						else
							in = new SequenceInputStream( in, in2 );
					}
				}
				catch( IOException ee ) { }

				if( in == null )
					throw new RuntimeException("libSDL: Extracting APP2SD-ed libs failed, the .apk file packaged incorrectly");

				ZipInputStream zip = new ZipInputStream(in);

				File cacheDir = getCacheDir();
				try {
					cacheDir.mkdirs();
				} catch( SecurityException ee ) { };
				
				byte[] buf = new byte[16384];
				while(true)
				{
					ZipEntry entry = null;
					entry = zip.getNextEntry();
					/*
					if( entry != null )
						System.out.println("Extracting lib " + entry.getName());
					*/
					if( entry == null )
					{
						System.out.println("Extracting libs finished");
						break;
					}
					if( entry.isDirectory() )
					{
						System.out.println("Warning '" + entry.getName() + "' is a directory");
						continue;
					}

					OutputStream out = null;
					String path = cacheDir.getAbsolutePath() + "/" + entry.getName();

					System.out.println("Saving to file '" + path + "'");

					out = new FileOutputStream( path );
					int len = zip.read(buf);
					while (len >= 0)
					{
						if(len > 0)
							out.write(buf, 0, len);
						len = zip.read(buf);
					}

					out.flush();
					out.close();
				}

				for(String l : Globals.AppLibraries)
				{
					String libname = System.mapLibraryName(l);
					File libpath = new File(cacheDir, libname);
					System.out.println("libSDL: loading lib " + libpath.getPath());
					System.load(libpath.getPath());
					libpath.delete();
				}
			}
			catch ( Exception ee )
			{
				System.out.println("libSDL: Error: " + e.toString());
			}
		}
	};

	public FrameLayout getVideoLayout() { return _videoLayout; }

	static int NOTIFY_ID = 12367098; // Random ID

	private static DemoGLSurfaceView mGLView = null;
	private static AudioThread mAudioThread = null;

	private TextView _tv = null;
	private Button _btn = null;
	private LinearLayout _layout = null;
	private LinearLayout _layout2 = null;

	private FrameLayout _videoLayout = null;
	private EditText _screenKeyboard = null;
	private boolean sdlInited = false;
	public Settings.TouchEventsListener touchListener = null;
	public Settings.KeyEventsListener keyListener = null;
	boolean _isPaused = false;

	public LinkedList<Integer> textInput = new LinkedList<Integer> ();

}
