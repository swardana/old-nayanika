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

package com.swardana.nayanika.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A thread pool executor.
 * <p>
 *     A singleton for {@link ExecutorService} instance with cached
 *     thread pool.
 * </p>
 *
 * @author Sukma Wardana
 */
public final class ThreadExecutor {

    private static final Logger LOGGER = Logger.getLogger(ThreadExecutor.class.getName());

    private final ExecutorService executor = Executors.newCachedThreadPool(r -> {
        var thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    private ThreadExecutor(){}

    public static ThreadExecutor getInstance() {
        return ThreadExecutorHelper.INSTANCE;
    }

    public ExecutorService executor() {
        return this.executor;
    }

    public void shutdown() {
        LOGGER.log(Level.FINE, "Disable new tasks from being submitted.");
        this.executor.shutdown();

        try {
            LOGGER.log(Level.FINE, "Wait a while for existing tasks to terminate.");
            if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                LOGGER.log(Level.FINE, "Cancel currently executing tasks.");
                this.executor.shutdownNow();

                LOGGER.log(Level.FINE, "Wait a while for tasks to respond to being cancelled.");
                if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    LOGGER.log(Level.WARNING, "The executor service did not terminate.");
                }
            }
        } catch (final InterruptedException ex) {
            LOGGER.log(Level.FINE, "(Re-)Cancel if current thread also interrupted.");
            this.executor.shutdown();

            LOGGER.log(Level.FINE, "Preserve interrupt status.");
            Thread.currentThread().interrupt();
        }
    }

    private static class ThreadExecutorHelper {
        private static final ThreadExecutor INSTANCE = new ThreadExecutor();
    }

}
