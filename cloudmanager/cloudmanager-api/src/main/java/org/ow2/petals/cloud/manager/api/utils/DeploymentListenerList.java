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
package org.ow2.petals.cloud.manager.api.utils;

import com.google.common.collect.Lists;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DeploymentListenerList implements DeploymentListener {

    private List<DeploymentListener> listeners;

    public DeploymentListenerList() {
        this.listeners = Lists.newArrayList();
    }

    /**
     * @param listeners
     */
    public DeploymentListenerList(List<DeploymentListener> listeners) {
        if (listeners == null) {
            listeners = new ArrayList<DeploymentListener>();
        }
        this.listeners = listeners;
    }

    public void on(String id, Node node, String action, String step, String pattern, Object... args) {
        for(DeploymentListener listener : listeners) {
            listener.on(id, node, action, step, pattern, args);
        }
    }

    /**
     * Add a listener to the list
     *
     * @param listener
     */
    public void addListener(DeploymentListener listener) {
        if (listener != null && listeners != null) {
            listeners.add(listener);
        }
    }

    public void add(List<DeploymentListener> listeners) {
        if (listeners != null) {
            this.listeners.addAll(listeners);
        }
    }
}
