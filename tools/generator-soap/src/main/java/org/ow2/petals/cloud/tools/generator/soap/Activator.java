/****************************************************************************
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
 *****************************************************************************/
package org.ow2.petals.cloud.tools.generator.soap;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Hashtable;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Activator implements BundleActivator {

    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);

    private BundleContext context;

    private File folder;

    public void start(BundleContext context) throws Exception {
        LOG.info("Starting the {} generator", SOAPArtifactGenerator.class.getName());
        this.context = context;

        // TODO
        folder = File.createTempFile("petalcontroller", "tmp").getParentFile();

        SOAPArtifactGenerator generator = new SOAPArtifactGenerator(folder);
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("name", generator.getName());
        props.put("mode", generator.getMode());
        props.put("protocol", generator.getProtocol());
        context.registerService(SOAPArtifactGenerator.class.getName(), generator, props);
    }

    public void stop(BundleContext context) throws Exception {
        LOG.info("Stopping the {}", SOAPArtifactGenerator.class.getName());

        ServiceReference ref = context.getServiceReference(SOAPArtifactGenerator.class.getName());
        if (ref != null) {
            Object o = context.getService(ref);
            if (o != null && o instanceof SOAPArtifactGenerator) {
                ((SOAPArtifactGenerator)o).clean();
            }
        }
    }
}
