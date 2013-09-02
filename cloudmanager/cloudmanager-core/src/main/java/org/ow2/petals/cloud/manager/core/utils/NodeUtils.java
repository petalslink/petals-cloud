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
package org.ow2.petals.cloud.manager.core.utils;


import org.ow2.petals.cloud.manager.api.deployment.Node;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NodeUtils {

    /**
     * Wait for a node to start. If the node is not started within the given period, raise a TimeoutException.
     * @param node
     * @param time
     * @param unit
     */
    public static void waitNodeToStart(Node node, long time, TimeUnit unit) throws TimeoutException {
        boolean started = false;
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() < start + unit.toMillis(time)) {
            if (isStarted(node)) {
                return;
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
            }
        }

        if (!started) {
            throw new TimeoutException("Can not connect to the node within the given delay");
        }
    }

    public static boolean isStarted(Node node) {
        return false;
        // TODO
    }
}
