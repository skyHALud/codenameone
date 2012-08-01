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
package com.codename1.demos.kitchen;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Shai Almog
 */
public class Camera extends Demo {

    public String getDisplayName() {
        return "Camera";
    }

    public Image getDemoIcon() {
        return getResources().getImage("f-spot.png");
    }

    public Container createDemo() {
        final ComponentGroup cnt = new ComponentGroup();
        final Button capture = new Button("Capture");
        cnt.addComponent(capture);
        capture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Capture.capturePhoto(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if(evt == null){
                                System.out.println("user cancelled");
                                return;
                            }
                            
                            String path = (String) evt.getSource();                            
                            // we are opening the image with the file handle since the image
                            // is large this method can scale it down dynamically to a manageable
                            // size that doesn't exceed the heap
                            Image i = Image.createImage(path);
                            Label image = new Label(i.scaledWidth(Display.getInstance().getDisplayWidth() / 2));
                            if(cnt.getComponentCount() > 1) {
                                cnt.removeComponent(cnt.getComponentAt(1));
                            }
                            cnt.addComponent(image);
                            cnt.getComponentForm().revalidate();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }                        
                    }
                });
            }
        });
        return cnt;
    }
    
}
