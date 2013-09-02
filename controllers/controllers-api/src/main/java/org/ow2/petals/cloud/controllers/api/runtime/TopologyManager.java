/****************************************************************************
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
 *****************************************************************************/
package org.ow2.petals.cloud.controllers.api.runtime;

import org.ow2.petals.cloud.controllers.api.notification.NotificationListener;

/**
 * The topology manager is in charge of keeping the current node list in the controller domain.
 * It uses a notification listener list to inform listeners when something changed.
 *
 * TODO : This will be better to add some process here (Activiti?)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface TopologyManager {

    /**
     * Add a node to the topology
     *
     * @param runtime
     */
    void addNode(Runtime runtime);

    /**
     * Remove a node to the topology
     *
     * @param runtime
     */
    void removeNode(Runtime runtime);

    /**
     * Add a listener to the topology manager. This will be useful ;)
     * @param notificationListener
     */
    void addListener(NotificationListener notificationListener);

    /**
     * Get the current topology (not modifiable)
     *
     * @return the current topology
     */
    Topology getTopology();
}
