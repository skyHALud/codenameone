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
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.animations.Animation;
import com.codename1.ui.animations.Motion;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;

/**
 * The on/off switch is a checkbox of sort (although it derives container) that represents its state as 
 * a switch each of which has a short label associated with it.
 * It has two types: Android and iOS. The types differ in the way that they are rendered.
 * The Android type (the default) is just a button with a label that can be moved/dragged between
 * the two states. The iOS version is more elaborate due to the look of that platform. 
 *
 * @author Shai Almog
 */
public class OnOffSwitch extends Container {
    private String on = "On";
    private String off = "Off";
    private boolean iosMode;
    private boolean value;
    private Button button;
    private boolean dragged;
    private boolean pressed;
    private int pressX;
    private int buttonWidth;
    private Image switchOnImage;
    private Image switchOffImage;
    private Image switchMaskImage;
    private int deltaX;
    
    /**
     * Default constructor
     */
    public OnOffSwitch() {
        setUIID("OnOffSwitch");
        initialize();
    }

    /**
     * @inheritDoc
     */
    protected Dimension calcPreferredSize() {
        if(iosMode) {
            return new Dimension(switchMaskImage.getWidth(), switchMaskImage.getHeight());
        }
        return super.calcPreferredSize();
    }
    
    private void initialize() {
        iosMode = UIManager.getInstance().isThemeConstant("onOffIOSModeBool", false);
        removeAll();
        setFocusable(true);
        if(iosMode) {
            button = null;
            on = on.toUpperCase();
            off = off.toUpperCase();
            switchMaskImage = UIManager.getInstance().getThemeImageConstant("switchMaskImage");
            switchOnImage = UIManager.getInstance().getThemeImageConstant("switchOnImage");
            switchOffImage = UIManager.getInstance().getThemeImageConstant("switchOffImage");
        } else {
            setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            button = new Button(off);
            buttonWidth = button.getPreferredW();
            button.setFocusable(false);
            updateButton();
            addComponent(button);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    flip();
                }
            });
        }
    }
    
    /**
     * @inheritDoc
     */
    protected boolean isStickyDrag() {
        return true;
    }
    
    /**
     * @inheritDoc
     */
    public void paint(Graphics g) {
        if(iosMode) {
            int switchButtonPadInt = UIManager.getInstance().getThemeConstant("switchButtonPadInt", 16);
            if(Display.getInstance().getDisplayWidth() > 480) {
                // is retina
                switchButtonPadInt *= 2;
            }
            Style s = getStyle();
            int x = getX() + s.getPadding(LEFT);
            int y = getY() + s.getPadding(TOP);
            if(!value) {
                if(deltaX > 0) {
                    dragged = false;
                } else {
                    if(deltaX < -switchOnImage.getWidth()) {
                        deltaX = -switchOnImage.getWidth();
                    }
                }
            } else {
                if(deltaX < 0) {
                    dragged = false;
                } else {
                    if(deltaX > switchOnImage.getWidth()) {
                        deltaX = switchOnImage.getWidth();
                    }
                }
            }
            if(dragged) {
                int onX;
                int offX;
                if(value) {
                    onX = x - deltaX + switchButtonPadInt;
                    offX = x - deltaX + switchOnImage.getWidth() - switchButtonPadInt;
                } else {
                    onX = x - deltaX - switchOnImage.getWidth() + switchButtonPadInt;
                    offX = x - deltaX - switchButtonPadInt;
                }
                switchButtonPadInt /= 2;
                g.drawImage(switchOnImage, onX, y);
                g.drawImage(switchOffImage, offX, y);
                int strWidth = s.getFont().stringWidth(on);
                int sX = onX + switchMaskImage.getWidth() / 2 - strWidth / 2 - switchButtonPadInt;
                int sY = y + switchMaskImage.getHeight() / 2 - s.getFont().getHeight() / 2;
                g.setColor(0xffffff);
                g.drawString(on, sX, sY, Style.TEXT_DECORATION_3D);
                strWidth = s.getFont().stringWidth(off);
                g.setColor(0x333333);
                sX = offX + switchMaskImage.getWidth() / 2 - strWidth / 2 + switchButtonPadInt;
                g.drawString(off, sX, sY);
            } else {
                String str;
                switchButtonPadInt /= 2;
                if(value) {
                    g.drawImage(switchOnImage, x, y);
                    str = on;
                    g.setColor(0xffffff);
                    switchButtonPadInt *= -1;
                } else {
                    g.drawImage(switchOffImage, x, y);
                    str = off;
                    g.setColor(0x333333);
                }
                int strWidth = s.getFont().stringWidth(str);
                int sX = x + switchMaskImage.getWidth() / 2 - strWidth / 2 + switchButtonPadInt;
                int sY = y + switchMaskImage.getHeight() / 2 - s.getFont().getHeight() / 2;
                g.drawString(str, sX, sY);
            }
            
            g.drawImage(switchMaskImage, x, y);
        } else {
            super.paint(g);
        }
    }
            
    private void updateButton() {
        if(value) {
            button.setText(on);
            getUnselectedStyle().setPadding(RIGHT, buttonWidth);
            getUnselectedStyle().setPadding(LEFT, 0);
        } else {
            button.setText(off);            
            getUnselectedStyle().setPadding(LEFT, buttonWidth);
            getUnselectedStyle().setPadding(RIGHT, 0);
        }
    }
    
    private void flip() {
        value = !value;
        if(iosMode) {
            repaint();
        } else {
            updateButton();
            animateLayout(150);
        }
    }
    
    /**
     * @inheritDoc
     */
    protected void initComponent() {
        super.initComponent();
    }

    /**
     * @inheritDoc
     */
    protected void deinitialize() {
        super.deinitialize();
    }    

    /**
     * @inheritDoc
     */
    public void pointerPressed(int x, int y) {
        super.pointerPressed(x, y);
        pressed = true;
        pressX = x;
    }
    
    /**
     * @inheritDoc
     */
    public void pointerDragged(int x, int y) {
        pressed = false;
        dragged = true;
        deltaX = pressX - x;
        if(!iosMode) {
            button.setText(on);
            int left = Math.max(0, buttonWidth - deltaX);
            int right = Math.min(buttonWidth, deltaX);
            if(deltaX < 0) {
                left = Math.min(buttonWidth, deltaX * -1);
                right = Math.max(0, buttonWidth + deltaX);
            }
            getUnselectedStyle().setPadding(RIGHT, right);
            getUnselectedStyle().setPadding(LEFT, left);
            if(right > left) {
                button.setText(on);
            } else {
                button.setText(off);
            }
            revalidate();
        }
    }

    private void animateTo(final boolean value, final int position) {
        int switchButtonPadInt = UIManager.getInstance().getThemeConstant("switchButtonPadInt", 16);
        if(Display.getInstance().getDisplayWidth() > 480) {
            // is retina
            switchButtonPadInt *= 2;
        }
        final Motion current = Motion.createEaseInOutMotion(Math.abs(position), switchMaskImage.getWidth() - switchButtonPadInt, 100);
        current.start();
        deltaX = position;
        getComponentForm().registerAnimated(new Animation() {
            public boolean animate() {
                deltaX = current.getValue();
                if(value) {
                    deltaX *= -1;
                }
                dragged = true;
                if(current.isFinished()) {
                    dragged = false;
                    Form f = getComponentForm();
                    if(f != null) {
                        f.deregisterAnimated(this);
                    }
                    OnOffSwitch.this.value = value;
                }
                repaint();
                return false;
            }

            public void paint(Graphics g) {
            }
        });
        dragged = true;
    }
    
    /**
     * @inheritDoc
     */
    public void pointerReleased(int x, int y) {
        if(iosMode) {
            if(dragged) {
                if(deltaX > 0) {
                    if(deltaX > switchMaskImage.getWidth() / 2) {
                        animateTo(false, deltaX);
                    } else {
                        animateTo(true, deltaX);
                    }
                } else {
                    if(deltaX * -1 > switchMaskImage.getWidth() / 2) {
                        animateTo(true, deltaX);
                    } else {
                        animateTo(false, deltaX);
                    }
                }
            } else {
                animateTo(!value, 0);
            }
            pressed = false;
            return;
        } else {
            if(!dragged) {
                flip();
            } else {
                int w = buttonWidth;
                deltaX = pressX - x;
                int left = Math.max(0, w - deltaX);
                int right = Math.min(w, deltaX);
                if(deltaX < 0) {
                    left = Math.min(buttonWidth, deltaX * -1);
                    right = Math.max(0, buttonWidth + deltaX);
                }
                if(right > left) {
                    value = true;
                } else {
                    value = false;
                }
                
                updateButton();
                animateLayout(150);
            }
        }        
        pressed = false;
        dragged = false;
    }
}
