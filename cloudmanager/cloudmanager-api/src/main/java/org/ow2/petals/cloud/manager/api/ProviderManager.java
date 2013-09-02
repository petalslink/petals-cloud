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
package org.ow2.petals.cloud.manager.api;

import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import java.util.List;

/**
 * Interface to communicate with compute (IaaS) providers such as OpenStack, EC2, CloudStack, ...
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ProviderManager {

    /**
     * Get the node manager provider name (openstack, ec2, ...)
     * @return
     */
    String getProviderName();

    /**
     * The provider version
     *
     * @return
     */
    String getProviderVersion();

    /**
     * Creates a new node on the provider. Creating a node does not necessary means that the node will be up and running after this call.
     * Up to the implementation to start it if necessary.
     *
     * @return the created node
     */
    Node createNode(Provider provider, Node node) throws CloudManagerException;

    /**
     * Get all the nodes which are managed by the provider
     *
     * @return
     */
    List<Node> getNodes(Provider provider) throws CloudManagerException;

    /**
     * Get a node from the infrastructure
     *
     * @param id the node ID in the infrastructure
     * @return
     * @throws CloudManagerException
     */
    Node getNode(Provider provider, String id) throws CloudManagerException;

    /**
     * Delete/Kills a node from the IaaS
     *
     * @param node
     * @return
     */
    boolean deleteNode(Provider provider, Node node) throws CloudManagerException;

}
