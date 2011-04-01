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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fullscreen mode
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if(Globals.InhibitSuspend)
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		System.out.println("libSDL: Creating startup screen");
		_layout = new LinearLayout(this);
		_layout.setOrientation(LinearLayout.VERTICAL);
		_layout.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		_layout2 = new LinearLayout(this);
		_layout2.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		_btn = new Button(this);
		_btn.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		_btn.setText(getResources().getString(R.string.device_change_cfg));
		class onClickListener implements View.OnClickListener
		{
				public MainActivity p;
				onClickListener( MainActivity _p ) { p = _p; }
				public void onClick(View v)
				{
					setUpStatusLabel();
					System.out.println("libSDL: User clicked change phone config button");
					Settings.showConfig(p);
				}
		};
		_btn.setOnClickListener(new onClickListener(this));

		_layout2.addView(_btn);

		_layout.addView(_layout2);
		
		ImageView img = new ImageView(this);

		img.setScaleType(ImageView.ScaleType.FIT_CENTER /* FIT_XY */ );
		img.setImageResource(R.drawable.publisherlogo);
		img.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		_layout.addView(img);
		
		_videoLayout = new FrameLayout(this);
		_videoLayout.addView(_layout);
		
		setContentView(_videoLayout);

		if(mAudioThread == null) // Starting from background (should not happen)
		{
			System.out.println("libSDL: Loading libraries");
			LoadLibraries();
			mAudioThread = new AudioThread(this);
			System.out.println("libSDL: Loading settings");
			Settings.Load(this);
		}

		if( !Settings.settingsChanged )
		{
			System.out.println("libSDL: 3-second timeout in startup screen");
			class Callback implements Runnable
			{
				MainActivity p;
				Callback( MainActivity _p ) { p = _p; }
				public void run()
				{
					try {
						Thread.sleep(3000);
					} catch( InterruptedException e ) {};
					if( Settings.settingsChanged )
						return;
					System.out.println("libSDL: Timeout reached in startup screen, process with downloader");
					p.startDownloader();
				}
			};
			Thread changeConfigAlertThread = null;
			changeConfigAlertThread = new Thread(new Callback(this));
			changeConfigAlertThread.start();
		}
		
		mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	public void setUpStatusLabel()
	{
		MainActivity Parent = this; // Too lazy to rename
		if( Parent._btn != null )
		{
			Parent._layout2.removeView(Parent._btn);
			Parent._btn = null;
		}
		if( Parent._tv == null )
		{
			Parent._tv = new TextView(Parent);
			Parent._tv.setMaxLines(1);
			Parent._tv.setText(R.string.init);
			Parent._layout2.addView(Parent._tv);
		}
	}

	public void startDownloader()
	{
		System.out.println("libSDL: Starting data downloader");
		class Callback implements Runnable
		{
			public MainActivity Parent;
			public void run()
			{
				setUpStatusLabel();
				System.out.println("libSDL: Starting downloader");
				if( Parent.downloader == null )
					Parent.downloader = new DataDownloader(Parent, Parent._tv);
			}
		}
		Callback cb = new Callback();
		cb.Parent = this;
		this.runOnUiThread(cb);
	}

	public void initSDL()
	{
		if(sdlInited)
			return;
		System.out.println("libSDL: Initializing video and SDL application");
		sdlInited = true;
		if(Globals.UseAccelerometerAsArrowKeys)
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_videoLayout.removeView(_layout);
		_layout = null;
		_layout2 = null;
		_btn = null;
		_tv = null;
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
		if( downloader != null ) {
			synchronized( downloader ) {
				downloader.setStatusField(null);
			}
		}
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
		else
		if( downloader != null ) {
			synchronized( downloader ) {
				downloader.setStatusField(_tv);
				if( downloader.DownloadComplete )
					initSDL();
			}
		}
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
		
		if( downloader != null ) {
			synchronized( downloader ) {
				downloader.setStatusField(null);
			}
		}
		if( mGLView != null )
			mGLView.exitApp();
		super.onDestroy();
		System.exit(0);
	}

	public void hideScreenKeyboard()
	{
		if(_screenKeyboard == null)
			return;

		synchronized(textInput)
		{
			String text = _screenKeyboard.getText().toString();
			for(int i = 0; i < text.length(); i++)
			{
				DemoRenderer.nativeTextInput( (int)text.charAt(i), (int)text.codePointAt(i) );
			}
		}
		DemoRenderer.nativeTextInputFinished();
		_videoLayout.removeView(_screenKeyboard);
		_screenKeyboard = null;
		mGLView.setFocusableInTouchMode(true);
		mGLView.setFocusable(true);
		mGLView.requestFocus();
	};
	
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
		
//		if(_screenKeyboard != null)
//			return;
//		class myKeyListener implements OnKeyListener 
//		{
//			MainActivity _parent;
//			boolean sendBackspace;
//			myKeyListener(MainActivity parent, boolean sendBackspace) { _parent = parent; this.sendBackspace = sendBackspace; };
//			public boolean onKey(View v, int keyCode, KeyEvent event) 
//			{
//				if ((event.getAction() == KeyEvent.ACTION_UP) && ((keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_BACK)))
//				{
//					_parent.hideScreenKeyboard();
//					return true;
//				}
//				if ((sendBackspace && event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_DEL || keyCode == KeyEvent.KEYCODE_CLEAR))
//				{
//					synchronized(textInput) {
//						DemoRenderer.nativeTextInput( 8, 0 ); // Send backspace to native code
//					}
//					return false; // and proceed to delete text in keyboard input field
//				}
//				return false;
//			}
//		};
//		_screenKeyboard = new EditText(this);
//		_videoLayout.addView(_screenKeyboard);
//		_screenKeyboard.setOnKeyListener(new myKeyListener(this, sendBackspace));
//		_screenKeyboard.setHint(R.string.text_edit_click_here);
//		_screenKeyboard.setText(oldText);
//		final Window window = getWindow();
//		_screenKeyboard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			public void onFocusChange(View v, boolean hasFocus)
//			{
//				if (hasFocus)
//					window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//			}
//		});
//		_screenKeyboard.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.NONE, false));
//		_screenKeyboard.setFocusableInTouchMode(true);
//		_screenKeyboard.setFocusable(true);
//		_screenKeyboard.requestFocus();
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
		if( keyCode == KeyEvent.KEYCODE_BACK && downloader != null )
		{ 
			if( downloader.DownloadFailed )
				System.exit(1);
			if( !downloader.DownloadComplete )
			 onStop();
		}
		else
		if( keyListener != null )
		{
			keyListener.onKeyEvent(keyCode);
		}
		return true;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, final KeyEvent event) {
		if(_screenKeyboard != null)
			_screenKeyboard.onKeyUp(keyCode, event);
		else
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
	private static DataDownloader downloader = null;

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
