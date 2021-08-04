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

package com.swardana.nayanika.gui.about;

import com.swardana.nayanika.AppPlatform;
import com.swardana.nayanika.I18N;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutVisual extends VBox implements AboutView {

    private static final int ABOUT_WIDTH = 400;
    private static final int ABOUT_HEIGHT = 220;
    private static final int LICENSE_WIDTH = 400;
    private static final int LICENSE_HEIGHT = 320;

    private final Stage stage;
    private final Text infoTxt;
    private final Text licenseTxt;

    private final AboutBehavior behavior;

    private final StringBuilder about;
    private final StringBuilder license;


    /**
     * Creates new AboutVisual.
     */
    public AboutVisual() {
        this.stage = new Stage();
        this.infoTxt = new Text();
        this.licenseTxt = new Text();

        this.about = new StringBuilder();
        this.license = new StringBuilder();

        this.behavior = new AboutBehavior(this);

        this.initGraphics();
    }

    @Override
    public final void about(final String text) {
        this.about.append(text);
    }

    @Override
    public final void license(final String text) {
        this.license.append(text);
    }

    @Override
    public void showAbout() {
        var scene = new Scene(this);
        scene.getStylesheets().add(AppPlatform.CSS_STYLE);

        this.stage.setScene(scene);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }

    @Override
    public final void showLicense() {
        var licenseStage = new Stage();

        var container = new VBox();
        container.setPrefWidth(LICENSE_WIDTH);
        container.setPrefHeight(LICENSE_HEIGHT);
        container.setSpacing(10);
        container.setPadding(new Insets(10));

        var body = new StackPane();
        body.getStyleClass().add("content");
        body.getChildren().add(this.licenseTxt);

        var okBtn = new Button();
        okBtn.textProperty().bind(I18N.INSTANCE.bind("label.ok"));
        okBtn.setOnAction(event -> licenseStage.close());

        var footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.getChildren().setAll(okBtn);

        container.getChildren().setAll(body, footer);

        var scene = new Scene(container);
        scene.getStylesheets().add(AppPlatform.CSS_STYLE);

        licenseStage.setX(this.stage.getX() + (this.stage.getWidth() - scene.getWidth()) / 2);
        licenseStage.setY(this.stage.getY() + (this.stage.getHeight() - scene.getHeight()) / 2);
        licenseStage.initModality(Modality.APPLICATION_MODAL);
        licenseStage.setScene(scene);
        licenseStage.show();
    }

    private void initGraphics() {
        this.behavior.initView();

        this.getStyleClass().add("about");
        this.setPrefWidth(ABOUT_WIDTH);
        this.setPrefHeight(ABOUT_HEIGHT);

        this.infoTxt.setText(this.about.toString());
        this.licenseTxt.setText(this.license.toString());

        this.initAboutGraphics();
    }

    private void initAboutGraphics() {
        var body = new HBox();
        body.getStyleClass().add("content");
        body.getChildren().add(this.infoTxt);

        var footer = new HBox();
        var separator = new Pane();
        HBox.setHgrow(separator, Priority.ALWAYS);

        var viewLicenseBtn = new Button();
        viewLicenseBtn.textProperty().bind(I18N.INSTANCE.bind("label.viewLicense"));
        viewLicenseBtn.setOnAction(event -> this.showLicense());

        var closeBtn = new Button();
        closeBtn.textProperty().bind(I18N.INSTANCE.bind("label.close"));
        closeBtn.setOnAction(event -> this.stage.close());

        footer.getChildren().setAll(viewLicenseBtn, separator, closeBtn);

        this.getChildren().setAll(body, footer);
    }

}
