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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * Unit test for {@link I18N}.
 *
 * @author Sukma Wardana
 */
public class I18NTest {

    @Test
    @DisplayName("Test switch to English")
    public void testSwitchLocaleToEnglish() {
        var expected = "English";
        I18N.INSTANCE.locale(Locale.ENGLISH);
        var actual = I18N.INSTANCE.message("this.language.name");
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test switch to Bahasa")
    public void testSwitchLocaleToBahasa() {
        var expected = "Bahasa";
        I18N.INSTANCE.locale(new Locale("id", "ID"));
        var actual = I18N.INSTANCE.message("this.language.name");
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

}