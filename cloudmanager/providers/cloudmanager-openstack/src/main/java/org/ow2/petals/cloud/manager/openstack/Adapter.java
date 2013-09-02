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
package org.ow2.petals.cloud.manager.openstack;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.woorea.openstack.nova.model.Server;
import org.ow2.petals.cloud.manager.api.deployment.Node;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Adapter implements Function<Server, Node> {

    public Node apply(com.woorea.openstack.nova.model.Server input) {
        Node node = new Node();
        node.setName(input.getName());
        node.setId(input.getId());
        node.setProvider("openstack");
        node.setPrivateIpAddress(Lists.newArrayList(input.getAccessIPv4()));
        // TODO : More
        return node;
    }

    public static final Node to(Server server) {
        return new Adapter().apply(server);
    }
}
