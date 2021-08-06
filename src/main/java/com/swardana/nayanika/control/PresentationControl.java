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

package com.swardana.nayanika.control;

import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.slideshow.SlideShow;
import com.swardana.nayanika.base.slideshow.TimelineSlideShow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * An observable presentation state control.
 *
 * @author Sukma Wardana
 */
public class PresentationControl implements PresentationSubject {

    private final BooleanProperty runningProperty;

    private double timeTransition;
    private Gallery slideGallery;
    private SlideShow slideShow;
    private boolean running;

    /**
     * Creates new PresentationControl.
     */
    public PresentationControl() {
        this.runningProperty = new SimpleBooleanProperty(this, "running", false);
        this.running = false;
        this.timeTransition = 8.0;
    }

    @Override
    public final void gallery(final Gallery gallery) {
        this.slideGallery = gallery;
        this.updateSlide();
    }

    @Override
    public final void time(final double time) {
        this.timeTransition = time;
        this.updateSlide();
    }

    @Override
    public final void start() {
        if (this.slideShow != null) {
            this.slideShow.play();
            this.running = true;
            this.runningStateChanged();
        }
    }

    @Override
    public final void stop() {
        if (this.slideShow != null) {
            this.slideShow.stop();
            this.running = false;
            this.runningStateChanged();
        }
    }

    @Override
    public final boolean isRunning() {
        return this.running;
    }

    @Override
    public final ReadOnlyBooleanProperty runningProperty() {
        return this.runningProperty;
    }

    private void runningStateChanged() {
        this.runningProperty.setValue(this.isRunning());
    }

    private void updateSlide() {
        this.slideShow = new TimelineSlideShow(this.slideGallery, this.timeTransition);
    }

}
