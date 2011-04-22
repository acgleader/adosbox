package org.hystudio.android.dosbox;

import java.io.File;
import java.lang.reflect.Method;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

abstract class DifferentTouchInput {
	public static DifferentTouchInput getInstance() {
		boolean multiTouchAvailable1 = false;
		boolean multiTouchAvailable2 = false;
		// Not checking for getX(int), getY(int) etc 'cause I'm lazy
		Method methods[] = MotionEvent.class.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equals("getPointerCount"))
				multiTouchAvailable1 = true;
			if (m.getName().equals("getPointerId"))
				multiTouchAvailable2 = true;
		}

		if (multiTouchAvailable1 && multiTouchAvailable2)
			return MultiTouchInput.Holder.sInstance;
		else
			return SingleTouchInput.Holder.sInstance;
	}

	public abstract void process(final MotionEvent event);

	private static class SingleTouchInput extends DifferentTouchInput {
		private static class Holder {
			private static final SingleTouchInput sInstance = new SingleTouchInput();
		}

		public void process(final MotionEvent event) {
			int action = -1;
			if (event.getAction() == MotionEvent.ACTION_DOWN)
				action = 0;
			if (event.getAction() == MotionEvent.ACTION_UP)
				action = 1;
			if (event.getAction() == MotionEvent.ACTION_MOVE)
				action = 2;
			if (action >= 0)
				DemoGLSurfaceView.nativeMouse((int) event.getX(),
						(int) event.getY(), action, 0,
						(int) (event.getPressure() * 1000.0),
						(int) (event.getSize() * 1000.0));
		}
	}

	private static class MultiTouchInput extends DifferentTouchInput {

		private static final int touchEventMax = 16; // Max multitouch pointers

		private class touchEvent {
			public boolean down = false;
			public int x = 0;
			public int y = 0;
			public int pressure = 0;
			public int size = 0;
		}

		private touchEvent touchEvents[];

		MultiTouchInput() {
			touchEvents = new touchEvent[touchEventMax];
			for (int i = 0; i < touchEventMax; i++)
				touchEvents[i] = new touchEvent();
		}

		private static class Holder {
			private static final MultiTouchInput sInstance = new MultiTouchInput();
		}

		public void process(final MotionEvent event) {
			int action = -1;

			if (event.getAction() == MotionEvent.ACTION_UP) {
				action = 1;
				for (int i = 0; i < touchEventMax; i++) {
					if (touchEvents[i].down) {
						touchEvents[i].down = false;
						DemoGLSurfaceView.nativeMouse(touchEvents[i].x,
								touchEvents[i].y, action, i,
								touchEvents[i].pressure, touchEvents[i].size);
					}
				}
			}
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				action = 0;
				for (int i = 0; i < event.getPointerCount(); i++) {
					int id = event.getPointerId(i);
					if (id >= touchEventMax)
						id = touchEventMax - 1;
					touchEvents[id].down = true;
					touchEvents[id].x = (int) event.getX(i);
					touchEvents[id].y = (int) event.getY(i);
					touchEvents[id].pressure = (int) (event.getPressure(i) * 1000.0);
					touchEvents[id].size = (int) (event.getSize(i) * 1000.0);
					DemoGLSurfaceView.nativeMouse(touchEvents[i].x,
							touchEvents[i].y, action, id,
							touchEvents[i].pressure, touchEvents[i].size);
				}
			}

			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				for (int i = 0; i < touchEventMax; i++) {
					int ii;
					for (ii = 0; ii < event.getPointerCount(); ii++) {
						if (i == event.getPointerId(ii))
							break;
					}
					if (ii >= event.getPointerCount()) {
						// Up event
						if (touchEvents[i].down) {
							action = 1;
							touchEvents[i].down = false;
							DemoGLSurfaceView.nativeMouse(touchEvents[i].x,
									touchEvents[i].y, action, i,
									touchEvents[i].pressure,
									touchEvents[i].size);
						}
					} else {
						int id = event.getPointerId(ii);
						if (id >= touchEventMax)
							id = touchEventMax - 1;
						if (touchEvents[id].down)
							action = 2;
						else
							action = 0;
						touchEvents[id].down = true;
						touchEvents[id].x = (int) event.getX(i);
						touchEvents[id].y = (int) event.getY(i);
						touchEvents[id].pressure = (int) (event.getPressure(i) * 1000.0);
						touchEvents[id].size = (int) (event.getSize(i) * 1000.0);
						DemoGLSurfaceView.nativeMouse(touchEvents[i].x,
								touchEvents[i].y, action, id,
								touchEvents[i].pressure, touchEvents[i].size);
					}
				}
			}
		}
	}
}

class DemoRenderer extends GLSurfaceView_SDL.Renderer {

	public DemoRenderer(MainActivity _context) {
		context = _context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		System.out.println("libSDL: DemoRenderer.onSurfaceCreated(): paused "
				+ mPaused + " mFirstTimeStart " + mFirstTimeStart);
		mGlSurfaceCreated = true;
		if (!mPaused && !mFirstTimeStart)
			nativeGlContextRecreated();
		mFirstTimeStart = false;
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		nativeResize(w, h, Globals.KeepAspectRatio ? 1 : 0);
	}

	public void onSurfaceDestroyed() {
		mGlSurfaceCreated = false;
		mGlContextLost = true;
		nativeGlContextLost();
	};

