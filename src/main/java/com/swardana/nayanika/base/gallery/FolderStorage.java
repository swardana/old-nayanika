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

package com.swardana.nayanika.base.gallery;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A computer folder.
 * <p>
 *     Find pictures inside the computer folder.
 * </p>
 *
 * @author Sukma Wardana
 */
public class FolderStorage implements Storage {

    private static final Logger LOGGER = Logger.getLogger(FolderStorage.class.getName());

    private final Path directory;

    /**
     * Creates new FolderStorage.
     *
     * @param dir the folder directory.
     */
    public FolderStorage(final Path dir) {
        this.directory = dir;
    }

    @Override
    public final List<Picture> search(final SupportedPicture filter) {
        final DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(this.directory, filter.pattern());
            LOGGER.log(
                Level.FINER,
                "Opened the directory stream. [dir={0}]",
                new Object[]{this.directory.toString()}
            );
        } catch (final IOException ex) {
            throw new RuntimeException("Search pictures in directory is fail.", ex);
        }
        try {
            LOGGER.log(
                Level.FINE,
                "Search pictures in directory. [dir={0}]",
                new Object[]{this.directory.toString()}
            );
            var pictures = new ArrayList<Picture>();
            for (final var path : stream) {
                pictures.add(
                    new StaticPicture(
                        path.getFileName().toString(),
                        path.toFile()
                    )
                );
                LOGGER.log(
                    Level.FINE,
                    "Add picture into collection. [picture={0}]",
                    new Object[]{path.toString()}
                );
            }
            LOGGER.log(
                Level.INFO,
                "Pictures from directory successfully add to collections. [dir={0}, picSize={1}]",
                new Object[]{this.directory.toString(), pictures.size()}
            );
            return pictures;
        } finally {
            try {
                stream.close();
                LOGGER.log(Level.FINER, "Closed the directory stream.");
            } catch (final IOException ex) {
                LOGGER.log(
                    Level.WARNING,
                    "Something wrong when close the directory stream!",
                    ex.getMessage()
                );
            }
        }
    }

}
