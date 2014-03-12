/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.impl.android;

import android.app.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.MotionEvent;
import com.codename1.codescan.ScanResult;
import com.codename1.media.Media;
import com.codename1.ui.geom.Dimension;


import android.webkit.CookieSyncManager;
import android.content.*;
import android.content.pm.*;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.codename1.ui.BrowserComponent;

import com.codename1.ui.Component;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.PeerComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.impl.CodenameOneImplementation;
import com.codename1.impl.VirtualKeyboardInterface;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Vector;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.Html;
import android.view.*;
import android.view.View.MeasureSpec;
import android.webkit.*;
import android.widget.*;
import com.codename1.codescan.CodeScanner;
import com.codename1.contacts.Contact;
import com.codename1.db.Database;
import com.codename1.io.BufferedInputStream;
import com.codename1.io.BufferedOutputStream;
import com.codename1.io.*;
import com.codename1.l10n.L10NManager;
import com.codename1.location.LocationManager;
import com.codename1.messaging.Message;
import com.codename1.payment.Purchase;
import com.codename1.push.PushCallback;
import com.codename1.ui.*;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.animations.Animation;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.EventDispatcher;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.codename1.util.StringUtil;
import java.io.*;
import java.net.CookieHandler;
import java.net.ServerSocket;
import java.util.*;
//import android.webkit.JavascriptInterface;

public class AndroidImplementation extends CodenameOneImplementation implements IntentResultListener {

    /**
     * make sure these important keys have a negative value when passed to
     * Codename One or they might be interpreted as characters.
     */
    static final int DROID_IMPL_KEY_LEFT = -23446;
    static final int DROID_IMPL_KEY_RIGHT = -23447;
    static final int DROID_IMPL_KEY_UP = -23448;
    static final int DROID_IMPL_KEY_DOWN = -23449;
    static final int DROID_IMPL_KEY_FIRE = -23450;
    static final int DROID_IMPL_KEY_MENU = -23451;
    static final int DROID_IMPL_KEY_BACK = -23452;
    static final int DROID_IMPL_KEY_BACKSPACE = -23453;
    static final int DROID_IMPL_KEY_CLEAR = -23454;
    static final int DROID_IMPL_KEY_SEARCH = -23455;
    static final int DROID_IMPL_KEY_CALL = -23456;
    static final int DROID_IMPL_KEY_VOLUME_UP = -23457;
    static final int DROID_IMPL_KEY_VOLUME_DOWN = -23458;
    static final int DROID_IMPL_KEY_MUTE = -23459;
    static int[] leftSK = new int[]{DROID_IMPL_KEY_MENU};
    static CodenameOneSurface myView = null;
    private Paint defaultFont;
    private final char[] tmpchar = new char[1];
    private final Rect tmprect = new Rect();
    protected int defaultFontHeight;
    private Vibrator v = null;
    private boolean vibrateInitialized = false;
    private int displayWidth;
    private int displayHeight;
    static Activity activity;
    RelativeLayout relativeLayout;
    final Vector nativePeers = new Vector();
    int lastDirectionalKeyEventReceivedByWrapper;
    private EventDispatcher callback;
    private int timeout = -1;
    private CodeScannerImpl scannerInstance;
    private HashMap apIds;
    private static View viewBelow;
    private static View viewAbove;
    private static int aboveSpacing;
    private static int belowSpacing;
    public static boolean asyncView = false;
    public static boolean textureView = false;
    
    /**
     * This method in used internally for ads
     * @param above shown above the view
     * @param below shown below the view
     */
    public static void setViewAboveBelow(View above, View below, int spacingAbove, int spacingBelow) {
        viewBelow = below;
        viewAbove = above;
        aboveSpacing = spacingAbove;
        belowSpacing = spacingBelow;
    }
    
    /**
     * Copy the input stream into the output stream, closes both streams when finishing or in
     * a case of an exception
     * 
     * @param i source
     * @param o destination
     */
    private static void copy(InputStream i, OutputStream o) throws IOException {
        copy(i, o, 8192);
    }

    /**
     * Copy the input stream into the output stream, closes both streams when finishing or in
     * a case of an exception
     *
     * @param i source
     * @param o destination
     * @param bufferSize the size of the buffer, which should be a power of 2 large enoguh
     */
    private static void copy(InputStream i, OutputStream o, int bufferSize) throws IOException {
        try {
            byte[] buffer = new byte[bufferSize];
            int size = i.read(buffer);
            while(size > -1) {
                o.write(buffer, 0, size);
                size = i.read(buffer);
            }
        } finally {
            sCleanup(o);
            sCleanup(i);
        }
    }

    private static void sCleanup(Object o) {
        try {
            if(o != null) {
                if(o instanceof InputStream) {
                    ((InputStream)o).close();
                    return;
                }
                if(o instanceof OutputStream) {
                    ((OutputStream)o).close();
                    return;
                }
            }
        } catch(Throwable t) {}
    }
    
