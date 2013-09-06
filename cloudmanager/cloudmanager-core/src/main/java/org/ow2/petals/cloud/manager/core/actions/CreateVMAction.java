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
package org.ow2.petals.cloud.manager.core.actions;

import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.deployment.VM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CreateVMAction extends MonitoredAction {

    private static Logger logger = LoggerFactory.getLogger(CreateVMAction.class);

    public CreateVMAction() {
        super();
    }

    public void doExecute(Context context) throws CloudManagerException {
        ProviderManager provider = getProviderManager(context);
        Deployment descriptor = getDescriptor(context);
        Node node = getNode(context);
        Provider account = getProvider(context);
        logger.info("Creating new node on {}", provider.getProviderName());

        if (node.getVm() == null) {
            node.setVm(descriptor.getVm());
        }
        if (node.getVm() == null) {
            // ...
            logger.warn("VM information is not set for node {}. Using defaults...", node.getId());
            VM vm = new VM();
            vm.setImage("");
            vm.setOs("");
            node.setVm(vm);
        }

        Node result = provider.createNode(account, node);
        logger.info("Node {} is created on provider {}", result.getId(), provider.getProviderName());
        if (logger.isDebugEnabled()) {
            logger.debug(result.toString());
        }

        notity(context, node, "create", "done", "Node has been created");

        // FIXME : Make a choice
        // TODO: return the node result in the context or merge relevant information in the input one...
        context.setOutput("node." + node.getId(), result);
    }

    @Override
    protected String getName() {
        return CreateVMAction.class.getName();
    }

}
