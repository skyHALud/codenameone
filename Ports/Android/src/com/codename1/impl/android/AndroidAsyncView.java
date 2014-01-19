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
package com.codename1.impl.android;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Rectangle;
import java.util.ArrayList;

public class AndroidAsyncView extends View implements CodenameOneSurface {
    interface AsyncOp {
        public void execute(AndroidGraphics underlying);
    }
    private ArrayList<AsyncOp> renderingOperations = new ArrayList<AsyncOp>();
    private ArrayList<AsyncOp> pendingRenderingOperations = new ArrayList<AsyncOp>();
    private CodenameOneView cn1View;
    private Rect rect;
    private Paint layerPaint = new Paint();
    private static final Object RENDER_LOCK = new Object();
    private AndroidImplementation implementation;
    private AndroidGraphics graphics;

    public AndroidAsyncView(Activity activity, AndroidImplementation implementation) {
        super(activity);
        setId(2001);
        this.implementation = implementation;
        graphics = new AsyncGraphics(implementation);
        cn1View = new CodenameOneView(activity, this, implementation, true);
    }

    @Override
    protected void onDraw(Canvas c) {
        c.saveLayer(null, layerPaint, 0);
        AndroidGraphics g = new AndroidGraphics(implementation, c);
        if(rect != null) {
            g.setClip(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
        }
        //Log.d(Display.getInstance().getProperty("AppName", "CodenameOne"), "On draw drawing " + renderingOperations.size() + " elements...");
        for(AsyncOp o : renderingOperations) {
            o.execute(g);
        }
        synchronized(RENDER_LOCK) {
            renderingOperations.clear();
        }
        c.restore();
    }
    
    public boolean isOpaque() {
        return true;
    }


    private void visibilityChangedTo(boolean visible) {
        cn1View.visibilityChangedTo(visible);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // method used for View implementation. is it still
        // required with a SurfaceView?
        super.onWindowVisibilityChanged(visibility);
        this.visibilityChangedTo(visibility == View.VISIBLE);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (!Display.isInitialized()) {
            return;
        }
        Display.getInstance().callSerially(new Runnable() {

            public void run() {
                cn1View.handleSizeChange(w, h);
            }
        });
    }


    public void flushGraphics(Rect rect) {
        //Log.d(Display.getInstance().getProperty("AppName", "CodenameOne"), "Flush graphics invoked with pending: " + pendingRenderingOperations.size() + " and current " + renderingOperations.size());
        this.rect = rect;
        
        // we might have pending entries in the rendering queue
        int counter = 0;
        if(renderingOperations.size() > 0) {
            postInvalidate();
            while(renderingOperations.size() > 0) {
                try {
                    Thread.sleep(5);

                    // don't let the EDT die here
                    counter++;
                    if(counter > 10) {
                        //Log.d(Display.getInstance().getProperty("AppName", "CodenameOne"), "Flush graphics timed out!!!");
                        return;
                    }
                } catch(InterruptedException err) {
                }
            }
        }
        synchronized(RENDER_LOCK) {
            ArrayList<AsyncOp> tmp = renderingOperations;
            renderingOperations = pendingRenderingOperations;
            pendingRenderingOperations = tmp;
        }
        if(rect == null) {
            postInvalidate();
        } else {
            postInvalidate(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public void flushGraphics() {
        flushGraphics(null);
    }
    
            
    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (InPlaceEditView.isEditing()) {
            return true;
        }
        return cn1View.onKeyUpDown(true, keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (InPlaceEditView.isEditing()) {
            return true;
        }
        return cn1View.onKeyUpDown(false, keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return cn1View.onTouchEvent(event);
    }

    public AndroidGraphics getGraphics() {
        return graphics;
    }

    public int getViewHeight() {
        return cn1View.height;
    }

    public int getViewWidth() {
        return cn1View.width;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {

        if (!Display.isInitialized() || Display.getInstance().getCurrent() == null) {
            return super.onCreateInputConnection(editorInfo);
        }
        cn1View.setInputType(editorInfo);
        return super.onCreateInputConnection(editorInfo);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        if (!Display.isInitialized() || Display.getInstance().getCurrent() == null) {
            return false;
        }
        Component txtCmp = Display.getInstance().getCurrent().getFocused();
        if (txtCmp != null && txtCmp instanceof TextField) {
            return true;
        }
        return false;
    }

    @Override
    public View getAndroidView() {
        return this;
    }

    class AsyncGraphics extends AndroidGraphics {
        private Rectangle clip = null;
        private int alpha;
        private int color;
        AsyncGraphics(AndroidImplementation impl) {
            super(impl, null);
        }

        @Override
        public void rotate(final float angle, final int x, final int y) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.rotate(angle, x, y);
                }
            });
        }

        @Override
        public void rotate(final float angle) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.rotate(angle);
                }
            });
        }

        @Override
        public void scale(final float x, final float y) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.scale(x, y);
                }
            });
        }

        @Override
        public void resetAffine() {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.resetAffine();
                }
            });
        }

        @Override
        public int getColor() {
            return color;
        }

        @Override
        public void clipRect(final int x, final int y, final int width, final int height) {
            if(clip == null) {
                clip = new Rectangle(x, y, width, height);
            } else {
                clip = clip.intersection(x, y, width, height);
            }
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.clipRect(x, y, width, height); 
                }
            });
        }

        @Override
        public void setClip(final int x, final int y, final int width, final int height) {
            if(clip == null) {
                clip = new Rectangle(x, y, width, height);
            } else {
                clip.setX(x);
                clip.setY(y);
                clip.setWidth(width);
                clip.setHeight(height);
            }
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.setClip(x, y, width, height); 
                }
            });
        }

        @Override
        public int getClipY() {
            if(clip != null) {
                return clip.getY();
            }
            return 0;
        }

        @Override
        public int getClipX() {
            if(clip != null) {
                return clip.getX();
            }
            return 0;
        }

        @Override
        public int getClipWidth() {
            if(clip != null) {
                return clip.getWidth();
            }
            return cn1View.width;
        }

        @Override
        public int getClipHeight() {
            if(clip != null) {
                return clip.getHeight();
            }
            return cn1View.height;
        }

        @Override
        public void setAlpha(final int alpha) {
            this.alpha = alpha;
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.setAlpha(alpha); 
                }
            });
        }

        @Override
        public int getAlpha() {
            return alpha;
        }

        @Override
        public void fillRoundRect(final int x, final int y, final int width, final int height, final int arcWidth, final int arcHeight) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.fillRoundRect(x, y, width, height, arcWidth, arcHeight); 
                }
            });
        }

        @Override
        public void fillRect(final int x, final int y, final int width, final int height) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.fillRect(x, y, width, height);
                }
            }); 
        }

        @Override
        public void fillArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.fillArc(x, y, width, height, startAngle, arcAngle); 
                }
            });
        }

        @Override
        public void drawArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawArc(x, y, width, height, startAngle, arcAngle); 
                }
            });
        }

        @Override
        public void drawString(final String str, final int x, final int y) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawString(str, x, y); 
                }
            });
        }

        @Override
        public void drawRoundRect(final int x, final int y, final int width, final int height, final int arcWidth, final int arcHeight) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawRoundRect(x, y, width, height, arcWidth, arcHeight); 
                }
            });
        }

        @Override
        public void drawRect(final int x, final int y, final int width, final int height) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawRect(x, y, width, height); 
                }
            });
        }

        @Override
        public void drawRGB(final int[] rgbData, final int offset, final int x, final int y, final int w, final int h, final boolean processAlpha) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawRGB(rgbData, offset, x, y, w, h, processAlpha); 
                }
            });
        }

        @Override
        public void fillPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.fillPolygon(xPoints, yPoints, nPoints); 
                }
            });
        }

        @Override
        public void drawPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawPolygon(xPoints, yPoints, nPoints); 
                }
            });
        }

        @Override
        public void drawLine(final int x1, final int y1, final int x2, final int y2) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawLine(x1, y1, x2, y2); 
                }
            });
        }

        @Override
        public void drawImage(final Object img, final int x, final int y, final int w, final int h) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawImage(img, x, y, w, h); 
                }
            });
        }

        @Override
        public void drawImage(final Object img, final int x, final int y) {
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.drawImage(img, x, y); 
                }
            });
        }

        @Override
        Paint getPaint() {
            return super.getPaint(); 
        }

        @Override
        void setColor(final int color) {
            this.color = color;
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.setColor(color);
                }
            });
        }

        @Override
        void setFont(final Paint font) {
            super.setFont(font); 
            pendingRenderingOperations.add(new AsyncOp() {
                @Override
                public void execute(AndroidGraphics underlying) {
                    underlying.setFont(font);
                }
            });
        }

        @Override
        Paint getFont() {
            return super.getFont(); 
        }

        @Override
        void setCanvas(Canvas canvas) {
            super.setCanvas(canvas); 
        }
        
        
    }
    
}
