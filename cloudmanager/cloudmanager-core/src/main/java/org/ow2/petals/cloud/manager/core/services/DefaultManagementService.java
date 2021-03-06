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
package org.ow2.petals.cloud.manager.core.services;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.core.actions.CreateVMAction;
import org.ow2.petals.cloud.manager.api.utils.DeploymentListenerList;
import org.ow2.petals.cloud.manager.core.actions.PuppetConfigureNodeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of the IaaS/PaaS ManagementService. The deployment descriptor is filled with all the information that is required to create the PaaS.
 * This deployment information may be updated by the deployment process.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DefaultManagementService implements org.ow2.petals.cloud.manager.api.services.ManagementService {

    private static Logger logger = LoggerFactory.getLogger(DefaultManagementService.class);

    private List<ProviderManager> providers;

    private List<DeploymentListener> listeners;

    public DefaultManagementService(List<ProviderManager> providers, List<DeploymentListener> listeners) {
        this.providers = providers;
        this.listeners = listeners;
    }

    public PaaS create(Deployment descriptor, DeploymentListener deploymentListener) throws CloudManagerException {
        checkNotNull(descriptor, "Deployment descriptor can not be null");
        if (descriptor.getId() == null) {
            descriptor.setId(UUID.randomUUID().toString());
        }

        PaaS paas = new PaaS();
        paas.setUuid(descriptor.getId());
        paas.setCreatedAt(new Date());

        // create a set of listeners within the current deployment lifetime
        // This uses the default platform level listeners + the input one
        DeploymentListenerList listeners = new DeploymentListenerList();
        if (this.listeners != null) {
            listeners.add(this.listeners);
        }
        listeners.addListener(deploymentListener);

        // FIXME : will be nice to do it with a workflow instead of this dirty Java code...

        /*
         * For each node:
         * - Create the VM, get back the IP and update the node information with create results (VM properties)
         * - Copy files if any defined
         * - Install additionnal software if any defined
         * - Run final scripts if any defined
         */

        // deploy nodes based on their priority
        for(org.ow2.petals.cloud.manager.api.deployment.Node node : getOrderedNodes(descriptor.getNodes())) {
            logger.info("Creating node {}", node);

            if (node.getProvider() != null) {
                Provider account = getProvider(descriptor, node);
                ProviderManager providerManager = getProviderManager(account);

                if (logger.isDebugEnabled()) {
                    logger.debug("Provider account : {}", account);
                    logger.debug("Provider Manager : {} {}", providerManager.getProviderName(), providerManager.getProviderVersion());
                }

                if (providerManager != null) {

                    Context context = new Context(descriptor.getId());
                    context.setProviderManager(providerManager);
                    context.setNode(node);
                    context.setListener(listeners);
                    context.setDescriptor(descriptor);
                    context.setProvider(account);

                    CreateVMAction create = new CreateVMAction();
                    listeners.on(descriptor.getId(), node, "create", "init", "Creating VM");
                    create.execute(context);

                    // generate all the configuration files and script
                    // then copy on the node
                    PuppetConfigureNodeAction configureNodeAction = new PuppetConfigureNodeAction();
                    listeners.on(descriptor.getId(), node, "configure", "init", "Creating VM");
                    configureNodeAction.execute(context);

                    paas.getNodes().add(node);

                    // copy files which have been set in the context
                    // TODO

                    // once all is done, run startup scripts (if any)
                    // TODO!!!
                    //RunRemoteScriptAction install = new RunRemoteScriptAction();
                    //install.execute(context);
                } else {
                    //
                    logger.warn("Can not find the provider {} defined in node {}", node.getProvider(), node);
                }
            } else {
                // skip
                logger.warn("Can not find provider ID for node {}", node);
            }
        }
        return paas;
    }

    /**
     * Get the provider from the platform
     *
     * @param provider
     * @return
     */
    protected ProviderManager getProviderManager(final Provider provider) {
        if (provider == null) {
                return null;
        }

        return Iterables.tryFind(providers, new Predicate<ProviderManager>() {
            public boolean apply(ProviderManager input) {
                return provider.getType().equals(input.getProviderName());
            }
        }).orNull();
    }

    /**
     * Get the account to be used with the node and defined provider manager
     *
     * @param node
     * @return
     */
    protected Provider getProvider(final Deployment descriptor, final Node node) {
        return Iterables.tryFind(descriptor.getProviders(), new Predicate<Provider>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Provider input) {
                return input.getName().equals(node.getProvider());
            }
        }).orNull();
    }

    protected List<Node> getOrderedNodes(List<Node> nodes) {
        Ordering<Node> ordering = Ordering.from(new Comparator<Node>() {
            public int compare(Node a, Node b) {
                return Ints.compare(NodeHelper.getPriority(b), NodeHelper.getPriority(a));
            }
        });
        return ordering.sortedCopy(nodes);
    }

    public List<PaaS> list() throws CloudManagerException {
        return null;
    }

    public PaaS get(String id) throws CloudManagerException {
        return null;
    }

    public boolean delete(String id, DeploymentListener listener) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

}
