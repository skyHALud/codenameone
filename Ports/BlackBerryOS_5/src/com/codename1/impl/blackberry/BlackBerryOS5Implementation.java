/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
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
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.codename1.impl.blackberry;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.PeerComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.rim.blackberry.api.invoke.CameraArguments;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.device.api.system.Branding;
import org.w3c.dom.Document;

// requires signing
import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldListener;
import net.rim.device.api.io.file.FileSystemJournal;
import net.rim.device.api.io.file.FileSystemJournalEntry;
import net.rim.device.api.io.file.FileSystemJournalListener;
import net.rim.device.api.script.Scriptable;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.EventInjector;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;

/**
 * Implementation class for newer blackberry devices
 *
 * @author Shai Almog, Thorsten Schemm
 */
public class BlackBerryOS5Implementation extends BlackBerryImplementation {

    BlackBerryCanvas createCanvas() {
        return new BlackBerryTouchSupport(this);
    }

    public void nativeEdit(final Component cmp, final int maxSize, final int constraint, String text, int keyCode) {
        BlackBerryVirtualKeyboard.blockFolding = true;
        super.nativeEdit(cmp, maxSize, constraint, text, keyCode);
    }

    protected void disableBlockFolding() {
        BlackBerryVirtualKeyboard.blockFolding = false;
    }

    public int getKeyboardType() {
        int keyT = Keypad.getHardwareLayout();
        switch (keyT) {
            case Keypad.HW_LAYOUT_TOUCHSCREEN_12:
            case Keypad.HW_LAYOUT_TOUCHSCREEN_24:
            case Keypad.HW_LAYOUT_TOUCHSCREEN_29:
                return Display.KEYBOARD_TYPE_VIRTUAL;
            default:
                return super.getKeyboardType();
        }
    }

    public String getProperty(String key, String defaultValue) {
        if ("User-Agent".equals(key)) {
            return "Blackberry" + DeviceInfo.getDeviceName() + "/" + DeviceInfo.getSoftwareVersion()
                    + " Profile/" + System.getProperty("microedition.profiles")
                    + " Configuration/"
                    + System.getProperty("microedition.configuration")
                    + " VendorID/" + Branding.getVendorId();
        }
        return super.getProperty(key, defaultValue);
    }

    public void copyToClipboard(Object obj) {
        if (obj instanceof String || obj instanceof StringBuffer) {
            net.rim.device.api.system.Clipboard.getClipboard().put(obj);
            super.copyToClipboard(null);
        } else {
            net.rim.device.api.system.Clipboard.getClipboard().put(null);
            super.copyToClipboard(obj);
        }
    }

    public Object getPasteDataFromClipboard() {
        Object o = net.rim.device.api.system.Clipboard.getClipboard().get();
        if (o != null) {
            return o;
        }
        return super.getPasteDataFromClipboard();
    }

    public boolean canForceOrientation() {
        return true;
    }

    public void lockOrientation(boolean portrait) {
        net.rim.device.api.ui.UiEngineInstance ue;
        ue = net.rim.device.api.ui.Ui.getUiEngineInstance();
        if (portrait) {
            ue.setAcceptableDirections(net.rim.device.api.system.Display.DIRECTION_PORTRAIT);
        } else {
            ue.setAcceptableDirections(net.rim.device.api.system.Display.DIRECTION_LANDSCAPE);
        }
    }

    public boolean isNativeBrowserComponentSupported() {
        return true;
    }

    public PeerComponent createBrowserComponent(Object browserComponent) {
        synchronized (UiApplication.getEventLock()) {
            BrowserField bff = new BrowserField();
            final BrowserComponent cmp = (BrowserComponent) browserComponent;
            bff.addListener(new BrowserFieldListener() {

                public void documentError(BrowserField browserField, Document document) throws Exception {
                    super.documentError(browserField, document);
                    cmp.fireWebEvent("onError", new ActionEvent(document));
                }

                public void documentLoaded(BrowserField browserField, Document document) throws Exception {
                    super.documentLoaded(browserField, document);
                    cmp.fireWebEvent("onLoad", new ActionEvent(document));
                }
            });
            return PeerComponent.create(bff);
        }
    }

    public void setBrowserProperty(PeerComponent browserPeer, String key, Object value) {
    }

