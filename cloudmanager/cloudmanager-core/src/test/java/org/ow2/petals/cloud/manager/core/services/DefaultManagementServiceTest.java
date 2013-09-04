package org.ow2.petals.cloud.manager.core.services; /**
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class DefaultManagementServiceTest {


    @Test
    public void testOrderNodes() {
        DefaultManagementService service = new DefaultManagementService(null, null);
        Deployment descriptor = new Deployment();

        Node a = new Node();
        a.setName("a");
        NodeHelper.setPriority(a, 1);
        Node b = new Node();
        b.setName("b");
        NodeHelper.setPriority(b, 2);
        Node c = new Node();
        c.setName("c");
        NodeHelper.setPriority(c, 3);

        descriptor.getNodes().add(a);
        descriptor.getNodes().add(b);
        descriptor.getNodes().add(c);

        List<Node> ordered = service.getOrderedNodes(descriptor.getNodes());

        assertEquals(3, ordered.size());
        assertEquals(c.getName(), ordered.get(0).getName());
        assertEquals(b.getName(), ordered.get(1).getName());
        assertEquals(a.getName(), ordered.get(2).getName());
    }

    @Test
    public void testOrderWithNullProperties() {
        DefaultManagementService service = new DefaultManagementService(null, null);
        Deployment descriptor = new Deployment();

        Node a = new Node();
        a.setName("a");
        Node b = new Node();
        b.setName("b");
        NodeHelper.setPriority(b, 2);
        Node c = new Node();
        c.setName("c");
        NodeHelper.setPriority(c, 3);

        descriptor.getNodes().add(a);
        descriptor.getNodes().add(b);
        descriptor.getNodes().add(c);

        List<Node> ordered = service.getOrderedNodes(descriptor.getNodes());

        assertEquals(3, ordered.size());
        assertEquals(c.getName(), ordered.get(0).getName());
        assertEquals(b.getName(), ordered.get(1).getName());
        assertEquals(a.getName(), ordered.get(2).getName());

    }
}
