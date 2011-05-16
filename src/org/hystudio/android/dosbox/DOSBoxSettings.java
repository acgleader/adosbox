/**
 * 
 */
package org.hystudio.android.dosbox;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zhguo
 * 
 */
public class DOSBoxSettings {
  private static AlertDialog dialog = null;

  protected static void showConfigMainMenu(final MainActivity p) {
    if (dialog != null) {
      dialog.show();
      return;
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(p);
    builder.setTitle(p.getResources().getString(R.string.dosbox_settings));

    LayoutInflater inflater = (LayoutInflater) p
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final ViewGroup dosboxSettingView = (ViewGroup) inflater.inflate(
        R.layout.dosbox_settings, null);

    ScrollView scrollView = (ScrollView) dosboxSettingView
        .findViewById(R.id.scroll);
    scrollView.setScrollbarFadingEnabled(false);

    final Button cpuIncBt = (Button) dosboxSettingView
        .findViewById(R.id.cycle_inc_bt);
    cpuIncBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        increaseCPUCycles(p);
        updateView(dosboxSettingView);
      }
    });

    final Button cpuDecBt = (Button) dosboxSettingView
        .findViewById(R.id.cycle_dec_bt);
    cpuDecBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        decreaseCPUCycles(p);
        updateView(dosboxSettingView);
      }
    });

    final Button frameskipIncBt = (Button) dosboxSettingView
        .findViewById(R.id.frameskip_inc_bt);
    frameskipIncBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        increaseFrameskip(p);
        updateView(dosboxSettingView);
      }
    });

    final Button frameskipDecBt = (Button) dosboxSettingView
        .findViewById(R.id.frameskip_dec_bt);
    frameskipDecBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        decreaseFrameskip(p);
        updateView(dosboxSettingView);
      }
    });

    // CPU cycle change
    final Button cpuCycleUpBt = (Button) dosboxSettingView
        .findViewById(R.id.cycleup_step_bt);
    cpuCycleUpBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(p);
        LinearLayout ll = new LinearLayout(p);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        final EditText cycleChangeET = new EditText(p);
        cycleChangeET.setSingleLine();
        cycleChangeET.setWidth(200);
        cycleChangeET.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll.addView(cycleChangeET);

        TextView helpView = new TextView(p);
        helpView.setText("hint: >= 100, absolute value; < 100, percentage");
        helpView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        ll.addView(helpView);

        builder.setView(ll);
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface paramDialogInterface,
                  int paramInt) {
                String strCycleChange = cycleChangeET.getText().toString();
                try {
                  int cycleChange = Integer.valueOf(strCycleChange);
                  int max = 1000, min = 5;
                  if (cycleChange > max || cycleChange < min) {
                    Toast.makeText(
                        p,
                        String.format("Entered value is not correct [%d-%d]",
                            min, max), 2000).show();
                    return;
                  }
                  nativeSetCPUCycleUp(cycleChange);
                  updateView(dosboxSettingView);
                } catch (Exception e) {
                  Log.e("aDOSBox", e.getMessage());
                }
              }
            });
        dialog.show();
      }
    });

    final Button cpuCycleDownBt = (Button) dosboxSettingView
        .findViewById(R.id.cycledown_step_bt);
    cpuCycleDownBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(p);
        LinearLayout ll = new LinearLayout(p);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        final EditText cycleChangeET = new EditText(p);
        cycleChangeET.setSingleLine();
        cycleChangeET.setWidth(200);
        cycleChangeET.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll.addView(cycleChangeET);

        TextView helpView = new TextView(p);
        helpView.setText("hint: >= 100, absolute value; < 100, percentage");
        helpView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        ll.addView(helpView);

        builder.setView(ll);
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface paramDialogInterface,
                  int paramInt) {
                String strCycleChange = cycleChangeET.getText().toString();
                try {
                  int cycleChange = Integer.valueOf(strCycleChange);
                  int max = 1000, min = 5;
                  if (cycleChange > max || cycleChange < min) {
                    Toast.makeText(
                        p,
                        String.format("Entered value is not correct [%d-%d]",
                            min, max), 2000).show();
                    return;
                  }
                  nativeSetCPUCycleDown(cycleChange);
                  updateView(dosboxSettingView);
                } catch (Exception e) {
                  Log.e("aDOSBox", e.getMessage());
                }
              }
            });
        dialog.show();
      }
    });

    final Button configSaveBt = (Button) dosboxSettingView
        .findViewById(R.id.config_save_bt);
    configSaveBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
        // TODO: to implement a file chooser
        // Intent intent = new Intent();
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.setType("*/*");
        // Intent.createChooser(intent, "Choose where to save");
        // p.startActivityForResult(intent, p.SELECT_CONFIG);

        AlertDialog.Builder builder = new AlertDialog.Builder(p);

        ScrollView sv = new ScrollView(p);
        sv.setLayoutParams(new ScrollView.LayoutParams(
            ScrollView.LayoutParams.WRAP_CONTENT,
            ScrollView.LayoutParams.WRAP_CONTENT));

        sv.setScrollbarFadingEnabled(false);
        LinearLayout ll = new LinearLayout(p);

        ll.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView dirTV = new TextView(p);
        StringBuilder sb = new StringBuilder();
        sb.append("Dir: " + Settings.getDOSBoxConfDir());
        sb.append("\nExisting config:\n");
        boolean isfirst = true;
        for (File file : listConfigFiles()) {
          if (!isfirst) {
            sb.append("\n");
          }
          isfirst = false;
          sb.append("    " + file.getName());
        }
        dirTV.setText(sb.toString());
        dirTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ll.addView(dirTV);

        final EditText filenameET = new EditText(p);
        filenameET.setSingleLine();
        filenameET.setWidth(200);
        ll.addView(filenameET);

        TextView helpView = new TextView(p);
        helpView.setText("Type name of config file you want to save");
        helpView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ll.addView(helpView);

        sv.addView(ll);
        builder.setView(sv);
        builder.setTitle("Save config file");
        AlertDialog dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface paramDialogInterface,
                  int paramInt) {
                String filename = filenameET.getText().toString();
                final File file = new File(Settings.getDOSBoxConfDir(),
                    filename);
                if (file.exists()) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(p);
                  builder.setTitle("Overwrite exisint file?");
                  builder.setPositiveButton("Overwrite",
                      new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          saveConfigFile(file);
                        }
                      });
                  builder.setNegativeButton("Cancel",
                      new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                      });
                  builder.show();
                } else {
                  saveConfigFile(file);
                }
              }
            });
        dialog.show();
      }
    });

    final Button configLoadBt = (Button) dosboxSettingView
        .findViewById(R.id.config_load_bt);
    configLoadBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
        chooseConfigFile(p);
      }
    });

    builder.setView(dosboxSettingView);

    dialog = builder.create();
    dialog.setOnShowListener(new OnShowListener() {
      @Override
      public void onShow(DialogInterface paramDialogInterface) {
        updateView(dosboxSettingView);
      }
    });
    dialog.show();
  }

  private static void saveConfigFile(File file) {
    String absFileName = file.getAbsolutePath();
    nativeDOSBoxWriteConfig(absFileName);
  }

  private static void updateView(ViewGroup dosboxSettingView) {
    TextView cycleTV = (TextView) dosboxSettingView
        .findViewById(R.id.cpucycleshow_tv);
    Button cycleUpBt = (Button) dosboxSettingView
        .findViewById(R.id.cycleup_step_bt);
    Button cycleDownBt = (Button) dosboxSettingView
        .findViewById(R.id.cycledown_step_bt);
    TextView frameskipTV = (TextView) dosboxSettingView
        .findViewById(R.id.frameskip_show__tv);

    int cycles = nativeGetCPUCycle();
    int cycleUp = nativeGetCPUCycleUp();
    int cycleDown = nativeGetCPUCycleDown();
    int frameskip = nativeGetFrameskip();

    cycleTV.setText(cycles + "");
    cycleUpBt.setText("\u21e7(" + cycleUp + ")");
    cycleDownBt.setText("\u21e9(" + cycleDown + ")");
    frameskipTV.setText(frameskip + "");
  }

  private static void increaseCPUCycles(Context p) {
    nativeCPUCycleIncrease();
  }

  private static void decreaseCPUCycles(Context p) {
    nativeCPUCycleDecrease();
  }

  private static void increaseFrameskip(Context p) {
    nativeFrameskipIncrease();
  }

  private static void decreaseFrameskip(Context p) {
    nativeFrameskipDecrease();
  }

  private static String configFileChoosed = null;

  private static void chooseConfigFile(final Activity p) {
    final List<File> configFiles = listConfigFiles();
    if (configFiles == null) {
      Toast.makeText(p, "No config file is found. SD card is busy?", 3000)
          .show();
      return;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(p);
    final String[] filenames = new String[configFiles.size()];
    int idxSelected = -1;
    for (int i = 0; i < configFiles.size(); ++i) {
      if (configFiles.get(i).getAbsolutePath().equals(
          Settings.dosboxConfFileFullPath))
        idxSelected = i;
      filenames[i] = configFiles.get(i).getName();
    }

    builder.setTitle("Select config file");
    builder.setSingleChoiceItems(filenames, idxSelected,
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            configFileChoosed = configFiles.get(which).getAbsolutePath();
          }
        });
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (configFileChoosed == null) {
          Toast.makeText(p, "You have not choosen any file", 2000).show();
          return;
        }

        String oldCmdLine = Globals.CommandLine;
        Globals.CommandLine = "dosbox -conf " + configFileChoosed;
        Settings.Save(p);
        Globals.CommandLine = oldCmdLine;
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {

      }
    });
    builder.show();
  }

  private static List<File> listConfigFiles() {
    String strDestDir = Settings.getDOSBoxConfDir();
    File destDir = new File(strDestDir);
    if (!destDir.isDirectory()) {
      Log.e("aDOSBox", "config dir " + destDir + " does not exist");
      return null;
    }
    File[] files = destDir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String filename) {
        if (filename.endsWith(".conf"))
          return true;
        return false;
      }
    });
    List<File> configFiles = Arrays.asList(files);
    return configFiles;
  }

  static {
    String libs[] = { "application", "sdl_main" };
    try {
      for (String l : libs) {
        System.loadLibrary(l);
      }
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      Log.e("aDOSBox", e.getMessage());
    }
  }

  private static native void nativeCPUCycleIncrease();

  private static native void nativeCPUCycleDecrease();

  private static native void nativeFrameskipIncrease();

  private static native void nativeFrameskipDecrease();

  private static native int nativeGetCPUCycle();

  private static native int nativeGetCPUCycleUp();

  private static native int nativeGetCPUCycleDown();

  private static native void nativeSetCPUCycleUp(int cycleup);

  private static native void nativeSetCPUCycleDown(int cycledown);

  private static native int nativeGetFrameskip();

  static native int nativeDOSExit();

  static native int nativeDOSBoxWriteConfig(String absFileName);
}
