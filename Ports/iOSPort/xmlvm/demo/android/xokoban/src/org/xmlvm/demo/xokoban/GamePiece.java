/* Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.xmlvm.demo.xokoban;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * A GamePiece represents the base class for a single tile in the game.
 */
public abstract class GamePiece {

    /**
     * Simple class for describing a position.
     */
    static class Position {
        private int x;
        private int y;


        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            Position other = (Position) o;
            return other.x == x && other.y == y;
        }
    }


    /** The threshold below which the LD tiles should be used. */
    public static final int SIZE_THRESHOLD = 30;

    /**
     * The size of the square piece in pixels.
     */
    private int             tileSize;
    /**
     * The x-coordinate of the GamePiece.
     */
    protected int           x;
    /**
     * The y-coordinate of the GamePiece.
     */
    protected int           y;
    /**
     * The {@link GameView} use for the current game.
     */
    protected GameView      view;
    /**
     * The {@link ImageView} used to draw the GamePiece.
     */
    protected ImageView     image;


    /**
     * Instantiates a GamePiece object.
     * 
     * @param view
     *            The GameView used in the current game.
     * @param resourceID
     *            The image resource ID to be used for drawing this GamePiece.
     * @param tileSize
     *            The tile size of this game piece.
     * @param x
     *            The x-coordinate to draw this GamePiece.
     * @param y
     *            The y-coordinate to draw this GamePiece.
     */
    public GamePiece(GameView view, int resourceID, int tileSize, int x, int y, boolean addToFront) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
        image = new ImageView(view.getContext());
        if (addToFront) {
            view.addViewToBoard(image);
        } else {
            view.addViewToBoard(image, 0);
        }
        image.setImageResource(resourceID);
        updatePosition();
    }

    /**
     * Updates the position of this GamePiece with the current location.
     */
    protected void updatePosition() {
        updatePosition(0, 0);
    }

    /**
     * Update the position of this GamePiece with the current location plus the
     * difference given.
     * 
     * @param px
     *            Adds to the x-position of the GamePiece.
     * @param py
     *            Adds to the y-position of the GamePiece.
     */
    protected void updatePosition(int px, int py) {
        final int left = view.getOffsetLeft() + x * tileSize + px;
        final int top = view.getOffsetTop() + y * tileSize + py;
        final int right = left + tileSize;
        final int bottom = top + tileSize;
        image.layout(left, top, right, bottom);
    }

    /**
     * Returns the x-coordinate of the GamePiece.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of the GamePiece.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the size of this tile.
     * 
     * @return
     */
    public int getTileSize() {
        return tileSize;
    }
}
