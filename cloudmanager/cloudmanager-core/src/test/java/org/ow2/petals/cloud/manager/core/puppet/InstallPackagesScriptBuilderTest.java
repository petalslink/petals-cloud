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
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Software;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class InstallPackagesScriptBuilderTest {

    @Test
    public void testGetPackages() throws CloudManagerException {
        InstallPackagesScriptBuilder builder = new InstallPackagesScriptBuilder();
        Node node = new Node();
        node.setSoftwares(Lists.newArrayList("curl", "tomcat"));

        Context context = new Context(UUID.randomUUID().toString());
        Deployment descriptor = new Deployment();
        Software curl = new Software();
        curl.setType(Constants.PACKAGE_TYPE);
        curl.setName("curl");
        curl.setVersion("1.0");
        descriptor.getSoftwares().add(curl);

        Software tomcat = new Software();
        tomcat.setType(Constants.PACKAGE_TYPE);
        tomcat.setName("tomcat");
        tomcat.setVersion("1.0");
        descriptor.getSoftwares().add(tomcat);

        Software mongo = new Software();
        mongo.setType(Constants.FILE_TYPE);
        mongo.setName("mongo");
        mongo.setVersion("1.0");
        descriptor.getSoftwares().add(mongo);

        context.setDescriptor(descriptor);

        List<String> list = builder.getPackages(node, context);
        System.out.println(list);
        assertEquals(2, list.size());
    }

    @Test
    public void testGenerateScript() throws CloudManagerException {
        InstallPackagesScriptBuilder builder = new InstallPackagesScriptBuilder();
        Node node = new Node();
        node.setSoftwares(Lists.newArrayList("curl", "tomcat"));

        Context context = new Context(UUID.randomUUID().toString());
        Deployment descriptor = new Deployment();
        Software curl = new Software();
        curl.setType(Constants.PACKAGE_TYPE);
        curl.setName("curl");
        curl.setVersion("1.0");
        descriptor.getSoftwares().add(curl);

        Software tomcat = new Software();
        tomcat.setType(Constants.PACKAGE_TYPE);
        tomcat.setName("tomcat");
        tomcat.setVersion("1.0");
        descriptor.getSoftwares().add(tomcat);

        Software mongo = new Software();
        mongo.setType(Constants.FILE_TYPE);
        mongo.setName("mongo");
        mongo.setVersion("1.0");
        descriptor.getSoftwares().add(mongo);

        context.setDescriptor(descriptor);

        String out = builder.build(node, context);
        System.out.println(out);

        assertTrue(out.contains("package { \"tomcat\": }"));
        assertTrue(out.contains("package { \"curl\": }"));
        assertFalse(out.contains("package { \"mongo\": }"));
    }
}
