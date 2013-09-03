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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
        p1.setType("uri");
        p1.setValue("http://foo/bar.zip");
        Property p2 = new Property();
        p2.setName("foobar");
        p2.setType("uri");
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
        p1.setType("uri");
        p1.setValue("http://foo/bar.zip");
        Property p2 = new Property();
        p2.setName("foobar");
        p2.setType("uri");
        p2.setValue("http://foo/bar/baz.zip");
        Property p3 = new Property();
        p3.setName("foobarbaz");
        p3.setType("uri");
        p3.setValue("http://foo/bar/baz/buz.zip");
        node.setProperties(Lists.newArrayList(p1, p2, p3));
        String out = builder.build(node, new Context(UUID.randomUUID().toString()));
        assertNotNull(out);
        assertTrue(out.contains("http://foo/bar.zip") && out.contains("http://foo/bar/baz.zip") && out.contains("http://foo/bar/baz/buz.zip"));
        System.out.println(out);
    }

}



