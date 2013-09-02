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
package org.ow2.petals.cloud.controllers.api.services;

import org.ow2.petals.cloud.controllers.api.ControllerException;
import org.ow2.petals.cloud.controllers.api.notification.DeploymentListener;
import org.ow2.petals.cloud.controllers.api.runtime.PetalsRuntime;
import org.ow2.petals.cloud.controllers.api.runtime.Topology;
import org.ow2.petals.cloud.controllers.api.runtime.VirtualContainer;

/**
 * Let's manage PaaS deployments!
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface DeploymentService {

    /**
     * Deploy a a new topology from scratch.
     *
     * @param topology the topology to deploy
     * @param deploymentListener listen to deployment steps
     *
     * @return the created virtual container
     * @throws org.ow2.petals.cloud.controllers.api.ControllerException
     */
    VirtualContainer deploy(Topology topology, DeploymentListener deploymentListener) throws ControllerException;

    /**
     * Add a nodes to an existing virtual container
     *
     * @param id the PaaS to add nodes to
     * @param nodes the nodes to add
     * @return
     * @throws ControllerException if something bad occurs...
     * For example, if the topology does not allow to add node with special mode, it will raise an exception...
     */
    VirtualContainer addNodes(String id, DeploymentListener deploymentListener, PetalsRuntime... nodes) throws ControllerException;

}
