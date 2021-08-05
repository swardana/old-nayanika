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

import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.control.ExhibitionSubject;
import com.swardana.nayanika.control.FullScreenSubject;
import com.swardana.nayanika.control.GallerySubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * A picture frame visual.
 *
 * @author Sukma Wardana
 */
public class FrameVisual extends ScrollPane implements FrameView {

    private final FramePane frame;
    private final ImageView image;
    private final Group content;

    private final FrameBehavior behavior;

    private final ExhibitionSubject exhibitionSubject;
    private final FullScreenSubject fullScreenSubject;

    private final ObjectProperty<Point2D> mouseAnchor;
    private final EventHandler<ScrollEvent> scrolledAction = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(final ScrollEvent event) {
            event.consume();
            if (event.getDeltaY() == 0) {
                return;
            }
            behavior.onViewZoom(
                event.getDeltaY(),
                image.getScaleX(),
                image.getScaleY()
            );
        }
    };
    private final ChangeListener<Bounds> boundsAction = new ChangeListener<Bounds>() {
        @Override
        public void changed(
            final ObservableValue<? extends Bounds> observable,
            final Bounds oldBounds,
            final Bounds newBounds
        ) {
            frame.setPrefSize(
                newBounds.getWidth(),
                newBounds.getHeight()
            );
        }
    };
    private final EventHandler<MouseEvent> mouseClickedAction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
            behavior.onViewMouseClicked(event.getClickCount());
        }
    };
    private final EventHandler<MouseEvent> mousePressedAction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
            mouseAnchor.set(
                new Point2D(
                    event.getX(),
                    event.getY()
                )
            );
        }
    };
    private final EventHandler<MouseEvent> mouseDraggedAction = new EventHandler<MouseEvent>() {
        @Override
        public void handle(final MouseEvent event) {
            event.consume();
            behavior.onViewMouseDragged(
                event.getX(),
                mouseAnchor.get().getX(),
                event.getY(),
                mouseAnchor.get().getY()
            );
        }
    };
    private final EventHandler<KeyEvent> keyPressedAction = new EventHandler<KeyEvent>() {
        @Override
        public void handle(final KeyEvent event) {
            switch (event.getCode()) {
                case RIGHT:
                case DOWN:
                    behavior.onViewNextPicture();
                    break;
                case LEFT:
                case UP:
                    behavior.onViewPreviousPicture();
                    break;
                default:
                    break;
            }
        }
    };
    private final ChangeListener<Image> imgChangeAction = new ChangeListener<Image>() {
        @Override
        public void changed(
            final ObservableValue<? extends Image> observable,
            final Image oldImg,
            final Image newImg
        ) {
            behavior.onViewImageChange();
        }
    };

    /**
     * Creates new FrameVisual.
     *
     * @param fullScreenSubject the full-screen observable state control.
     * @param exhibitionSubject the exhibition observable state control.
     */
    public FrameVisual(
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject
    ) {
        this.frame = new FramePane();
        this.image = new ImageView();
        this.content = new Group();

        this.fullScreenSubject = fullScreenSubject;
        this.exhibitionSubject = exhibitionSubject;
        this.behavior = new FrameBehavior(this, fullScreenSubject);

        this.mouseAnchor = new SimpleObjectProperty<>(this, "mouse", null);

        this.initGraphics();
        this.registerListeners();
    }

    @Override
    public final void focus() {
        this.setFocusTraversable(true);
        this.requestFocus();
    }

    @Override
    public final void enableZoom() {
        this.frame.setOnScroll(this.scrolledAction);
    }

    @Override
    public final void disableZoom() {
        this.frame.setOnScroll(null);
    }

    @Override
    public final void rescalePicture(final double horizontal, final double vertical) {
        this.image.setScaleX(horizontal);
        this.image.setScaleY(vertical);
    }

    @Override
    public final double horizontalScrollOffset() {
        double extraWidth = this.content.getLayoutBounds().getWidth()
            - this.getViewportBounds().getWidth();

        double hScrollProportion = (this.getHvalue() - this.getHmin())
            / (this.getHmax() - this.getHmin());

        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);

        return scrollXOffset;
    }

    @Override
    public final double verticalScrollOffset() {
        double extraHeight = this.content.getLayoutBounds().getHeight()
            - this.getViewportBounds().getHeight();

        double vScrollProportion = (this.getVvalue() - this.getVmin())
            / (this.getVmax() - this.getVmin());

        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);

        return scrollYOffset;
    }

    @Override
    public final void repositionScroller(
        final double scaleFactor,
        final double scrollXOffset,
        final double scrollYOffset
    ) {
        double extraWidth = this.content.getLayoutBounds().getWidth()
            - this.getViewportBounds().getWidth();

        if (extraWidth > 0) {
            double halfWidth = this.getViewportBounds().getWidth() / 2;

            double newScrollXOffset = (scaleFactor - 1)
                *  halfWidth + scaleFactor * scrollXOffset;

            this.setHvalue(
                this.getHmin() + newScrollXOffset
                    * (this.getHmax() - this.getHmin()) / extraWidth
            );
        } else {
            this.setHvalue(this.getHmin());
        }

        double extraHeight = this.content.getLayoutBounds().getHeight()
            - this.getViewportBounds().getHeight();

        if (extraHeight > 0) {
            double halfHeight = this.getViewportBounds().getHeight() / 2;

            double newScrollYOffset = (scaleFactor - 1)
                * halfHeight + scaleFactor * scrollYOffset;

            this.setVvalue(
                this.getVmin() + newScrollYOffset
                    * (this.getVmax() - this.getVmin()) / extraHeight
            );
        } else {
            this.setHvalue(this.getHmin());
        }
    }

    @Override
    public final void horizontalMove(final double deltaX) {
        double extraWidth = this.content.getLayoutBounds().getWidth()
            - this.getViewportBounds().getWidth();

        double deltaH = deltaX * (this.getHmax() - this.getHmin()) / extraWidth;

        double desiredH = this.getHvalue() - deltaH;

        this.setHvalue(Math.max(0, Math.min(this.getHmax(), desiredH)));
    }

    @Override
    public final void verticalMove(final double deltaY) {
        double extraHeight = this.content.getLayoutBounds().getHeight()
            - this.getViewportBounds().getHeight();

        double deltaV = deltaY * (this.getHmax() - this.getHmin()) / extraHeight;

        double desiredV = this.getVvalue() - deltaV;

        this.setVvalue(Math.max(0, Math.min(this.getVmax(), desiredV)));
    }

    @Override
    public final Gallery gallery() {
        return this.exhibitionSubject.galleryProperty().getValue();
    }

    private void initGraphics() {
        this.image.setPreserveRatio(true);
        this.image.setSmooth(true);
        this.image.setCache(true);
        this.frame.setImage(this.image);
        this.content.getChildren().add(this.frame);
        this.setContent(this.content);
    }

    private void registerListeners() {
        this.behavior.initView();
        this.setOnKeyPressed(this.keyPressedAction);
        this.viewportBoundsProperty().addListener(this.boundsAction);
        this.setOnMouseClicked(this.mouseClickedAction);
        this.content.setOnMousePressed(mousePressedAction);
        this.content.setOnMouseDragged(mouseDraggedAction);
        this.frame.setOnScroll(this.scrolledAction);
        this.image.imageProperty().addListener(this.imgChangeAction);
        this.exhibitionSubject.galleryProperty().addListener(new ChangeListener<GallerySubject>() {
            @Override
            public void changed(
                final ObservableValue<? extends GallerySubject> observable,
                final GallerySubject oldGallery,
                final GallerySubject newGallery
            ) {
                handleExhibitionChange(newGallery);
            }
        });
        this.fullScreenSubject.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldValue,
                final Boolean newValue
            ) {
                behavior.onViewScreenChange();
            }
        });
    }

    /**
     * Handle when the {@link GallerySubject} is changed.
     * <p>
     *     When the {@link GallerySubject} is changed, will need to un-bind from
     *     old image gallery to the new one. Also require to reset picture scale
     *     and focus.
     * </p>
     *
     * @param subject the gallery subject.
     */
    private void handleExhibitionChange(final GallerySubject subject) {
        this.image.imageProperty().unbind();
        this.image.imageProperty().bind(subject.imageProperty());
        this.resetPictureScale();
        this.focus();
    }

}
