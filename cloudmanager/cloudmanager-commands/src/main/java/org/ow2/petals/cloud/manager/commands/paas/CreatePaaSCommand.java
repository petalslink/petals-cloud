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
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.ow2.petals.cloud.manager.api.CloudManager;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.*;
import org.ow2.petals.cloud.manager.api.deployment.tools.DeploymentProvider;
import org.ow2.petals.cloud.manager.api.deployment.utils.PropertyHelper;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;
import org.ow2.petals.cloud.manager.api.utils.DeploymentListenerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(CreatePaaSCommand.class);

    private final ProviderRegistryService providerRegistry;

    @Option(name = "--size", description = "Number of nodes to create", required = true)
    protected Integer size;

    @Option(name = "--type", description = "Type of PaaS to create (check paas:providers)", required = true)
    protected String type;

    /**
     * The iaas is the account name of the IaaS to target. The provider must be retrieved from this name and injected into the nodes.
     */
    @Option(name = "--iaas", description = "The account used to deploy the PaaS", required = true)
    protected String iaas;

    public CreatePaaSCommand(CloudManager cloudManager, List<DeploymentProvider> supportedRuntimes, ProviderRegistryService providerRegistryService) {
        super(cloudManager, supportedRuntimes);
        this.providerRegistry = providerRegistryService;
    }

    @Override
    protected Object doExecute() throws Exception {

        DeploymentProvider deploymentProvider = getDeploymentProvider(type);
        if (deploymentProvider == null) {
            throw new Exception("Can not find deployment provider for type : " + type);
        }

        Provider provider = getProvider(iaas);
        if (provider == null) {
            throw new Exception("Can not find IaaS provider from IaaS account " + iaas);
        }

        // TODO : The provider must provide a set of requirements which must be filled.
        Map<String, String> args = Maps.newHashMap();
        args.put("size", "" + size);
        // TODO : more args...

        // set input values to the descriptor
        Deployment descriptor = new Deployment();
        descriptor.getProperties().add(PropertyHelper.get("size", null, "" + size));
        descriptor.getProperties().add(PropertyHelper.get("iaas", null, iaas));
        // FIXME : env
        descriptor.getProperties().add(PropertyHelper.get("iaas.key", null, "petals"));
        descriptor.getProviders().add(provider);

        // enrich descriptor using the paas deployment provider
        // TODO : Move to the management service?
        deploymentProvider.populate(descriptor, args);

        for(Node node : descriptor.getNodes()) {
            // add iaas
            // TODO : Can be based on rules to dispatch deployment on several nodes.
            node.setProvider(provider.getName());
        }

        // once populated, add metadatas
        for(Node node : descriptor.getNodes()) {
            List<Property> nodeMetadata = deploymentProvider.getMetadata(descriptor, node);
            if (nodeMetadata != null) {
                for(Property meta : nodeMetadata) {
                    node.getProperties().add(PropertyHelper.get(meta.getName(), "metadata", meta.getValue()));
                }
            }
        }

        List<Property> deploymentMetadata = deploymentProvider.getMetadata(descriptor);
        if (deploymentMetadata != null) {
            for(Property meta : deploymentMetadata) {
                descriptor.getProperties().add(PropertyHelper.get(meta.getName(), "metadata", meta.getValue()));
            }
        }

        // add VM information
        VM vm = new VM();
        vm.setImage("");
        vm.setOs("");
        descriptor.setVm(vm);

        // TODO : Async
        PaaS paas = this.cloudManager.getManagementService().create(descriptor, new DeploymentListenerList(deploymentProvider.getDeploymentListeners()));
        this.cloudManager.getStorageService().create(paas);
        if (logger.isInfoEnabled()) {
            logger.info("PAAS {}", paas);
        }

        return paas;
    }

    /**
     * Get the provider from its name
     *
     * @param providerName
     * @return
     * @throws CloudManagerException
     */
    protected Provider getProvider(final String providerName) throws CloudManagerException {
        return Iterables.tryFind(providerRegistry.get(), new Predicate<Provider>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Provider input) {
                return input.getName() != null && input.getName().equals(providerName);
            }
        }).orNull();
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
