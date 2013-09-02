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
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.listeners.NodeManagerListener;
import org.ow2.petals.cloud.manager.core.actions.CreateVMAction;
import org.ow2.petals.cloud.manager.core.actions.InstallSoftwareAction;
import org.ow2.petals.cloud.manager.core.actions.RunScriptsAction;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of the IaaS/PaaS ManagementService.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DefaultManagementService implements org.ow2.petals.cloud.manager.api.services.ManagementService {

    private List<ProviderManager> providers;

    private NodeManagerListener listener;

    public DefaultManagementService(List<ProviderManager> providers, NodeManagerListener listener) {
        this.providers = providers;
        this.listener = listener;
    }

    public PaaS create(Deployment descriptor) throws CloudManagerException {
        checkNotNull(descriptor, "Deployment descriptor can not be null");
        if (descriptor.getId() == null) {
            descriptor.setId(UUID.randomUUID().toString());
        }

        // NOTE : will be nice to do it with a workflow instead of this dirty Java code...

        for(org.ow2.petals.cloud.manager.api.deployment.Node node : descriptor.getNodes()) {
            if (node.getProvider() != null) {
                ProviderManager provider = getProvider(node.getProvider());
                if (provider != null) {

                    CreateVMAction create = new CreateVMAction();
                    Context context = new Context(descriptor.getId());
                    context.setProviderManaer(provider);
                    context.setNode(node);
                    create.execute(context);

                    InstallSoftwareAction install = new InstallSoftwareAction();
                    install.execute(context);

                    RunScriptsAction run = new RunScriptsAction();
                    run.execute(context);

                } else {
                    //
                }
            } else {
                // skip
            }
        }
        return null;
    }

    /**
     * Get the provider from the platform
     *
     * @param provider
     * @return
     */
    protected ProviderManager getProvider(final String provider) {
        return Iterables.tryFind(providers, new Predicate<ProviderManager>() {
            public boolean apply(ProviderManager input) {
                return provider.equalsIgnoreCase(input.getProviderName());
            }
        }).orNull();
    }

    public List<PaaS> list() throws CloudManagerException {
        return null;
    }

    public PaaS get(String id) throws CloudManagerException {
        return null;
    }

    public boolean delete(String id) throws CloudManagerException {
        return false;
    }

}
