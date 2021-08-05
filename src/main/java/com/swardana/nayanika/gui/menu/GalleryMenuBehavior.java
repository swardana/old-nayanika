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

package com.swardana.nayanika.gui.menu;

import com.swardana.nayanika.base.FullScreen;
import com.swardana.nayanika.base.Presentation;
import com.swardana.nayanika.base.ThreadExecutor;
import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.SupportedPicture;
import com.swardana.nayanika.base.slideshow.SlideShow;
import com.swardana.nayanika.base.slideshow.TimelineSlideShow;
import com.swardana.nayanika.command.OpenGallery;

import java.io.File;
import java.util.Objects;

/**
 * A behavior of {@link GalleryMenuView}.
 *
 * @author Sukma Wardana
 */
public abstract class GalleryMenuBehavior {

    private final GalleryMenuView view;
    private final FullScreen fullScreen;
    private final Presentation presentation;
    private final OpenGallery openGallery;

    /**
     * Creates new GalleryMenuBehavior.
     *
     * @param view the toolbar view counter-part.
     * @param fullScreen the app full-screen window.
     * @param exhibition the exhibition.
     * @param presentation the slide-show presentation.
     */
    public GalleryMenuBehavior(
        final GalleryMenuView view,
        final FullScreen fullScreen,
        final Exhibition exhibition,
        final Presentation presentation
    ) {
        this.view = view;
        this.fullScreen = fullScreen;
        this.presentation = presentation;
        this.openGallery = new OpenGallery(
            exhibition, ThreadExecutor.getInstance().executor()
        );
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when picture gallery is changed.
     * </p>
     */
    public final void onPictureGalleryChange() {
        if (Objects.isNull(this.view.gallery())) {
            return;
        }
        this.view.enableSlideShowMenu();
    }

    /**
     * Called by the view.
     * <p>
     *     Handling operation opening picture gallery with set up the current
     *     active picture.
     * </p>
     *
     * @param pic the current active picture.
     * @param filter the applied filter for pictures.
     */
    public final void onOpenPictureGallery(final File pic, final SupportedPicture filter) {
        if (Objects.isNull(pic) || pic.isDirectory()) {
            return;
        }
        this.openGallery.execute(pic, filter);
    }

    /**
     * Called by the view.
     * <p>
     *     Handling operation opening picture gallery storage.
     * </p>
     *
     * @param dir the picture gallery directory.
     * @param filter the applied filter for pictures.
     */
    public final void onOpenPictureGalleryDirectory(final File dir, final SupportedPicture filter) {
        if (Objects.isNull(dir) || dir.isFile()) {
            return;
        }
        this.openGallery.execute(dir, filter);
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the next one.
     * </p>
     */
    public final void onViewNextPicture() {
        this.view.gallery().next();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the previous one.
     * </p>
     */
    public final void onViewPreviousPicture() {
        this.view.gallery().previous();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the first one.
     * </p>
     */
    public final void onViewFirstPicture() {
        this.view.gallery().first();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle switch the current active picture to the last one.
     * </p>
     */
    public final void onViewLastPicture() {
        this.view.gallery().last();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle operation to enter full-screen mode.
     * </p>
     */
    public final void onViewEnterFullScreen() {
        this.fullScreen.enterFullScreen();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle operation to exit full-screen mode.
     * </p>
     */
    public final void onViewExitFullScreen() {
        this.fullScreen.exitFullScreen();
    }

    /**
     * Handle when observable full-screen subject changed.
     */
    public final void onFullScreenControlChange() {
        if (this.fullScreen.isFullScreen()) {
            this.view.showExitFullScreenMenu();
        } else {
            this.view.showEnterFullScreenMenu();
        }
    }

    /**
     * Called by the view.
     * <p>
     *     Handle operation to start slide presentation.
     * </p>
     */
    public final void onViewStartPresentation() {
        if (this.view.gallery().isLast()) {
            return;
        }
        if (!this.fullScreen.isFullScreen()) {
            this.fullScreen.enterFullScreen();
        }
        this.presentation.start();
    }

    /**
     * Called by the view.
     * <p>
     *     Handle operation to stop slide presentation.
     * </p>
     */
    public final void onViewStopPresentation() {
        this.presentation.stop();
    }

    /**
     * Handle when observable presentation subject changed.
     */
    public final void onPresentationControlChange() {
        if (this.presentation.isRunning()) {
            this.view.showStopSlideShowMenu();
        } else {
            this.view.showStartSlideShowMenu();
        }
    }

    /**
     * Called by the view.
     * <p>
     *     Updating the picture gallery and transition animation duration for
     *     the slide presentation.
     * </p>
     *
     * @param gallery the new picture gallery.
     * @param duration the new transition speed duration.
     */
    public final void updateSlidePresentation(final Gallery gallery, final double duration) {
        this.updateSlidePresentation(new TimelineSlideShow(gallery, duration));
    }

    /**
     * Called by the view.
     * <p>
     *     Update the slide presentation.
     * </p>
     *
     * @param slideShow the new slide-show presentation.
     */
    public final void updateSlidePresentation(final SlideShow slideShow) {
        this.presentation.slide(slideShow);
    }

}
