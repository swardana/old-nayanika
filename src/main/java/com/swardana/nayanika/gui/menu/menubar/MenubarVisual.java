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

package com.swardana.nayanika.gui.menu.menubar;

import com.swardana.nayanika.AppPlatform;
import com.swardana.nayanika.I18N;
import com.swardana.nayanika.base.gallery.Gallery;
import com.swardana.nayanika.base.gallery.SupportedPicture;
import com.swardana.nayanika.control.ExhibitionSubject;
import com.swardana.nayanika.control.FullScreenSubject;
import com.swardana.nayanika.control.GallerySubject;
import com.swardana.nayanika.control.PresentationSubject;
import com.swardana.nayanika.control.ToolbarSubject;
import com.swardana.nayanika.gui.about.AboutVisual;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

public class MenubarVisual extends MenuBar implements MenubarView {

    private static final int MENU_SCREEN_INDEX = 2;
    private static final int MENU_SLIDE_INDEX = 3;

    private static final KeyCombination.Modifier MODIFIER;
    private static final SupportedPicture PICTURE_FILTER = SupportedPicture.ALL;

    private final Stage owner;

    // File Menu
    private final Menu fileMenu = new Menu();
    private final MenuItem openPicture = new MenuItem();
    private final MenuItem openDirectory = new MenuItem();
    private final MenuItem exit = new MenuItem();

    // Edit Menu
    private final Menu editMenu = new Menu();
    private final MenuItem next = new MenuItem();
    private final MenuItem prev = new MenuItem();
    private final MenuItem toFirst = new MenuItem();
    private final MenuItem toLast = new MenuItem();

    // View Menu
    private final Menu viewMenu = new Menu();
    private final CheckMenuItem toolBarCheckMenu = new CheckMenuItem();
    private final MenuItem enterFullScreen = new MenuItem();
    private final MenuItem exitFullScreen = new MenuItem();
    private final MenuItem startSlideShow = new MenuItem();
    private final MenuItem stopSlideShow = new MenuItem();

    // Setting Menu
    private final Menu settingMenu = new Menu();
    private final Menu language = new Menu();
    private final CheckMenuItem english = new CheckMenuItem();
    private final CheckMenuItem indonesia = new CheckMenuItem();
    private final MenuItem option = new MenuItem();

    // Help Menu
    private final Menu helpMenu = new Menu();
    private final MenuItem about = new MenuItem();

    private final AboutVisual aboutVisual;

    private final FullScreenSubject fullScreenSubject;
    private final ExhibitionSubject exhibitionSubject;
    private final PresentationSubject presentationSubject;
    private final MenubarBehavior behavior;

    private double time;

