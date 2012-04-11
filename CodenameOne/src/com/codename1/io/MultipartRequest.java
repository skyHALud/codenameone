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
package com.codename1.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A multipart post request allows a developer to submit large binary data 
 * files to the server in a post request
 *
 * @author Shai Almog
 */
public class MultipartRequest extends ConnectionRequest {
    private String boundary;
    private Hashtable args = new Hashtable();
    private Hashtable mimeTypes = new Hashtable();
    private static final String CRLF = "\r\n"; 
    
    /**
     * Initialize variables
     */
    public MultipartRequest() {
        setPost(true);
        setWriteRequest(true);
        
        // Just generate some unique random value.
        boundary = Long.toString(System.currentTimeMillis(), 16); 
        
        // Line separator required by multipart/form-data.
        setContentType("multipart/form-data; boundary=" + boundary);
    }
    
    /**
     * Adds a binary argument to the arguments
     * @param name the name of the data
     * @param data the data as bytes
     * @param mimeType the mime type for the content
     */
    public void addData(String name, byte[] data, String mimeType) {
        args.put(name, data);
        mimeTypes.put(name, mimeType);
    }

    /**
     * Adds a binary argument to the arguments, notice the input stream will be read only during submission
     * @param name the name of the data
     * @param data the data stream
     * @param mimeType the mime type for the content
     */
    public void addData(String name, InputStream data, String mimeType) {
        args.put(name, data);
        mimeTypes.put(name, mimeType);
    }
    
    /**
     * @inheritDoc
     */
    public void addArgument(String name, String value) {
        args.put(name, value);
    }

    /**
     * @inheritDoc
     */
    protected void buildRequestBody(OutputStream os) throws IOException {
        Writer writer = null;
        writer = new OutputStreamWriter(os, "UTF-8"); 
        Enumeration e = args.keys();
        while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            Object value = args.get(key);
            
            writer.write("--" + boundary);
            writer.write(CRLF);
            if(value instanceof String) {
                writer.write("Content-Disposition: form-data; name=\"" + key + "\"");
                writer.write(CRLF);
                writer.write("Content-Type: text/plain; charset=UTF-8");
                writer.write(CRLF);
                writer.write(CRLF);
                writer.flush();
                writer.write(Util.encodeBody((String)value));
                writer.write(CRLF);
                writer.flush();
            } else {
                writer.write("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + key +"\"");
                writer.write(CRLF);
                writer.write("Content-Type: ");
                writer.write((String)mimeTypes.get(key));
                writer.write(CRLF);
                writer.write("Content-Transfer-Encoding: binary");
                writer.write(CRLF);
                writer.write(CRLF);
                if(value instanceof InputStream) {
                    InputStream i = (InputStream)value;
                    byte[] buffer = new byte[8192];
                    int s = i.read(buffer);
                    while(s > -1) {
                        os.write(buffer, 0, s);
                        s = i.read(buffer);
                    }
                } else {
                    os.write((byte[])value);
                }
                writer.write(CRLF);
                writer.flush();
            }
            writer.write(CRLF);
            writer.flush();
        }
        
        writer.write("--" + boundary + "--");
        writer.write(CRLF);
        writer.close();
    }
}