    public String getBrowserTitle(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            return ((BrowserField) browserPeer.getNativePeer()).getDocumentTitle();
        }
    }

    public String getBrowserURL(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            return ((BrowserField) browserPeer.getNativePeer()).getDocumentUrl();
        }
    }

    public void setBrowserURL(PeerComponent browserPeer, String url) {
        if (url.startsWith("jar://")) {
            //ApplicationDescriptor ad = ApplicationDescriptor.currentApplicationDescriptor();
            //url = "cod://" + ad.getModuleName() +  url.substring(6);
            //super.setBrowserURL(browserPeer, url);
            //url = "local://" + url.substring(6);

            // load from jar:// URL's
            try {
                InputStream i = Display.getInstance().getResourceAsStream(getClass(), url.substring(6));
                if (i == null) {
                    System.out.println("Local resource not found: " + url);
                    return;
                }
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int size = i.read(buffer);
                while (size > -1) {
                    bo.write(buffer, 0, size);
                    size = i.read(buffer);
                }
                i.close();
                bo.close();
                String htmlText = new String(bo.toByteArray(), "UTF-8");
                int pos = url.lastIndexOf('/');
                if (pos > 6) {
                    url = url.substring(6, pos);
                } else {
                    url = "/";
                }
                String baseUrl = "local://" + url;
                setBrowserPage(browserPeer, htmlText, baseUrl);
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).requestContent(url);
        }
    }

    public void browserReload(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).refresh();
        }
    }

    public boolean browserHasBack(PeerComponent browserPeer) {
        return ((BrowserField) browserPeer.getNativePeer()).getHistory().canGoBack();
    }

    public boolean browserHasForward(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            return ((BrowserField) browserPeer.getNativePeer()).getHistory().canGoForward();
        }
    }

    public void browserBack(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).back();
        }
    }

    public void browserForward(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).forward();
        }
    }

    public void browserClearHistory(PeerComponent browserPeer) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).getHistory().clearHistory();
        }
    }

    public void setBrowserPage(PeerComponent browserPeer, String html, String baseUrl) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).displayContent(html, baseUrl);
        }
    }

    public void browserExecute(PeerComponent browserPeer, String javaScript) {
        synchronized (UiApplication.getEventLock()) {
            ((BrowserField) browserPeer.getNativePeer()).executeScript(javaScript);
        }
    }

    public void browserExposeInJavaScript(PeerComponent browserPeer, Object o, String name) {
        synchronized (UiApplication.getEventLock()) {
            try {
                ((BrowserField) browserPeer.getNativePeer()).extendScriptEngine(name, (Scriptable) o);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void captureVideo(ActionListener response) {
        this.camResponse = response;

        UiApplication.getUiApplication().addFileSystemJournalListener(new FileSystemJournalListener() {

            private long lastUSN;
            private String videoPath;

            public void fileJournalChanged() {
                // next sequence number file system will use
                long USN = FileSystemJournal.getNextUSN();

                for (long i = USN - 1; i >= lastUSN && i < USN; --i) {
                    FileSystemJournalEntry entry = FileSystemJournal.getEntry(i);
                    if (entry == null) {
                        break;
                    }

                    String path = entry.getPath();
                    if (entry.getEvent() == FileSystemJournalEntry.FILE_ADDED
                            && videoPath == null) {
                        int index = path.indexOf(".3GP");
                        if (index != -1) {
                            videoPath = path;
                        }
                    } else if (entry.getEvent() == FileSystemJournalEntry.FILE_RENAMED) {
                        if (path != null && path.equals(videoPath)) {
                            //close the camera
                            UiApplication.getUiApplication().removeFileSystemJournalListener(this);

                            try {
                                EventInjector.KeyEvent inject = new EventInjector.KeyEvent(EventInjector.KeyEvent.KEY_DOWN, Characters.ESCAPE, 0, 200);
                                inject.post();
                                inject.post();
                            } catch (Exception e) {
                                //try to close the camera
                            }

                            camResponse.actionPerformed(new ActionEvent("file://" + path));
                            camResponse = null;
                            videoPath = null;
                            break;
                        }
                    }
                }
                lastUSN = USN;
            }
        });
        synchronized (UiApplication.getEventLock()) {
            Invoke.invokeApplication(Invoke.APP_TYPE_CAMERA, new CameraArguments(CameraArguments.ARG_VIDEO_RECORDER));
        }
    }
}
