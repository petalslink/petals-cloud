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
package org.ow2.petals.cloud.manager.ec2;

import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import java.util.List;

/**
 * Provides access to Amazon EC2 (or any compliant API).
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class EC2ProviderManager implements ProviderManager {

    public EC2ProviderManager() {
    }

    public String getProviderName() {
        return "aws-ec2";
    }

    public String getProviderVersion() {
        return "1.0";
    }

    public Node createNode(Provider provider, Node node) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

    public List<Node> getNodes(Provider provider) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

    public Node getNode(Provider provider, String id) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

    public boolean deleteNode(Provider provider, Node node) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }
}
