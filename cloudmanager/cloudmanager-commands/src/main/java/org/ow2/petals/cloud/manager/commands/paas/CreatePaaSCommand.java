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
package org.ow2.petals.cloud.manager.commands.paas;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.ow2.petals.cloud.manager.api.CloudManager;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.tools.DeploymentProvider;
import org.ow2.petals.cloud.manager.api.deployment.utils.PropertyHelper;
import org.ow2.petals.cloud.manager.api.utils.DeploymentListenerList;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Create a new PaaS command. Just give the initial PaaS size, the controller will do the rest...
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "paas", name = "create", description = "Create a new Petals PaaS")
public class CreatePaaSCommand extends BaseCommand {

    @Option(name = "--size", description = "Number of nodes to create", required = true)
    protected Integer size;

    @Option(name = "--type", description = "Type of PaaS to create", required = true)
    protected String type;

    @Option(name = "--iaas", description = "Where to deploy the PaaS", required = true)
    protected String iaas;

    public CreatePaaSCommand(CloudManager cloudManager, List<DeploymentProvider> supportedRuntimes) {
        super(cloudManager, supportedRuntimes);
    }

    @Override
    protected Object doExecute() throws Exception {

        DeploymentProvider deploymentProvider = getDeploymentProvider(type);
        if (deploymentProvider == null) {
            throw new Exception("Can not find deployment provider for type : " + type);
        }

        // TODO : The provider must provide a set of requirements which must be filled.
        Map<String, String> args = Maps.newHashMap();
        args.put("size", "" + size);
        // TODO : more args

        // set input values to the descriptor
        Deployment descriptor = new Deployment();
        descriptor.getProperties().add(PropertyHelper.get("size", null, "" + size));
        descriptor.getProperties().add(PropertyHelper.get("iaas", null, iaas));

        // enrich descriptor using the paas deployment provider
        // TODO : Move to the management service?
        deploymentProvider.populate(descriptor, args);

        // TODO : Async
        this.cloudManager.getManagementService().create(descriptor, new DeploymentListenerList(deploymentProvider.getDeploymentListeners()));
        return "PaaS has been created with size " + size;
    }

    @VisibleForTesting
    void setSize(Integer size) {
        this.size = checkNotNull(size, "size is null");
    }

    @VisibleForTesting
    void setType(String type) {
        this.type = checkNotNull(type, "type is null");
    }

    @VisibleForTesting
    void setIaas(String iaas) {
        this.iaas = checkNotNull(iaas, "iaas is null");
    }
}
