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
package org.ow2.petals.cloud.controllers.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ow2.petals.cloud.controllers.api.artifact.ArtifactDeployer;
import org.ow2.petals.cloud.controllers.core.artifact.PetalsJMXArtifactDeployer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Activator implements BundleActivator {

    private BundleContext context;

    private static Logger LOG = LoggerFactory.getLogger(Activator.class);

    public void start(BundleContext context) throws Exception {
        this.context = context;
        LOG.info("Registering {}", PetalsJMXArtifactDeployer.class.getName());

        PetalsJMXArtifactDeployer deployer = new PetalsJMXArtifactDeployer();
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("target", "petals");
        props.put("protocol", "jmx");
        context.registerService(ArtifactDeployer.class.getName(), deployer, props);
    }

    public void stop(BundleContext context) throws Exception {
    }
}
