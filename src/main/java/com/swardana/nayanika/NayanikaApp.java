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

package com.swardana.nayanika;

import com.swardana.nayanika.base.BuildVersion;
import com.swardana.nayanika.base.ThreadExecutor;
import com.swardana.nayanika.gui.AppVisual;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The bootstrap class for Nayanika application.
 *
 * @author Sukma Wardana
 */
public class NayanikaApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        var title = I18N.INSTANCE.message("this.name")
            + "-"
            + BuildVersion.getInstance().buildVersion();
        var app = new AppVisual(stage);
        var scene = new Scene(app, 600, 480);
        scene.getStylesheets().add(AppPlatform.CSS_STYLE);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().add(new Image(AppPlatform.ICON));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ThreadExecutor.getInstance().shutdown();
    }

}
