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

import com.swardana.nayanika.I18N;
import com.swardana.nayanika.base.FullScreen;
import com.swardana.nayanika.base.Presentation;
import com.swardana.nayanika.base.Toolbar;
import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.gui.menu.GalleryMenuBehavior;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A behavior of {@link MenubarView}.
 *
 * @author Sukma Wardana
 */
class MenubarBehavior extends GalleryMenuBehavior {

    private static final Logger LOGGER = Logger.getLogger(MenubarBehavior.class.getName());

    private final MenubarView view;
    private final Toolbar toolbar;

    /**
     * Creates new MenubarBehavior.
     *
     * @param view the toolbar view counter part.
     * @param fullScreen the app full-screen window.
     * @param exhibition the exhibition.
     * @param presentation the slide-show presentation.
     * @param toolbar the toolbar menu.
     */
    MenubarBehavior(
        final MenubarView view,
        final FullScreen fullScreen,
        final Exhibition exhibition,
        final Presentation presentation,
        final Toolbar toolbar
    ) {
        super(view, fullScreen, exhibition, presentation);
        this.view = view;
        this.toolbar = toolbar;
    }

    /**
     * Called by the view.
     * <p>
     *     Set up the initial data or configuration for app view.
     * </p>
     */
    final void initView() {
        this.view.updateActiveLanguage("EN");
        this.view.updateToolbarVisibility(true);
    }

    /**
     * Called by the view.
     * <p>
     *     Handle when the toolbar visibility menu state is changed.
     * </p>
     */
    final void onToolbarVisibilityChange() {
        if (this.view.isToolbarVisible()) {
            this.toolbar.showToolbar();
        } else {
            this.toolbar.hideToolbar();
        }
        LOGGER.log(
            Level.FINE,
            "The control toolbar state is changed. [toolbar={0}]",
            new Object[]{this.toolbar.isVisible()}
        );
        LOGGER.log(Level.INFO, "Successfully change the control toolbar state.");
    }

    /**
     * Called by the view.
     * <p>
     *     Operation to change the language.
     * </p>
     */
    final void onLanguageChange(final String lang) {
        switch (lang) {
            case "EN":
                I18N.INSTANCE.locale(Locale.ENGLISH);
                this.view.updateActiveLanguage(lang);
                break;
            case "ID":
                I18N.INSTANCE.locale(new Locale("in", "ID"));
                this.view.updateActiveLanguage(lang);
                break;
            default:
                break;
        }
    }

}