    /**
     * Copied here since the cleanup method in util would crash append notification that runs when the app isn't in the foreground
     */
    private static byte[] readInputStream(InputStream i) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        copy(i, b);
        return b.toByteArray();
    }
    
    
    public static void appendNotification(String type, String body, Context a) {
        try {
            String[] fileList = a.fileList();
            byte[] data = null;
            for (int iter = 0; iter < fileList.length; iter++) {
                if (fileList[iter].equals("CN1$AndroidPendingNotifications")) {
                    InputStream is = a.openFileInput("CN1$AndroidPendingNotifications");
                    if(is != null) {
                        data = readInputStream(is);
                        sCleanup(a);
                        break;
                    }
                }
            }
            DataOutputStream os = new DataOutputStream(a.openFileOutput("CN1$AndroidPendingNotifications", 0));
            if(data != null) {
                data[0]++;
                os.write(data);
            } else {
                os.writeByte(1);
            }
            if(type != null) {
                os.writeBoolean(true);
                os.writeUTF(type);
            } else {
                os.writeBoolean(false);
            }
            os.writeUTF(body);
            os.writeLong(System.currentTimeMillis());
        } catch(IOException err) {
            err.printStackTrace();
        }
    }

    public static void firePendingPushes(final PushCallback c, Activity a) {
        try {
            if(c != null) {
                InputStream i = a.openFileInput("CN1$AndroidPendingNotifications");
                if(i == null) {
                    return;
                }
                DataInputStream is = new DataInputStream(i);
                int count = is.readByte();
                for(int iter = 0 ; iter < count ; iter++) {
                    boolean hasType = is.readBoolean();
                    String actualType = null;
                    if(hasType) {
                        actualType = is.readUTF();
                    }
                    final String t = actualType;
                    final String b = is.readUTF();
                    long s = is.readLong();
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            Display.getInstance().setProperty("pushType", t);
                            if(t != null && "3".equals(t)) {                                
                                String[] a = b.split(";");
                                c.push(a[0]);
                                c.push(a[1]);
                            } else {
                                c.push(b);
                            }
                        }
                    });
                }
                a.deleteFile("CN1$AndroidPendingNotifications");
            }
        } catch(IOException err) {
            err.printStackTrace();
        }
    }
    
    public static String[] getPendingPush(String type, Context a) {
        InputStream i = null;
        try {
            i = a.openFileInput("CN1$AndroidPendingNotifications");
            if (i == null) {
                return null;
            }
            DataInputStream is = new DataInputStream(i);
            int count = is.readByte();
            Vector v = new Vector<String>();
            for (int iter = 0; iter < count; iter++) {
                boolean hasType = is.readBoolean();
                String actualType = null;
                if (hasType) {
                    actualType = is.readUTF();
                }
                final String t = actualType;
                final String b = is.readUTF();
                long s = is.readLong();
                if (t.equals(type)) {
                    v.add(b);
                }

            }
            String [] retVal = new String[v.size()];
            for (int j = 0; j < retVal.length; j++) {
                retVal[j] = (String)v.get(j);
            }
            return retVal;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(i != null){
                    i.close();
                }
            } catch (IOException ex) {
            }
        }
        return null;
    }
    
    @Override
    public void init(Object m) {
        this.activity = (Activity) m;

        if (!hasActionBar()) {
            try {
                activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            } catch (Exception e) {
                //Log.d("Codename One", "No idea why this throws a Runtime Error", e);
            }
        } else {
            activity.invalidateOptionsMenu();
            try {
                activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                activity.requestWindowFeature(Window.FEATURE_PROGRESS);                
            } catch (Exception e) {
                //Log.d("Codename One", "No idea why this throws a Runtime Error", e);
            }
            NotifyActionBar notify = new NotifyActionBar(activity, false);
            notify.run();
        }
        if(Display.getInstance().getProperty("StatusbarHidden", "").equals("true")){
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        
        if(Display.getInstance().getProperty("KeepScreenOn", "").equals("true")){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (m instanceof CodenameOneActivity) {
            ((CodenameOneActivity) m).setDefaultIntentResultListener(this);
            ((CodenameOneActivity) m).setIntentResultListener(this);
        }

        /**
         * translate our default font height depending on the screen density.
         * this is required for new high resolution devices. otherwise
         * everything looks awfully small.
         *
         * we use our default font height value of 16 and go from there. i
         * thought about using new Paint().getTextSize() for this value but if
         * some new version of android suddenly returns values already tranlated
         * to the screen then we might end up with too large fonts. the
         * documentation is not very precise on that.
         */
        final int defaultFontPixelHeight = 16;
        this.defaultFontHeight = this.translatePixelForDPI(defaultFontPixelHeight);


        this.defaultFont = (Paint) ((NativeFont) this.createFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM)).font;
        Display.getInstance().setTransitionYield(-1);
        
        initSurface();
        /**
         * devices are extremely sensitive so dragging should start a little
         * later than suggested by default implementation.
         */
        this.setDragStartPercentage(1);
        VirtualKeyboardInterface vkb = new AndroidKeyboard(this);
        Display.getInstance().registerVirtualKeyboard(vkb);
        Display.getInstance().setDefaultVirtualKeyboard(vkb);

        InPlaceEditView.endEdit();

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (nativePeers.size() > 0) {
            for (int i = 0; i < nativePeers.size(); i++) {
                ((AndroidImplementation.AndroidPeer) nativePeers.elementAt(i)).init();
            }
        }

        HttpURLConnection.setFollowRedirects(false);
        CookieHandler.setDefault(null);
    }
    
    private static class InvalidateOptionsMenuImpl implements Runnable {
        private Activity activity;

        public InvalidateOptionsMenuImpl(Activity activity) {
            this.activity = activity;
        }
        
        @Override
        public void run() {
            activity.invalidateOptionsMenu();
        }
    }

    private boolean hasActionBar() {
        return android.os.Build.VERSION.SDK_INT >= 11;
    }

    public int translatePixelForDPI(int pixel) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, pixel,
                activity.getResources().getDisplayMetrics());
    }

    /**
     * Returns the platform EDT thread priority
     */
    public int getEDTThreadPriority(){
        return Thread.NORM_PRIORITY;
    }
    
    @Override
    public int getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        /*if (isTablet()) {
            if(getDisplayWidth() > 1400) {
                return Display.DENSITY_VERY_HIGH;
            }
            return Display.DENSITY_MEDIUM;
        }*/
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return Display.DENSITY_LOW;
            case DisplayMetrics.DENSITY_HIGH:
            case 213: // DENSITY_TV 
                return Display.DENSITY_HIGH;
            case DisplayMetrics.DENSITY_XHIGH:
                return Display.DENSITY_VERY_HIGH;
            case 480: // DisplayMetrics.DENSITY_XXHIGH
                return Display.DENSITY_HD;
            default:
                return Display.DENSITY_MEDIUM;
        }
    }

    public static void syncDeinitialize() {
        Display.getInstance().callSerially(new Runnable() {
            @Override
            public void run() {
                Display.deinitialize();
            }
        });
    }
    
    public void deinitialize() {
        //activity.getWindowManager().removeView(relativeLayout);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (nativePeers.size() > 0) {
                    for (int i = 0; i < nativePeers.size(); i++) {
                        ((AndroidImplementation.AndroidPeer) nativePeers.elementAt(i)).deinit();
                    }
                }
                relativeLayout.removeAllViews();
                relativeLayout = null;
                myView = null;
            }
        });
    }
    
    /**
     * init view. a lot of back and forth between this thread and the UI thread.
     */
    private void initSurface() {
        
        relativeLayout=  new RelativeLayout(activity);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT));
        relativeLayout.setFocusable(false);

        activity.getWindow().setBackgroundDrawable(null);
        if(asyncView) {
            if(android.os.Build.VERSION.SDK_INT < 14){
                myView = new AndroidSurfaceView(activity, AndroidImplementation.this);        
            } else {
                int hardwareAcceleration = 16777216;
                activity.getWindow().setFlags(hardwareAcceleration, hardwareAcceleration);
                myView = new AndroidAsyncView(activity, AndroidImplementation.this);                
            }
        } else {
            if(textureView || android.os.Build.VERSION.SDK_INT == 18){
                int hardwareAcceleration = 16777216;
                activity.getWindow().setFlags(hardwareAcceleration, hardwareAcceleration);
                myView = new AndroidTextureView(activity, AndroidImplementation.this);                
            } else {
                myView = new AndroidSurfaceView(activity, AndroidImplementation.this);        
            }
        }
        myView.getAndroidView().setVisibility(View.VISIBLE);

        relativeLayout.addView(myView.getAndroidView());
        myView.getAndroidView().setVisibility(View.VISIBLE);
        
        int id = activity.getResources().getIdentifier("main", "layout", activity.getApplicationInfo().packageName);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(activity).inflate(id, null);
        if(viewAbove != null) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp2.setMargins(0, 0, aboveSpacing, 0);
            relativeLayout.setLayoutParams(lp2);
            root.addView(viewAbove, lp);
        }
        root.addView(relativeLayout);
        if(viewBelow != null) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp2.setMargins(0, 0, 0, belowSpacing);
            relativeLayout.setLayoutParams(lp2);
            root.addView(viewBelow, lp);
        }
        activity.setContentView(root);
        myView.getAndroidView().requestFocus();
    }

    @Override
    public void confirmControlView() {
        myView.getAndroidView().setVisibility(View.VISIBLE);
    }

    public void hideNotifyPublic() {
        super.hideNotify();
        saveTextEditingState();
    }

    public void showNotifyPublic() {
        super.showNotify();
    }

    @Override
    public boolean isMinimized() {
        return ((CodenameOneActivity)activity).isBackground();
    }

    @Override
    public boolean minimizeApplication() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
        return true;
    }

    @Override
    public void restoreMinimizedApplication() {
        Intent i = new Intent(activity, activity.getClass());
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        activity.startActivity(i);
    }

    @Override
    public boolean isNativeInputImmediate() {
        return true;
    }
    
    public void editString(final Component cmp, int maxSize, final int constraint, String text, int keyCode) {
        if (keyCode > 0 && getKeyboardType() == Display.KEYBOARD_TYPE_QWERTY) {
            text += (char) keyCode;
        }
        Display display = Display.getInstance();
        String userInput = InPlaceEditView.edit(this, cmp, constraint);
        display.onEditingComplete(cmp, userInput);
    }

    protected boolean editInProgress() {
        return InPlaceEditView.isEditing();
    }

    public static void stopEditing(){
        final boolean[] flag = new boolean[]{false};

        // InPlaceEditView.endEdit must be called from the UI thread.
        // We must wait for this call to be over, otherwise Codename One's painting
        // of the next form will be garbled.
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Must be called from the UI thread
                InPlaceEditView.endEdit();

                synchronized (flag) {
                    flag[0] = true;
                    flag.notify();
                }
            }
        });

        if (!flag[0]) {
            // Wait (if necessary) for the asynchronous runOnUiThread to do its work
            synchronized (flag) {

                try {
                    flag.wait();
                } catch (InterruptedException e) {
                }
            }
        }        
    }
    
    @Override
    public void saveTextEditingState() {
        stopEditing();
    }

    protected void setLastSizeChangedWH(int w, int h) {
        // not used?
        //this.lastSizeChangeW = w;
        //this.lastSizeChangeH = h;
    }

    /*@Override
    public boolean handleEDTException(final Throwable err) {

        final boolean[] messageComplete = new boolean[]{false};

        Log.e("Codename One", "Err on EDT", err);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIManager m = UIManager.getInstance();
                final FrameLayout frameLayout = new FrameLayout(
                        activity);
                final TextView textView = new TextView(
                        activity);
                textView.setGravity(Gravity.CENTER);
                frameLayout.addView(textView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.FILL_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT));
                textView.setText("An internal application error occurred: " + err.toString());
                AlertDialog.Builder bob = new AlertDialog.Builder(
                        activity);
                bob.setView(frameLayout);
                bob.setTitle("");
                bob.setPositiveButton(m.localize("ok", "OK"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int which) {
                                d.dismiss();
                                synchronized (messageComplete) {
                                    messageComplete[0] = true;
                                    messageComplete.notify();
                                }
                            }
                        });
                AlertDialog editDialog = bob.create();
                editDialog.show();
            }
        });

        synchronized (messageComplete) {
            if (messageComplete[0]) {
                return true;
            }
            try {
                messageComplete.wait();
            } catch (Exception ignored) {
                ;
            }
        }
        return true;
    }*/

    @Override
    public InputStream getResourceAsStream(Class cls, String resource) {
        try {
            if (resource.startsWith("/")) {
                resource = resource.substring(1);
            }
            return activity.getAssets().open(resource);
        } catch (IOException ex) {
            Log.i("Codename One", "Resource not found: " + resource);
            return null;
        }
    }

    @Override
    protected void pointerPressed(final int x, final int y) {
        super.pointerPressed(x, y);
    }

    @Override
    protected void pointerPressed(final int[] x, final int[] y) {
        super.pointerPressed(x, y);
    }

    @Override
    protected void pointerReleased(final int x, final int y) {
        super.pointerReleased(x, y);
    }

    @Override
    protected void pointerReleased(final int[] x, final int[] y) {
        super.pointerReleased(x, y);
    }

    @Override
    protected void pointerDragged(int x, int y) {
        super.pointerDragged(x, y);
    }

    @Override
    protected void pointerDragged(int[] x, int[] y) {
        super.pointerDragged(x, y);
    }

    @Override
    protected int getDragAutoActivationThreshold() {
        return 1000000;
    }

    @Override
    public void flushGraphics() {
        if (myView != null) {
            myView.flushGraphics();
        }

    }

    @Override
    public void flushGraphics(int x, int y, int width, int height) {
        this.tmprect.set(x, y, x + width, y + height);
        if (myView != null) {
            myView.flushGraphics(this.tmprect);
        }
    }

    @Override
    public int charWidth(Object nativeFont, char ch) {
        this.tmpchar[0] = ch;
        float w = (nativeFont == null ? this.defaultFont
                : (Paint) ((NativeFont) nativeFont).font).measureText(this.tmpchar, 0, 1);
        if (w - (int) w > 0) {
            return (int) (w + 1);
        }
        return (int) w;
    }

    @Override
    public int charsWidth(Object nativeFont, char[] ch, int offset, int length) {
        float w = (nativeFont == null ? this.defaultFont
                : (Paint) ((NativeFont) nativeFont).font).measureText(ch, offset, length);
        if (w - (int) w > 0) {
            return (int) (w + 1);
        }
        return (int) w;
    }

    @Override
    public int stringWidth(Object nativeFont, String str) {
        float w = (nativeFont == null ? this.defaultFont
                : (Paint) ((NativeFont) nativeFont).font).measureText(str);
        if (w - (int) w > 0) {
            return (int) (w + 1);
        }
        return (int) w;
    }

    @Override
    public void setNativeFont(Object graphics, Object font) {
        if (font == null) {
            font = this.defaultFont;
        }
        if (font instanceof NativeFont) {
            ((AndroidGraphics) graphics).setFont((Paint) ((NativeFont) font).font);
        } else {
            ((AndroidGraphics) graphics).setFont((Paint) font);
        }
    }

    @Override
    public int getHeight(Object nativeFont) {
        Paint font = (nativeFont == null ? this.defaultFont
                : (Paint) ((NativeFont) nativeFont).font);
        return font.getFontMetricsInt(font.getFontMetricsInt());
    }

    public int getFace(Object nativeFont) {
        if (nativeFont == null) {
            return Font.FACE_SYSTEM;
        }
        return ((NativeFont) nativeFont).face;
    }

    public int getStyle(Object nativeFont) {
        if (nativeFont == null) {
            return Font.STYLE_PLAIN;
        }
        return ((NativeFont) nativeFont).style;
    }

    @Override
    public int getSize(Object nativeFont) {
        if (nativeFont == null) {
            return Font.SIZE_MEDIUM;
        }
        return ((NativeFont) nativeFont).size;
    }
    
    @Override
    public boolean isTrueTypeSupported() {
        return true;
    }

    @Override
    public Object loadTrueTypeFont(String fontName, String fileName) {
        Typeface t = Typeface.createFromAsset(activity.getAssets(), fileName);
        if(t == null) {
            throw new RuntimeException("Font not found: " + fileName);
        }
        TextPaint newPaint = new TextPaint();
        newPaint.setAntiAlias(true);
        newPaint.setSubpixelText(true);
        newPaint.setTypeface(t);
        return new NativeFont(com.codename1.ui.Font.FACE_SYSTEM, 
                com.codename1.ui.Font.STYLE_PLAIN, com.codename1.ui.Font.SIZE_MEDIUM, newPaint, fileName, 0, 0);
    }
    
    static class NativeFont {
        int face;
        int style;
        int size;
        Object font;
        String fileName;
        float height;
        int weight;
        
        public NativeFont(int face, int style, int size, Object font, String fileName, float height, int weight) {
            this(face, style, size, font);
            this.fileName = fileName;
            this.height = height;
            this.weight = weight;
        }
        
        public NativeFont(int face, int style, int size, Object font) {
            this.face = face;
            this.style = style;
            this.size = size;
            this.font = font;
        }
        
        public boolean equals(Object o) {
            if(o == null) {
                return false;
            }
            NativeFont n = ((NativeFont)o);
            if(fileName != null) {
                return n.fileName != null && fileName.equals(n.fileName) && n.height == height && n.weight == weight;
            }
            return n.face == face && n.style == style && n.size == size && font.equals(n.font);
        }
        
        public int hashCode() {
            return face | style | size;
        }
    }

    @Override
    public Object deriveTrueTypeFont(Object font, float size, int weight) {
        NativeFont fnt = (NativeFont)font;
        TextPaint paint = (TextPaint)fnt.font;
        paint.setAntiAlias(true);
        Typeface type = paint.getTypeface();
        int fontstyle = Typeface.NORMAL;
        if ((weight & Font.STYLE_BOLD) != 0) {
            fontstyle |= Typeface.BOLD;
        }
        if ((weight & Font.STYLE_ITALIC) != 0) {
            fontstyle |= Typeface.ITALIC;
        }
        type = Typeface.create(type, fontstyle);
        TextPaint newPaint = new TextPaint();
        newPaint.setTypeface(type);
        newPaint.setTextSize(size);
        newPaint.setAntiAlias(true);
        NativeFont n = new NativeFont(com.codename1.ui.Font.FACE_SYSTEM, com.codename1.ui.Font.STYLE_PLAIN, com.codename1.ui.Font.SIZE_MEDIUM, newPaint, fnt.fileName, size, weight);
        return n;
    }

    @Override
    public Object createFont(int face, int style, int size) {
        Paint font = new TextPaint();
        font.setAntiAlias(true);
        Typeface typeface = null;
        switch (face) {
            case Font.FACE_MONOSPACE:
                typeface = Typeface.MONOSPACE;
                break;
            default:
                typeface = Typeface.DEFAULT;
                break;
        }

        int fontstyle = Typeface.NORMAL;
        if ((style & Font.STYLE_BOLD) != 0) {
            fontstyle |= Typeface.BOLD;
        }
        if ((style & Font.STYLE_ITALIC) != 0) {
            fontstyle |= Typeface.ITALIC;
        }


        int height = this.defaultFontHeight;
        int diff = height / 3;

        switch (size) {
            case Font.SIZE_SMALL:
                height -= diff;
                break;
            case Font.SIZE_LARGE:
                height += diff;
                break;
        }

        font.setTypeface(Typeface.create(typeface, fontstyle));
        font.setUnderlineText((style & Font.STYLE_UNDERLINED) != 0);
        font.setTextSize(height);
        return new NativeFont(face, style, size, font);

    }

    /**
     * Loads a native font based on a lookup for a font name and attributes.
     * Font lookup values can be separated by commas and thus allow fallback if
     * the primary font isn't supported by the platform.
     *
     * @param lookup string describing the font
     * @return the native font object
     */
    public Object loadNativeFont(String lookup) {
        try {
            lookup = lookup.split(";")[0];
            Paint font = new TextPaint();
            font.setAntiAlias(true);
            int typeface = Typeface.NORMAL;
            String familyName = lookup.substring(0, lookup.indexOf("-"));
            String style = lookup.substring(lookup.indexOf("-") + 1, lookup.lastIndexOf("-"));
            String size = lookup.substring(lookup.lastIndexOf("-") + 1, lookup.length());

            if (style.equals("bolditalic")) {
                typeface = Typeface.BOLD_ITALIC;
            } else if (style.equals("italic")) {
                typeface = Typeface.ITALIC;
            } else if (style.equals("bold")) {
                typeface = Typeface.BOLD;
            }
            font.setTypeface(Typeface.create(familyName, typeface));
            font.setTextSize(Integer.parseInt(size));
            return new NativeFont(0, 0, 0, font);
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * Indicates whether loading a font by a string is supported by the platform
     *
     * @return true if the platform supports font lookup
     */
    @Override
    public boolean isLookupFontSupported() {
        return true;
    }

    @Override
    public boolean isAntiAliasedTextSupported() {
        return true;
    }

    @Override
    public void setAntiAliasedText(Object graphics, boolean a) {
        ((AndroidGraphics) graphics).getFont().setAntiAlias(a);
    }

    @Override
    public Object getDefaultFont() {
        TextPaint paint = new TextPaint();
        paint.set(this.defaultFont);
        return new NativeFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM, paint);
    }

    @Override
    public Object getNativeGraphics() {
        return myView.getGraphics();
    }

    @Override
    public Object getNativeGraphics(Object image) {
        return new AndroidGraphics(this, new Canvas((Bitmap) image));
    }

    @Override
    public void getRGB(Object nativeImage, int[] arr, int offset, int x, int y,
            int width, int height) {
        ((Bitmap) nativeImage).getPixels(arr, offset, width, x, y, width,
                height);
    }

    @Override
    public Object createImage(String path) throws IOException {
        int IMAGE_MAX_SIZE = getDisplayHeight();
        if (exists(path)) {
            Bitmap b = null;
            try {
                //Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                o.inPreferredConfig = Bitmap.Config.ARGB_8888;

                FileInputStream fis = new FileInputStream(path);
                BitmapFactory.decodeStream(fis, null, o);
                fis.close();

                int scale = 1;
                if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                    scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
                }

                //Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
                o2.inSampleSize = scale;
                o2.inPurgeable = true;
                o2.inInputShareable = true;
                fis = new FileInputStream(path);
                b = BitmapFactory.decodeStream(fis, null, o2);
                fis.close();
                
                //fix rotation 
                ExifInterface exif = new ExifInterface(path);               
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                int angle = 0;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        angle = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        angle = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        angle = 270;
                        break;
                }

                if (angle != 0) {
                    Matrix mat = new Matrix();
                    mat.postRotate(angle);
                    Bitmap correctBmp = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), mat, true);
                    b.recycle();
                    b = correctBmp;
                }
                
                
            } catch (IOException e) {
            }
            return b;
        } else {
            InputStream in = this.getResourceAsStream(getClass(), path);
            if (in == null) {
                throw new IOException("Resource not found. " + path);
            }
            try {
                return this.createImage(in);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ignored) {
                        ;
                    }
                }
            }
        }
    }
    
    @Override
    public boolean areMutableImagesFast() {
        return !myView.alwaysRepaintAll();
    }
    
    @Override
    public void repaint(Animation cmp) {
        if(myView.alwaysRepaintAll()) {
            if(cmp instanceof Component) {
                Component c = (Component)cmp;
                c.setDirtyRegion(null);
                if(c.getParent() != null) {
                    cmp = c.getComponentForm();
                } else {
                    Form f = getCurrentForm();
                    if(f != null) {
                        cmp = f;
                    }
                }
            } else {
                // make sure the form is repainted for standalone anims e.g. in the case
                // of replace animation
                Form f = getCurrentForm();
                if(f != null) {
                    super.repaint(f);
                }
            }
        }
        super.repaint(cmp);
    }
    
    @Override
    public Object createImage(InputStream i) throws IOException {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(i, null, opts);
    }

    @Override
    public void releaseImage(Object image) {
        Bitmap i = (Bitmap) image;
        i.recycle();
    }

    @Override
    public Object createImage(byte[] bytes, int offset, int len) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            BitmapFactory.Options.class.getField("inPurgeable").set(opts, true);
        } catch (Exception e) {
            // inPurgeable not supported
            // http://www.droidnova.com/2d-sprite-animation-in-android-addendum,505.html
        }
        return BitmapFactory.decodeByteArray(bytes, offset, len, opts);
    }

    @Override
    public Object createImage(int[] rgb, int width, int height) {
        return Bitmap.createBitmap(rgb, width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public boolean isAlphaMutableImageSupported() {
        return true;
    }

    @Override
    public Object scale(Object nativeImage, int width, int height) {
        return Bitmap.createScaledBitmap((Bitmap) nativeImage, width, height,
                false);
    }

//    @Override
//    public Object rotate(Object image, int degrees) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degrees);
//        return Bitmap.createBitmap((Bitmap) image, 0, 0, ((Bitmap) image).getWidth(), ((Bitmap) image).getHeight(), matrix, true);
//    }
    @Override
    public boolean isRotationDrawingSupported() {
        return false;
    }

    @Override
    protected boolean cacheLinearGradients() {
        return false;
    }

    @Override
    public boolean isNativeInputSupported() {
        return true;
    }

    @Override
    public Object createMutableImage(int width, int height, int fillColor) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        AndroidGraphics graphics = (AndroidGraphics) this.getNativeGraphics(bitmap);
        graphics.fillBitmap(fillColor);
        return bitmap;
    }

    @Override
    public int getImageHeight(Object i) {
        return ((Bitmap) i).getHeight();
    }

    @Override
    public int getImageWidth(Object i) {
        return ((Bitmap) i).getWidth();
    }

    @Override
    public void drawImage(Object graphics, Object img, int x, int y) {
        ((AndroidGraphics) graphics).drawImage(img, x, y);
    }

    public boolean isScaledImageDrawingSupported() {
        return true;
    }

    public void drawImage(Object graphics, Object img, int x, int y, int w, int h) {
        ((AndroidGraphics) graphics).drawImage(img, x, y, w, h);
    }

    @Override
    public void drawLine(Object graphics, int x1, int y1, int x2, int y2) {
        ((AndroidGraphics) graphics).drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean isAntiAliasingSupported() {
        return true;
    }

    @Override
    public void setAntiAliased(Object graphics, boolean a) {
        ((AndroidGraphics) graphics).getPaint().setAntiAlias(a);
    }

    @Override
    public void drawPolygon(Object graphics, int[] xPoints, int[] yPoints, int nPoints) {
        ((AndroidGraphics) graphics).drawPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void fillPolygon(Object graphics, int[] xPoints, int[] yPoints, int nPoints) {
        ((AndroidGraphics) graphics).fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public void drawRGB(Object graphics, int[] rgbData, int offset, int x,
            int y, int w, int h, boolean processAlpha) {
        ((AndroidGraphics) graphics).drawRGB(rgbData, offset, x, y, w, h, processAlpha);
    }

    @Override
    public void drawRect(Object graphics, int x, int y, int width, int height) {
        ((AndroidGraphics) graphics).drawRect(x, y, width, height);
    }

    @Override
    public void drawRoundRect(Object graphics, int x, int y, int width,
            int height, int arcWidth, int arcHeight) {
        ((AndroidGraphics) graphics).drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    @Override
    public void drawString(Object graphics, String str, int x, int y) {
        ((AndroidGraphics) graphics).drawString(str, x, y);
    }

    @Override
    public void drawArc(Object graphics, int x, int y, int width, int height,
            int startAngle, int arcAngle) {
        ((AndroidGraphics) graphics).drawArc(x, y, width, height, startAngle, arcAngle);
    }

    @Override
    public void fillArc(Object graphics, int x, int y, int width, int height,
            int startAngle, int arcAngle) {
        ((AndroidGraphics) graphics).fillArc(x, y, width, height, startAngle, arcAngle);
    }
    
    @Override
    public void fillRect(Object graphics, int x, int y, int width, int height) {
        ((AndroidGraphics) graphics).fillRect(x, y, width, height);
    }

    @Override
    public void fillRoundRect(Object graphics, int x, int y, int width,
            int height, int arcWidth, int arcHeight) {
        ((AndroidGraphics) graphics).fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    @Override
    public int getAlpha(Object graphics) {
        return ((AndroidGraphics) graphics).getAlpha();
    }

    @Override
    public void setAlpha(Object graphics, int alpha) {
        ((AndroidGraphics) graphics).setAlpha(alpha);
    }

    @Override
    public boolean isAlphaGlobal() {
        return true;
    }

    @Override
    public void setColor(Object graphics, int RGB) {
        ((AndroidGraphics) graphics).setColor((getColor(graphics) & 0xff000000) | RGB);
    }

    @Override
    public int getBackKeyCode() {
        return DROID_IMPL_KEY_BACK;
    }

    @Override
    public int getBackspaceKeyCode() {
        return DROID_IMPL_KEY_BACKSPACE;
    }

    @Override
    public int getClearKeyCode() {
        return DROID_IMPL_KEY_CLEAR;
    }

    @Override
    public int getClipHeight(Object graphics) {
        return ((AndroidGraphics) graphics).getClipHeight();
    }

    @Override
    public int getClipWidth(Object graphics) {
        return ((AndroidGraphics) graphics).getClipWidth();
    }

    @Override
    public int getClipX(Object graphics) {
        return ((AndroidGraphics) graphics).getClipX();
    }

    @Override
    public int getClipY(Object graphics) {
        return ((AndroidGraphics) graphics).getClipY();
    }

    @Override
    public void setClip(Object graphics, int x, int y, int width, int height) {
        ((AndroidGraphics) graphics).setClip(x, y, width, height);
    }

    @Override
    public void clipRect(Object graphics, int x, int y, int width, int height) {
        ((AndroidGraphics) graphics).clipRect(x, y, width, height);
    }

    @Override
    public int getColor(Object graphics) {
        return ((AndroidGraphics) graphics).getColor();
    }

    @Override
    public int getDisplayHeight() {
        if (this.myView != null) {
            int h = this.myView.getViewHeight();
            displayHeight = h;
            return h;
        }
        return displayHeight;
    }

    @Override
    public int getDisplayWidth() {
        if (this.myView != null) {
            int w = this.myView.getViewWidth();
            displayWidth = w;
            return w;
        }
        return displayWidth;
    }

    @Override
    public int getActualDisplayHeight() {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public int getGameAction(int keyCode) {
        switch (keyCode) {
            case DROID_IMPL_KEY_DOWN:
                return Display.GAME_DOWN;
            case DROID_IMPL_KEY_UP:
                return Display.GAME_UP;
            case DROID_IMPL_KEY_LEFT:
                return Display.GAME_LEFT;
            case DROID_IMPL_KEY_RIGHT:
                return Display.GAME_RIGHT;
            case DROID_IMPL_KEY_FIRE:
                return Display.GAME_FIRE;
            default:
                return 0;
        }
    }

    @Override
    public int getKeyCode(int gameAction) {
        switch (gameAction) {
            case Display.GAME_DOWN:
                return DROID_IMPL_KEY_DOWN;
            case Display.GAME_UP:
                return DROID_IMPL_KEY_UP;
            case Display.GAME_LEFT:
                return DROID_IMPL_KEY_LEFT;
            case Display.GAME_RIGHT:
                return DROID_IMPL_KEY_RIGHT;
            case Display.GAME_FIRE:
                return DROID_IMPL_KEY_FIRE;
            default:
                return 0;
        }
    }

    @Override
    public int[] getSoftkeyCode(int index) {
        if (index == 0) {
            return leftSK;
        }
        return null;
    }

    @Override
    public int getSoftkeyCount() {
        /**
         * one menu button only. we may have to stuff some code here as soon as
         * there are devices that no longer have only a single menu button.
         */
        return 1;
    }

    @Override
    public void vibrate(int duration) {
        if (!this.vibrateInitialized) {
            try {
                v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            } catch (Throwable e) {
                Log.e("Codename One", "problem with virbrator(0)", e);
            } finally {
                this.vibrateInitialized = true;
            }
        }
        if (v != null) {
            try {
                v.vibrate(duration);
            } catch (Throwable e) {
                Log.e("Codename One", "problem with virbrator(1)", e);
            }
        }
    }

    @Override
    public boolean isTouchDevice() {
        return activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);
    }

    @Override
    public boolean hasPendingPaints() {
        //if the view is not visible make sure the edt won't wait.
        if (myView != null && myView.getAndroidView().getVisibility() != View.VISIBLE) {
            return true;
        } else {
            return super.hasPendingPaints();
        }
    }

    public void revalidate() {
        if (myView != null) {
            myView.getAndroidView().setVisibility(View.VISIBLE);
            getCurrentForm().revalidate();
            flushGraphics();
        }

    }

    @Override
    public int getKeyboardType() {
        if (Display.getInstance().getDefaultVirtualKeyboard().isVirtualKeyboardShowing()) {
            return Display.KEYBOARD_TYPE_VIRTUAL;
        }
        /**
         * can we detect this? but even if we could i think it is best to have
         * this fixed to qwerty. we pass unicode values to Codename One in any
         * case. check AndroidView.onKeyUpDown() method. and read comment below.
         */
        return Display.KEYBOARD_TYPE_QWERTY;
        /**
         * some info from the MIDP docs about keycodes:
         *
         * "Applications receive keystroke events in which the individual keys
         * are named within a space of key codes. Every key for which events are
         * reported to MIDP applications is assigned a key code. The key code
         * values are unique for each hardware key unless two keys are obvious
         * synonyms for each other. MIDP defines the following key codes:
         * KEY_NUM0, KEY_NUM1, KEY_NUM2, KEY_NUM3, KEY_NUM4, KEY_NUM5, KEY_NUM6,
         * KEY_NUM7, KEY_NUM8, KEY_NUM9, KEY_STAR, and KEY_POUND. (These key
         * codes correspond to keys on a ITU-T standard telephone keypad.) Other
         * keys may be present on the keyboard, and they will generally have key
         * codes distinct from those list above. In order to guarantee
         * portability, applications should use only the standard key codes.
         *
         * The standard key codes values are equal to the Unicode encoding for
         * the character that represents the key. If the device includes any
         * other keys that have an obvious correspondence to a Unicode
         * character, their key code values should equal the Unicode encoding
         * for that character. For keys that have no corresponding Unicode
         * character, the implementation must use negative values. Zero is
         * defined to be an invalid key code."
         *
         * Because the MIDP implementation is our reference and that
         * implementation does not interpret the given keycodes we behave alike
         * and pass on the unicode values.
         */
    }

    /**
     * Exits the application...
     */
    public void exitApplication() {
        System.exit(0);
    }

    @Override
    public void notifyCommandBehavior(int commandBehavior) {
        if (commandBehavior == Display.COMMAND_BEHAVIOR_NATIVE) {
            if (activity instanceof CodenameOneActivity) {
                ((CodenameOneActivity) activity).enableNativeMenu(true);
            }
        }
        if (hasActionBar()) {
            //activity.runOnUiThread(new NotifyActionBar(activity, commandBehavior));
        }
    }
    
    private static class NotifyActionBar implements Runnable {
        private Activity activity;
        private boolean show;
        
        public NotifyActionBar(Activity activity, int commandBehavior) {
            this.activity = activity;
            show = commandBehavior == Display.COMMAND_BEHAVIOR_NATIVE;
        }
        
        public NotifyActionBar(Activity activity, boolean show) {
            this.activity = activity;
            this.show = show;
        }
        
        @Override
        public void run() {
            activity.invalidateOptionsMenu();
            if (show) {
                activity.getActionBar().show();
            } else {
                activity.getActionBar().hide();
            }
        }
    }

    /**
     * @inheritDoc
     */
    public String getProperty(String key, String defaultValue) {
        if ("OS".equals(key)) {
            return "Android";
        }
        if ("androidId".equals(key)) {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        
        if ("AppArg".equals(key)) {
             android.content.Intent intent = activity.getIntent();
             if(intent != null){
                 Uri u = intent.getData();
                 if (u != null) {
                    if ("content".equals(intent.getScheme())) {
                        try {
                            InputStream attachment = activity.getContentResolver().openInputStream(u);
                            if (attachment != null) {
                                String name = getContentName(activity.getContentResolver(), u);
                                if (name != null) {
                                    String filePath = getAppHomePath()
                                            + getFileSystemSeparator() + name;
                                    File f = new File(filePath);
                                    FileOutputStream tmp = new FileOutputStream(f);
                                    byte[] buffer = new byte[1024];
                                    while (attachment.read(buffer) > 0) {
                                        tmp.write(buffer);
                                    }
                                    tmp.close();
                                    attachment.close();
                                    return filePath;
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            return defaultValue;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return defaultValue;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return defaultValue;
                        }
                    } else {
                        String encodedPath = u.getEncodedPath();
                        if(encodedPath != null && encodedPath.length() > 0){
                            return encodedPath;
                        }
                        return u.toString();
                    }
                }
            }
        }
        
        if ("cellId".equals(key)) {
            try {
                String serviceName = Context.TELEPHONY_SERVICE;
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(serviceName);
                int cellId = ((GsmCellLocation) telephonyManager.getCellLocation()).getCid();
                return "" + cellId;
            } catch (Throwable t) {
                return defaultValue;
            }
        }
        if ("AppName".equals(key)) {
            
            final PackageManager pm = activity.getPackageManager();
            ApplicationInfo ai;
            try {
                ai = pm.getApplicationInfo(activity.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                ai = null;
            }
            String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : null);
            if(applicationName == null){
                return defaultValue;
            }
            return applicationName;
        }
        if ("AppVersion".equals(key)) {
            try {
                PackageInfo i = activity.getPackageManager().getPackageInfo(activity.getApplicationInfo().packageName, 0);
                return i.versionName;
            } catch (NameNotFoundException ex) {
                ex.printStackTrace();
            }
            return defaultValue;
        }
        if ("Platform".equals(key)) {
            String p = System.getProperty("platform");
            if(p == null) {
                return defaultValue;
            }
            return p;
        }
        if ("User-Agent".equals(key)) {
            String ua = getUserAgent();
            if(ua == null) {
                return defaultValue;
            }
            return ua;
        }
        if("OSVer".equals(key)) {
            return "" + android.os.Build.VERSION.RELEASE;
        }
        try {
            if ("IMEI".equals(key) || "UDID".equals(key)) {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getDeviceId();
            }
            if ("MSISDN".equals(key)) {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getLine1Number();
            }
        } catch(Throwable t) {
            // will be caused by no permissions.
            return defaultValue;
        }

        android.content.Intent intent = activity.getIntent();
        if(intent != null){
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String value = extras.getString(key);
                if(value != null) {
                    return value;
                }
            }
        }
        
        //these keys/values are from the Application Resources (strings values)
        try {
            int id = activity.getResources().getIdentifier(key, "string", activity.getApplicationInfo().packageName);
            String val = activity.getResources().getString(id);
            return val;

        } catch (Exception e) {
        }
        return System.getProperty(key, defaultValue);
    }

    private String getContentName(ContentResolver resolver, Uri uri) {
        Cursor cursor = resolver.query(uri, null, null, null, null);
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        if (nameIndex >= 0) {
            String name = cursor.getString(nameIndex);
            cursor.close();
            return name;
        }
        return null;
    }
    
    private String getUserAgent() {
        try {
            String userAgent = System.getProperty("http.agent");            
            if(userAgent != null){
                return userAgent;
            }
        } catch (Exception e) {
        }
        
        try {
            Constructor<WebSettings> constructor = WebSettings.class.getDeclaredConstructor(Context.class, WebView.class);
            constructor.setAccessible(true);
            try {
                WebSettings settings = constructor.newInstance(activity, null);
                return settings.getUserAgentString();
            } finally {
                constructor.setAccessible(false);
            }
        } catch (Exception e) {
            final StringBuffer ua = new StringBuffer();
            if (Thread.currentThread().getName().equalsIgnoreCase("main")) {
                WebView m_webview = new WebView(activity);
                ua.append(m_webview.getSettings().getUserAgentString());
                m_webview.destroy();
            } else {
                final boolean[] flag = new boolean[1];
                Thread thread = new Thread() {
                    public void run() {
                        Looper.prepare();
                        WebView m_webview = new WebView(activity);
                        ua.append(m_webview.getSettings().getUserAgentString());
                        m_webview.destroy();
                        Looper.loop();
                        flag[0] = true;
                        synchronized (flag) {
                            flag.notify();
                        }
                    }
                };
                thread.start();
                while (!flag[0]) {
                    synchronized (flag) {
                        try {
                            flag.wait(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
            return ua.toString();
        }
    }

    private String getMimeType(String url){
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }
    
    public void execute(String url, ActionListener response) {
        if (response != null) {
            callback = new EventDispatcher();
            callback.addListener(response);
        }

        Intent intent;
        Uri uri;
        try {
            if (url.startsWith("intent")) {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } else {
                url = fixAttachmentPath(url);
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (url.startsWith("/")) {
                    uri = Uri.fromFile(new File(url));
                }else{
                    uri = Uri.parse(url);
                }
                String mimeType = getMimeType(url);
                if(mimeType != null){
                    intent.setDataAndType(uri, mimeType);            
                }else{
                    intent.setData(uri);
                }
            }
            if(response != null){
                activity.startActivityForResult(intent, IntentResultListener.URI_SCHEME);
            }else{
                activity.startActivity(intent);
            }
            return;
        } catch (Exception ex) {           
        }
        
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @inheritDoc
     */
    @Override
    public void execute(String url) {
        execute(url, null);
    }

    /**
     * @inheritDoc
     */
    public void playBuiltinSound(String soundIdentifier) {
        if (Display.SOUND_TYPE_BUTTON_PRESS == soundIdentifier) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (myView != null) {
                        myView.getAndroidView().playSoundEffect(AudioManager.FX_KEY_CLICK);
                    }
                }
            });
        }
    }

    /**
     * @inheritDoc
     */
    protected void playNativeBuiltinSound(Object data) {
    }

    /**
     * @inheritDoc
     */
    public boolean isBuiltinSoundAvailable(String soundIdentifier) {
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Media createMedia(final String uri, boolean isVideo, final Runnable onCompletion) throws IOException {

        if (uri.startsWith("file://")) {
            return createMedia(uri.substring(7), isVideo, onCompletion);
        }
        File file = null;
        if (uri.indexOf(':') < 0) {
            // use a file object to play to try and workaround this issue:
            // http://code.google.com/p/android/issues/detail?id=4124
            file = new File(uri);
        }

        Media retVal;

        if (isVideo) {
            final AndroidImplementation.Video[] video = new AndroidImplementation.Video[1];
            final boolean[] flag = new boolean[1];
            final File f = file;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    VideoView v = new VideoView(activity);
                    v.setZOrderMediaOverlay(true);
                    if (f != null) {
                        v.setVideoURI(Uri.fromFile(f));
                    } else {
                        v.setVideoURI(Uri.parse(uri));
                    }
                    video[0] = new AndroidImplementation.Video(v, activity, onCompletion);
                    flag[0] = true;
                    synchronized (flag) {
                        flag.notify();
                    }
                }
            });
            while (!flag[0]) {
                synchronized (flag) {
                    try {
                        flag.wait(100);
                    } catch (InterruptedException ex) {
                    }
                }
            }
            return video[0];
        } else {
            MediaPlayer player;
            if (file != null) {
                FileInputStream is = new FileInputStream(file);
                player = new MediaPlayer();
                player.setDataSource(is.getFD());
                player.prepare();
            } else {
                player = MediaPlayer.create(activity, Uri.parse(uri));
            }
            retVal = new Audio(activity, player, null, onCompletion);
        }
        return retVal;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Media createMedia(InputStream stream, String mimeType, final Runnable onCompletion) throws IOException {

        boolean isVideo = mimeType.contains("video");

        if (!isVideo && stream instanceof FileInputStream) {
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(((FileInputStream) stream).getFD());
            player.prepare();
            return new Audio(activity, player, stream, onCompletion);
        }

        final File temp = File.createTempFile("mtmp", "dat");
        temp.deleteOnExit();
        FileOutputStream out = new FileOutputStream(temp);
       
        byte buf[] = new byte[256];
        int len = 0;
        while ((len = stream.read(buf, 0, buf.length)) > -1) {
            out.write(buf, 0, len);
        }
        out.close();
        stream.close();
        
        final Runnable finish = new Runnable() {

            @Override
            public void run() {
                if(onCompletion != null){
                    Display.getInstance().callSerially(onCompletion);
                    
                    // makes sure the file is only deleted after the onCompletion was invoked
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            temp.delete();                
                        }
                    });
                    return;
                }
                temp.delete();                
            }
        };

        if (isVideo) {
            final AndroidImplementation.Video[] retVal = new AndroidImplementation.Video[1];
            final boolean[] flag = new boolean[1];

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    VideoView v = new VideoView(activity);
                    v.setZOrderMediaOverlay(true);
                    v.setVideoURI(Uri.fromFile(temp));
                    retVal[0] = new AndroidImplementation.Video(v, activity, finish);
                    flag[0] = true;
                    synchronized (flag) {
                        flag.notify();
                    }
                }
            });
            while (!flag[0]) {
                synchronized (flag) {
                    try {
                        flag.wait(100);
                    } catch (InterruptedException ex) {
                    }
                }
            }

            return retVal[0];
        } else {
            return createMedia(new FileInputStream(temp), mimeType, finish);
        }

    }

    @Override
    public Media createMediaRecorder(final String path, final String mimeType) throws IOException {

        final AndroidRecorder[] record = new AndroidRecorder[1];
        final IOException[] error = new IOException[1];

        final Object lock = new Object();
        synchronized (lock) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        MediaRecorder recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        if(mimeType.contains("amr")){
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        }else{
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);            
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        }
                        recorder.setOutputFile(path);
                        try {
                            recorder.prepare();
                            record[0] = new AndroidRecorder(recorder);
                        } catch (IllegalStateException ex) {
                            Logger.getLogger(AndroidImplementation.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            error[0] = ex;
                        } finally {
                            lock.notify();
                        }


                    }
                }
            });

            try {
                lock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (error[0] != null) {
                throw error[0];
            }

            return record[0];
        }
    }
    
    public String [] getAvailableRecordingMimeTypes(){
        return new String[]{"audio/amr", "audio/aac"};
    }
    

    /**
     * @inheritDoc
     */
    public Object createSoftWeakRef(Object o) {
        return new SoftReference(o);
    }

    /**
     * @inheritDoc
     */
    public Object extractHardRef(Object o) {
        SoftReference w = (SoftReference) o;
        if (w != null) {
            return w.get();
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    public PeerComponent createNativePeer(Object nativeComponent) {
        if (!(nativeComponent instanceof View)) {
            throw new IllegalArgumentException(nativeComponent.getClass().getName());
        }
        return new AndroidImplementation.AndroidPeer((View) nativeComponent);
    }

    private void blockNativeFocusAll(boolean block) {
        synchronized (this.nativePeers) {
            final int size = this.nativePeers.size();
            for (int i = 0; i < size; i++) {
                AndroidImplementation.AndroidPeer next = (AndroidImplementation.AndroidPeer) this.nativePeers.get(i);
                next.blockNativeFocus(block);
            }
        }
    }

    public void onFocusChange(View view, boolean bln) {

        if (bln) {
            /**
             * whenever the base view receives focus we automatically block
             * possible native subviews from gaining focus.
             */
            blockNativeFocusAll(true);
            if (this.lastDirectionalKeyEventReceivedByWrapper != 0) {
                /**
                 * because we also consume any key event in the OnKeyListener of
                 * the native wrappers, we have to simulate key events to make
                 * Codename One move the focus to the next component.
                 */
                if (!myView.getAndroidView().isInTouchMode()) {
                    switch (lastDirectionalKeyEventReceivedByWrapper) {
                        case AndroidImplementation.DROID_IMPL_KEY_LEFT:
                        case AndroidImplementation.DROID_IMPL_KEY_RIGHT:
                        case AndroidImplementation.DROID_IMPL_KEY_UP:
                        case AndroidImplementation.DROID_IMPL_KEY_DOWN:
                            Display.getInstance().keyPressed(lastDirectionalKeyEventReceivedByWrapper);
                            Display.getInstance().keyReleased(lastDirectionalKeyEventReceivedByWrapper);
                            break;
                        default:
                            Log.d("Codename One", "unexpected keycode: " + lastDirectionalKeyEventReceivedByWrapper);
                            break;
                    }
                } else {
                    Log.d("Codename One", "base view gained focus but no key event to process.");
                }
                lastDirectionalKeyEventReceivedByWrapper = 0;
            }
        }

    }

    /**
     * wrapper component that capsules a native view object in a Codename One
     * component. this involves A LOT of back and forth between the Codename One
     * EDT and the Android UI thread.
     *
     *
     * To use it you would:
     *
     * 1) create your native Android view(s). Make sure to work on the Android
     * UI thread when constructing and modifying them. 2) create a Codename One
     * peer component by calling:
     *
     * com.codename1.ui.PeerComponent.create(myAndroidView);
     *
     * 3) currently the view's size is not automatically calculated from the
     * native view. so you should set the preferred size of the Codename One
     * component manually.
     *
     *
     */
    class AndroidPeer extends PeerComponent {

        private View v;
        private AndroidImplementation.AndroidRelativeLayout layoutWrapper = null;
        private Paint clear = new Paint();


        public AndroidPeer(View vv) {
            super(vv);
            this.v = vv;
            clear.setColor(0xAA000000);
            clear.setStyle(Style.FILL);
            v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }

        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);
            this.doSetVisibility(visible);
        }

        void doSetVisibility(final boolean visible) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    v.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                    if (visible) {
                        v.bringToFront();
                    }
                }
            });
            if(visible){
                layoutPeer();
            }
        }

        private void doSetVisibilityInternal(final boolean visible) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    v.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                    if (visible) {
                        v.bringToFront();
                    }
                }
            });
        }
        
        protected void deinitialize() {
            super.deinitialize();
            synchronized (nativePeers) {
                nativePeers.remove(this);
            }
            deinit();
        }

        public void deinit(){
            final boolean [] removed = new boolean[1];
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (layoutWrapper != null && AndroidImplementation.this.relativeLayout != null) {
                        AndroidImplementation.this.relativeLayout.removeView(layoutWrapper);
                        AndroidImplementation.this.relativeLayout.requestLayout();
                    }
                    removed[0] = true;
                }
            });
            Display.getInstance().invokeAndBlock(new Runnable() {
                public void run() {
                    while (!removed[0]) {
                        try {
                            Thread.sleep(5);
                        } catch(InterruptedException er) {}
                    }
                }
            });
        }
        
        protected void initComponent() {
            super.initComponent();
            synchronized (nativePeers) {
                nativePeers.add(this);
            }
            init();
        }
        
        public void init(){
            final boolean [] added = new boolean[1];
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (layoutWrapper == null) {
                        /**
                         * wrap the native item in a layout that we can move
                         * around on the surface view as we like.
                         */
                        layoutWrapper = new AndroidImplementation.AndroidRelativeLayout(activity, AndroidImplementation.AndroidPeer.this, v);
                        layoutWrapper.setBackgroundDrawable(null);
                        v.setVisibility(View.INVISIBLE);
                        v.setFocusable(AndroidImplementation.AndroidPeer.this.isFocusable());
                        v.setFocusableInTouchMode(true);
                        ArrayList<View> viewList = new ArrayList<View>();
                        viewList.add(layoutWrapper);
                        v.addFocusables(viewList, View.FOCUS_DOWN);
                        v.addFocusables(viewList, View.FOCUS_UP);
                        v.addFocusables(viewList, View.FOCUS_LEFT);
                        v.addFocusables(viewList, View.FOCUS_RIGHT);
                        if (v.isFocusable() || v.isFocusableInTouchMode()) {
                            if (AndroidImplementation.AndroidPeer.super.hasFocus()) {
                                AndroidImplementation.this.blockNativeFocusAll(true);
                                blockNativeFocus(false);
                                v.requestFocus();
                            } else {
                                blockNativeFocus(true);
                            }
                        } else {
                            blockNativeFocus(true);
                        }
                        layoutWrapper.setOnKeyListener(new View.OnKeyListener() {
                            public boolean onKey(View view, int i, KeyEvent ke) {
                                lastDirectionalKeyEventReceivedByWrapper = CodenameOneView.internalKeyCodeTranslate(ke.getKeyCode());

                                // move focus back to base view.
                                AndroidImplementation.this.myView.getAndroidView().requestFocus();

                                /**
                                 * if the wrapper has focus, then only because
                                 * the wrapped native component just lost focus.
                                 * we consume whatever key events we receive,
                                 * just to make sure no half press/release
                                 * sequence reaches the base view (and therefore
                                 * Codename One).
                                 */
                                return true;
                            }
                        });
                        layoutWrapper.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            public void onFocusChange(View view, boolean bln) {
                                Log.d("Codename One", "on focus change. " + view.toString() + " focus:" + bln + " touchmode: " + v.isInTouchMode());
                            }
                        });
                        layoutWrapper.setOnTouchListener(new View.OnTouchListener() {
                            public boolean onTouch(View v, MotionEvent me) {
                                return myView.getAndroidView().onTouchEvent(me);
                            }
                        });
                    }
                    if(AndroidImplementation.this.relativeLayout != null){
                        AndroidImplementation.this.relativeLayout.addView(layoutWrapper);
                    }
                    added[0] = true;
                }
            });

            Display.getInstance().invokeAndBlock(new Runnable() {
                public void run() {
                    while (!added[0]) {
                        try {
                            Thread.sleep(5);
                        } catch(InterruptedException err) {}
                    }
                }
            });
        }

        @Override
        protected void onPositionSizeChange() {
            
            Form f = getComponentForm();
            if (v.getVisibility() == View.INVISIBLE
                    && f != null
                    && Display.getInstance().getCurrent() == f) {
                doSetVisibilityInternal(true);
                return;
            }
            layoutPeer();
        }

        protected void layoutPeer(){
            // called by Codename One EDT to position the native component.
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (layoutWrapper != null) {
                        if (v.getVisibility() == View.VISIBLE) {

                            RelativeLayout.LayoutParams layoutParams = layoutWrapper.createMyLayoutParams(
                                    AndroidImplementation.AndroidPeer.this.getAbsoluteX(),
                                    AndroidImplementation.AndroidPeer.this.getAbsoluteY(),
                                    AndroidImplementation.AndroidPeer.this.getWidth(),
                                    AndroidImplementation.AndroidPeer.this.getHeight());
                            layoutWrapper.setLayoutParams(layoutParams);
                            if(AndroidImplementation.this.relativeLayout != null){
                                AndroidImplementation.this.relativeLayout.requestLayout();
                            }
                        }
                    }
                }
            });        
        }
        
        void blockNativeFocus(boolean block) {
            if (layoutWrapper != null) {
                layoutWrapper.setDescendantFocusability(block
                        ? ViewGroup.FOCUS_BLOCK_DESCENDANTS : ViewGroup.FOCUS_AFTER_DESCENDANTS);
            }
        }

        @Override
        public boolean isFocusable() {
            // EDT
            if (v != null) {
                return v.isFocusableInTouchMode() || v.isFocusable();
            } else {
                return super.isFocusable();
            }
        }

        @Override
        public void setFocusable(final boolean focusable) {
            // EDT
            super.setFocusable(focusable);
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    v.setFocusable(focusable);
                }
            });
        }

        @Override
        protected void focusGained() {
            Log.d("Codename One", "native focus gain");
            // EDT
            super.focusGained();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    // allow this one to gain focus
                    blockNativeFocus(false);
                    if (v.isInTouchMode()) {
                        v.requestFocusFromTouch();
                    } else {
                        v.requestFocus();
                    }
                }
            });
        }

        @Override
        protected void focusLost() {
            Log.d("Codename One", "native focus loss");
            // EDT
            super.focusLost();
            if (layoutWrapper != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        // request focus of the wrapper. that will trigger the
                        // android focus listener and move focus back to the
                        // base view.
                        layoutWrapper.requestFocus();
                    }
                });
            }
        }

        public void release() {
            deinitialize();
        }

        @Override
        protected Dimension calcPreferredSize() {
            int w = 1;
            int h = 1;
            Drawable d = v.getBackground();
            if (d != null) {
                w = d.getMinimumWidth();
                h = d.getMinimumHeight();
            }
            w = Math.max(v.getMeasuredWidth(), w);
            h = Math.max(v.getMeasuredHeight(), h);
            if (v instanceof TextView) {
                w = (int) android.text.Layout.getDesiredWidth(((TextView) v).getText(), ((TextView) v).getPaint());
            }
            return new Dimension(w, h);
        }
    }

    /**
     * inner class that wraps the native components. this is a useful thingy to
     * handle focus stuff and buffering.
     */
    class AndroidRelativeLayout extends RelativeLayout {

        private AndroidImplementation.AndroidPeer peer;

        public AndroidRelativeLayout(Context activity, AndroidImplementation.AndroidPeer peer, View v) {
            super(activity);

            this.peer = peer;
            this.setLayoutParams(createMyLayoutParams(peer.getAbsoluteX(), peer.getAbsoluteY(),
                    peer.getWidth(), peer.getHeight()));
            this.addView(v, new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.FILL_PARENT));
            this.setDrawingCacheEnabled(false);
            this.setAlwaysDrawnWithCacheEnabled(false);
            this.setFocusable(true);
            this.setFocusableInTouchMode(false);
            this.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        }

        /**
         * create a layout parameter object that holds the native component's
         * position.
         *
         * @return
         */
        private RelativeLayout.LayoutParams createMyLayoutParams(int x, int y, int width, int height) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.width = width;
            layoutParams.height = height;
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            return layoutParams;
        }
        
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            int keycode = event.getKeyCode();
            keycode = CodenameOneView.internalKeyCodeTranslate(keycode);
            if (keycode == AndroidImplementation.DROID_IMPL_KEY_BACK) {
                Display.getInstance().keyPressed(keycode);
                Display.getInstance().keyReleased(keycode);
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        }
        

    }
    
    private boolean testedNativeTheme;
    private boolean nativeThemeAvailable;

    public boolean hasNativeTheme() {
        if (!testedNativeTheme) {
            testedNativeTheme = true;
            try {
                InputStream is;
                if (android.os.Build.VERSION.SDK_INT < 14 && !isTablet()) {
                    is = getResourceAsStream(getClass(), "/androidTheme.res");
                } else {
                    is = getResourceAsStream(getClass(), "/android_holo_light.res");
                }
                nativeThemeAvailable = is != null;
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return nativeThemeAvailable;
    }

    /**
     * Installs the native theme, this is only applicable if hasNativeTheme()
     * returned true. Notice that this method might replace the
     * DefaultLookAndFeel instance and the default transitions.
     */
    public void installNativeTheme() {
        hasNativeTheme();
        if (nativeThemeAvailable) {
            try {
                InputStream is;
                if (android.os.Build.VERSION.SDK_INT < 14 && !isTablet()) {
                    is = getResourceAsStream(getClass(), "/androidTheme.res");
                } else {
                    is = getResourceAsStream(getClass(), "/android_holo_light.res");
                }
                Resources r = Resources.open(is);
                Hashtable h = r.getTheme(r.getThemeResourceNames()[0]);
                h.put("@commandBehavior", "Native");
                UIManager.getInstance().setThemeProps(h);
                is.close();
                Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_NATIVE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isNativeBrowserComponentSupported() {
        return true;
    }

    @Override
    public void setNativeBrowserScrollingEnabled(PeerComponent browserPeer, boolean e) {
        super.setNativeBrowserScrollingEnabled(browserPeer, e);
        AndroidBrowserComponent bc = (AndroidBrowserComponent)browserPeer;
        bc.setScrollingEnabled(e);
        
    }

    @Override
    public void setPinchToZoomEnabled(PeerComponent browserPeer, boolean e) {
        super.setPinchToZoomEnabled(browserPeer, e);
        AndroidBrowserComponent bc = (AndroidBrowserComponent)browserPeer;
        bc.setPinchZoomEnabled(e);
    }

    public PeerComponent createBrowserComponent(final Object parent) {
        final AndroidImplementation.AndroidBrowserComponent[] bc = new AndroidImplementation.AndroidBrowserComponent[1];

        final Object lock = new Object();
        synchronized (lock) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        WebView wv = new WebView(activity) {
                            
                            public boolean onKeyDown(int keyCode, KeyEvent event) {
                                switch (keyCode) {
                                    case KeyEvent.KEYCODE_BACK:
                                        Display.getInstance().keyPressed(AndroidImplementation.DROID_IMPL_KEY_BACK);
                                        return true;
                                    case KeyEvent.KEYCODE_MENU:
                                        //if the native commands are used don't handle the keycode
                                        if (Display.getInstance().getCommandBehavior() != Display.COMMAND_BEHAVIOR_NATIVE) {
                                            Display.getInstance().keyPressed(AndroidImplementation.DROID_IMPL_KEY_MENU);
                                            return true;
                                        }
                                }
                                return super.onKeyDown(keyCode, event);
                            }

                            public boolean onKeyUp(int keyCode, KeyEvent event) {
                                switch (keyCode) {
                                    case KeyEvent.KEYCODE_BACK:
                                        Display.getInstance().keyReleased(AndroidImplementation.DROID_IMPL_KEY_BACK);
                                        return true;
                                    case KeyEvent.KEYCODE_MENU:
                                        //if the native commands are used don't handle the keycode
                                        if (Display.getInstance().getCommandBehavior() != Display.COMMAND_BEHAVIOR_NATIVE) {
                                            Display.getInstance().keyPressed(AndroidImplementation.DROID_IMPL_KEY_MENU);
                                            return true;
                                        }
                                }
                                return super.onKeyUp(keyCode, event);
                            }
                        };
                        wv.setOnTouchListener(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                    case MotionEvent.ACTION_UP:
                                        if (!v.hasFocus()) {
                                            v.requestFocus();
                                        }
                                        break;
                                }
                                return false;
                            }
                        });
                        wv.getSettings().setDomStorageEnabled(true);
                        wv.requestFocus(View.FOCUS_DOWN);
                        wv.setFocusableInTouchMode(true);
                        bc[0] = new AndroidImplementation.AndroidBrowserComponent(wv, activity, parent);
                        lock.notify();
                    }
                }
            });
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return bc[0];
    }

    public void setBrowserProperty(PeerComponent browserPeer, String key, Object value) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).setProperty(key, value);
    }

    public String getBrowserTitle(PeerComponent browserPeer) {
        return ((AndroidImplementation.AndroidBrowserComponent) browserPeer).getTitle();
    }

    public String getBrowserURL(PeerComponent browserPeer) {
        return ((AndroidImplementation.AndroidBrowserComponent) browserPeer).getURL();
    }

    public void setBrowserURL(PeerComponent browserPeer, String url) {
        if (url.startsWith("jar:")) {
            url = url.substring(6);
            if(url.indexOf("/") != 0) {
                url = "/"+url;
            }

            url = "file:///android_asset"+url;
        }
        AndroidImplementation.AndroidBrowserComponent bc = (AndroidImplementation.AndroidBrowserComponent) browserPeer;
        if(bc.parent.getBrowserNavigationCallback().shouldNavigate(url)) {
            bc.setURL(url);
        }
    }

    public void browserStop(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).stop();
    }

    public void browserDestroy(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).destroy();
    }
    
    /**
     * Reload the current page
     *
     * @param browserPeer browser instance
     */
    public void browserReload(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).reload();
    }

    /**
     * Indicates whether back is currently available
     *
     * @param browserPeer browser instance
     * @return true if back should work
     */
    public boolean browserHasBack(PeerComponent browserPeer) {
        return ((AndroidImplementation.AndroidBrowserComponent) browserPeer).hasBack();
    }

    public boolean browserHasForward(PeerComponent browserPeer) {
        return ((AndroidImplementation.AndroidBrowserComponent) browserPeer).hasForward();
    }

    public void browserBack(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).back();
    }

    public void browserForward(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).forward();
    }

    public void browserClearHistory(PeerComponent browserPeer) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).clearHistory();
    }

    public void setBrowserPage(PeerComponent browserPeer, String html, String baseUrl) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).setPage(html, baseUrl);
    }

    public void browserExposeInJavaScript(PeerComponent browserPeer, Object o, String name) {
        ((AndroidImplementation.AndroidBrowserComponent) browserPeer).exposeInJavaScript(o, name);
    }


    /**
     * Executes javascript and returns a string result where appropriate.
     * @param browserPeer
     * @param javaScript
     * @return
     */
    @Override
    public String browserExecuteAndReturnString(final PeerComponent browserPeer, final String javaScript) {
        final AndroidImplementation.AndroidBrowserComponent bc = (AndroidImplementation.AndroidBrowserComponent) browserPeer;

        // The jsCallback is a special java object exposed to javascript that we use
        // to return values from javascript to java.
        synchronized (bc.jsCallback){
            // Initialize the return value to null
            bc.jsCallback.setReturnValue(null);

            // Reset the callback so that it will fire the notify() when
            // a value is set.
            bc.jsCallback.reset();
        }

        // We are placing the javascript inside eval() so we need to escape
        // the input.
        String escaped = StringUtil.replaceAll(javaScript, "\\", "\\\\");
        escaped = StringUtil.replaceAll(escaped, "'", "\\'");

        final String js = "javascript:(function(){"
                + AndroidBrowserComponentCallback.JS_RETURNVAL_VARNAME+"=null;try{"
                + AndroidBrowserComponentCallback.JS_RETURNVAL_VARNAME
                + "=eval('"+escaped +"');} catch (e){console.log(e)};"
                + AndroidBrowserComponentCallback.JS_VAR_NAME+".setReturnValue(''+"
                + AndroidBrowserComponentCallback.JS_RETURNVAL_VARNAME
                + ");})()";

        // Send the Javascript string via SetURL.
        // NOTE!! This is sent asynchronously so we will need to wait for
        // the result to come in.
        bc.setURL(js);
        if(Display.getInstance().isEdt()) {
            // If we are on the EDT then we need to invokeAndBlock
            // so that we wait for the javascript result, but we don't
            // prevent the EDT from executing the rest of the pipeline.
            Display.getInstance().invokeAndBlock(new Runnable() {
                public void run() {
                    // Loop/wait until the callback value has been set.
                    // The callback.setReturnValue() method, which will
                    // be called from Javascript issues a notify() to
                    // let us know it is done.
                    while (!bc.jsCallback.isValueSet()) {
                        synchronized(bc.jsCallback){
                            try {
                                bc.jsCallback.wait(200);
                            } catch (InterruptedException ex) {}
                        }
                    }
                }
            });
        } else {
            // If we are not on the EDT, then it is safe to just loop and wait.
            while (!bc.jsCallback.isValueSet()) {
                synchronized(bc.jsCallback){
                    try {
                        bc.jsCallback.wait(200);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        return bc.jsCallback.getReturnValue();
    }

    
    public boolean canForceOrientation() {
        return true;
    }

    public void lockOrientation(boolean portrait) {
        if(portrait){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);        
        }
    }

    public void unlockOrientation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
    
    
    
    public boolean isAffineSupported() {
        return true;
    }

    public void resetAffine(Object nativeGraphics) {
        ((AndroidGraphics) nativeGraphics).resetAffine();
    }

    public void scale(Object nativeGraphics, float x, float y) {
        ((AndroidGraphics) nativeGraphics).scale(x, y);
    }

    public void rotate(Object nativeGraphics, float angle) {
        ((AndroidGraphics) nativeGraphics).rotate(angle);
    }

    public void rotate(Object nativeGraphics, float angle, int x, int y) {
        ((AndroidGraphics) nativeGraphics).rotate(angle, x, y);
    }

    public void shear(Object nativeGraphics, float x, float y) {
    }

    public boolean isTablet() {
        return (activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Executes r on the UI thread and blocks the EDT to completion
     * @param r runnable to execute
     */
    public static void runOnUiThreadAndBlock(final Runnable r) {
        final boolean[] completed = new boolean[1];
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                r.run();
                completed[0] = true;
                synchronized(completed) {
                    completed.notify();
                }
            }
        });
        Display.getInstance().invokeAndBlock(new Runnable() {
            @Override
            public void run() {
                synchronized(completed) {
                    while(!completed[0]) {
                        try {
                            completed.wait();
                        } catch(InterruptedException err) {}
                    }
                }
            }
        });
    }
    
    public int convertToPixels(int dipCount, boolean horizontal) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        float ppi = dm.density * 160f;
        return (int) (((float) dipCount) / 25.4f * ppi);
    }

    public boolean isPortrait() {
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_UNDEFINED
                || orientation == Configuration.ORIENTATION_SQUARE) {
            return super.isPortrait();
        }
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * A class that is used by executeAndReturnString to be exposed in Javascript
     * so that it can accept return values.
     */
    static class AndroidBrowserComponentCallback {
        static final String JS_VAR_NAME = "com_codename1_impl_AndroidImplementation_AndroidBrowserComponent";
        static final String JS_RETURNVAL_VARNAME = "com_codename1_impl_AndroidImplementation_AndroidBrowserComponent_returnValue";
        private String returnValue;
        private boolean valueSet = false;
        
        //@JavascriptInterface
        public synchronized void setReturnValue(String value){
            valueSet = true;
            this.returnValue = value;
            notify();
        }

        public String getReturnValue() {
            return returnValue;
        }

        public boolean isValueSet() {
            return valueSet;
        }

        public void reset() {
            valueSet = false;
        }
    }

    
    
    
    class AndroidBrowserComponent extends AndroidImplementation.AndroidPeer {

        private Activity act;
        private WebView web;
        private BrowserComponent parent;
        private boolean scrollingEnabled = true;
        protected AndroidBrowserComponentCallback jsCallback;
        private boolean lightweightMode = false;        
        private ProgressDialog progressBar;
        private boolean hideProgress;
        
        
        public AndroidBrowserComponent(final WebView web, Activity act, Object p) {
            super(web);
            doSetVisibility(false);
            parent = (BrowserComponent) p;
            this.web = web;
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setSupportZoom(parent.isPinchToZoomEnabled());
            this.act = act;
            jsCallback = new AndroidBrowserComponentCallback();
            hideProgress = Display.getInstance().getProperty("WebLoadingHidden", "false").equals("true");
            
            web.addJavascriptInterface(jsCallback, AndroidBrowserComponentCallback.JS_VAR_NAME);

            web.setWebViewClient(new WebViewClient() {
                public void onLoadResource(WebView view, String url) {
                    try {
                        URI uri = new URI(url);
                        CookieManager mgr = CookieManager.getInstance();
                        String cookieStr = mgr.getCookie(url);
                        if (cookieStr!=null){
                            String[] cookies = cookieStr.split(";");
                            int len = cookies.length;
                            Vector out = new Vector();
                            String domain = uri.getHost();
                            for ( int i=0; i<len; i++){
                                Cookie c = new Cookie();
                                String[] parts = cookies[i].split("=");
                                c.setName(parts[0].trim());
                                if(parts.length > 1){
                                    c.setValue(parts[1].trim());
                                }else{
                                    c.setValue("");                                
                                }
                                c.setDomain(domain);
                                out.add(c);
                            }
                            Cookie[] cookiesArr = new Cookie[out.size()];
                            out.toArray(cookiesArr);
                            AndroidImplementation.this.addCookie(cookiesArr, false);
                        }

                    } catch (URISyntaxException ex) {

                    }
                    
                    parent.fireWebEvent("onLoadResource", new ActionEvent(url));
                    super.onLoadResource(view, url);
                    setShouldCalcPreferredSize(true);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    parent.fireWebEvent("onStart", new ActionEvent(url));
                    super.onPageStarted(view, url, favicon);
                    dismissProgress();
                    //show the progress only if there is no ActionBar
                    if(!hideProgress && !isNativeTitle()){
                        progressBar = ProgressDialog.show(activity, null, "Loading...");
                        //if the page hasn't finished for more the 10 sec, dismiss 
                        //the dialog
                        Timer t= new Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                dismissProgress();
                            }
                        }, 10000);
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    parent.fireWebEvent("onLoad", new ActionEvent(url));
                    super.onPageFinished(view, url);
                    setShouldCalcPreferredSize(true);
                    dismissProgress();
                }
                
                private void dismissProgress() {
                    if (progressBar != null && progressBar.isShowing()) {
                        progressBar.dismiss();
                        Display.getInstance().callSerially(new Runnable() {

                            public void run() {
                                setVisible(true);
                                repaint();
                            }
                        });
                    }
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    parent.fireWebEvent("onError", new ActionEvent(description, errorCode));
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    super.shouldOverrideKeyEvent(view, null);
                    dismissProgress();
                }

                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                    int keyCode = event.getKeyCode();
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
                        return true;
                    }

                    return super.shouldOverrideKeyEvent(view, event);
                }

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("jar:")) {
                        setURL(url);
                        return true;
                    }
                    
                    // this will fail if dial permission isn't declared
                    if(url.startsWith("tel:")) {
                        if(parent.getBrowserNavigationCallback().shouldNavigate(url)) {
                            try {
                                Intent dialer = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse(url));
                                activity.startActivity(dialer);
                            } catch(Throwable t) {}
                        }
                        return true;
                    }
                    return !parent.getBrowserNavigationCallback().shouldNavigate(url); 
                }
                
                
            });
            
            web.setWebChromeClient(new WebChromeClient(){
                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    com.codename1.io.Log.p("["+consoleMessage.messageLevel()+"] "+consoleMessage.message()+" On line "+consoleMessage.lineNumber()+" of "+consoleMessage.sourceId());
                    return true;
                }

               @Override 
               public void onProgressChanged(WebView view, int newProgress) {
                    if(!hideProgress && isNativeTitle() && getCurrentForm() != null && getCurrentForm().getTitle() != null && getCurrentForm().getTitle().length() > 0 ){
                        if(activity != null){
                            try{
                                activity.setProgressBarVisibility(true);
                                activity.setProgress(newProgress * 100);
                                if(newProgress == 100){
                                    activity.setProgressBarVisibility(false);
                                }                            
                            }catch(Throwable t){
                            }
                        }
                    }
                }
                 
                @Override
                public void onGeolocationPermissionsShowPrompt(String origin,
                        GeolocationPermissions.Callback callback) {
                    // Always grant permission since the app itself requires location
                    // permission and the user has therefore already granted it
                    callback.invoke(origin, true, false);
                }
            });
        }
        
        @Override
        protected void initComponent() {
            super.initComponent();
            setPeerImage(null);
        }
        
        
        @Override
        protected Image generatePeerImage() {
            try {
                final Bitmap nativeBuffer = Bitmap.createBitmap(
                        getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Image image = new AndroidImplementation.NativeImage(nativeBuffer);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Canvas canvas = new Canvas(nativeBuffer);
                            web.draw(canvas);
                        } catch(Throwable t) {
                            t.printStackTrace();
                        }
                    }
                });
                return image;
            } catch(Throwable t) {
                t.printStackTrace();
                return Image.createImage(5, 5);
            }
        }
        
        protected boolean shouldRenderPeerImage() {
            return lightweightMode || !isInitialized();
        }

        protected void setLightweightMode(boolean l) {
            doSetVisibility(!l);
            if (lightweightMode == l) {
                return;
            }
            lightweightMode = l;
        }
        
        

        public void setScrollingEnabled(boolean enabled){
            this.scrollingEnabled = enabled;
            web.setHorizontalScrollBarEnabled(enabled);
            web.setVerticalScrollBarEnabled(enabled);
            if ( !enabled ){
                web.setOnTouchListener(new View.OnTouchListener(){

                    @Override
                    public boolean onTouch(View view, MotionEvent me) {
                        return (me.getAction() == MotionEvent.ACTION_MOVE);
                    }

                });
            } else {
               web.setOnTouchListener(null);
            }
            
        }
        
        public boolean isScrollingEnabled(){
            return scrollingEnabled;
        }
        
        public void setProperty(final String key, final Object value) {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    WebSettings s = web.getSettings();
                    String methodName = "set" + key;
                    for (Method m : s.getClass().getMethods()) {
                        if (m.getName().equalsIgnoreCase(methodName) && m.getParameterTypes().length == 0) {
                            try {
                                m.invoke(s, value);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }
                    }
                }
            });
        }

        public String getTitle() {
            final String[] retVal = new String[1];
            
            act.runOnUiThread(new Runnable() {
                public void run() {
                    retVal[0] = web.getTitle();
                }
            });
            
            Display.getInstance().invokeAndBlock(new Runnable() {
                @Override
                public void run() {
                    while (retVal[0] == null) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            });
            return retVal[0];
        }

        public String getURL() {
            final String[] retVal = new String[1];
            
            act.runOnUiThread(new Runnable() {
                public void run() {
                    retVal[0] = web.getUrl();
                }
            });
            
            Display.getInstance().invokeAndBlock(new Runnable() {
                @Override
                public void run() {
                    while (retVal[0] == null) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            });
            return retVal[0];
        }

        public void setURL(final String url) {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.loadUrl(url);
                }
            });
        }

        public void reload() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.reload();
                }
            });
        }

        public boolean hasBack() {
            final Boolean [] retVal = new Boolean[1];
            
            act.runOnUiThread(new Runnable() {
                public void run() {
                    retVal[0] = web.canGoBack();
                }
            });
            
            Display.getInstance().invokeAndBlock(new Runnable() {
                @Override
                public void run() {
                    while (retVal[0] == null) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            });
            return retVal[0].booleanValue();
        }

        public boolean hasForward() {
            final Boolean [] retVal = new Boolean[1];
            
            act.runOnUiThread(new Runnable() {
                public void run() {
                    retVal[0] = web.canGoForward();
                }
            });
            
            Display.getInstance().invokeAndBlock(new Runnable() {
                @Override
                public void run() {
                    while (retVal[0] == null) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            });
            return retVal[0].booleanValue();
        }

        public void back() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.goBack();
                }
            });
        }

        public void forward() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.goForward();
                }
            });
        }

        public void clearHistory() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.clearHistory();
                }
            });
        }

        public void stop() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.stopLoading();
                }
            });
        }
        
        public void destroy() {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.destroy();
                }
            });
        }
        
        public void setPage(final String html, final String baseUrl) {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.loadDataWithBaseURL(baseUrl, html, "text/html", "UTF-8", null);
                }
            });
        }

        public void exposeInJavaScript(final Object o, final String name) {
            act.runOnUiThread(new Runnable() {
                public void run() {
                    web.addJavascriptInterface(o, name);
                }
            });
        }

        public  void setPinchZoomEnabled(boolean e) {
            web.getSettings().setSupportZoom(e);
            web.getSettings().setBuiltInZoomControls(e);
        }
    }

    private Context getContext() {
        return activity;
    }

    public Object connect(String url, boolean read, boolean write, int timeout) throws IOException {
        URL u = new URL(url);
        CookieHandler.setDefault(null);
        URLConnection con = u.openConnection();
        if (con instanceof HttpURLConnection) {
            HttpURLConnection c = (HttpURLConnection) con;
            c.setUseCaches(false);
            c.setDefaultUseCaches(false);
            c.setInstanceFollowRedirects(false);
            if(timeout > -1) {
                c.setConnectTimeout(timeout);
            }
            if(read){
                if(timeout > -1) {                
                    c.setReadTimeout(timeout);
                }else{
                    c.setReadTimeout(7000);                
                }
            }
        }
        con.setDoInput(read);
        con.setDoOutput(write);
        return con;
    }
    
    /**
     * @inheritDoc
     */
    public Object connect(String url, boolean read, boolean write) throws IOException {
        return connect(url, read, write, timeout);
    }

    /**
     * @inheritDoc
     */
    public void setHeader(Object connection, String key, String val) {
        ((URLConnection) connection).setRequestProperty(key, val);
    }

    /**
     * @inheritDoc
     */
    public OutputStream openOutputStream(Object connection) throws IOException {
        if (connection instanceof String) {
            String con = (String)connection;
            if (con.startsWith("file://")) {
                con = con.substring(7);
            }

            FileOutputStream fc = new FileOutputStream((String) con);
            BufferedOutputStream o = new BufferedOutputStream(fc, (String) con);
            return o;
        }
        return new BufferedOutputStream(((URLConnection) connection).getOutputStream(), connection.toString());
    }

    /**
     * @inheritDoc
     */
    public OutputStream openOutputStream(Object connection, int offset) throws IOException {
        String con = (String) connection;
        if (con.startsWith("file://")) {
            con = con.substring(7);
        }
        RandomAccessFile rf = new RandomAccessFile(con, "rw");
        rf.seek(offset);
        FileOutputStream fc = new FileOutputStream(rf.getFD());
        BufferedOutputStream o = new BufferedOutputStream(fc, con);
        o.setConnection(rf);
        return o;
    }

    /**
     * @inheritDoc
     */
    public void cleanup(Object o) {
        try {
            super.cleanup(o);
            if (o != null) {
                if (o instanceof RandomAccessFile) {
                    ((RandomAccessFile) o).close();
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @inheritDoc
     */
    public InputStream openInputStream(Object connection) throws IOException {
        if (connection instanceof String) {
            String con = (String) connection;
            if (con.startsWith("file://")) {
                con = con.substring(7);
            }
            FileInputStream fc = new FileInputStream(con);
            BufferedInputStream o = new BufferedInputStream(fc, con);
            return o;
        }
        if(connection instanceof HttpURLConnection) {
            HttpURLConnection ht = (HttpURLConnection)connection;
            if(ht.getResponseCode() < 400) {
                return new BufferedInputStream(ht.getInputStream());
            }
            return new BufferedInputStream(ht.getErrorStream());
        } else {
            return new BufferedInputStream(((URLConnection) connection).getInputStream());
        }        
    }

    /**
     * @inheritDoc
     */
    public void setHttpMethod(Object connection, String method) throws IOException {
        ((HttpURLConnection) connection).setRequestMethod(method);
    }

    /**
     * @inheritDoc
     */
    public void setPostRequest(Object connection, boolean p) {
        try {
            if (p) {
                ((HttpURLConnection) connection).setRequestMethod("POST");
            } else {
                ((HttpURLConnection) connection).setRequestMethod("GET");
            }
        } catch (IOException err) {
            // an exception here doesn't make sense
            err.printStackTrace();
        }
    }

    /**
     * @inheritDoc
     */
    public int getResponseCode(Object connection) throws IOException {
        return ((HttpURLConnection) connection).getResponseCode();
    }

    /**
     * @inheritDoc
     */
    public String getResponseMessage(Object connection) throws IOException {
        return ((HttpURLConnection) connection).getResponseMessage();
    }

    /**
     * @inheritDoc
     */
    public int getContentLength(Object connection) {
        return ((HttpURLConnection) connection).getContentLength();
    }

    /**
     * @inheritDoc
     */
    public String getHeaderField(String name, Object connection) throws IOException {
        return ((HttpURLConnection) connection).getHeaderField(name);
    }

    /**
     * @inheritDoc
     */
    public String[] getHeaderFieldNames(Object connection) throws IOException {
        Set<String> s = ((HttpURLConnection) connection).getHeaderFields().keySet();
        String[] resp = new String[s.size()];
        s.toArray(resp);
        return resp;
    }

    /**
     * @inheritDoc
     */
    public String[] getHeaderFields(String name, Object connection) throws IOException {
        HttpURLConnection c = (HttpURLConnection) connection;
        List<String> headers = new ArrayList<String>();
        
        // we need to merge headers with differing case since this should be case insensitive
        for(String key : c.getHeaderFields().keySet()) {
            if(key != null && key.equalsIgnoreCase(name)) {
                headers.addAll(c.getHeaderFields().get(key));
            }
        }
        if (headers.size() > 0) {
            List<String> v = new ArrayList<String>();
            v.addAll(headers);
            Collections.reverse(v);
            String[] s = new String[v.size()];
            v.toArray(s);
            return s;
        }
        // workaround for a bug in some android devices
        String f = c.getHeaderField(name);
        if(f != null && f.length() > 0) {
            return new String[] {f};
        }
        return null;



    }

    /**
     * @inheritDoc
     */
    public void deleteStorageFile(String name) {
        getContext().deleteFile(name);
    }

    /**
     * @inheritDoc
     */
    public OutputStream createStorageOutputStream(String name) throws IOException {
        return getContext().openFileOutput(name, 0);
    }

    /**
     * @inheritDoc
     */
    public InputStream createStorageInputStream(String name) throws IOException {
        return getContext().openFileInput(name);
    }

    /**
     * @inheritDoc
     */
    public boolean storageFileExists(String name) {
        String[] fileList = getContext().fileList();
        for (int iter = 0; iter < fileList.length; iter++) {
            if (fileList[iter].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @inheritDoc
     */
    public String[] listStorageEntries() {
        return getContext().fileList();
    }

    /**
     * @inheritDoc
     */
    public int getStorageEntrySize(String name) {
        return (int)new File(getContext().getFilesDir(), name).length();
    }
    
    /**
     * @inheritDoc
     */
    public String[] listFilesystemRoots() {
        String [] storageDirs = getStorageDirectories();
        if(storageDirs != null){
            String [] roots = new String[storageDirs.length + 1];
            System.arraycopy(storageDirs, 0, roots, 0, storageDirs.length);
            roots[roots.length - 1] = Environment.getRootDirectory().getAbsolutePath();
            return roots;
        }
        return new String[]{Environment.getRootDirectory().getAbsolutePath()};
    }
    
    private String[] getStorageDirectories() {
        String [] storageDirs = null;
        
        String storageDev = Environment.getExternalStorageDirectory().getPath();
        String storageRoot = storageDev.substring(0, storageDev.length() - 1);
        BufferedReader bufReader = null;
        
        try {
            bufReader = new BufferedReader(new FileReader("/proc/mounts"));
            ArrayList<String> list = new ArrayList<String>();
            String line;
            
            while ((line = bufReader.readLine()) != null) {
                if (line.contains("vfat") || line.contains("/mnt") || line.contains("/storage")) {
                    StringTokenizer tokens = new StringTokenizer(line, " ");
                    String s = tokens.nextToken();
                    s = tokens.nextToken(); // Take the second token, i.e. mount point
                    
                    if (s.indexOf("secure") != -1) {
                        continue;
                    }

                    if (s.startsWith(storageRoot) == true) {
                        list.add(s);
                        continue;
                    }

                    if (line.contains("vfat") && line.contains("/mnt")) {
                        list.add(s);
                        continue;
                    }
                }
            }

            int count = list.size();
            
            if (count < 2) {
                storageDirs = new String[] {
                    storageDev
                };
            }
            else {
                storageDirs = new String[count];

                for (int i = 0; i < count; i++) {
                    storageDirs[i] = (String) list.get(i);
                }
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
        finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                }
                catch (IOException e) {}
            }

            return storageDirs;
        }
    }    

    /**
     * @inheritDoc
     */
    public String getAppHomePath() {
        return getContext().getFilesDir().getAbsolutePath();
    }

    /**
     * @inheritDoc
     */
    public String[] listFiles(String directory) throws IOException {
        return new File(directory).list();
    }

    /**
     * @inheritDoc
     */
    public long getRootSizeBytes(String root) {
        return -1;
    }

    /**
     * @inheritDoc
     */
    public long getRootAvailableSpace(String root) {
        return -1;
    }

    /**
     * @inheritDoc
     */
    public void mkdir(String directory) {
        new File(directory).mkdirs();
    }

    /**
     * @inheritDoc
     */
    public void deleteFile(String file) {
        File f = new File(file);
        f.delete();
    }

    /**
     * @inheritDoc
     */
    public boolean isHidden(String file) {
        return new File(file).isHidden();
    }

    /**
     * @inheritDoc
     */
    public void setHidden(String file, boolean h) {
    }

    /**
     * @inheritDoc
     */
    public long getFileLength(String file) {
        return new File(file).length();
    }

    /**
     * @inheritDoc
     */
    public long getFileLastModified(String file) {
        return new File(file).lastModified();
    }
    
    /**
     * @inheritDoc
     */
    public boolean isDirectory(String file) {
        return new File(file).isDirectory();
    }

    /**
     * @inheritDoc
     */
    public char getFileSystemSeparator() {
        return File.separatorChar;
    }

    /**
     * @inheritDoc
     */
    public OutputStream openFileOutputStream(String file) throws IOException {
        return new FileOutputStream(file);
    }

    /**
     * @inheritDoc
     */
    public InputStream openFileInputStream(String file) throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public boolean isMultiTouch() {
        return true;
    }

    /**
     * @inheritDoc
     */
    public boolean exists(String file) {
        if (file.startsWith("file://")) {
            file = file.substring(7);
        }
        return new File(file).exists();
    }

    /**
     * @inheritDoc
     */
    public void rename(String file, String newName) {
        if (file.startsWith("file://")) {
            file = file.substring(7);
        }
        new File(file).renameTo(new File(new File(file).getParentFile(), newName));
    }

    /**
     * @inheritDoc
     */
    public boolean shouldWriteUTFAsGetBytes() {
        return true;
    }

    /**
     * @inheritDoc
     */
    /*public void startThread(String name, Runnable r) {
     new Thread(Thread.currentThread().getThreadGroup(), r, name, 64 * 1024).start();
     }*/
    /**
     * @inheritDoc
     */
    public void closingOutput(OutputStream s) {
        // For some reasons the Android guys chose not doing this by default:
        // http://android-developers.blogspot.com/2010/12/saving-data-safely.html
        // this seems to be a mistake of sacrificing stability for minor performance
        // gains which will only be noticeable on a server.
        if (s != null) {
            if (s instanceof FileOutputStream) {
                try {
                    FileDescriptor fd = ((FileOutputStream) s).getFD();
                    if (fd != null) {
                        fd.sync();
                    }
                } catch (IOException ex) {
                    // this exception doesn't help us
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    public void printStackTraceToStream(Throwable t, Writer o) {
        PrintWriter p = new PrintWriter(o);
        t.printStackTrace(p);
    }

    /**
     * This method returns the platform Location Control
     *
     * @return LocationControl Object
     */
    public LocationManager getLocationManager() {
        return AndroidLocationManager.getInstance(activity);
    }

    private String fixAttachmentPath(String attachment) {
        if (attachment.contains(getAppHomePath())) {
            FileSystemStorage fs = FileSystemStorage.getInstance();
            final char sep = fs.getFileSystemSeparator();
            String fileName = attachment.substring(attachment.lastIndexOf(sep) + 1);
            String[] roots = FileSystemStorage.getInstance().getRoots();
            // iOS doesn't have an SD card
            String root = roots[0];
            for (int i = 0; i < roots.length; i++) {
                if (FileSystemStorage.getInstance().getRootType(roots[i]) == FileSystemStorage.ROOT_TYPE_SDCARD) {
                    root = roots[i];
                    break;
                }
            }
            String fileUri = root + sep + "tmp" + sep + fileName;
            FileSystemStorage.getInstance().mkdir(root + sep + "tmp");
            try {
                InputStream is = FileSystemStorage.getInstance().openInputStream(attachment);
                OutputStream os = FileSystemStorage.getInstance().openOutputStream(fileUri);
                byte [] buf = new byte[1024];
                int len;
                while((len = is.read(buf)) > -1){
                    os.write(buf, 0, len);
                }
                is.close();
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AndroidImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }

            attachment = fileUri;
        }
        if (attachment.indexOf(":") < 0) {
            attachment = "file://" + attachment;
        }
        return attachment;
    }
    
    /**
     * @inheritDoc
     */
    public void sendMessage(String[] recipients, String subject, Message msg) {
        if(editInProgress()) {
            stopEditing();
        }
        Intent emailIntent;
        String attachment = msg.getAttachment();
        boolean hasAttachment = (attachment != null && attachment.length() > 0) || msg.getAttachments().size() > 0;
            
        if(msg.getMimeType().equals(Message.MIME_TEXT) && !hasAttachment){
            StringBuilder to = new StringBuilder();
            for (int i = 0; i < recipients.length; i++) {
                to.append(recipients[i]);
                to.append(";");
            }
            emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse(
                    "mailto:" + to.toString()
                    + "?subject=" + Uri.encode(subject)
                    + "&body=" + Uri.encode(msg.getContent())));        
        }else{
            if (hasAttachment) {
                if(msg.getAttachments().size() > 0) {
                    emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                    emailIntent.setType(msg.getMimeType());
                    ArrayList<Uri> uris = new ArrayList<Uri>();
                    
                    for(String path : msg.getAttachments().keySet()) {
                        uris.add(Uri.parse(fixAttachmentPath(path)));
                    }
                    
                    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                } else {
                    emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                    emailIntent.setType(msg.getMimeType());
                    emailIntent.setType(msg.getAttachmentMimeType());
                    //if the attachment is in the uder home dir we need to copy it 
                    //to an accessible dir
                    attachment = fixAttachmentPath(attachment);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(attachment));
                }
            } else {
                emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                emailIntent.setType(msg.getMimeType());
            }
            if (msg.getMimeType().equals(Message.MIME_HTML)) {
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(msg.getContent()));                                
            }else{
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg.getContent());                    
            }
            
        }
        final String attach = attachment;
        AndroidNativeUtil.startActivityForResult(Intent.createChooser(emailIntent, "Send mail..."), new IntentResultListener() {

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(attach != null && attach.length() > 0 && attach.contains("tmp")){
                    FileSystemStorage.getInstance().delete(attach);
                }
            }
        });
    }

    /**
     * @inheritDoc
     */
    public void dial(String phoneNumber) {
        Intent dialer = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        activity.startActivity(dialer);
    }

    /**
     * @inheritDoc
     */
    public void sendSMS(final String phoneNumber, final String message) throws IOException {
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
//                new Intent("SMS_DELIVERED"), 0);
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);
        sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
    }
    
    @Override
    public void dismissNotification(Object o) {
        Integer n = (Integer)o;
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(n.intValue());
    }
    
    @Override
    public boolean isNotificationSupported() {
        return true;
    }

    @Override
    public Object notifyStatusBar(String tickerText, String contentTitle,
            String contentBody, boolean vibrate, boolean flashLights, Hashtable args) {
        int id = activity.getResources().getIdentifier("icon", "drawable", activity.getApplicationInfo().packageName);

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Activity.NOTIFICATION_SERVICE);
        Notification notification = new Notification(id, tickerText, System.currentTimeMillis());

        if (flashLights) {
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        }
        if (vibrate) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        
        int notifyId = 10001;
        if(args != null) {
            Boolean b = (Boolean)args.get("persist");
            if(b != null && b.booleanValue()) {
                notification.defaults |= Notification.FLAG_ONGOING_EVENT;
            }
            
            Integer notId = (Integer)args.get("id");
            if(notId != null) {
                notifyId = notId.intValue();
            }
        }
        
        Intent notificationIntent = new Intent();
        notificationIntent.setComponent(activity.getComponentName());
        PendingIntent contentIntent = PendingIntent.getActivity(activity, 0, notificationIntent, 0);

        notification.setLatestEventInfo(activity, contentTitle, contentBody, contentIntent);
        notificationManager.notify(notifyId, notification);
        return new Integer(notifyId);
    }

    @Override
    public String[] getAllContacts(boolean withNumbers) {
        return AndroidContactsManager.getInstance().getContacts(activity, withNumbers);
    }

    @Override
    public Contact getContactById(String id) {
        return AndroidContactsManager.getInstance().getContact(activity, id);
    }

    @Override 
    public Contact getContactById(String id, boolean includesFullName, boolean includesPicture, 
            boolean includesNumbers, boolean includesEmail, boolean includeAddress){
        return AndroidContactsManager.getInstance().getContact(activity, id, includesFullName, includesPicture, 
            includesNumbers, includesEmail, includeAddress);
    }
    
    public String createContact(String firstName, String surname, String officePhone, String homePhone, String cellPhone, String email) {
         return AndroidContactsManager.getInstance().createContact(activity, firstName, surname, officePhone, homePhone, cellPhone, email);
    }

    public boolean deleteContact(String id) {
        return AndroidContactsManager.getInstance().deleteContact(activity, id);
    }
    
    @Override
    public boolean isNativeShareSupported() {
        return true;
    }

    @Override
    public void share(String text, String image, String mimeType){   
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        if(image == null){
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        }else{
            shareIntent.setType(mimeType);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(fixAttachmentPath(image)));
        }
        activity.startActivity(Intent.createChooser(shareIntent, "Share with..."));
    }

    /**
     * @inheritDoc
     */
    public String getPlatformName() {
        return "and";
    }

    /**
     * @inheritDoc
     */
    public String[] getPlatformOverrides() {
        if (isTablet()) {
            return new String[]{"tablet", "android", "android-tab"};
        } else {
            return new String[]{"phone", "android", "android-phone"};
        }
    }
    
    /**
     * @inheritDoc
     */
    public void copyToClipboard(final Object obj) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < 11) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(obj.toString());
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = ClipData.newPlainText("Codename One", obj.toString());
                    clipboard.setPrimaryClip(clip);
                }        
            }
        });
    }

    /**
     * @inheritDoc
     */
    public Object getPasteDataFromClipboard() {
        final Object[] response = new Object[1];
        runOnUiThreadAndBlock(new Runnable() {
            @Override
            public void run() {
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < 11) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    response[0] = clipboard.getText().toString();
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    response[0] = item.getText();    
                }
            }
        });
        return response[0];
    }
    
    
    

    public class Video extends AndroidImplementation.AndroidPeer implements Media {

        private VideoView nativeVideo;
        private Activity activity;
        private boolean fullScreen = false;
        private Rectangle bounds;
        private boolean nativeController = true;
        private boolean nativePlayer;
        private Form curentForm;

        public Video(final VideoView nativeVideo, final Activity activity, final Runnable onCompletion) {
            super(nativeVideo);
            this.nativeVideo = nativeVideo;
            this.activity = activity;
            if (nativeController) {
                MediaController mc = new AndroidImplementation.CN1MediaController();
                nativeVideo.setMediaController(mc);
            }

            nativeVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    if (onCompletion != null) {
                        onCompletion.run();
                    }
                }
            });

            nativeVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (onCompletion != null) {
                        onCompletion.run();
                    }
                    return false;
                }
            });

        }
        
        @Override
        public void init() {
            super.init();
            setVisible(true);
        }        
        
        public void prepare() { 
        }

        @Override
        public void play() {
            if (nativePlayer && curentForm == null) {
                curentForm = Display.getInstance().getCurrent();
                Form f = new Form();
                f.setLayout(new BorderLayout());
                f.addComponent(BorderLayout.CENTER, getVideoComponent());
                f.show();
            }
            nativeVideo.start();
        }

        @Override
        public void pause() {
            nativeVideo.stopPlayback();
        }

        @Override
        public void cleanup() {
            nativeVideo.stopPlayback();
            nativeVideo = null;
            if (nativePlayer && curentForm != null) {
                curentForm.showBack();
                curentForm = null;
            }
        }

        @Override
        public int getTime() {
            return nativeVideo.getCurrentPosition();
        }

        @Override
        public void setTime(int time) {
            nativeVideo.seekTo(time);
        }

        @Override
        public int getDuration() {
            return nativeVideo.getDuration();
        }

        @Override
        public void setVolume(int vol) {
            // float v = ((float) vol) / 100.0F;
            AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
        }

        @Override
        public int getVolume() {
            AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            return am.getStreamVolume(AudioManager.STREAM_MUSIC);
        }

        @Override
        public boolean isVideo() {
            return true;
        }

        @Override
        public boolean isFullScreen() {
            return fullScreen || nativePlayer;
        }

        @Override
        public void setFullScreen(boolean fullScreen) {
            this.fullScreen = fullScreen;
            if (fullScreen) {
                bounds = new Rectangle(getBounds());
                setX(0);
                setY(0);
                setWidth(Display.getInstance().getDisplayWidth());
                setHeight(Display.getInstance().getDisplayHeight());
            } else {
                if (bounds != null) {
                    setX(bounds.getX());
                    setY(bounds.getY());
                    setWidth(bounds.getSize().getWidth());
                    setHeight(bounds.getSize().getHeight());
                }
            }
            repaint();
        }

        @Override
        public Component getVideoComponent() {
            return this;
        }

        @Override
        protected Dimension calcPreferredSize() {
            return new Dimension(nativeVideo.getWidth(), nativeVideo.getHeight());
        }

        @Override
        public void setNativePlayerMode(boolean nativePlayer) {
            this.nativePlayer = nativePlayer;
        }

        @Override
        public boolean isNativePlayerMode() {
            return nativePlayer;
        }

        @Override
        public boolean isPlaying() {
            return nativeVideo.isPlaying();
        }

        public void setVariable(String key, Object value) {
        }

        public Object getVariable(String key) {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        
        if (requestCode == ZOOZ_PAYMENT) {
            ((IntentResultListener) pur).onActivityResult(requestCode, resultCode, intent);
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE) {
                try {
                    String imageUri = (String) Storage.getInstance().readObject("imageUri");
                    Vector pathandId = StringUtil.tokenizeString(imageUri, ";");
                    String path = (String)pathandId.get(0);
                    String lastId = (String)pathandId.get(1);                    
                    Storage.getInstance().deleteStorageFile("imageUri");                                        
                    clearMediaDB(lastId, path);
                    callback.fireActionEvent(new ActionEvent(path));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAPTURE_VIDEO) {
                String path = (String) Storage.getInstance().readObject("videoUri");
                Storage.getInstance().deleteStorageFile("videoUri");                                        
                callback.fireActionEvent(new ActionEvent(path));
                return;
            } else if (requestCode == CAPTURE_AUDIO) {
                Uri data = intent.getData();
                String path = convertImageUriToFilePath(data, activity);
                callback.fireActionEvent(new ActionEvent(path));
                return;
            } else if (requestCode == OPEN_GALLERY) {
                Uri selectedImage = intent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                
                // this happens on Android devices, not exactly sure what the use case is
                if(cursor == null) {
                    callback.fireActionEvent(null);
                    return;
                }
                
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                callback.fireActionEvent(new ActionEvent(filePath));
                return;
            } else {
                if(callback != null) {
                    callback.fireActionEvent(new ActionEvent("ok"));
                }
                return;
            }
        }
        //clean imageUri
        String imageUri = (String) Storage.getInstance().readObject("imageUri");
        if(imageUri != null){
            Storage.getInstance().deleteStorageFile("imageUri");                                        
        }
        
        if(callback != null) {
            callback.fireActionEvent(null);
        }
    }

    @Override
    public void capturePhoto(ActionListener response) {
        callback = new EventDispatcher();
        callback.addListener(response);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        File newFile = getOutputMediaFile(false);
        Uri imageUri = Uri.fromFile(newFile);

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        
        String lastImageID = getLastImageId();
        Storage.getInstance().writeObject("imageUri", newFile.getAbsolutePath() + ";" + lastImageID);

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        this.activity.startActivityForResult(intent, CAPTURE_IMAGE);
    }

    @Override
    public void captureVideo(ActionListener response) {
        callback = new EventDispatcher();
        callback.addListener(response);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        
        File newFile = getOutputMediaFile(true);
        Uri videoUri = Uri.fromFile(newFile);

        Storage.getInstance().writeObject("videoUri", newFile.getAbsolutePath());

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, videoUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        this.activity.startActivityForResult(intent, CAPTURE_VIDEO);
    }

    @Override
    public void captureAudio(ActionListener response) {
        callback = new EventDispatcher();
        callback.addListener(response);
        Intent intent = new Intent(android.provider.MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        this.activity.startActivityForResult(intent, CAPTURE_AUDIO);
    }

    /**
     * Opens the device image gallery
     *
     * @param response callback for the resulting image
     */
    public void openImageGallery(ActionListener response) {
        callback = new EventDispatcher();
        callback.addListener(response);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        this.activity.startActivityForResult(galleryIntent, OPEN_GALLERY);
    }

    class NativeImage extends Image {

        public NativeImage(Bitmap nativeImage) {
            super(nativeImage);
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(boolean isVideo) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        return GetOutputMediaFile.getOutputMediaFile(isVideo, activity);
    }
    
    private static class GetOutputMediaFile {
        public static File getOutputMediaFile(boolean isVideo, Activity activity) {
            activity.getComponentName();

            File mediaStorageDir = null;
            if(android.os.Build.VERSION.SDK_INT >= 8) {
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                     Environment.DIRECTORY_PICTURES), "" + activity.getTitle());
            } else {
                mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "" + activity.getTitle());
            }            
            
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(Display.getInstance().getProperty("AppName", "CodenameOne"), "failed to create directory");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = null;
            if (!isVideo) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "VID_" + timeStamp + ".mp4");
            }

            return mediaFile;
        }
    }
    
    @Override
    public void systemOut(String content){
        Log.d(Display.getInstance().getProperty("AppName", "CodenameOne"), content);
    }

    private boolean hasAndroidMarket() {
        return hasAndroidMarket(activity);
    }

    private static final String GooglePlayStorePackageNameOld = "com.google.market";
    private static final String GooglePlayStorePackageNameNew = "com.android.vending";

    /**
     * Indicates whether this is a Google certified device which means that it
     * has Android market etc.
     */
    public static boolean hasAndroidMarket(Context activity) {
        final PackageManager packageManager = activity.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(GooglePlayStorePackageNameOld) ||
                packageInfo.packageName.equals(GooglePlayStorePackageNameNew)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerPush(Hashtable metaData, boolean noFallback) {
        boolean has = hasAndroidMarket();
        if (noFallback && !has) {
            Log.d("Codename One", "Device doesn't have Android market/google play can't register for push!");
            return;
        }
        String id = (String)metaData.get(com.codename1.push.Push.GOOGLE_PUSH_KEY);
        if(has) {
            Log.d("Codename One", "Sending async push request for id: " + id);
            ((CodenameOneActivity) activity).registerForPush(id);
        } else {
            PushNotificationService.forceStartService(activity.getPackageName() + ".PushNotificationService", activity);
            if(!registerServerPush(id, getApplicationKey(), (byte)10, getProperty("UDID", ""), getPackageName())) {
                sendPushRegistrationError("Server registration error", 1);
            } 
        }
    }

    public static void stopPollingLoop() {
        stopPolling();
    }

    public static void registerPolling() {
        registerPollingFallback();
    }

    @Override
    public void deregisterPush() {
        boolean has = hasAndroidMarket();
        if (has) {
            ((CodenameOneActivity) activity).stopReceivingPush();
            deregisterPushFromServer();
        } else {
            super.deregisterPush();
        }
    }

    private static String convertImageUriToFilePath(Uri imageUri, Activity activity) {
        Cursor cursor = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        cursor = activity.getContentResolver().query(imageUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    class CN1MediaController extends MediaController {

        public CN1MediaController() {
            super(activity);
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            int keycode = event.getKeyCode();
            keycode = CodenameOneView.internalKeyCodeTranslate(keycode);
            if (keycode == AndroidImplementation.DROID_IMPL_KEY_BACK) {
                Display.getInstance().keyPressed(keycode);
                Display.getInstance().keyReleased(keycode);
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        }
    }
    private L10NManager l10n;

    /**
     * @inheritDoc
     */
    public L10NManager getLocalizationManager() {
        if (l10n == null) {
            Locale l = Locale.getDefault();
            l10n = new L10NManager(l.getLanguage(), l.getCountry()) {
                public String format(int number) {
                    return NumberFormat.getNumberInstance().format(number);
                }

                public String format(double number) {
                    return NumberFormat.getNumberInstance().format(number);
                }

                public String formatCurrency(double currency) {
                    return NumberFormat.getCurrencyInstance().format(currency);
                }

                public String formatDateLongStyle(Date d) {
                    return DateFormat.getDateInstance(DateFormat.LONG).format(d);
                }

                public String formatDateShortStyle(Date d) {
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(d);
                }

                public String formatDateTime(Date d) {
                    return DateFormat.getDateTimeInstance().format(d);
                }

                public String getCurrencySymbol() {
                    return NumberFormat.getInstance().getCurrency().getSymbol();
                }

                public void setLocale(String locale, String language) {
                    super.setLocale(locale, language);
                    Locale l = new Locale(language, locale);
                    Locale.setDefault(l);
                }
            };
        }
        return l10n;
    }
    private com.codename1.ui.util.ImageIO imIO;

    @Override
    public com.codename1.ui.util.ImageIO getImageIO() {
        if (imIO == null) {
            imIO = new com.codename1.ui.util.ImageIO() {
                @Override
                public void save(InputStream image, OutputStream response, String format, int width, int height, float quality) throws IOException {
                    Bitmap.CompressFormat f = Bitmap.CompressFormat.PNG;
                    if (format == FORMAT_JPEG) {
                        f = Bitmap.CompressFormat.JPEG;
                    }
                    Image img = Image.createImage(image).scaled(width, height);
                    if (width < 0) {
                        width = img.getWidth();
                    }
                    if (height < 0) {
                        width = img.getHeight();
                    }
                    Bitmap b = (Bitmap) img.getImage();
                    b.compress(f, (int) (quality * 100), response);
                }

                public void save(String imageFilePath, OutputStream response, String format, int width, int height, float quality) throws IOException {
                    Image i = Image.createImage(imageFilePath);
                    Image newImage = i.scaled(width, height);
                    save(newImage, response, format, quality);
                    newImage.dispose();
                    i.dispose();
                } 

                @Override
                protected void saveImage(Image img, OutputStream response, String format, float quality) throws IOException {
                    Bitmap.CompressFormat f = Bitmap.CompressFormat.PNG;
                    if (format == FORMAT_JPEG) {
                        f = Bitmap.CompressFormat.JPEG;
                    }
                    Bitmap b = (Bitmap) img.getImage();
                    b.compress(f, (int) (quality * 100), response);
                }

                @Override
                public boolean isFormatSupported(String format) {
                    return format == FORMAT_JPEG || format == FORMAT_PNG;
                }
            };
        }
        return imIO;
    }

    @Override
    public Database openOrCreateDB(String databaseName) throws IOException {
        SQLiteDatabase db = activity.openOrCreateDatabase(databaseName, activity.MODE_PRIVATE, null);        
        return new AndroidDB(db);
    }

    @Override
    public void deleteDB(String databaseName) throws IOException {
        activity.deleteDatabase(databaseName);
    }

    @Override
    public boolean existsDB(String databaseName) {
        File db = new File(activity.getApplicationInfo().dataDir + "/databases/" + databaseName);
        return db.exists();
    }

    public String getDatabasePath(String databaseName) {
        File db = new File(activity.getApplicationInfo().dataDir + "/databases/" + databaseName);
        return db.getAbsolutePath();
    }
    
    public boolean isNativeTitle() {
        return hasActionBar() && Display.getInstance().getCommandBehavior() == Display.COMMAND_BEHAVIOR_NATIVE;
    }

    public void refreshNativeTitle(){
        Form f = getCurrentForm();
        if (f != null && isNativeTitle() &&  !(f instanceof Dialog)) {
            activity.runOnUiThread(new SetCurrentFormImpl(activity, f));
        }
    }
    
    public void setCurrentForm(final Form f) {
        super.setCurrentForm(f);
        if (isNativeTitle() &&  !(f instanceof Dialog)) {
            activity.runOnUiThread(new SetCurrentFormImpl(activity, f));
        }
    }

    @Override
    public void setNativeCommands(Vector commands) {
        refreshNativeTitle();
    }
    
    @Override
    public boolean isScreenLockSupported() {
        return true;
    }
    
    @Override
    public void lockScreen(){
        ((CodenameOneActivity)activity).lockScreen();
    }
    
    @Override
    public void unlockScreen(){
        ((CodenameOneActivity)activity).unlockScreen();
    }
    
    private static class SetCurrentFormImpl implements Runnable {
        private Activity activity;
        private Form f;
        
        public SetCurrentFormImpl(Activity activity, Form f) {
            this.activity = activity;
            this.f = f;
        }

        @Override
        public void run() {
            ActionBar ab = activity.getActionBar();
            String title = f.getTitle();
            boolean hasMenuBtn = false;
            if(android.os.Build.VERSION.SDK_INT >= 14){
                try {
                    ViewConfiguration vc = ViewConfiguration.get(activity);
                    Method m = vc.getClass().getMethod("hasPermanentMenuKey", (Class[])null);
                    hasMenuBtn = ((Boolean)m.invoke(vc, (Object[])null)).booleanValue();
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
            if((title != null && title.length() > 0) || (f.getCommandCount() > 0 && !hasMenuBtn)){
                activity.runOnUiThread(new NotifyActionBar(activity, true));
            }else{
                activity.runOnUiThread(new NotifyActionBar(activity, false));
                return;
            }
            
            ab.setTitle(title);
            ab.setDisplayHomeAsUpEnabled(f.getBackCommand() != null);
            if(android.os.Build.VERSION.SDK_INT >= 14){
                Image icon = f.getTitleComponent().getIcon();
                try {
                    if(icon != null){
                        ab.getClass().getMethod("setIcon", Drawable.class).invoke(ab, new BitmapDrawable(activity.getResources(), (Bitmap)icon.getImage()));
                    }else{
                        if(activity.getApplicationInfo().icon != 0){
                            ab.getClass().getMethod("setIcon", Integer.TYPE).invoke(ab, activity.getApplicationInfo().icon);
                        }
                    }
                    activity.runOnUiThread(new InvalidateOptionsMenuImpl(activity));
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        
    }
    
    private Purchase pur;
    
    @Override
    public Purchase getInAppPurchase() {
        try {
            pur = ZoozPurchase.class.newInstance();
            return pur;
        } catch(Throwable t) {
            return super.getInAppPurchase();
        }
    }

    @Override
    public boolean isTimeoutSupported() {
        return true;
    }

    @Override
    public void setTimeout(int t) {
        timeout = t;
    }

    @Override
    public CodeScanner getCodeScanner() {
        if(scannerInstance == null) {
            scannerInstance = new CodeScannerImpl();
        }
        return scannerInstance;
    }

    public void addCookie(Cookie c, boolean addToWebViewCookieManager, boolean sync) {
        if(addToWebViewCookieManager) {
            CookieManager mgr;
            CookieSyncManager syncer;
            try {
                syncer = CookieSyncManager.getInstance();
                mgr = CookieManager.getInstance();
            } catch(IllegalStateException ex) {
                syncer = CookieSyncManager.createInstance(this.getContext());
                mgr = CookieManager.getInstance();
            }
            String cookieString = c.getName()+"="+c.getValue()+
                    "; Domain="+c.getDomain()+
                    "; Path="+c.getPath()+
                    "; "+(c.isSecure()?"Secure;":"")
                    +(c.isHttpOnly()?"httpOnly;":"");
            mgr.setCookie("http"+
                    (c.isSecure()?"s":"")+"://"+
                    c.getDomain()+
                    c.getPath(), cookieString);
            if(sync) {
                syncer.sync();
            }
        }
        super.addCookie(c);
            
        
        
    }

    @Override
    public void addCookie(Cookie c) {
        if(isUseNativeCookieStore()) {
            this.addCookie(c, true, true);
        } else {
            super.addCookie(c);
        }
    }
    
    

    @Override
    public void addCookie(Cookie[] cookiesArray) {
        if(isUseNativeCookieStore()) {
            this.addCookie(cookiesArray, true);
        } else {
            super.addCookie(cookiesArray);
        }
    }
    
    public void addCookie(Cookie[] cookiesArray, boolean addToWebViewCookieManager){
        int len = cookiesArray.length;
        for ( int i=0; i< len; i++){
            this.addCookie(cookiesArray[i], addToWebViewCookieManager, false);
        }
        if(addToWebViewCookieManager) {
            try {
                CookieSyncManager.getInstance().sync();
            } catch (IllegalStateException ex) {
                CookieSyncManager.createInstance(getContext()).sync();
            }
        }
    }
    
    
    
    class CodeScannerImpl extends CodeScanner implements IntentResultListener {
        private ScanResult callback;
        
        @Override
        public void scanQRCode(ScanResult callback) {
            if (activity instanceof CodenameOneActivity) {
                ((CodenameOneActivity) activity).setIntentResultListener(this);
            }
            this.callback = callback;
            IntentIntegrator in = new IntentIntegrator(activity);
            if(!in.initiateScan(IntentIntegrator.QR_CODE_TYPES, "QR_CODE_MODE")){
                // restore old activity handling
                 Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            if(CodeScannerImpl.this != null && CodeScannerImpl.this.callback != null) {
                                CodeScannerImpl.this.callback.scanError(-1, "no scan app");
                                CodeScannerImpl.this.callback = null;
                            }
                        }
                    });
                 
                if (activity instanceof CodenameOneActivity) {
                    ((CodenameOneActivity) activity).restoreIntentResultListener();
                }
            }
        }

        @Override
        public void scanBarCode(ScanResult callback) {
            if (activity instanceof CodenameOneActivity) {
                ((CodenameOneActivity) activity).setIntentResultListener(this);
            }
            this.callback = callback;
            IntentIntegrator in = new IntentIntegrator(activity);
            Collection<String> types = IntentIntegrator.PRODUCT_CODE_TYPES;
            if(Display.getInstance().getProperty("scanAllCodeTypes", "false").equals("true")) {
                types = IntentIntegrator.ALL_CODE_TYPES;
            } 
            if(!in.initiateScan(types, "ONE_D_MODE")){
                // restore old activity handling
                 Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            CodeScannerImpl.this.callback.scanError(-1, "no scan app");
                            CodeScannerImpl.this.callback = null;
                        }
                    });

                if (activity instanceof CodenameOneActivity) {
                    ((CodenameOneActivity) activity).restoreIntentResultListener();
                }
            }
        }

        public void onActivityResult(int requestCode, final int resultCode, Intent data) {
            if (requestCode == IntentIntegrator.REQUEST_CODE && callback != null) {
                final ScanResult sr = callback;
                if (resultCode == Activity.RESULT_OK) {
                    final String contents = data.getStringExtra("SCAN_RESULT");
                    final String formatName = data.getStringExtra("SCAN_RESULT_FORMAT");
                    final byte[] rawBytes = data.getByteArrayExtra("SCAN_RESULT_BYTES");
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            sr.scanCompleted(contents, formatName, rawBytes);
                        }
                    });
                } else if(resultCode == Activity.RESULT_CANCELED) {
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            sr.scanCanceled();
                        }
                    });
                
                } else {
                    Display.getInstance().callSerially(new Runnable() {
                        @Override
                        public void run() {
                            sr.scanError(resultCode, null);
                        }
                    });
                }
                callback = null;
            }
            
            // restore old activity handling
            if (activity instanceof CodenameOneActivity) {
                ((CodenameOneActivity) activity).restoreIntentResultListener();
            }
        }
    }
    
    public boolean hasCamera() {
        try {
            int numCameras = Camera.getNumberOfCameras();
            return numCameras > 0;
        } catch(Throwable t) {
            return true;
        }
    }

    public String getCurrentAccessPoint() {

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return null;
        }
        String apName = info.getTypeName() + "_" + info.getSubtypeName();
        if (info.getExtraInfo() != null) {
            apName += "_" + info.getExtraInfo();
        }
        return apName;
    }

    /**
     * @inheritDoc
     */
    public String[] getAPIds() {
        if (apIds == null) {
            apIds = new HashMap();
            NetworkInfo[] aps = ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworkInfo();
            for (int i = 0; i < aps.length; i++) {
                String apName = aps[i].getTypeName() + "_" + aps[i].getSubtypeName();
                if (aps[i].getExtraInfo() != null) {
                    apName += "_" + aps[i].getExtraInfo();
                }
                apIds.put(apName, aps[i]);
            }
        }
        if (apIds.isEmpty()) {
            return null;
        }
        String[] ret = new String[apIds.size()];
        Iterator iter = apIds.keySet().iterator();
        for (int i = 0; iter.hasNext(); i++) {
            ret[i] = iter.next().toString();
        }
        return ret;

    }

    /**
     * @inheritDoc
     */
    public int getAPType(String id) {
        if (apIds == null) {
            getAPIds();
        }
        NetworkInfo info = (NetworkInfo) apIds.get(id);
        if (info == null) {
            return NetworkManager.ACCESS_POINT_TYPE_UNKNOWN;
        }
        int type = info.getType();
        int subType = info.getSubtype();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return NetworkManager.ACCESS_POINT_TYPE_WLAN;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 400-7000 kbps
            /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK3G; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return NetworkManager.ACCESS_POINT_TYPE_NETWORK2G;
            }
        } else {
            return NetworkManager.ACCESS_POINT_TYPE_UNKNOWN;
        }
    }
   
    /**
     * @inheritDoc
     */
    public void setCurrentAccessPoint(String id) {

        if (apIds == null) {
            getAPIds();
        }
        NetworkInfo info = (NetworkInfo) apIds.get(id);
        if (info == null || info.isConnectedOrConnecting()) {
            return;

        }
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.setNetworkPreference(info.getType());
    }
    
    private void scanMedia(File file) {
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        activity.sendBroadcast(scanFileIntent);
    }
    
    /**
     * Gets the last image id from the media store
     *
     * @return
     */
    private String getLastImageId() {
        int idVal = 0;;
        final String[] imageColumns = {MediaStore.Images.Media._ID};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        final String imageWhere = null;
        final String[] imageArguments = null;
        Cursor imageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
        if (imageCursor.moveToFirst()) {
            int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            imageCursor.close();
            idVal = id;
        } 
        return "" + idVal;
    }
    
    private void clearMediaDB(String lastId, String capturePath) {
        final String[] imageColumns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.SIZE, MediaStore.Images.Media._ID};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        final String imageWhere = MediaStore.Images.Media._ID + ">?";
        final String[] imageArguments = {lastId};
        Cursor imageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
        if (imageCursor.getCount() > 1) {
            while (imageCursor.moveToNext()) {
                int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                String path = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Long takenTimeStamp = imageCursor.getLong(imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                Long size = imageCursor.getLong(imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (path.contentEquals(capturePath)) {
                    // Remove it
                    ContentResolver cr = activity.getContentResolver();
                    cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=?", new String[]{Long.toString(id)});
                    break;
                }
            }
        }
        imageCursor.close();
    }
    
    
    @Override
    public boolean isNativePickerTypeSupported(int pickerType) {
        return pickerType == Display.PICKER_TYPE_DATE || pickerType == Display.PICKER_TYPE_TIME || pickerType == Display.PICKER_TYPE_STRINGS;
    }
    
    @Override
    public Object showNativePicker(final int type, final Component source, final Object currentValue, final Object data) {
        if(type == Display.PICKER_TYPE_TIME) {
            class TimePick implements TimePickerDialog.OnTimeSetListener, TimePickerDialog.OnCancelListener, Runnable {
                int result = ((Integer)currentValue).intValue();
                boolean dismissed;
                boolean canceled;
                public void onTimeSet(TimePicker tp, int hour, int minute) {
                    result = hour * 60 + minute;
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }
                }
                
                public void run() {
                    while(!dismissed) {
                        synchronized(this) {
                            try {
                                wait(50);
                            } catch(InterruptedException er) {}
                        }
                    }
                }

                @Override
                public void onCancel(DialogInterface di) {
                    canceled = true;
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }
                }
            }
            final TimePick pickInstance = new TimePick();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    int hour = ((Integer)currentValue).intValue() / 60;
                    int minute = ((Integer)currentValue).intValue() % 60;
                    TimePickerDialog tp = new TimePickerDialog(activity, pickInstance, hour, minute, true);
                    tp.setOnCancelListener(pickInstance);
                        //DateFormat.is24HourFormat(activity));
                    tp.show();
                }
            });
            Display.getInstance().invokeAndBlock(pickInstance);
            if(pickInstance.canceled) {
                return null;
            }
            return new Integer(pickInstance.result);
        }
        if(type == Display.PICKER_TYPE_DATE) {
            final java.util.Calendar cl = java.util.Calendar.getInstance();
            cl.setTime((Date)currentValue);
            class DatePick implements DatePickerDialog.OnDateSetListener,DatePickerDialog.OnCancelListener, Runnable {
                Date result = (Date)currentValue;
                boolean dismissed;
                
                public void onDateSet(DatePicker dp, int year, int month, int day) {
                    java.util.Calendar c = java.util.Calendar.getInstance();
                    c.set(java.util.Calendar.YEAR, year);
                    c.set(java.util.Calendar.MONTH, month);
                    c.set(java.util.Calendar.DAY_OF_MONTH, day);
                    result = c.getTime();
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }                    
                }
                
                public void run() {
                    while(!dismissed) {
                        synchronized(this) {
                            try {
                                wait(50);
                            } catch(InterruptedException er) {}
                        }
                    }
                }

                public void onCancel(DialogInterface di) {
                    result = null;
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }
                }
            }
            final DatePick pickInstance = new DatePick();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    DatePickerDialog tp = new DatePickerDialog(activity, pickInstance, cl.get(java.util.Calendar.YEAR), cl.get(java.util.Calendar.MONTH), cl.get(java.util.Calendar.DAY_OF_MONTH));
                    tp.setOnCancelListener(pickInstance);
                    tp.show();
                }
            });
            Display.getInstance().invokeAndBlock(pickInstance);
            return pickInstance.result;
        }
        if(type == Display.PICKER_TYPE_STRINGS) {
            final String[] values = (String[])data;
            class StringPick implements Runnable, NumberPicker.OnValueChangeListener {
                int result = -1;
                boolean dismissed;
                boolean canceled;
                
                StringPick() {
                }
                
                public void run() {
                    while(!dismissed) {
                        synchronized(this) {
                            try {
                                wait(50);
                            } catch(InterruptedException er) {}
                        }
                    }
                }

                public void cancel() {
                    canceled = true;
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }
                }

                public void ok() {
                    canceled = false;
                    dismissed = true;
                    synchronized(this) {
                        notify();
                    }
                }

                @Override
                public void onValueChange(NumberPicker np, int oldVal, int newVal) {
                    result = newVal;
                }
            }
            
            final StringPick pickInstance = new StringPick();
            for(int iter = 0 ; iter < values.length ; iter++) {
                if(values[iter].equals(currentValue)) {
                    pickInstance.result = iter;
                    break;
                }
            }

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    NumberPicker picker = new NumberPicker(activity);
                    picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    picker.setMinValue(0);
                    picker.setMaxValue(values.length - 1);
                    picker.setDisplayedValues(values);
                    picker.setOnValueChangedListener(pickInstance);
                    if(pickInstance.result > -1) {
                        picker.setValue(pickInstance.result);
                    }
                    RelativeLayout linearLayout = new RelativeLayout(activity);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
                    RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                    linearLayout.setLayoutParams(params);
                    linearLayout.addView(picker,numPicerParams);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    alertDialogBuilder.setView(linearLayout);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            pickInstance.ok();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                            pickInstance.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
            Display.getInstance().invokeAndBlock(pickInstance);
            if(pickInstance.canceled) {
                return null;
            }
            if(pickInstance.result < 0) {
                return null;
            }
            return values[pickInstance.result];
        }
        return null;
    }

    class SocketImpl {
        java.net.Socket socketInstance;
        int errorCode = -1;
        String errorMessage = null;
        InputStream is;
        OutputStream os;

        public boolean connect(String param, int param1) {
            try {
                socketInstance = new java.net.Socket(param, param1);
                return true;
            } catch(Exception err) {
                err.printStackTrace();
                errorMessage = err.toString();
                return false;
            }
        }

        private InputStream getInput() throws IOException {
            if(is == null) {
                if(socketInstance != null) {
                    is = socketInstance.getInputStream();
                } else {

                }
            }
            return is;
        }

        private OutputStream getOutput() throws IOException {
            if(os == null) {
                os = socketInstance.getOutputStream();
            }
            return os;
        }

        public int getAvailableInput() {
            try {
                return getInput().available();
            } catch(IOException err) {
                errorMessage = err.toString();
                err.printStackTrace();
            }
            return 0;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getIP() {
            try {
                return java.net.InetAddress.getLocalHost().getHostAddress();
            } catch(Throwable t) {
                t.printStackTrace();
                errorMessage = t.toString();
                return t.getMessage();
            }
        }

        public byte[] readFromStream() {
            try {
                int av = getAvailableInput();
                if(av > 0) {
                    byte[] arr = new byte[av];
                    int size = getInput().read(arr);
                    if(size == arr.length) {
                        return arr;
                    }
                    return shrink(arr, size);
                }
                byte[] arr = new byte[8192];
                int size = getInput().read(arr);
                if(size == arr.length) {
                    return arr;
                }
                return shrink(arr, size);
            } catch(IOException err) {
                err.printStackTrace();
                errorMessage = err.toString();
                return null;
            }
        }

        private byte[] shrink(byte[] arr, int size) {
            byte[] n = new byte[size];
            System.arraycopy(arr, 0, n, 0, size);
            return n;
        }

        public void writeToStream(byte[] param) {
            try {
                OutputStream os = getOutput();
                os.write(param);
                os.flush();
            } catch(IOException err) {
                errorMessage = err.toString();
                err.printStackTrace();
            }
        }

        public void disconnect() {
            try {
                if(socketInstance != null) {
                    if(is != null) {
                        try {
                            is.close();
                        } catch(IOException err) {}
                    }
                    if(os != null) {
                        try {
                            os.close();
                        } catch(IOException err) {}
                    }
                    socketInstance.close();
                    socketInstance = null;
                }
            } catch(IOException err) {
                errorMessage = err.toString();
                err.printStackTrace();
            }
        }

        public Object listen(int param) {
            try {
                ServerSocket serverSocketInstance = new ServerSocket(param);
                socketInstance = serverSocketInstance.accept();
                return socketInstance;
            } catch(Exception err) {
                errorMessage = err.toString();
                err.printStackTrace();
                return null;
            }
        }

        public boolean isConnected() {
            return socketInstance != null;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }
    
    @Override
    public Object connectSocket(String host, int port) {
        SocketImpl i = new SocketImpl();
        if(i.connect(host, port)) {
            return i;
        }
        return null;
    }
    
    @Override
    public Object listenSocket(int port) {
        return new SocketImpl().listen(port);
    }
    
    @Override
    public String getHostOrIP() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch(Throwable t) {
            t.printStackTrace();
            return t.getMessage();
        }
    }

    @Override
    public void disconnectSocket(Object socket) {
        ((SocketImpl)socket).disconnect();
    }    
    
    @Override
    public boolean isSocketConnected(Object socket) {
        return ((SocketImpl)socket).isConnected();
    }
    
    @Override
    public boolean isServerSocketAvailable() {
        return true;
    }

    @Override
    public boolean isSocketAvailable() {
        return true;
    }
    
    @Override
    public String getSocketErrorMessage(Object socket) {
        return ((SocketImpl)socket).getErrorMessage();
    }
    
    @Override
    public int getSocketErrorCode(Object socket) {
        return ((SocketImpl)socket).getErrorCode();
    }
    
    @Override
    public int getSocketAvailableInput(Object socket) {
        return ((SocketImpl)socket).getAvailableInput();
    }
    
    @Override
    public byte[] readFromSocketStream(Object socket) {
        return ((SocketImpl)socket).readFromStream();
    }
    
    @Override
    public void writeToSocketStream(Object socket, byte[] data) {
        ((SocketImpl)socket).writeToStream(data);
    }
}
