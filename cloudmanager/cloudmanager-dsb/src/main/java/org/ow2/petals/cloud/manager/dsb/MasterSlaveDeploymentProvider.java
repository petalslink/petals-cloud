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

import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Software;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper.setPriority;
import static org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper.setProperty;

/**
 * Generate deployment descriptor for the Master Slave DSB Mode.
 * In this type of deployment, master is set to a high level priority to be deployed and started first.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MasterSlaveDeploymentProvider extends DSBDeploymentProvider {

    private static Logger logger = LoggerFactory.getLogger(MasterSlaveDeploymentProvider.class);

    protected static final int DEFAULT_SIZE = 3;

    private List<DeploymentListener> listeners;

    /**
     *
     */
    public MasterSlaveDeploymentProvider(List<DeploymentListener> listeners) {
        this.listeners = listeners;
    }

    // TODO : Populate from config files...
    public void populate(Deployment descriptor, Map<String, String> args) {
        int size = (args.get("size") == null ? DEFAULT_SIZE : Integer.parseInt(args.get("size")));

        descriptor.setDescription("Petals DSB Master - Slave deployment");

        // we provision DSB from scratch!
        Software dsb = new Software();
        dsb.setName("petals-dsb");
        dsb.setType(Constants.URL_TYPE);
        dsb.setSource("http://central.maven.org/maven2/org/ow2/petals/dsb/distribution/dsb-distribution/1.0.0/dsb-distribution-1.0.0.zip");

        Software jdk = new Software();
        jdk.setType(Constants.PACKAGE_TYPE);
        jdk.setName("openjdk-6-jdk");

        Node node = new Node();
        node.setName("master");
        node.getSoftwares().add(dsb.getName());
        node.getSoftwares().add(jdk.getName());
        node.getPorts().add(7700);
        node.getPorts().add(7701);
        // master node have a high deployment priority
        setPriority(node, 1000);
        setPetalsProperties(node);
        descriptor.getNodes().add(node);

        for(int i=0; i< size - 1; i++) {
            Node slave = new Node();
            setPriority(node, 0);
            slave.setName("slave-" + i);
            slave.getSoftwares().add(dsb.getName());
            slave.getSoftwares().add(jdk.getName());
            slave.getPorts().add(7700);
            slave.getPorts().add(7701);
            setPetalsProperties(slave);
            descriptor.getNodes().add(slave);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("DSB provider descriptor result : " + descriptor);
        }

    }

    /**
     * Set all the relevant properties which will be used by the DSB runtime.
     * We use mustache-based expression for values which need to be generated.
     *
     * @param node
     */
    protected void setPetalsProperties(Node node) {
        setProperty(node, "petals.controller.endpoint", "url", getControllerEndpoint(node));
        setProperty(node, "petals.topology.url", "url", getTopologyURL(node));
        setProperty(node, "petals.container.id", "uuid", "{{petals.container.id}}");
        setProperty(node, "petals.virtual.id", "uuid", "{{petals.virtual.id}}");
    }

    /**
     * The the topology URL for the given node
     *
     * @param node
     * @return
     */
    protected String getTopologyURL(Node node) {
        // TODO : From context
        return "http://localhost:5555/topology/{{node.id}}";
    }

    /**
     * Get the controller endpoint for the given node
     *
     * @param node
     * @return
     */
    protected String getControllerEndpoint(Node node) {
        // TODO : From context
        return "http://localhost:5555/controller/{{node.id}}";
    }

    public List<DeploymentListener> getDeploymentListeners() {
        return listeners;
    }

    public String getType() {
        return "dsb-masterslave";
    }
}
