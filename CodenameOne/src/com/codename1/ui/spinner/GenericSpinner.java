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
package com.codename1.ui.spinner;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.list.ListModel;

/**
 * A spinner class that allows arbitrary values, this is effectively a combo box replacement for platforms
 * where a combo box is not available
 *
 * @author Shai Almog
 */
public class GenericSpinner extends BaseSpinner {
    private Spinner spin;
    private ListModel model = new DefaultListModel(new Object[] {"Value 1", "Value 2", "Value 3"});
    private ListCellRenderer renderer = new DefaultListCellRenderer(false);
    
    /**
     * Default constructor
     */
    public GenericSpinner() {
        DefaultListCellRenderer render = (DefaultListCellRenderer) renderer;
        render.setRTL(false);
        render.setShowNumbers(false);
        render.setUIID("SpinnerRenderer");
    }
    
    /**
     * Default constructor
     */
    protected void initComponent() {
        super.initComponent();
        if(spin == null) {
            spin = createSpinner();
            setLayout(new BorderLayout());
            addComponent(BorderLayout.CENTER, spin);
        }
    }

    Spinner createSpinner() {
        Spinner spin = new Spinner(model, renderer);
        spin.setRenderingPrototype(null);
        spin.setShouldCalcPreferredSize(true);
        spin.setListSizeCalculationSampleCount(30);
        return spin;
    }

    
    /**
     * @inheritDoc
     */
    public String[] getPropertyNames() {
        return new String[] {"model", "renderer"};
    }

    /**
     * @inheritDoc
     */
    public Class[] getPropertyTypes() {
       return new Class[] {ListModel.class, ListCellRenderer.class};
    }

    /**
     * @inheritDoc
     */
    public Object getPropertyValue(String name) {
        if(name.equals("model")) {
            return getModel();
        }
        if(name.equals("renderer")) {
            return getRenderer();
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    public String setPropertyValue(String name, Object value) {
        if(name.equals("model")) {
            setModel((ListModel)value);
            return null;
        }
        if(name.equals("renderer")) {
            setRenderer((ListCellRenderer)value);
            return null;
        }
        return super.setPropertyValue(name, value);
    }

    /**
     * @return the model
     */
    public ListModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(ListModel model) {
        this.model = model;
        if(spin != null) {
            spin.setModel(model);
        }
    }

    /**
     * @return the renderer
     */
    public ListCellRenderer getRenderer() {
        return renderer;
    }

    /**
     * @param renderer the renderer to set
     */
    public void setRenderer(ListCellRenderer renderer) {
        this.renderer = renderer;
        if(spin != null) {
            spin.setRenderer(renderer);
        }
    }
}