    private final EventHandler<ActionEvent> openPictureAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                PICTURE_FILTER.name(), PICTURE_FILTER.extensions()
            );
            chooser.getExtensionFilters().add(filter);
            File pic = chooser.showOpenDialog(owner);
            behavior.onOpenPictureGallery(pic, PICTURE_FILTER);
        }
    };

    private final EventHandler<ActionEvent> openFolderAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            DirectoryChooser chooser = new DirectoryChooser();
            File dir = chooser.showDialog(owner);
            behavior.onOpenPictureGalleryDirectory(dir, SupportedPicture.ALL);
        }
    };

    private final EventHandler<ActionEvent> fullScreenAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewEnterFullScreen();
        }
    };

    private final EventHandler<ActionEvent> exitFullScreenAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewExitFullScreen();
        }
    };

    private final EventHandler<ActionEvent> startSlideAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewStartPresentation();
        }
    };

    private final EventHandler<ActionEvent> stopSlideAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewStopPresentation();
        }
    };

    private final EventHandler<ActionEvent> nextAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewNextPicture();
        }
    };

    private final EventHandler<ActionEvent> previousAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewPreviousPicture();
        }
    };

    private final EventHandler<ActionEvent> firstAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewFirstPicture();
        }
    };

    private final EventHandler<ActionEvent> lastAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(final ActionEvent event) {
            behavior.onViewLastPicture();
        }
    };

    private final ChangeListener<Boolean> toolbarListen = new ChangeListener<Boolean>() {
        @Override
        public void changed(
            final ObservableValue<? extends Boolean> observable,
            final Boolean oldVal,
            final Boolean newVal
        ) {
            behavior.onToolbarVisibilityChange();
        }
    };

    static {
        if (AppPlatform.IS_MAC) {
            MODIFIER = KeyCombination.META_DOWN;
        } else {
            // Should cover Windows, Solaris, Linux.
            MODIFIER = KeyCombination.CONTROL_DOWN;
        }
    }

    /**
     * Creates new MenubarVisual.
     *
     * @param stage the primary stage.
     * @param fullScreenSubject the full-screen observable subject.
     * @param exhibitionSubject the exhibition observable subject.
     * @param presentationSubject the presentation observable subject.
     * @param toolbarSubject the toolbar observable subject.
     */
    public MenubarVisual(
        final Stage stage,
        final FullScreenSubject fullScreenSubject,
        final ExhibitionSubject exhibitionSubject,
        final PresentationSubject presentationSubject,
        final ToolbarSubject toolbarSubject
    ) {
        this.owner = stage;

        this.aboutVisual = new AboutVisual();

        this.fullScreenSubject = fullScreenSubject;
        this.exhibitionSubject = exhibitionSubject;
        this.presentationSubject = presentationSubject;
        this.behavior = new MenubarBehavior(
            this, fullScreenSubject, exhibitionSubject, presentationSubject, toolbarSubject
        );
        this.time = 8.0;

        this.initGraphics();
        this.registerListeners();
    }

    @Override
    public final void enableSlideShowMenu() {
        this.startSlideShow.setDisable(false);
    }

    @Override
    public final void showEnterFullScreenMenu() {
        this.viewMenu.getItems().remove(MENU_SCREEN_INDEX);
        this.viewMenu.getItems().add(MENU_SCREEN_INDEX, this.enterFullScreen);
    }

    @Override
    public final void showExitFullScreenMenu() {
        this.viewMenu.getItems().remove(MENU_SCREEN_INDEX);
        this.viewMenu.getItems().add(MENU_SCREEN_INDEX, this.exitFullScreen);
    }

    @Override
    public final void showStartSlideShowMenu() {
        this.viewMenu.getItems().remove(MENU_SLIDE_INDEX);
        this.viewMenu.getItems().add(MENU_SLIDE_INDEX, this.startSlideShow);
    }

    @Override
    public final void showStopSlideShowMenu() {
        this.viewMenu.getItems().remove(MENU_SLIDE_INDEX);
        this.viewMenu.getItems().add(MENU_SLIDE_INDEX, this.stopSlideShow);
    }

    @Override
    public final void updateActiveLanguage(final String lang) {
        switch (lang) {
            case "EN":
                this.english.setSelected(true);
                this.indonesia.setSelected(false);
                break;
            case "ID":
                this.english.setSelected(false);
                this.indonesia.setSelected(true);
                break;
            default:
                break;
        }
    }

    @Override
    public final void updateToolbarVisibility(final boolean val) {
        this.toolBarCheckMenu.setSelected(val);
    }

    @Override
    public final void showSettingDialog() {
        var choices = Arrays.asList(
            1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D
        );
        var dialog = new ChoiceDialog<Double>(
            this.time,
            choices
        );
        dialog.setTitle("Setting Options");
        dialog.setHeaderText("Options");
        dialog.setContentText("Slide Show Duration: ");
        var result = dialog.showAndWait();
        if (result.isPresent()) {
            this.time = result.get().doubleValue();
            this.presentationSubject.time(this.time);
        }
    }

    @Override
    public final void showAboutDialog() {
        this.aboutVisual.showAbout();
    }

    @Override
    public final boolean isToolbarVisible() {
        return this.toolBarCheckMenu.isSelected();
    }

    @Override
    public final Gallery gallery() {
        return this.exhibitionSubject.galleryProperty().getValue();
    }

    private void initGraphics() {
        if (AppPlatform.IS_MAC) {
            this.useSystemMenuBar();
            this.removeExitMenu();
        }

        this.fileMenu.textProperty().bind(I18N.INSTANCE.bind("menu.title.file"));
        this.editMenu.textProperty().bind(I18N.INSTANCE.bind("menu.title.edit"));
        this.viewMenu.textProperty().bind(I18N.INSTANCE.bind("menu.title.view"));
        this.settingMenu.textProperty().bind(I18N.INSTANCE.bind("menu.title.settings"));
        this.helpMenu.textProperty().bind(I18N.INSTANCE.bind("menu.title.help"));

        this.initFileMenuGraphics();
        this.initEditMenuGraphics();
        this.initViewMenuGraphics();
        this.initSettingMenuGraphics();
        this.initHelpMenuGraphics();

        this.getMenus().setAll(
            this.fileMenu,
            this.editMenu,
            this.viewMenu,
            this.settingMenu,
            this.helpMenu
        );
    }

    private void useSystemMenuBar() {
        this.setUseSystemMenuBar(true);
        this.useSystemMenuBarProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                if (!newVal) {
                    setUseSystemMenuBar(true);
                }
            }
        });
    }

    private void removeExitMenu() {
        Platform.runLater(() -> this.fileMenu.getItems().remove(this.exit));
    }

    private void initFileMenuGraphics() {
        this.openPicture.textProperty().bind(
            I18N.INSTANCE.bind("menu.title.file.open")
        );
        this.openDirectory.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.file.openDirectory"));
        this.exit.textProperty().bind(I18N.INSTANCE.bind("menu.title.file.exit"));

        this.fileMenu.getItems().setAll(
            this.openPicture,
            this.openDirectory,
            this.exit
        );
    }

    private void initEditMenuGraphics() {
        this.next.textProperty().bind(I18N.INSTANCE.bind("menu.title.edit.nextPicture"));
        this.next.setDisable(true);
        this.prev.textProperty().bind(I18N.INSTANCE.bind("menu.title.edit.prevPicture"));
        this.prev.setDisable(true);
        this.toFirst.textProperty().bind(I18N.INSTANCE.bind("menu.title.edit.toFirst"));
        this.toFirst.setDisable(true);
        this.toLast.textProperty().bind(I18N.INSTANCE.bind("menu.title.edit.toLast"));
        this.toLast.setDisable(true);

        this.editMenu.getItems().setAll(
            this.prev,
            this.next,
            new SeparatorMenuItem(),
            this.toFirst,
            this.toLast
        );
    }

    private void initViewMenuGraphics() {
        this.toolBarCheckMenu.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.view.toolbar"));
        this.enterFullScreen.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.view.enterFullScreen"));
        this.exitFullScreen.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.view.exitFullScreen"));
        this.startSlideShow.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.view.startSlideShow"));
        this.startSlideShow.setDisable(true);
        this.stopSlideShow.textProperty()
            .bind(I18N.INSTANCE.bind("menu.title.view.stopSlideShow"));

        this.viewMenu.getItems().setAll(
            this.toolBarCheckMenu,
            new SeparatorMenuItem(),
            this.enterFullScreen,
            this.startSlideShow
        );
    }

    private void initSettingMenuGraphics() {
        this.language.textProperty().bind(I18N.INSTANCE.bind("menu.title.settings.language"));
        this.option.textProperty().bind(I18N.INSTANCE.bind("menu.title.settings.options"));
        this.english.textProperty().bind(
            I18N.INSTANCE.bind("menu.title.settings.language.english")
        );
        this.indonesia.textProperty().bind(
            I18N.INSTANCE.bind("menu.title.settings.language.bahasa")
        );

        this.language.getItems().setAll(
            this.english,
            this.indonesia
        );

        this.settingMenu.getItems().setAll(
            this.language,
            this.option
        );
    }

    private void initHelpMenuGraphics() {
        this.about.textProperty().bind(I18N.INSTANCE.bind("menu.title.help.about"));

        this.helpMenu.getItems().setAll(this.about);
    }

    private void registerListeners() {
        this.behavior.initView();

        this.registerFileMenuListeners();
        this.registerEditMenuListeners();
        this.registerViewMenuListeners();
        this.registerSettingMenuListeners();
        this.registerHelpMenuListeners();

        this.fullScreenSubject.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onFullScreenControlChange();
            }
        });
        this.presentationSubject.runningProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                behavior.onPresentationControlChange();
            }
        });
        this.exhibitionSubject.galleryProperty().addListener(new ChangeListener<GallerySubject>() {
            @Override
            public void changed(
                final ObservableValue<? extends GallerySubject> observable,
                final GallerySubject oldGallery,
                final GallerySubject newGallery
            ) {
                behavior.onPictureGalleryChange();
                presentationSubject.gallery(newGallery);
                bindGalleryNavigationControl(newGallery);
            }
        });
    }

    private void bindGalleryNavigationControl(final GallerySubject subject) {
        this.next.disableProperty().unbind();
        this.prev.disableProperty().unbind();
        this.toFirst.disableProperty().unbind();
        this.toLast.disableProperty().unbind();

        this.toFirst.disableProperty().bind(subject.firstProperty());
        this.toLast.disableProperty().bind(subject.lastProperty());
        this.next.disableProperty().bind(subject.lastProperty());
        this.prev.disableProperty().bind(subject.firstProperty());
    }

    private void registerFileMenuListeners() {
        this.openPicture.setAccelerator(new KeyCodeCombination(KeyCode.O, MODIFIER));
        this.openPicture.setOnAction(this.openPictureAction);
        this.openDirectory.setAccelerator(new KeyCodeCombination(KeyCode.D, MODIFIER));
        this.openDirectory.setOnAction(this.openFolderAction);
        this.exit.setAccelerator(new KeyCodeCombination(KeyCode.X, MODIFIER));
        this.exit.setOnAction(event -> Platform.exit());
    }

    private void registerEditMenuListeners() {
        this.next.setOnAction(this.nextAction);
        this.prev.setOnAction(this.previousAction);
        this.toFirst.setOnAction(this.firstAction);
        this.toLast.setOnAction(this.lastAction);
    }

    private void registerViewMenuListeners() {
        this.toolBarCheckMenu.selectedProperty().addListener(this.toolbarListen);
        this.enterFullScreen.setOnAction(this.fullScreenAction);
        this.exitFullScreen.setOnAction(this.exitFullScreenAction);
        this.startSlideShow.setOnAction(this.startSlideAction);
        this.stopSlideShow.setOnAction(this.stopSlideAction);
    }

    private void registerSettingMenuListeners() {
        this.english.disableProperty().bind(this.indonesia.selectedProperty().not());
        this.indonesia.disableProperty().bind(this.english.selectedProperty().not());
        this.english.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                if (newVal) {
                    behavior.onLanguageChange("EN");
                }
            }
        });
        this.indonesia.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(
                final ObservableValue<? extends Boolean> observable,
                final Boolean oldVal,
                final Boolean newVal
            ) {
                if (newVal) {
                    behavior.onLanguageChange("ID");
                }
            }
        });
        this.option.setOnAction(event -> this.showSettingDialog());
    }

    private void registerHelpMenuListeners() {
        this.about.setOnAction(event -> this.showAboutDialog());
    }
}