	public void onDrawFrame(GL10 gl) {

		nativeInitJavaCallbacks();

		// Make main thread priority lower so audio thread won't get underrun
		// Thread.currentThread().setPriority((Thread.currentThread().getPriority()
		// + Thread.MIN_PRIORITY)/2);

		mGlContextLost = false;

		String libs[] = { "application", "sdl_main" };
		try {
			for (String l : libs) {
				System.loadLibrary(l);
			}
		} catch (UnsatisfiedLinkError e) {
			for (String l : libs) {
				String libname = System.mapLibraryName(l);
				File libpath = new File(context.getCacheDir(), libname);
				System.out.println("libSDL: loading lib " + libpath.getPath());
				System.load(libpath.getPath());
				libpath.delete();
			}
		}

		Settings.Apply(context);
		accelerometer = new AccelerometerReader(context);
		// Tweak video thread priority, if user selected big audio buffer
		if (Globals.AudioBufferConfig >= 2)
			Thread.currentThread().setPriority(
					(Thread.NORM_PRIORITY + Thread.MIN_PRIORITY) / 2); // Lower
																		// than
																		// normal
		nativeInit(Globals.DataDir, Globals.CommandLine,
				(Globals.SwVideoMode && Globals.MultiThreadedVideo) ? 1 : 0); // Calls
																				// main()
																				// and
																				// never
																				// returns,
																				// hehe
																				// -
																				// we'll
																				// call
																				// eglSwapBuffers()
																				// from
																				// native
																				// code
		
		context.onDestroy();
	}

	public int swapBuffers() // Called from native code
	{
		synchronized (this) {
			this.notify();
		}
		if (!super.SwapBuffers() && Globals.NonBlockingSwapBuffers)
			return 0;
		if (mGlContextLost) {
			mGlContextLost = false;
			Settings.SetupTouchscreenKeyboardGraphics(context); // Reload
																// on-screen
																// buttons
																// graphics
		}

		return 1;
	}

	public void showScreenKeyboard(final String oldText, int sendBackspace) // Called
																			// from
																			// native
																			// code
	{
		class Callback implements Runnable {
			public MainActivity parent;
			public String oldText;
			public boolean sendBackspace;

			public void run() {
				parent.showScreenKeyboard(oldText, sendBackspace);
			}
		}
		Callback cb = new Callback();
		cb.parent = context;
		cb.oldText = oldText;
		cb.sendBackspace = (sendBackspace != 0);
		context.runOnUiThread(cb);
	}

	public void exitApp() {
		nativeDone();
	};

	private native void nativeInitJavaCallbacks();

	private native void nativeInit(String CurrentPath, String CommandLine,
			int multiThreadedVideo);

	private native void nativeResize(int w, int h, int keepAspectRatio);

	private native void nativeDone();

	private native void nativeGlContextLost();

	public native void nativeGlContextRecreated();

	public static native void nativeTextInput(int ascii, int unicode);

	public static native void nativeTextInputFinished();

	private MainActivity context = null;
	private AccelerometerReader accelerometer = null;

	private EGL10 mEgl = null;
	private EGLDisplay mEglDisplay = null;
	private EGLSurface mEglSurface = null;
	private EGLContext mEglContext = null;
	private boolean mGlContextLost = false;
	public boolean mGlSurfaceCreated = false;
	public boolean mPaused = false;
	private boolean mFirstTimeStart = true;
}

class DemoGLSurfaceView extends GLSurfaceView_SDL {
	public DemoGLSurfaceView(MainActivity context) {
		super(context);
		mParent = context;
		touchInput = DifferentTouchInput.getInstance();
		setEGLConfigChooser(Globals.NeedDepthBuffer);
		mRenderer = new DemoRenderer(context);
		setRenderer(mRenderer);
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		touchInput.process(event);
		// Wait a bit, and try to synchronize to app framerate, or event thread
		// will eat all CPU and we'll lose FPS
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			synchronized (mRenderer) {
				try {
					mRenderer.wait(300L);
				} catch (InterruptedException e) {
				}
			}
		}
		return true;
	};

	public void exitApp() {
		mRenderer.exitApp();
	};

	@Override
	public void onPause() {
		super.onPause();
		mRenderer.mPaused = true;
	};

	public boolean isPaused() {
		return mRenderer.mPaused;
	}

	@Override
	public void onResume() {
		super.onResume();
		mRenderer.mPaused = false;
		System.out
				.println("libSDL: DemoGLSurfaceView.onResume(): mRenderer.mGlSurfaceCreated "
						+ mRenderer.mGlSurfaceCreated
						+ " mRenderer.mPaused "
						+ mRenderer.mPaused);
		if (mRenderer.mGlSurfaceCreated && !mRenderer.mPaused
				|| Globals.NonBlockingSwapBuffers)
			mRenderer.nativeGlContextRecreated();
	};

	private boolean shiftPressed = false;
	@Override
	public boolean onKeyDown(int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU)
			mParent.showScreenKeyboard("", false);
		else if (keyCode == KeyEvent.KEYCODE_SEARCH)
			mParent.showSDLSettings();
		else if (keyCode == KeyEvent.KEYCODE_PERIOD && shiftPressed)
			nativeKey(KeyEvent.KEYCODE_SEMICOLON, 1);
		else {
			if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT)
				shiftPressed = true;
			nativeKey(keyCode, 1);
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_PERIOD && shiftPressed) {
			nativeKey(KeyEvent.KEYCODE_SEMICOLON, 0);
			return true;
		}
		
		if (keyCode != KeyEvent.KEYCODE_MENU && keyCode != KeyEvent.KEYCODE_SEARCH) {
			if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT)
				shiftPressed = false;
			
			nativeKey(keyCode, 0);
		}
		return true;
	}

	DemoRenderer mRenderer;
	MainActivity mParent;
	DifferentTouchInput touchInput = null;

	public static native void nativeMouse(int x, int y, int action,
			int pointerId, int pressure, int radius);

	public static native void nativeKey(int keyCode, int down);

	public static native void initJavaCallbacks();

}
