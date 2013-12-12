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
package com.codename1.ui.util.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A JAXB XML object for loading the resource file into RAM
 *
 * @author Shai Almog
 */
@XmlRootElement(name="resource")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceFileXML {
    @XmlAttribute
    private int majorVersion;

    @XmlAttribute
    private int minorVersion;
    
    @XmlElement
    private Theme[] theme;
    
    @XmlElement
    private Ui[] ui;

    @XmlElement
    private LegacyFont[] legacyFont;

    @XmlElement
    private Data[] data;

    @XmlElement
    private Image[] image;

    @XmlElement
    private L10n[] l10n;

    /**
     * @return the majorVersion
     */
    public int getMajorVersion() {
        return majorVersion;
    }

    /**
     * @return the minorVersion
     */
    public int getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return the theme
     */
    public Theme[] getTheme() {
        return theme;
    }

    /**
     * @return the ui
     */
    public Ui[] getUi() {
        return ui;
    }

    /**
     * @return the legacyFont
     */
    public LegacyFont[] getLegacyFont() {
        return legacyFont;
    }

    /**
     * @return the data
     */
    public Data[] getData() {
        return data;
    }

    /**
     * @return the image
     */
    public Image[] getImage() {
        return image;
    }

    /**
     * @return the l10n
     */
    public L10n[] getL10n() {
        return l10n;
    }
}
