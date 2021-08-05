/*
 * Nayanika, picture viewer application
 * Copyright (C) 2021  Sukma Wardana
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.swardana.nayanika.gui.frame;

import com.swardana.nayanika.base.FullScreen;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A behavior of {@link FrameView}.
 *
 * @author Sukma Wardana
 */
class FrameBehavior {

    private static final Logger LOGGER = Logger.getLogger(FrameBehavior.class.getName());

    private static final double SCALE_DELTA = 1.125;
    private static final double MIN_SCALE = 1;

    private final FrameView view;
    private final FullScreen control;

    /**
     * Creates new FrameBehavior.
     *
     * @param view the frame view counter part.
     * @param control the app full-screen window.
     */
    FrameBehavior(final FrameView view, final FullScreen control) {
        this.view = view;
        this.control = control;
    }

    /**
     * Called by the view.
     * <p>
     *     Set up the initial data or configuration for frame view.
     * </p>
     */
    final void initView() {
        this.zoomAvailability();
    }

    /**
     * Called by the view.
     * <p>
     *     Operation to handle picture zoom.
     * </p>
     *
     * @param delta the vertical scroll amount.
     * @param horizontal the scale factor by which coordinates along the X axis.
     * @param vertical the scale factor by which coordinates along the Y axis.
     */
    final void onViewZoom(
        final double delta,
        final double horizontal,
        final double vertical
    ) {
        double scale = this.scaleFactor(delta);

        /**
         * Amount of scrolling in each direction in scrollContent coordinate
         * units.
         */
        double scrollXOffset = this.view.horizontalScrollOffset();
        double scrollYOffset = this.view.verticalScrollOffset();

        double maxHorizontal = Math.max(horizontal * scale, MIN_SCALE);
        double maxVertical = Math.max(vertical * scale, MIN_SCALE);

        this.view.rescalePicture(maxHorizontal, maxVertical);
        LOGGER.log(
            Level.FINER,
            "Rescale the picture. "
                + "[horizontal={0}, vertical={1}, scale={2}, "
                + "min-scale={3}, max-horizontal={4}, max-vertical={5}",
            new Object[]{horizontal, vertical, scale, MIN_SCALE, maxHorizontal, maxVertical}
        );

        /**
         * Move viewport so that old center remains in the center after the
         * scaling.
         */
        this.view.repositionScroller(scale, scrollXOffset, scrollYOffset);
        LOGGER.log(
            Level.FINER,
            "Reposition the scroller. "
                + "[scale={0}, scroll-x-offset={1}, scroll-y-offset={2}]",
            new Object[]{scale, scrollXOffset, scrollYOffset}
        );
    }

    /**
     * Called by the view.
     * <p>
     *     Operation to handle mouse click on the pane.
     *     Double click mean to reset the picture scale.
     * </p>
     *
     * @param count the mouse clicked count.
     */
    final void onViewMouseClicked(final int count) {
        if (count == 2) {
            this.view.resetPictureScale();
            LOGGER.log(
                Level.FINER,
                "Reset the picture scale on frame. [mouse-click={0}]",
                new Object[]{count}
            );
        }
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when mouse dragged on zoomed picture.
     * </p>
     *
     * @param clickedX the horizontal point when mouse clicked.
     * @param draggedX the horizontal point when mouse stopped after drag.
     * @param clickedY the vertical point when mouse clicked.
     * @param draggedY the vertical point when mouse stopped after drag.
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    final void onViewMouseDragged(
        final double clickedX,
        final double draggedX,
        final double clickedY,
        final double draggedY
    ) {
        double deltaX = clickedX - draggedX;
        double deltaY = clickedY - draggedY;

        this.view.horizontalMove(deltaX);
        this.view.verticalMove(deltaY);
        LOGGER.log(
            Level.FINER,
            "Reposition the picture. "
                + "[delta-x={0}, click-x={1}, drag-x={2}, "
                + "delta-y={3}, click-y={4}, drag-y={5}]",
            new Object[]{deltaX, clickedX, draggedX, deltaY, clickedY, draggedY}
        );
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the next one.
     * </p>
     */
    final void onViewNextPicture() {
        this.view.gallery().next();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the next one.
     * </p>
     */
    final void onViewPreviousPicture() {
        this.view.gallery().previous();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when view image is change.
     * </p>
     */
    final void onViewImageChange() {
        this.view.focus();
        this.view.resetPictureScale();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when app window full-screen state change.
     * </p>
     */
    final void onViewScreenChange() {
        this.zoomAvailability();
        this.view.resetPictureScale();
        this.view.focus();
    }

    private double scaleFactor(final double delta) {
        final double result;
        if (delta > 0) {
            result = SCALE_DELTA;
        } else {
            result = MIN_SCALE / SCALE_DELTA;
        }
        return result;
    }

    private void zoomAvailability() {
        if (this.control.isFullScreen()) {
            this.view.disableZoom();
        } else {
            this.view.enableZoom();
        }
        LOGGER.log(
            Level.FINE,
            "Initiate the zoom availability. [zoom={0}]",
            new Object[]{!this.control.isFullScreen()}
        );
        LOGGER.log(Level.INFO, "Successfully initiate the zoom feature availability.");
    }

}
