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
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
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
        Node node = getNode(context);
        Provider account = getProvider(context);
        logger.info("Creating new node on {}", provider.getProviderName());

        Node result = provider.createNode(account, node);
        logger.info("Node {} is started on provider {}", result.getId(), provider.getProviderName());
        if (logger.isDebugEnabled()) {
            logger.debug(result.toString());
        }
    }

    @Override
    protected String getName() {
        return CreateVMAction.class.getName();
    }

}
