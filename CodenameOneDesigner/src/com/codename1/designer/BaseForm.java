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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.PainterGlasspane;
import org.jdesktop.swingx.sort.RowFilters;

/**
 * Base class for forms containing common functionality for all forms
 *
 * @author Shai Almog
 */
public class BaseForm extends JPanel {
    private static ImageIcon overrideImage;

    public static boolean isRightClick(MouseEvent ev) {
        /*if(ResourceEditorApp.IS_MAC) {
            return ev.isPopupTrigger();
        }*/
        return SwingUtilities.isRightMouseButton(ev);
    }
    
    public BaseForm() {
        RootPaneContainer r = (RootPaneContainer)ResourceEditorApp.getApplication().getMainFrame();
        r.setGlassPane(new JLabel());
    }
    
    public synchronized void setOverrideMode(boolean overrideMode, java.awt.Component c) {
        RootPaneContainer r = (RootPaneContainer)SwingUtilities.windowForComponent(c);
        if(overrideMode) {
            if(overrideImage == null) {
                overrideImage = new ImageIcon(getClass().getResource("/override_stamp.png"));
            }
            PainterGlasspane pg = new PainterGlasspane();
            MattePainter matte = new MattePainter(new Color(0xcc,0xcc, 0xcc, 120)) {
                protected  void doPaint(java.awt.Graphics2D g, java.lang.Object component, int width, int height) {
                    super.doPaint(g, component, width, height);
                    overrideImage.paintIcon(BaseForm.this, g, 0, 0);
                    //g.drawImage(overrideImage.getImage(), width / 2 - overrideImage.getIconWidth() / 2, 0, BaseForm.this);
                }
            };
            pg.setPainter(matte);
            pg.addTarget(this);
            r.setGlassPane(pg);
            pg.setBounds(0, 0, r.getContentPane().getWidth(), r.getContentPane().getHeight());
            pg.setVisible(true);
        } else {
            r.setGlassPane(new JLabel());
        }
    }
            
    /**
     * Returns the selection in the JXTable mapped to the model since the table
     * might be sorted or filtered
     */
    protected int getModelSelection(JTable t) {
        EditorTable table = (EditorTable)t;
        /*int r = table.getSelectedRow();
        if(r != -1) {
            r = table.convertRow(r);
        }
        return r;*/
        if(table.getSelectedRow() < 0 || table.getVisibleRowCount() == 0) {
            return -1;
        }
        return table.convertRowIndexToModel(table.getSelectedRow());
    }
    
    /**
     * Create an empty sortable JXTable with sorting etc. enabled for a common look
     */
    protected JTable createTable() {
        EditorTable table = new EditorTable();
        try {
            table.getAccessibleContext().setAccessibleName("Table");
            table.getAccessibleContext().setAccessibleDescription("Table");
            table.setFillsViewportHeight(true);
        } catch(Throwable err) {
            // doesn't exist in Java 5
        }
        table.setShowGrid(false);
        return table;
    }

    /**
     * Binds search filtering to a JTable
     */
    protected void bindSearch(final JTextField search, JTable table) {
        final EditorTable jx = (EditorTable) table;
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateSearch();
            }

            public void removeUpdate(DocumentEvent e) {
                updateSearch();
            }

            public void changedUpdate(DocumentEvent e) {
                updateSearch();
            }

            private void updateSearch() {
                String t = search.getText();
                //jx.filter(t);
                if(t.length() > 0) {
                    jx.setRowFilter(RowFilters.regexFilter(Pattern.CASE_INSENSITIVE, t));
                } else {
                    jx.setRowFilter(null);
                }
            }
        });
    }

    /**
     * Implementation of the JTable providing sorting, highlighting and filtering 
     */
    public static class EditorTable extends JXTable {
        
        public EditorTable() {
            setSortable(true);
            addHighlighter(HighlighterFactory.createSimpleStriping());
            addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW, null, Color.RED));  
            setColumnControlVisible(true);
        }
    }
    
    public boolean isEditResourceSupported() {
        try {
            return Desktop.getDesktop().isSupported(Desktop.Action.EDIT);
        } catch(Throwable t) {
            return false;
        }
    }
    
    public static void editResource(java.awt.Component ui, String name, String extension, byte[] data, final UpdatedFile up) {
        try {
            final File tempImage = File.createTempFile(name, extension);
            tempImage.deleteOnExit();
            
            FileOutputStream os = new FileOutputStream(tempImage);
            os.write(data);
            os.close();
            Desktop.getDesktop().edit(tempImage);
            new Thread() {
                public void run() {
                    long tstamp = tempImage.lastModified();
                    File f = ResourceEditorView.getLoadedFile();
                    while(f == ResourceEditorView.getLoadedFile() && tempImage.exists()) {
                        if(tstamp != tempImage.lastModified()) {
                            tstamp = tempImage.lastModified();
                            up.fileUpdated(tempImage);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ui, "An error occured working with the file: " + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);            
        }
    }
    
    public static interface UpdatedFile {
        public void fileUpdated(File f);
    }
}
