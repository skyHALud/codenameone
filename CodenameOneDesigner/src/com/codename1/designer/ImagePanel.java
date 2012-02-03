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

package com.codename1.designer;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Panel containing a CodenameOne image that provides a photoshop-like square background
 * effect. 
 *
 * @author Shai Almog
 */
class ImagePanel extends JPanel {

    public void paintComponent(Graphics g) {
        g.setColor(new Color(CheckerBoardColorCalibration.getColorA()));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(CheckerBoardColorCalibration.getColorB()));
        int width = getWidth();
        int height = getHeight();
        for (int x = 0; x < width; x += 8) {
            if (x % 16 == 0) {
                for (int y = 0; y < height; y += 16) {
                    g.fillRect(x, y, 8, 8);
                }
            } else {
                for (int y = 8; y < height; y += 16) {
                    g.fillRect(x, y, 8, 8);
                }
            }
        }
        super.paintComponent(g);
    }
}
