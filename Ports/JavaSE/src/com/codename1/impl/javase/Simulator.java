/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
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
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores
 * CA 94065 USA or visit www.oracle.com if you need additional information or
 * have any questions.
 */
package com.codename1.impl.javase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple class that can invoke a lifecycle object to allow it to run a Codename One application.
 * Classes are loaded with a classloader so the UI skin can be updated and the lifecycle 
 * objects reloaded.
 *
 * @author Shai Almog
 */
public class Simulator {

    /**
     * Accepts the classname to launch
     */
    public static void main(final String[] argv) throws Exception {
        String skin = System.getProperty("dskin");
        if (skin == null) {
            System.setProperty("dskin", "/iphone3gs.skin");
        }
        StringTokenizer t = new StringTokenizer(System.getProperty("java.class.path"), File.pathSeparator);
        System.setProperty("MainClass", argv[0]);
        File[] files = new File[t.countTokens()];
        for (int iter = 0; iter < files.length; iter++) {
            files[iter] = new File(t.nextToken());
        }
        ClassLoader ldr = new ClassPathLoader(files);
        Class c = Class.forName("com.codename1.impl.javase.Executor", true, ldr);
        Method m = c.getDeclaredMethod("main", String[].class);
        m.invoke(null, new Object[]{argv});        
        
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    String r = System.getProperty("reload.simulator");
                    if(r != null && r.equals("true")) {
                        System.setProperty("reload.simulator", "");
                        try {
                            main(argv);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return;
                    }
                }
            }
        }.start();
    }
}

class ClassPathLoader extends ClassLoader {
    private File[] classpath;
    private Map classes = new HashMap(); 

    public ClassPathLoader(File[] classpath) {
        super(ClassPathLoader.class.getClassLoader()); 
        this.classpath = classpath;
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    public Class findClass(String className) throws ClassNotFoundException {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); //checks in cached classes
        if (result != null) {
            return result;
        }

        try {
            for(File f : classpath) {
                InputStream is;
                if(f.isDirectory()) {
                    File current = new File(f, className.replace('.', File.separatorChar) + ".class");
                    if(!current.exists()) {
                        continue;
                    }
                    is = new FileInputStream(current);
                } else {
                    try {
                        JarFile jar = new JarFile(f);
                        JarEntry entry = jar.getJarEntry(className.replace('.', '/') + ".class");
                        if(entry == null) {
                            continue;
                        }
                        is = jar.getInputStream(entry);
                        if(is == null) {
                            continue;
                        }
                    } catch(Throwable t) {
                        continue;
                    }
                }
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                int nextValue = is.read();
                while (-1 != nextValue) {
                    byteStream.write(nextValue);
                    nextValue = is.read();
                }
                is.close();
                classByte = byteStream.toByteArray();
                result = defineClass(className, classByte, 0, classByte.length, null);
                classes.put(className, result);
                return result;
            }
        } catch (Exception e) {
        }
        return findSystemClass(className);
    }

}
