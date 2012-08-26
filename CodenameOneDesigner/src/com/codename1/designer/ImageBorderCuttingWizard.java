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

import com.codename1.ui.EncodedImage;
import com.codename1.ui.resource.util.ImageTools;
import com.codename1.ui.util.EditableResources;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Part of the image border wizard in the theme
 *
 * @author Shai Almog
 */
public class ImageBorderCuttingWizard extends javax.swing.JPanel {
    private EditableResources res;
    private String theme;
    private ImageBorderWizard wiz;
    private ImageBorderAppliesToWizard applies;

    /** Creates new form ImageBorderCuttingWizard */
    public ImageBorderCuttingWizard(EditableResources res, String theme, ImageBorderWizard wiz, ImageBorderAppliesToWizard applies) {
        this.res = res;
        this.theme = theme;
        this.wiz = wiz;
        this.applies = applies;
        initComponents();
        bottom.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
        left.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
        right.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
        top.setModel(new SpinnerNumberModel(5, 1, 1000, 1));
        zoom.setModel(new SpinnerNumberModel(1, 1, 10, 1));
        cropTop.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        cropBottom.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        cropLeft.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        cropRight.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        wiz.setWiz(imageLabel);
        multiImageComboActionPerformed(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        imageLabel = new ImageLabel();
        jLabel1 = new javax.swing.JLabel();
        zoom = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        top = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        bottom = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        left = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        right = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        cropTop = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        cropBottom = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        cropLeft = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        cropRight = new javax.swing.JSpinner();
        multiImageCombo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lowDPI = new javax.swing.JCheckBox();
        mediumDPI = new javax.swing.JCheckBox();
        highDPI = new javax.swing.JCheckBox();
        veryHighDPI = new javax.swing.JCheckBox();
        veryLowDPI = new javax.swing.JCheckBox();
        hdDPI = new javax.swing.JCheckBox();

        FormListener formListener = new FormListener();

        setOpaque(false);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        imageLabel.setName("imageLabel"); // NOI18N
        jPanel1.add(imageLabel);

        jScrollPane1.setViewportView(jPanel1);

        jLabel1.setText("Zoom");
        jLabel1.setName("jLabel1"); // NOI18N

        zoom.setName("zoom"); // NOI18N
        zoom.addChangeListener(formListener);

        jLabel2.setText("Top");
        jLabel2.setName("jLabel2"); // NOI18N

        top.setName("top"); // NOI18N
        top.addChangeListener(formListener);

        jLabel3.setText("Bottom");
        jLabel3.setName("jLabel3"); // NOI18N

        bottom.setName("bottom"); // NOI18N
        bottom.addChangeListener(formListener);

        jLabel4.setText("Left");
        jLabel4.setName("jLabel4"); // NOI18N

        left.setName("left"); // NOI18N
        left.addChangeListener(formListener);

        jLabel5.setText("Right");
        jLabel5.setName("jLabel5"); // NOI18N

        right.setName("right"); // NOI18N
        right.addChangeListener(formListener);

        jLabel6.setText("Crop Top");
        jLabel6.setName("jLabel6"); // NOI18N

        cropTop.setName("cropTop"); // NOI18N
        cropTop.addChangeListener(formListener);

        jLabel7.setText("Crop Bottom");
        jLabel7.setName("jLabel7"); // NOI18N

        cropBottom.setName("cropBottom"); // NOI18N
        cropBottom.addChangeListener(formListener);

        jLabel8.setText("Crop Left");
        jLabel8.setName("jLabel8"); // NOI18N

        cropLeft.setName("cropLeft"); // NOI18N
        cropLeft.addChangeListener(formListener);

        jLabel9.setText("Crop Right");
        jLabel9.setName("jLabel9"); // NOI18N

        cropRight.setName("cropRight"); // NOI18N
        cropRight.addChangeListener(formListener);

        multiImageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Generate RGB Image", "Generate Medium Resolution MultiImage", "Generate High Resolution MultiImage", "Generate Very High Resolution MultiImage", "Generate HD Resolution MultiImage" }));
        multiImageCombo.setSelectedIndex(2);
        multiImageCombo.setName("multiImageCombo"); // NOI18N
        multiImageCombo.addActionListener(formListener);

        jLabel10.setText("Geneate Multi Image");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText("Auto Scale To DPI");
        jLabel11.setName("jLabel11"); // NOI18N

        lowDPI.setSelected(true);
        lowDPI.setText("Low");
        lowDPI.setName("lowDPI"); // NOI18N

        mediumDPI.setSelected(true);
        mediumDPI.setText("Medium");
        mediumDPI.setName("mediumDPI"); // NOI18N

        highDPI.setText("High");
        highDPI.setName("highDPI"); // NOI18N

        veryHighDPI.setText("Very High");
        veryHighDPI.setName("veryHighDPI"); // NOI18N

        veryLowDPI.setText("Very Low");
        veryLowDPI.setName("veryLowDPI"); // NOI18N

        hdDPI.setText("HD");
        hdDPI.setName("hdDPI"); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2)
                            .add(jLabel3)
                            .add(jLabel4)
                            .add(jLabel5))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(right)
                            .add(left)
                            .add(bottom)
                            .add(top)
                            .add(zoom))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel6)
                            .add(jLabel7)
                            .add(jLabel8)
                            .add(jLabel9))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cropRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cropLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cropBottom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cropTop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(55, 55, 55)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel10)
                            .add(jLabel11))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(mediumDPI)
                            .add(highDPI)
                            .add(multiImageCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(veryLowDPI)
                                    .add(lowDPI))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(hdDPI)
                                    .add(veryHighDPI)))))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 208, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(zoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(multiImageCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(top, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(cropTop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11)
                    .add(veryLowDPI)
                    .add(veryHighDPI))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(bottom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(cropBottom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lowDPI)
                    .add(hdDPI))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(left, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8)
                    .add(cropLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(mediumDPI))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(right, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9)
                    .add(cropRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(highDPI))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.ChangeListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == multiImageCombo) {
                ImageBorderCuttingWizard.this.multiImageComboActionPerformed(evt);
            }
        }

        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            if (evt.getSource() == zoom) {
                ImageBorderCuttingWizard.this.zoomStateChanged(evt);
            }
            else if (evt.getSource() == top) {
                ImageBorderCuttingWizard.this.topStateChanged(evt);
            }
            else if (evt.getSource() == bottom) {
                ImageBorderCuttingWizard.this.bottomStateChanged(evt);
            }
            else if (evt.getSource() == left) {
                ImageBorderCuttingWizard.this.leftStateChanged(evt);
            }
            else if (evt.getSource() == right) {
                ImageBorderCuttingWizard.this.rightStateChanged(evt);
            }
            else if (evt.getSource() == cropTop) {
                ImageBorderCuttingWizard.this.cropTopStateChanged(evt);
            }
            else if (evt.getSource() == cropBottom) {
                ImageBorderCuttingWizard.this.cropBottomStateChanged(evt);
            }
            else if (evt.getSource() == cropLeft) {
                ImageBorderCuttingWizard.this.cropLeftStateChanged(evt);
            }
            else if (evt.getSource() == cropRight) {
                ImageBorderCuttingWizard.this.cropRightStateChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void zoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomStateChanged
        imageLabel.revalidate();
    }//GEN-LAST:event_zoomStateChanged

    private void topStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_topStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_topStateChanged

    private void bottomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bottomStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_bottomStateChanged

    private void leftStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_leftStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_leftStateChanged

    private void rightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rightStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_rightStateChanged

    private void cropTopStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cropTopStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_cropTopStateChanged

    private void cropBottomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cropBottomStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_cropBottomStateChanged

    private void cropLeftStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cropLeftStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_cropLeftStateChanged

    private void cropRightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cropRightStateChanged
        imageLabel.repaint();
    }//GEN-LAST:event_cropRightStateChanged

    private void enableChecks(boolean b) {
        lowDPI.setEnabled(b);
        mediumDPI.setEnabled(b);
        veryHighDPI.setEnabled(b);
        veryLowDPI.setEnabled(b);
        hdDPI.setEnabled(b);
        highDPI.setEnabled(b);
    }

    private void multiImageComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiImageComboActionPerformed
        switch(multiImageCombo.getSelectedIndex()) {
            // Generate RGB Image
            case 0:
                enableChecks(false);
                break;

            // Generate Medium Resolution MultiImage
            case 1:
                enableChecks(true);
                mediumDPI.setEnabled(false);
                mediumDPI.setSelected(false);
                break;

            // Generate High Resolution MultiImage
            case 2:
                enableChecks(true);
                highDPI.setEnabled(false);
                highDPI.setSelected(false);
                break;

            // Generate Very High Resolution MultiImage
            case 3:
                enableChecks(true);
                veryHighDPI.setEnabled(false);
                veryHighDPI.setSelected(false);
                break;

            // Generate HD Resolution MultiImage
            case 4:
                enableChecks(true);
                hdDPI.setEnabled(false);
                hdDPI.setSelected(false);
                break;
        }
    }//GEN-LAST:event_multiImageComboActionPerformed

    public void generate() {
        if(applies.getAppliesTo().getModel().getSize() == 0) {
            JOptionPane.showMessageDialog(this, "You haven't selected components to apply this border to!\nPlease go to the apply tab and ADD component types/styles",
                    "No Components Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BufferedImage img = wiz.getImage();
        BufferedImage buff = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg2d = buff.createGraphics();
        bg2d.drawImage(img.getSubimage(get(cropLeft), get(cropTop), img.getWidth() - get(cropLeft) - get(cropRight),
                    img.getHeight() - get(cropTop) - get(cropBottom)), get(cropLeft), get(cropTop), null);
        bg2d.dispose();
        img = buff;
        BufferedImage topLeft = img.getSubimage(0, 0, get(left), get(top));
        BufferedImage topRight = img.getSubimage(img.getWidth() - get(right), 0, get(right), get(top));
        BufferedImage bottomLeft = img.getSubimage(0, img.getHeight() - get(bottom), get(left), get(bottom));
        BufferedImage bottomRight = img.getSubimage(img.getWidth() - get(right), img.getHeight() - get(bottom), get(right), get(bottom));
        BufferedImage center = img.getSubimage(get(left), get(top), img.getWidth() - get(right) - get(left), img.getHeight() - get(bottom) - get(top));
        BufferedImage topImage = img.getSubimage(get(left), 0, img.getWidth() - get(left) - get(right), get(top));
        BufferedImage bottomImage = img.getSubimage(get(left), img.getHeight() - get(bottom), img.getWidth() - get(left) - get(right), get(bottom));
        BufferedImage leftImage = img.getSubimage(0, get(top), get(left), img.getHeight() - get(top) - get(bottom));
        BufferedImage rightImage = img.getSubimage(img.getWidth() - get(right), get(top), get(right), img.getHeight() - get(top) - get(bottom));

        // optimize the size of the center/top/left/bottom/right images which is a HUGE performance deterant
        if(center.getWidth() < 10 || center.getHeight() < 10) {
            center = ImageTools.getScaledInstance(center, Math.max(20, center.getWidth()), Math.max(20, center.getHeight()));
            topImage = ImageTools.getScaledInstance(topImage, Math.max(20, topImage.getWidth()), topImage.getHeight());
            leftImage = ImageTools.getScaledInstance(leftImage, leftImage.getWidth(), Math.max(20, leftImage.getHeight()));
            rightImage = ImageTools.getScaledInstance(rightImage, rightImage.getWidth(), Math.max(20, rightImage.getHeight()));
            bottomImage = ImageTools.getScaledInstance(bottomImage, Math.max(20, bottomImage.getWidth()), bottomImage.getHeight());
        }
        
        com.codename1.ui.EncodedImage topLeftCodenameOne = com.codename1.ui.EncodedImage.create(toPng(topLeft));
        com.codename1.ui.EncodedImage topRightCodenameOne = com.codename1.ui.EncodedImage.create(toPng(topRight));
        com.codename1.ui.EncodedImage bottomLeftCodenameOne = com.codename1.ui.EncodedImage.create(toPng(bottomLeft));
        com.codename1.ui.EncodedImage bottomRightCodenameOne = com.codename1.ui.EncodedImage.create(toPng(bottomRight));
        com.codename1.ui.EncodedImage centerCodenameOne = com.codename1.ui.EncodedImage.create(toPng(center));
        com.codename1.ui.EncodedImage topImageCodenameOne = com.codename1.ui.EncodedImage.create(toPng(topImage));
        com.codename1.ui.EncodedImage bottomImageCodenameOne = com.codename1.ui.EncodedImage.create(toPng(bottomImage));
        com.codename1.ui.EncodedImage leftImageCodenameOne = com.codename1.ui.EncodedImage.create(toPng(leftImage));
        com.codename1.ui.EncodedImage rightImageCodenameOne = com.codename1.ui.EncodedImage.create(toPng(rightImage));
        String prefix = (String)applies.getAppliesTo().getModel().getElementAt(0);
        topLeftCodenameOne = storeImage(topLeftCodenameOne, prefix +"TopL");
        topRightCodenameOne = storeImage(topRightCodenameOne, prefix +"TopR");
        bottomLeftCodenameOne = storeImage(bottomLeftCodenameOne, prefix +"BottomL");
        bottomRightCodenameOne = storeImage(bottomRightCodenameOne, prefix +"BottomR");
        centerCodenameOne = storeImage(centerCodenameOne, prefix + "Center");
        topImageCodenameOne = storeImage(topImageCodenameOne, prefix + "Top");
        bottomImageCodenameOne = storeImage(bottomImageCodenameOne, prefix + "Bottom");
        leftImageCodenameOne = storeImage(leftImageCodenameOne, prefix + "Left");
        rightImageCodenameOne = storeImage(rightImageCodenameOne, prefix + "Right");
        com.codename1.ui.plaf.Border b = com.codename1.ui.plaf.Border.createImageBorder(topImageCodenameOne, bottomImageCodenameOne, leftImageCodenameOne,
                rightImageCodenameOne, topLeftCodenameOne, topRightCodenameOne,
                bottomLeftCodenameOne, bottomRightCodenameOne, centerCodenameOne);
        Hashtable newTheme = new Hashtable(res.getTheme(theme));
        for(int i = 0 ; i < applies.getAppliesTo().getModel().getSize() ; i++) {
            newTheme.put(applies.getAppliesTo().getModel().getElementAt(i), b);
        }
        ((DefaultListModel)applies.getAppliesTo().getModel()).removeAllElements();
        res.setTheme(theme, newTheme);
    }

    private com.codename1.ui.EncodedImage storeImage(com.codename1.ui.EncodedImage img, String prefix) {
        int i = 1;
        while(res.containsResource(prefix + "_" + i + ".png")) {
            i++;
        }

        float ratioWidth = 0;
        int multiVal = 0;
        switch(multiImageCombo.getSelectedIndex()) {
            // Generate RGB Image
            case 0:
                res.setImage(prefix + "_" + i + ".png", img);
                return img;

            // Generate Medium Resolution MultiImage
            case 1:
                multiVal = com.codename1.ui.Display.DENSITY_MEDIUM;
                ratioWidth = 320;
                break;

            // Generate High Resolution MultiImage
            case 2:
                ratioWidth = 480;
                multiVal = com.codename1.ui.Display.DENSITY_HIGH;
                break;

            // Generate Very High Resolution MultiImage
            case 3:
                ratioWidth = 640;
                multiVal = com.codename1.ui.Display.DENSITY_VERY_HIGH;
                break;

            // Generate HD Resolution MultiImage
            case 4:
                ratioWidth = 1080;
                multiVal = com.codename1.ui.Display.DENSITY_HD;
                break;
        }
        EditableResources.MultiImage multi = new EditableResources.MultiImage();
        multi.setDpi(new int[] {multiVal});
        multi.setInternalImages(new com.codename1.ui.EncodedImage[] {img});
        if(lowDPI.isSelected()) {
            float ratio = 240.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_LOW, w, h, multi);
        }

        if(veryLowDPI.isSelected()) {
            float ratio = 176.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_VERY_LOW, w, h, multi);
        }

        if(mediumDPI.isSelected()) {
            float ratio = 320.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_MEDIUM, w, h, multi);
        }

        if(highDPI.isSelected()) {
            float ratio = 480.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_HIGH, w, h, multi);
        }

        if(veryHighDPI.isSelected()) {
            float ratio = 640.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_VERY_HIGH, w, h, multi);
        }

        if(hdDPI.isSelected()) {
            float ratio = 1080.0f / ratioWidth;
            int w = Math.max((int)(img.getWidth() * ratio), 1);
            int h = Math.max((int)(img.getHeight() * ratio), 1);
            multi = ImageMultiEditor.scaleMultiImage(multiVal, com.codename1.ui.Display.DENSITY_HD, w, h, multi);
        }

        res.setMultiImage(prefix + "_" + i + ".png", multi);
        return multi.getBest();
    }


    public static byte[] toPng(BufferedImage b) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ImageIO.write(b, "png", bo);
            bo.close();
            return bo.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner bottom;
    private javax.swing.JSpinner cropBottom;
    private javax.swing.JSpinner cropLeft;
    private javax.swing.JSpinner cropRight;
    private javax.swing.JSpinner cropTop;
    private javax.swing.JCheckBox hdDPI;
    private javax.swing.JCheckBox highDPI;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner left;
    private javax.swing.JCheckBox lowDPI;
    private javax.swing.JCheckBox mediumDPI;
    private javax.swing.JComboBox multiImageCombo;
    private javax.swing.JSpinner right;
    private javax.swing.JSpinner top;
    private javax.swing.JCheckBox veryHighDPI;
    private javax.swing.JCheckBox veryLowDPI;
    private javax.swing.JSpinner zoom;
    // End of variables declaration//GEN-END:variables

    private int get(JSpinner s) {
        return ((Number)s.getValue()).intValue();
    }

    class ImageLabel extends JLabel {
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            // prevent the clipping from applying to the lines
            Graphics2D another = (Graphics2D)g2d.create();

            g2d.scale(get(zoom), get(zoom));
            g2d.clipRect(get(cropLeft) + 10, get(cropTop) + 10, getWidth() / get(zoom) - get(cropLeft) - get(cropRight) - 20,
                    getHeight() / get(zoom) - get(cropTop) - get(cropBottom) - 20);
            BufferedImage img = wiz.getImage();
            g2d.drawImage(img, 10, 10, null);

            another.scale(get(zoom), get(zoom));
            another.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            another.drawLine(0, get(top) + 10, getWidth() / get(zoom), get(top) + 10);
            another.drawLine(0, getHeight() / get(zoom) - get(bottom) - 10, getWidth() / get(zoom), getHeight() / get(zoom) - get(bottom) - 10);
            another.drawLine(get(left) + 10, 0, get(left) + 10, getHeight() / get(zoom));
            another.drawLine(getWidth() / get(zoom) - get(right) - 10, 0, getWidth() / get(zoom) - get(right) - 10, getHeight() / get(zoom));
            another.dispose();
        }

        public Dimension getPreferredSize() {
            if(wiz == null) {
                return new Dimension(300, 300);
            }
            BufferedImage img = wiz.getImage();
            return new Dimension((20 + img.getWidth(this)) * get(zoom), (20 + img.getHeight(this)) * get(zoom));
        }
    }
}
