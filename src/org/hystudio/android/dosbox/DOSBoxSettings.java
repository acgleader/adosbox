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
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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

    final LinearLayout cpuIncLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.cpucycle_inc_ll);
    cpuIncLL.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        increaseCPUCycles(p);
        updateView(dosboxSettingView);
      }
    });

    final LinearLayout cpuDecLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.cpucycle_dec_ll);
    cpuDecLL.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        decreaseCPUCycles(p);
        updateView(dosboxSettingView);
      }
    });

    final LinearLayout frameskipIncLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.frameskip_inc_ll);
    frameskipIncLL.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        increaseFrameskip(p);
        updateView(dosboxSettingView);
      }
    });

    final LinearLayout frameskipDecLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.frameskip_dec_ll);
    frameskipDecLL.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View paramView) {
        decreaseFrameskip(p);
        updateView(dosboxSettingView);
      }
    });

    // CPU cycle change
    final LinearLayout cpuCycleUpLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.cyclechange_up_ll);
    cpuCycleUpLL.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View paramView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(p);
        LinearLayout ll = new LinearLayout(p);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        final EditText cycleChangeET = new EditText(p);
        cycleChangeET.setSingleLine();
        cycleChangeET.setWidth(200);
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
        return true;
      }
    });

    final LinearLayout cpuCycleDownLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.cyclechange_down_ll);
    cpuCycleDownLL.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View paramView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(p);
        LinearLayout ll = new LinearLayout(p);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        final EditText cycleChangeET = new EditText(p);
        cycleChangeET.setSingleLine();
        cycleChangeET.setWidth(200);
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
        return true;
      }
    });

    final LinearLayout configSaveLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.config_save_ll);
    configSaveLL.setOnClickListener(new OnClickListener() {
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
                final File file = new File(Settings.getDOSBoxConfDir(), filename);
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

    final LinearLayout configLoadLL = (LinearLayout) dosboxSettingView
        .findViewById(R.id.config_load_ll);
    configLoadLL.setOnClickListener(new OnClickListener() {
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
    TextView incTV = (TextView) dosboxSettingView
        .findViewById(R.id.cpucycleshow_inc_tv);
    TextView decTV = (TextView) dosboxSettingView
        .findViewById(R.id.cpucycleshow_dec_tv);
    TextView cycleUpTV = (TextView) dosboxSettingView
        .findViewById(R.id.cpucycleup_show_tv);
    TextView cycleDownTV = (TextView) dosboxSettingView
        .findViewById(R.id.cpucycledown_show_tv);
    TextView frameskipIncTV = (TextView) dosboxSettingView
        .findViewById(R.id.frameskip_show_inc_tv);
    TextView frameskipDecTV = (TextView) dosboxSettingView
        .findViewById(R.id.frameskip_show_dec_tv);

    int cycles = nativeGetCPUCycle();
    int cycleUp = nativeGetCPUCycleUp();
    int cycleDown = nativeGetCPUCycleDown();
    int frameskip = nativeGetFrameskip();

    incTV.setText(cycles + "");
    decTV.setText(cycles + "");
    cycleUpTV.setText(cycleUp + "");
    cycleDownTV.setText(cycleDown + "");
    frameskipIncTV.setText(frameskip + "");
    frameskipDecTV.setText(frameskip + "");
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
      Toast.makeText(p, "No config file is found. SD card is busy?", 3000).show();
      return;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(p);
    final String[] filenames = new String[configFiles.size()];
    int idxSelected = -1;
    for (int i = 0; i < configFiles.size(); ++i) {
      if (configFiles.get(i).getAbsolutePath().equals(Settings.dosboxConfFileFullPath))
        idxSelected =i ;
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
