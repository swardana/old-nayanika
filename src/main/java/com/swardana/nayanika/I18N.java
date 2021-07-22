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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * Internationalization bundle resource message.
 * <p>
 *     A singleton for internationalization resource bundle.
 * </p>
 *
 * @author Sukma Wardana
 */
public enum I18N {

    INSTANCE;

    private final ObjectProperty<Locale> locale;

    I18N() {
        this.locale = new SimpleObjectProperty<>(this.defaultLocale());
        this.locale.addListener(new ChangeListener<Locale>() {
            @Override
            public void changed(
                final ObservableValue<? extends Locale> observable,
                final Locale oldValue,
                final Locale newValue
            ) {
                Locale.setDefault(newValue);
            }
        });
    }

    /**
     * The Locale I18N.
     *
     * @return the current {@link Locale} value.
     */
    public Locale locale() {
        return this.localeProperty().getValue();
    }

    /**
     * Replace the current Locale.
     *
     * @param lcl the new {@link Locale} value.
     */
    public void locale(final Locale lcl) {
        this.localeProperty().set(lcl);
    }

    /**
     * Localized formatted message.
     * <p>
     * Retrieve the string with the given key from the resource bundle for the
     * current locale and uses it as first argument to
     * {@code MessageFormat.format}, passing in the optional args and returning
     * the result.
     *
     * @param key the message key.
     * @param args the optional arguments for the message.
     * @return the localized formatted string.
     */
    public String message(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(
            System.getProperty(
                "resourcebundle.properties",
                "com/swardana/nayanika/i18n/NayanikaApp"
            ),
            this.locale()
        );
        return MessageFormat.format(bundle.getString(key), args);
    }

    /**
     * Creates a String binding to a localized String for the given message
     * bundle key.
     *
     * @param key the message key.
     * @param args the optional arguments for the message
     * @return the String binding.
     */
    public StringBinding bind(final String key, final Object... args) {
        return Bindings.createStringBinding(
            () -> message(key, args),
            this.localeProperty()
        );
    }

    /**
     * Creates a String Binding to a localized String that is computed by
     * calling the given func.
     *
     * @param func the function called on every change.
     * @return the String Binding.
     */
    public StringBinding bind(final Callable<String> func) {
        return Bindings.createStringBinding(func, this.localeProperty());
    }

    private List<Locale> supportedLocales() {
        return new ArrayList<>(
            Arrays.asList(
                Locale.ENGLISH,
                new Locale("in", "ID")
            )
        );
    }

    private Locale defaultLocale() {
        final Locale result;
        if (this.supportedLocales().contains(Locale.getDefault())) {
            result = Locale.getDefault();
        } else {
            result = Locale.ENGLISH;
        }
        return result;
    }

    private ObjectProperty<Locale> localeProperty() {
        return this.locale;
    }

}
