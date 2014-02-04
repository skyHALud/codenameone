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

import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.Motion;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;

/**
 * Shows a "Washing Machine" infinite progress indication animation
 *
 * @author Shai Almog
 */
public class InfiniteProgress extends Component {
    private Image animation;
    private int angle = 0;
    private int tick;
    
    /**
     * Default constructor to define the UIID
     */
    public InfiniteProgress() {
        setUIID("InfiniteProgress");
    }
    
    /**
     * Shows the infinite progress over the whole screen
     */
    public Dialog showInifiniteBlocking() {
        Form f = Display.getInstance().getCurrent();
        if(f == null) {
            f = new Form();
            f.show();
        }
        int i = f.getTintColor();
        f.setTintColor(0x90000000);
        Dialog d = new Dialog();
        d.setDialogUIID("Container");
        d.setLayout(new BorderLayout());
        d.addComponent(BorderLayout.CENTER, this);
        d.setTransitionInAnimator(CommonTransitions.createEmpty());
        d.setTransitionOutAnimator(CommonTransitions.createEmpty());
        d.showPacked(BorderLayout.CENTER, false);
        return d;
    }
    
    /**
     * @inheritDoc
     */
    protected void initComponent() {
        super.initComponent();
        if(animation == null) {
            animation = UIManager.getInstance().getThemeImageConstant("infiniteImage");
        }
        getComponentForm().registerAnimated(this);
    }

    /**
     * @inheritDoc
     */
    protected void deinitialize() {
        super.deinitialize();
        getComponentForm().deregisterAnimated(this);
    }
    
    /**
     * @inheritDoc
     */
    public boolean animate() {
        // reduce repaint thrushing of the UI from the infinite progress
        boolean val = super.animate() || tick % 4 == 0;
        tick++;
        return val;
    }
    
    /**
     * @inheritDoc
     */
    protected Dimension calcPreferredSize() {
        if(animation == null) {
            animation = UIManager.getInstance().getThemeImageConstant("infiniteImage");
        }
        if(animation == null) {
            return new Dimension(100, 100);
        }
        Style s = getStyle();
        return new Dimension(s.getPadding(LEFT) + s.getPadding(RIGHT) + animation.getWidth(), 
                s.getPadding(TOP) + s.getPadding(BOTTOM) + animation.getHeight());
    }
    
    /**
     * @inheritDoc
     */
    public void paint(Graphics g) {
        super.paint(g);
        if(animation == null) {
            return;
        }
        int v = angle+=16 % 360;
        Style s = getStyle();
        /*if(g.isAffineSupported()) {
            g.rotate(((float)v) / 57.2957795f, getAbsoluteX() + s.getPadding(LEFT) + getWidth() / 2, getAbsoluteY() + s.getPadding(TOP) + getHeight() / 2);
            g.drawImage(getAnimation(), getX() + s.getPadding(LEFT), getY() + s.getPadding(TOP));
            g.resetAffine();
        } else {*/
            g.drawImage(animation.rotate(v), getX() + s.getPadding(LEFT), getY() + s.getPadding(TOP));            
        //}
    }
    
    /**
     * @return the animation
     */
    public Image getAnimation() {
        return animation;
    }

    /**
     * @param animation the animation to set
     */
    public void setAnimation(Image animation) {
        this.animation = animation;
    }

    /**
     * @inheritDoc
     */
    public String[] getPropertyNames() {
        return new String[] {"animation"};
    }

    /**
     * @inheritDoc
     */
    public Class[] getPropertyTypes() {
       return new Class[] {Image.class};
    }

    /**
     * @inheritDoc
     */
    public Object getPropertyValue(String name) {
        if(name.equals("animation")) {
            return animation;
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    public String setPropertyValue(String name, Object value) {
        if(name.equals("animation")) {
            this.animation = (Image)value;
            return null;
        }
        return super.setPropertyValue(name, value);
    }
}
