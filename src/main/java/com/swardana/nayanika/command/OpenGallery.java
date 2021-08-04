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

package com.swardana.nayanika.command;

import com.swardana.nayanika.base.gallery.AscendingSortedPictures;
import com.swardana.nayanika.base.gallery.Exhibition;
import com.swardana.nayanika.base.gallery.FolderStorage;
import com.swardana.nayanika.base.gallery.Picture;
import com.swardana.nayanika.base.gallery.StaticPicture;
import com.swardana.nayanika.base.gallery.SupportedPicture;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * An abstract command for opening picture gallery.
 * <p>
 *     The command to search pictures is done in asynchronous manner and the
 *     result is automatically sort in ascending order.
 * </p>
 *
 * @author Sukma Wardana
 */
public class OpenGallery {

    private final Exhibition exhibition;
    private final ExecutorService executor;

    /**
     * Creates new OpenGallery.
     *
     * @param exhibition the picture gallery exhibition.
     * @param executor the asynchronous task executor.
     */
    public OpenGallery(final Exhibition exhibition, final ExecutorService executor) {
        this.exhibition = exhibition;
        this.executor = executor;
    }

    /**
     * Execute picture gallery exhibition.
     * <p>
     *     This will perform {@code Exhibition.exhibits()} method.
     * </p>
     *
     * @param file the picture gallery file.
     * @param filter the applied supported picture format filter.
     */
    public void execute(final File file, final SupportedPicture filter) {
        var target = this.source(file);
        var pictures = this.ascendingPictures(target, filter);
        if (file.isFile()) {
            var picture = new StaticPicture(file.getName(), file);
            this.exhibition.exhibits(target.toString(), pictures, picture);
        } else {
            this.exhibition.exhibits(target.toString(), pictures);
        }
    }

    private Path source(final File file) {
        final Path result;
        if (file.isFile()) {
            result = Paths.get(file.getAbsoluteFile().getParent());
        } else {
            result = file.toPath();
        }
        return result;
    }

    /**
     * Search pictures on computer folder and sort in ascending order
     * asynchronously.
     *
     * @param src the directtory of computer folder.
     * @param filter the supportted picture filter.
     * @return the list of picture in ascending order.
     */
    private List<Picture> ascendingPictures(final Path src, final SupportedPicture filter) {
        return this.asyncPictures(src, filter)
            .thenCompose(this::asyncAscendingPictures)
            .join();
    }

    /**
     * Search picture on computer folder storage asynchronously.
     *
     * @param src the directory of computer folder.
     * @param filter the supported picture filter.
     * @return the list of pictures.
     */
    private CompletableFuture<List<Picture>> asyncPictures(
        final Path src,
        final SupportedPicture filter
    ) {
        return CompletableFuture.supplyAsync(new Supplier<List<Picture>>() {
            @Override
            public List<Picture> get() {
                var storage = new FolderStorage(src);
                return storage.search(filter);
            }
        }, this.executor);
    }

    /**
     * Asynchronously sorting picture in ascending order.
     *
     * @param pictures the list of pictures
     * @return the list of picture in ascending order.
     */
    private CompletableFuture<List<Picture>> asyncAscendingPictures(
        final List<Picture> pictures
    ) {
        return CompletableFuture.supplyAsync(new Supplier<List<Picture>>() {
            @Override
            public List<Picture> get() {
                return new AscendingSortedPictures(pictures).sorted();
            }
        }, this.executor);
    }

}
