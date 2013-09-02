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
package org.ow2.petals.cloud.manager.dsb;

import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.tools.DeploymentProvider;

import java.util.Map;

/**
 * Generate deployment descriptor for the Master Slave DSB Mode
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MasterSlaveDeploymentProvider implements DeploymentProvider {

    protected static final int DEFAULT_SIZE = 5;

    public void populate(Deployment descriptor, Map<String, String> args) {
        int size = (args.get("size") == null ? DEFAULT_SIZE : Integer.parseInt(args.get("size")));

        Node node = new Node();
        node.setName("master");
        node.getPorts().add(7700);
        node.getPorts().add(7701);
        descriptor.getNodes().add(node);

        for(int i=0; i< size - 1; i++) {
            Node slave = new Node();
            slave.setName("slave-" + i);
            slave.getPorts().add(7700);
            slave.getPorts().add(7701);
            descriptor.getNodes().add(slave);
        }
    }

    public String getType() {
        return "dsb-masterslave";
    }
}
