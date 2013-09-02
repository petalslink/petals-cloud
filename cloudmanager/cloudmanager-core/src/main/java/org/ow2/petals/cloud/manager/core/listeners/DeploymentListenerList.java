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

import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.List;

/**
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DeploymentListenerList implements DeploymentListener {

    private List<DeploymentListener> listeners;

    public DeploymentListenerList(List<DeploymentListener> listeners) {
        this.listeners = listeners;
    }

    public void on(String id, String step, String pattern, String... args) {
        for(DeploymentListener listener : listeners) {
            listener.on(id, step, pattern, args);
        }
    }
}
