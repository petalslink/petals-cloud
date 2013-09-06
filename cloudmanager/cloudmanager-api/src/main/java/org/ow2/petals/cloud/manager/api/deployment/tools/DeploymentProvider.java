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
package org.ow2.petals.cloud.manager.api.deployment.tools;

import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.List;
import java.util.Map;

/**
 * Deployment Providers are custom hooks to be used by providers in order to enrich initial deployment descriptor.
 * For example, a provider may be able to add some configuration files, some scripts, add nodes, update VM properties...
 *
 * The Deployment Provider is retrieved at deployment time and #populate automatically is called before the first call to the provider API.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface DeploymentProvider {

    /**
     * Populate the deployment descriptor with some custom properties. The provider may create nodes, modify them, etc...
     * This will be called before the metadata related methods below.
     *
     * @param deployment initial deployment to populate
     * @param args variables coming from the deployment manager. Modifying these values may not affect anything.
     *
     * @return
     */
    void populate(Deployment deployment, Map<String, String> args);

    /**
     * Get a list of metadata which will be added to the deployment descriptor by the cloud manager and injected into the VM at boot time.
     * This is called after the populate method.
     *
     * @param deployment
     * @return the list of metadata to add or null if none
     */
    List<Property> getMetadata(Deployment deployment);

    /**
     * Get a list of metadata for the given node which MUST belong to the deployment descriptor.
     * If it does nto belong to it, the framework will not ensure that this node will be deployed...
     * Note that modifying the node properties in this method will not affect the original node.
     * This method is called after the populate method.
     *
     * @param deployment
     * @param node
     * @return the list of metadata to add to the node or null if none
     */
    List<Property> getMetadata(Deployment deployment, Node node);

    /**
     * Get the list of deployment listeners to notify on deployment actions.
     *
     * @return
     */
    List<DeploymentListener> getDeploymentListeners();

    /**
     * DeploymentProvider type
     *
     * @return
     */
    String getType();
}
