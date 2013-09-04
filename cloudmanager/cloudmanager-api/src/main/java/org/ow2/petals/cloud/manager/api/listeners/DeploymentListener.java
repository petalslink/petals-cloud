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
package org.ow2.petals.cloud.manager.api.listeners;

import org.ow2.petals.cloud.manager.api.deployment.Node;

/**
 * Deployment listener is used to track node deployment steps
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface DeploymentListener {

    /**
     * @param id the deployment ID (unique)
     * @param node node listening to
     * @param action : current action
     * @param step : current step in the action
     * @param pattern message to send
     * @param args
     */
    void on(String id, Node node, String action, String step, String pattern, Object... args);
}
