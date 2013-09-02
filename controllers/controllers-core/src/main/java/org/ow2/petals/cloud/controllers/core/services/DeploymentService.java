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
package org.ow2.petals.cloud.controllers.core.services;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ow2.petals.cloud.controllers.api.ControllerException;
import org.ow2.petals.cloud.controllers.api.notification.DeploymentListener;
import org.ow2.petals.cloud.controllers.api.runtime.PetalsRuntime;
import org.ow2.petals.cloud.controllers.api.runtime.Topology;
import org.ow2.petals.cloud.controllers.api.runtime.VirtualContainer;

import java.util.Set;
import java.util.UUID;

/**
 * Deployment service delegates the node deployment to the Cloud Manager
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DeploymentService implements org.ow2.petals.cloud.controllers.api.services.DeploymentService {

    public DeploymentService() {

    }

    public VirtualContainer addNodes(String id, DeploymentListener deploymentListener, PetalsRuntime... nodes) throws ControllerException {
        return null;
    }

    public VirtualContainer deploy(Topology topology, DeploymentListener deploymentListener) throws ControllerException {
        String id = UUID.randomUUID().toString();

        deploymentListener.on(id, "deploy.start", null);

        if (id == null || topology == null) {
            throw new ControllerException("Can not deploy null things...");
        }

        if (topology.getRuntimes() == null || topology.getRuntimes().size() == 0) {
            throw new ControllerException("Topology can not be null nor empty");
        }

        deploymentListener.on(id, "master.lookup", null);

        // get nodes to deploy first
        // For now we just look at the master
        // TODO : Deployment rules to be plugged here
        final Optional<PetalsRuntime> master = Iterables.tryFind(topology.getRuntimes(), new Predicate<PetalsRuntime>() {
            public boolean apply(org.ow2.petals.cloud.controllers.api.runtime.PetalsRuntime input) {
                return input.getMode() == PetalsRuntime.MODE.MASTER;
            }
        });

        Set<PetalsRuntime> remains = null;

        /*
        if (master.isPresent()) {
            deploymentListener.on(id, "master.found", null);
            // deploy the master first...
            // TODO : Create properties to be injected in the VM

            Properties props = new Properties();
            props.put("localcontainerid", master.get().getName());
            props.put("controllerEndpoint", "http://locahost");
            props.put("virtualContainerId", id);
            // TODO : Fill more data from runtime

            Node node = null;
            try {
                deploymentListener.on(id, "master.create", "%s", master.get().getName());
                node = providerManager.createNode(DEFAULT, props);
                listener.onCreateSuccess(node);
            } catch (Exception e) {
                listener.onCreateFailure(node);
            }

            deploymentListener.on(id, "master.start", "%s", master.get().getName());
            long time = 10;
            try {
                deploymentListener.on(id, "master.wait", "%s", master.get().getName());
                PetalsNodeUtils.waitNodeToStart(master.get(), time, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                listener.onDeployFailure(node);
                throw new CloudManagerException("Can not start the Petals node within " + time + TimeUnit.MINUTES);
            }
            listener.onDeploySuccess(node);

            remains = Sets.filter(Sets.newHashSet(topology.getRuntimes()), new Predicate<PetalsRuntime>() {
                public boolean apply(org.ow2.petals.controllers.manager.runtime.PetalsRuntime runtime) {
                    return !runtime.getName().equals(master.get().getName());
                }
            });

        } else {
            deploymentListener.on(id, "!master.found", null);
            remains = Sets.newHashSet(topology.getRuntimes());
        }

        for(PetalsRuntime runtime : remains) {

            Properties props = new Properties();
            props.put("localcontainerid", master.get().getName());
            props.put("controllerEndpoint", "http://locahost");
            props.put("virtualContainerId", id);
            // TODO : Fill more data from runtime

            Node n = null;
            try {
                deploymentListener.on(id, "node.create", "%s", runtime.getName());
                n = providerManager.createNode(VMProperties.DEFAULT, env);
                listener.onCreateSuccess(n);
            } catch (Exception e) {
                listener.onCreateFailure(n);
            }

            deploymentListener.on(id, "node.start", "%s", runtime.getName());

            // TODO : Check that all nodes have been successfully started.
            // Note this can be done on the controller level

        }
        deploymentListener.on(id, "deploy.done", null);
        */
        return null;
    }

}
