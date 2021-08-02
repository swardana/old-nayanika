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

package com.swardana.nayanika.base.slideshow;

import com.swardana.nayanika.base.gallery.Gallery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * A slide-show presentation using JavaFX {@link Timeline}.
 *
 * @author Sukma Wardana
 */
public class TimelineSlideShow implements SlideShow {

    private static final double DEFAULT_TRANSITION = 8;

    private final Timeline timeline;
    private final Gallery gallery;
    private final double time;

    private final EventHandler<ActionEvent> transitionAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            if (gallery.isLast()) {
                stop();
            } else {
                gallery.next();
            }
        }
    };

    /**
     * Creates new TimelineSlideShow.
     * <p>
     *     Assign default transition speed.
     * </p>
     *
     * @param gallery the animated gallery during slide-show presentation.
     */
    public TimelineSlideShow(final Gallery gallery) {
        this(gallery, DEFAULT_TRANSITION);
    }

    /**
     * Creates new TimelineSlideShow.
     *
     * @param gallery the animated gallery during slide-show presentation.
     * @param duration the transition time duration
     */
    public TimelineSlideShow(final Gallery gallery, final Double duration) {
        this(gallery, duration.doubleValue());
    }

    /**
     * Creates new TimelineSlideShow.
     *
     * @param gallery the animated gallery during slide-show presentation.
     * @param duration the transition time duration
     */
    public TimelineSlideShow(final Gallery gallery, final double duration) {
        this.gallery = gallery;
        this.time = duration;
        this.timeline = new Timeline();
    }

    @Override
    public final void play() {
        var keyFrame = new KeyFrame(
            Duration.seconds(this.time),
            this.transitionAction
        );
        this.timeline.getKeyFrames().addAll(keyFrame);
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    @Override
    public final void stop() {
        this.timeline.stop();
        this.timeline.getKeyFrames().clear();
    }

}
