/**
 *
 * Copyright (c) 2013, Linagora
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 *
 */
package org.ow2.petals.cloud.manager.core.listeners;

import org.ow2.petals.cloud.manager.api.listeners.Monitor;

import java.util.logging.Logger;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class JULMonitor implements Monitor {

    public static final Logger logger = Logger.getLogger(JULMonitor.class.getName());

    public void start(String step) {
        logger.info(step + " : START");
    }

    public void end(String step) {
        logger.info(step + " : END");
    }

    public void time(String step, long time) {
        logger.info(step + " : TIME " + time + " ms");
    }

    public void error(String step, Exception e) {
        logger.info(step + " : ERROR " + e.getMessage());
    }
}
