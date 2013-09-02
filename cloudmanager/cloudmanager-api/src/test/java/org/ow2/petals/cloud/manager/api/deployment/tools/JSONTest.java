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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Software;

import java.io.InputStreamReader;
import java.io.StringWriter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class JSONTest {

    @Test
    public void testReadFromResource() {
        InputStreamReader reader = new InputStreamReader(JSONTest.class.getResourceAsStream("/deployment.sample.json"));
        Deployment descriptor = JSON.read(reader);

        assertNotNull(descriptor);
        assertEquals("sample", descriptor.getName());
        assertEquals("This is a distributed deployment sample", descriptor.getDescription());
        assertEquals(2, descriptor.getProviders().size());
        assertNotNull(descriptor.getVm());
        assertEquals(2, descriptor.getNodes().size());
        assertEquals(2, descriptor.getSoftwares().size());

        // get node A

        Node node = Iterables.tryFind(descriptor.getNodes(), new Predicate<Node>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Node input) {
                return input.getName().equals("A");
            }
        }).orNull();

        assertNotNull(node);

        assertEquals(2, node.getPrivateIpAddress().size());
        assertEquals(1, node.getPublicIpAddress().size());
    }

    @Test
    public void testWrite() {
        StringWriter writer = new StringWriter();
        Deployment descriptor = new Deployment();
        descriptor.setName("foo");
        descriptor.setDescription("bar");
        Software soft = new Software();
        soft.setName("petals");
        soft.setType("url");
        soft.setSource("http://petals.ow2.org/downloads/d.zip");
        descriptor.getSoftwares().add(soft);

        JSON.write(descriptor, writer);

        String out = writer.toString();
        System.out.println(out);

        assertTrue(out.contains("http://petals.ow2.org/downloads/d.zip"));
        assertTrue(out.contains("petals"));

        // TODO : Find a JSON comparator
        String expected = "{\"name\":\"foo\",\"description\":\"bar\",\"providers\":[],\"softwares\":[{\"name\":\"petals\",\"type\":\"url\",\"source\":\"http://petals.ow2.org/downloads/d.zip\"}],\"nodes\":[]}";
        assertEquals(expected, out);
    }

}
