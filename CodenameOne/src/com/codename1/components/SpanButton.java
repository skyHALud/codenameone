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
package com.codename1.components;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;

/**
 * A complex button similar to MultiButton that breaks lines automatically and looks like a regular button (more or less).
 * Unlike the multi button the span button has the UIID style of a button.
 *
 * @author Shai Almog
 */
class SpanButton extends Container {
    private Button actualButton;
    private TextArea text;
    
    /**
     * Default constructor will be useful when adding this to the GUI builder
     */
    public SpanButton() {
        this("");
    }
    
    
    /**
     * Constructor accepting default text
     */
    public SpanButton(String txt) {
        setUIID("Button");
        setLayout(new BorderLayout());
        text = new TextArea(txt);
        text.setUIID("Label");
        text.setEditable(false);
        text.setFocusable(false);
        actualButton = new Button();
        actualButton.setUIID("Icon");
        addComponent(BorderLayout.WEST, actualButton);
        addComponent(BorderLayout.CENTER, text);
        setLeadComponent(actualButton);
    }
    
    /**
     * Set the text of the button
     * @param t text of the button
     */
    public void setText(String t) {
        text.setText(t);
    }

    /**
     * Sets the icon for the button
     * @param i the icon
     */
    public void setIcon(Image i) {
        actualButton.setIcon(i);
    }
    
    /**
     * Returns the text of the button
     * @return the text
     */
    public String getText() {
        return text.getText();
    }
    
    /**
     * Returns the image of the icon
     * @return the icon
     */
    public Image getIcon() {
        return actualButton.getIcon();
    }
    
    /**
     * Binds an action listener to button events
     * 
     * @param l the listener
     */
    public void addActionListener(ActionListener l) {
        actualButton.addActionListener(l);
    }

    /**
     * Removes the listener from tracking button events
     * @param l the listener
     */
    public void removeActionListener(ActionListener l) {
        actualButton.removeActionListener(l);
    }
}
