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
package org.ow2.petals.cloud.manager.core.puppet;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class DownloadFilesScriptBuilderTest {

    @Test
    public void testGetURIs() {
        DownloadFilesScriptBuilder builder = new DownloadFilesScriptBuilder();
        Node node = new Node();
        Property p1 = new Property();
        p1.setName("foo");
        p1.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        p1.setValue("http://foo/bar.zip");
        Property p2 = new Property();
        p2.setName("foobar");
        p2.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        p2.setValue("http://foo/bar/baz.zip");
        Property p3 = new Property();
        p3.setName("foobarbaz");
        p3.setType("file");
        p3.setValue("file:///home/petals/baz.zip");
        node.setProperties(Lists.newArrayList(p1, p2, p3));

        List<Map<String, String>> uris = builder.getURIs(node, new Context(UUID.randomUUID().toString()));
        System.out.println(uris);
        assertEquals(2, uris.size());
    }

    @Test
    public void testGenerateNodeURLs() throws CloudManagerException {
        DownloadFilesScriptBuilder builder = new DownloadFilesScriptBuilder();
        Node node = new Node();
        Property p1 = new Property();
        p1.setName("foo");
        p1.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        p1.setValue("http://foo/bar.zip");
        Property p2 = new Property();
        p2.setName("foobar");
        p2.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        p2.setValue("http://foo/bar/baz.zip");
        Property p3 = new Property();
        p3.setName("foobarbaz");
        p3.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        p3.setValue("http://foo/bar/baz/buz.zip");
        node.setProperties(Lists.newArrayList(p1, p2, p3));
        String out = builder.build(node, new Context(UUID.randomUUID().toString()));
        assertNotNull(out);
        assertTrue(out.contains("http://foo/bar.zip") && out.contains("http://foo/bar/baz.zip") && out.contains("http://foo/bar/baz/buz.zip"));
        System.out.println(out);
    }

    @Test
    public void testDownloadDefinedSoftware() throws CloudManagerException {
        DownloadFilesScriptBuilder builder = new DownloadFilesScriptBuilder();
        Software s = new Software();
        s.setName("foo");
        s.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        s.setSource("http://foobar");

        Node node = new Node();
        node.getSoftwares().add(s.getName());

        Deployment descriptor = new Deployment();
        descriptor.getSoftwares().add(s);

        Context context = new Context(UUID.randomUUID().toString());
        context.setDescriptor(descriptor);

        String out = builder.build(node, context);
        assertNotNull(out);
        System.out.println(out);
        assertTrue(out.contains(s.getSource()));
    }

    @Test
    public void testDownloadPartialDefinedSoftware() throws CloudManagerException {
        DownloadFilesScriptBuilder builder = new DownloadFilesScriptBuilder();
        Software s = new Software();
        s.setName("foo");
        s.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.URL_TYPE);
        s.setSource("http://foobar");

        // define a software with package but http source will not be included (bad source)
        Software ss = new Software();
        ss.setName("bar");
        ss.setType(org.ow2.petals.cloud.manager.api.deployment.Constants.PACKAGE_TYPE);
        ss.setSource("http://foobarbaz");

        Node node = new Node();
        node.getSoftwares().add(s.getName());

        Deployment descriptor = new Deployment();
        descriptor.getSoftwares().add(s);
        descriptor.getSoftwares().add(ss);

        Context context = new Context(UUID.randomUUID().toString());
        context.setDescriptor(descriptor);

        String out = builder.build(node, context);
        assertNotNull(out);
        System.out.println(out);
        assertTrue(out.contains(s.getSource()));
        assertFalse(out.contains(ss.getSource()));

    }

}



