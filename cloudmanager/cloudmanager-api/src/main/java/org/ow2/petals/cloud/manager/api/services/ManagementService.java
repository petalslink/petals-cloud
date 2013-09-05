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
package org.ow2.petals.cloud.manager.api.services;

import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.List;

/**
 * Manage the Petals PaaS. Deployments are managed in the DeploymentService.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ManagementService {

    /**
     * Create a new PaaS from the deployment descriptor
     *
     * @param deploymentDescriptor the descriptor contains all the required information to deploy and configure the PaaS
     * @param deploymentListener a listener to receive deployment progress
     *
     * @return the created PaaS
     * @throws org.ow2.petals.cloud.manager.api.CloudManagerException
     */
    PaaS create(Deployment deploymentDescriptor, DeploymentListener deploymentListener) throws CloudManagerException;

    /**
     * Get all the PaaS
     *
     * @return
     * @throws CloudManagerException
     */
    List<PaaS> list() throws CloudManagerException;

    /**
     * Get a PaaS from its ID
     *
     * @param id
     * @return the PaaS or null if not found
     *
     * @throws CloudManagerException
     */
    PaaS get(String id) throws CloudManagerException;

    /**
     * Delete a PaaS from its ID
     *
     * @param id
     * @return
     * @throws CloudManagerException
     */
    boolean delete(String id, DeploymentListener deploymentListener) throws CloudManagerException;

}
