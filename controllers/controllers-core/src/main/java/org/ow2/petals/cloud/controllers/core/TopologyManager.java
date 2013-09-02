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
package org.ow2.petals.cloud.controllers.core;

import com.google.common.collect.Lists;
import org.ow2.petals.cloud.controllers.api.notification.NotificationListener;
import org.ow2.petals.cloud.controllers.api.runtime.*;
import org.ow2.petals.cloud.controllers.api.runtime.Runtime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class TopologyManager implements org.ow2.petals.cloud.controllers.api.runtime.TopologyManager {

    private static Logger LOG = LoggerFactory.getLogger(org.ow2.petals.cloud.controllers.api.runtime.TopologyManager.class);

    private Topology topology;

    private List<NotificationListener> topologyListeners;

    public TopologyManager() {
        topologyListeners = Lists.newArrayList();
    }

    /**
     * Add node to the topology
     *
     * @param runtime
     */
    public void addNode(org.ow2.petals.cloud.controllers.api.runtime.Runtime runtime) {
        if (runtime == null) {
            LOG.warn("Can not add a null runtime to the topology");
            return;
        }

        LOG.debug("Adding node to the topology {}", runtime);

        Iterator<NotificationListener> iter = topologyListeners.iterator();
        while(iter.hasNext()) {
            NotificationListener listener = iter.next();
            // TODO : Do it async...
            // TODO : Generics...
            listener.on("add", runtime);
        }
    }

    /**
     * Remve node from the topology
     *
     * @param runtime
     */
    public void removeNode(Runtime runtime) {
        if (runtime == null) {
            LOG.debug("Can not remove null node");
            return;
        }

        Iterator<NotificationListener> iter = topologyListeners.iterator();
        while(iter.hasNext()) {
            NotificationListener listener = iter.next();
            // TODO : Do it async...
            // TODO : Generics...
            listener.on("remove", runtime);
        }
    }


    public void addListener(NotificationListener notificationListener) {
        if (notificationListener == null) {
            return;
        }
        this.topologyListeners.add(notificationListener);
    }

    /**
     * Get the current topology
     *
     * @return
     */
    public Topology getTopology() {
        return new Topology(Lists.newCopyOnWriteArrayList(topology.getRuntimes()));
    }

}
