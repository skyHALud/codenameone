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
package com.codename1.designer;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Quick and dirty demo of the main components within the framework allowing 
 * designers to customize the UI of the application even when no GUI is present.
 *
 * @author Shai Almog
 */
public class LiveDemo {
    public void init(Object context) {
    }
    
    public void start() {
        Form previewForm = new Form("Preview Theme");
        previewForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        previewForm.addComponent(new Label("This is a Label"));
        previewForm.addComponent(new Button("This is a Button"));
        previewForm.addComponent(new CheckBox("This is a CheckBox"));
        previewForm.addComponent(new RadioButton("This is a Radio Button"));
        previewForm.addComponent(new CheckBox("This is a CheckBox"));
        previewForm.show();
    }

    public void stop() {
    }
    
    public void destroy() {
    }
}
