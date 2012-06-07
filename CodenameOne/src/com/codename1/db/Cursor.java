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
package com.codename1.db;

import java.io.IOException;

/**
 * The Cursor Object is used to iterate over the results
 *
 * @author Chen
 */
public interface Cursor {

    /**
     * Move the cursor to the first row.
     * 
     * If cursor provides forward-only navigation and is positioned after the 
     * first row then calling first() method would throw a IOException.     
     * @return true if succeeded 
     * @throws IOException 
     */
    public boolean first() throws IOException;

    /**
     * Move the cursor to the last row.
     * 
     * @return true if succeeded 
     * @throws IOException 
     */
    public boolean last() throws IOException;

    /**
     * Moves the cursor to the next row.
     * Calling next() method the first time will position cursor on the first.
     * 
     * @return true if succeeded 
     * @throws IOException 
     */
    public boolean next() throws IOException;

    /**
     * Moves the cursor to the previous row.
     * If cursor is forward type then calling prev() would throw a IOException.
     * 
     * @return true if succeeded 
     * @throws IOException 
     */
    public boolean prev() throws IOException;

    /**
     * Returns the zero-based index for a given column name.
     * Note that columns meta information is available only after navigation to 
     * the first row
     * 
     * @param columnName the name of the column.
     * @return the index of the column
     * @throws IOException 
     */
    public int getColumnIndex(String columnName) throws IOException;

    /**
     * Returns the column name at a given zero-based column index.
     * Note that columns meta information is available only after navigation to 
     * the first row
     * 
     * @param columnIndex the index of the column
     * @return the name of the column
     * 
     * @throws IOException 
     */
    public String getColumnName(int columnIndex) throws IOException;

    /**
     * Returns the current Cursor position.
     * 
     * @return the cursor position
     * @throws IOException 
     */
    public int getPosition() throws IOException;
    
    /**
     * Move the cursor to an absolute row position
     * 
     * @param row position to move to
     * @return true if succeeded 
     * @throws IOException 
     */
    public boolean position(int row) throws IOException;

    /**
     * Close the cursor and release its resources
     * @throws IOException 
     */
    public void close() throws IOException;
    
    /**
     * Get the Row data Object.
     * 
     * @return a Row Object
     * @throws IOException 
     */
    public Row getRow() throws IOException; 
    
}
